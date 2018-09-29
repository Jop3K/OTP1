package dataAccessObjects;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.EventModel;
import models.GoogleAccount;
import models.Palkka;
import models.Palkkalisa;
import models.User;
import models.WorkProfile;

import org.hibernate.criterion.Restrictions;;

public class DataAccessObject {

	Session session;
	Transaction transaction;

	public DataAccessObject() {

	}

	private static SessionFactory getSessionFactory() {
        Configuration con = new Configuration().configure().addAnnotatedClass(User.class);
        con.addAnnotatedClass(GoogleAccount.class);
        con.addAnnotatedClass(WorkProfile.class);
        con.addAnnotatedClass(Palkka.class);
        con.addAnnotatedClass(Palkkalisa.class);
        con.addAnnotatedClass(EventModel.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        SessionFactory sf = con.buildSessionFactory(reg);

        return sf;
	}
	 public Session openCurrentSession(){
		  session = getSessionFactory().openSession();
		  return session;

	}
	 public void closeCurrentSession(){
		   session.close();
	 }

	 public void closeCurrentSessionWithTransaction() {
		 transaction.commit();
		 session.close();
	 }
	//Transactionia käytetään, kun tietokantaa muutetaan. Haut sieltä suoritetaan normaalilla sessionilla
	 public Session openCurrentSessionWithTransaction() {
		 session = getSessionFactory().openSession();
		 transaction = session.beginTransaction();

		 return session;
	 }

	 public Session getCurrentSession() {
		 return this.session;
	 }
	 public void setCurrentSession(Session session) {
		 this.session = session;
	 }

	public Transaction getCurrentTransaction() {
		return transaction;
	}

	public void setCurrentTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
