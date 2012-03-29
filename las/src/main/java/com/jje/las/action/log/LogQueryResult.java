package com.jje.las.action.log;

import java.util.List;

public class LogQueryResult {

    private List<Log> list;
    private int totalPage;
    private int currentPage;


    public List<Log> getList() {
        return list;
    }

    public void setList(List<Log> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public static LogQueryResult getResult(List<Log> list, long dataCount, int currentPage, int pageSize) {
        LogQueryResult r = new LogQueryResult();
        r.setList(list);
      
       int pz =Long.valueOf(dataCount).intValue() / pageSize;
       int mod =Long.valueOf(dataCount).intValue()%pageSize;
       if(mod>0)
       {
    	   pz++;
       }
        r.setTotalPage(pz);
        r.setCurrentPage(currentPage);
        return r;
    }

}
