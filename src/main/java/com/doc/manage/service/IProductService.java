package com.doc.manage.service;

import java.util.List;

import com.doc.manage.DTO.EcomProductModel;

/**
 * @author Girish.Yadav
 *
 */
public interface IProductService {

	EcomProductModel createProduct(EcomProductModel ecomProductModel);

	//boolean deleteProduct(EcomProductModel ecomProductModel);

	List<EcomProductModel> getProductsByCategory(String categoryId);

	void deleteProduct(Long prodId);
	
	EcomProductModel getProductById(Long id);

	List<EcomProductModel> getProductLikeName(String name);

		
}
