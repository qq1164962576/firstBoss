

import javax.xml.bind.annotation.XmlRootElement;

/**  
 * ClassName:User <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 10:48:53 AM <br/>       
 */
@XmlRootElement(name = "user")
public class User {
    private int  id ;
    private String name;
    private String password;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
    }
    
}
  
