package com.MangaPing.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.MangaPing.Model.User;
import com.MangaPing.Service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = userController.class)
public class userControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	public void registerUser() throws Exception {
		User usuario = new User();
		User usuario2 = new User();

		usuario.setId((long) 1);
		usuario2.setId((long) 1);

		usuario.setUsername("usuario 1");
		usuario2.setUsername("usuario 2");

		usuario.setPassword("12345678");
		usuario2.setPassword("12345678");

		usuario.setEmail("usuario1@pete.cl");
		usuario2.setEmail("usuario2@pete.cl");


		when(userService.registerUser(usuario)).thenReturn(ResponseEntity.ok().build());

		//mockMvc.perfom(get("/api/v1/user"))



	} 
}
