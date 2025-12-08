package com.efit.hrms.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

public class GoogleDriveUtil {

    private static final String APPLICATION_NAME = "HRMS Drive Integration";
    private static final String SERVICE_ACCOUNT_KEY_PATH = "C:/hrms-drive-key.json/promising-lamp-477106-p1-70a20aa82f6f.json";
    private static final String FOLDER_ID = "1VsjY4aziolowSOC9Iioy19BWMEdjTRXm";

    private static Drive driveService;

    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        if (driveService == null) {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));

            driveService = new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),                 
                    new HttpCredentialsAdapter(credentials)
            ).setApplicationName(APPLICATION_NAME).build();
        }
        return driveService;
    }

    public static String uploadFileToDrive(java.io.File file, String fileName)
            throws IOException, GeneralSecurityException {

        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        FileContent mediaContent = new FileContent("application/octet-stream", file);

        File uploadedFile = getDriveService().files()
                .create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .setSupportsAllDrives(true) // ✅ important
                .execute();

        // Make file public (optional)
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        getDriveService().permissions()
                .create(uploadedFile.getId(), permission)
                .setSupportsAllDrives(true) // ✅ important
                .execute();

        return "https://drive.google.com/uc?id=" + uploadedFile.getId();
    }
}
