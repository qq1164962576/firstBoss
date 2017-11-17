package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import com.itheima.bos.service.base.AreaService;
import com.ithiema.utils.PinYin4jUtils;



/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 10:47:49 AM <br/>       
 */
@Namespace("/")
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
public class AreaAction extends BaseAction<Area> {
    private static final long serialVersionUID = -5785267435377938972L;
    private File file;
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    @Autowired
    private AreaService areaService;
    
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
            
            String citycode = PinYin4jUtils.hanziToPinyin(province+city+district, "").toUpperCase();
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
    @Action("areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list ;
        if(StringUtils.isNotEmpty(q)) {
            list = areaService.findByQ(q);
        }else {
            list = areaService.findAll();
        }
        List2Json(list, new String[] {"subareas"});
        return NONE;
    }
    
    @Action(value=("areaAction_save"),results= {@Result(name="success",type="redirect",location="/page/base/area.html")})
    public String save() throws IOException {
        areaService.save(getModel());
        return SUCCESS;
    }
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageable);
        Map2Json(page, new String[] {"subareas"});
        return NONE;
    }
}
  
