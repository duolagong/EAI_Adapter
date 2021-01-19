package com.longtop.Util.PostMan;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JmsUtil {

    public  String SendMessage(String message, String targetUrl, String targetFactoryName, String targetName,int time) {
        boolean transacted = false;
        int acknowledgementMode = Session.AUTO_ACKNOWLEDGE;
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        properties.put(Context.PROVIDER_URL, targetUrl);
        try {
            Context context = new InitialContext(properties);
            Object obj = context.lookup(targetFactoryName);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) obj;
            obj = context.lookup(targetName);
            Queue queue = (Queue) obj;
            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();// 产生连接
            queueConnection.start();
            QueueSession queueSession = queueConnection.createQueueSession(transacted, acknowledgementMode);
            TextMessage textMessage = queueSession.createTextMessage();
            textMessage.clearBody();
            textMessage.setText(message);
            QueueSender queueSender = queueSession.createSender(queue);
            queueSender.setTimeToLive(time*1000);
            queueSender.send(textMessage);
            if (transacted) queueSession.commit();
            if (queueSender != null) queueSender.close();
            if (queueSession != null) queueSession.close();
            if (queueConnection != null) queueConnection.close();
            return "M0001发送成功";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "E9999发送失败" + ex.getMessage();
        }
    }
}
