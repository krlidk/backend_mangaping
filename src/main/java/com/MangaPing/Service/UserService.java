package com.MangaPing.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MangaPing.Model.User;
import com.MangaPing.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public ResponseEntity<?> registerUser(User newUser) {

		if (userRepository.existsByEmail(newUser.getEmail())
				|| userRepository.existsByUsername(newUser.getUsername())) {
			return ResponseEntity.badRequest().body("Este usuario ya existe");
		}
		User savedUser = userRepository.save(newUser);

		return ResponseEntity.ok("usuario registrado " + savedUser.getUsername());
	}

	public ResponseEntity<?> loginUser(String username, String password, HttpSession session) {

		if (username.isBlank() || password.isBlank()) {
			return ResponseEntity.badRequest().body("Debes rellenar todos los campos");
		}

		User loginUser = userRepository.findByUsername(username).orElse(null);

		if (loginUser == null || !loginUser.getPassword().equals(password)) {
			return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
		}

		session.setAttribute("userId", loginUser.getId());
		session.setAttribute("username", loginUser.getUsername());
		session.setMaxInactiveInterval(30 * 60);

		return ResponseEntity.ok(Map.of(
			"message", "Login Exitoso",
			"userId", loginUser.getId(),
			"username", loginUser.getUsername()
		));
	}

	public ResponseEntity<?> getUserData(String userId) {

		if(userId == null){
			return ResponseEntity.badRequest().body("Usuario no encontrado");
		}

		User user = userRepository.findById(userId).orElse(null);

		if (user == null) {
			return ResponseEntity.badRequest().body("Usuario no encontrado");
		}

		return ResponseEntity.ok(Map.of(
				"userId", user.getId(),
				"username", user.getUsername(),
				"email", user.getEmail(),
				"favoriteAnimes", user.getAnimeIds() != null ? user.getAnimeIds() : List.of()
			));
	}

	public ResponseEntity<?> getUserByEmail(String email) {
		Optional<User> userOp = userRepository.findByEmail(email);

		User user = userOp.orElse(null);
		if (user == null) {
			System.out.println(user);
			return ResponseEntity.badRequest().body("Usuario no encontrado");
		}

		return ResponseEntity.ok(user);
	}

	public ResponseEntity<?> getUserByName(String username) {
		Optional<User> userOp = userRepository.findByUsername(username);

		if (!userOp.isPresent()) {
			return ResponseEntity.badRequest().body("No se han encontrado un usuario con ese nombre");
		}

		return ResponseEntity.ok(userOp.get());
	}

	public ResponseEntity<?> getFavoriteAnimes(Long id) {
		List<Integer> animeIds = userRepository.getFavoriteAnimes(id);

		if (animeIds.isEmpty()) {
			return ResponseEntity.badRequest().body("No se han encontrado animes favoritos de este usuario");
		}
		return ResponseEntity.ok(animeIds);
	}

	@Transactional
	public ResponseEntity<?> addFavoriteAnime(String userId, int animeId) {
		if (userId == null) {
			return ResponseEntity.badRequest().body("No se ha encontrado usuario logueado");
		}

		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("No se ha encontrado usuario logueado");
		}

		User activeUser = user.get();
		List<Integer> userFavoriteAnimes = activeUser.getAnimeIds();

		if(userFavoriteAnimes.contains(animeId)){
			return ResponseEntity.badRequest().body("Este anime ya se encuenta agregado a favoritos");
		}
		userFavoriteAnimes.add(animeId);
		userRepository.save(activeUser);
		return ResponseEntity.ok("Anime agregado");
	}

	@Transactional
	public ResponseEntity<?> removeFavoriteAnime(String userId, Integer animeId) {

		if (userId == null || userId.isBlank()) {
			return ResponseEntity.badRequest().body("No se ha encontrado el id de este usuario");
		}

		User activeUser = userRepository.findById(userId).orElseThrow(null);

		if (activeUser == null) {
			return ResponseEntity.badRequest().body("No se ha podido encontrar el usuario");
		}

		List<Integer> favoriteAnimes = activeUser.getAnimeIds();
		int removeIndex = favoriteAnimes.indexOf(animeId);

		// si el valor es -1 significa que no encontro el valor dentro de la lista
		// valor definido por el metodo indexOf()
		if (removeIndex == -1) {
			return ResponseEntity.badRequest().body("No se ha podido encontrar un anime con este id");
		}

		favoriteAnimes.remove(removeIndex);
		userRepository.save(activeUser);
		return ResponseEntity.ok("Anime eliminado de la lista de favoritos");

	}
}
