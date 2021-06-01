package com.doc.manage.entity;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * The persistent class for the ecom_product database table.
 * 
 */
@Entity
//@Cacheable(true)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Table(name="ecom_product")
//@NamedQuery(name="productsByCategory", query="SELECT e FROM EcomProduct e WHERE ecomCategory.categoryId=:catId")
//@NamedQueries({
@NamedQuery(name="EcomProduct.findLikeName", query="SELECT e FROM EcomProduct e WHERE e.name LIKE '%:name%'")
//@NamedQuery(name="EcomProduct.findProductById", query="Select p from EcomProduct p where p.productId= :productId", hints = { @QueryHint(name = UruAppConstants.ORG_HIBERNATE_CACHEABLE, value = "true")}),})
public class EcomProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	

	public EcomProduct(String name, String creator, String type, Date creationDate,
			String docUrl,long size, EcomCategory ecomCategory) {
		super();
		this.name = name;
		this.creator = creator;
		this.type = type;
		this.creationDate = creationDate;
		this.docUrl = docUrl;
		this.size = size;
		this.ecomCategory = ecomCategory;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CREATOR")
	private String creator;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="LONG_DESC")
	private String longDesc;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date creationDate;

	@Column(name="DOC_URL")
	private String docUrl;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="VISIBLE")
	private int visible;
	
	@Column(name="SIZE")
	private long size;
	
	@Column(name="EXT")
	private String extension;

	//bi-directional many-to-one association to EcomCategory
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID")
	private EcomCategory ecomCategory;

	//bi-directional many-to-one association to EcomProductKeyword
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(mappedBy="ecomProduct",cascade = CascadeType.ALL)
	private List<EcomProductKeyword> ecomProductKeywords;
	

	public EcomProduct() {
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate==null?new Date():creationDate;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}



	public String getLongDesc() {
		return this.longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVisible() {
		return this.visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}


	public EcomCategory getEcomCategory() {
		return this.ecomCategory;
	}

	public void setEcomCategory(EcomCategory ecomCategory) {
		this.ecomCategory = ecomCategory;
	}

	public List<EcomProductKeyword> getEcomProductKeywords() {
		return this.ecomProductKeywords;
	}

	public void setEcomProductKeywords(List<EcomProductKeyword> ecomProductKeywords) {
		this.ecomProductKeywords = ecomProductKeywords;
	}

	public EcomProductKeyword addEcomProductKeyword(EcomProductKeyword ecomProductKeyword) {
		getEcomProductKeywords().add(ecomProductKeyword);
		ecomProductKeyword.setEcomProduct(this);

		return ecomProductKeyword;
	}

	public EcomProductKeyword removeEcomProductKeyword(EcomProductKeyword ecomProductKeyword) {
		getEcomProductKeywords().remove(ecomProductKeyword);
		ecomProductKeyword.setEcomProduct(null);

		return ecomProductKeyword;
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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	

	
}