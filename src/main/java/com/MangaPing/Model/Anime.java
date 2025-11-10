package com.MangaPing.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "anime")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Anime {
    @Id
    private Integer idAnime;

}
