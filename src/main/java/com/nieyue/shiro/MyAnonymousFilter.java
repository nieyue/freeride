package com.nieyue.shiro;

import com.nieyue.exception.CommonRollbackException;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 自定义匿名访问
 * @author 聂跃
 * @date 2018年4月4日
 */
public class MyAnonymousFilter extends AnonymousFilter {
	@Override
	protected boolean onPreHandle(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		if(request.getRequestURI().equals("/")){
			//重定向, (请求转发不行)
			try {
				//StringBuffer url =request.getRequestURL();
				//String tempContextUrl =url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
				if(StringUtils.isEmpty(request.getHeader("X-Forwarded-Proto"))){
					response.sendRedirect("/home/index.html");
				}else{
					//ssl
					String tempContextUrl =request.getHeader("X-Forwarded-Proto") + "://" + request.getServerName();
					response.sendRedirect(tempContextUrl+"/home/index.html");
				}
			} catch (IOException e) {
				throw new CommonRollbackException("跳转错误");
			}
			return false;
		}
		/*if(request.getRequestURI().indexOf(".txt")>-1){
			return true;
		}*/
		return super.onPreHandle(request, response, mappedValue);
	}
}
