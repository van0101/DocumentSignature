package com.example.fileUpload.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentDAO
{
    List<Document> documentsStorage = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(DocumentDAO.class);
    void saveFinalDocument(Document document)
    {
        logger.info(" ## Saving File " + document.getDid());
        documentsStorage.add(document);
        logger.info(" ## Saving Done ");
    }
    Document get(String id)
    {
        logger.info("## Searching for file in storage ");
        for (Document document : documentsStorage)
        {
            if(document.getDid().equals(id))
            {
                logger.info("## Found doc" + document.getDid() + " " + document.getFileName());
                return document;
            }
        }
        return null;
    }
}
