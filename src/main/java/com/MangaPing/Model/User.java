package com.MangaPing.Model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@ElementCollection
	@CollectionTable(
		name = "user_favorite_anime",
		joinColumns = @JoinColumn(name = "user_id")
	)
	@Column(name = "anime_ids")
	private List<Integer> animeIds = new ArrayList<>();

}