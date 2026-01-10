package com.efit.hrms.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "essldevicelog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsslDeviceLogVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "devicelogid")
    private Long deviceLogId;

    @Column(name = "deviceid")
    private Integer deviceId;

    @Column(name = "employeecode", length = 50)
    private String employeeCode;

    @Column(name = "logdate")
    private LocalDateTime logDate;

    @Column(name = "verificationtype", length = 2)
    private String verificationType;

    @Column(name = "gps", length = 100)
    private String gps;

    @Column(name = "direction", length = 50)
    private String direction;

    @Column(name = "attdirectioncode", length = 50)
    private String attDirectionCode;

    @Column(name = "createdon", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "checkinoutstatus", length = 10)
    private String checkInOutStatus;
}

