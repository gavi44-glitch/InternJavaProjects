package com.example.demo.pdf;

import com.example.demo.utils.DocxUtils;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;


public class HeaderFooterAll implements IEventHandler {
    private Table headerTable;
    private Table footerTable;

    public HeaderFooterAll(){

    }

    public HeaderFooterAll(Table headerTable, Table footerTable) {
        this.headerTable = headerTable;
        this.footerTable = footerTable;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();

        // konstruktor PdfCanvas ringkas
        PdfCanvas pdfCanvas = new PdfCanvas(page);

        float marginX = 72f; // Margin kiri-kanan (0.5 inci)
        float pageWidth = page.getPageSize().getWidth() - (marginX * 2);


        // 1. ATUR POSISI HEADER (Terkunci di Atas)
        Rectangle headerArea = new Rectangle(marginX, 735f, pageWidth, 80f);
        Canvas headerCanvas = new Canvas(pdfCanvas, headerArea);
        headerCanvas.add(headerTable);
        headerCanvas.close();

        // 2. ATUR POSISI FOOTER (Terkunci di Bawah)
        Rectangle footerArea = new Rectangle(marginX, 20f, pageWidth, 40f);
        Canvas footerCanvas = new Canvas(pdfCanvas, footerArea);
        footerCanvas.add(footerTable);
        footerCanvas.close();
    }

    // UNTUK DOKUMEN WORDS
//    @Autowired
//    private DocxUtils docxUtils;

    private DocxUtils docxUtils = new DocxUtils();

