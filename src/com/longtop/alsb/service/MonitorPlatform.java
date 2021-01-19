package com.longtop.alsb.service;

import com.longtop.Util.LoadMotor.LoadDBSource;
import com.longtop.Util.HelpCentre.StringUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonitorPlatform {

    private static String url = null;
    private static String usernameNet = null;
    private static String passwordNet = null;

    /**
     * 监控平台入口
     *
     * @param mapText
     * @return
     */
    public static String getStringMap(String mapText) {
        Map map = new HashMap();
        String Sql = "";
        mapText = mapText.substring(1, mapText.length() - 1);
        String[] text = mapText.split(",");
        for (int i = 0; i < text.length; i++) {
            String infor = text[i];
            String[] inforString = infor.split("=");
            map.put(inforString[0].replaceAll(" ", ""), inforString[1]);
        }
        String findType = map.get("FindType").toString();
        if (findType.equals("1")) {
            Sql = "select * " + getInitialInforSql(map);
        } else if (findType.equals("2")) {
            Sql = getSynInforSql(map);
        } else if (findType.equals("3")) {
            Sql = "select count(1) " + getInitialInforSql(map);
        }
        return findType + "|" + Sql;
    }

    /**
     * 根据监控平台请求的参数组织查询条件
     *
     * @param findCode
     * @return
     */
    public static String getBankCode(String findCode) {
        String addSql = "";
        if (findCode.equals("M")) {
            addSql = "and BankFinalCode like 'M%'";
        } else if (findCode.equals("E")) {
            addSql = "and (BankFinalCode like 'E%' or (BankFinalCode is null and ErpCode like 'E%'))";
        } else {
            addSql = "and ((BankFinalCode not like 'E%' and BankFinalCode not like 'M%')or ((BankFinalCode is null) and (ERPCODE not like 'E%' or ERPCODE is null)))";
        }
        return addSql;
    }

    public static String getPayType(String PayType) {
        String addSql = "";
        if (PayType.equals("0")) {
            addSql = "and agentId in('W01','Z01')";
        } else {
            addSql = "and agentId in('102','103','104','105','301')";
        }
        return addSql;
    }


    public static String getVersion(String versionGroup) {
        String[] versionText = versionGroup.split("\\|");
        String version = "";
        for (int i = 0; i < versionText.length; i++) {
            String infor = "";
            infor = versionText[i];
            if (i < 1) {
                version = "version='" + infor + "'";
            } else {
                version = version + " or " + "version='" + infor + "'";
            }
        }
        return version;
    }

    /**
     * 组织给网银查询初始交易信息的sql
     *
     * @param map
     * @return
     */
    public static String getInitialInforSql(Map map) {
        String sql = "from (select ROWNUM RN,TRANXML,BANKFINALCODE,ERPCODE,VERSION," +
                "to_char(to_date(substr(s.txmoment, 0, 8), 'yyyymmdd'),'yyyy-mm-dd') as TXMOMENT," +
                "ORDERNUM,PERSONFLAG,TXAMOUNT,SUBBRANCHID,AGENTID from bnk_pay_infor s where "
                + " to_date(substr(s.txmoment, 0, 8), 'yyyymmdd') >= to_date('"
                + map.get("txmoment_start")
                + "', 'yyyy-mm-dd') and to_date(substr(s.txmoment, 0, 8), 'yyyymmdd') <= to_date('"
                + map.get("txmoment_end") + "', 'yyyy-mm-dd')";
        if (StringUtil.isEmpty(map.get("version"))) {
            sql += " and (" + getVersion(map.get("version").toString()) + ")";
        }
        if (StringUtil.isEmpty(map.get("PayType"))) {
            sql += getPayType(map.get("PayType").toString());
        }
        if (StringUtil.isEmpty(map.get("agentId"))) {
            sql += " and agentId = '" + map.get("agentId") + "'";
        }
        if (StringUtil.isEmpty(map.get("orderNum"))) {
            sql += " and orderNum = '" + map.get("orderNum") + "'";
        }
        if (StringUtil.isEmpty(map.get("personFlag"))) {
            sql += " and personFlag = '" + map.get("personFlag") + "'";
        }
        if (StringUtil.isEmpty(map.get("state"))) {
            sql += getBankCode(map.get("state").toString());
//			sql += " and bankFinalCode = '"+ map.get("bankFinalCode")+"'";
        }
        if (StringUtil.isEmpty(map.get("subbranchid"))) {
            sql += " and subbranchid = '" + map.get("subbranchid") + "'";
        }
        if (StringUtil.isEmpty(map.get("txAmount_start"))) {
            sql += " and TxAmount >= " + map.get("txAmount_start");
        }
        if (StringUtil.isEmpty(map.get("txAmount_end"))) {
            sql += " and TxAmount <= " + map.get("txAmount_end");
        }
        if (StringUtil.isEmpty(map.get("startIndex")) && StringUtil.isEmpty(map.get("endIndex"))) {
            int startIndex = Integer.parseInt((String) map.get("startIndex"));
            int endIndex = Integer.parseInt((String) map.get("endIndex"));
            sql += " )WHERE RN BETWEEN " + startIndex + " AND " + endIndex;
        } else {
            sql += " )";
        }
        return sql;
    }

    /**
     * 组织给网银查询同步状态的SQL
     *
     * @param map
     * @return
     */
    public static String getSynInforSql(Map map) {
        String sql = "select ERPCODE,ERPMSG,ERPXML,to_char(ERPDATE,'yyyy/MM/dd HH24:mi:ss')as ERPDATE," +
                "ATOMCODE,ATOMMSG,ATOMXML,to_char(ATOMDATE,'yyyy/MM/dd HH24:mi:ss')as ATOMDATE," +
                "BANKSYNCODE,BANKSYNMSG,BANKSYNXML,to_char(BANKSYNDATE,'yyyy/MM/dd HH24:mi:ss')as BANKSYNDATE," +
                "BANKFINALCODE,BANKFINALMSG,BANKFINALXML,to_char(BANKFINALDATE,'yyyy/MM/dd HH24:mi:ss')as BANKFINALDATE," +
                "ORDERNUM,VERSION,AGENTID,TRANXML,to_char(TRANDATE,'yyyy/MM/dd HH24:mi:ss')as TRANDATE,PERSONFLAG,ERPSYNCODE," +
                "ERPSYNMSG,ERPSYNXML,to_char(ERPSYNDATE,'yyyy/MM/dd HH24:mi:ss')as ERPSYNDATE" + " " +
                "FROM bnk_pay_infor where "
                + "version = '" + map.get("version") + "'";
        if (StringUtil.isEmpty(map.get("agentId"))) {
            sql += " and agentId = '" + map.get("agentId") + "'";
        }
        if (StringUtil.isEmpty(map.get("orderNum"))) {
            sql += " and orderNum = '" + map.get("orderNum") + "'";
        }
        if (StringUtil.isEmpty(map.get("personFlag"))) {
            sql += " and personFlag = '" + map.get("personFlag") + "'";
        }
        return sql;
    }

    public static String getSuperNetClob(String sqlMap) {
        List returnNet = new ArrayList();
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String atomTime = "";
        oracle.sql.CLOB clob = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            LoadDBSource.loadFile("database.properties");
            url = LoadDBSource.getPropertyValue("urlNet");
            usernameNet = LoadDBSource.getPropertyValue("userNet");
            passwordNet = LoadDBSource.getPropertyValue("passwordNet");
            conn = DriverManager.getConnection(url, usernameNet, passwordNet);
            System.out.println("OSB数据库连接建立成功...");
            stmt = conn.createStatement();
            String sql = getStringMap(sqlMap);
            System.out.println("组织成SQL：" + sql);
            String sqlString[] = sql.split("\\|");
            String findType = sqlString[0];
            sql = sqlString[1];
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map rowData = new HashMap();
                if (findType.equals("1")) {
                    for (int i = 1; i <= columnCount; i++) {
                        if (i == 2) {
                            clob = (oracle.sql.CLOB) rs.getClob("TRANXML");
                            String value = clob.getSubString(1, (int) clob.length());
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("TRANXML", value);
                        } else {
                            rowData.put(md.getColumnName(i), rs.getObject(i));
                        }
                    }
                } else {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = "null";
                        if (i == 3) {
                            clob = (oracle.sql.CLOB) rs.getClob("ERPXML");
                            if (StringUtil.isEmpty(clob)) {
                                value = clob.getSubString(1, (int) clob.length());
                            }
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("ERPXML", value);
                        } else if (i == 7) {
                            clob = (oracle.sql.CLOB) rs.getClob("ATOMXML");
                            if (StringUtil.isEmpty(clob)) {
                                value = clob.getSubString(1, (int) clob.length());
                            }
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("ATOMXML", value);
                        } else if (i == 11) {
                            clob = (oracle.sql.CLOB) rs.getClob("BANKSYNXML");
                            if (StringUtil.isEmpty(clob)) {
                                value = clob.getSubString(1, (int) clob.length());
                            }
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("BANKSYNXML", value);
                        } else if (i == 15) {
                            clob = (oracle.sql.CLOB) rs.getClob("BANKFINALXML");
                            if (StringUtil.isEmpty(clob)) {
                                value = clob.getSubString(1, (int) clob.length());
                            }
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("BANKFINALXML", value);
                        } else if (i == 20) {
                            clob = (oracle.sql.CLOB) rs.getClob("TRANXML");
                            if (StringUtil.isEmpty(clob)) {
                                value = clob.getSubString(1, (int) clob.length());
                            }
                            value = value.replaceAll("\n", "").replaceAll(" ", "");
                            rowData.put("TRANXML", value);
                        } else {
                            rowData.put(md.getColumnName(i), rs.getObject(i));
                        }
                    }
                }
                returnNet.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return returnNet.toString();
    }


    public static List<Map<String, Object>> Play(String xml) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String[] xmlGrop = xml.split("},");
        String[] listGropString;
        String tranXml = "";
        for (int i = 0; i < xmlGrop.length; i++) {
            List listGrop;
            if (i == 0) {
                String xmlString = xmlGrop[0].substring(2);
                tranXml = getTRANXML(xmlString);
                String newXml = delTRANXML(xmlString);
                listGropString = newXml.split(",");
            } else if (i == xmlGrop.length - 1) {
                String xmlString = xmlGrop[i].substring(0, (xmlGrop[i].length()) - 2);
                tranXml = getTRANXML(xmlString);
                String newXml = delTRANXML(xmlString);
                newXml = newXml.substring(2);
                listGropString = newXml.split(",");
            } else {
                tranXml = getTRANXML(xmlGrop[i]);
                String newXml = delTRANXML(xmlGrop[i]);
                newXml = newXml.substring(2);
                listGropString = newXml.split(",");
            }
            Map map = new HashMap();
            map.put("TRANXML", tranXml.substring(tranXml.indexOf("=") + 1));
            int yN = listGropString.length;
            for (int y = 0; y < yN; y++) {
                String node = listGropString[y];
                map.put(node.substring(0, node.indexOf("=")), node.substring(node.indexOf("=") + 1));
            }
            list.add(map);
        }

        return list;
    }

    /**
     * 正则表达式提取
     *
     * @param xml
     * @return
     */
    public static String getTRANXML(String xml) {
        Pattern p = Pattern.compile("TRANXML=.*>,");
        Matcher m = p.matcher(xml);
        String tranXml = "";
        if (m.find()) {
            tranXml = m.group();
            tranXml = tranXml.substring(0, tranXml.length() - 1);
        } else {
            p = Pattern.compile("TRANXML=.*>");
            m = p.matcher(xml);
            m.find();
            tranXml = m.group();
        }
        return tranXml;
    }

    public static String delTRANXML(String xml) {
        return xml.replaceAll("TRANXML=.*>,", "");
    }

}
