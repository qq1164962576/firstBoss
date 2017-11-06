package bos_managerment_service;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.itheima.bos.domain.base.Customer;


/**  
 * ClassName:TestWebservice <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 9:18:51 AM <br/>       
 */
public class TestWebservice {

    @Test
    public void test() {
       List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/bos-crm/webservice/customerService/customer").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
       for (Customer customer : list) {
           System.out.println(customer);
    }
    }
}
  
