package com.MangaPing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.MangaPing.Service.UserService;

@Controller
@RequestMapping("api/v1/user")
public class userController {

	@Autowired
	UserService userService;

	@GetMapping("/userByEmail/{email}")
	public ResponseEntity<?> getUserEmail(@PathVariable String email){
		if(email.isBlank()){
			return ResponseEntity.badRequest().build();
		}

		ResponseEntity<?> response = userService.getUserByEmail(email);

		return response;
	}


}