    public void attachHeaderTable(XWPFHeader header) {

        // membuat table 1 row 2 kolom
        XWPFTable headerTable = header.createTable(1, 2);
        removeTableBorders(headerTable);

        CTTblPr tblPr = headerTable.getCTTbl().getTblPr();
        if (tblPr == null) tblPr = headerTable.getCTTbl().addNewTblPr();

        // Set total lebar TABEL menjadi 5000 pct => di xml = 100%
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setType(STTblWidth.PCT);
        tblWidth.setW(BigInteger.valueOf(5000));

        // B. PAKSA TATA LETAK AUTOFIT/AUTO AGAR SEL KANAN MENGISI SELURUH SISA RUANG
        CTTblLayoutType layoutType = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        layoutType.setType(STTblLayoutType.AUTOFIT);

        // C. SET INDENTASI TABEL KIRI = 0 (Rata kiri penuh)
        CTTblWidth tblInd = tblPr.isSetTblInd() ? tblPr.getTblInd() : tblPr.addNewTblInd();
        tblInd.setType(STTblWidth.DXA);
        tblInd.setW(BigInteger.valueOf(0));

        headerTable.setCellMargins(0, 0, 0, 0);

        XWPFTableRow headerRow = headerTable.getRow(0);

        // 2. Kolom 1: Sisipkan Gambar Logo
        XWPFTableCell cellLeft = headerRow.getCell(0);
        XWPFParagraph imageParagraph = cellLeft.getParagraphs().get(0);
        imageParagraph.setAlignment(ParagraphAlignment.LEFT);
        imageParagraph.setSpacingBefore(0);
        imageParagraph.setSpacingAfter(0);
        XWPFRun imageRun = imageParagraph.createRun();

        String imagePath = "resources/confo/permata_pdf.png";
        try (InputStream imageStream = new FileInputStream(imagePath)) {
            int format = Document.PICTURE_TYPE_PNG;

            // Atur lebar & tinggi gambar dalam satuan Pixel (misal: 121 x 32 px)
            int widthPx = 121;  // 3.2 cm
            int heightPx = 32;  // 0.85 cm

            imageRun.addPicture(
                    imageStream,
                    format,
                    "logo.png",
                    Units.toEMU(widthPx),
                    Units.toEMU(heightPx)
            );
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        XWPFTableCell cellRight = headerRow.getCell(1);

        // 3. Kolom 2: Teks Kanan
        XWPFParagraph pHeader = cellRight.getParagraphs().get(0);
        pHeader.setAlignment(ParagraphAlignment.RIGHT);
        pHeader.setSpacingBefore(0);
        pHeader.setSpacingAfter(0);

        String blackColor = "000000";

        XWPFRun run1 = pHeader.createRun();
        run1.setText("PermataBank Tower III Tbk.");
//        run1.setFontSize(8.5);

        float targetSizeRun1 = 8.5f;
        int halfPointsRun1 = Math.round(targetSizeRun1 * 2);
        CTRPr rpr = run1.getCTR().isSetRPr() ? run1.getCTR().getRPr() : run1.getCTR().addNewRPr();
        CTHpsMeasure szRun1 = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
        szRun1.setVal(BigInteger.valueOf(halfPointsRun1));

        run1.setColor(blackColor);
        run1.setBold(true); // Ekuivalen dengan fontMedium()
        run1.addBreak();    // Pindah baris (\n)

        XWPFRun run2 = pHeader.createRun();
        run2.setText("Treasury Operations.");
//        run2.setFontSize(8.5);

        float targetSizeRun2 = 8.5f;
        int halfPointsRun2 = Math.round(targetSizeRun2 * 2);
        CTRPr rprRun2 = run2.getCTR().isSetRPr() ? run2.getCTR().getRPr() : run2.getCTR().addNewRPr();
        CTHpsMeasure szRun2 = rprRun2.isSetSz() ? rprRun2.getSz() : rprRun2.addNewSz();
        szRun2.setVal(BigInteger.valueOf(halfPointsRun2));
        run2.setColor(blackColor);
        run2.addBreak();    // Pindah baris (\n)

        XWPFRun run3 = pHeader.createRun();
        run3.setText("Jl. M.H. Thamrin Blok B II No.1 - 11th Floor");
        run3.addBreak();
        run3.setText("Bintaro Jaya Sektor VII-CBD");
        run3.addBreak();
        run3.setText("Pondok Aren, Tangerang 15224");
        run3.addBreak();
        run3.setText("Indonesia.");
//        run3.setFontSize(8.5); // Format size 6.5 akan dibulatkan POI ke 6 atau 7
        float targetSizeRun3 = 8.5f;
        int halfPointsRun3 = Math.round(targetSizeRun3 * 2);
        CTRPr rprRun3 = run3.getCTR().isSetRPr() ? run3.getCTR().getRPr() : run3.getCTR().addNewRPr();
        CTHpsMeasure szRun3 = rprRun3.isSetSz() ? rprRun3.getSz() : rprRun3.addNewSz();
        szRun3.setVal(BigInteger.valueOf(halfPointsRun3));
        run3.setColor(blackColor);

        // Spasi / Margin bawah (Ekuivalen dengan Paragraph kosong + MultipliedLeading)
        pHeader.setSpacingAfter(100); // Satuan dxa/twips.

//        // 4. Atur Lebar Kolom (Pengaturan via CTTblWidth atau setWidth)
//        // Dalam POI, setWidth pada sel menggunakan satuan dxa (1 inch = 1440 dxa)
//        setCellWidthExact(cellLeft,3000);  // ~5.29 cm);
//        setCellWidthExact(cellRight,8226); // ~14.51 cm (Total = 11226 dxa)

        // atur lebar kolom dengan satuan PCT (percent)
        setCellWidthExact(cellLeft,1500);  // ~5.29 cm);
        setCellWidthExact(cellRight,3500); // ~14.51 cm (Total = 11226 dxa)

    }

    public void attachFooterTable(XWPFFooter footer){
        // membuat table 1 row 2 kolom
        XWPFTable footerTable = footer.createTable(1, 2);
        removeTableBorders(footerTable);

        // Set total lebar TABEL menjadi 5000 pct
        CTTblPr tblPr = footerTable.getCTTbl().getTblPr();
        if (tblPr == null) tblPr = footerTable.getCTTbl().addNewTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setType(STTblWidth.PCT);
        tblWidth.setW(BigInteger.valueOf(5000));

        // B. PAKSA TATA LETAK AUTOFIT/AUTO AGAR SEL KANAN MENGISI SELURUH SISA RUANG
        CTTblLayoutType layoutType = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        layoutType.setType(STTblLayoutType.AUTOFIT);

        // C. SET INDENTASI TABEL KIRI = 0 (Rata kiri penuh)
        CTTblWidth tblInd = tblPr.isSetTblInd() ? tblPr.getTblInd() : tblPr.addNewTblInd();
        tblInd.setType(STTblWidth.DXA);
        tblInd.setW(BigInteger.valueOf(0));

        footerTable.setCellMargins(0, 0, 0, 0);

        XWPFTableRow footerRow = footerTable.getRow(0);

        // 2. Kolom 1: Sisipkan Gambar Logo
        XWPFTableCell cellLeft = footerRow.getCell(0);
        XWPFParagraph textParagraph = cellLeft.getParagraphs().get(0);
        textParagraph.setAlignment(ParagraphAlignment.LEFT);
        textParagraph.setSpacingBefore(0);
        textParagraph.setSpacingAfter(0);
        XWPFRun textRun = textParagraph.createRun();

        String blackColor = "000000";

        textRun.setText("Restricted");

//        textRun.setFontSize(7.5);
        float targetSizeTextRun = 7.5f;
        int halfPointsTextRun = Math.round(targetSizeTextRun * 2);
        CTRPr rprTextRun = textRun.getCTR().isSetRPr() ? textRun.getCTR().getRPr() : textRun.getCTR().addNewRPr();
        CTHpsMeasure szTitle = rprTextRun.isSetSz() ? rprTextRun.getSz() : rprTextRun.addNewSz();
        szTitle.setVal(BigInteger.valueOf(halfPointsTextRun));
        textRun.setColor(blackColor);
        textRun.setFontFamily(docxUtils.fontBBLRegularItalic());
//        textRun.setBold(true); // Ekuivalen dengan fontMedium()
//        run1.addBreak();    //

        XWPFTableCell cellRight = footerRow.getCell(1);
        XWPFParagraph textParagraphRight = cellRight.getParagraphs().get(0);
        textParagraphRight.setAlignment(ParagraphAlignment.RIGHT);
        textParagraphRight.setSpacingBefore(0);
        textParagraphRight.setSpacingAfter(0);
        XWPFRun textRunRight = textParagraphRight.createRun();

        textRunRight.setText("PT Bank Permata, Tbk. berizin dan diawasi oleh Otoritas Jasa Keuangan dan Bank Indonesia");
//        textRunRight.setFontSize(7.5);
        float targetSizeTextRunRight = 7.5f;
        int halfPointsTextRunRight = Math.round(targetSizeTextRunRight * 2);
        CTRPr rprTextRunRight = textRunRight.getCTR().isSetRPr() ? textRunRight.getCTR().getRPr() : textRunRight.getCTR().addNewRPr();
        CTHpsMeasure szTextRunRight = rprTextRunRight.isSetSz() ? rprTextRunRight.getSz() : rprTextRunRight.addNewSz();
        szTextRunRight.setVal(BigInteger.valueOf(halfPointsTextRunRight));
        textRunRight.setColor(blackColor);
        textRunRight.setFontFamily(docxUtils.fontBBLRegular());

//        // 4. Atur Lebar Kolom (Pengaturan via CTTblWidth atau setWidth)
//        // Dalam POI, setWidth pada sel menggunakan satuan dxa (1 inch = 1440 dxa)
//        setCellWidthExact(cellLeft,3000);  // ~5.29 cm);
//        setCellWidthExact(cellRight,8226); // ~14.51 cm (Total = 11226 dxa)

        // atur lebar kolom dengan satuan PCT (percent)
        setCellWidthExact(cellLeft,1500);  // ~5.29 cm);
        setCellWidthExact(cellRight,3500); // ~14.51 cm (Total = 11226 dxa)
    }

    // helper untuk lock lebar sel secara matematis
    private void setCellWidthExact(XWPFTableCell cell, int widthInPct) {
        CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
        CTTblWidth cellWidth = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
        cellWidth.setType(STTblWidth.PCT);
        cellWidth.setW(BigInteger.valueOf(widthInPct));
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
