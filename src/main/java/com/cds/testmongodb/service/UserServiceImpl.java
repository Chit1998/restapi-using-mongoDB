package com.cds.testmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cds.testmongodb.exception.CollectionException;
import com.cds.testmongodb.models.UserModel;
import com.cds.testmongodb.repositories.UserRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void createUser(UserModel model) throws ConstraintViolationException,CollectionException {
		// TODO Auto-generated method stub
		Optional<UserModel> optional = userRepo.findByUser(model.getEmail());
		
		if (optional.isPresent()) {
			throw new CollectionException(CollectionException.AlreadyExistsException());
		}else {
			model.setCreateAt(new Date(System.currentTimeMillis()));
			model.setUpdatedAt(new Date(System.currentTimeMillis()));
			userRepo.save(model);

		}
		
	}

	@Override
	public List<UserModel> getAllData() {
		// TODO Auto-generated method stub
		List<UserModel> list = userRepo.findAll();
		if (list.size() > 0) {
			return list;
		}else {
			return new ArrayList<UserModel>();
		}
	}

	@Override
	public UserModel getSingleUserData(String id) throws CollectionException {
		// TODO Auto-generated method stub
		Optional<UserModel> optional = userRepo.findById(id);
		if (!optional.isPresent()) {
			throw new CollectionException(CollectionException.notFoundException(id));
		}else {
			return optional.get();	
		}
	}

	@Override
	public void updateUser(String id, UserModel model) throws CollectionException {
		// TODO Auto-generated method stub
		Optional<UserModel> optionalUpdate = userRepo.findById(id);
		Optional<UserModel> optionalEmailUpdate = userRepo.findByUser(model.getEmail());
		if (optionalUpdate.isPresent()) {
			if (optionalEmailUpdate.isPresent() && !optionalEmailUpdate.get().getId().equals(id)) {
				throw new CollectionException(CollectionException.AlreadyExistsException());
			}
			UserModel userUpdate = optionalUpdate.get();
			userUpdate.setEmail(model.getEmail());
			userUpdate.setName(model.getName());
			userUpdate.setPhoneNumber(model.getEmail());
			userUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			userRepo.save(userUpdate);
		}else {
			throw new CollectionException(CollectionException.notFoundException(id));
		}
	}

	@Override
	public void deleteUser(String id) throws CollectionException {
		// TODO Auto-generated method stub
		Optional<UserModel> optional = userRepo.findById(id);
		
		if (!optional.isPresent()) {
			throw new CollectionException(CollectionException.notFoundException(id));
		}else {
			userRepo.deleteById(id);
		}
	}

}
