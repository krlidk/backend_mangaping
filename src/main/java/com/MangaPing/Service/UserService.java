package com.MangaPing.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MangaPing.Model.User;
import com.MangaPing.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public ResponseEntity<?> getUserByEmail(String email){
		if(email.isBlank()){
			return ResponseEntity.badRequest().build();
		}
		Optional<User> userOp = userRepository.findByEmail(email);

		if(userOp == null){
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
