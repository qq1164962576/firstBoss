package com.itheima.bos.fore.web.action.base;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.bos.fore.domain.Customer;
import com.ithiema.utils.MailUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAciton <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 3:59:56 PM <br/>       
 */

@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
        /////CustomerAction
public class CustomerAcitons2 extends ActionSupport implements ModelDriven<Customer> {
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = -4199319003358187727L;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private Customer model = new Customer();
    
    private String checkcode;
    @Override
    public Customer getModel() {
        return model;
    }
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    //用户登录
    @Action(value="customerAction_login",results= {@Result(name="success",type="redirect",location="index.html"),
            @Result(name="error",type="redirect",location="login.html"),
            @Result(name="unactived",type="redirect",location="active-error.html")})
    public String login() {//
        
        String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode"); 
        //检测验证码
        if(checkcode!=null&&checkcode.equals(validatecode)&&model.getTelephone()!=null&&model.getPassword()!=null) {

            Customer customer = WebClient.create("http://localhost:8180/bos-crm/webservice/customerService/login")
                .accept(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .query("password", model.getPassword())
                .get(Customer.class);
            if(customer!=null && customer.getType()!=null&&customer.getType()==1) {
                ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
                return SUCCESS;
            }
            return "unactived";
        }     
        return ERROR;
    }
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Action(value="customerAction_sendSMS",results= {@Result(name="success",type="json")})
    public String sendSMS() {
        //发送验证码之前 查看该手机号码是否注册  如果注册,告诉客户该用户已注册;
        Customer customer = WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findByTelephone")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .get(Customer.class);
        Map<String, String> map = new HashMap<>();   
        if(customer==null) {
            map.put("result","ok");
        //生成随机码
        String code = RandomStringUtils.randomNumeric(6);
        //发送验证码
        final String msg = "尊敬的客户你好，您本次获取的验证码为：" + code;
        jmsTemplate.send("sms", new MessageCreator() {
            
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("msg", msg);
                mapMessage.setString("telephone", model.getTelephone());
                return mapMessage;
            }
        });
        //将随机验证码放入session中保存起来
        ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), code);
        }else {
            map.put("result", "exist");
        }
        //用值栈将map放回页面
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }
    //用户注册
    @Action(value="customerAction_regist",results={@Result(name="success",type="redirect",location="/signup-success.html"),
            @Result(name="error",type="redirect",location="/signup-fail.html")})
    public String regist() {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
        /*if( StringUtils.isNotEmpty(code) && code.equals(checkcode) ) {*/
                //如果验证码正确则注册
                
                WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/regist")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(model);
                String activecode = RandomStringUtils.randomNumeric(32);
                redisTemplate.opsForValue().set(model.getTelephone(), activecode, 1, TimeUnit.DAYS);
                //发送激活邮件
                String emailBod = "你好,用户注册成功,请点击<a href=http://localhost:8280/bos-fore/customerAction_active.action?telephone="+model.getTelephone()+"&checkcode="+activecode+">激活账号<a/> 完成激活";
                MailUtils.sendMail(model.getEmail(), "激活邮件", emailBod);
                return SUCCESS;
        //}
        //return ERROR;
    }
    //用户激活
    @Action(value="customerAction_active",results={@Result(name="success",type="redirect",location="/active-success.html"),
            @Result(name="error",type="redirect",location="/active-error.html"),
            @Result(name="actived",type="redirect",location="/active-fail.html")})
    public String active() {
        String telephone = model.getTelephone();
        //如果激活码正确  先查看是否激活  若未激活 前往crm请求激活  
        if(StringUtils.isNotEmpty(checkcode)&&checkcode.equals(redisTemplate.opsForValue().get(telephone))) {
                Customer customer = WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findByTelephone")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .query("telephone", model.getTelephone())
                .get(Customer.class);
                
                
                if (customer!=null) {
                  
                    if(customer.getType()!=null&&customer.getType()==1) {  
                        return "actived";//如果用户已被激活
                    }
                    //激活
                    WebClient
                    .create("http://localhost:8180/bos-crm/webservice/customerService/active")
                    .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .put(null);
                    return SUCCESS;
                }    
        }    
        return ERROR;
    }
}
  
