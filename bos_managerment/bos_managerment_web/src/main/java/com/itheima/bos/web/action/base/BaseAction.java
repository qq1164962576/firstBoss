package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.procedure.internal.Util.ResultClassesResolutionContext;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.ehcache.util.FindBugsSuppressWarnings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    private static final long serialVersionUID = 4129828872009772355L;
    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
	private T t;

	public BaseAction() {
		Class<? extends BaseAction> childrenClass = this.getClass();
		Type type = childrenClass.getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Class realClass = (Class) actualTypeArguments[0];
		try {
			t = (T) realClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public T getModel() {
		return t;
	}
	public void Map2Json(Page<T> page,String[] arr) throws IOException {
	    long total = page.getTotalElements();
        //2.Standard 的 list集合
        List<T> list = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        JsonConfig config = new JsonConfig();
        config.setExcludes(arr);
        String json = JSONObject.fromObject(map,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
	}
	public void List2Json(List list,String[] arr) throws IOException {
        //2.Standard 的 list集合
        JsonConfig config = new JsonConfig();
        config.setExcludes(arr);
        String json = JSONArray.fromObject(list,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
	
}
