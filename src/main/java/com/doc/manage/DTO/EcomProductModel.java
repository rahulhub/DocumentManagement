package com.doc.manage.DTO;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


public class EcomProductModel {
	
	
	
	
	public EcomProductModel() {
		super();
	}

	public EcomProductModel(Date creationDate, String creator, String docUrl, String longDesc, String name,long size,
			EcomCatalogModel ecomCategory,String ext) {
		super();
		this.creationDate = creationDate;
		this.creator = creator;
		this.docUrl = docUrl;
		this.longDesc = longDesc;
		this.name = name;
		this.ecomCategory = ecomCategory;
		this.size=size;
		this.extension=ext;
	}

	private Long productId;

	private Date creationDate;

	private String creator;

	private String docUrl;

	private String longDesc;

	private Date modifiedDate;

	private String name;

	private int visible;
	
	private long size;
	
	private String extension;
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@JsonBackReference(value="product-catagory")
	private EcomCatalogModel ecomCategory;

	@JsonManagedReference(value="product-keyword")
	private List<EcomProductKeywordModel> ecomProductKeywords;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate==null?new Date():creationDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public EcomCatalogModel getEcomCategory() {
		return ecomCategory;
	}

	public void setEcomCategory(EcomCatalogModel ecomCategory) {
		this.ecomCategory = ecomCategory;
	}

	public List<EcomProductKeywordModel> getEcomProductKeywords() {
		return ecomProductKeywords;
	}

	public void setEcomProductKeywords(
			List<EcomProductKeywordModel> ecomProductKeywords) {
		this.ecomProductKeywords = ecomProductKeywords;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	


	
}
