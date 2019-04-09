package cn.appsys.tools;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//拦截器
public class SysInterceptor extends HandlerInterceptorAdapter{
	  public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws Exception{
		  if(request.getSession().getAttribute("userSession")==null&&request.getSession().getAttribute("devUserSession")==null){
			  response.sendRedirect(request.getContextPath()+"/403.jsp");
			  return  false;
		  }else{
			  return  true;
		  }
	  }
}
