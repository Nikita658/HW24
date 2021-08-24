package dao;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import hibernateUtils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class CustomerDao {
    public void saveCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Object object = session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateCustomerById(Customer customer, int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "UPDATE Artist set name = :name, surname = :surname age = :age, gender = :gender "
                    + "WHERE id = :customerId";
            Query query = session.createQuery(hql);
            query.setParameter("name", customer.getName());
            query.setParameter("surname", customer.getSurname());
            query.setParameter("age", customer.getAge());
            query.setParameter("gender", customer.getGender());
            query.setParameter("customerId", id);
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

    public Customer getCustomerById(int id) {
        Transaction transaction = null;
        Customer customer = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM Customer C WHERE C.id = :customerId";
            Query query = session.createQuery(hql);
            query.setParameter("customerId", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                customer = (Customer) results.get(0);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return customer;
    }


    public List<Customer> getCustomers() {
        List<Customer> customers = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            customers = session.createQuery("from Customer", Customer.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void deleteCustomerById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                String hql = "DELETE FROM Customer " + "WHERE id = :customerId";
                Query query = session.createQuery(hql);
                query.setParameter("customerId", id);
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

    public void deleteCustomers() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Customer";
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