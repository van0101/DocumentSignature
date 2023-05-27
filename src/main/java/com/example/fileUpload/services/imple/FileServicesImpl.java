package com.example.fileUpload.services.imple;

import com.example.fileUpload.model.Document;
import com.example.fileUpload.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; //?

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service //?
public class FileServicesImpl implements FileService
{

    @Override
    public boolean uploadImage(Document document, MultipartFile file) throws IOException
    {

        String path = document.getFilePath();
        // File name
        String name = file.getOriginalFilename(); // file coming name

        // Random name generator
        String randomID = UUID.randomUUID().toString();
        String newFilename = randomID.concat(name.substring(name.lastIndexOf(".")));


        // Full path (till file)
        String filePath = path + File.separator + newFilename;

        // Create folder if not created
        File f = new File(path);

        if(!f.exists())
        {
            f.mkdir();
        }
        // File Copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        document.setUUIDfileName(newFilename);
        document.setFileName(name);

        // Msg if upload is successfull
        return !(name == null);
    }
}
