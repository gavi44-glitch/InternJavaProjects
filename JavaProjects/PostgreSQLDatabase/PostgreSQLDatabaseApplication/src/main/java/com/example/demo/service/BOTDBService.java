package com.example.demo.service;

import com.example.demo.model.BOTDB;

import java.util.Map;

public interface BOTDBService {
    public BOTDB create(BOTDB botdb);
    public BOTDB detailDataSource(String dataSourceCode);
    public BOTDB deleteDataSource(String dataSourceCode);
    public BOTDB updateDataSource(String dataSourceCode, Map<String,Object> updates);
}
