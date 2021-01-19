package com.longtop.Util.Hibernate.Achieve;

import com.longtop.Util.Hibernate.HibernateDao;
import com.longtop.Util.Hibernate.Interface.HibernateStrategy;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.sql.Clob;
import java.util.List;

public class HibernateSQLStrategy implements HibernateStrategy {

    private static volatile HibernateDao dao;

    public HibernateSQLStrategy() {
        if (dao == null) {
            synchronized (HibernateSQLStrategy.class){
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

    @Override
    public void add(Object object) {

    }

    @Override
    public boolean update(String hqlStr, Type[] types, Object... values) {
        return false;
    }

    @Override
    public List<Object> get(String sql)throws Exception {
        Session session = null;
        List list = null;
        try {
            session = dao.openSession();
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            list = sqlQuery.list();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List<Object> get(String sql, Type[] types, Object... values) {
        return null;
    }

    @Override
    public Clob stringToClob(String stringXml) {
        return null;
    }
}
