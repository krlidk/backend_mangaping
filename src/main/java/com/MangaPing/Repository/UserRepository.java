package com.MangaPing.Repository;

import com.MangaPing.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	@Query( nativeQuery = true, value = "SELECT anime_ids FROM user_favorite_anime WHERE user_id = :id")
	List<Integer> getFavoriteAnimes(@Param("id") Long id);

} 
