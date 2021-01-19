package com.longtop.Util.Hibernate.Achieve;

import com.longtop.Util.Hibernate.HibernateDao;
import com.longtop.Util.Hibernate.Interface.HibernateStrategy;
import org.hibernate.*;
import org.hibernate.type.Type;
import java.sql.Clob;
import java.util.List;

public class HibernateHQLStrategy  implements HibernateStrategy {

    private static volatile HibernateDao dao;

    public HibernateHQLStrategy() {
        if (dao == null) {
           synchronized (HibernateHQLStrategy.class){
               if (dao==null){
                   this.dao = HibernateDao.getInstance();
               }
           }
        }
    }

    @Override
    public Session getSession() throws Exception {
        return dao.openSession();
    }

    public List<Object> get(String hql)throws Exception {
        List list = null;
        Session session = null;
        try {
            session = dao.openSession();
            Query query = session.createQuery(hql);
            list = query.list();
        } finally {
            session.close();
        }
        return list;
    }



    public List<Object> get(String hql, Type[] types, Object... values) throws Exception{
        List list = null;
        Session session = null;
        try {
            session = dao.openSession();
            Query query = session.createQuery(hql);
            query.setParameters(values, types);
            list = query.list();
        } catch (HibernateException e) {
            throw e;
        } finally {
            session.close();
        }
        return list;
    }

    public void add(Object object) throws Exception {
        Session session = null;
        Transaction transaction = null;
        try {
            session = dao.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public boolean update(String hql, Type[] types, Object... values) throws Exception {
        Session session = dao.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int num = session.createQuery(hql).setParameters(values, types).executeUpdate();
            transaction.commit();
            return num > 0;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

/*    public boolean update(String hqlStr, Type[] types, Object[] values) throws Exception {
        Session session = dao.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int num = session.createQuery(hqlStr).setParameters(values, types).executeUpdate();
            tx.commit();
            return num > 0;
        } catch (HibernateException e) {
            if (tx != null)
                try {
                    tx.rollback();
                }
                catch (HibernateException he) {
                }
            throw e;
        } finally {
            session.close();
        }
    }*/

    public Clob stringToClob(String stringXml) {
        Session session = null;
        Clob clob = null;
        try {
            session = dao.openSession();
            clob = session.getLobHelper().createClob(stringXml);
        } catch (HibernateException e) {
            throw e;
        } finally {
            session.close();
        }
        return clob;
    }
}
