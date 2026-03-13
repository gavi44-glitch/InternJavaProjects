package com.example.demo.controller;

import com.example.demo.model.BOTDB;
import com.example.demo.DTO.DataSourceBean;
import com.example.demo.model.User;
import com.example.demo.service.BOTDBService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Tag(name = "DataSource Management", description = "CRUD DataSource Operation Endpoints")
@RequestMapping("/dataSource")
public class DataSourceController {

    private final BOTDBService botdbService;

    public DataSourceController(BOTDBService botdbService) {
        this.botdbService = botdbService;
    }

    @PostMapping("/createDataSource")
    @Operation(summary = "Create DataSource", security = @SecurityRequirement(name = "bearerAuth"))
    public BOTDB create(@RequestBody BOTDB botdb, Authentication authToken){
        String userID = (String) authToken.getPrincipal();
//        botdb.setStatus(STATIC);
        botdb.setCreatedBy(userID);
        System.out.println(botdb);
        return botdbService.create(botdb);
    }

    @PostMapping("/detailDataSource")
    @Operation(summary = "Detail DataSource", security = @SecurityRequirement(name = "bearerAuth"))
    public BOTDB detail(@RequestBody DataSourceBean dataSourceBean){
        return botdbService.detailDataSource(dataSourceBean.getDataSourceCode());
    }

    @DeleteMapping("/deleteDataSource")
    @Operation(summary = "Delete DataSource", security = @SecurityRequirement(name = "bearerAuth"))
    public BOTDB delete(@RequestBody DataSourceBean dataSourceBean){
        return botdbService.deleteDataSource(dataSourceBean.getDataSourceCode());
    }

    @PatchMapping("/updateDataSource/{dataSourceCode}")
    @Operation(summary = "Update DataSource by dataSourceCode", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BOTDB> updateDataSource(@PathVariable String dataSourceCode, @RequestBody Map<String, Object> updates){
        BOTDB updateDataSource = botdbService.updateDataSource(dataSourceCode, updates);
        return ResponseEntity.ok(updateDataSource);
    }

}
