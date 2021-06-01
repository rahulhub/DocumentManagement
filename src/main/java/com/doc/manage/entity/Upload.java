package com.doc.manage.entity;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class represents the Object Model of an uploaded file
 */
@Data
@Entity
public class Upload extends ResourceSupport{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    @Column
    private String filename;
    private String owner;
    private String extension;
    private String description;

    private Timestamp dateUploaded;
    private Timestamp lastAccessed;

    /**
     * Upload Constructor
     * For use to create instances of Upload to be saved in the database
     *
     * @param filename
     * @param owner
     * @param extension
     * @param description
     */
    public Upload(String filename, String owner, String extension, String description){
        this.filename = filename;
        this.extension = extension;
        this.description = description;
        this.owner = owner;

        this.dateUploaded = Timestamp.valueOf(LocalDateTime.now());
        this.lastAccessed = dateUploaded;
    }

    // this default constructor is created for the sake of JPA, will not be used directly
    protected Upload(){}

    public void updateLastAccessed(){
        this.lastAccessed = Timestamp.valueOf(LocalDateTime.now());
    }

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Timestamp dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public Timestamp getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Timestamp lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
}
