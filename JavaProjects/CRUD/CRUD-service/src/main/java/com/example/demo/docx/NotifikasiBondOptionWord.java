package com.example.demo.docx;
import com.example.demo.pdf.HeaderFooterAll;
import com.example.demo.pdf.HeaderFooterHandler;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
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
public class NotifikasiBondOptionWord {
    private final static Logger log = Logger.getLogger(com.example.demo.pdf.TCFXOption.class);

    private Map<String, String> data;
//    public FontUtils fontUtils = new FontUtils();

    @Autowired
    private FontUtils fontUtils;

//    @Autowired
//    private DocxUtils docxUtils;
    private DocxUtils docxUtils = new DocxUtils();

//    @Autowired
//    private HeaderFooterHandlerWord headerFooterHandlerWord;

    private HeaderFooterAll headerFooterAll = new HeaderFooterAll();

    @Value("${app.pdf.directory}")
    private String dir;

    public String createWord() {

        String password = "PASSWORDHEHE";
        String fileName = "Notifikasi Bond Option";
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
            headerFooterAll.attachHeaderTable(header);

            XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);
            headerFooterAll.attachFooterTable(footer);

            XWPFParagraph pPerihal = doc.createParagraph();
            pPerihal.setAlignment(ParagraphAlignment.LEFT);

            addRun(pPerihal, "Perihal   : ", 9.5f, docxUtils.fontBBLRegular(), false);
            addRun(pPerihal, "Pemberitahuan Status Eksekusi Transaksi Bond Option - ", 9.5f, docxUtils.fontBBLRegular(), false);

            XWPFRun runPerihal = pPerihal.createRun();
            runPerihal.setText("[Nomor Transaksi]");
//            runPerihal.setFontSize(9.5f);
            float targetSizeRunPerihal = 9.5f;
            int halfPointsRunPerihal = Math.round(targetSizeRunPerihal * 2);
            CTRPr rprPerihal = runPerihal.getCTR().isSetRPr() ? runPerihal.getCTR().getRPr() : runPerihal.getCTR().addNewRPr();
            CTHpsMeasure szPerihal = rprPerihal.isSetSz() ? rprPerihal.getSz() : rprPerihal.addNewSz();
            szPerihal.setVal(BigInteger.valueOf(halfPointsRunPerihal));
            runPerihal.setFontFamily(docxUtils.fontBBLRegular());
//            runPerihal.setTextHighlightColor("yellow");

            // tabel data setelah perihal
            createDataHeader(doc);

            // Pengantar data detail
            XWPFParagraph pPengantarDetail = doc.createParagraph();
            pPengantarDetail.setAlignment(ParagraphAlignment.LEFT);

//            pPengantarDetail.setSpacingBefore(160);
//            pPengantarDetail.setSpacingAfter(160);

            addRun(pPengantarDetail, "Kami informasikan bahwa transaksi ", 9.5f, docxUtils.fontBBLRegular(), false);
            addRun(pPengantarDetail, "Bond Option ", 9.5f, docxUtils.fontBBLMedium(), false);
            addRun(pPengantarDetail, "yang Bapak/Ibu lakukan dengan PT Bank Permata. Tbk \n" +
                    "memiliki tanggal jatuh tempo pada [dd/mm/yyyy]. ", 9.5f, docxUtils.fontBBLRegular(), false);
//            XWPFRun run1 = pPengantarDetail.createRun();
//            run1.setFontSize(6.0f);
//            run1.addBreak();
            addRun(pPengantarDetail, "Berdasarkan kondisi pasar dan ketentuan dalam perjaniian transaksi, berikut kami sampaikan ",9.5f, docxUtils.fontBBLRegular(),false);
            addRun(pPengantarDetail, "status akhir dari transaksi ",9.5f, docxUtils.fontBBLRegular(),false);
            addRun(pPengantarDetail, "tersebut:",9.5f, docxUtils.fontBBLRegular(),false);

            XWPFRun run2 = pPengantarDetail.createRun();
            run2.setFontSize(6);
            run2.addBreak();
            // tabel data detail
            createDataDetail(doc);

            XWPFParagraph pPenutup = doc.createParagraph();
            pPenutup.setAlignment(ParagraphAlignment.LEFT);

            pPenutup.setSpacingBefore(160);
            pPenutup.setSpacingAfter(160);

