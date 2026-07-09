package com.efit.hrms.service;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    private ResourceLoader resourceLoader;

    // ==================================================
    // 11:30 AM LOGIN REPORT
    // ==================================================
//    @Scheduled(cron = "0 0 11 * * *")
    public void morningReport() {

        if (isHoliday()) {
            return;
        }

        sendMail("TimeLogIn Report");
    }

    // ==================================================
    // 10 PM LOGOUT REPORT
    // ==================================================
//    @Scheduled(cron = "0 0 22 * * *")
    public void nightReport() {

        if (isHoliday()) {
            return;
        }

        sendMail("TimeLogOut Report");
    }

    // ==================================================
    // COMMON MAIL METHOD
    // ==================================================
    public void sendMail(String subject) {

        try {

            // ==================================================
            // GET EMAIL IDS
            // ==================================================
            String empSql =
                    "SELECT DISTINCT  email\r\n"
                    + "FROM employee\r\n"
                    + "WHERE\r\n"
                    + "(\r\n"
        	        + "    designation IN\r\n"
        	        + "    ('MANAGING DIRECTOR', 'GENERAL MANAGER')\r\n"
        	        + "\r\n"
        	        + "    OR\r\n"
        	        + "\r\n"
                    + "    employeecode IN ('wds038', 'wds051')\r\n"
                    + ")\r\n"
                    + "AND email IS NOT NULL\r\n"
                    + "AND email <> ''";

            List<String> mailList =
                    jdbcTemplate.queryForList(
                            empSql,
                            String.class
                    );

            if (mailList.isEmpty()) {

                System.out.println("No Mail IDs Found");
                return;
            }

            // ==================================================
            // REPORT DATA
            // ==================================================
            String sql =
                    "SELECT\r\n"
                    + "    e.employee AS Employee,\r\n"
                    + "\r\n"
                    + "    e.employeecode AS Code,\r\n"
                    + "\r\n"
                    + "    a.checkindate AS Date,\r\n"
                    + "\r\n"
                    + "    a.intime AS Checkin,\r\n"
                    + "\r\n"
                    + "    a.outtime AS Checkout\r\n"
                    + "\r\n"
                    + "FROM employee e\r\n"
                    + "\r\n"
                    + "LEFT JOIN attendancedaily a\r\n"
                    + "       ON a.empcode = e.employeecode\r\n"
                    + "      AND a.checkindate = CURRENT_DATE() \r\n"
                    + "\r\n"
                    + "WHERE e.orgid = '1000000001'\r\n"
                    + "AND e.active = 1\r\n"
                    + "\r\n"
                    + "AND e.employeecode NOT IN\r\n"
                    + "(\r\n"
                    + "    'wds001',\r\n"
                    + "    'wds008',\r\n"
                    + "    'wds025'\r\n"
                    + ")\r\n"
                    + "\r\n"
                    + "ORDER BY \r\n"
                    + "    CASE \r\n"
                    + "        WHEN a.intime IS NULL THEN 1\r\n"
                    + "        ELSE 0\r\n"
                    + "    END,\r\n"
                    + "    a.intime ASC,\r\n"
                    + "    e.employee ASC";

            List<Map<String, Object>> list =
                    jdbcTemplate.queryForList(sql);

            // ==================================================
            // COMMON CELL STYLE
            // ==================================================
            String tdStyle = "padding:5px 6px;border:1px solid #ddd;font-size:13px;";

            // ==================================================
            // BUILD TABLE ROWS
            // ==================================================
            StringBuilder rows = new StringBuilder();

            int i = 1;

            for (Map<String, Object> row : list) {

                rows.append("<tr>");

                // SERIAL NUMBER
                rows.append("<td style='").append(tdStyle).append("'>")
                        .append(i++)
                        .append("</td>");

                // EMPLOYEE NAME
                rows.append("<td style='").append(tdStyle).append("'>")
                        .append(row.get("Employee"))
                        .append("</td>");

                // EMPLOYEE CODE
                rows.append("<td style='").append(tdStyle).append("'>")
                        .append(row.get("Code"))
                        .append("</td>");

                // ==================================================
                // CHECK IN
                // ==================================================
                Object checkinObj = row.get("Checkin");

                if (checkinObj == null) {

                    // NO CHECKIN
                    rows.append("<td style='").append(tdStyle).append("'>-</td>");

                } else {

                    LocalTime inTime =
                            ((java.sql.Time) checkinObj).toLocalTime();

                    // ==========================================
                    // AFTER 10:58 -> SHOW "-"
                    // ==========================================
                    LocalTime allowedTime =
                            LocalTime.of(10, 58);

                    if (inTime.isAfter(allowedTime)) {

                        rows.append("<td style='").append(tdStyle).append("'>-</td>");

                    } else {

                        String checkin = checkinObj.toString();

                        LocalDate date =
                                ((java.sql.Date) row.get("Date")).toLocalDate();

                        DayOfWeek day = date.getDayOfWeek();

                        // ==========================================
                        // LATE MARK TIME
                        // SATURDAY -> 09:31
                        // OTHER DAYS -> 10:01
                        // ==========================================
                        LocalTime limitTime =
                                (day == DayOfWeek.SATURDAY)
                                        ? LocalTime.of(9, 31)
                                        : LocalTime.of(10, 1);

                        // ==========================================
                        // RED COLOR FOR LATE
                        // ==========================================
                        if (!inTime.isBefore(limitTime)) {

                            rows.append("<td style='").append(tdStyle)
                                    .append("color:red;font-weight:bold;'>")
                                    .append(checkin)
                                    .append("</td>");

                        } else {

                            rows.append("<td style='").append(tdStyle).append("'>")
                                    .append(checkin)
                                    .append("</td>");
                        }
                    }
                }

                // ==================================================
                // CHECK OUT
                // ==================================================
                if (!subject.equalsIgnoreCase("TimeLogIn Report")) {

                    rows.append("<td style='").append(tdStyle).append("'>")
                            .append(
                                    row.get("Checkout") == null
                                            ? "-"
                                            : row.get("Checkout")
                            )
                            .append("</td>");
                }

                rows.append("</tr>");
            }

            // ==================================================
            // LOAD HTML TEMPLATE
            // ==================================================
            Resource resource =
                    resourceLoader.getResource(
                            "classpath:templates/checkinout_report.html"
                    );

            String html =
                    new String(
                            resource.getInputStream().readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            // ==================================================
            // REPLACE SUBJECT
            // ==================================================
            html = html.replace("${subject}", subject);

            // ==================================================
            // REPLACE DATE
            // ==================================================
            String formattedDate =
                    LocalDate.now()
                    .format(
                        java.time.format.DateTimeFormatter
                        .ofPattern("dd-MM-yyyy")
                    );

            html = html.replace(
                    "${date}",
                    formattedDate
            );

            // ==================================================
            // COLGROUP + CHECK OUT HEADER (kept in sync)
            // ==================================================
            boolean includeCheckout = !subject.equalsIgnoreCase("TimeLogIn Report");

            String colgroup =
                    includeCheckout
                    ? "<colgroup><col style='width:65px;'><col style='width:220px;'><col style='width:120px;'><col style='width:130px;'><col style='width:130px;'></colgroup>"
                    : "<colgroup><col style='width:65px;'><col style='width:220px;'><col style='width:120px;'><col style='width:130px;'></colgroup>";

            html = html.replace("${colgroup}", colgroup);

            if (!includeCheckout) {

                html = html.replace(
                        "${checkoutHeader}",
                        ""
                );

            } else {

                html = html.replace(
                        "${checkoutHeader}",
                        "<th style='padding:5px 6px;background:#198754;color:#ffffff;border:1px solid #ddd;text-align:left;font-size:13px;'>Check Out</th>"
                );
            }

            // ==================================================
            // REPLACE ROWS
            // ==================================================
            html = html.replace(
                    "${rows}",
                    rows.toString()
            );

            // ==================================================
            // DEBUG HTML
            // ==================================================
            System.out.println(html);

            // ==================================================
            // SEND MAIL
            // ==================================================
            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true,
                            "UTF-8"
                    );

            helper.setTo(
                    mailList.toArray(new String[0])
            );

            helper.setSubject(subject);

            helper.setText(html, true);

            mailSender.send(message);

            System.out.println("Mail Sent Successfully");

        } catch (Exception e) {

            System.out.println("MAIL ERROR");
            System.out.println(e.getMessage());

            e.printStackTrace();
        }
    }

    // ==================================================
    // HOLIDAY CHECK
    // ==================================================
    public boolean isHoliday() {

        try {

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

            // SUNDAY HOLIDAY
            if (day == DayOfWeek.SUNDAY) {
                return true;
            }

            return count != null && count > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}