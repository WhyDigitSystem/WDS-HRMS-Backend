package com.efit.hrms.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.entity.CompanyVO;
import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.repo.AttendanceProcessRepo;
import com.efit.hrms.repo.CompanyRepo;
import com.efit.hrms.repo.EmployeeRepo;

@Service
public class NewDashBoardServiceImpl implements NewDashBoardService{

	public static final Logger LOGGER = LoggerFactory.getLogger(NewDashBoardServiceImpl.class);
	
	@Autowired
	AttendanceProcessRepo attendanceProcessRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	CompanyRepo companyRepo;
	
	 @Autowired
	 private JavaMailSender mailSender;
	
	@Override
	public List<Map<String, Object>> getMonthlyAttendanceForDashBoard(String employeeCode, Long orgId,
			String department, String branch, String type, String contractor) {

		Set<Object[]> result = attendanceProcessRepo.getLeaveCountForDashBoard(employeeCode, orgId,
				department, branch, type, contractor);
		return getLeaveCountForDashBoard(result);
	}

	private List<Map<String, Object>> getLeaveCountForDashBoard(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();
		  if (result == null || result.isEmpty()) {

		        String currentMonth = LocalDate.now()
		                .getMonth()
		                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

		        throw new RuntimeException(
		                "NO DATA FOUND IN " + currentMonth.toUpperCase() + " MONTH.");
		    }
		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", record[0] != null ? record[0].toString() : "");
			map.put("employeeCode", record[1] != null ? record[1].toString() : "");
			map.put("month", record[2] != null ? record[2].toString() : "0");
			map.put("year", record[3] != null ? record[3].toString() : "0");
			map.put("totalDays", record[4] != null ? record[4].toString() : "0");
			map.put("salaryDays", record[5] != null ? record[5].toString() : "0");

			detailsList.add(map);
		}
		return detailsList;
	}

	//takenleave api
	
	 @Override
	    public List<Map<String, Object>> getLeaveTakenReport(
	            Long orgId,
	            String employeecode
	    ) {

	        List<Map<String, Object>> rawList =
	        		attendanceProcessRepo.getLeaveTakenReport(
	                        orgId,
	                        employeecode
	                );

	        List<Map<String, Object>> orderedList =
	                new ArrayList<>();

	        for (Map<String, Object> row : rawList) {

	            Map<String, Object> orderedMap =
	                    new LinkedHashMap<>();

	            orderedMap.put(
	                    "employeecode",
	                    row.get("employeecode")
	            );

	            orderedMap.put(
	                    "employee",
	                    row.get("employee")
	            );

	            orderedMap.put(
	                    "CL",
	                    row.get("CL")
	            );

	            orderedMap.put(
	                    "COMP-OFF",
	                    row.get("COMP-OFF")
	            );

	            orderedMap.put(
	                    "LOP",
	                    row.get("LOP")
	            );

	            orderedList.add(orderedMap);
	        }

	        return orderedList;
	    }

	 
	 //latelogin api
	 
	 @Override
	 public List<Map<String, Object>> getLateLoginReportforDashBoard(
	         Long orgId,
	         String branchcode,
	         String employeecode
	 ) {

	     List<Map<String, Object>> rawList =
	    		 attendanceProcessRepo.getLateLoginReportforDashBoard(
	                     orgId,
	                     branchcode,
	                     employeecode
	             );

	     List<Map<String, Object>> orderedList =
	             new ArrayList<>();

	     for (Map<String, Object> row : rawList) {

	         Map<String, Object> orderedMap =
	                 new LinkedHashMap<>();

	         orderedMap.put(
	                 "employeecode",
	                 row.get("employeecode")
	         );

	         orderedMap.put(
	                 "employee",
	                 row.get("empname")
	         );

	         orderedMap.put(
	                 "lateCheckinCount",
	                 row.get("late_checkin_count")
	         );

	         orderedMap.put(
	                 "lateDates",
	                 row.get("late_dates")
	         );

	         orderedList.add(orderedMap);
	     }

	     return orderedList;
	 }
	 
	 
	 @Override
	 public List<Map<String, Object>> pendingApprovalForDashBoard(
	         Long orgId,String employeeCode) {

	     String methodName = "pendingApprovalForDashBoard()";

	     LOGGER.debug(
	             CommonConstant.STARTING_METHOD,
	             methodName);

	     List<Map<String, Object>> approvalList =
	             new ArrayList<>();

	     try {

	         approvalList =
	        		 attendanceProcessRepo
	                         .getpendingApprovalForDashBoard(orgId,employeeCode);

	     } catch (Exception e) {

	         LOGGER.error(
	                 UserConstants.ERROR_MSG_METHOD_NAME,
	                 methodName,
	                 e.getMessage());

	         e.printStackTrace();
	     }

	     LOGGER.debug(
	             CommonConstant.ENDING_METHOD,
	             methodName);

	     return approvalList;
	 }
	 
	 //working hours api
	 
	 @Override
	 public List<Map<String, Object>> getAttendanceDashboard(
	         Long orgId,
	         String employeecode) {

	     List<Map<String, Object>> rawList =
	             attendanceProcessRepo.getAttendanceDashboard(
	                     orgId,
	                     employeecode
	             );

	     List<Map<String, Object>> orderedList =
	             new ArrayList<>();

	     for (Map<String, Object> row : rawList) {

	         Map<String, Object> orderedMap =
	                 new LinkedHashMap<>();

	         orderedMap.put(
	                 "employeecode",
	                 row.get("employeecode")
	         );

	         orderedMap.put(
	                 "employee",
	                 row.get("employee")
	         );

	         orderedMap.put(
	                 "breaktime",
	                 row.get("breaktime")
	         );

	         orderedMap.put(
	                 "office_hours",
	                 row.get("office_hours")
	         );

	         orderedMap.put(
	                 "present_days",
	                 row.get("present_days")
	         );

	         orderedMap.put(
	                 "casual_leave",
	                 row.get("casual_leave")
	         );

	         orderedMap.put(
	                 "lop_leave",
	                 row.get("lop_leave")
	         );

	         orderedMap.put(
	                 "compoff_used",
	                 row.get("compoff_used")
	         );

	         orderedMap.put(
	                 "compoff_earned",
	                 row.get("compoff_earned")
	         );

	         orderedMap.put(
	                 "permission_hours",
	                 row.get("permission_hours")
	         );

	         orderedMap.put(
	                 "actual_worked_hours",
	                 row.get("actual_worked_hours")
	         );

	         orderedMap.put(
	                 "expected_working_hours",
	                 row.get("expected_working_hours")
	         );

	         orderedMap.put(
	                 "missing_hours",
	                 row.get("missing_hours")
	         );

	         orderedMap.put(
	                 "extra_hours",
	                 row.get("extra_hours")
	         );

	         orderedMap.put(
	                 "current_month_extra_hours",
	                 row.get("current_month_extra_hours")
	         );

	         orderedMap.put(
	                 "final_balance_hours",
	                 row.get("final_balance_hours")
	         );

	         orderedList.add(orderedMap);
	     }

	     return orderedList;
	 }
	 
	 
	 @Override
	 public List<Map<String, Object>> getRejectedRequests(
	         Long orgid,
	         String fromDate,
	         String toDate,
	         String employeecode,
	         String type) {

	     List<Map<String, Object>> rawList =
	    		 attendanceProcessRepo.getRejectedRequests(
	                     orgid,
	                     fromDate,
	                     toDate,
	                     employeecode,
	                     type);

	     List<Map<String, Object>> orderedList =
	             new ArrayList<>();

	     for (Map<String, Object> row : rawList) {

	         Map<String, Object> orderedMap =
	                 new LinkedHashMap<>();

	         orderedMap.put(
	                 "type",
	                 row.get("type")
	         );

	         orderedMap.put(
	                 "id",
	                 row.get("id")
	         );

	         orderedMap.put(
	                 "employeecode",
	                 row.get("employeecode")
	         );

	         orderedMap.put(
	                 "employeename",
	                 row.get("employeename")
	         );

	         orderedMap.put(
	                 "requestdate",
	                 row.get("requestdate")
	         );

	         orderedMap.put(
	                 "approvestatus",
	                 row.get("approvestatus")
	         );

	         orderedMap.put(
	                 "intime",
	                 row.get("intime")
	         );

	         orderedMap.put(
	                 "outtime",
	                 row.get("outtime")
	         );

	         orderedMap.put(
	                 "reason",
	                 row.get("reason")
	         );

	         orderedMap.put(
	                 "createdon",
	                 row.get("createdon")
	         );

	         orderedList.add(orderedMap);
	     }

	     return orderedList;
	 }
	 
	 
	 //monthlyAttendanceMailSend
	 
