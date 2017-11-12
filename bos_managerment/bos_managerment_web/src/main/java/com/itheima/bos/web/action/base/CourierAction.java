package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
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

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class CourierAction extends BaseAction<Courier>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2925375582327233286L;
	private String IDS;
	public void setIDS(String IDS) {
		this.IDS = IDS;
	}
	@Autowired
	private CourierService courierService;
	@Action(value="courierAction_save",results={@Result(name="success",type="redirect",location="/pages/base/courier.html")})
	public String save(){													
		courierService.save(getModel());
		return SUCCESS;
	}
	
	//作废快递员信息
	@Action(value="courierAction_deleteById",results={@Result(name="success",type="redirect",location="/pages/base/courier.html")})
	public String deleteById(){
		if(!StringUtils.isEmpty(IDS)){
		String[] ids = IDS.split(",");
			courierService.deleteById(ids);
		}
		return SUCCESS;
	}
	
	//恢复快递员信息
	@Action(value="courierAction_declineById",results={@Result(name="success",type="redirect",location="pages/base/courier.html")})
	public String declineById(){
		if(!StringUtils.isEmpty(IDS)){
		String[] ids = IDS.split(",");
			courierService.declineById(ids);
		}
		return SUCCESS;
	}
	@Action(value="courierAction_finAll")
    public String finAll() throws IOException{
        List<Courier> list = courierService.findByDelTagisNull();
        List2Json(list, new String[] {"fixedAreas"});
	    return NONE;
    }
	@Action("courierAciton_pageQuery")
	public String pageQuery() throws IOException{
	    Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String courierNum = getModel().getCourierNum();
                String type = getModel().getType();
                String company = getModel().getCompany();
                Standard standard = getModel().getStandard();
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
            Map2Json(page,new String[] {"fixedAreas"});
            return NONE;
	}
}
