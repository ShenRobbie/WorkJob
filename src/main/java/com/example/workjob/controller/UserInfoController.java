package com.example.workjob.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.workjob.entity.BootStrapDataGrid;
import com.example.workjob.entity.Message;
import com.example.workjob.entity.UserInfo;
import com.example.workjob.entity.UserLogInfor;
import com.example.workjob.service.UserInfoService;
import com.example.workjob.service.UserLogInforService;
import com.example.workjob.until.CustomUntil;
import com.example.workjob.until.GeneralMethod;

@Controller
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserLogInforService userLogInforService;
	
	@Autowired
	private Message message;

	@Value(value = "${server.servlet.context-path}")
	private String workJob;
	
	
	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public String updateUserInfo(UserInfo userInfo, RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		String fileName = file.getOriginalFilename();
		int uid = (int) Math.round(Math.random() * 10000);
		fileName = GeneralMethod.getDateString() + uid + fileName;
		String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
		try {
			CustomUntil.uploadFile(file.getBytes(), filePath, fileName);
		} catch (Exception e) {

		}
		userInfo.setHeadPortraitPath(workJob + "/imgupload/" + fileName);
		UserInfo oldUserInfo=userInfoService.findByUsername(userInfo.getUsername());
		String[] oldFileName=oldUserInfo.getHeadPortraitPath().split("/imgupload/");
		CustomUntil.deleteLoadFile(filePath, oldFileName[1]);
		userInfoService.updateUserInfo(userInfo);
		return "";
	}

	@RequestMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(UserInfo userInfo) {
		
		
		return userInfoService.updatePassword(userInfo);
	}
	
	@RequestMapping(value = { "getUserLogInforListData" })
	@ResponseBody
	public BootStrapDataGrid getUserLogInforListData(UserLogInfor userLogInfor,HttpServletRequest request,HttpServletResponse response) {

		BootStrapDataGrid dataGrid = new BootStrapDataGrid();

		dataGrid=userLogInforService.getUserLogInforListData(userLogInfor,request);

		return dataGrid;
	}
}