//	 @Scheduled(cron = "0 0 8 1 * ?")
//	 @Scheduled(cron = "*/30 * * * * ?")
	 public void sendMonthlyAttendanceMail() {
         CompanyVO mailCompany = null;

	     try {

	         // ADMIN EMPLOYEE
	         EmployeeVO adminEmployee =
	                 employeeRepo.findByEmployeeCode(
	                         "WDS038"
	                 );

	         if (adminEmployee == null
	                 || adminEmployee.getEmail() == null
	                 || adminEmployee.getEmail().trim().isEmpty()) {

	             return;
	         }

	         // GET ACTIVE EMPLOYEES
	         List<EmployeeVO> employees =
	                 employeeRepo.findByActiveTrue();

	         employees.sort(

	        	        Comparator.comparing(
	        	                EmployeeVO::getEmployeeName,
	        	                String.CASE_INSENSITIVE_ORDER
	        	        )
	        	);
	         
	         // EXCLUDED EMPLOYEES
	         List<String> excludedEmployees =
	                 Arrays.asList(
	                         "WDS025",
	                         "WDS008",
	                         "WDS001",
	                         "WDS0010",
	                         "WDS0008"
	                 );

	         StringBuilder tableRows =
	                 new StringBuilder();

	         int index = 0;

	         for (EmployeeVO emp : employees) {

	             try {

	                 // SKIP EXCLUDED EMPLOYEES
	                 if (excludedEmployees.contains(
	                         emp.getEmployeeCode()
	                 )) {

	                     continue;
	                 }

	                 // GET COMPANY
	                 Optional<CompanyVO> optionalCompany =
	                         companyRepo.findById(
	                                 emp.getOrgId()
	                         );

	                 if (!optionalCompany.isPresent()) {

	                     continue;
	                 }
	                 
	                 CompanyVO companyVO =
	                         optionalCompany.get();

	                 mailCompany = companyVO;
	                 // IMPORTANT CONDITION
	                 // ONLY TRUE COMPANY SEND
	                 if (!companyVO.isMonthlAttendanceMail()) {

	                     continue;
	                 }

	                 // GET ATTENDANCE DATA
	                 List<Map<String, Object>> list =
	                         attendanceProcessRepo
	                                 .getAttendanceDashboard(
	                                         emp.getOrgId(),
	                                         emp.getEmployeeCode()
	                                 );

	                 if (list == null
	                         || list.isEmpty()) {

	                     continue;
	                 }

	                 Map<String, Object> row =
	                         list.get(0);

	                 String expectedHours =
	                         String.valueOf(
	                                 row.get(
	                                         "expected_working_hours"
	                                 )
	                         );

	                 String actualHours =
	                         String.valueOf(
	                                 row.get(
	                                         "actual_worked_hours"
	                                 )
	                         );

	                 long expectedSeconds =
	                         convertToSeconds(
	                                 expectedHours
	                         );

	                 long actualSeconds =
	                         convertToSeconds(
	                                 actualHours
	                         );

	                 String actualHourColor =
	                         actualSeconds < expectedSeconds
	                         ? "red"
	                         : "green";

	                 String rowColor =
	                         index % 2 == 0
	                         ? "#ffffff"
	                         : "#f8fbff";

	                 long deficitSeconds =
	                	        expectedSeconds - actualSeconds;
	                 
	                 String deficitHours =
	                	        deficitSeconds > 0
	                	        ? formatSeconds(deficitSeconds)
	                	        : "00 Hr 00 Min";
	                 tableRows.append(

	                         "<tr style='background:"
	                         + rowColor
	                         + ";'>"

	                         + "<td style='padding:16px 22px;"
	                         + "border-bottom:1px solid #edf2f7;"
	                         + "font-size:14px;"
	                         + "font-weight:500;'>"

	                         + row.get("employeecode")

	                         + "</td>"

	                         + "<td style='padding:16px 22px;"
	                         + "border-bottom:1px solid #edf2f7;"
	                         + "font-size:14px;"
	                         + "font-weight:500;'>"

	                         + row.get("employee")

	                         + "</td>"

	                         + "<td style='padding:16px 22px;"
	                         + "border-bottom:1px solid #edf2f7;"
	                         + "font-size:14px;'>"

	                         + formatHours(expectedHours)

	                         + "</td>"

	                         + "<td style='padding:16px 22px;"
	                         + "border-bottom:1px solid #edf2f7;"
	                         + "font-size:14px;"
	                         + "font-weight:700;"
	                         + "color:"
	                         + actualHourColor
	                         + ";'>"

	                         + formatHours(actualHours)

	                         + "</td>"
	                         + "<td style='padding:16px 22px;"
							+ "border-bottom:1px solid #edf2f7;"
							+ "font-size:14px;"
							+ "font-weight:700;"
							+ "color:red;'>"
							
							+ deficitHours
							
							+ "</td>"

	                         + "</tr>"
	                 );

	                 index++;

	             } catch (Exception e) {

	                 e.printStackTrace();
	             }
	         }

	         // LOAD HTML TEMPLATE
	         String html =
	                 AttendanceMailTemplate
	                         .loadMonthlySummaryTemplate();

	         // PREVIOUS MONTH NAME
	         String monthName =
	                 java.time.LocalDate.now()
	                         .minusMonths(1)
	                         .getMonth()
	                         .getDisplayName(
	                                 java.time.format.TextStyle.FULL,
	                                 java.util.Locale.ENGLISH
	                         );

	         html = html.replace(
	                 "{{month_name}}",
	                 monthName
	         );

	         // REPLACE TABLE ROWS
	         html = html.replace(
	                 "{{table_rows}}",
	                 tableRows.toString()
	         );

	         // CREATE MAIL
	         MimeMessage mimeMessage =
	                 mailSender.createMimeMessage();

	         MimeMessageHelper helper =
	                 new MimeMessageHelper(
	                         mimeMessage,
	                         true,
	                         "UTF-8"
	                 );

	         helper.setTo(
	                 adminEmployee.getEmail()
	         );

	         helper.setSubject(
	                 "All Employee Attendance Summary"
	         );

	         helper.setText(
	                 html,
	                 true
	         );

	         
	         if (mailCompany != null
	        	        && mailCompany.getCompanyLogo() != null) {

	        	    ByteArrayResource image =
	        	            new ByteArrayResource(
	        	                    mailCompany.getCompanyLogo()
	        	            );

	        	    helper.addInline(
	        	            "companyLogo",
	        	            image,
	        	            "image/png"
	        	    );
	        	}
	         // SEND MAIL
	         mailSender.send(mimeMessage);

	         System.out.println(
	                 "Attendance Mail Sent Successfully"
	         );

	     } catch (Exception e) {

	         e.printStackTrace();
	     }
	 }

	 private long convertToSeconds(
	         String time
	 ) {

	     try {

	         String[] parts =
	                 time.split(":");

	         long hours =
	                 Long.parseLong(parts[0]);

	         long minutes =
	                 Long.parseLong(parts[1]);

	         long seconds =
	                 Long.parseLong(parts[2]);

	         return
	                 (hours * 3600)
	                 + (minutes * 60)
	                 + seconds;

	     } catch (Exception e) {

	         return 0;
	     }
	 }

	 private String formatHours(
	         String time
	 ) {

	     try {

	         String[] parts =
	                 time.split(":");

	         return
	                 parts[0]
	                 + " Hr "
	                 + parts[1]
	                 + " Min";

	     } catch (Exception e) {

	         return time;
	     }
	 }
	 
	 private String formatSeconds(
		        long totalSeconds
		) {

		    long hours =
		            totalSeconds / 3600;

		    long minutes =
		            (totalSeconds % 3600) / 60;

		    return
		            String.format(
		                    "%02d Hr %02d Min",
		                    hours,
		                    minutes
		            );
		}
	 
	 //send mail separateemployee
	 
