package com.capg.LensKart.profile.profilerepositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.capg.LensKart.profile.entity.Cart;


@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
	
	@Query(value = "SELECT * FROM Cart  WHERE username LIKE ?", nativeQuery = true)
	public List<Cart> getCartByUserName(@PathVariable("usNam") String usNam);
	
}