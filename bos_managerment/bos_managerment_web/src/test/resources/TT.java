import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.itheima.bos.domain.base.Customer;

/**  
 * ClassName:TT <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 9:41:48 AM <br/>       
 */
public class TT {
    @Test
    public void test() {
       List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/bos-crm/webservice/customerService/customer").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
       for (Customer customer : list) {
           System.out.println(customer);
    }
    }

}
  