//	 @Scheduled(cron = "0 0 8 1 * ?")
//	 @Scheduled(cron = "*/30 * * * * ?")
	 public void sendEmployeeAttendanceMail() {

	     try {

	         List<EmployeeVO> employees =
	                 employeeRepo.findByActiveTrue();
	        

	         List<String> excludedEmployees =
	                 Arrays.asList(
	                         "WDS025",
	                         "WDS008",
	                         "WDS001",
	                         "WDS0010",
	                         "WDS0008"
	                 );

	         String monthName =
	                 LocalDate.now()
	                         .minusMonths(1)
	                         .getMonth()
	                         .getDisplayName(
	                                 TextStyle.FULL,
	                                 Locale.ENGLISH
	                         );

	         for (EmployeeVO emp : employees) {

	             try {

	                 // EXCLUDED EMPLOYEE
	                 if (excludedEmployees.contains(
	                         emp.getEmployeeCode()
	                 )) {	

	                     continue;
	                 }

	                 // EMPTY EMAIL
	                 if (emp.getEmail() == null
	                         || emp.getEmail().trim().isEmpty()) {

	                     continue;
	                 }

	                 // COMPANY CHECK
	                 Optional<CompanyVO> optionalCompany =
	                         companyRepo.findById(
	                                 emp.getOrgId()
	                         );

	                 if (!optionalCompany.isPresent()) {
	                     continue;
	                 }

	                 CompanyVO companyVO =
	                         optionalCompany.get();
	                 
	                 if (!companyVO.isMonthlAttendanceMail()) {
	                     continue;
	                 }

	                 // ATTENDANCE
	                 List<Map<String, Object>> list =
	                         attendanceProcessRepo
	                                 .getAttendanceDashboard(
	                                         emp.getOrgId(),
	                                         emp.getEmployeeCode()
	                                 );

	                 if (list == null || list.isEmpty()) {
	                     continue;
	                 }

	                 Map<String, Object> row =
	                         list.get(0);

	                 String expectedHours =
	                         String.valueOf(
	                                 row.get(
	                                         "expected_working_hours"
	                                 )
	                         );

	                 String actualHours =
	                         String.valueOf(
	                                 row.get(
	                                         "actual_worked_hours"
	                                 )
	                         );

	                 long expectedSeconds =
	                         convertToSeconds(
	                                 expectedHours
	                         );

	                 long actualSeconds =
	                         convertToSeconds(
	                                 actualHours
	                         );
	                 
	                 long deficitSeconds =
	                	        expectedSeconds - actualSeconds;

	                	String deficitHours =
	                	        deficitSeconds > 0
	                	        ? formatSeconds(deficitSeconds)
	                	        : "00 Hr 00 Min";

	                 String actualColor =
	                         actualSeconds < expectedSeconds
	                         ? "red"
	                         : "green";

	                 // LOAD TEMPLATE
	                 String html =
	                         AttendanceMailTemplate
	                                 .loadEmployeeTemplate();

	                 html = html.replace(
	                         "{{month_name}}",
	                         monthName
	                 );

	                 html = html.replace(
	                         "{{employeecode}}",
	                         String.valueOf(
	                                 row.get("employeecode")
	                         )
	                 );

	                 html = html.replace(
	                         "{{employee}}",
	                         String.valueOf(
	                                 row.get("employee")
	                         )
	                 );

	                 html = html.replace(
	                         "{{expected_working_hours}}",
	                         formatHours(expectedHours)
	                 );

	                 html = html.replace(
	                         "{{actual_worked_hours}}",
	                         formatHours(actualHours)
	                 );

	                 html = html.replace(
	                	        "{{deficit_hours}}",
	                	        deficitHours
	                	);
	                 
	                 String deficitColor =
	                	        deficitSeconds > 0
	                	        ? "red"
	                	        : "green";
	                	        
	                 String actualHoursCell;

	                 if (actualSeconds < expectedSeconds) {

	                     actualHoursCell =

	                             "<td style='color:red;"
	                             + "font-weight:bold;'>"

	                             + formatHours(actualHours)

	                             + "</td>";

	                 } else {

	                     actualHoursCell =

	                             "<td style='color:green;"
	                             + "font-weight:bold;'>"

	                             + formatHours(actualHours)

	                             + "</td>";
	                 }

	                 html = html.replace(
	                         "##ACTUAL_HOURS_CELL##",
	                         actualHoursCell
	                 );
	                 
	                 String deficitHoursCell;

	                 if (deficitSeconds > 0) {

	                     deficitHoursCell =

	                             "<td style='padding:16px;"
	                             + "color:red;"
	                             + "font-weight:bold;"
	                             + "vertical-align:middle;'>"

	                             + deficitHours

	                             + "</td>";

	                 } else {

	                     deficitHoursCell =

	                             "<td style='padding:16px;"
	                             + "color:green;"
	                             + "font-weight:bold;"
	                             + "vertical-align:middle;'>"

	                             + deficitHours

	                             + "</td>";
	                 }

	                 html = html.replace(
	                         "##DEFICIT_HOURS_CELL##",
	                         deficitHoursCell
	                 );
	                 // SEND MAIL
	                 MimeMessage mimeMessage =
	                         mailSender.createMimeMessage();

	                 MimeMessageHelper helper =
	                         new MimeMessageHelper(
	                                 mimeMessage,
	                                 true,
	                                 "UTF-8"
	                         );

	                 helper.setTo(
	                         emp.getEmail()
	                 );

	                 helper.setSubject(
	                         "Previous Month Attendance Summary - "
	                         + monthName
	                 );

	                 helper.setText(
	                         html,
	                         true
	                 );
	                 
	                 if (companyVO.getCompanyLogo() != null) {

	                	    ByteArrayResource image =
	                	            new ByteArrayResource(
	                	                    companyVO.getCompanyLogo()
	                	            );

	                	    helper.addInline(
	                	            "companyLogo",
	                	            image,
	                	            "image/png"
	                	    );
	                	}

	                 mailSender.send(mimeMessage);

	                 System.out.println(
	                         "Mail Sent : "
	                         + emp.getEmployeeCode()
	                 );

	             } catch (Exception e) {

	                 e.printStackTrace();
	             }
	         }

	     } catch (Exception e) {

	         e.printStackTrace();
	     }
	 }
	 
	}
