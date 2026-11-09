package com.example.demo.docx;
import com.example.demo.pdf.HeaderFooterHandler;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.demo.utils.DocxUtils;
import org.apache.poi.xwpf.usermodel.*;

import com.example.demo.utils.ConfoUtils;
import com.example.demo.utils.FontUtils;
import org.apache.log4j.Logger;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Component
public class TCFXOptionWord {
    private final static Logger log = Logger.getLogger(com.example.demo.pdf.TCFXOption.class);

    private Map<String, String> data;
//    public FontUtils fontUtils = new FontUtils();

    @Autowired
    private FontUtils fontUtils;

    @Autowired
    private DocxUtils docxUtils;

    @Autowired
    private HeaderFooterHandlerWord headerFooterHandlerWord;

    @Value("${app.pdf.directory}")
    private String dir;
    
    public String createWord() {

        String password = "PASSWORDHEHE";
        String fileName = "TC FX Option";
        String baseFile = dir + fileName + ".docx";
        String secFile = dir + fileName + "_secured.docx";
        try {
            ConfoUtils.deleteTemp(baseFile);
            ConfoUtils.deleteTemp(secFile);
        }catch (Exception e){
            e.getCause();
        }

        Map<String,String> data = new HashMap<>();

        data.put("PASSWORD",password);

        createBaseFile(data, baseFile);
        String pw = null;

        try {
            pw = data.get("PASSWORD");
            log.info("----------------------------------------------------------------------- pasword +" + data.get("PASSWORD"));
        } catch (NullPointerException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }

        if (!ConfoUtils.isNullOrEmpty(pw)) {
            ConfoUtils.secureWord(baseFile, secFile, data.get("PASSWORD"));
            return secFile;
        }

        return baseFile;
    }

    public void createBaseFile(Map<String, String> data, String file) {
        this.data = data;

        XWPFDocument doc = new XWPFDocument();

        docxUtils.setPageSizeAndMargins(doc, 2.0, 0.6, 1.6, 1.0, 1.6);

        try {

            // memanggil class yang define header dan footer

            XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);
            headerFooterHandlerWord.attachHeaderTable(header);

            XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);
            headerFooterHandlerWord.attachFooterTable(footer);

            XWPFParagraph pTitle = doc.createParagraph();
            pTitle.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun runTitle = pTitle.createRun();
            runTitle.setText("KONFIRMASI TRANSAKSI FX OPTION");
//            runTitle.setBold(true);
//            runTitle.setFontSize(12.5f);
            // Terapkan langsung ke XML underlying (TIDAK perlu run.setFontSize lagi)
            float targetSizeTitle = 12.5f;
            int halfPoints = Math.round(targetSizeTitle * 2);
            CTRPr rpr = runTitle.getCTR().isSetRPr() ? runTitle.getCTR().getRPr() : runTitle.getCTR().addNewRPr();
            CTHpsMeasure szTitle = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
            szTitle.setVal(BigInteger.valueOf(halfPoints));


            runTitle.setUnderline(UnderlinePatterns.THICK);
            runTitle.setFontFamily(docxUtils.fontBBLMedium());
            // (1 pt = 20 twips)
            //  12pt = 12 * 20 = 240 twips
            pTitle.setSpacingAfter(240);
//            runTitle.addBreak();

            XWPFParagraph pPerihal = doc.createParagraph();
            pPerihal.setAlignment(ParagraphAlignment.LEFT);

            addRun(pPerihal, "Perihal   : ", 9.5f, docxUtils.fontBBLRegular(), false);
            addRun(pPerihal, "Konfirmasi Transaksi FX Option - [Nomor Transaksi]", 9.5f, docxUtils.fontBBLMedium(), false);

            // tabel data setelah perihal
            createDataHeader(doc);

            // Pengantar data detail
            XWPFParagraph pPengantarDetail = doc.createParagraph();
            pPengantarDetail.setAlignment(ParagraphAlignment.LEFT);

            pPengantarDetail.setSpacingBefore(160);
            pPengantarDetail.setSpacingAfter(160);

            addRun(pPengantarDetail, "Terima kasih telah melakukan transaksi ", 9.5f, docxUtils.fontBBLRegular(), false);
            addRun(pPengantarDetail, "FX Option ", 9.5f, docxUtils.fontBBLMedium(), false);
            addRun(pPengantarDetail, "dengan PT Bank Permata. Tbk. Berikut kami sampaikan " +
                    "detail transaksi yang telah diselesaikan :", 9.5f, docxUtils.fontBBLRegular(), false);

            // tabel data detail
            createDataDetail(doc);

            XWPFParagraph pPenutup = doc.createParagraph();
            pPenutup.setAlignment(ParagraphAlignment.LEFT);

