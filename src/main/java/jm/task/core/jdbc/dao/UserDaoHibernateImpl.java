package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY" +
                            " AUTO_INCREMENT, name VARCHAR(40), lastName VARCHAR(40), age tinyint)")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new User(name, lastName, (byte) age));
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (null != user) {
                session.delete(user);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users")
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }
    }
}