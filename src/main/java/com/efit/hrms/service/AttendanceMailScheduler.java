package com.efit.hrms.service;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@EnableScheduling
public class AttendanceMailScheduler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private org.springframework.core.io.ResourceLoader resourceLoader;

    // ==========================================
    // 11 AM
    // ==========================================
    @Scheduled(cron = "0 0 11 * * *")
    public void morningReport() {

    	if (isHoliday()) {
            return;
        }
    	
        sendMail(
            "TimeLogIn Report"
        );
    }
    
    // ==========================================
    // 9 PM
    // ==========================================
    @Scheduled(cron = "0 0 22 * * *")
    public void nightReport() {

    	if (isHoliday()) {
            return;
        }
    	
        sendMail(
            "TimeLogOut Report"
        );
    }

    // ==========================================
    // COMMON METHOD
    // ==========================================
    public void sendMail(
            String subject) {

        try {

            // ===============================
            // GET MAIL IDS
            // ===============================
            String empSql =
                "SELECT email FROM employee " +
                "WHERE designation IN " +
                "('MANAGING DIRECTOR'," +
                "'GENERAL MANAGER') " +
                "AND email IS NOT NULL " +
                "AND email <> ''";
        	
//        	String empSql =
//                    "SELECT email FROM employee " +
//                    "WHERE orgid=1000000001 and active=1 and  designation IN " +
//                    "('SOFTWARE DEVELOPER') " +
//                    "AND email IS NOT NULL " +
//                    "AND email <> ''";

            List<String> mailList =
                jdbcTemplate.queryForList(
                    empSql,
                    String.class
                );

            if (mailList.isEmpty()) {
                return;
            }

            // ===============================
            // REPORT DATA
            // ===============================
            String sql =
                "SELECT \r\n"
                + "    empname AS Employee,\r\n"
                + "    empcode AS Code,\r\n"
                + "    checkindate as Date,\r\n"
                + "    CASE \r\n"
                + "        WHEN checkindate = CURRENT_DATE() \r\n"
                + "             AND intime IS NOT NULL\r\n"
                + "        THEN intime\r\n"
                + "        ELSE NULL\r\n"
                + "    END AS Checkin,\r\n"
                + "\r\n"
                + "    CASE \r\n"
                + "        WHEN checkoutdate = CURRENT_DATE() \r\n"
                + "             AND outtime IS NOT NULL\r\n"
                + "        THEN outtime\r\n"
                + "        ELSE NULL\r\n"
                + "    END AS Checkout\r\n"
                + "\r\n"
                + "FROM attendancedaily\r\n"
                + "\r\n"
                + "WHERE orgid = 1000000001\r\n"
                + "AND (\r\n"
                + "      checkindate = CURRENT_DATE()\r\n"
                + "   OR checkoutdate = CURRENT_DATE()\r\n"
                + ") ORDER BY empname ASC";

            List<Map<String, Object>> list =
                jdbcTemplate.queryForList(sql);

            // ===============================
            // ROWS
            // ===============================
            StringBuilder rows =
                new StringBuilder();

            int i = 1;

            for (Map<String, Object> row : list) {

                rows.append("<tr>");

                rows.append("<td>")
                    .append(i++)
                    .append("</td>");

                rows.append("<td>")
                    .append(row.get("Employee"))
                    .append("</td>");

                rows.append("<td>")
                    .append(row.get("Code"))
                    .append("</td>");

                Object checkinObj = row.get("Checkin");

                if (checkinObj == null) {

                    rows.append("<td>-</td>");

                } else {

                    String checkin =
                        checkinObj.toString();

                    LocalTime inTime = LocalTime.parse(checkin);

                    LocalDate date = ((java.sql.Date) row.get("Date")).toLocalDate();
                    DayOfWeek day = date.getDayOfWeek();

                    LocalTime limitTime = (day == DayOfWeek.SATURDAY)
                            ? LocalTime.of(9, 1)
                            : LocalTime.of(10, 1);

                    if (!inTime.isBefore(limitTime)) {

                        rows.append("<td style='color:red;font-weight:bold;'>")
                            .append(checkin)
                            .append("</td>");

                    } else {

                        rows.append("<td>")
                            .append(checkin)
                            .append("</td>");
                    }
                }


                if (!subject.equalsIgnoreCase(
                        "TimeLogIn Report")) {

                    rows.append("<td>")
                        .append(
                            row.get("Checkout") == null
                            ? "-"
                            : row.get("Checkout")
                        )
                        .append("</td>");
                }
                rows.append("</tr>");
            }

            // ===============================
            // READ HTML FILE
            // ===============================
            org.springframework.core.io.Resource resource =
                resourceLoader.getResource(
                    "classpath:templates/checkinout_report.html"
                );

            String html =
                new String(
                    resource.getInputStream()
                            .readAllBytes(),
                    StandardCharsets.UTF_8
                );

            html = html.replace(
                "${subject}",
                subject
            );

            html = html.replace(
                "${date}",
                LocalDate.now().toString()
            );
            if (subject.equalsIgnoreCase(
                    "TimeLogIn Report")) {

                html = html.replace(
                    "${checkoutHeader}",
                    ""
                );

            } else {

                html = html.replace(
                    "${checkoutHeader}",
                    "<th>Check Out</th>"
                );
            }
            html = html.replace(
                "${rows}",
                rows.toString()
            );

            // ===============================
            // SEND MAIL
            // ===============================
            MimeMessage message =
                mailSender.createMimeMessage();

            MimeMessageHelper helper =
                new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8"
                );

            helper.setTo(
                mailList.toArray(
                    new String[0]
                )
            );

            helper.setSubject(subject);

            helper.setText(
                html,
                true
            );

            mailSender.send(message);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
 // ==========================================
 // ADD THIS METHOD INSIDE SAME SCHEDULER CLASS
 // HOLIDAY CHECK - IF HOLIDAY RETURN
 // ==========================================

 public boolean isHoliday() {

     String sql =
         "SELECT COUNT(*) " +
         "FROM holidays " +
         "WHERE orgid = 1000000001 " +
         "AND active = 1 " +
         "AND cancel = 0 " +
         "AND holidaydate = CURRENT_DATE()";

     Integer count =
         jdbcTemplate.queryForObject(
             sql,
             Integer.class
         );
     
     DayOfWeek day =
    		    LocalDate.now().getDayOfWeek();

    		if (day == DayOfWeek.SUNDAY) {
    		    return true;
    		}

     return count != null && count > 0;
 }
}