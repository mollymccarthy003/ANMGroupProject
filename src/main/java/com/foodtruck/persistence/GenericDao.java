package com.foodtruck.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * A generic DAO for CRUD operations on any entity type.
 *
 * @param <T> The entity type
 */
public class GenericDao<T> {

    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    // ----------------------------
    // CRUD operations
    // ----------------------------

    public T getById(int id) {
        Session session = getSession();
        try {
            return session.get(type, id);
        } finally {
            session.close();
        }
    }

    public int insert(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(entity);
            tx.commit();

            int id = 0;
            try {
                var method = entity.getClass().getMethod("getId");
                id = (int) method.invoke(entity);
            } catch (Exception e) {
                logger.warn("Could not retrieve ID for entity of type {}", type.getSimpleName());
            }
            return id;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<T> getAll() {
        Session session = getSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            Root<T> root = criteria.from(type);
            criteria.select(root);
            return session.createQuery(criteria).getResultList();
        } finally {
            session.close();
        }
    }

    // ----------------------------
    // Property search helpers
    // ----------------------------

    public List<T> getByPropertyEqual(String propertyName, String value) {
        Session session = getSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            Root<T> root = criteria.from(type);
            criteria.select(root).where(builder.equal(root.get(propertyName), value));
            return session.createQuery(criteria).getResultList();
        } finally {
            session.close();
        }
    }

    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = getSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            Root<T> root = criteria.from(type);
            Expression<String> path = root.get(propertyName);
            criteria.select(root).where(builder.like(path, "%" + value + "%"));
            return session.createQuery(criteria).getResultList();
        } finally {
            session.close();
        }
    }

    // ----------------------------
    // Helper
    // ----------------------------
    private Session getSession() {
        return sessionFactory.openSession();
    }
}
