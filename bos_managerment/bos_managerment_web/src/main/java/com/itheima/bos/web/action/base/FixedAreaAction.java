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
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.FixedAreaService;

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class FixedAreaAction extends BaseAction<FixedArea> {
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = -145670947751517733L;
    @Autowired
    private FixedAreaService fixedAreaService;
    private List<Long> customerIds;
    private List<Long> subAreaIds;
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setSubAreaIds(List<Long> subAreaIds) {
        this.subAreaIds = subAreaIds;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    //查找所有定区
    @Action("fixedAreaAction_findAll")
    public String findAll() throws IOException{
        List<FixedArea> list = fixedAreaService.findAll();
        List2Json(list, new String[] {"subareas","couriers"});
        return NONE;
    }
    //保存定区
    @Action(value=("fixedAreaAction_save"),results={@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String save() throws IOException {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }   
    //关联快递员
    @Action(value=("fixedAreaAction_associationCourierToFixedArea"),results= {@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String associationCourierToFixedArea() throws IOException {
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        return SUCCESS;
    }
    //查找未关联客户
    @Action("fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findunassociatedcustomers")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        List2Json(list, null);
        return NONE;
    }
    
    //根据id查找已关联客户
    @Action("fixedAreaAction_findcustomersassociated2fixedarea")
    public String findcustomersassociated2fixedarea() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/findcustomersassociated2fixedarea")
                .query("fixedAreaId", getModel().getId())
                .getCollection(Customer.class);
        List2Json(list, null);
        return NONE;
    }
    
    //关联客户
    @Action(value=("fixedAreaAction_assignCustomers2FixedArea"),results={@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
    public String assignCustomers2FixedArea() throws IOException {
                WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/assigncustomers2fixedarea")
                .query("fixedAreaId", getModel().getId())
                .query("ids", customerIds)
                .put(null);
        return SUCCESS;
    }
    //查找所有已关联的客户
    @Action("fixedAreaAction_assignAllCustomers2FixedArea")
    public String assignAllCustomers2FixedArea() throws IOException {
                List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/bos-crm/webservice/customerService/customer")
                .accept(MediaType.APPLICATION_XML)
                .getCollection(Customer.class);
                List2Json(list, null);
        return NONE;
    }
    //查找没有关联的分区fixedAreaAction_findUnAssociatedsubArea
    @Action("fixedAreaAction_findUnAssociatedsubArea")
    public String findUnAssociatedsubArea() throws IOException {
        List<SubArea> list = fixedAreaService.findUnAssociatedSubArea();
        List2Json(list, new String[] {"fixedArea","area"});
        return NONE;
    }
    //查找已关联的分区fixedAreaAction_findsubareasassociated2fixedarea
    @Action("fixedAreaAction_findsubareasassociated2fixedarea")
    public String findSubAreasassociated2fixedArea() throws IOException {
        List<SubArea> list = fixedAreaService.findByFixedAreaIdAssociatedSubArea(getModel());
        List2Json(list, new String[] {"fixedArea","area"});
        return NONE;
    }
    //关联分区
    @Action(value= ("fixedAreaAction_assignSubArea2FixedArea"),results={@Result(name="success",type="redirect",location="/pages/base/fixed_area.html")})
    public String assignSubArea2FixedArea() throws IOException {
        fixedAreaService.assignSubArea2FixedArea(getModel().getId(),subAreaIds);    
        return SUCCESS;
    }
}
  
