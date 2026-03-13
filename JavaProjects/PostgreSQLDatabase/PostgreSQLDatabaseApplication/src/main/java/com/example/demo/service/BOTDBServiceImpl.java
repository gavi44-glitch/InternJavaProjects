package com.example.demo.service;

import com.example.demo.DAO.BOTDBDAO;
import com.example.demo.model.BOTDB;
import com.example.demo.model.PendingTask;
import com.example.demo.utils.BaseHelper;
import com.example.demo.utils.StaticParameter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
//import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

@Service
public class BOTDBServiceImpl implements BOTDBService{

    private final BOTDBDAO botdbdao;
//    private final PendingTask pendingTask;
    private final PendingTaskService pendingTaskService;

    public BOTDBServiceImpl(BOTDBDAO botdbdao, PendingTaskService pendingTaskService) {
        this.botdbdao = botdbdao;
//        this.pendingTask = pendingTask;
        this.pendingTaskService = pendingTaskService;
    }

    @Override
    @Transactional
    public BOTDB create(BOTDB botdb) {

        botdb.setDataSourceCode(BaseHelper.getRandomNumber());
        botdb.setCreatedDt(BaseHelper.getCurrentTimestamp());
        botdb.setStatus(StaticParameter.WORKFLOW_STATUS_WAITING_APPROVAL);

        PendingTask pendingTask = new PendingTask();
        UUID uuid = UUID.randomUUID();
        String pendingTaskCode = uuid.toString();

        pendingTask.setCode(pendingTaskCode);
        pendingTask.setServiceType(StaticParameter.SERVICE_NAME_DATASOUCE);
        pendingTask.setReferenceNo(botdb.getDataSourceCode());
        pendingTask.setInfo1(botdb.getDataSourceCode());
        pendingTask.setInfo2(botdb.getDataSourceName());
        pendingTask.setInfo3(botdb.getDescription());
        pendingTask.setInfo4(botdb.getCreatedBy());
        pendingTask.setCreatedBy(botdb.getCreatedBy());
        pendingTask.setCreatedDate(botdb.getCreatedDt());
        pendingTask.setApprovedBy(botdb.getApprovalBy());
        pendingTask.setApprovedDate(botdb.getApprovalDt());
        pendingTask.setWorkflowStatus(botdb.getStatus());
        pendingTask.setActionInfo(StaticParameter.PENDING_TASK_ACTION_INFO_ADD);

        pendingTaskService.create(pendingTask); // insert ke table pending_task
        botdbdao.create(botdb); // insert ke table bo_tdb


        return botdb;
    }

    @Override
    public BOTDB detailDataSource(String dataSourceCode) {

        BOTDB botdb = botdbdao.detailDataSource(dataSourceCode);

        return botdb;
    }

    @Override
    @Transactional
    public BOTDB deleteDataSource(String dataSourceCode){

        return botdbdao.deleteDataSource(dataSourceCode);
    }

    @Override
    @Transactional
    public BOTDB updateDataSource(String dataSourceCode, Map<String,Object> updates){

        BOTDB botdb = botdbdao.detailDataSource(dataSourceCode);

        HashSet<String> allowedUpdates = new HashSet<>();
        allowedUpdates.add("dataSourceName");
        allowedUpdates.add("description");

        for(Map.Entry<String,Object> entry : updates.entrySet()){
            if(!allowedUpdates.contains(entry.getKey())){
                continue;
            }

            try {
                Field field = BOTDB.class.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(botdb, entry.getValue());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return botdb;

        //GAUSAH MERGE LAGI, KARENA UPDATE ENTITY ADA DALAM 1 TRANSAKSI YANG SAMA DENGAN detailDataSource
        // jadi flownya find botdb dulu -> baru update gitu
        // kalo udah di find, udah ada dalam persistence context, jadi kalo di .merge lagi -> redundant.

//        return botdbdao.updateDataSource(dataSourceCode,updates);
    }
}
