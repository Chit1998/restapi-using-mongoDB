package com.cds.testmongodb.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cds.testmongodb.models.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
	
	@Query("{'email': ?0}")
	Optional<UserModel> findByUser(String id);
}
