package com.longtop.alsb.dao;
import com.longtop.Util.Hibernate.HibernateDao;
import com.longtop.Util.Hibernate.Interface.HibernateStrategy;
import org.hibernate.Session;
import org.hibernate.type.Type;
import java.sql.Clob;
import java.util.List;

public class HibernateContext {
    private HibernateStrategy hibernateStrategy;

    public HibernateContext(HibernateStrategy hibernateStrategy){ this.hibernateStrategy=hibernateStrategy; }

    public Session getSession()throws Exception{return this.hibernateStrategy.getSession();}

    public List<Object> get(String hqlStr) throws Exception { return hibernateStrategy.get(hqlStr); }

    public List<Object> get(String hqlStr,Type[] types, Object... values) throws Exception { return hibernateStrategy.get(hqlStr, types, values); }

    public void add(Object object) throws Exception { hibernateStrategy.add(object); }

    public boolean update(String hqlStr, Type[] types, Object... values) throws Exception { return hibernateStrategy.update(hqlStr, types,values); }

    public Clob stringToClob(String stringXml){ return hibernateStrategy.stringToClob(stringXml); }
}
