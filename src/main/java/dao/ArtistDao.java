package dao;

import entity.Artist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernateUtils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class ArtistDao {
    public void saveArtist(Artist artist) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(artist);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateAlbumById(Artist artist, int artistId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Artist set nickname = :nickname, age = :age, gender = :gender, " +
                    "plays = :plays " + "WHERE id = :artistId";
            Query query = session.createQuery(hql);
            query.setParameter("nickname", artist.getNickname());
            query.setParameter("age", artist.getAge());
            query.setParameter("gender", artist.getGender());
            query.setParameter("plays", artist.getPlays());
            query.setParameter("artistId", artistId);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Artist getArtistById(int id) {
        Transaction transaction = null;
        Artist artist = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Artist A WHERE A.id = :artistId";
            Query query = session.createQuery(hql);
            query.setParameter("artistId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                artist = (Artist) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return artist;
    }

    public Artist getArtistByNickname(String nickname) {
        Transaction transaction = null;
        Artist artist = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Artist A WHERE A.title LIKE :artistName";
            Query query = session.createQuery(hql);
            query.setParameter("artistName", nickname);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                artist = (Artist) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return artist;
    }


    public List<Artist> getArtists() {
        List<Artist> artists = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            artists = session.createQuery("from Artist", Artist.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }

    public void deleteArtistById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Artist artist = session.get(Artist.class, id);
            if (artist != null) {
                String hql = "DELETE FROM Artist " + "WHERE id = :artistId";
                Query query = session.createQuery(hql);
                query.setParameter("artistId", id);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteArtists() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Artist";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}