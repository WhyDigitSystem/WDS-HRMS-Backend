package com.efit.hrms.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetimage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetImageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetimagegen")
    @SequenceGenerator(name = "assetimagegen", sequenceName = "assetimageseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "assetimageid")
    private Long id;

//    @Column(name = "imagepath", nullable = true)
//    private String imagePath;  
    
    @Lob
    @Column(name = "imageattachment", columnDefinition = "LONGBLOB")
    private byte[] imageAttachment;

    @Column(name = "filename")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assetmasterid", nullable = false)
    @JsonBackReference
    private AssetMasterVO assetMaster;

}
