package com.example.workjob.dao;

import com.example.workjob.entity.UserInfo;



public interface UserInfoDao {

	public UserInfo findByUsername(String username);
	
	public int ifExistUser(String username);

	public void insertUserInfo(UserInfo userInfo);

	public int updatePassword(UserInfo userInfo);

	public void updateUserInfo(UserInfo userInfo);

}
