package com.example.workjob.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workjob.entity.UserInfo;
import com.example.workjob.service.UserInfoService;
import com.example.workjob.service.UserLogInforService;

@Controller
public class PageController {

	
	@Autowired
	private UserInfoService userInfoService;

	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		request.setAttribute("userInfoMiddle", userInfoMiddle);
		// String strDirPath =
		// request.getServletContext().getRealPath("/resources/static/static/images/");
		// strDirPath=strDirPath.replaceAll("\\\\"+"webapp", "");
		// String strDirPath = request.getServletContext().getRealPath("/");
		// File file =new File(strDirPath);
		// System.out.println(file.getParent());
		return "index";
	}
	
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/layuimain")
	public String layuimain(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		userInfoMiddle = userInfoService.findByUsername(userInfoMiddle.getUsername());
		request.setAttribute("userInfoMiddle", userInfoMiddle);
		return "layuimain";
	}
	
	@RequestMapping("/userInfo")
	public String userInfo(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		userInfoMiddle = userInfoService.findByUsername(userInfoMiddle.getUsername());
		request.setAttribute("userInfoMiddle", userInfoMiddle);
		return "userInfo";
	}
	
	@RequestMapping("/changePwd")
	public String changePwd(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		request.setAttribute("userInfoMiddle", userInfoMiddle);
		return "changePwd";
	}
	
	@RequestMapping("/homePage")
	public String homePage() {
		return "homePage";
	}
}
