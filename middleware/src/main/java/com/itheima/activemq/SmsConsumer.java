package com.itheima.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**  
 * ClassName:SmsConsumer <br/>  
 * Function:  <br/>  
 * Date:     Nov 12, 2017 8:13:06 PM <br/>       
 */
@Component
public class SmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
          MapMessage mapMessage =  (MapMessage) message;
          
        try {
            String telephone = mapMessage.getString("telephone");
            String msg = mapMessage.getString("msg");
            System.out.println(telephone+":"+msg);
        } catch (JMSException e) {
            
            e.printStackTrace();  
        }
        
    }
    
}
  
