package com.cds.testmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cds.testmongodb.exception.CollectionException;
import com.cds.testmongodb.models.UserModel;
import com.cds.testmongodb.repositories.UserRepository;
import com.cds.testmongodb.service.UserService;

import jakarta.validation.ConstraintViolationException;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService serviceRepo;
	
	@GetMapping("/")
	public ResponseEntity<?> getNoData(){
		return new ResponseEntity<>("[{'msg' : 'no url'}]", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/allData")
	public ResponseEntity<?> getAllData(){
		List<UserModel> list = userRepo.findAll();
		
		if (list.size() > 0) {
			return new ResponseEntity<List<UserModel>>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("[{'msg' : 'no user data'}]", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/allData")
	public ResponseEntity<?> createData(@RequestBody UserModel model){
		try {
			model.setCreateAt(new Date(System.currentTimeMillis()));
			model.setUpdatedAt(new Date(System.currentTimeMillis()));
			userRepo.save(model);
			return new ResponseEntity<UserModel>(model, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
	}
	
	@GetMapping("/allData/{id}")
	public ResponseEntity<?> userData(@PathVariable("id") String id){
		Optional<UserModel> userOptional = userRepo.findById(id);
		if(userOptional.isPresent()) {
			return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/allData/{id}")
	public ResponseEntity<?> updateByIdData(@PathVariable("id") String id, @RequestBody UserModel model){
		Optional<UserModel> userOptional = userRepo.findById(id);
		if(userOptional.isPresent()) {
			UserModel user = userOptional.get();
			user.setId(model.getId()!= null ? model.getId() : user.getId());
			user.setEmail(model.getEmail()!= null ? model.getEmail() : user.getEmail());
			user.setPhoneNumber(model.getPhoneNumber()!= null ? model.getPhoneNumber() : user.getPhoneNumber());
			user.setName(model.getName()!= null ? model.getName() : user.getName());
			user.setUpdatedAt(new Date(System.currentTimeMillis()));
			userRepo.save(user);
			return new ResponseEntity<>(user,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/allData/{id}")
	public ResponseEntity<?> deleteByIdData(@PathVariable("id") String id){
	
		try {
			userRepo.deleteById(id);
			return new ResponseEntity<>("{'msg' : 'delete user data'}",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
	
//	TODO new Way
	
	@PostMapping("/createData")
	public ResponseEntity<?> createNewData(@RequestBody UserModel model){
		try {
			serviceRepo.createUser(model);
			return new ResponseEntity<UserModel>(model, HttpStatus.CREATED);
		}catch(ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(CollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/allUser")
	public ResponseEntity<?> getUsersData(){
		List<UserModel> list = serviceRepo.getAllData();
		return new ResponseEntity<>(list, list.size() > 0 ?  HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/allUser/{id}")
	public ResponseEntity<?> singleUserData(@PathVariable("id") String id){
		try {
			return new ResponseEntity<>(serviceRepo.getSingleUserData(id),HttpStatus.OK);
		}catch (Exception e){
			e.getMessage();
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/allUser/{id}")
	public ResponseEntity<?> updateUserByIdData(@PathVariable("id") String id, @RequestBody UserModel model){
		try {
			serviceRepo.updateUser(id, model);
			return new ResponseEntity<>("Updated "+id,HttpStatus.OK);
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(CollectionException e) {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/allUser/{id}")
	public ResponseEntity<?> deleteUserByIdData(@PathVariable("id") String id){
	
		try {
			serviceRepo.deleteUser(id);
			return new ResponseEntity<>("{'msg' : 'delete user data'}",HttpStatus.OK);
		}catch(CollectionException e) {
			return new ResponseEntity<>("{'msg' : 'no user data'}",HttpStatus.NOT_FOUND);
		}
	}
}
