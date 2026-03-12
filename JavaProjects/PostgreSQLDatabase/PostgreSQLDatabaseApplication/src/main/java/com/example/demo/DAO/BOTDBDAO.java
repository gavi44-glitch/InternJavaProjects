package com.example.demo.DAO;

import com.example.demo.model.BOTDB;

public interface BOTDBDAO {
    public BOTDB create(BOTDB botdb);
    public BOTDB detailDataSource(String dataSourceCode);
    public BOTDB deleteDataSource(String dataSourceCode);
}
