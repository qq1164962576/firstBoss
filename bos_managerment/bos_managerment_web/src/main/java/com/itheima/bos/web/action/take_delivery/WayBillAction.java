package com.itheima.bos.web.action.take_delivery;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import com.itheima.bos.web.action.base.BaseAction;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 10:36:28 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WayBillAction extends BaseAction<WayBill>{
    @Autowired
    private WayBillService wayBillService;
    @Action("waybillAction_save")
    public String save() throws IOException {
        String flag = "1";
        try {
            wayBillService.save(getModel());
        } catch (Exception e) {
            flag = "0"; 
            e.printStackTrace();  
            
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(flag);
        return NONE;
    }
}
  
