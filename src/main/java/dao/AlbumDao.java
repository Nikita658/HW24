package dao;

import entity.Album;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernateUtils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class AlbumDao {
    public void saveAlbum(Album album) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateAlbumById(Album album, int albumId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Album set title = :title, price = :price, releaseDate = :releaseDate, " +
                    "plays = :plays " + "WHERE id = :albumId";
            Query query = session.createQuery(hql);
            query.setParameter("title", album.getTitle());
            query.setParameter("price", album.getPrice());
            query.setParameter("releaseDate", album.getReleaseDate());
            query.setParameter("plays", album.getPlays());
            query.setParameter("albumId", albumId);
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

    public Album getAlbumById(int id) {
        Transaction transaction = null;
        Album album = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Album A WHERE A.id = :albumId";
            Query query = session.createQuery(hql);
            query.setParameter("albumId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                album = (Album) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return album;
    }

    public Album getAlbumByTitle(String title) {
        Transaction transaction = null;
        Album album = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Album A WHERE A.title LIKE :albumTitle";
            Query query = session.createQuery(hql);
            query.setParameter("albumTitle", title);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                album = (Album) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return album;
    }


    public List<Album> getAlbums() {
        List<Album> albumList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            albumList = session.createQuery("from Album", Album.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumList;
    }

    public void deleteAlbumById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Album album = session.get(Album.class, id);
            if (album != null) {
                String hql = "DELETE FROM Album " + "WHERE id = :albumId";
                Query query = session.createQuery(hql);
                query.setParameter("albumId", id);
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

    public void deleteAlbums() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Album";
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