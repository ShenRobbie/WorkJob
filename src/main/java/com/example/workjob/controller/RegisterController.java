package com.example.workjob.controller;


import static org.assertj.core.api.Assertions.from;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.workjob.entity.Message;
import com.example.workjob.entity.UserInfo;
import com.example.workjob.service.UserInfoService;
import com.example.workjob.until.CustomUntil;
import com.example.workjob.until.GeneralMethod;

import net.sf.json.JSONObject;

@RestController
public class RegisterController {

	public static Logger logger = LogManager.getLogger(RegisterController.class.getName());
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private Message message;
	
	@Value(value = "${server.servlet.context-path}")
	private String workJob;
	
	
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	public Message registerUser(UserInfo userInfo, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		JSONObject json = null;
		logger.info("username--"+userInfo.getUsername());
		logger.info("password--"+userInfo.getPassword());
		if(userInfoService.ifExistUser(userInfo.getUsername())){
			message.setResult(-1);
			message.setMsg("用户名已存在!");
//			JSONObject json = new JSONObject();
//			json.put("result", 0);
//			json.put("msg", "用户名已存在！");
			return message;
		}
		
		String fileName = file.getOriginalFilename();
		/* 
		 * System.out.println("fileName-->" + fileName);
		 * System.out.println("getContentType-->" + contentType);
		 */
		
		
		int uid = (int) Math.round(Math.random() * 10000);
		userInfo.setUid(uid);
		userInfo.setUsertype("普通注册用户");
		userInfo.setSalt(uid + "--" + userInfo.getUsername());
		String saltPassword = GeneralMethod.addSaltMD5(userInfo);
		userInfo.setPassword(saltPassword);
		if (null == fileName || "".equals(fileName)) {
			userInfo.setHeadPortraitPath(workJob + "/imgupload/user_defalut.png");
		} else { 
			fileName = GeneralMethod.getDateString() + uid + fileName;
			String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
			try {
				CustomUntil.uploadFile(file.getBytes(), filePath, fileName);
			} catch (Exception e) {

			}
			userInfo.setHeadPortraitPath(workJob + "/imgupload/" + fileName);
		}

		userInfoService.insertUserInfo(userInfo);
		message.setResult(1);
		message.setMsg("注册成功！");
//		json = JSONObject.fromObject(message);
		return message;
	}
	
}
