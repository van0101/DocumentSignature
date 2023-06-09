package com.example.fileUpload.controllers;

import com.example.fileUpload.model.Document;
import com.example.fileUpload.model.DocumentSignature;
import com.example.fileUpload.model.DocumentImpl;
import com.example.fileUpload.payload.FileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController
{

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private Document document;

    @Autowired
    private DocumentImpl documentImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/upload")
    public ResponseEntity<?> fileUpload(
            @RequestParam("doc")MultipartFile Inputdoc,
            @RequestParam("request") String data)
    {

        logger.info("## Input document: " + Inputdoc.getName());
        logger.info("## Input user data JSON : \n" + data);

        String filename = null;

        // Convert from json
        DocumentSignature documentSignature = null; // Relevant details of signature
        try
        {
            documentSignature = objectMapper.readValue(data, DocumentSignature.class);
        } catch (JsonProcessingException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Invalid Request");
        }

        logger.info("## Obj converted " + documentSignature);

        // Save File and Signature
        try
        {
            filename = documentImpl.saveData(documentSignature, Inputdoc);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(new FileResponse
                    (filename, "File is not uploaded!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new FileResponse
                (filename, "File is uploaded!").postDisplay(),HttpStatus.OK);

    }
    @GetMapping("/document/{documentId}")
    public Document getDoc(@PathVariable String documentId)
    {
        logger.info("## Searching for Document " + documentId);

        Document requestDoc = this.documentImpl.getDoc(documentId);

        if(requestDoc == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Document Not Found.");
        }
        return requestDoc;
    }

    @GetMapping("/document/download")
    @ResponseBody
    public ResponseEntity<?> getDocument(@RequestParam(value = "id") String documentId)
    {
        logger.info("## Searching for Document for download " + documentId);

        // Get all file details and the document wrt to id
        FileResponse requestDoc;
        try
        {
            requestDoc = this.documentImpl.downloadDoc(documentId);
        } catch (IOException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Document Not Found.");
        }

        String filename = requestDoc
                .getDocument()
                .getFileName() + ".pdf";

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");


        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(requestDoc.getDownloadedFile());
    }

}
