package com.doc.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.manage.DTO.EcomCatalogModel;
import com.doc.manage.entity.EcomCategory;
import com.doc.manage.repository.CategoryRepository;
import com.doc.manage.service.ICatalogService;
import com.doc.manage.util.EntityToModelUtil;
import com.doc.manage.util.ModelToEntityUtil;

@Service
public class CatalogServiceImpl implements ICatalogService {

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private ModelToEntityUtil m2e;
	
	@Autowired
	private EntityToModelUtil e2m;
	
	@Override
	public List<EcomCatalogModel> getAllRootCategories() {
		List<EcomCategory> root = catRepo.findRootCategories();
		List<EcomCatalogModel> modelList = new ArrayList<>();
		root.stream().forEach(cat -> {
			modelList.add(e2m.convertEcomCatalogToModel(cat));
		});
		
		return modelList;
	}

	@Override
	@Transactional
	public EcomCatalogModel createCategory(EcomCatalogModel ecomCatalogModel) {
		EcomCategory entity = m2e.convertProductCatalogToEntity(ecomCatalogModel);
		EcomCategory out = catRepo.save(entity);
		EcomCatalogModel resModel = e2m.convertEcomCatalogToModel(out);
		return resModel;
	}

	@Override
	public List<EcomCatalogModel> getAllSubCategories(String categoryId) {		
		return null;
	}

	
//	@Override
//	public String testCreate() {
//		CatalogPropertyKeyModel key1 = new CatalogPropertyKeyModel();
//		key1.setPropertyCode("LenCode");
//		key1.setPropertyName("length");
//		String propertyKey1=catalogDao.createCatalogPropertyKey(key1);
//		
//		CatalogPropertyKeyModel key2 = new CatalogPropertyKeyModel();
//		key2.setPropertyCode("widCode");
//		key2.setPropertyName("width");
//		
//		String propertyKey2=catalogDao.createCatalogPropertyKey(key2);
//		
//		
//		EcomCatalogModel ecomCatalogTop = new EcomCatalogModel();
//		
//		ecomCatalogTop.setName("Optimet");
//		
//		
//		EcomCatalogModel ecomCatalogChild1 = new EcomCatalogModel();
//		ecomCatalogChild1.setName("Steel");
//		ecomCatalogChild1.setParentCategory(ecomCatalogTop);
//		
//		List<EcomCatalogModel> subCategories = new ArrayList<EcomCatalogModel>();
//		subCategories.add(ecomCatalogChild1);
//		
//		ecomCatalogTop.setEcomCategories(subCategories);
//		
//		List<EcomProductModel> products = new ArrayList<EcomProductModel>();
//		
//		EcomProductModel ecomProduct = new EcomProductModel();
//		ecomProduct.setName("Sheets");
//		ecomProduct.setEcomCategory(ecomCatalogChild1);
//		
//		EcomProductModel ecomProduct1 = new EcomProductModel();
//		ecomProduct1.setName("CR Section");
//		ecomProduct1.setEcomCategory(ecomCatalogChild1);
//		
//		products.add(ecomProduct);
//		products.add(ecomProduct1);
//		
//		ecomCatalogChild1.setEcomProducts(products);
//		
//		List<CatalogPropertyValueModel> valList1 = new ArrayList<CatalogPropertyValueModel>();
//		List<CatalogPropertyValueModel> valList2 = new ArrayList<CatalogPropertyValueModel>();
//		
//		
//		
//		CatalogPropertyValueModel catPropValModel1 = new CatalogPropertyValueModel();
//		catPropValModel1.setTextValue("100");
//		catPropValModel1.setCatalogPropertyKey(catalogDao.fetchCatalogPropertyByCode(key1.getPropertyCode()));
//		
//		CatalogPropertyValueModel catPropValModel2 = new CatalogPropertyValueModel();
//		catPropValModel2.setTextValue("200");
//		catPropValModel2.setCatalogPropertyKey(catalogDao.fetchCatalogPropertyByCode(key2.getPropertyCode()));
//		
//		
//		valList1.add(catPropValModel1);
//		valList1.add(catPropValModel2);
//		valList2.add(catPropValModel1);
//		valList2.add(catPropValModel2);
//		
//		EcomItemModel ecomItem11 = new EcomItemModel();
//		ecomItem11.setName("Item1");
//		ecomItem11.setEcomProduct(ecomProduct);
//		ecomItem11.setCatalogPropertyValues(valList1);
//		
//		
//		EcomItemModel ecomItem12 = new EcomItemModel();
//		ecomItem12.setName("Item2");
//		ecomItem12.setEcomProduct(ecomProduct);
//		ecomItem12.setCatalogPropertyValues(valList2);
//		
//		EcomItemModel ecomItem21 = new EcomItemModel();
//		ecomItem21.setName("Item21");
//		ecomItem21.setEcomProduct(ecomProduct1);
//		ecomItem21.setCatalogPropertyValues(valList1);
//		
//		EcomItemModel ecomItem22 = new EcomItemModel();
//		ecomItem22.setName("Item22");
//		ecomItem22.setEcomProduct(ecomProduct1);
//		ecomItem22.setCatalogPropertyValues(valList2);
//		
//		
//		List<EcomItemModel> itemList1 = new ArrayList<EcomItemModel>();
//		itemList1.add(ecomItem11);
//		itemList1.add(ecomItem12);
//		
//		List<EcomItemModel> itemList2 = new ArrayList<EcomItemModel>();
//		itemList2.add(ecomItem21);
//		itemList2.add(ecomItem22);
//		
//		
//		ecomProduct.setEcomItems(itemList1);
//		ecomProduct1.setEcomItems(itemList2);
//		
//		catalogDao.createCategory(ecomCatalogTop);
//		
//		return "success";
//	}


	@Override
	public EcomCatalogModel getCategoryById(Long id) {
		return e2m.convertEcomCatalogToModel(catRepo.findById(id).get());
//		return catalogDao.findCategoryById(id);
	}
	

	
}
