package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class CourierAction2 extends ActionSupport implements ModelDriven<Courier> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2925375582327233286L;
	private Courier model;
	private int page;
	private int rows;
	private String IDS;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setIDS(String IDS) {
		this.IDS = IDS;
	}
	@Autowired
	private CourierService courierService;
	@Override
	public Courier getModel() {
		if(model==null){
			model = new Courier();
		}
		return model;
	}
	@Action(value="courierAction_save",results={@Result(name="success",type="redirect",location="pages/base/courier.html")})
	public String save(){													
		courierService.save(model);
		return SUCCESS;
	}
	@Action(value="courierAction_deleteById",results={@Result(name="success",type="redirect",location="pages/base/courier.html")})
	public String deleteById(){
		System.out.println(IDS);
		if(!StringUtils.isEmpty(IDS)){
		String[] ids = IDS.split(",");
			courierService.deleteById(ids);
		}
		return SUCCESS;
	}
	@Action(value="courierAction_declineById",results={@Result(name="success",type="redirect",location="pages/base/courier.html")})
	public String declineById(){
		System.out.println(IDS);
		if(!StringUtils.isEmpty(IDS)){
		String[] ids = IDS.split(",");
			courierService.declineById(ids);
		}
		return SUCCESS;
	}
	@Action("courierAciton_pageQuery")
	public String pageQuery() throws IOException{
	    Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String courierNum = model.getCourierNum();
                String type = model.getType();
                String company = model.getCompany();
                Standard standard = model.getStandard();
                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if(StringUtils.isNotEmpty(type)) {
                    Predicate p2 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p2);
                }
                if(StringUtils.isNotEmpty(company)) {
                    Predicate p3 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p3);
                }
                if(standard!=null&&StringUtils.isNotEmpty(standard.getName())) {
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }
                if(list.size()>0) {
                    Predicate[] arr = new Predicate[list.size()];
                    list.toArray(arr);
                    return cb.and(arr);
                }
                return null;
            }};
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Courier> page = courierService.pageQuery(specification,pageable);
		List<Courier> rows = page.getContent();
		long total = page.getTotalElements();
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", rows);
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] {"fixedAreas"});
		String json = JSONObject.fromObject(map,config).toString();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		return NONE;
	}
}
