package dataAccessObjects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.EventModel;
import models.ExtraPay;
import models.User;
import models.WeekDays;
import models.WorkProfile;

public class DataAccessObject {

    private static Session session;
    private static Transaction transaction;
    private static SessionFactory sessionFactory;

    public DataAccessObject() {
        Configuration con = new Configuration().configure().addAnnotatedClass(User.class)
                .addAnnotatedClass(WorkProfile.class)
                .addAnnotatedClass(ExtraPay.class)
                .addAnnotatedClass(EventModel.class)
                .addAnnotatedClass(WeekDays.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        sessionFactory = con.buildSessionFactory(reg);
    }

    private static SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public static Session openCurrentSession() {
        session = getSessionFactory().openSession();
        
        return session;
    }

    public static void closeCurrentSession() {
        session.close();
    }

    public static void closeCurrentSessionWithTransaction() {
        transaction.commit();
        session.close();
    }
    //Transactionia käytetään, kun tietokantaa muutetaan. Haut sieltä suoritetaan normaalilla sessionilla

    public static Session openCurrentSessionWithTransaction() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();

        return session;
    }

    public static Session getCurrentSession() {
        return session;
    }

    public static void setCurrentSession(Session s) {
        session = s;
    }

    public static Transaction getCurrentTransaction() {
        return transaction;
    }

    public static void setCurrentTransaction(Transaction t) {
        transaction = t;
    }

}
