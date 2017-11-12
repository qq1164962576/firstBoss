package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.procedure.internal.Util.ResultClassesResolutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 9:54:16 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class TakeTimeAction extends BaseAction<TakeTime> {
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = -5445692012921386542L;
    @Autowired
    private TakeTimeService takeTimeService;

    @Action("takeTimeAction_findAll")
    public String findAll() throws IOException {
        
        List<TakeTime> list = takeTimeService.findAll();
        List2Json(list, null);
        return NONE;
    }
    @Action(value="takeTimeAction_save",results={@Result(name="success",type="redirect",location="/pages/base/take_time.html")})
    public String save() {
        takeTimeService.save(getModel());
        return SUCCESS;
    }
}
  
