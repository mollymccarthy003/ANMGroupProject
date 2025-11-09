package persistence;

import entities.Truck;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

public class TruckData {
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get truck by id
     *
     * @param id - trucks id
     * @return truck
     */
    public Truck getById(int id) {
        Session session = sessionFactory.openSession();
        Truck truck = session.get(Truck.class, id);
        session.close();
        return truck;
    }
}