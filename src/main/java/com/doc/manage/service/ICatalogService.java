package com.doc.manage.service;

import java.util.List;

import com.doc.manage.DTO.EcomCatalogModel;


/**
 * @author Rahul Dev
 *
 */
public interface ICatalogService {

	List<EcomCatalogModel> getAllRootCategories();

	EcomCatalogModel createCategory(EcomCatalogModel ecomCatalogModel);

	List<EcomCatalogModel> getAllSubCategories(String categoryId);

	EcomCatalogModel getCategoryById(Long id);


	
}
