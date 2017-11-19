package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Select;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.take_delivery.OrderService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 2:44:29 PM <br/>       
 */
@Namespace("/") 
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
    
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 5429673556450389607L;
    @Autowired
    private OrderService orderService;
    
    @Action(value="orderAction_findOrderByNum")
    public String  findOrderByNum() throws IOException {
        Order order = orderService.findOrderByNum(getModel().getOrderNum());
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[] {"sendArea","recArea","workBills","courier","wayBill"});
        String json = JSONArray.fromObject(order, config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        
        /*Map<String,Object> map = new HashMap<>();
        map.put("order", order); //懒加载导致不能正常实现功能
        ServletActionContext.getContext().getValueStack().push(map);*/
        return NONE;
    }
    
}
  
