package cn.appsys.controller.developer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;



@Controller
@RequestMapping(value="/dev")
public class DevLoginController {
	
	 @Autowired
	 private  DevUserService  dService;
	
	 //页面跳转到“开发者-登录页”
     @RequestMapping("/tologin")
     public  String  toLogin(){
    	 return "developer/devlogin";
     }
     
     
     //执行登录
     @RequestMapping("/login")
     public  String  login(DevUser  dUser,HttpSession session,HttpServletRequest request){
    	 DevUser  devUser=dService.getDevUser(dUser);
    	 String  info="";
    	 if(devUser==null){
    		 request.setAttribute("error", "用户名不存在！");
    		 info="developer/devlogin";
    	 }else{
    		 if(dUser.getDevCode().equals(devUser.getDevCode())&&dUser.getDevPassword().equals(devUser.getDevPassword())){
    			 session.setAttribute("devUserSession", devUser);
    			 info="developer/main";//跳转到-欢迎页面		
    		 }else{
    			 request.setAttribute("error", "密码错误！");
    			 info="developer/devlogin";
    		 }
    	 }
    	 return info;
     }
     
     //注销
     @RequestMapping("/logout")
     public String  logout(HttpSession session){
    	 session.invalidate();
    	 return "developer/devlogin";
     }
     

}

