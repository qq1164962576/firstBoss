package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.ithiema.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
import com.itheima.bos.service.base.AreaService;
import com.ithiema.utils.PinYin4jUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    @Action(value=("areaAction_exportExcel"))
    public String exportExcel() throws IOException {
        List<Area> list = areaService.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("区域数据");
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");

        for (Area area : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
        String fileName = "区域数据.xls";

        HttpServletRequest request = ServletActionContext.getRequest();
        String agent = request.getHeader("User-Agent");
        fileName = FileUtils.encodeDownloadFilename(fileName,agent);

        HttpServletResponse response = ServletActionContext.getResponse();
        // 设置文件的类型
        response.setContentType(ServletActionContext.getServletContext().getMimeType(fileName));
        // 设置 Content-Disposition 头
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return NONE;
    }
}
  
