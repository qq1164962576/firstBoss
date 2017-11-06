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
    @POST
    @Path("/customer")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void save(Customer customer);
    
    @GET
    @Path("/customer")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findAll();
    
    @GET
    @Path("/findunassociatedcustomers")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findUnAssociatedCustomers();
    
    @GET
    @Path("/findcustomersassociated2fixedarea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String FixedAreaId);
    
    @PUT
    @Path("/assigncustomers2fixedarea")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public void assignCustomers2Fixedarea(@QueryParam("fixedAreaId") String fixedAreaId ,@QueryParam("ids")List<Long> ids);
}
  
