package com.example.demo.controller;

import com.example.demo.model.BOTDB;
import com.example.demo.service.BOTDBService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "DataSource Management", description = "CRUD DataSource Operation Endpoints")
@RequestMapping("/dataSource")
public class DataSourceController {

    private final BOTDBService botdbService;

    public DataSourceController(BOTDBService botdbService) {
        this.botdbService = botdbService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create DataSource")
    public BOTDB create(@RequestBody BOTDB botdb, Authentication authToken){
        String userID = (String) authToken.getPrincipal();
//        botdb.setStatus(STATIC);
        botdb.setCreatedBy(userID);
        System.out.println(botdb);
        return botdbService.create(botdb);
    }

}
