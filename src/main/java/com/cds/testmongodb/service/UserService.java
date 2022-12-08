package com.cds.testmongodb.service;

import com.cds.testmongodb.exception.CollectionException;
import com.cds.testmongodb.models.UserModel;
import java.util.List;

import jakarta.validation.ConstraintViolationException;

public interface UserService {
	
	void createUser(UserModel model) throws ConstraintViolationException, CollectionException;
	
	List<UserModel> getAllData();
	
	UserModel getSingleUserData(String id) throws CollectionException;
	
	void updateUser(String id, UserModel model) throws CollectionException;
	
	void deleteUser(String id) throws CollectionException;

}
