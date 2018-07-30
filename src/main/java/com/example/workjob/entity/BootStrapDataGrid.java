package com.example.workjob.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * table Json BootStrapDataGrid模型
 * 
 * 
 */
public class BootStrapDataGrid implements java.io.Serializable {

	private Long total = 0L;
	private List rows = new ArrayList();

	public BootStrapDataGrid() {
		super();
	}

	public BootStrapDataGrid(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
