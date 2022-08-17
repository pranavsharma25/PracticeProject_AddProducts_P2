package com.example;


import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductShopDAO {

	public void addProduct(String name, String color, String price) {
		try {
			Configuration configuration = new Configuration();
			SessionFactory sessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Products products = new Products();
			products.setName(name);
			products.setColor(color);
			products.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
			session.save(products);
			transaction.commit();
			session.close();
		} catch(HibernateException e) {
			System.out.println(e.getMessage());
			System.out.println("error");
		}
	}
}
