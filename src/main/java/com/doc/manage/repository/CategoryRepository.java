package com.doc.manage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doc.manage.entity.EcomCategory;

public interface CategoryRepository extends JpaRepository<EcomCategory, Long> {
	
	List<EcomCategory> findRootCategories();

}
