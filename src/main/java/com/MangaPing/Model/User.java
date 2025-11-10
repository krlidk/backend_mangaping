package com.MangaPing.Model;

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
    private String email;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
        name = "user_anime", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email"),
        inverseJoinColumns = @JoinColumn(name = "anime_id", referencedColumnName = "idAnime") // Llave foránea hacia Anime
    )
    private List<Anime> animes;


}