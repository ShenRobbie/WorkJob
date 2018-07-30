package com.example.workjob.until;

import java.util.Comparator;


import net.sf.json.JSONObject;

public class TestServerComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		long  parm1=Long.parseLong(o1);
		long  parm2=Long.parseLong(o2);


		return (int) (parm2-parm1);
	}

	

}
