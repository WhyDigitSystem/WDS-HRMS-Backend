package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.dto.PendingRequestProjection;
import com.efit.hrms.entity.LeaveRequestVO;

@Repository
public interface PendingRequestRepository extends JpaRepository<LeaveRequestVO, Long> {

    @Query(value =
        "SELECT * FROM ( " +

        " SELECT 'LEAVE_REQUEST' AS type, " +
        "        lr.fromdate AS fromdate, " +
        "        lr.todate AS todate, " +
        "        NULL AS time, " +
        "        NULL AS status, " +
        "        lr.approvestatus AS approvalstatus " +
        " FROM leaverequest lr " +
        " WHERE lr.employeecode = ?1 AND lr.approvestatus = 'PENDING' " +
        " ORDER BY lr.leaverequestid DESC LIMIT 10 " +

        ") A " +

        "UNION ALL " +

        "SELECT * FROM ( " +

        " SELECT 'PERMISSION_REQUEST' AS type, " +
        "        pr.date AS fromdate, " +
        "        pr.date AS todate, " +
        "        NULL AS time, " +
        "        NULL AS status, " +
        "        pr.approvestatus AS approvalstatus " +
        " FROM permissionrequest pr " +
        " WHERE pr.employeecode = ?1 AND pr.approvestatus = 'PENDING' " +
        " ORDER BY pr.permissionrequestid DESC LIMIT 10 " +

        ") B " +

        "UNION ALL " +

        "SELECT * FROM ( " +

        " SELECT 'COMPENSATORY_OFF' AS type, " +
        "        co.compoffdate AS fromdate, " +
        "        co.compoffdate AS todate, " +
        "        NULL AS time, " +
        "        NULL AS status, " +
        "        co.approvalstatus AS approvalstatus " +
        " FROM compensatoryoff co " +
        " WHERE co.employeecode = ?1 AND co.approvalstatus = 'PENDING' " +
        " ORDER BY co.compensatoryoffid DESC LIMIT 10 " +

        ") C " +

        "UNION ALL " +

        "SELECT * FROM ( " +

        " SELECT 'CHECKINOUT_ADJUSTMENT' AS type, " +
        "        NULL AS fromdate, " +
        "        NULL AS todate, " +
        "        ca.entrytime AS time, " +
        "        ca.status AS status, " +
        "        ca.approvalstatus AS approvalstatus " +
        " FROM checkinoutadjustment ca " +
        " WHERE ca.empcode = ?1 AND ca.approvalstatus = 'PENDING' " +
        " ORDER BY ca.checkinoutadjustmentid DESC LIMIT 10 " +

        ") D",
        nativeQuery = true)
    List<PendingRequestProjection> getPendingRequests(String employeeCode);

}

