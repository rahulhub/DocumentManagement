package com.doc.manage.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.ApiResponse;
import com.doc.manage.entity.Favorite;
import com.doc.manage.service.IFavoriteService;

@RestController
@RequestMapping("/favorite") 
class FavoriteController {
	
	@Autowired
	private IFavoriteService favService;
	
	@GetMapping("/all/{userName}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public ApiResponse<List<EcomProductModel>> getAllFavorite(@PathVariable(value="userName") String userName) {
		return new ApiResponse<>(HttpStatus.OK.value(), "All Favorites returned.",favService.getAllForUser(userName));
	}
	
	@PostMapping("/create")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public ApiResponse<Favorite> createFavorite(@RequestBody Favorite favorite){
		return new ApiResponse<>(HttpStatus.OK.value(), "Favorite Created successfully.",favService.createFavorite(favorite));
    }

}
