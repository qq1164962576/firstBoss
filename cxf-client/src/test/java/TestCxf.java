import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

/**  
 * ClassName:TestCxf <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 12:17:18 PM <br/>       
 */
public class TestCxf {
    @Test
    public void test1() {
        User user =  new User();
        user.setId(1);
        user.setName("明");
        user.setPassword("123123");
        WebClient.create("http://localhost:8080/service-cxf-rs/Webservice/userService/user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_ATOM_XML).post(user);
    }
    @Test
    public void test2() {
        User user =  new User();
        user.setId(1);
        user.setName("玉");
        user.setPassword("123123");
        WebClient.create("http://localhost:8080/service-cxf-rs/Webservice/userService/user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_ATOM_XML).query("name", user.getName()).delete();
    }
    @Test
    public void test3() {
        User user =  new User();
        user.setId(1);
        user.setName("明");
        user.setPassword("123123");
        WebClient.create("http://localhost:8080/service-cxf-rs/Webservice/userService/user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_ATOM_XML).put(user);
    }
    @Test
    public void test4() {
        User user =  new User();
        user = WebClient.create("http://localhost:8080/service-cxf-rs/Webservice/userService/user/"+2).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_ATOM_XML).get(User.class);
        System.out.println(user);
    }
}
  
