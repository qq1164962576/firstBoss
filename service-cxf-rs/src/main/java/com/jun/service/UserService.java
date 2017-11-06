package com.jun.service;  
/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 10:51:29 AM <br/>       
 */

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jun.domain.User;

public interface UserService {
    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON})
    public void save(User user);
    
    @DELETE
    @Path("/user")
    @Consumes({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
    public void delete(@QueryParam("name") String name);
    
    @PUT
    @Path("/user")
    @Consumes({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
    public void update(User user);
    
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
    public List<User> findAll();
    
    @GET
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_ATOM_XML,MediaType.APPLICATION_JSON})
    public User findById(@PathParam("id") int id);
}
  