            addRun(pPenutup,"*Fixing rate merupakan harga yang dinformasikan oleh Bank kepada Nasabah berdasarkan IDMA Mid-Rate dari \n" +
                    "Bloomberg di tanggal dan waktu Fixing pukul 11:00 WIB.",9.5f,docxUtils.fontBBLRegularItalic(),false).addBreak();
            XWPFRun runPenutup1 = pPenutup.createRun();
//            runPenutup1.setFontSize(6.5f);
            float targetSizeRunPenutup1 = 6.5f;
            int halfPointsRunPenutup1 = Math.round(targetSizeRunPenutup1 * 2);
            CTRPr rprPenutup1 = runPenutup1.getCTR().isSetRPr() ? runPenutup1.getCTR().getRPr() : runPenutup1.getCTR().addNewRPr();
            CTHpsMeasure szPenutup1 = rprPenutup1.isSetSz() ? rprPenutup1.getSz() : rprPenutup1.addNewSz();
            szPenutup1.setVal(BigInteger.valueOf(halfPointsRunPenutup1));
            runPenutup1.addBreak();

            addRun(pPenutup, "Untuk informasi lebih lanjut, Bapak/Ibu dapat menghubungi Treasury Sales atau Relationship " +
                    "Manager kami yang biasa menangani akun Bapak Ibu. ",9.5f,docxUtils.fontBBLRegular(),false);
            addRun(pPenutup, "Demikian kami sampaikan, Terima kasih atas kepercayaan Bapak/ Ibu kepada Bank Permata.",9.5f,docxUtils.fontBBLRegular(),false).addBreak();
            XWPFRun runPenutup2 = pPenutup.createRun();
            runPenutup2.setFontSize(6);
            runPenutup2.addBreak();

            addRun(pPenutup,"Hormat kami ",9.5f,docxUtils.fontBBLRegular(),false).addBreak();
            addRun(pPenutup, "Treasury Operations",9.5f,docxUtils.fontBBLRegular(),false).addBreak();
            addRun(pPenutup, "PT Bank Permata Tbk",9.5f, docxUtils.fontBBLRegular(),false).addBreak();

            XWPFRun runPenutup3 = pPenutup.createRun();
//            runPenutup3.setFontSize(9.5f);
            float targetSizeRunPenutup3 = 9.5f;
            int halfPointsRunPenutup3 = Math.round(targetSizeRunPenutup3 * 2);
            CTRPr rprPenutup3 = runPenutup3.getCTR().isSetRPr() ? runPenutup3.getCTR().getRPr() : runPenutup3.getCTR().addNewRPr();
            CTHpsMeasure szPenutup3 = rprPenutup3.isSetSz() ? rprPenutup3.getSz() : rprPenutup3.addNewSz();
            szPenutup3.setVal(BigInteger.valueOf(halfPointsRunPenutup3));
            runPenutup3.addBreak();

            addRun(pPenutup,"*Notifikasi tidak memerlukan balasan maupun tanda tangan Nasabah",7.5f,docxUtils.fontBBLRegularItalic(),false);

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

        XWPFParagraph pSalamPembuka = doc.createParagraph();
        addRun(pSalamPembuka, "Kepada Yth. Bapak/Ibu",9.5f,docxUtils.fontBBLRegular(),false).addBreak();
        addRun(pSalamPembuka, "[Nama Nasabah],",9.5f,docxUtils.fontBBLRegular(),false).addBreak();

//        XWPFRun runSalamPembuka1 = pSalamPembuka.createRun();
//        runSalamPembuka1.setFontSize(6.0f);
//        runSalamPembuka1.addBreak();
        addRun(pSalamPembuka, "Di tempat",9.5f,docxUtils.fontBBLRegular(),false).addBreak();

        XWPFRun runSalamPembuka2 = pSalamPembuka.createRun();
        runSalamPembuka2.setFontSize(6);
        runSalamPembuka2.addBreak();

