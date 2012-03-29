package com.jje.las.analysis;

import com.mongodb.BasicDBObject;
import java.util.Date;

public class ErrorLog {

	private String errorFile;
	private int rows;
	private String errorInfo;
	private Date current;
	private String detail;
	

	
	public ErrorLog()
	{
		
	}
	
	public ErrorLog(String errorFile,int rows,String errorInfo,String detail)
	{
		setErrorFile(errorFile);
		setRows(rows);
		setErrorInfo(errorInfo);
		setCurrent(current);
		setDetail(detail);
	}
	
	public String getErrorFile() {
		return errorFile;
	}
	public void setErrorFile(String errorFile) {
		this.errorFile = errorFile;
		
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Date getCurrent() {
		return current;
	}

	public void setCurrent(Date current) {
		this.current = current;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
