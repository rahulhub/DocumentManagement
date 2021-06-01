package com.doc.manage.DTO;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class EcomCatalogModel {
	
	
	
	public EcomCatalogModel() {
		super();
	}

	public EcomCatalogModel(Long categoryId) {
		super();
		this.categoryId = categoryId;
	}

	private Long categoryId;
	
	private Date creationDate;

	private String largeImgAltText;

	private String largeImgUrl;

	private String longDesc;

	private Date modifiedDate;

	private String name;

	private String shortDesc;

	private String smallImgAltText;

	private String smallImgUrl;


	@JsonBackReference(value="catagory")
	private EcomCatalogModel parentCategory;

	@JsonManagedReference(value="catagory")
	private List<EcomCatalogModel> children;

	@JsonManagedReference(value="product-catagory")
	private List<EcomProductModel> ecomProducts;
	
	private Integer isDeleted=0;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate==null?new Date():creationDate;
	}

	public String getLargeImgAltText() {
		return largeImgAltText;
	}

	public void setLargeImgAltText(String largeImgAltText) {
		this.largeImgAltText = largeImgAltText;
	}


	public String getLargeImgUrl() {
		return largeImgUrl;
	}

	public void setLargeImgUrl(String largeImgUrl) {
		this.largeImgUrl = largeImgUrl;
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


	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getSmallImgAltText() {
		return smallImgAltText;
	}

	public void setSmallImgAltText(String smallImgAltText) {
		this.smallImgAltText = smallImgAltText;
	}


	public String getSmallImgUrl() {
		return smallImgUrl;
	}

	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}


	public EcomCatalogModel getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(EcomCatalogModel parentCategory) {
		this.parentCategory = parentCategory;
	}


	public List<EcomCatalogModel> getChildren() {
		return children;
	}

	public void setChildren(List<EcomCatalogModel> children) {
		this.children = children;
	}

	public List<EcomProductModel> getEcomProducts() {
		return ecomProducts;
	}

	public void setEcomProducts(List<EcomProductModel> ecomProducts) {
		this.ecomProducts = ecomProducts;
	}



	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted==null?0:isDeleted;
	}

	
	

}
