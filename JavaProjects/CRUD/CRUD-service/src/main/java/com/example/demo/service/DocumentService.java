package com.example.demo.service;


//import com.example.demo.repository.repo;

//import com.example.demo.docx.NotifikasiBondOptionWord;
//import com.example.demo.docx.NotifikasiFXOptionWord;
//import com.example.demo.docx.TCBondOptionWord;
import com.example.demo.docx.NotifikasiBondOptionWord;
import com.example.demo.docx.NotifikasiFXOptionWord;
import com.example.demo.docx.TCBondOptionWord;
import com.example.demo.docx.TCFXOptionWord;
import com.example.demo.pdf.*;
import javazoom.jl.player.Player;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

import static sun.management.Agent.getText;

@Service
//@RequiredArgsConstructor
public class DocumentService {

//    private String temDir = getText("confo.tempdir");

    private final static Logger log = Logger.getLogger(TCFXOption.class);
//    private final TCFXOption tcfxOption;
//    private final NotifikasiFXOption notifikasiFXOption;
    @Autowired
    private TCFXOption tcfxOption;

    @Autowired
    private NotifikasiFxOption notifikasiFxOption;

    @Autowired
    private TCBondOption tcBondOption;

    @Autowired
    private NotifikasiBondOption notifikasiBondOption;

    //WORD
    @Autowired
    private TCFXOptionWord tcfxOptionWord;

    @Autowired
    private NotifikasiFXOptionWord notifikasiFXOptionWord;
//
    @Autowired
    private TCBondOptionWord tcBondOptionWord;
//
    @Autowired
    private NotifikasiBondOptionWord notifikasiBondOptionWord;

//    public DocumentService(TCFXOption tcfxOption, NotifikasiFXOption notifikasiFXOption) {
//        this.tcfxOption = tcfxOption;
//        this.notifikasiFXOption = notifikasiFXOption;
//    }


//    @Autowired
//    private FiTradeTemplate2 fiTradeTemplate2;

    public String generatePDFTcFxOption() {

        tcfxOption.createPdf();
        return "TC FX Option PDF Template GENERATED";
    }

    public String generatePDFNotifikasiFxOption() {
        notifikasiFxOption.createPdf();
        return "Notifikasi FX Option GENERATED";
    }

    public String generatePDFTcBondOption() {
        tcBondOption.createPdf();
        return "TC Bond Option PDF Template GENERATED";
    }

    public String generatePDFNotifikasiBondOption() {
        notifikasiBondOption.createPdf();
        return "Notifikasi Bond Option GENERATED";
    }

    public String generateWordTcFxOption() {

        tcfxOptionWord.createWord();
        return "TC FX Option Word Template GENERATED";
    }

    public String generateWordTcBondOption(){
        tcBondOptionWord.createWord();
        return "TC Bond Option Word Template GENERATED";
    }
//
    public String generateWordNotifikasiFXOption(){
        notifikasiFXOptionWord.createWord();
        return "Notifikasi FX Option Word GENERATED";
    }
//
    public String generateWordNotifikasiBondOption(){
        notifikasiBondOptionWord.createWord();
        return "Notifikasi Bond Option Word GENERATED";
    }


//    public String openSvrReport(Map<String, String> data) {
//        FiTradeTemplate1 fiTradeTemplate1 = new FiTradeTemplate1();
//        FiTradeTemplate2 fiTradeTemplate2 = new FiTradeTemplate2();
//        FiTradeTemplate3 fiTradeTemplate3 = new FiTradeTemplate3();
//        log.info("-----------------------------------------------------------------CCODE " + data.get("CCODE"));
//        log.info("---------------------------------------------------------------- TRADE_TYPE >> " + data.get("TRADE_TYPE"));
//        if(data.get("TRADE_TYPE").equalsIgnoreCase(getText("negativeConfo"))) {
//            if (com.idm.portal.utils.ConfoUtils.isNullOrEmpty(data.get("REVREASON"))) {
//                return fiTradeTemplate3.createPdf(data, temDir);
//            } else {
//                try {
//                    return fiTradeTemplate3.createPdfReversal(data, temDir);
//                } catch (IOException e) {
//                    StringWriter errors = new StringWriter();
//                    e.printStackTrace(new PrintWriter(errors));
//                    log.error(errors.toString());
//                }
//            }
//        } else {
//            if (data.get("CCODE").equals("ID")) {
//                if (com.idm.portal.utils.ConfoUtils.isNullOrEmpty(data.get("REVREASON"))) {
//                    return fiTradeTemplate2.createPdf(data, temDir);
//                } else {
//                    try {
//                        return fiTradeTemplate2.createPdfReversal(data, temDir);
//                    } catch (IOException e) {
//                        StringWriter errors = new StringWriter();
//                        e.printStackTrace(new PrintWriter(errors));
//                        log.error(errors.toString());
//                    }
//                }
//            } else {
//                if (com.idm.portal.utils.ConfoUtils.isNullOrEmpty(data.get("REVREASON"))) {
//                    return fiTradeTemplate1.createPdf(data, temDir);
//                } else {
//                    try {
//                        return fiTradeTemplate1.createPdfReversal(data, temDir);
//                    } catch (IOException e) {
//                        StringWriter errors = new StringWriter();
//                        e.printStackTrace(new PrintWriter(errors));
//                        log.error(errors.toString());
//                    }
//                }
//            }
//        }
//        return "criteria not match";
//    }

//    public static void play(String file, int repeat) {
//
//        try {
//
//            for(int i=0;i<repeat;i++){
//
//                FileInputStream fis = new FileInputStream(file);
//
//                Player player = new Player(fis);
//
//                player.play();
//
//                fis.close();
//                System.out.println(i+1);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void main(String[] args) {
////
//////;9
////        play("D:/music/kicau.mpeg",  1);
////        play("D:/music/hatiKecewa.mpeg",1);
//////
//        play("D:/music/sakading.mpeg",10);
////////                        play("D:/music/ajukajuk.mp3",1);
////
////// kepala 20 lagi
////        // sakading 20 lagi
////            play("D:/music/kepala.mpeg",15);
//////            play("D:/music/digidigi.mp3",  1);
//////
////
////
////    }
//}
 }
