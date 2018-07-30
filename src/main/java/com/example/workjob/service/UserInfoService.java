package com.example.workjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workjob.dao.UserInfoDao;
import com.example.workjob.entity.UserInfo;

@Service
public class UserInfoService {

	
	@Autowired
	private UserInfoDao userInfoDao;
	
	public UserInfo findByUsername(String username) {
		// TODO Auto-generated method stub
		return userInfoDao.findByUsername(username);
	}

	public boolean ifExistUser(String username) {
		 if(userInfoDao.ifExistUser(username) == 0){
			 return false;
		 }else{
			 return true;
		 }
	}
	
	public void insertUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoDao.insertUserInfo(userInfo);
	}

	public String updatePassword(UserInfo userInfo) {
		// TODO Auto-generated method stub
		int num= userInfoDao.updatePassword(userInfo);
		if(num>=1){
			return userInfo.getUsername()+"密码修改成功";
			
		}else{
			return userInfo.getUsername()+"密码修改失败";
		}
		
	}

	public void updateUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoDao.updateUserInfo(userInfo);
	}

}
