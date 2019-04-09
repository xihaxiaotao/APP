package cn.appsys.controller.backend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;

@Controller
@RequestMapping(value="/manager")
public class UserLoginController {
	
	@Autowired
	private  BackendUserService bService;
	
	
	//页面跳转到“后台管理系统-登录页”
    @RequestMapping("/tologin")
    public  String  toLogin(){
   	   return "backend/backendlogin";
    }
    
    //登录验证
    @RequestMapping("/login")
    public String login(BackendUser bUser,HttpSession session,HttpServletRequest request){
    	 BackendUser  bakUser=bService.getBackendUser(bUser);
	   	 String  info="";
	   	 if(bakUser==null){
	   		 request.setAttribute("error", "用户名不存在！");
	   		 info="backend/backendlogin";
	   	 }else{
	   		 if(bUser.getUserCode().equals(bakUser.getUserCode())&&bUser.getUserPassword().equals(bakUser.getUserPassword())){
	   			 session.setAttribute("userSession", bakUser);
	   			 info="backend/main";//跳转到-欢迎页面		
	   		 }else{
	   			 request.setAttribute("error", "密码错误！");
	   			 info="backend/backendlogin";
	   		 }
	   	 }
	   	 return info;
    }
    
    //注销
    @RequestMapping("/logout")
    public String  logout(HttpSession session){
   	 session.invalidate();
   	 return "backend/backendlogin";
    }
}
