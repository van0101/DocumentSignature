package com.example.fileUpload.services.imple;

import com.example.fileUpload.model.Document;
import com.example.fileUpload.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; //?

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service //?
public class FileServicesImpl implements FileService
{

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Override
    public boolean uploadDocument(Document document, MultipartFile file) throws IOException
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

    @Override
    public ByteArrayResource downloadDoc(Document document) throws IOException
    {
        String filePath = document.getFilePath();
        File file = new File(filePath + document.getUUIDfileName());

        logger.info("## File path " + filePath + document.getUUIDfileName());
        logger.info("## File " + file.getName());
        logger.info("## File to path  " + file.toPath());

        ByteArrayResource byteFile = new ByteArrayResource(Files.readAllBytes(file.toPath()));


        logger.info("## ByteFile " );//+ Arrays.toString(byteFile));

        return byteFile;
    }
}
