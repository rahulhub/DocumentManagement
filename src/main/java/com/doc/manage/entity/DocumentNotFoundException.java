package com.doc.manage.entity;

/**
 * Class defines a runtime exception for the Upload Class
 */
public class DocumentNotFoundException extends RuntimeException{

    /**
     * Main Constructor for UploadNotFoundException
     *
     * @param filename
     */
    public DocumentNotFoundException(String filename){
        super(filename);
    }
}
