package com.longtop.Util.Hibernate;

import com.Eureka.EntranceServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateDao {
    public SessionFactory sessionFactory;
    public static HibernateDao instance;
    public static String hibernateFile="hibernateNew.cfg.xml";

    private HibernateDao() {
        Configuration configuration = new Configuration().configure(hibernateFile);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //EntranceServiceImpl.getEntrance().initStart(10,false); //本地运行需要注释掉
        new EaiSign();
    }

    public static HibernateDao getInstance() {
        if (instance == null) {
            instance = new HibernateDao();
        }
        return instance;
    }

    public void destroy() {
        sessionFactory.close();
        instance = null;
    }

    // 获得session => 获得全新session
    public  Session openSession() {
        return sessionFactory.openSession();
    }

    // 获得session => 获得与线程绑定的session
    public  Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
