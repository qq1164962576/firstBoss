package com.itheima.bos.web.action.take_delivery;


import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import com.itheima.bos.web.action.base.BaseAction;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 18, 2017 10:51:39 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {
    @Autowired
    private PromotionService promotionService;
       
    private File titleImgFile;
    
    private String titleImgFileFileName;
    
    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }
    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }
    
    @Action(value="promotionAction_save",results= {@Result(type="redirect",name=SUCCESS,location="/pages/tale_delivery/promotion.html")})
    public String save() {
        promotionService.save(getModel());
        return NONE;
    }
}
  
