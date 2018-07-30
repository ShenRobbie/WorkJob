package com.example.workjob.controller;




import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.workjob.entity.Message;
import com.example.workjob.entity.UserInfo;
import com.example.workjob.entity.UserLogInfor;
import com.example.workjob.service.UserInfoService;
import com.example.workjob.service.UserLogInforService;
import com.example.workjob.until.CustomUntil;
import com.example.workjob.until.GeneralMethod;

import net.sf.json.JSONObject;

@Controller
public class LoginController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserLogInforService userLogInforService;
	
	@Autowired
	private Message message;

	@Value(value = "${server.servlet.context-path}")
	private String workJob;

//	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/login")
	public String loginAciton(UserInfo userInfo, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception {
		if (bindingResult.hasErrors()) {
			return "login";
		}

		System.out.println("xxxx" + userInfo.getPassword());
		UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());
		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(token);
		} catch (IncorrectCredentialsException ice) {
			System.out.println("11111111111");
			redirectAttributes.addFlashAttribute("error", "用户名或密码不正确！");
		} catch (UnknownAccountException uae) {
			System.out.println("22222222222");
			redirectAttributes.addFlashAttribute("error", "未知账户！");
		} catch (LockedAccountException lae) {
			System.out.println("33333333333");
			redirectAttributes.addFlashAttribute("error", "账户已锁定！");
		} catch (ExcessiveAttemptsException eae) {
			System.out.println("44444444444");
			redirectAttributes.addFlashAttribute("error", "用户名或密码错误次数太多！");
		} catch (AuthenticationException ae) {

			System.out.println("55555555555");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "用户名或密码不正确！");
		}
		System.out.println("6666666666");
		if (currentUser.isAuthenticated()) {
			UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
			request.setAttribute("userInfoMiddle", userInfoMiddle);


			System.out.println("7777777777");
			return "layuimain";
		} else {
			System.out.println("88888888888");
			token.clear();

			return "login";
		}
	}

	@RequestMapping("/quit")
	public String quit(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		currentUser.logout();
		UserLogInfor userLogInfor = new UserLogInfor();
		userLogInfor.setCreateDate(GeneralMethod.getDate());
		userLogInfor.setMessage("用户退出系统");
		userLogInfor.setUsername(userInfoMiddle.getUsername());
		userLogInforService.insertUserLogInfor(userLogInfor);
		
		String filePath = request.getSession().getServletContext().getRealPath("logUplode/");
		String fileName = CustomUntil.getFileName(filePath,GeneralMethod.getDayString())+".log";
        GeneralMethod.writeFile(filePath,fileName, userLogInfor.toString());

		return "redirect:" + "/login";
	}

	
	
	@RequestMapping(value="/firstIndex",method=RequestMethod.GET)
	public String firstIndex(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfoMiddle = (@Valid UserInfo) currentUser.getPrincipal();
		request.setAttribute("userInfoMiddle", userInfoMiddle);
		
		UserLogInfor userLogInfor = new UserLogInfor();
		userLogInfor.setCreateDate(GeneralMethod.getDate());
		userLogInfor.setMessage("用户登录系统");
		userLogInfor.setUsername(userInfoMiddle.getUsername());
		userLogInforService.insertUserLogInfor(userLogInfor);
		
		String filePath = request.getSession().getServletContext().getRealPath("logUplode/");
		String fileName=CustomUntil.getFileName(filePath,GeneralMethod.getDayString())+".log";
        GeneralMethod.writeFile(filePath,fileName, userLogInfor.toString());
		
		return "redirect:" + "/layuimain";
	}
	
	
	
	
	
	
	
	
	

	
	
	
	

}
