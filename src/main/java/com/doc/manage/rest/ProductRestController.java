package com.doc.manage.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.ApiResponse;
import com.doc.manage.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductRestController {

	@Autowired
	private IProductService prodService;
	
	@PostMapping(path="/create")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public ApiResponse<EcomProductModel> createProduct(@RequestBody EcomProductModel product){

		EcomProductModel out = prodService.createProduct(product);

        return new ApiResponse<>(HttpStatus.OK.value(),"Name Updated",out);
    }
	
	@GetMapping(path="/getProductsByCategory")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public Resource<List<EcomProductModel>> getProductsByCategoryId(@RequestParam String catId){
		
		List<EcomProductModel> out = prodService.getProductsByCategory(catId);
		
		return new Resource<>(out);
		
	}
	
	@DeleteMapping(path="/deleteDoc/{prodId}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ApiResponse<Boolean> deleteDocument(@PathVariable Long prodId){
		
		EcomProductModel prodModel = prodService.getProductById(prodId);
		
		Path filePath = Paths.get("uploads"+"/"+prodModel.getDocUrl());

		boolean result = false;
		try {
			result = Files.deleteIfExists(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prodService.deleteProduct(prodId);
		
		return new ApiResponse<>(HttpStatus.OK.value(), "Deleted Document" , result);
		
	}
	
	@GetMapping(path="/search/{name}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public ApiResponse<List<EcomProductModel>> searchProducts(@PathVariable String name){
		
		List<EcomProductModel> out = prodService.getProductLikeName(name);
		
		return new ApiResponse<>(HttpStatus.OK.value(),"Returned products",out);
		
	}
	
	
	
}
