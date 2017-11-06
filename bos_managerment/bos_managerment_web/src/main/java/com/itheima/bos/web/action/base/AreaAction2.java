package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.ithiema.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sourceforge.pinyin4j.PinyinHelper;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 10:47:49 AM <br/>       
 */
@Namespace("/")
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
public class AreaAction2 extends ActionSupport implements ModelDriven<Area> {
    private Area model = new Area();
    private File file;
    private  int page;
    private  int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    @Autowired
    private AreaService areaService;
    @Override
    public Area getModel() {
        
        return model;
    }
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value="areaAction_importXls",results= {@Result(type="redirect",name="success",location="/pages/base/area.html")})
    public String importXls() throws IOException {
        //加载文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        // 获取第一个工作簿
        HSSFSheet sheet = workbook.getSheetAt(0);
        
        List<Area>  list = new ArrayList<>();
        //遍历每行,分装为一个area
        
        for (Row row : sheet) {
            if(row.getRowNum()==0) {
                continue;
            }
            //第一列的是编号,不要,插入数据是另外生成作为主键
            String province = row.getCell(1).getStringCellValue();  //第二列的数据
            String city = row.getCell(2).getStringCellValue();      //第三列的数据    
            String district = row.getCell(3).getStringCellValue();  //第四列的数据
            String postcode = row.getCell(4).getStringCellValue();  //第五列的数据
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            postcode = postcode.substring(0, postcode.length()-1);
            String citycode = PinYin4jUtils.hanziToPinyin(province, "").toUpperCase();
            String[] headByString = PinYin4jUtils.getHeadByString(province + city + district,true);
            String shortcode = StringUtils.join(headByString);
            Area area = new Area(province, city, district, postcode,citycode,shortcode);
            list.add(area);
            }
        areaService.save(list);
        //将集合存进数组中
        workbook.close();
        return SUCCESS;
    }
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageable);
        //封装数据
        //1.总数据条数
        long total = page.getTotalElements();
        //2.Standard 的 list集合
        List<Area> list = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[] {"subareas"});
        String json = JSONObject.fromObject(map,config).toString();
        //把对象转成json数据  
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
  
