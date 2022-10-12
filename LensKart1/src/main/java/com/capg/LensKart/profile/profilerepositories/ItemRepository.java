package com.capg.LensKart.profile.profilerepositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.capg.LensKart.profile.entity.Items;


@Repository
public interface ItemRepository extends CrudRepository<Items, Integer> {
	
	@Query(value = "SELECT * FROM Items  WHERE product_name LIKE ? AND username LIKE ?", nativeQuery = true)
	public List<Items> findByName(@PathVariable("name") String name, @PathVariable("usNam") String usNam);
	
	@Query(value = "SELECT * FROM Items  WHERE username LIKE ?", nativeQuery = true)
	public List<Items> findItemsByUSerName(@PathVariable("usNam") String usNam);
	
	
}
