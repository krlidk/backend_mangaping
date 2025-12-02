package com.MangaPing.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.MangaPing.Model.User;
import com.MangaPing.Service.UserService;

import jakarta.servlet.http.HttpSession;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = userController.class)
public class userControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	public void registerUser() throws Exception {

		when(userService.registerUser(any(User.class))).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(post("/api/v1/user/registerUser")
				.contentType("application/json")
				.accept("application/json")
				.content("""
						{

						"username": "usuario1",
						"password": "12345678",
						"email": "usuario1@gmail.com"
						}
						""")).andExpect(status().isOk());
	}

	@Test
	public void login() throws Exception {

		when(userService.loginUser(
				any(String.class),
				any(String.class),
				any(HttpSession.class))).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(post("/api/v1/user/auth/login")
				.contentType("application/json")
				.accept("application/json")
				.content("""
						{
						    "username": "usuario1",
						    "password": "12345678"
						}
						"""))
				.andExpect(status().isOk());
	}

}
