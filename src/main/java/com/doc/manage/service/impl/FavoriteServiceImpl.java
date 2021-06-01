package com.doc.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.EcomProduct;
import com.doc.manage.entity.Favorite;
import com.doc.manage.repository.FavoriteRepository;
import com.doc.manage.repository.ProductRepository;
import com.doc.manage.service.IFavoriteService;
import com.doc.manage.util.EntityToModelUtil;

@Service
public class FavoriteServiceImpl implements IFavoriteService {
	
	@Autowired
	private FavoriteRepository favRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private EntityToModelUtil e2m;

	@Override
	public List<EcomProductModel> getAllForUser(String userName) {

		List<Favorite> favList = favRepo.findByUser(userName);
		
		List<EcomProductModel> prodModelList = new ArrayList<>();
		
		for(Favorite fav : favList) {
			EcomProduct ecomProd =  prodRepo.findByProductId(fav.getProductId());
			EcomProductModel prodmodel = e2m.convertProductTypeToModel(ecomProd);
			prodModelList.add(prodmodel);
		}
		
		
		return prodModelList;
	}

	@Override
	public Favorite createFavorite(Favorite favModel) {
		
		return favRepo.save(favModel);
	}

}
