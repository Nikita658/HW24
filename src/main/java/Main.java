import dao.AlbumDao;
import dao.ArtistDao;
import dao.CustomerDao;
import dao.OrderDao;
import entity.*;
import entity.entity_utils.Gender;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ArtistDao artistDao = new ArtistDao();
        CustomerDao customerDao = new CustomerDao();
        OrderDao orderDao = new OrderDao();
        AlbumDao albumDao = new AlbumDao();

        Customer customer = new Customer("Bruce", "Wayne", 30, Gender.MALE);
        Customer customer2 = new Customer("Ben", "Affleck", 48, Gender.MALE);
        Order order = new Order(UUID.randomUUID());
        Order order2 = new Order(UUID.randomUUID());
        Artist artist = new Artist("lsp", 22, Gender.MALE, "456789");
        Album album = new Album("One More City", "300", "01.11.2020", "5467890");
        Track track = new Track("fgkjlk;", "150", "01/10/2017");
        Artist artist1 = new Artist("cat", 22, Gender.FEMALE, "800000");
        Track track1 = new Track("dog", "10", "01/02/2020");

        Artist artist3 = new Artist("morg", 20, Gender.MALE, "9939999");
        Track track3 = new Track("Ice", "200", "01/02/2019");
        album.getTracks().add(track);
        album.getArtists().add(artist);
        album.getArtists().add(artist3);
        artist.getAlbums().add(album);
        artist3.getAlbums().add(album);
        artist.getTracks().add(track);

        artist1.getTracks().add(track1);
        artist3.getTracks().add(track3);

        order2.getTracks().add(track1);
        order2.getTracks().add(track3);
        customer2.getOrders().add(order2);

        order.getAlbums().add(album);
        order.getTracks().add(track);
        customer.getOrders().add(order);

        artistDao.saveArtist(artist1);
        artistDao.saveArtist(artist);
        customerDao.saveCustomer(customer);
        customerDao.saveCustomer(customer2);
    }
}
