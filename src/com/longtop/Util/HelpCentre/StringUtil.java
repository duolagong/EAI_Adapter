package com.longtop.Util.HelpCentre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class StringUtil {

    /**
     * Oracle-Clob转String
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob clob) throws SQLException, IOException {
        Reader is=null;
        String clobStr=null;
        try {
            is=clob.getCharacterStream();
            BufferedReader bufferedReader = new BufferedReader(is);
            StringBuffer stringBuffer = new StringBuffer();
            String stringRead=bufferedReader.readLine();
            while (stringRead!=null){
                stringBuffer.append(stringRead);
                stringRead=bufferedReader.readLine();
            }
            clobStr = stringBuffer.toString();
        } catch (SQLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return clobStr;
    }

    /**
     * 校验是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        return obj != null && obj.toString().length() != 0;
    }
}
