package com.doc.manage.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doc.manage.DTO.EcomCatalogModel;
import com.doc.manage.entity.ApiResponse;
import com.doc.manage.service.ICatalogService;

@RestController
@RequestMapping("/folder")
public class CategoryRestController {
	
	@Autowired
	private ICatalogService catService;
	
	
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public ApiResponse<List<EcomCatalogModel>> retrieveAllCategories() {
		return new ApiResponse<>(HttpStatus.OK.value(), "All Folders returned.",catService.getAllRootCategories());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public ApiResponse<List<EcomCatalogModel>> getCategoryById(@PathVariable("id") long id) {
		return new ApiResponse<>(HttpStatus.OK.value(), "Folders returned for id:"+id,catService.getCategoryById(id));
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public ApiResponse<EcomCatalogModel> createFolder(@RequestBody EcomCatalogModel folder){
		return new ApiResponse<>(HttpStatus.OK.value(), "Folder Created successfully.",catService.createCategory(folder));
    }
	
	@RequestMapping(value= "/**", method=RequestMethod.OPTIONS)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
	public void corsHeaders(HttpServletResponse response) {
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
	    response.addHeader("Access-Control-Max-Age", "3600");
	}
	
	

}
