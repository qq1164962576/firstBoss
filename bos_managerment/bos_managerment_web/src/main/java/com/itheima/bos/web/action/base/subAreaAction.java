package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**  
 * ClassName:subAreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:56:05 AM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class subAreaAction extends BaseAction<SubArea> {
    @Autowired
    private SubAreaService subAreaService;
    @Action(value="subareaAction_save",results={@Result(name="success",type="redirect",location="/pages/base/sub_area.html")})
    public String save() {
        subAreaService.save(getModel());
        return SUCCESS;
    }
    @Action("subareaAction_findAll")
    public String findAll() throws IOException{
        List<SubArea> list = subAreaService.findAll();
        List2Json(list, new String[] {"subareas","fixedArea"});
        return NONE;
    }
    @Action(value="subareaAction_pageQuery",results={@Result(name="success",type="redirect",location="/pages/base/sub_area.html")})
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<SubArea> page = subAreaService.pageQuery(pageable);
        Map2Json(page, null);
        return SUCCESS;
    }
}
  
