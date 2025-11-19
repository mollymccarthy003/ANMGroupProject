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

    /** Class type of the entity */
    private final Class<T> type;

    /** Logger for debugging and error messages */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /** Hibernate SessionFactory for opening sessions */
    private final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Constructs a GenericDao for the given entity type.
     *
     * @param type The class of the entity
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    // ----------------------------
    // CRUD operations
    // ----------------------------

    /**
     * Retrieves an entity by its ID.
     *
     * @param id The ID of the entity
     * @return The entity instance, or null if not found
     */
    public T getById(int id) {
        Session session = getSession();
        try {
            return session.get(type, id);
        } finally {
            session.close();
        }
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity The entity to persist
     * @return The generated ID of the entity, if retrievable
     */
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

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity with updated values
     */
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

    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity to delete
     */
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

    /**
     * Retrieves entities where a property matches exactly the given value.
     *
     * @param propertyName The property to filter by
     * @param value        The exact value to match
     * @return List of matching entities
     */
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

    /**
     * Retrieves entities where a property contains the given value.
     *
     * @param propertyName The property to filter by
     * @param value        The substring to match (like %value%)
     * @return List of matching entities
     */
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

    /**
     * Opens a new Hibernate session.
     *
     * @return A session from the SessionFactory
     */
    private Session getSession() {
        return sessionFactory.openSession();
    }
}
