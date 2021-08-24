package dao;

import entity.Track;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernateUtils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class TrackDao {
    public void saveTrack(Track track) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(track);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateTrackById(Track track, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Album set title = :title, price = :price, releaseDate = :releaseDate, "
                    + "WHERE id = :trackId";
            Query query = session.createQuery(hql);
            query.setParameter("title", track.getTitle());
            query.setParameter("price", track.getPrice());
            query.setParameter("releaseDate", track.getReleaseDate());
            query.setParameter("trackId", id);
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

    public Track getTrackById(int id) {
        Transaction transaction = null;
        Track track = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Track T WHERE T.id = :trackId";
            Query query = session.createQuery(hql);
            query.setParameter("trackId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                track = (Track) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return track;
    }

    public Track getTrackByTitle(String title) {
        Transaction transaction = null;
        Track track = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Track T WHERE T.title LIKE :trackTitle";
            Query query = session.createQuery(hql);
            query.setParameter("carTitle", title);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                track = (Track) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return track;
    }


    public List<Track> getTracks() {
        List<Track> trackList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trackList = session.createQuery("from Track", Track.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackList;
    }

    public void deleteTrackById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Track track = session.get(Track.class, id);
            if (track != null) {
                String hql = "DELETE FROM Track " + "WHERE id = :trackId";
                Query query = session.createQuery(hql);
                query.setParameter("trackId", id);
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

    public void deleteTracks() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Track";
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