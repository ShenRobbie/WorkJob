package com.example.workjob.dao;

import java.util.List;

import com.example.workjob.entity.UserLogInfor;

public interface UserLogInforDao {

	public void insertUserLogInfor(UserLogInfor userLogInfor);

	public List<UserLogInfor> getUserLogInforListData(UserLogInfor userLogInfor);

	public Long getPageTotal(UserLogInfor userLogInfor);

}
