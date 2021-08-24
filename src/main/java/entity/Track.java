package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "track")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private String price;

    @Column(name = "release_date")
    private String releaseDate;

    public Track(String title, String price, String releaseDate) {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
    }
}