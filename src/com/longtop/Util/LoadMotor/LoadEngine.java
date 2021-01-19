package com.longtop.Util.LoadMotor;

import com.Eureka.EntranceServiceImpl;
import com.longtop.Util.Hibernate.Achieve.HibernateHQLStrategy;
import com.longtop.Util.Hibernate.Achieve.HibernateSQLStrategy;
import com.longtop.alsb.dao.HibernateContext;


public class LoadEngine {

    private static volatile HibernateContext sqlContext;
    private static volatile HibernateContext hqlContext;

    public static HibernateContext getDBContext(String Type) {
        if ("SQL".equals(Type)) {
            if (sqlContext == null) {
                synchronized (LoadEngine.class) {
                    if (sqlContext == null) {
                        sqlContext = new HibernateContext(new HibernateSQLStrategy());
                    }
                }
            }
            return sqlContext;
        } else {
            if (hqlContext == null) {
                synchronized (LoadEngine.class) {
                    if (hqlContext == null) {
                        hqlContext = new HibernateContext(new HibernateHQLStrategy());
                    }
                }
            }
            return hqlContext;
        }
    }
}
