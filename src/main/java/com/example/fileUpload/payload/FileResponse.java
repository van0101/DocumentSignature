package com.example.fileUpload.payload;

import com.example.fileUpload.model.Document;
import org.springframework.core.io.ByteArrayResource;

public class FileResponse
{

    private String filename;
    private String message;

    private String fileId;

    private Document document;



    private ByteArrayResource downloadedFile;

    public FileResponse(String filename, String message)
    {
        this.filename = filename;
        this.message = message;
    }
    public FileResponse(String fileId, String message,
                        ByteArrayResource byteArrayResource, Document document)
    {
        this.fileId = fileId;
        this.message = message;

        downloadedFile = byteArrayResource;
        this.document = document;
    }

    public Document getDocument()
    {
        return document;
    }

    public ByteArrayResource getDownloadedFile()
    {
        return downloadedFile;
    }

    public String getFilename()
    {
        return filename;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String postDisplay()
    {
        return "Status : " + message + "\nFile Recevied : " + filename;
    }
}
