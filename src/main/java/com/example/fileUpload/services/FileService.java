package com.example.fileUpload.services;

import com.example.fileUpload.model.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService
{
    boolean uploadImage(Document document, MultipartFile file) throws IOException;


}
