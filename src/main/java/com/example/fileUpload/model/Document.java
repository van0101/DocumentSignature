package com.example.fileUpload.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Document
{
    private String did;
    private DocumentSignature documentSignature;

    private String fileName;
    private String filePath;
    private String UUIDfileName;

    public String getUUIDfileName()
    {
        return UUIDfileName;
    }

    public void setUUIDfileName(String UUIDfileName)
    {
        this.UUIDfileName = UUIDfileName;
    }

    public String getFileName()
    {
        return fileName;
    }

    public DocumentSignature getDocumentSignature()
    {
        return documentSignature;
    }

    public void setDocumentSignature(DocumentSignature documentSignature)
    {
        this.documentSignature = documentSignature;
    }

    public String getDid()
    {
        return did;
    }

    public void setDid(String did)
    {
        this.did = did;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
}
