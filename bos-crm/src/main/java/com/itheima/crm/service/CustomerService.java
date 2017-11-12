package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 8:29:49 AM <br/>       
 */                        
public interface CustomerService {
    //保存customer
    @POST
    @Path("/customer")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void save(Customer customer);
    //获得所有customer
    @GET
    @Path("/customer")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findAll();
    //所有未关联的客户
    @GET
    @Path("/findunassociatedcustomers")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findUnAssociatedCustomers();
    //根据Id查找已关联的客户
    @GET
    @Path("/findcustomersassociated2fixedarea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String FixedAreaId);
    //根据定区id 和customerid 关联
    @PUT
    @Path("/assigncustomers2fixedarea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void assignCustomers2Fixedarea(@QueryParam("fixedAreaId") String fixedAreaId ,@QueryParam("ids")List<Long> ids);

    //注册客户
    @POST
    @Path("/regist")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void regist(Customer customer);
    
    //登录
    @GET
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Customer login(@QueryParam("telephone") String telephone,@QueryParam("password") String password);
    @GET
    @Path("/findByTelephone")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Customer findByTelephone(@QueryParam("telephone") String telephone);
    
    //激活
    @PUT
    @Path("/active")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void active(@QueryParam("telephone") String telephone);
       
    @GET
    @Path("/findByfixedAreaId")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Long findfixedAreaId(@QueryParam("address") String sendAdrress);
    
}
  
