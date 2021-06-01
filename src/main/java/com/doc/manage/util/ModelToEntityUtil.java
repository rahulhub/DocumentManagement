package com.doc.manage.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doc.manage.DTO.EcomCatalogModel;
import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.EcomCategory;
import com.doc.manage.entity.EcomProduct;
import com.doc.manage.repository.CategoryRepository;
import com.doc.manage.repository.ProductRepository;

/**
 * @author Rahul Dev
 *
 */
@Component
public class ModelToEntityUtil {

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private ProductRepository prodRepo;

	public EcomCategory convertProductCatalogToEntity(final EcomCatalogModel model) {

		Optional<EcomCategory> orgEntity = null;
		EcomCategory pcEntity = new EcomCategory();
		if (null != model.getCategoryId())
		{
			orgEntity = this.catRepo.findById((Long.valueOf(model.getCategoryId())));
			pcEntity = orgEntity.get();
		}
		
			if(model.getCategoryId() != null)
			pcEntity.setCategoryId(model.getCategoryId());
			
			if(model.getCreationDate() != null)
			pcEntity.setCreationDate(model.getCreationDate());
			
			if(model.getLargeImgAltText() != null)
			pcEntity.setLargeImgAltText(model.getLargeImgAltText());
			
			if(model.getLargeImgUrl() != null)
			pcEntity.setLargeImgUrl(model.getLargeImgUrl());
			
			if(model.getLongDesc() != null)
			pcEntity.setLongDesc(model.getLongDesc());
			
			if(model.getModifiedDate() != null)
			pcEntity.setModifiedDate(model.getModifiedDate());
			
			if(model.getName() != null)
			pcEntity.setName(model.getName());
			
			if(model.getShortDesc() != null)
			pcEntity.setShortDesc(model.getShortDesc());
			
			if(model.getSmallImgAltText() != null)
			pcEntity.setSmallImgAltText(model.getSmallImgAltText());
			
			
			if(model.getSmallImgUrl() != null)
			pcEntity.setSmallImgUrl(model.getSmallImgUrl());
			
			
			if(model.getIsDeleted() != -1)
			pcEntity.setIsDeleted(model.getIsDeleted());


		final List<EcomCategory> ecomCategories = new ArrayList<EcomCategory>();

		if (model.getChildren() != null && model.getChildren().size() > 0) {
			for (final EcomCatalogModel pcm : model.getChildren()) {
				final EcomCategory pc = this.convertProductCatalogToEntity(pcm);
				pc.setEcomCategory(pcEntity);
				ecomCategories.add(pc);
			}
			pcEntity.setEcomCategories(ecomCategories);
		} else
			pcEntity.setEcomCategories(null);

		if (model.getEcomProducts() != null && model.getEcomProducts().size() > 0) {

			final List<EcomProduct> prodTypeEntities = new ArrayList<EcomProduct>();
			for (final EcomProductModel prodTypeModel : model.getEcomProducts()) {
				final EcomProduct pt = this.convertProductTypeModelToEntity(prodTypeModel);
				pt.setEcomCategory(pcEntity);
				prodTypeEntities.add(pt);
			}
			pcEntity.setEcomProducts(prodTypeEntities);
		}
		return pcEntity;
	}

	public EcomProduct convertProductTypeModelToEntity(final EcomProductModel model) {
		Optional<EcomProduct> orgProd=null;
		EcomProduct prodType = new EcomProduct();
		if (null != model.getProductId())
		{
			orgProd = this.prodRepo.findById(Long.valueOf(model.getProductId()));
			prodType = orgProd.get();
		}
		
		if(model.getEcomCategory() !=null){
			Optional<EcomCategory> orgEntity = this.catRepo.findById((Long.valueOf(model.getEcomCategory().getCategoryId())));
			prodType.setEcomCategory(orgEntity.get());
		}

		if(model.getProductId() != null)
		prodType.setProductId(model.getProductId());
		
		if(model.getCreationDate() != null)
		prodType.setCreationDate(model.getCreationDate());
		
		if(model.getCreator() != null)
		prodType.setCreator(model.getCreator());
		
		if(model.getLongDesc() != null)
		prodType.setLongDesc(model.getLongDesc());
		
		if(model.getModifiedDate() != null)
		prodType.setModifiedDate(model.getModifiedDate());
		
		if(model.getName() != null)
		prodType.setName(model.getName());
		
		if(model.getDocUrl() != null)
			prodType.setDocUrl(model.getDocUrl());
		
		
		if(model.getVisible() != -1)
		prodType.setVisible(model.getVisible());
		
		if(model.getSize() != -1)
			prodType.setSize(model.getSize());
		
		if(model.getExtension() != null)
		prodType.setExtension(model.getExtension());

		return prodType;
	}

	
}
