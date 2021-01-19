package com.longtop.Util.Hibernate.Interface;
import com.longtop.Util.Hibernate.HibernateDao;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.sql.Clob;
import java.util.List;

public interface HibernateStrategy {

    /**
     * 获取Session，个性化处理
     * @return
     * @throws Exception
     */
    Session getSession() throws Exception;

    /**
     * 新增
     * @param object
     */
    void add(Object object)throws Exception;

    /**
     * 修改
     * @param hqlStr
     * @param types
     * @param values
     * @return
     */
    boolean update(String hqlStr, Type[] types, Object... values)throws Exception;

    /**
     * 查询
     * @param sql
     * @return
     */
    List<Object> get(String sql) throws Exception;

    /**
     * 带参查询
     * @param sql
     * @param types
     * @param values
     * @return
     */
    List<Object> get(String sql, Type[] types, Object... values)throws Exception;

    /**
     * 转换 String转clob
     * @return
     */
    Clob stringToClob(String stringXml);
}
