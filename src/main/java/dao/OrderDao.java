package dao;

import entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernateUtils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

public class OrderDao {
    public void saveOrder(Order order) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void insertOrder() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            String hql = "INSERT INTO Orders (orderId) "
                    + "SELECT orderId FROM Student";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);

            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateOrderById(Order order, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Order set orderId = :orderId " + "WHERE id = :orId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", order.getOrderId());
            query.setParameter("orId", id);
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

    public Order getOrderById(int id) {
        Transaction transaction = null;
        Order order = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Order O WHERE O.id = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                order = (Order) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return order;
    }


    public List<Order> getOrder() {
        List<Order> orders = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orders = session.createQuery("from Order", Order.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void deleteOrderById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Order order = session.get(Order.class, id);
            if (order != null) {
                String hql = "DELETE FROM Order " + "WHERE id = :orderId";
                Query query = session.createQuery(hql);
                query.setParameter("orderId", id);
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

    public void deleteOrders() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Order";
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

    public void batchInsert(int quantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (int i = 0; i < quantity; i++) {
                Order order = new Order(UUID.randomUUID());
                session.save(order);

                if (i % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}