package com.efit.hrms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.TimeSheetDTO;
import com.efit.hrms.dto.TimeSheetDetailsDTO;
import com.efit.hrms.entity.ApprovalLeavesVO;
import com.efit.hrms.entity.TimeSheetDetailsVO;
import com.efit.hrms.entity.TimeSheetVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.ApprovalLeavesRepo;
import com.efit.hrms.repo.EmployeeRepo;
import com.efit.hrms.repo.HolidayRepo;
import com.efit.hrms.repo.TimeSheetDetailsRepo;
import com.efit.hrms.repo.TimeSheetRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@Component
public class TimeSheetServiceImpl implements TimeSheetService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetServiceImpl.class);

	@Autowired
	TimeSheetRepo timeSheetRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	TimeSheetDetailsRepo timeSheetDetailsRepo;

//	@Autowired
//	AttandanceTimeRepo attandanceTimeRepo;

	@Autowired
	ApprovalLeavesRepo approvalLeavesRepo;

	@Autowired
	HolidayRepo holidayRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;

	// Timesheet

//		@Override
//		public Map<String, Object> createUpdateTimeSheet(TimeSheetDTO timeSheetDTO) throws ApplicationException {
//
//		    TimeSheetVO timeSheetVO;
//		    String message;
//		    String screenCode = "TS";
//
//		    if (ObjectUtils.isNotEmpty(timeSheetDTO.getId())) {
//		        timeSheetVO = timeSheetRepo.findById(timeSheetDTO.getId())
//		                .orElseThrow(() -> new ApplicationException("Invalid TimeSheet details"));
//		        timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());
//		        
//		        if (timeSheetDTO.getId() != null) {
//		            List<TimeSheetDetailsVO> timeSheetDetailsVO = timeSheetDetailsRepo.findByTimeSheetVO(timeSheetVO);
//		            timeSheetDetailsRepo.deleteAll(timeSheetDetailsVO);
//		        }
//	            
//		        message = "TimeSheet Updated Successfully";
//		    } else {
//		        timeSheetVO = new TimeSheetVO();
//		        timeSheetVO.setCreatedBy(timeSheetDTO.getCreatedBy());
//		        timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());
//		        message = "TimeSheet Created Successfully";
//		    }
//
//		    // Set basic fields
//		    timeSheetVO.setOrgId(timeSheetDTO.getOrgId());
//		    timeSheetVO.setEmployeeName(timeSheetDTO.getEmployeeName());
//		    timeSheetVO.setEmployeeCode(timeSheetDTO.getEmployeeCode());
//		    timeSheetVO.setDate(timeSheetDTO.getDate());
//		    timeSheetVO.setBranch(timeSheetDTO.getBranch());
//		    timeSheetVO.setBranchCode(timeSheetDTO.getBranchCode());
//		    timeSheetVO.setActive(timeSheetDTO.isActive());
//		    
//		    String totalWorkingStr = timeSheetDetailsRepo.findTotalWorkingHoursByEmpcodeAndDate(
//			        timeSheetDTO.getEmployeeCode(), timeSheetDTO.getDate());
//
//			    if (totalWorkingStr == null || totalWorkingStr.equals("00:00:00")) {
//			        throw new ApplicationException("Employee not found in attendance records for the given date");
//			    }
//			    
//			    double allowedHours = convertTimeToDecimalHours(totalWorkingStr);
//			    System.out.println(allowedHours);
//
//		    List<ApprovalLeavesVO> approvalLeavesVO = approvalLeavesRepo
//		    	    .findByLeaveDateAndOrgIdAndEmployeeCodeAndBranchCode(
//		    	        timeSheetDTO.getDate(),
//		    	        timeSheetDTO.getOrgId(),
//		    	        timeSheetDTO.getEmployeeCode(),
//		    	        timeSheetDTO.getBranchCode());
//
//		    	if (approvalLeavesVO != null && !approvalLeavesVO.isEmpty()) {
//		    	    throw new ApplicationException("Timesheet cannot be saved. Leave already approved for the selected date.");
//		    	}
//		    	
//		    // Handle child list
//		    List<TimeSheetDetailsVO> detailsList = new ArrayList<>();
//		    double totalHours = 0.0;
//
//		    for (TimeSheetDetailsDTO dto : timeSheetDTO.getTimeSheetDetailsDTO()) {
//		        TimeSheetDetailsVO detail = new TimeSheetDetailsVO();
//		        detail.setProjectName(dto.getProjectName());
//		        detail.setDescription(dto.getDescription());
//		        detail.setFromTime(dto.getFromTime());
//		        detail.setToTime(dto.getToTime());
//		        detail.setProject(dto.getProject());
//		        detail.setWip(dto.getWip());
//		        detail.setStatus(dto.getStatus());
//		        detail.setRemarks(dto.getRemarks());
//
//		        double hours = calculateHours(dto.getFromTime(), dto.getToTime());
//		        totalHours += hours;
//
//		        detail.setTimeSheetVO(timeSheetVO);
//		        detailsList.add(detail);
//		    }
//
//		    // Fetch total working hours from AttendanceTime table
//		   System.out.println(totalHours);
//		    // Validate total hours
//		    if (totalHours > allowedHours) {
//		        throw new ApplicationException("Total hours in timesheet (" + round(totalHours) + 
//		            ") exceeds allowed working hours (" + round(allowedHours) + ") from Attendance.");
//		    }
//
//		    timeSheetVO.setTotalhours(round(totalHours));
//		    timeSheetVO.setTimeSheetDetailsVO(detailsList);
//
//		    // Save parent with child
//		    timeSheetVO = timeSheetRepo.save(timeSheetVO);
//
//		    Map<String, Object> response = new HashMap<>();
//		    response.put("timeSheetVO", timeSheetVO);
//		    response.put("message", message);
//		    return response;
//		}
//
//
//		// Helper to calculate hours between two times
////		private double calculateHours(String fromTime, String toTime) {
////		    try {
////		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
////		        LocalTime start = LocalTime.parse(fromTime, formatter);
////		        LocalTime end = LocalTime.parse(toTime, formatter);
////		        Duration duration = Duration.between(start, end);
////		        return duration.toMinutes() / 60.0;
////		    } catch (Exception e) {
////		        return 0.0;
////		    }
////		}
//		private double calculateHours(String fromTime, String toTime) {
//		    try {
//		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//		        LocalTime start = LocalTime.parse(fromTime, formatter);
//		        LocalTime end = LocalTime.parse(toTime, formatter);
//
//		        // Calculate the duration in minutes
//		        Duration duration = Duration.between(start, end);
//
//		        // Return the duration in hours (as a decimal)
//		        return duration.toMinutes() / 60.0;
//		    } catch (Exception e) {
//		        // Handle exceptions and return 0.0 if any issue occurs
//		        e.printStackTrace();
//		        return 0.0;
//		    }
//		}
//
//
//		// Convert "HH:mm:ss" to decimal hours
//		public double convertTimeToDecimalHours(String time) {
//		    if (time == null || time.trim().isEmpty()) return 0;
//
//		    String[] parts = time.split(":");
//		    int hrs = Integer.parseInt(parts[0]);
//		    int mins = Integer.parseInt(parts[1]);
//		    int secs = Integer.parseInt(parts[2]);
//
//		    return hrs + (mins / 60.0) + (secs / 3600.0);
//		}
//
//		// Round to 2 decimal places
//		private double round(double value) {
//		    return Math.round(value * 100.0) / 100.0;
//		}

	@Override
	public Map<String, Object> createUpdateTimeSheet(TimeSheetDTO timeSheetDTO) throws ApplicationException {

		TimeSheetVO timeSheetVO;
		String message;
		int totalMinutes = 0; 


		if (ObjectUtils.isNotEmpty(timeSheetDTO.getId())) {
			// Update existing
			timeSheetVO = timeSheetRepo.findById(timeSheetDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid TimeSheet details"));
			timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());

			// Clear old details
			List<TimeSheetDetailsVO> oldDetails = timeSheetDetailsRepo.findByTimeSheetVO(timeSheetVO);
			timeSheetDetailsRepo.deleteAll(oldDetails);

			message = "TimeSheet Updated Successfully";
		} else {
			// Create new
			timeSheetVO = new TimeSheetVO();
			timeSheetVO.setCreatedBy(timeSheetDTO.getCreatedBy());
			timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());
			message = "TimeSheet Created Successfully";
		}

		// Header fields
		timeSheetVO.setOrgId(timeSheetDTO.getOrgId());
		timeSheetVO.setEmployeeName(timeSheetDTO.getEmployeeName());
		timeSheetVO.setEmployeeCode(timeSheetDTO.getEmployeeCode());
		timeSheetVO.setDate(timeSheetDTO.getDate());
		timeSheetVO.setBranch(timeSheetDTO.getBranch());
		timeSheetVO.setBranchCode(timeSheetDTO.getBranchCode());
		timeSheetVO.setActive(timeSheetDTO.isActive());

		// Allowed hours from attendance
		String totalWorkingStr = timeSheetDetailsRepo
				.findTotalWorkingHoursByEmpcodeAndDate(timeSheetDTO.getEmployeeCode(), timeSheetDTO.getDate());

		if (totalWorkingStr == null || totalWorkingStr.equals("00:00:00")) {
			throw new ApplicationException("Employee not found in attendance records for the given date");
		}

		double allowedHours = convertTimeToDecimalHours(totalWorkingStr);

		// Leave validation
		List<ApprovalLeavesVO> approvalLeavesVO = approvalLeavesRepo
				.findByLeaveDateAndOrgIdAndEmployeeCodeAndBranchCode(timeSheetDTO.getDate(), timeSheetDTO.getOrgId(),
						timeSheetDTO.getEmployeeCode(), timeSheetDTO.getBranchCode());

		if (approvalLeavesVO != null && !approvalLeavesVO.isEmpty()) {
			throw new ApplicationException("Timesheet cannot be saved. Leave already approved for the selected date.");
		}

		// Handle child list and sum total hours
		List<TimeSheetDetailsVO> detailsList = new ArrayList<>();
		double totalHours = 0.0;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		List<LocalTime[]> slots = new ArrayList<>();

		for (TimeSheetDetailsDTO dto : timeSheetDTO.getTimeSheetDetailsDTO()) {
			LocalTime from = LocalTime.parse(dto.getFromTime(), formatter);
			LocalTime to = LocalTime.parse(dto.getToTime(), formatter);

			// ✅ Validation 1: fromTime < toTime
			if (!from.isBefore(to)) {
				throw new ApplicationException("Invalid time range: " + dto.getFromTime() + " - " + dto.getToTime());
			}

			// ✅ Validation 2 & 3: Check overlaps with existing slots
			for (LocalTime[] slot : slots) {
				LocalTime existingFrom = slot[0];
				LocalTime existingTo = slot[1];

				boolean overlaps = (from.isBefore(existingTo) && to.isAfter(existingFrom));
				if (overlaps) {
					throw new ApplicationException("Time overlap found between " + dto.getFromTime() + "-"
							+ dto.getToTime() + " and " + existingFrom + "-" + existingTo);
				}
			}
			slots.add(new LocalTime[] { from, to });

			// Create detail record
			TimeSheetDetailsVO detail = new TimeSheetDetailsVO();
			detail.setProjectName(dto.getProjectName());
			detail.setDescription(dto.getDescription());
			detail.setFromTime(dto.getFromTime());
			detail.setToTime(dto.getToTime());
			detail.setProject(dto.getProject());
			detail.setWip(dto.getWip());
			detail.setStatus(dto.getStatus());
			detail.setRemarks(dto.getRemarks());

			// Calculate hours for each child
			 String worked = calculateHoursTask(dto.getFromTime(), dto.getToTime()); // e.g., "1:20"
			    System.out.println("Worked: " + worked);

			    // Convert to minutes and add to total
			    String[] parts = worked.split(":");
			    int hrs = Integer.parseInt(parts[0]);
			    int mins = Integer.parseInt(parts[1]);
			    totalMinutes += hrs * 60 + mins;

			detail.setTimeSheetVO(timeSheetVO);
			detailsList.add(detail);
		}

		// Validate against allowed hours
		if (totalHours > allowedHours) {
			throw new ApplicationException("Total hours in timesheet (" + round(totalHours)
					+ ") exceeds allowed working hours (" + round(allowedHours) + ") from Attendance.");
		}

		long hours = totalMinutes / 60;
		long minutes = totalMinutes % 60;

		// Convert to H.MM as BigDecimal
		BigDecimal totalWorked = new BigDecimal(hours)
		        .add(new BigDecimal(minutes).divide(new BigDecimal(100)));

		// Round to 2 decimal places
		totalWorked = totalWorked.setScale(2, RoundingMode.DOWN);

		// ✅ Directly set BigDecimal
		timeSheetVO.setTotalhours(totalWorked);

		System.out.println("Total Worked: " + totalWorked);

		// Attach children
		timeSheetVO.setTimeSheetDetailsVO(detailsList);

		// Save parent + children
		timeSheetVO = timeSheetRepo.save(timeSheetVO);

		Map<String, Object> response = new HashMap<>();
		response.put("timeSheetVO", timeSheetVO);
		response.put("message", message);
		return response;
	}

	public double convertTimeToDecimalHours(String time) {
		if (time == null || time.trim().isEmpty())
			return 0;

		String[] parts = time.split(":");
		int hrs = Integer.parseInt(parts[0]);
		int mins = Integer.parseInt(parts[1]);
		int secs = Integer.parseInt(parts[2]);

		return hrs + (mins / 60.0) + (secs / 3600.0);
	}

	// Round to 2 decimal places
	private double round(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private double calculateHours(String fromTime, String toTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime start = LocalTime.parse(fromTime, formatter);
		LocalTime end = LocalTime.parse(toTime, formatter);

		long minutes = Duration.between(start, end).toMinutes();
		long hours = minutes / 60;
		long remainingMinutes = minutes % 60;

		// convert to H.MM as double for display, accurate sum
		return hours + (remainingMinutes / 60.0);
	}

	@Override
	public List<TimeSheetVO> getTimeSheetByOrgId(Long orgId, String empCode, String date) {
		// TODO Auto-generated method stub
		return timeSheetRepo.getTimeSheetByOrgId(orgId, empCode, date);
	}

	@Override
	public TimeSheetVO getTimeSheetById(Long id) {
		return timeSheetRepo.getTimeSheetById(id);

	}

	@Override
	public List<Map<String, Object>> getApprovedLeaveForTimeSheet(Long orgId, LocalDate date, String employeeCode) {
		Set<Object[]> result = approvalLeavesRepo.getApprovedLeaveForTimeSheet(orgId, date, employeeCode);
		return getApprovedLeaveForTimeSheet(result);
	}

	private List<Map<String, Object>> getApprovedLeaveForTimeSheet(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();

		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("checkIn", record[0] != null ? record[0].toString() : "");
			map.put("checkOut", record[1] != null ? record[1].toString() : "");
			map.put("totalHours", record[2] != null ? record[2].toString() : " ");
			map.put("employeeStatus", record[3] != null ? record[3].toString() : " ");

			detailsList.add(map);
		}
		return detailsList;
	}

	@Override
	public List<TimeSheetVO> getTimeSheetDescByOrgId(Long orgId, String empCode, String branchCode, String fromDate,
			String toDate) {
		// TODO Auto-generated method stub
		return timeSheetRepo.getTimeSheetDescByOrgId(orgId, empCode, branchCode, fromDate, toDate);
	}

	public List<Map<String, Object>> getAllTimeSheetDescByOrgId(String month, String year, Long orgId, String branch, String department, String employeecode) {
		List<Object[]> results = timeSheetRepo.getAllTimeSheetDescByOrgId( month,  year,  orgId,  branch,  department,  employeecode);

		List<Map<String, Object>> response = new ArrayList<>();
		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("empcodename", row[0]);
			map.put("employeename",  row[1]);
			map.put("employeecode",  row[2]);


			try {
				// Parse JSON string into List of Maps
				String timesheetJson = (String) row[3];
				List<Map<String, Object>> timesheetList = objectMapper.readValue(timesheetJson,
						new TypeReference<List<Map<String, Object>>>() {
						});
				map.put("timesheets", timesheetList);
			} catch (Exception e) {
				map.put("timesheets", new ArrayList<>()); // fallback empty
			}

			response.add(map);
		}
		return response;
	}
	
	
	@Override
	public List<Map<String, Object>> getEmployeeDetailsForAllTaskReport(Long orgId, String branchCode, String department) {
		Set<Object[]> result = employeeRepo.getEmployeeDetailsForAllTaskReport( orgId,  branchCode,  department);
		return getEmployeeDetailsForAllTaskReport(result);
	}

	private List<Map<String, Object>> getEmployeeDetailsForAllTaskReport(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();

		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("employee", record[0] != null ? record[0].toString() : " ");
			map.put("employeeCode", record[1] != null ? record[1].toString() : " ");

			detailsList.add(map);
		}
		return detailsList;
	}

	@Override
	public List<Map<String, Object>> getApprovedLeaveForTimeSheetReport(Long orgId, LocalDate fromDate,
			LocalDate toDate, String branchCode, String employeeCode) {
		Set<Object[]> result = approvalLeavesRepo.getApprovedLeaveForTimeSheetReport(orgId, fromDate, toDate,
				branchCode, employeeCode);
		return getApprovedLeaveForTimeSheetReport(result);
	}

	private List<Map<String, Object>> getApprovedLeaveForTimeSheetReport(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();

		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("leaveDate", record[0] != null ? record[0].toString() : " ");
			map.put("leaveType", record[1] != null ? record[1].toString() : " ");

			detailsList.add(map);
		}
		return detailsList;
	}

	@Override
	public List<Map<String, Object>> getHolidaysForTimeSheetReport(Long orgId, LocalDate fromDate, LocalDate toDate,
			String branchCode) {
		Set<Object[]> result = holidayRepo.getHolidaysForTimeSheetReport(orgId, fromDate, toDate, branchCode);
		return HolidaysForTimeSheetReport(result);
	}

	private List<Map<String, Object>> HolidaysForTimeSheetReport(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();

		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("leaveDate", record[0] != null ? record[0].toString() : " ");
			map.put("leaveType", record[1] != null ? record[1].toString() : " ");

			detailsList.add(map);
		}
		return detailsList;
	}
	
	
	@Override
	public Map<String, Object> createUpdateTask(TimeSheetDTO timeSheetDTO) throws ApplicationException {

		TimeSheetVO timeSheetVO;
		String message;
		int totalMinutes = 0; 


		if (ObjectUtils.isNotEmpty(timeSheetDTO.getId())) {
			// Update existing
			timeSheetVO = timeSheetRepo.findById(timeSheetDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid TimeSheet details"));
			timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());

			// Clear old details
			List<TimeSheetDetailsVO> oldDetails = timeSheetDetailsRepo.findByTimeSheetVO(timeSheetVO);
			timeSheetDetailsRepo.deleteAll(oldDetails);

			message = "TimeSheet Updated Successfully";
		} else {
			// Create new
			timeSheetVO = new TimeSheetVO();
			timeSheetVO.setCreatedBy(timeSheetDTO.getCreatedBy());
			timeSheetVO.setUpdatedBy(timeSheetDTO.getCreatedBy());
			message = "TimeSheet Created Successfully";
		}

		// Header fields
		timeSheetVO.setOrgId(timeSheetDTO.getOrgId());
		timeSheetVO.setEmployeeName(timeSheetDTO.getEmployeeName());
		timeSheetVO.setEmployeeCode(timeSheetDTO.getEmployeeCode());
		timeSheetVO.setDate(timeSheetDTO.getDate());
		timeSheetVO.setBranch(timeSheetDTO.getBranch());
		timeSheetVO.setBranchCode(timeSheetDTO.getBranchCode());
		timeSheetVO.setActive(timeSheetDTO.isActive());

		// Allowed hours from attendance
//		String totalWorkingStr = timeSheetDetailsRepo
//				.findTotalWorkingHoursByEmpcodeAndDate(timeSheetDTO.getEmployeeCode(), timeSheetDTO.getDate());

//		if (totalWorkingStr == null || totalWorkingStr.equals("00:00:00")) {
//			throw new ApplicationException("Employee not found in attendance records for the given date");
//		}

//		double allowedHours = convertTimeToDecimalHours(totalWorkingStr);

		// Leave validation
//		List<ApprovalLeavesVO> approvalLeavesVO = approvalLeavesRepo
//				.findByLeaveDateAndOrgIdAndEmployeeCodeAndBranchCode(timeSheetDTO.getDate(), timeSheetDTO.getOrgId(),
//						timeSheetDTO.getEmployeeCode(), timeSheetDTO.getBranchCode());
//		

//		if (approvalLeavesVO != null && !approvalLeavesVO.isEmpty()) {
//			throw new ApplicationException("Timesheet cannot be saved. Leave already approved for the selected date.");
//		}
		
		List<ApprovalLeavesVO> approvalLeavesVO = approvalLeavesRepo
		        .findByLeaveDateAndOrgIdAndEmployeeCodeAndBranchCode(
		                timeSheetDTO.getDate(),
		                timeSheetDTO.getOrgId(),
		                timeSheetDTO.getEmployeeCode(),
		                timeSheetDTO.getBranchCode()
		        );

		if (approvalLeavesVO != null && !approvalLeavesVO.isEmpty()) {
		    boolean hasFullDayLeave = approvalLeavesVO.stream()
		            .anyMatch(l -> l.getTotalLeave() != null && l.getTotalLeave().compareTo(BigDecimal.ONE) >= 0);

		    if (hasFullDayLeave) {
		        throw new ApplicationException("Timesheet cannot be saved. Full-day leave already approved for the selected date.");
		    }
		}
	


		// Handle child list and sum total hours
		List<TimeSheetDetailsVO> detailsList = new ArrayList<>();
		double totalHours = 0.0;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		List<LocalTime[]> slots = new ArrayList<>();

		for (TimeSheetDetailsDTO dto : timeSheetDTO.getTimeSheetDetailsDTO()) {
			LocalTime from = LocalTime.parse(dto.getFromTime(), formatter);
			LocalTime to = LocalTime.parse(dto.getToTime(), formatter);

			// ✅ Validation 1: fromTime < toTime
			if (!from.isBefore(to)) {
				throw new ApplicationException("Invalid time range: " + dto.getFromTime() + " - " + dto.getToTime());
			}

			// ✅ Validation 2 & 3: Check overlaps with existing slots
			for (LocalTime[] slot : slots) {
				LocalTime existingFrom = slot[0];
				LocalTime existingTo = slot[1];

				boolean overlaps = (from.isBefore(existingTo) && to.isAfter(existingFrom));
				if (overlaps) {
					throw new ApplicationException("Time overlap found between " + dto.getFromTime() + "-"
							+ dto.getToTime() + " and " + existingFrom + "-" + existingTo);
				}
			}
			slots.add(new LocalTime[] { from, to });

			// Create detail record
			TimeSheetDetailsVO detail = new TimeSheetDetailsVO();
			detail.setProjectName(dto.getProjectName());
			detail.setDescription(dto.getDescription());
			detail.setFromTime(dto.getFromTime());
			detail.setToTime(dto.getToTime());
			detail.setProject(dto.getProject());
			detail.setWip(dto.getWip());
			detail.setStatus(dto.getStatus());
			detail.setRemarks(dto.getRemarks());

			// Calculate hours for each child
			 String worked = calculateHoursTask(dto.getFromTime(), dto.getToTime()); // e.g., "1:20"
			    System.out.println("Worked: " + worked);

			    // Convert to minutes and add to total
			    String[] parts = worked.split(":");
			    int hrs = Integer.parseInt(parts[0]);
			    int mins = Integer.parseInt(parts[1]);
			    totalMinutes += hrs * 60 + mins;



			detail.setTimeSheetVO(timeSheetVO);
			detailsList.add(detail);
		}

		
		long hours = totalMinutes / 60;
		long minutes = totalMinutes % 60;

		// Convert to H.MM as BigDecimal
		BigDecimal totalWorked = new BigDecimal(hours)
		        .add(new BigDecimal(minutes).divide(new BigDecimal(100)));

		// Round to 2 decimal places
		totalWorked = totalWorked.setScale(2, RoundingMode.DOWN);

		// ✅ Directly set BigDecimal
		timeSheetVO.setTotalhours(totalWorked);

		System.out.println("Total Worked: " + totalWorked); // prints 2.20

		
//		// Validate against allowed hours
//		if (totalHours > allowedHours) {
//			throw new ApplicationException("Total hours in timesheet (" + round(totalHours)
//					+ ") exceeds allowed working hours (" + round(allowedHours) + ") from Attendance.");
//		}

		// Save total hours in header
//		System.out.println("Test2: " + totalHours);


		
		// Attach children
		timeSheetVO.setTimeSheetDetailsVO(detailsList);

		// Save parent + children
		timeSheetVO = timeSheetRepo.save(timeSheetVO);

		Map<String, Object> response = new HashMap<>();
		response.put("timeSheetVO", timeSheetVO);
		response.put("message", message);
		return response;
	}	

	
	private String calculateHoursTask(String fromTime, String toTime) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	    LocalTime start = LocalTime.parse(fromTime, formatter);
	    LocalTime end = LocalTime.parse(toTime, formatter);

	    if (end.isBefore(start)) {
	        end = end.plusHours(24); // handle overnight shifts
	    }

	    long totalMinutes = Duration.between(start, end).toMinutes();
	    long hours = totalMinutes / 60;
	    long minutes = totalMinutes % 60;

	    return String.format("%d:%02d", hours, minutes); // 1:20
	}
	
	// Sum multiple slots in H:MM format
	private String sumTimeSlots(List<String> slots) {
	    int totalMinutes = 0;
	    for (String slot : slots) {
	        String[] parts = slot.split(":");
	        int hrs = Integer.parseInt(parts[0]);
	        int mins = Integer.parseInt(parts[1]);
	        totalMinutes += hrs * 60 + mins;
	    }
	    int hours = totalMinutes / 60;
	    int minutes = totalMinutes % 60;
	    return String.format("%d:%02d", hours, minutes);
	}



	// Convert decimal hours to H:MM format for display
	private String formatHoursHMM(double decimalHours) {
	    int hours = (int) decimalHours;
	    int minutes = (int) Math.round((decimalHours - hours) * 60);
	    return String.format("%d:%02d", hours, minutes); // e.g., 1.1666 -> "1:10"
	}


	


}
