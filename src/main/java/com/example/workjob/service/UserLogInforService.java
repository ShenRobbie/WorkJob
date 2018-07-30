package com.example.workjob.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.workjob.dao.UserLogInforDao;
import com.example.workjob.entity.BootStrapDataGrid;
import com.example.workjob.entity.UserLogInfor;
import com.github.pagehelper.PageHelper;

@Service
public class UserLogInforService {

	@Autowired
	private UserLogInforDao userLogInforDao;
	
	
	public void insertUserLogInfor(UserLogInfor userLogInfor) {
		// TODO Auto-generated method stub
		userLogInforDao.insertUserLogInfor(userLogInfor);
	}


	public BootStrapDataGrid getUserLogInforListData(UserLogInfor userLogInfor, HttpServletRequest request) {
		// TODO Auto-generated method stub
		BootStrapDataGrid bootStrapDataGrid=new BootStrapDataGrid();
		int pageNo=1;
		int pageSize=10;
		String no = request.getParameter("pageNo");
		String size = request.getParameter("pageSize");
		if (StringUtils.isNumeric(no)){
			pageNo=Integer.parseInt(no);
		}
	    if (StringUtils.isNumeric(no)){
	    	pageSize=Integer.parseInt(size);
		}
		PageHelper.startPage(pageNo,pageSize);
		List<UserLogInfor> list =userLogInforDao.getUserLogInforListData(userLogInfor);
		Long totalNum=userLogInforDao.getPageTotal(userLogInfor);
		bootStrapDataGrid.setTotal(totalNum);
		bootStrapDataGrid.setRows(list);
		return bootStrapDataGrid;
	}

}