            pPenutup.setSpacingBefore(160);
            pPenutup.setSpacingAfter(160);

            XWPFRun run1 = pPenutup.createRun();
            run1.setText("*Nominal akan terkonversi apabila harga menyentuh atau melewati harga strike sesuai dengan ketentuan kontrak FX Option.");
//            run1.setFontSize(9.5f);
            float targetSizeRun1 = 9.5f;
            int halfPointsRun1 = Math.round(targetSizeRun1 * 2);
            CTRPr rprRun1 = run1.getCTR().isSetRPr() ? run1.getCTR().getRPr() : run1.getCTR().addNewRPr();
            CTHpsMeasure szRun1 = rprRun1.isSetSz() ? rprRun1.getSz() : rprRun1.addNewSz();
            szRun1.setVal(BigInteger.valueOf(halfPointsRun1));

            run1.setFontFamily(docxUtils.fontBBLRegular());
            run1.addBreak(); // Pindah baris
            run1.addBreak(); // Pindah baris

            XWPFRun run2 = pPenutup.createRun();
            run2.setText("Konfirmasi ini tidak memerlukan balasan maupun tanda tangan. Apabila rincian transaksi di bawah ini tidak sesuai dengan kesepakatan yang Bapak/ Ibu sudah lakukan, mohon segera menghubungi Bank Permata untuk klarifikasi lebih lanjut.");
//            run2.setFontSize(9.5f);
            float targetSizeRun2 = 9.5f;
            int halfPointsRun2 = Math.round(targetSizeRun2 * 2);
            CTRPr rprRun2 = run2.getCTR().isSetRPr() ? run2.getCTR().getRPr() : run2.getCTR().addNewRPr();
            CTHpsMeasure szRun2 = rprRun2.isSetSz() ? rprRun2.getSz() : rprRun2.addNewSz();
            szRun2.setVal(BigInteger.valueOf(halfPointsRun2));
            run2.setFontFamily(docxUtils.fontBBLRegular());
            run2.addBreak(); // Pindah baris
            run2.addBreak(); // Pindah baris

            XWPFRun run3 = pPenutup.createRun();
            run3.setText("Demikian kami sampaikan, Terima kasih atas kepercayaan Bapak/ Ibu kepada Bank Permata.");
//            run3.setFontSize(9.5f);
            float targetSizeRun3 = 9.5f;
            int halfPointsRun3 = Math.round(targetSizeRun3 * 2);
            CTRPr rprRun3 = run3.getCTR().isSetRPr() ? run3.getCTR().getRPr() : run3.getCTR().addNewRPr();
            CTHpsMeasure szRun3 = rprRun3.isSetSz() ? rprRun3.getSz() : rprRun3.addNewSz();
            szRun3.setVal(BigInteger.valueOf(halfPointsRun3));
            run3.setFontFamily(docxUtils.fontBBLRegular());
            run3.addBreak(); // Pindah baris
            run3.addBreak(); // Pindah baris

            XWPFRun run4 = pPenutup.createRun();
            run4.setText("Hormat kami");
//            run4.setFontSize(9.
            float targetSizeRun4 = 9.5f;
            int halfPointsRun4 = Math.round(targetSizeRun4 * 2);
            CTRPr rprRun4 = run4.getCTR().isSetRPr() ? run4.getCTR().getRPr() : run4.getCTR().addNewRPr();
            CTHpsMeasure szRun4 = rprRun4.isSetSz() ? rprRun4.getSz() : rprRun4.addNewSz();
            szRun4.setVal(BigInteger.valueOf(halfPointsRun4));
            run4.setFontFamily(docxUtils.fontBBLRegular());
            run4.addBreak(); // Pindah baris

            XWPFRun run5 = pPenutup.createRun();
            run5.setText("Treasury Operations");
//            run5.setFontSize(9.5f);
            float targetSizeRun5 = 9.5f;
            int halfPointsRun5 = Math.round(targetSizeRun5 * 2);
            CTRPr rprRun5 = run5.getCTR().isSetRPr() ? run5.getCTR().getRPr() : run5.getCTR().addNewRPr();
            CTHpsMeasure szRun5 = rprRun5.isSetSz() ? rprRun5.getSz() : rprRun5.addNewSz();
            szRun5.setVal(BigInteger.valueOf(halfPointsRun5));
            run5.setFontFamily(docxUtils.fontBBLMedium());
            run5.addBreak(); // Pindah baris

