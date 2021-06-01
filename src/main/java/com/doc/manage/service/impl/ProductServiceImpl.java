package com.doc.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.EcomProduct;
import com.doc.manage.repository.FavoriteRepository;
import com.doc.manage.repository.ProductRepository;
import com.doc.manage.service.IProductService;
import com.doc.manage.util.EntityToModelUtil;
import com.doc.manage.util.ModelToEntityUtil;

@Service
public class ProductServiceImpl implements IProductService {

//	@Inject
//	IProductDao productDao;
	
	@Autowired
	ProductRepository prodRepo;
	
	@Autowired
	FavoriteRepository favRepo;
	
	@Autowired
	private ModelToEntityUtil m2e;
	
	@Autowired
	private EntityToModelUtil e2m;
	
	@Override
	public EcomProductModel createProduct(EcomProductModel ecomProductModel) {
		
		EcomProduct productEntity = m2e.convertProductTypeModelToEntity(ecomProductModel);
		prodRepo.save(productEntity);
		
		return e2m.convertProductTypeToModel(productEntity);
	}

	@Override
	public List<EcomProductModel> getProductsByCategory(String categoryId) {
		
		List<EcomProduct> prodList = prodRepo.findAll();
		List<EcomProductModel> returnList = new ArrayList<>();
		prodList.stream().forEach(prod -> {
			returnList.add(e2m.convertProductTypeToModel(prod));
		});
		
		return returnList;
	}

	@Override
	public void deleteProduct(Long prodId) {
		favRepo.deleteByProductId(prodId);
		prodRepo.deleteById(prodId);
		
	}

	@Override
	public EcomProductModel getProductById(Long id) {
		return e2m.convertProductTypeToModel(prodRepo.findById(id).get());
	}
	
	@Override
	public List<EcomProductModel> getProductLikeName(String name) {
		List<EcomProduct> returned = prodRepo.findByNameContaining(name);
		List<EcomProductModel> converted = new ArrayList<>();
		returned.forEach(prod -> {
			converted.add(e2m.convertProductTypeToModel(prod));
		});
		
		return converted;
	}
	
	
	



	

}
