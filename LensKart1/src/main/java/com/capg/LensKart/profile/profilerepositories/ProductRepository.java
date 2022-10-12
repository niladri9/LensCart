package com.capg.LensKart.profile.profilerepositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capg.LensKart.profile.entity.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	@Query(value = "SELECT * FROM Frame  WHERE  brand_name LIKE ?", nativeQuery = true)
	public List<Product> findByNameFrame(@Param("name") String name);
	
	@Query(value = "SELECT * FROM Glass  WHERE  brand_name LIKE ?", nativeQuery = true)
	public List<Product> findByNameGlass(@Param("name") String name);
	
	@Query(value = "SELECT * FROM Lenses  WHERE  brand_name LIKE ?", nativeQuery = true)
	public List<Product> findByNameLenses(@Param("name") String name);
	
	@Query(value = "SELECT * FROM SunGlasses  WHERE  brand_name LIKE ?", nativeQuery = true)
	public List<Product> findByNameSunGlasses(@Param("name") String name);
	
	@Query(value = "SELECT * FROM Product  WHERE  category LIKE ?", nativeQuery = true)
	public List<Product> findByCategory(@Param("cat") String cat);
	
	
}