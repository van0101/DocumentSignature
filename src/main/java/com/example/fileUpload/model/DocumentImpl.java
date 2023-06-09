package com.example.fileUpload.model;

import com.example.fileUpload.payload.FileResponse;
import com.example.fileUpload.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentImpl
{
    private Logger logger = LoggerFactory.getLogger(DocumentImpl.class);

    @Autowired
    FileService fileService;


    @Autowired
    Document document;

    @Value("${project.doc}")
    private String path;

    @Autowired
    private DocumentDAO documentDAO;

    public String saveData(DocumentSignature documentSignature, MultipartFile file) throws IOException
    {

        // Setting Save path
        document.setFilePath(path);


        // Signature
        logger.info(" ## Attaching Signature " + documentSignature.getName());
        // Create DID
        attachSignatures(document, documentSignature);

        // Here, the document obj has the file uploaded
        // Can add other impl as needed

        // Upload File
        boolean isUploaded = this.fileService.uploadDocument(document, file);

        if(isUploaded) logger.info(" ## Document Uploaded");
        else logger.info(" ## Document could not be Uploaded");

        // Save File
        documentDAO.saveFinalDocument(document);

        // Response
        return document.getFileName();

    }
    void attachSignatures(Document document, DocumentSignature documentSignature)
    {
        // Random
        document.setDid("DID" + documentSignature.getId() + "ABCD123" );

        document.setDocumentSignature(documentSignature);
    }

    public Document getDoc(String id)
    {
        return documentDAO.get(id);
    }

    public FileResponse downloadDoc(String id) throws IOException
    {
        Document downloadDoc = documentDAO.get(id);

        return new FileResponse(id,
                "File Downloaded!",
                fileService.downloadDoc(downloadDoc),
                downloadDoc);
    }

}
