package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.jasperreports.web.actions.SaveAction;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction2 extends ActionSupport implements ModelDriven<Standard> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Standard model = new Standard();
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	@Autowired
	private StandardService standardService;
	@Override
	public Standard getModel() {
		return model;
	}
	/*@Action("standardAction_findAll")
	public String findAll(){
	Page<Standard> page = standardService.findAll(standard);
	
	}*/
	@Action("standardAction_findAll")
	public String findAll() throws IOException{
		List<Standard> list = standardService.findAll();
		String thestandards = JSONArray.fromObject(list).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(thestandards);
		return NONE;
	}
	@Action(value="standardAction_save",results={@Result(name="success",type="redirect",location="pages/base/standard.html")})
	public String save(){
		standardService.save(model);
		return SUCCESS;
	}
	@Action("standardAction_pageQuery")
	public String pageQuery() throws IOException{
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Standard> page = standardService.pageQuery(pageable);
		//封装数据
		//1.总数据条数
		long total = page.getTotalElements();
		//2.Standard 的 list集合
		List<Standard> list = page.getContent();
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
	
		//把对象转成json数据  
		String json = JSONObject.fromObject(map).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		return NONE;
	}
}
