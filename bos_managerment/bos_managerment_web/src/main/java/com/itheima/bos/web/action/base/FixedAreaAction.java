package com.itheima.bos.web.action.base;  
/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:27:04 PM <br/>       
 */

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class FixedAreaAction extends BaseAction<FixedArea> {
    @Autowired
    private FixedAreaService fixedAreaService;
    private List<Long> customerIds;
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    @Action("fixedAreaAction_findAll")
    public String findAll() throws IOException{
        List<FixedArea> list = fixedAreaService.findAll();
        List2Json(list, new String[] {"subareas","couriers"});
        return NONE;
    }
    @Action(value=("fixedAreaAction_save"),results= {@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
    public String save() throws IOException {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }   
    @Action("fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findunassociatedcustomers")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        List2Json(list, null);
        return NONE;
    }
    @Action("fixedAreaAction_findcustomersassociated2fixedarea")
    public String findcustomersassociated2fixedarea() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findcustomersassociated2fixedarea")
                .query("fixedAreaId", getModel().getId())
                .getCollection(Customer.class);
        List2Json(list, null);
        return NONE;
    }
    @Action(value=("fixedAreaAction_assignCustomers2FixedArea"),results={@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
    public String assignCustomers2FixedArea() throws IOException {
                WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/assigncustomers2fixedarea")
                .query("fixedAreaId", getModel().getId())
                .query("ids", customerIds)
                .put(null);
        return SUCCESS;
    }
}
  
