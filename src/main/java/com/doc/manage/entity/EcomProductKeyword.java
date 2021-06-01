package com.doc.manage.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * The persistent class for the ecom_product_keyword database table.
 * 
 */
@Entity
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="EcomProductKeyword")
@Table(name="ecom_product_keyword")
//@NamedQuery(name="EcomProductKeyword.findAll", query="SELECT e FROM EcomProductKeyword e", hints = { @QueryHint(name = UruAppConstants.ORG_HIBERNATE_CACHEABLE, value = "true")})
public class EcomProductKeyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="KEYWORD")
	private String keyword;

	//bi-directional many-to-one association to EcomProduct
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private EcomProduct ecomProduct;

	public EcomProductKeyword() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public EcomProduct getEcomProduct() {
		return this.ecomProduct;
	}

	public void setEcomProduct(EcomProduct ecomProduct) {
		this.ecomProduct = ecomProduct;
	}

}