package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetImageDTO {
    private String imagePath;
//    private byte[] imageData;
    private String fileName;
}
