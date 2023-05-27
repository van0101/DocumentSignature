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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

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

        logger.info("Input doc: " + Inputdoc);
        logger.info("Input user data JSON : " + data);

        String filename = null;

        // To json
        DocumentSignature documentSignature = null;
        try
        {
            documentSignature = objectMapper.readValue(data, DocumentSignature.class);
        } catch (JsonProcessingException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Invalid Request");
        }

        logger.info("Obj converted " + documentSignature);
        // TODO Verify Credentials
        boolean verified = true;


        // Save File and Signature
        if(verified)
        {
            try
            {
                filename = documentImpl.saveData(documentSignature, Inputdoc);
            }
            catch (IOException e)
            {
                return new ResponseEntity<>(new FileResponse
                        (filename, "File is not uploaded!"),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new FileResponse
                (filename, "File is uploaded!"),HttpStatus.OK);

    }

    // TODO Verify Credentials
    @GetMapping("/document/{documentId}")
    public Document getDoc(@PathVariable String documentId)
    {
        logger.info("Searching for Document " + documentId);

        Document requestDoc = this.documentImpl.getDoc(documentId);

        if(requestDoc == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Document Not Found.");
        }
        return requestDoc;
    }

}
