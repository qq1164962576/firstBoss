package com.itheima.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.FileManager;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 18, 2017 10:59:32 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class ImageAction extends BaseAction<Object>{
    
    private File imgFile;
    private String imgFileFileName;
    
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }   
    
    //图片访问路径
    @Action("imageAction_upload")
    public String imageSave() throws IOException {
        
        Map<String, Object> map = new HashMap<>();
        //获取后缀名
        try {
            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            //生成文件名
            String fileName = UUID.randomUUID().toString();
            
            fileName = fileName + suffix;
            
            //获取图片保存路径
            String saveDirPath = "upload";
            String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
            
            FileUtils.copyFile(imgFile, new File(saveDirRealPath+"/"+fileName));
            
            //获取本项目路径名
            String path = ServletActionContext.getServletContext().getContextPath();
            String relativepath = path+"/"+saveDirPath+"/"+fileName;
            map.put("error",0);
            map.put("url", relativepath);
            
        } catch (IOException e) {
            map.put("error",1);
            map.put("message", e.getMessage());
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            
        }
        //传送json数据到前台
        MapToJson(map, null);
        
        return NONE;
    }
    
    //图片空间访问地址
    @Action("imageAction_manager") 
    public String manager() throws IOException {
        
        //获取保存图片在服务器的路径
        String saveDir = "upload";
        String realPath = ServletActionContext.getServletContext().getRealPath(saveDir);
        
        //遍历此文件夹下面的文件
        File saveDirFile = new File(realPath);
        // 图片扩展名
        String[] fileTypes = new String[] {"gif", "jpg", "jpeg", "png", "bmp"};

        // 遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (saveDirFile.listFiles() != null) {
            for (File file : saveDirFile.listFiles()) {
                Hashtable<String, Object> hash =
                        new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt =
                            fileName.substring(fileName.lastIndexOf(".") + 1)
                                    .toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo",
                            Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(file.lastModified()));
                fileList.add(hash);
            }
        }

        // 封装写回客户端的数据
        Map<String, Object> map = new HashMap<>();
        // 指定所有文件的信息
        map.put("file_list", fileList);
        //文件 路径  项目名真实路径/图保存路径/
        map.put("current_url", ServletActionContext.getServletContext().getContextPath()+"/"+saveDir+"/");

        MapToJson(map, null);
        //将路径返回给前端
        
        return NONE;
    }
    
}
  