            XWPFRun run6 = pPenutup.createRun();
            run6.setText("PT Bank Permata Tbk");
//            run6.setFontSize(9.5f);
            float targetSizeRun6 = 9.5f;
            int halfPointsRun6 = Math.round(targetSizeRun6 * 2);
            CTRPr rprRun6 = run6.getCTR().isSetRPr() ? run6.getCTR().getRPr() : run6.getCTR().addNewRPr();
            CTHpsMeasure szRun6 = rprRun6.isSetSz() ? rprRun6.getSz() : rprRun6.addNewSz();
            szRun6.setVal(BigInteger.valueOf(halfPointsRun6));
            run6.setFontFamily(docxUtils.fontBBLRegular());


            // Simpan File
            try (FileOutputStream out = new FileOutputStream(file)) {
                doc.write(out);
            } catch (Exception e) {
                log.info(e);
            }

        }catch(Exception e){
            log.info(e);
        }
    }

    public void  createDataHeader(XWPFDocument doc) throws IOException {

        XWPFTable tableHeader = doc.createTable(4,3);
        removeTableBorders(tableHeader);

        // atur lebar masing masing kolom
        // Kolom 0 (Label): 2000 twips (~3.5 cm)
        // Kolom 1 (Titik dua): 400 twips (~0.7 cm)
        // Kolom 2 (Isi): 4000 twips (~7.0 cm)
        setTableWidths(tableHeader, new int[]{3500, 400, 4000});

        XWPFTableRow row1 = tableHeader.getRow(0);
        formatCell(row1.getCell(0),"Kepada",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row1.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row1.getCell(2),"[Nama Nasabah]",docxUtils.fontBBLRegular(),"808080",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row2 = tableHeader.getRow(1);
        formatCell(row2.getCell(0),"Alamat",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row2.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row2.getCell(2),"",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row3 = tableHeader.getRow(2);
        formatCell(row3.getCell(0),"No.Telepon",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row3.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row3.getCell(2),"",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row4 = tableHeader.getRow(3);
        formatCell(row4.getCell(0),"E-mail",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row4.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row4.getCell(2),"",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);

    }

    public void createDataDetail(XWPFDocument doc) throws IOException {

        XWPFTable tableDetail = doc.createTable(15,3);
        removeTableBorders(tableDetail);

        // atur lebar masing masing kolom
        // Kolom 0 (Label): 2000 twips (~3.5 cm)
        // Kolom 1 (Titik dua): 400 twips (~0.7 cm)
        // Kolom 2 (Isi): 4000 twips (~7.0 cm)
        setTableWidths(tableDetail, new int[]{3500, 400, 4000});

        XWPFTableRow row1 = tableDetail.getRow(0);
        formatCell(row1.getCell(0),"Transaksi Nasabah",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row1.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row1.getCell(2),"[Beli/Jual]",docxUtils.fontBBLRegular(),"808080",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row2 = tableDetail.getRow(1);
        formatCell(row2.getCell(0),"Jenis Opsi",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row2.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row2.getCell(2),"[Call/Put]",docxUtils.fontBBLRegular(),"808080",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row3 = tableDetail.getRow(2);
        formatCell(row3.getCell(0),"Option Style",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row3.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row3.getCell(2),"European Style",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row4 = tableDetail.getRow(3);
        formatCell(row4.getCell(0),"Pasangan Mata Uang",docxUtils.fontBBLRegular(),"000000",9.5f,false,ParagraphAlignment.LEFT,null);
        formatCell(row4.getCell(1),":",docxUtils.fontBBLRegular(),"000000",9.5f,false, ParagraphAlignment.LEFT, null);
        formatCell(row4.getCell(2),"[Contoh: USD/IDR]",docxUtils.fontBBLRegular(),"808080",9.5f,false,ParagraphAlignment.LEFT,null);

        XWPFTableRow row5 = tableDetail.getRow(4);
        formatCell(row5.getCell(0), "Tanggal Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row5.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row5.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row6 = tableDetail.getRow(5);
        formatCell(row6.getCell(0), "Tanggal Valuta Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row6.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row6.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row7 = tableDetail.getRow(6);
        formatCell(row7.getCell(0), "Tanggal Valuta Premi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row7.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row7.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row8 = tableDetail.getRow(7);
        formatCell(row8.getCell(0), "Tanggal dan waktu Fixing", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row8.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row8.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row9 = tableDetail.getRow(8);
        formatCell(row9.getCell(0), "Tanggal Jatuh Tempo", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row9.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row9.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row10 = tableDetail.getRow(9);
        formatCell(row10.getCell(0), "Nominal Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row10.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row10.getCell(2), "[Contoh: USD 1,000,000]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row11 = tableDetail.getRow(10);
        formatCell(row11.getCell(0), "*Nominal Terkonversi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row11.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row11.getCell(2), "", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row12 = tableDetail.getRow(11);
        formatCell(row12.getCell(0), "Harga Strike", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row12.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row12.getCell(2), "", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row13 = tableDetail.getRow(12);
        formatCell(row13.getCell(0), "Harga Premi (%)", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row13.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row13.getCell(2), "[5%]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row14 = tableDetail.getRow(13);
        formatCell(row14.getCell(0), "Nominal Premi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row14.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row14.getCell(2), "[Contoh: USD 500]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row15 = tableDetail.getRow(14);
        formatCell(row15.getCell(0), "Status Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row15.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row15.getCell(2), "Telah dikonfirmasi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
    }


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }


//    private XWPFRun addRun(XWPFParagraph p, String text, int size, String font, boolean bold) {
//        XWPFRun run = p.createRun();
//        run.setText(text);
//        run.setFontSize(size);
//        if (font != null) run.setFontFamily(font);
//        run.setBold(bold);
//        return run;
//    }
    private XWPFRun addRun(XWPFParagraph p, String text, float size, String font, boolean bold) {
        XWPFRun run = p.createRun();
        run.setText(text);

        int halfPoints = Math.round(size * 2.0f);

        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();

        sz.setVal(BigInteger.valueOf(halfPoints));

        if (font != null) {
            run.setFontFamily(font);
        }
        run.setBold(bold);

        return run;
    }

//    private static void formatCell(XWPFTableCell cell, String text, String fontName, String fontColor, int fontSize,
//                                   boolean isBold, ParagraphAlignment alignment, String backgroundColorHex) {
//
//        if (!cell.getParagraphs().isEmpty()) {
//            cell.removeParagraph(0);
//        }
//
//        XWPFParagraph p = cell.addParagraph();
//        p.setAlignment(alignment);
//        p.setSpacingBefore(0);
//        p.setSpacingAfter(0);
//
//        XWPFRun run = p.createRun();
//        run.setText(text != null ? text : "");
//        run.setFontSize(fontSize);
//        run.setColor(fontColor);
//        run.setBold(isBold);
//        if (fontName != null && !fontName.isEmpty()) {
//            run.setFontFamily(fontName);
//        }
//
//        if (backgroundColorHex != null && !backgroundColorHex.isEmpty()) {
//            cell.setColor(backgroundColorHex);
//        }
//    }

    private static void formatCell(XWPFTableCell cell, String text, String fontName, String fontColor, float fontSize,
                                   boolean isBold, ParagraphAlignment alignment, String backgroundColorHex) {

        if (!cell.getParagraphs().isEmpty()) {
            cell.removeParagraph(0);
        }

        XWPFParagraph p = cell.addParagraph();
        p.setAlignment(alignment);
        p.setSpacingBefore(0);
        p.setSpacingAfter(0);

        XWPFRun run = p.createRun();
        run.setText(text != null ? text : "");

        // 1. Konversi ukuran float (pt) ke half-points (dikali 2)
        int halfPoints = Math.round(fontSize * 2.0f);

        // 2. Terapkan ukuran font presisi via XML (Menggantikan run.setFontSize)
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
        sz.setVal(BigInteger.valueOf(halfPoints));

        if (fontColor != null && !fontColor.isEmpty()) {
            run.setColor(fontColor);
        }

        run.setBold(isBold);

        if (fontName != null && !fontName.isEmpty()) {
            run.setFontFamily(fontName);
        }

        if (backgroundColorHex != null && !backgroundColorHex.isEmpty()) {
            cell.setColor(backgroundColorHex);
        }
    }
    private static void setTableWidths(XWPFTable table, int[] widthsInTwips) {
        for (XWPFTableRow row : table.getRows()) {
            for (int i = 0; i < widthsInTwips.length; i++) {
                if (i < row.getTableCells().size()) {
                    XWPFTableCell cell = row.getCell(i);
                    CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
                    CTTblWidth cellWidth = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
                    cellWidth.setType(STTblWidth.DXA);
                    cellWidth.setW(BigInteger.valueOf(widthsInTwips[i]));
                }
            }
        }
    }
    public void removeTableBorders(XWPFTable table) {

        // buat objek complex type table properties
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }

        // buat objek complex type table borders
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();

       // set semua border menjadi none
        borders.addNewTop().setVal(STBorder.NONE);
        borders.addNewLeft().setVal(STBorder.NONE);
        borders.addNewBottom().setVal(STBorder.NONE);
        borders.addNewRight().setVal(STBorder.NONE);
        borders.addNewInsideH().setVal(STBorder.NONE); // Garis horizontal antar sel
        borders.addNewInsideV().setVal(STBorder.NONE); // Garis vertikal antar sel
    }
}

