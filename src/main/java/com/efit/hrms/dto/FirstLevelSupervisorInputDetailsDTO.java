package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstLevelSupervisorInputDetailsDTO {

    private String goals;
    private String selfInput;
    private int score;
    private String supervisorRating;
}
