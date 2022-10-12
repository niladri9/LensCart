package com.capg.LensKart.profile.profilerepositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capg.LensKart.profile.entity.Profile;


@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

	
	@Query(value = "SELECT * FROM Profile  WHERE  email_id LIKE ?", nativeQuery = true)
	public List<Profile> findByUserName(@Param("emailId") String email);
}
