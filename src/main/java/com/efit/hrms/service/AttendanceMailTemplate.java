package com.efit.hrms.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class AttendanceMailTemplate {

    // ALL EMPLOYEE SUMMARY TEMPLATE
    public static String loadMonthlySummaryTemplate()
            throws IOException {

        ClassPathResource resource =
                new ClassPathResource(
                        "templates/monthly-attendance-mail.html"
                );

        return StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    // INDIVIDUAL EMPLOYEE TEMPLATE
    public static String loadEmployeeTemplate()
            throws IOException {

        ClassPathResource resource =
                new ClassPathResource(
                        "templates/employee-attendance-summary.html"
                );

        return StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}