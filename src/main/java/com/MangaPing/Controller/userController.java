package com.MangaPing.Controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.MangaPing.Model.User;
import com.MangaPing.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("api/v1/user")
public class userController {

	@Autowired
	UserService userService;


	@PostMapping("/registerUser")
	public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> newUser){

		if(!newUser.keySet().containsAll(Arrays.asList("username","email","password"))){

			return ResponseEntity.badRequest().body("todos los campos deben ser llenados 1");
		}

		String username = (String) newUser.get("username");
		String email = (String) newUser.get("email");
		String password = (String) newUser.get("password");

		if (username == null || username.trim().isEmpty() ||
				email == null || email.trim().isEmpty() ||
				password == null || password.trim().isEmpty()) {
					return ResponseEntity.badRequest().body("todos los campos tienen que ser llenados");
		}

		User user = new User();

		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		ResponseEntity<?> response = userService.registerUser(user);
		return response;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request, HttpSession session ){
		String username = request.get("username");
		String password = request.get("password");

		if(username == null || password == null){
			return ResponseEntity.badRequest().body("Se requiere nombre de usuario y contraseña");
		}

		return userService.loginUser(username, password, session);
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<?> logoutUser(HttpSession session){
		session.invalidate();
		return ResponseEntity.ok("Sesion cerrada exitosamente");
	}

	@GetMapping("/auth/current-user")
	public ResponseEntity<?> getCurrentUser(HttpSession session){
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null){
			return ResponseEntity.status(401).body("Usuario no autenticado");
		}
		String id = userId.toString();
		return userService.getUserData(id);
	}

	@GetMapping("/userByEmail/{email}")
	public ResponseEntity<?> getUserEmail(@PathVariable String email){
		if(email.isBlank()){
			return ResponseEntity.badRequest().build();
		}

		ResponseEntity<?> response = userService.getUserByEmail(email);

		return response;
	}

	@GetMapping("/userByUsername/{username}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String username){
		if(username.isBlank()){
			return ResponseEntity.badRequest().body("No se ha encontrado un usuario con ese nombre");
		}

		ResponseEntity<?> response = userService.getUserByName(username);
		return response;
	}

	@GetMapping("/favoriteAnimes/{id}")
	public ResponseEntity<?> getFavoriteAnime(@PathVariable Long id){
		if(id < 1){
			return ResponseEntity.badRequest().body("Ingrese id valido");
		}

		 ResponseEntity<?> favoriteAnimes = userService.getFavoriteAnimes(id);
		 return favoriteAnimes;
	}


	@PostMapping("/addFavoriteAnime")
    public ResponseEntity<?> addFavoriteAnime(@RequestBody Map<String,Object> data){

		if(!data.keySet().containsAll(Arrays.asList("animeId","userId"))){
			return ResponseEntity.badRequest().body("Falta alguno de los parametros: animeId, userId");
		}

		Integer userId = (Integer) data.get("userId");
		String animeId = (String) data.get("animeId");

		if(userId == null) {
			return ResponseEntity.badRequest().body("No se ha encontrado el id para el usuario actual");
		}
		String id = userId.toString();
		Integer aniId = Integer.parseInt(animeId);

		ResponseEntity<?> reponse = userService.addFavoriteAnime(id, aniId);
		return reponse;
	
    }

	@DeleteMapping("/removeFromFavorite")
	public ResponseEntity<?> removeFromFavoriteAnime(@RequestBody Map<String, Object> data){
		if(!data.keySet().containsAll(Arrays.asList("userId", "animeId"))){
			return ResponseEntity.badRequest().body("No se han encontrado parametros validos");
		}

		Integer userId = (Integer) data.get("userId");
		String animeId = (String) data.get("animeId");

		if(userId == null){
			return ResponseEntity.badRequest().body("El id del usuario no es valido");
		}

		String uId = userId.toString(); 
		Integer aniId = Integer.parseInt(animeId);

		ResponseEntity<?> response = userService.removeFavoriteAnime(uId, aniId);

		return response;
	}
}
