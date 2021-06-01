package com.doc.manage.service;

import java.util.List;

import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.Favorite;

public interface IFavoriteService {
	
	List<EcomProductModel> getAllForUser(String userName);
	
	Favorite createFavorite(Favorite fav);

}
