package com.itheima.bos.fore.web.action.base;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.fore.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 11, 2017 7:09:06 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
    private Order model = new Order();
    
    @Override
    public Order getModel() {
          
        return model;
    }
    @Action(value="orderAction_add",results= {@Result(name="success",type="redirect",location="index.html")})
    public String createOrder() {
        WebClient.create("http://localhost:8080/bos_managerment_web/webservice/orderService/createorder")
        .type(MediaType.APPLICATION_JSON)
        .post(model);
        return SUCCESS;
    }
    
    
}
  
