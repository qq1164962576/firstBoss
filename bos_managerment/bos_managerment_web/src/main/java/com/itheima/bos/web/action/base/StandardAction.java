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
import com.itheima.bos.service.base.StandardService;
import net.sf.json.JSONArray;
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends BaseAction<Standard> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Standard model = new Standard();
	@Autowired
	private StandardService standardService;
	@Override
	public Standard getModel() {
		return model;
	}
	@Action(value="standardAction_save",results={@Result(name="success",type="redirect",location="pages/base/standard.html")})
	public String save(){
	    standardService.save(model);
	    return SUCCESS;
	}
	@Action("standardAction_findAll")
	public String findAll() throws IOException{
		List<Standard> list = standardService.findAll();
		return NONE;
	}
	@Action("standardAction_pageQuery")
	public String pageQuery() throws IOException{
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Standard> page = standardService.pageQuery(pageable);
		Map2Json(page, null);
		return NONE;
	}
}
