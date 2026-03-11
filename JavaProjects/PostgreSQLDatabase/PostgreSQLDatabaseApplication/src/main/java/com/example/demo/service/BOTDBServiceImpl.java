package com.example.demo.service;

import com.example.demo.DAO.BOTDBDAO;
import com.example.demo.model.BOTDB;
import com.example.demo.utils.BaseHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BOTDBServiceImpl implements BOTDBService{

    private final BOTDBDAO botdbdao;

    public BOTDBServiceImpl(BOTDBDAO botdbdao) {
        this.botdbdao = botdbdao;
    }

    @Override
    @Transactional
    public BOTDB create(BOTDB botdb) {

        botdb.setDataSourceCode(BaseHelper.getRandomNumber());
        botdb.setCreatedDt(BaseHelper.getCurrentTimestamp());
        return botdbdao.create(botdb);
    }

}