        addRun(pSalamPembuka, "Dengan hormat,",9.5f,docxUtils.fontBBLRegular(),false).addBreak();
//        XWPFRun runSalamPembuka3 = pSalamPembuka.createRun();
//        runSalamPembuka3.setFontSize(6.0f);
//        runSalamPembuka3.addBreak();
    }

    public void createDataDetail(XWPFDocument doc) throws IOException {

        XWPFTable tableDetail = doc.createTable(18,3);
        removeTableBorders(tableDetail);

        // ukuran dalam satuan twips
        setTableWidths(tableDetail, new int[]{4800, 420, 6006});

        XWPFTableRow row0 = tableDetail.getRow(0);
        formatCell(row0.getCell(0), "Detail Transaksi", docxUtils.fontBBLMedium(), "000000", 9.5f, false, ParagraphAlignment.LEFT, "F3F3F3");
        formatCell(row0.getCell(1), "", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, "F3F3F3");
        formatCell(row0.getCell(2), "Keterangan", docxUtils.fontBBLMedium(), "000000", 9.5f, false, ParagraphAlignment.LEFT, "F3F3F3");

        XWPFTableRow row1 = tableDetail.getRow(1);
        formatCell(row1.getCell(0), "Transaksi Nasabah", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row1.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row1.getCell(2), "[Beli / Jual]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row2 = tableDetail.getRow(2);
        formatCell(row2.getCell(0), "Jenis Opsi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row2.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row2.getCell(2), "[Call / Put]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row3 = tableDetail.getRow(3);
        formatCell(row3.getCell(0), "Option Style", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row3.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row3.getCell(2), "European Style", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row4 = tableDetail.getRow(4);
        formatCell(row4.getCell(0), "Nama Obligasi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row4.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row4.getCell(2), "[Contoh: FR0102]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row5 = tableDetail.getRow(5);
        formatCell(row5.getCell(0), "Kode ISIN", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row5.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row5.getCell(2), "[Contoh: IDG000032XXXX]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row6 = tableDetail.getRow(6);
        formatCell(row6.getCell(0), "Nama Penerbit", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row6.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row6.getCell(2), "Pemerintah Republik Indonesia", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row7 = tableDetail.getRow(7);
        formatCell(row7.getCell(0), "Tanggal Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row7.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row7.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row8 = tableDetail.getRow(8);
        formatCell(row8.getCell(0), "Tanggal Valuta Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row8.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row8.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row9 = tableDetail.getRow(9);
        formatCell(row9.getCell(0), "Tanggal Valuta Premi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row9.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row9.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row10 = tableDetail.getRow(10);
        formatCell(row10.getCell(0), "Tanggal dan Waktu Fixing", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row10.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row10.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row11 = tableDetail.getRow(11);
        formatCell(row11.getCell(0), "Tanggal Jatuh Tempo", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row11.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row11.getCell(2), "[DD/MM/YYYY]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row12 = tableDetail.getRow(12);
        formatCell(row12.getCell(0), "Nominal Transaksi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row12.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row12.getCell(2), "[Contoh: USD 100,000]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row13 = tableDetail.getRow(13);
        formatCell(row13.getCell(0), "Harga Strike", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row13.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row13.getCell(2), "[Contoh: 102.50% dari nilai nominal]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);


        XWPFTableRow row14 = tableDetail.getRow(14);
        formatCell(row14.getCell(0), "*Harga Fixing (Fixing Rate)", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row14.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row14.getCell(2), "[Contoh: 101.00% dari nilai nominal]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);

        XWPFTableRow row15 = tableDetail.getRow(15);
        formatCell(row15.getCell(0), "Status Eksekusi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row15.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row15.getCell(2), "[EXERCISE / EXPIRY]", docxUtils.fontBBLRegularItalic(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null); // Cetak tebal / miring sesuai format dokumen

        XWPFTableRow row16 = tableDetail.getRow(16);
        formatCell(row16.getCell(0), "Tanggal Eksekusi", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row16.getCell(1), ":", docxUtils.fontBBLRegular(), "000000", 9.5f, false, ParagraphAlignment.LEFT, null);
        formatCell(row16.getCell(2), "[DD/MM/YYYY atau -]", docxUtils.fontBBLRegular(), "808080", 9.5f, false, ParagraphAlignment.LEFT, null);
    }


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }


//    private XWPFRun addRun(XWPFParagraph p, String text, float size, String font, boolean bold) {
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

//    private static void formatCell(XWPFTableCell cell, String text, String fontName, String fontColor, float fontSize,
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
//        // --- PENGATURAN BORDER (808080) ---
//        CTTcPr cellProperties = cell.getCTTc().isSetTcPr()
//                ? cell.getCTTc().getTcPr()
//                : cell.getCTTc().addNewTcPr();
//
//        CTTcBorders borders = cellProperties.isSetTcBorders()
//                ? cellProperties.getTcBorders()
//                : cellProperties.addNewTcBorders();
//
//        String borderColorHex = "F3F3F3"; // ini light grey sesuai dokumen panduan word
//        BigInteger borderWidth = BigInteger.valueOf(8); // Ukuran 8/8 pt = 1 pt
//
//        setSingleBorder(borders.isSetTop() ? borders.getTop() : borders.addNewTop(), borderColorHex, borderWidth);
//        setSingleBorder(borders.isSetBottom() ? borders.getBottom() : borders.addNewBottom(), borderColorHex, borderWidth);
//        setSingleBorder(borders.isSetLeft() ? borders.getLeft() : borders.addNewLeft(), borderColorHex, borderWidth);
//        setSingleBorder(borders.isSetRight() ? borders.getRight() : borders.addNewRight(), borderColorHex, borderWidth);
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
    // Helper method internal agar kode tetap rapi
    private static void setSingleBorder(CTBorder border, String colorHex, BigInteger size) {
        border.setVal(STBorder.SINGLE);
        border.setColor(colorHex);
        border.setSz(size);
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

