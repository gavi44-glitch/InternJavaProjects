package com.example.demo.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.idm.portal.net.ExecuteCoreJob;
//import com.idm.portal.service.CommonService;
import com.itextpdf.kernel.pdf.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("Duplicates")
public class ConfoUtils {
    private final static Logger log = Logger.getLogger(ConfoUtils.class);
//    private static CommonService commonService;

    public static void securePdf(String src, String dest, String pw) {
        try {
            PdfReader reader = new PdfReader(src);
            WriterProperties props = new WriterProperties()
                    .setStandardEncryption(pw.getBytes(), pw.getBytes(), EncryptionConstants.ALLOW_PRINTING,
                            EncryptionConstants.ENCRYPTION_AES_128 | EncryptionConstants.DO_NOT_ENCRYPT_METADATA);
            PdfWriter writer = new PdfWriter(new FileOutputStream(dest), props);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            pdfDoc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
    }

    // enkripsi agile = enkripsi aes_128
    public static void secureWord(String src, String dest, String pw){

        // ini merupakan kontainer file OLE2 (intinya seluruh dokumen word yang
        // sudah dalam format tereknkripsi -> sperti brankas pelindung)
        POIFSFileSystem fs = new POIFSFileSystem();

        // jadi di dalam kontainer ini, isinya adalah doc word yang encrypted (isinya gbs dibaca)
        // encriptionInfo itu berisi informasi metadata dokumen
        // password yang exist digunakan sebagai key untuk mengubah dokumen mentah word menjadi encrypted format
        //
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
        Encryptor encryptor = info.getEncryptor();

        encryptor.confirmPassword(pw);

        // proses memasukan dokumen .docx asli ke dalam encrypted stream
        try (  InputStream is = new FileInputStream(src);
               OutputStream os = encryptor.getDataStream(fs))
        {
            byte[] buffer = new byte[1024];
            int length;
            while((length = is.read(buffer))>0){
                os.write(buffer, 0, length);
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fs.writeFilesystem(fos);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTemp(String path) {
        File file = new File(path);
        if (file.delete()) {
            System.out.println(path + " File deleted");
        } else System.out.println(path + " doesn't exists");
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    public static String nilaiTransaksi(Map<String, String> data) {
        String amount = "";
        try {
            double settSum = 0;
            if (Double.parseDouble(data.get("ACCINT")) < 1.0) {
                settSum = (Double.parseDouble(data.get("ACCINT")) * -1) + (Double.parseDouble(data.get("NOMINAL")) * Double.parseDouble(data.get("PRICE")) * 0.01);
            } else {
                settSum = Double.parseDouble(data.get("ACCINT")) + (Double.parseDouble(data.get("NOMINAL")) * Double.parseDouble(data.get("PRICE")) * 0.01);
            }

            double settAmt = 0;
            if (data.get("SECID").substring(0, 4).equals("IDBI") || data.get("SECID").substring(0, 4).equals("IDSD") || data.get("SECID").substring(0, 3).equals("SPN")) {
                settAmt = Double.parseDouble(data.get("SETAMT"));
            } else if (data.get("PRODTYPE").substring(0, 2).equals("FR") || data.get("PRODTYPE").substring(0, 2).equals("VR") || data.get("PRODTYPE").equals("TR")) {
                settAmt = Double.parseDouble(data.get("SETAMT"));
            } else {
                settAmt = settSum;
            }

            amount = String.valueOf(settAmt * Double.parseDouble(data.get("KURS")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }


    public static boolean emateraiValidate(Map<String, String> data) {
        if (isNullOrEmpty(data.get("REVREASON")) && Double.parseDouble(nilaiTransaksi(data)) >= 5000000)
            return false;
        return true;
    }

    // VALIDATE EMATERAI UNTUK CONFO REPO
    public static String nilaiTransRepo(Map<String, String> data) {
        String amount = "";
        try {
            amount = String.valueOf(Double.parseDouble(data.get("FACEAMT")) * Double.parseDouble(data.get("KURS")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }


    public static boolean emateraiValidateRepo(Map<String, String> data) {
        if (isNullOrEmpty(data.get("REVREASON")) && Double.parseDouble(nilaiTransRepo(data)) >= 5000000)
            return false;
        return true;
    }

    public static String localizeDate(String inputdate, Locale locale) {
        Date date = new Date();
        SimpleDateFormat dateFormatCN = new SimpleDateFormat("dd MMMM yyyy", locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        try {
            date = dateFormat.parse(inputdate);
        } catch (Exception e) {
            log.info("Input date was not correct. Can not localize it.");
            return inputdate;
        }
        return dateFormatCN.format(date);
    }

    public static String formatDouble(Double number) {
        DecimalFormat format = new DecimalFormat("##.########");
        String ret = "";
        try {
            ret = format.format(number);
        } catch (Exception e) {
            log.info("Input number was not correct! number can't be decimal!");
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.info(errors.toString());
            return Double.toString(number);
        }
        return ret;
    }
    //TAMBAHAN REPO END

//    public static Map mapRowData(JsonElement data) {
//        JsonObject obj = data.getAsJsonObject();
//        JsonArray colData = obj.get("columnDatas").getAsJsonArray();
//        int colDataSize = colData.size();
//        Map<String, String> mappedCol = new HashMap<>();
//        for (int j = 0; j < colDataSize; j++) {
//            JsonObject col = colData.get(j).getAsJsonObject();
//            String colName = "", colVal = "";
//            try {
//                colName = col.get("key").toString();
//                colName = colName.replaceAll("\"", "");
//
//                if (col.has("value")) {
//                    try {
//                        JsonObject val1 = col.get("value").getAsJsonObject();
//                        if (val1.has("value")) {
//                            colVal = val1.get("value").toString();
//                        }
//                    } catch (IllegalStateException e) {
//                        colVal = col.get("value").toString();
//                    }
//                    colVal = colVal.replaceAll("\"", "");
//                    // colVal = colVal.replaceAll(" ","");
//                    colVal = StringUtils.trim(colVal);
//                    if (colVal.equalsIgnoreCase("null")) {
//                        colVal = "";
//                    }
//                }
//            } catch (Exception ex1) {
//                StringWriter errors = new StringWriter();
//                ex1.printStackTrace(new PrintWriter(errors));
//                log.error(errors.toString());
//            } finally {
//                if (colName.equals("CCYAMT")) {
////                    colVal = String.format ("%.2f", colVal);
//                }
//                mappedCol.put(colName, colVal);
//            }
//        }
//        return mappedCol;
//    }
//
//    public static String getEmailsMM(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData = ex.executeGetObject("CONFO_GET_EMAIL_MM");
//
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("EMAIL").split(",");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getEmailsFI(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData = ex.executeGetObject("CONFO_GET_EMAIL_FI");
//
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("EMAIL").split(",");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getEmailsRepo(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        try{
//            String jobResponseData = ex.executeGetObject("CONFO_GET_EMAIL_REPO");
//
//            //memasukkan email ke object
//            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//            JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//            JsonElement dataEmail = rowData.get(0);
//            Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//            String[] email = mappedData.get("EMAIL").split(",");
//
//            //menghilangkan nilai kosong
//            email = Arrays.stream(email)
//                    .filter(s -> (s != null && s.length() > 0))
//                    .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//            emails = String.join(",", email);
//        } catch (Exception e) {
//            log.info(">>>>>>>>>>>>>> Error Get Emails Confo REPO !!!! <<<<<<<<<<<<<<");
//            StringWriter errors = new StringWriter();
//            e.printStackTrace(new PrintWriter(errors));
//            log.error(errors.toString());
//        }
//
//        return emails;
//    }
//
//    public static String getEmailsFXIB(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData = ex.executeGetObject("CONFO_GET_EMAIL_FXIB");
//
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("EMAIL").split(",");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getEmailsFX(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData = ex.executeGetObject("CONFO_GET_EMAIL_FX");
//
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("EMAIL").split(",");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getFaxMM(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData =  ex.executeGetObject("CONFO_GET_FAXNO_MM");
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("FAXNO").split("_");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getFaxFI(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData =  ex.executeGetObject("CONFO_GET_FAXNO_FI");
//
//        if(!ConfoUtils.isNullOrEmpty(jobResponseData)){
//            //memasukkan email ke object
//            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//            JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//            if(rowData.size() > 0){
//                JsonElement dataEmail = rowData.get(0);
//                Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//                String[] email = mappedData.get("FAXNO").split("_");
//
//                //menghilangkan nilai kosong
//                email = Arrays.stream(email)
//                        .filter(s -> (s != null && s.length() > 0))
//                        .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//                emails = String.join(",", email);
//            }
//        }
//
//        return emails;
//    }
//
//    public static String getFaxRepo(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        try{
//            String jobResponseData =  ex.executeGetObject("CONFO_GET_FAXNO_REPO");
//
//            if(!ConfoUtils.isNullOrEmpty(jobResponseData)){
//                //memasukkan faxno ke object
//                JsonParser jsonParser = new JsonParser();
//                JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//                JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//                if(rowData.size() > 0){
//                    JsonElement dataEmail = rowData.get(0);
//                    Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//                    String[] email = mappedData.get("FAXNO").split("_");
//
//                    //menghilangkan nilai kosong
//                    email = Arrays.stream(email)
//                            .filter(s -> (s != null && s.length() > 0))
//                            .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//                    emails = String.join(",", email);
//                }
//            }
//        } catch (Exception e) {
//            log.info(">>>>>>>>>>>>>> Error Get Fax Confo REPO !!!! <<<<<<<<<<<<<<");
//            StringWriter errors = new StringWriter();
//            e.printStackTrace(new PrintWriter(errors));
//            log.error(errors.toString());
//        }
//        return emails;
//    }
//
//    public static String getFaxFXIB(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData =  ex.executeGetObject("CONFO_GET_FAXNO_FXIB");
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("FAXNO").split("_");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//
//    public static String getFaxFX(Map<String, String> data) {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData =  ex.executeGetObject("CONFO_GET_FAXNO_FX");
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("FAXNO").split("_");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }

    public static List<String> addToEmailList(Map<String, String> data) {
        List<String> to = new ArrayList<>();
        // add email from query result to list
        for (int i = 1; i <= 15; i++) {
            String em = i == 1 ? "EMAIL" : "EMAIL" + i;
            try {
                if (data.get(em).length() > 0) {
                    to.add(data.get(em));
                }
            } catch (NullPointerException e) {

                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                log.error(errors.toString());
            }
        }
        return to;
    }

    public static List<String> addToFaxList(Map<String, String> data) {
        List<String> to = new ArrayList<>();
        // add email from query result to list
        for (int i = 1; i <= 10; i++) {
            String em = i == 1 ? "FAXNO" : "FAXNO" + i;
            try {
                if (data.get(em).length() > 0) {
                    to.add(data.get(em));
                }
            } catch (NullPointerException e) {

                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                log.error(errors.toString());
            }
        }
        return to;
    }

    public static String formatNumber(String number) {
        float fNumber = Float.parseFloat(number);
        NumberFormat formatter = new DecimalFormat("#,###,###,###,###.00");
        String ret = formatter.format(fNumber);
        if (ret.equals(".00")) {
            ret = "0.00";
        }
        return ret;
    }

    public static String formatNumber(double number) {
        NumberFormat formatter = new DecimalFormat("#,###,###,###,###.00");
        String ret = formatter.format(number);
        if (ret.equals(".00")) {
            ret = "0.00";
        }
        return ret;
    }

    public static String formatNumberr(String number) {
        String pagar = "###,###.00";
        DecimalFormat decimalFormat = new DecimalFormat(pagar);
        String hasil = decimalFormat.format(Double.valueOf(number));
        return hasil;
    }

    public static String formatNumber1(String number) {
        BigDecimal bigDecimalVal = new BigDecimal(number);
        BigDecimal bigDecimalVal1 = bigDecimalVal.abs();
        NumberFormat formatter = new DecimalFormat("#,###,###,###,###.00");
        String ret = formatter.format(bigDecimalVal1);
        if (ret.equals(".00")) {
            ret = "0.00";
        }
        return ret;
    }


//    public static String formatNumber(float number){
//        NumberFormat formatter = new De
//        return ret;
//    }

    public static String getCurrentTimeStr() {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();
        return fmt.format(d);
    }

    public static String yyyymmddToYyyy_mm_dd(String d) {
        return d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
    }
}

//    public static List<Map> responseToList(String jobResponse) {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObjectView = jsonParser.parse(jobResponse).getAsJsonObject();
//
//        JsonArray rowDataView = jsonObjectView.get("rowData").getAsJsonArray();
//
//        int dataViewSize = rowDataView.size();
//        List<Map> dataViewResult = new ArrayList<>();
//
//        for (int i = 0; i < dataViewSize; i++) {
//            JsonElement data = rowDataView.get(i);
//            Map<String, String> mappedData = ConfoUtils.mapRowData(data);
//            dataViewResult.add(mappedData);
//        }
//
//        return dataViewResult;
//    }
//
//    public CommonService getCommonService() {
//        return commonService;
//    }
//
//    public void setCommonService(CommonService commonService) {
//        this.commonService = commonService;
//    }
//
//    public static String getEmailsMtm() {
//        String emails = "";
//        ExecuteCoreJob ex = new ExecuteCoreJob();
//
//        String jobResponseData = ex.executeGetObject("MTM_GET_EMAIL");
//
//
//        //memasukkan email ke object
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = jsonParser.parse(jobResponseData).getAsJsonObject();
//        JsonArray rowData = jsonObject.get("rowData").getAsJsonArray();
//        JsonElement dataEmail = rowData.get(0);
//        Map<String, String> mappedData = ConfoUtils.mapRowData(dataEmail);
//        String[] email = mappedData.get("EMAIL").split(",");
//
//        //menghilangkan nilai kosong
//        email = Arrays.stream(email)
//                .filter(s -> (s != null && s.length() > 0))
//                .toArray(String[]::new);
//
////        List<String> emailList = addToEmailList(emailResult);
//        emails = String.join(",", email);
//        return emails;
//    }
//}
