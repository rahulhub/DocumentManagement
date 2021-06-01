package com.doc.manage.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.doc.manage.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	
	List<Favorite> findByUser(@Param("user") String user);

	@Transactional
	@Modifying
	void deleteByProductId(@Param("productId") Long productId);

}
