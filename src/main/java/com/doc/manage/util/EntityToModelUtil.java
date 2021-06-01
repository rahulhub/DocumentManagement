package com.doc.manage.util;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.doc.manage.DTO.EcomCatalogModel;
import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.EcomCategory;
import com.doc.manage.entity.EcomProduct;


@Component
public class EntityToModelUtil {

	public EcomCatalogModel convertEcomCatalogToModel(EcomCategory ecomCategory) {
		
		EcomCatalogModel model = new EcomCatalogModel();
		model.setCategoryId(ecomCategory.getCategoryId());
		model.setCreationDate(ecomCategory.getCreationDate());
		model.setLargeImgAltText(ecomCategory.getLargeImgAltText());
		model.setLargeImgUrl(ecomCategory.getLargeImgUrl());
		model.setLongDesc(ecomCategory.getLongDesc());
		model.setModifiedDate(ecomCategory.getModifiedDate());		
		model.setName(ecomCategory.getName());		
		model.setShortDesc(ecomCategory.getShortDesc());
		model.setSmallImgAltText(ecomCategory.getSmallImgAltText());
		model.setSmallImgUrl(ecomCategory.getSmallImgUrl());
		model.setIsDeleted(ecomCategory.getIsDeleted());
		
		
		if(ecomCategory.getEcomCategories()!=null && !ecomCategory.getEcomCategories().isEmpty()){
			List<EcomCatalogModel> listEcomCatalogModel = new ArrayList<EcomCatalogModel>();
			for(EcomCategory localEcomCategory: ecomCategory.getEcomCategories()){
				EcomCatalogModel ecomCatalogModel = convertEcomCatalogToModel(localEcomCategory);
				ecomCatalogModel.setParentCategory(model);
				listEcomCatalogModel.add(ecomCatalogModel);
			}
			model.setChildren(listEcomCatalogModel);
		}
		
		if(ecomCategory.getEcomProducts() != null && !ecomCategory.getEcomProducts().isEmpty()){			
		List<EcomProductModel> ptmSet = new ArrayList<EcomProductModel>();
		for (EcomProduct pt : ecomCategory.getEcomProducts()) {
			EcomProductModel ptm = convertProductTypeToModel(pt);
			ptm.setEcomCategory(model);
			ptmSet.add(ptm);
		}

		model.setEcomProducts(ptmSet);
		}
		return model;
	}

	public EcomProductModel convertProductTypeToModel(EcomProduct ecomProduct) {

		EcomProductModel ptModel = new EcomProductModel();
		ptModel.setProductId(ecomProduct.getProductId());
		ptModel.setCreationDate(ecomProduct.getCreationDate());
		ptModel.setCreator(ecomProduct.getCreator());
		ptModel.setLongDesc(ecomProduct.getLongDesc());
		ptModel.setModifiedDate(ecomProduct.getModifiedDate());
		ptModel.setName(ecomProduct.getName());
		ptModel.setVisible(ecomProduct.getVisible());
		ptModel.setDocUrl(ecomProduct.getDocUrl());
		ptModel.setSize(ecomProduct.getSize());
		ptModel.setExtension(ecomProduct.getExtension());
		
//		List<EcomItemModel> itemModels = new ArrayList<EcomItemModel>();
//		if (null != ecomProduct.getEcomItems()) {
//			for (EcomItem item : ecomProduct.getEcomItems()) {
//				EcomItemModel im = convertItemToModel(item);
//				im.setEcomProduct(ptModel);
//				itemModels.add(im);
//			}
//		}
//
//		ptModel.setEcomItems(itemModels);

		return ptModel;

	}

	
}
	


	

