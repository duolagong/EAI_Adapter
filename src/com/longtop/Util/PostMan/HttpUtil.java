package com.longtop.Util.PostMan;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    /**
     * http 发送方法
     * json to json
     * @param url
     * @param sendData
     * @param time
     * @return
     */
    public String httpPost(String url, String sendData,int time)throws Exception{
        String returnMsg = "";
        HttpURLConnection urlConnection = null;
        try {
            URL aURL = new URL(url);
            urlConnection = (HttpURLConnection) aURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(time*1000);
            urlConnection.setReadTimeout(time*1000);
            urlConnection.setRequestProperty("Content-Type", "application/json;");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
            urlConnection.getOutputStream().write(sendData.getBytes("UTF-8"));
            urlConnection.getResponseCode();
            int resCode = urlConnection.getResponseCode();
            if(resCode==200){
                InputStream inputStream=urlConnection.getInputStream();
                byte[] data=new byte[1024];
                StringBuffer stringBuffer=new StringBuffer();
                int length=0;
                while ((length=inputStream.read(data))!=-1){
                    String s=new String(data, 0,length);
                    stringBuffer.append(s);
                }
                returnMsg=stringBuffer.toString();
                inputStream.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                    urlConnection = null;
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        return returnMsg;
    }

    public String httpGet(){
        return null;
    }
}
