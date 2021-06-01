package com.doc.manage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.doc.manage.entity.EcomProduct;

public interface ProductRepository extends JpaRepository<EcomProduct, Long> {

	//@Query("SELECT e FROM EcomProduct e WHERE e.ecomCategory.categoryId=:categoryId")
	EcomProduct findByProductId(Long categoryId);
	
	List<EcomProduct> findLikeName(@Param("name") String name);
	
	List<EcomProduct> findByNameContaining(String name);
	
	

}
