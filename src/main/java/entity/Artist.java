package entity;

import entity.entity_utils.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "artists")
    private List<Album> albums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Track> tracks = new ArrayList<>();
    @Column(name = "plays")
    private String plays;
    public Artist(String nickname, int age, Gender gender, String plays) {
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.plays = plays;
    }
}