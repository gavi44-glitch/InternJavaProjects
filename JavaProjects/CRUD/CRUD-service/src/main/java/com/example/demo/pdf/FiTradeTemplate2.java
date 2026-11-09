package com.example.demo.pdf;

import com.example.demo.utils.ConfoUtils;
import com.example.demo.utils.FontUtils;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.layer.PdfLayer;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Component
public class FiTradeTemplate2 {
    private final static Logger log = Logger.getLogger(FiTradeTemplate2.class);
    private Map<String, String> data2;
    private FontUtils fontUtils = new FontUtils();

    //    @Override

    @Value("${app.pdf.directory}")
    private String dir;

    public String createPdf() {
        String cno = "CNOHEHE";
        String dealno = "DealnoHEHE";
//        String password = "PASSWORDHEHE";
        String fileName = "FI_" + cno + "_" + dealno;
        String baseFile = dir + fileName + ".pdf";
        String secFile = dir + fileName + "_secured.pdf";
        try {
            ConfoUtils.deleteTemp(baseFile);
            ConfoUtils.deleteTemp(secFile);
        }catch (Exception e){
            e.getCause();
        }

        Map<String,String> data = new HashMap<>();
        data.put("CNO",cno);
        data.put("DEALNO",dealno);

        createBaseFile(data, baseFile);

        String pw = null;

//        try {
//            pw = data.get("PASSWORD");
//            log.info("----------------------------------------------------------------------- password : " + data.get("PASSWORD"));
//        } catch (NullPointerException e) {
//            StringWriter errors = new StringWriter();
//            e.printStackTrace(new PrintWriter(errors));
//            log.error(errors.toString());
//        }

//        if (!ConfoUtils.isNullOrEmpty(pw)) {
//            ConfoUtils.securePdf(baseFile, secFile, data.get("PASSWORD"));
//            return secFile;
//        }
//        return baseFile;
        return "";
    }

    //    @Override
    public String createPdfReversal(Map<String, String> data, String dir) throws IOException {


        String cno = "CNOHEHE";
        String dealno = "DealnoHEHE";
//        String password = "PASSWORDHEHE";
        String fileName = "FI_" + cno + "_" + dealno;
        String baseFile = dir + fileName + ".pdf";

        String revFile = dir + fileName + "_reversal.pdf";
        String secFile = dir + fileName + "_reversal_secured.pdf";

        try {
//            ConfoUtils.deleteTemp(baseFile);
//            ConfoUtils.deleteTemp(revFile);
//            ConfoUtils.deleteTemp(secFile);
        } catch (Exception e) {
            e.getCause();
        }

        createRevFile(data, revFile);

//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(baseFile), new PdfWriter(revFile));
//        int numberOfPages = pdfDoc.getNumberOfPages();
//
//        PageSize ps = pdfDoc.getDefaultPageSize();
//        for (int i = 1; i <= numberOfPages; i++) {
//            PdfPage page = pdfDoc.getPage(i);
//
//            PdfLayer layer = new PdfLayer("watermark", pdfDoc);
//            PdfCanvas canvas = new PdfCanvas(page);
//            Rectangle pageSize = page.getPageSize();
//            Paragraph paragraph = new Paragraph("CANCELLED").setFontSize(80).setItalic();
//            paragraph.setOpacity(0.5f);
//
//            Canvas canvasModel;
//            canvas.beginLayer(layer);
//            canvasModel = new Canvas(canvas, pdfDoc, ps);
//            canvasModel.showTextAligned(paragraph, pageSize.getWidth() / 2, pageSize.getHeight() / 2, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
//            canvasModel.setOpacity(0.2f);
//            canvas.endLayer();
//        }
//        pdfDoc.close();

        String pw = null;

//        try {
//            pw = data.get("PASSWORD");
//            log.info("--------------------------------------------------------------- password : " + data.get("PASSWORD"));
//        } catch (NullPointerException e) {
//
//            StringWriter errors = new StringWriter();
//            e.printStackTrace(new PrintWriter(errors));
//            log.error(errors.toString());
//        }

//        if (!ConfoUtils.isNullOrEmpty(pw)) {
//            ConfoUtils.securePdf(revFile, secFile, data.get("PASSWORD"));
//            ConfoUtils.deleteTemp(baseFile);
//            return secFile;
//        } else {
//            ConfoUtils.deleteTemp(baseFile);
//            return revFile;
//        }
        return "";
    }

    //    @Override
    public void createBaseFile(Map<String, String> data, String file) {
        this.data2 = data;
        PdfWriter writer;
        try {
            writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4, false);
            document.setMargins(fontUtils.marginsTop(), fontUtils.marginRight(), fontUtils.marginsBottom(), fontUtils.marginLeft());

            // HEADER
            Table logo = fontUtils.headerLogoConven();
//            Paragraph address = fontUtils.address();

            // Title
            Paragraph pTitle = new Paragraph().setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(1.0f);
            Text titleId = new Text("KONFIRMASI PERDAGANGAN\n").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeTitle()).setUnderline(1, -5);
            Text titleEn = new Text("TRADE CONFIRMATION (BY FAX)").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeTitle());
            pTitle.add(titleId).add(titleEn);

            // Data Header
            Table tblDataHeader = createDataHeader();

            // Table Detail Trx
            Table tblDataDetail = createDataDetail();

            Table createSign = createSign();

            // Customer Sign
            Table tblCustSign = createCustSign();

            // document notes
            Paragraph pDocNotes = createDocNotes();

            // Authorized Sign
            Table createMaterai = createMaterai();

            // Footer
            Paragraph footer = fontUtils.footer();

            Table box = new Table(2);
            box.setWidth(UnitValue.createPercentValue(100));

            Cell cell1 = new Cell().setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.LEFT).setTextAlignment(TextAlignment.LEFT);
            cell1.add(new Paragraph().add(new Text("\n")));
            cell1.add(createMaterai);

            Cell cell2 = new Cell().setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.RIGHT);
            cell2.add(createSign);

            box.addCell(cell1);
            box.addCell(cell2);

            // Build Document
            document.add(logo);
            document.add(fontUtils.line()).setFontSize(fontUtils.sizeFont());
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.05f));
//            document.add(address);
            document.add(pTitle);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.25f));
            document.add(tblDataHeader);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));
            document.add(fontUtils.line()).setFontSize(fontUtils.sizeFont());
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(new Paragraph()
                    .add(new Text("Kami menyatakan transaksi sebagai berikut\n").setFont(fontUtils.fontRegular()))
                    .add(new Text("We would like to confirm the following transaction :").setFont(fontUtils.fontMedium())).setMultipliedLeading(1.0f));
//            document.add(new Paragraph().add(new Text("We would like to confirm the following transaction :\n").setBold()));

            document.add(tblDataDetail);

            document.add(new Paragraph().add(new Text("\n")));
            document.add(tblCustSign).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(box);
            document.add(new Paragraph().add(new Text("\n")));
            document.add(pDocNotes);

            // Setting footer
            int n = document.getPdfDocument().getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                document.showTextAligned(
                        new Paragraph().add(new Text((String.format("Halaman/Page 0%s /0%s\n", i, n))).setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeNotes())).setMultipliedLeading(3f)
                        ,
                        580, 70, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                document.showTextAligned(footer,580, 40, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
            }

            document.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
    }

    public void createRevFile(Map<String, String> data, String file) {
        this.data2 = data;
        PdfWriter writer;
        try {
            writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4, false);
            document.setMargins(fontUtils.marginsTop(), fontUtils.marginRight(), fontUtils.marginsBottom(), fontUtils.marginLeft());

            // HEADER
            Table logo = fontUtils.headerLogoConven();
//            Paragraph address = fontUtils.address();

            // Title
            Paragraph pTitle = new Paragraph().setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(1.0f);
            Text titleId = new Text("KONFIRMASI PERDAGANGAN\n").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeTitle()).setUnderline(1, -5);
            Text titleEn = new Text("TRADE CONFIRMATION").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeTitle());
            pTitle.add(titleId).add(titleEn);

            // Data Header
            Table tblDataHeader = createDataHeader();

            // Table Detail Trx
            Table tblDataDetail = createDataDetail();

            Table createSign = createSign();

            // Customer Sign
            Table tblCustSign = createCustSign();

            // document notes
            Paragraph pDocNotes = createDocNotes();

            // Authorized Sign
            Paragraph footer = fontUtils.footer();

            // Build Document
            document.add(logo);
            document.add(fontUtils.line()).setFontSize(fontUtils.sizeFont());
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.05f));
//            document.add(address);
            document.add(pTitle);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.25f));
            document.add(tblDataHeader);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(fontUtils.line()).setFontSize(fontUtils.sizeFont());
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(new Paragraph()
                    .add(new Text("Kami menyatakan transaksi sebagai berikut\n").setFont(fontUtils.fontRegular()))
                    .add(new Text("We would like to confirm the following transaction :").setFont(fontUtils.fontMedium())).setMultipliedLeading(1.0f));

            document.add(tblDataDetail);

            document.add(new Paragraph().add(new Text("\n")));
            document.add(tblCustSign).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(createSign);
            document.add(new Paragraph().add(new Text("\n")));
            document.add(pDocNotes);

            // Setting footer
            int n = document.getPdfDocument().getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                document.showTextAligned(
                        new Paragraph().add(new Text((String.format("Halaman/Page 0%s /0%s\n", i, n))).setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont())).setMultipliedLeading(3f)
                        ,
                        580, 70, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
                document.showTextAligned(footer,580, 40, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
            }

            int numberOfPages = pdfDoc.getNumberOfPages();
            PageSize ps = pdfDoc.getDefaultPageSize();
            for (int i = 1; i <= numberOfPages; i++) {
                PdfPage page = pdfDoc.getPage(i);

                PdfLayer layer = new PdfLayer("watermark", pdfDoc);
                PdfCanvas canvas = new PdfCanvas(page);
                Rectangle pageSize = page.getPageSize();
                Paragraph paragraph = new Paragraph("CANCELLED").setFontSize(80).setFont(fontUtils.fontRegularItalic());
                paragraph.setOpacity(0.5f);

                Canvas canvasModel;
                canvas.beginLayer(layer);
                canvasModel = new Canvas(canvas, pdfDoc, ps);
                canvasModel.showTextAligned(paragraph, pageSize.getWidth() / 2, pageSize.getHeight() / 2, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
                canvasModel.setOpacity(0.2f);
                canvas.endLayer();
            }

            document.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
    }

    //    @Override
    public Table createSign() throws IOException {
        Table createSign = new Table(1);
        createSign.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());
        createSign.setWidth(UnitValue.createPercentValue(50));
        createSign.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        Cell tblDataHeaderRow1C1 = new Cell().setBorder(Border.NO_BORDER);
        tblDataHeaderRow1C1.setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.RIGHT).add(new Paragraph()
                .add(new Text("Dinyatakan oleh\n"))
                .add(new Text("Confirmed by\n\n\n" + data2.get("CUSTNAM"))).setMultipliedLeading(1.0f));

        createSign.addCell(tblDataHeaderRow1C1);

        return createSign;

    }

    public Table createDataHeader() throws IOException {
        Table tblDataHeader = new Table(4);
        tblDataHeader.setWidth(UnitValue.createPercentValue(100));
        tblDataHeader.setBorder(Border.NO_BORDER);
        tblDataHeader.setFont(fontUtils.fontRegular());
        tblDataHeader.setFontSize(fontUtils.sizeFont());


        Cell tblDataHeaderRow1C1 = new Cell()
                .setBorder(Border.NO_BORDER)
                .setWidth(UnitValue.createPercentValue(15));
        tblDataHeaderRow1C1.add(new Paragraph()
                .add(new Text("Kepada\n").setUnderline())
                .add(new Text("To")).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow1C2 = new Cell()
                .setBorder(Border.NO_BORDER);
        tblDataHeaderRow1C2.add(new Paragraph(":    " + data2.get("CUSTNAM")).setFont(fontUtils.fontMedium()).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow1C3 = new Cell()
                .setBorder(Border.NO_BORDER)
                .setWidth(UnitValue.createPercentValue(20));
        tblDataHeaderRow1C3.add(new Paragraph()
                .add(new Text("No. Kesepakatan\n").setUnderline())
                .add(new Text("Deal No")).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow1C4 = new Cell().setBorder(Border.NO_BORDER);
        tblDataHeaderRow1C4.add(new Paragraph(":    " + data2.get("DEALNO")).setFont(fontUtils.fontMedium()).setMultipliedLeading(1.0f));

        tblDataHeader.addCell(tblDataHeaderRow1C1);
        tblDataHeader.addCell(tblDataHeaderRow1C2);
        tblDataHeader.addCell(tblDataHeaderRow1C3);
        tblDataHeader.addCell(tblDataHeaderRow1C4);
        tblDataHeader.startNewRow();
//        ----------------------------
        Cell tblDataHeaderRow2C1 = new Cell()
                .setBorder(Border.NO_BORDER);
        tblDataHeaderRow2C1.add(new Paragraph()
                .add(new Text("U.p\n").setUnderline())
                .add(new Text("Attention")).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow2C2 = new Cell()
                .setBorder(Border.NO_BORDER);
        tblDataHeaderRow2C2.add(new Paragraph(":    " + data2.get("ATTENTION")).setFont(fontUtils.fontMedium()).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow2C3 = new Cell().setBorder(Border.NO_BORDER);
        tblDataHeaderRow2C3.add(new Paragraph()
                .add(new Text("No. Faks\n").setUnderline())
                .add(new Text("Fax No")).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow2C4 = new Cell().setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        tblDataHeaderRow2C4.add(new Paragraph(":    " + data2.get("FAXNO")).setFont(fontUtils.fontMedium()).setMultipliedLeading(1.0f));

        tblDataHeader.addCell(tblDataHeaderRow2C1);
        tblDataHeader.addCell(tblDataHeaderRow2C2);
        tblDataHeader.addCell(tblDataHeaderRow2C3);
        tblDataHeader.addCell(tblDataHeaderRow2C4);
        tblDataHeader.startNewRow();

//        ----------------------------

        Cell tblDataHeaderRow3C1 = new Cell().setBorder(Border.NO_BORDER);
        tblDataHeaderRow3C1.add(new Paragraph()
                .add(new Text("Telepon\n").setUnderline())
                .add(new Text("Phone.")).setMultipliedLeading(1.0f));

        Cell tblDataHeaderRow3C2 = new Cell().setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        tblDataHeaderRow3C2.add(new Paragraph(":    " + data2.get("PHONENO")).setFont(fontUtils.fontMedium()).setMultipliedLeading(1.0f));


        tblDataHeader.addCell(tblDataHeaderRow3C1);
        tblDataHeader.addCell(tblDataHeaderRow3C2);
        tblDataHeader.startNewRow();

        return tblDataHeader;
    }

    public Table createDataDetail() throws IOException {
        Table tblDataDetail = new Table(5);
        tblDataDetail.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());
        tblDataDetail.setWidth(UnitValue.createPercentValue(100));
        tblDataDetail.setBorder(Border.NO_BORDER);

        Cell R0C1 = new Cell().setBorder(Border.NO_BORDER).setBorder(Border.NO_BORDER).setWidth(UnitValue.createPercentValue(25));
        R0C1.add(new Paragraph(""));

        Cell R0C2 = new Cell().setBorder(Border.NO_BORDER).setWidth(UnitValue.createPercentValue(5));
        R0C2.add(new Paragraph(""));

        Cell R0C3 = new Cell().setBorder(Border.NO_BORDER).setWidth(UnitValue.createPercentValue(20));
        R0C2.add(new Paragraph(""));

        Cell R0C4 = new Cell().setBorder(Border.NO_BORDER).setWidth(UnitValue.createPercentValue(40));
        R0C4.add(new Paragraph(""));

        Cell R0C5 = new Cell().setBorder(Border.NO_BORDER).setWidth(UnitValue.createPercentValue(10));
        R0C4.add(new Paragraph(""));

        tblDataDetail.addCell(R0C1);
        tblDataDetail.addCell(R0C2);
        tblDataDetail.addCell(R0C3);
        tblDataDetail.addCell(R0C4);
        tblDataDetail.addCell(R0C5);
        tblDataDetail.startNewRow();

//        ----------------------
        Cell R1C1 = new Cell().setBorder(Border.NO_BORDER);
        R1C1.add(new Paragraph()
                .add(new Text("Nama penerbit\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Issuer Name").setFont(fontUtils.fontMedium())).setMultipliedLeading(1.0f));


        Cell R1C2 = new Cell(1, 3).setBorder(Border.NO_BORDER);
        R1C2.setVerticalAlignment(VerticalAlignment.MIDDLE);
        R1C2.add(new Paragraph(": " + data2.get("ISSUERNM")).setMultipliedLeading(1.0f));

        Cell R1C4 = new Cell().setBorder(Border.NO_BORDER);
        R1C4.add(new Paragraph(data2.get("SECID")).setMultipliedLeading(1.0f));

        tblDataDetail.addCell(R1C1);
        tblDataDetail.addCell(R1C2);
        tblDataDetail.addCell(R1C4);
        tblDataDetail.startNewRow();

//        ----------------------

        Cell R2C1 = new Cell().setBorder(Border.NO_BORDER);
        String isin = data2.get("SECID").equals("SBI") ? "CUSIP" : "ISIN";
        R2C1.add(new Paragraph().add(new Text(isin).setFont(fontUtils.fontMedium())).setMultipliedLeading(1.0f));

        Cell R2C2 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R2C2.add(new Paragraph(": " + data2.get("ISIN")).setMultipliedLeading(1.0f));

        Cell R2C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).add(new Paragraph("").setMultipliedLeading(1.0f));
        tblDataDetail.addCell(R2C1);
        tblDataDetail.addCell(R2C2);
        tblDataDetail.addCell(R2C4);
        tblDataDetail.startNewRow();

//        ----------------------

        Cell R3C1 = new Cell().setBorder(Border.NO_BORDER);
        R3C1.add(new Paragraph()
                .add(new Text("Penjual\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Seller").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        String seller = data2.get("PS").equals("P") ? data2.get("CUSTNAM") : "PT. Bank Permata Tbk";
        Cell R3C2 = new Cell(1, 4).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R3C2.add(new Paragraph(": " + seller).setMultipliedLeading(1));

        tblDataDetail.addCell(R3C1);
        tblDataDetail.addCell(R3C2);
        tblDataDetail.startNewRow();

//        ---------------------------
        Cell R4C1 = new Cell().setBorder(Border.NO_BORDER);
        R4C1.add(new Paragraph()
                .add(new Text("Pembeli\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Buyer").setFont(fontUtils.fontMedium())));

        String buyer = data2.get("PS").equals("P") ? "PT. Bank Permata Tbk" : data2.get("CUSTNAM");
        Cell R4C2 = new Cell(1, 4).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R4C2.add(new Paragraph()
                .add(new Text(": " + buyer).setFont(fontUtils.fontRegular())).setMultipliedLeading(1));


        tblDataDetail.addCell(R4C1);
        tblDataDetail.addCell(R4C2);
        tblDataDetail.startNewRow();

//        -------------------------------------

        Cell R5C1 = new Cell().setBorder(Border.NO_BORDER);
        R5C1.add(new Paragraph()
                .add(new Text("Tanggal perdagangan\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Trade Date").setFont(fontUtils.fontMedium())));

        Cell R5C2 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R5C2.add(new Paragraph()
                .add(new Text(": " + data2.get("TRADEDT")).setFont(fontUtils.fontRegular())));

        Cell R5C4 = new Cell(1, 2).setBorder(Border.NO_BORDER);
        R5C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())));

        tblDataDetail.addCell(R5C1);
        tblDataDetail.addCell(R5C2);
        tblDataDetail.addCell(R5C4);
        tblDataDetail.startNewRow();

//        ---------------------------------------------

        Cell R6C1 = new Cell().setBorder(Border.NO_BORDER);
        R6C1.add(new Paragraph()
                .add(new Text("Tanggal penyelesaian\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Settlement Date").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        Cell R6C2 = new Cell(1, 2).setBorder(Border.NO_BORDER);
        R6C2.add(new Paragraph()
                .add(new Text(": " + data2.get("SETTDAT")).setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

        Cell R6C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R6C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R6C1);
        tblDataDetail.addCell(R6C2);
        tblDataDetail.addCell(R6C4);
        tblDataDetail.startNewRow();

//        ----------------------------------------

        Cell R7C1 = new Cell().setBorder(Border.NO_BORDER);
        R7C1.add(new Paragraph()
                .add(new Text("Nominal").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        double nominal = 0;
        if (data2.get("PRODTYPE").equals("FR") || data2.get("PRODTYPE").equals("VR")) {
            double p1 = Double.parseDouble(data2.get("PRNCAMT"));
            double p2 = Double.parseDouble(data2.get("PRICE"));
            nominal = Math.abs(p1 / p2 * 100);
        } else {
            nominal = Math.abs(Double.parseDouble(data2.get("NOMINAL")));
        }

        Cell R7C2 = new Cell().setBorder(Border.NO_BORDER);
        R7C2.add(new Paragraph()
                .add(new Text(": " + data2.get("CCY"))
                        .setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        Cell R7C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(ConfoUtils.formatNumber(nominal)).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

        Cell R7C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R7C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R7C1);
        tblDataDetail.addCell(R7C2);
//        tblDataDetail.addCell(R7C3);
        tblDataDetail.addCell(R7C4);
        tblDataDetail.startNewRow();

//        -------------------------------------

        Cell R8C1 = new Cell().setBorder(Border.NO_BORDER);
        R8C1.add(new Paragraph()
                .add(new Text("Tanggal jatuh tempo\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Maturity Date").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        Cell R8C2 = new Cell(1, 2).setBorder(Border.NO_BORDER);
        R8C2.add(new Paragraph()
                .add(new Text(": " + data2.get("MATDAT")).setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

        Cell R8C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R8C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R8C1);
        tblDataDetail.addCell(R8C2);
        tblDataDetail.addCell(R8C4);
        tblDataDetail.startNewRow();

        //        -------------------------------------

//        String price = data2.get("SECID").substring(0, 3).equals("SBI") ? "Disc Rate" : "Harga \n Price";

        Cell R9C1 = new Cell().setBorder(Border.NO_BORDER);
        if(data2.get("SECID").substring(0, 3).equals("SBI")){
            R9C1.add(new Paragraph()
                    .add(new Text("Disc Rate\n").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));
        } else {
            R9C1.add(new Paragraph()
                    .add(new Text("Harga\n").setFont(fontUtils.fontMedium()))
                    .add(new Text("Price").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));
        }

        Double a = Double.valueOf(data2.get("YIELD"));
        Double b = Double.valueOf(data2.get("PRICE"));
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        String hasilA = decimalFormat.format(a);
        String hasilB = decimalFormat.format(b);
        String prodPrice = data2.get("SECID").substring(0, 3).equals("SBI") ? hasilA : hasilB;

        Cell R9C2 = new Cell().setBorder(Border.NO_BORDER);
        R9C2.add(new Paragraph()
                .add(new Text(": ").setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

        Cell R9C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(prodPrice + "%").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

//        Cell R9C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
//        R9C4.add(new Paragraph()
//                .add(new Text("%").setFont(fontHelv)));

        tblDataDetail.addCell(R9C1);
        tblDataDetail.addCell(R9C2);
        tblDataDetail.addCell(R9C3);
//        tblDataDetail.addCell(R9C4);
        tblDataDetail.startNewRow();

        //        -------------------------------------

        Cell R10C1 = new Cell().setBorder(Border.NO_BORDER);
        R10C1.add(new Paragraph()
                .add(new Text("Jumlah pokok\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Principal Amount").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));
//        R10C1.add(new Paragraph()
//                .add(new Text("Principal Amount").setFont(fontHelv)));

        double princAmt = 0;


        if (data2.get("SECID").substring(0, 4).equals("IDBI") || data2.get("SECID").substring(0, 4).equals("IDSD") || data2.get("SECID").substring(0, 3).equals("SPN")) {
            princAmt = Double.parseDouble(data2.get("SETAMT"));
        } else if (data2.get("PRODTYPE").equals("FR") || data2.get("PRODTYPE").equals("VR") || data2.get("PRODTYPE").equals("TR")) {
            princAmt = Double.parseDouble(data2.get("PRNCAMT"));
        } else {
            princAmt = Math.round((Double.parseDouble(data2.get("NOMINAL")) * Double.parseDouble(data2.get("PRICE")) * 0.01)) ;
        }

        log.info("----------------------------------------------------------------------------- PRINCAMT" + princAmt);
        log.info("----------------------------------------------------------------------------- PRINCAMT" + Double.parseDouble(data2.get("NOMINAL")));

        Cell R10C2 = new Cell().setBorder(Border.NO_BORDER);
        R10C2.add(new Paragraph()
                .add(new Text(": ").setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        Cell R10C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(ConfoUtils.formatNumber(princAmt)).setTextAlignment(TextAlignment.RIGHT));
        Cell R10C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R10C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R10C1);
        tblDataDetail.addCell(R10C2);
//        tblDataDetail.addCell(R10C3);
        tblDataDetail.addCell(R10C4);
        tblDataDetail.startNewRow();
        //        -------------------------------------

        String ccy11 = data2.get("SECID").substring(0, 3).equals("SBI") ? "" : data2.get("CCY");
        Cell R11C1 = new Cell().setBorder(Border.NO_BORDER);
        R11C1.add(new Paragraph()
                .add(new Text("Bunga berjalan\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Accrued Interest").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));
//        R11C1.add(new Paragraph()
//                .add(new Text("Accrued Interest").setFont(fontHelv)));


        Cell R11C2 = new Cell().setBorder(Border.NO_BORDER);
        R11C2.add(new Paragraph()
                .add(new Text(": " + ccy11).setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        Cell R11C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(ConfoUtils.formatNumberr(data2.get("ACCINT"))).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

        Cell R11C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R11C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R11C1);
        tblDataDetail.addCell(R11C2);
//        tblDataDetail.addCell(R11C3);
        tblDataDetail.addCell(R11C4);
        tblDataDetail.startNewRow();
        //        -------------------------------------

        Cell R12C1 = new Cell().setBorder(Border.NO_BORDER);
        R12C1.add(new Paragraph()
                .add(new Text("Pajak bunga berjalan\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Tax on Accrued Interest").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));
//        R12C1.add(new Paragraph()
//                .add(new Text("Tax on Accrued Interest").setFont(fontHelv)));

        double princAmtNm = 0;
        if (data2.get("SECID").substring(0, 3).equals("SBI")) {
            if (data2.get("CUSTTYPE").substring(0, 3).equals("Yes")) {
                try {
                    Date settDate = new SimpleDateFormat("yyyy-MM-dd").parse(data2.get("SETTDAT"));
                    Date matDate = new SimpleDateFormat("yyyy-MM-dd").parse(data2.get("MATDAT"));
                    long milliseconds = matDate.getTime() - settDate.getTime();
                    int days = (int) (milliseconds / (1000 * 60 * 60 * 24));
                    princAmtNm = Math.round((((360 * Double.parseDouble(data2.get("NOMINAL"))) / (Double.parseDouble(data2.get("YIELD")) * 0.01 * days)) + 360) * 100.0) / 100.0;
                } catch (ParseException e) {

                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    log.error(errors.toString());
                }
            } else {
                try {
                    Date settDate = new SimpleDateFormat("yyyy-MM-dd").parse(data2.get("SETTDAT"));
                    Date matDate = new SimpleDateFormat("yyyy-MM-dd").parse(data2.get("MATDAT"));
                    long milliseconds = matDate.getTime() - settDate.getTime();
                    int days = (int) (milliseconds / (1000 * 60 * 60 * 24));
                    princAmtNm = Math.round((((360 * Double.parseDouble(data2.get("NOMINAL"))) / (Double.parseDouble(data2.get("YIELD")) * 0.01 * days)) + 360));
                } catch (ParseException e) {

                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    log.error(errors.toString());
                }
            }
        } else {
            princAmtNm = Math.round((Double.parseDouble(data2.get("NOMINAL")) * Double.parseDouble(data2.get("PRICE")) * 0.01) * 100.0) / 100.0;
        }

        double princAmtRound = 0;
        if (data2.get("SECID").substring(0, 3).equals("SBI")) {
            if (data2.get("CUSTTYPE").substring(0, 3).equals("Yes")) {
                if (data2.get("INTERPARM").equals("0")) {
                    princAmtRound = 0;
                } else {
                    princAmtRound = Math.round((Double.parseDouble(data2.get("NOMINAL")) - princAmtNm * Double.parseDouble(data2.get("INTERPARM"))) * 100.0) / 100.0;
                }

            } else {
                if (data2.get("INTERPARM").equals("0")) {
                    princAmtRound = 0;
                } else {
                    princAmtRound = Math.round(Double.parseDouble(data2.get("NOMINAL")) - princAmtNm * Double.parseDouble(data2.get("INTERPARM")));
                }

            }
        }

        Cell R12C2 = new Cell().setBorder(Border.NO_BORDER);
        R12C2.add(new Paragraph()
                .add(new Text(": " + data2.get("CCY")).setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        Cell R12C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(ConfoUtils.formatNumber(princAmtRound)).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

        Cell R12C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R12C4.add(new Paragraph()
                .add(new Text("\n\n\n\n\n\n").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R12C1);
        tblDataDetail.addCell(R12C2);
//        tblDataDetail.addCell(R12C3);
        tblDataDetail.addCell(R12C4);
        tblDataDetail.startNewRow();
        //        -------------------------------------

        Cell R13C1 = new Cell().setBorder(Border.NO_BORDER);
        R13C1.add(new Paragraph()
                .add(new Text("Pajak pendapatan\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Tax on Capital Gain").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        Cell R13C2 = new Cell().setBorder(Border.NO_BORDER);
        R13C2.add(new Paragraph()
                .add(new Text(": ").setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));
        Cell R13C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

        Cell R13C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R13C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R13C1);
        tblDataDetail.addCell(R13C2);
        tblDataDetail.addCell(R13C3);
        tblDataDetail.addCell(R13C4);
        tblDataDetail.startNewRow();
        //        -------------------------------------

        Cell R14C1 = new Cell().setBorder(Border.NO_BORDER);
        R14C1.add(new Paragraph()
                .add(new Text("Jumlah uang penyelesaian\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Settlement Amount").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));

        double settSum = 0;
        if (Double.parseDouble(data2.get("ACCINT")) < 1.0) {
            settSum = (Double.parseDouble(data2.get("ACCINT")) * -1) + (Double.parseDouble(data2.get("NOMINAL")) * Double.parseDouble(data2.get("PRICE")) * 0.01);
        } else {
            settSum = Double.parseDouble(data2.get("ACCINT")) + (Double.parseDouble(data2.get("NOMINAL")) * Double.parseDouble(data2.get("PRICE")) * 0.01);
        }

        double settAmt = 0;
        if (data2.get("SECID").substring(0, 4).equals("IDBI") || data2.get("SECID").substring(0, 4).equals("IDSD") || data2.get("SECID").substring(0, 3).equals("SPN")) {
            settAmt = Double.parseDouble(data2.get("SETAMT"));
        } else if (data2.get("PRODTYPE").substring(0, 2).equals("FR") || data2.get("PRODTYPE").substring(0, 2).equals("VR") || data2.get("PRODTYPE").equals("TR")) {
            settAmt = Double.parseDouble(data2.get("SETAMT"));
        } else {
            settAmt = settSum;
        }

        Cell R14C2 = new Cell().setBorder(Border.NO_BORDER);
        R14C2.add(new Paragraph()
                .add(new Text(": " + data2.get("CCY")).setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        Cell R14C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(ConfoUtils.formatNumber(settAmt)).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

        Cell R14C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        R14C4.add(new Paragraph()
                .add(new Text("").setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        tblDataDetail.addCell(R14C1);
        tblDataDetail.addCell(R14C2);
//        tblDataDetail.addCell(R14C3);
        tblDataDetail.addCell(R14C4);
        tblDataDetail.startNewRow();

        //        -------------------------------------

        Cell R15C1 = new Cell().setBorder(Border.NO_BORDER);
        R15C1.add(new Paragraph()
                .add(new Text("Kupon Saat ini\n").setFont(fontUtils.fontMedium()))
                .add(new Text("Current Coupon").setFont(fontUtils.fontMedium())).setMultipliedLeading(1));


        Cell R15C2 = new Cell().setBorder(Border.NO_BORDER);
        R15C2.add(new Paragraph()
                .add(new Text(": ").setFont(fontUtils.fontRegular())).setVerticalAlignment(VerticalAlignment.MIDDLE).setMultipliedLeading(1));

//        String coup = ConfoUtils.isNullOrEmpty(data2.get("CRNTCOUP")) ? "" : data2.get("CRNTCOUP");
//        Cell R15C3 = new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(coup + "%").setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(1));

//        Cell R15C4 = new Cell(1, 2).setBorder(Border.NO_BORDER).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
//        R15C4.add(new Paragraph()
//                .add(new Text("%").setFont(fontHelv)));

        tblDataDetail.addCell(R15C1);
        tblDataDetail.addCell(R15C2);
//        tblDataDetail.addCell(R15C3);
//        tblDataDetail.addCell(R15C4);

        return tblDataDetail;

    }


    //    @Override
    public Table createCustSign() throws IOException {
        Table createCustSign = new Table(2);
        createCustSign.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());
        createCustSign.setWidth(UnitValue.createPercentValue(100));
        createCustSign.setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell tblDataHeaderRow1C1 = new Cell().setBackgroundColor(fontUtils.activeBlueColor()).setTextAlignment(TextAlignment.CENTER).setWidth(250f);
        tblDataHeaderRow1C1.add(new Paragraph().add(new Text("Seller's Instruction").setFont(fontUtils.fontMedium()).setFontColor(fontUtils.whiteColor())).setMultipliedLeading(1));

        Cell tblDataHeaderRow1C2 = new Cell().setBackgroundColor(fontUtils.activeBlueColor()).setTextAlignment(TextAlignment.CENTER).setWidth(250f);
        tblDataHeaderRow1C2.add(new Paragraph().add(new Text("Buyer's Instruction").setFont(fontUtils.fontRegular()).setFontColor(fontUtils.whiteColor())).setMultipliedLeading(1));


        createCustSign.addCell(tblDataHeaderRow1C1);
        createCustSign.addCell(tblDataHeaderRow1C2);
        createCustSign.startNewRow();

        String sellerInst = "";
        if (data2.get("PS").equals("S")) {
            if (data2.get("PRODTYPE").substring(0, 2).equals("NG") || data2.get("PRODTYPE").substring(0, 2).equals("AF")) {
                sellerInst = "Kustodian Sentral Efek Indonesia\nC-Best : BALI1-0001-001-88\n IFO : PT. Bank Permata Tbk.";
            } else if (data2.get("CCY").equals("USD") && data2.get("ISSUER").equals("6000000000") && data2.get("SECSACCT").equals("CLEARSTREAM")) {
                sellerInst = "CLEARSTREAM BANKING S.A., LUXEMBOURG A/C. 21870";
            } else if (data2.get("CCY").equals("USD") && data2.get("ISSUER").equals("800000873") && data2.get("SECSACCT").equals("CLEARSTREAM")) {
                sellerInst = "FED WIRE ABA 021000089\nSAFE/089154 CSC21870";
            } else if (data2.get("CCY").equals("IDR") && !data2.get("PRODTYPE").substring(0, 2).equals("NG") && !data2.get("PRODTYPE").substring(0, 2).equals("AF") ) {
                sellerInst = "Bank Indonesia Central Registry\nP.T. Bank Permata Tbk.\nSecurities A/C : 3.1.2.013\nIDR A/C : 523.013.000.990";
            }
        }
//        -----------------------------------

        Cell tblDataHeaderRow2C1 = new Cell().setHorizontalAlignment(HorizontalAlignment.CENTER).setHeight(60).setTextAlignment(TextAlignment.CENTER).setWidth(250f);
        tblDataHeaderRow2C1.add(new Paragraph().add(new Text(sellerInst).setFont(fontUtils.fontRegular())).setMultipliedLeading(1));

        String buyerInst = "";
        if (data2.get("PS").equals("P")) {
            if (data2.get("PRODTYPE").substring(0, 2).equals("NG") || data2.get("PRODTYPE").substring(0, 2).equals("AF")) {
                buyerInst = "Kustodian Sentral Efek Indonesia\nC-Best : BALI1-0001-001-88\n IFO : PT. Bank Permata Tbk.";
            } else if (data2.get("CCY").equals("USD") && data2.get("ISSUER").equals("6000000000") && data2.get("SECSACCT").equals("CLEARSTREAM")) {
                buyerInst = "CLEARSTREAM BANKING S.A., LUXEMBOURG A/C. 21870";
            } else if (data2.get("CCY").equals("USD") && data2.get("ISSUER").equals("800000873") && data2.get("SECSACCT").equals("CLEARSTREAM")) {
                buyerInst = "FED WIRE ABA 021000089\nSAFE/089154 CSC21870";
            } else if (data2.get("CCY").equals("IDR") && !data2.get("PRODTYPE").substring(0, 2).equals("NG")&& !data2.get("PRODTYPE").substring(0, 2).equals("AF")) {
                buyerInst = "Bank Indonesia Central Registry\nP.T. Bank Permata Tbk.\nSecurities A/C : 3.1.2.013\nIDR A/C : 523.013.000.990";
            }
        }
        Cell tblDataHeaderRow2C2 = new Cell().setHorizontalAlignment(HorizontalAlignment.CENTER).setHeight(60).setTextAlignment(TextAlignment.CENTER).setWidth(250f);
        tblDataHeaderRow2C2.add(new Paragraph().add(new Text(buyerInst).setFont(fontUtils.fontRegular())).setMultipliedLeading(1));


        createCustSign.addCell(tblDataHeaderRow2C1);
        createCustSign.addCell(tblDataHeaderRow2C2);
        createCustSign.startNewRow();
        return createCustSign;

    }

    //    @Override
    public Paragraph createDocNotes() throws IOException {
        Paragraph p = new Paragraph();
        p.setFontSize(fontUtils.sizeNotes());
        p.setFont(fontUtils.fontRegular()).setMultipliedLeading(1);
        p.setTextAlignment(TextAlignment.JUSTIFIED);
        p.add(new Text("Konfirmasi ini diterbitkan pada saat Tanggal Kesepakatan.\n"));
        p.add(new Text("This confirmation is issued on the deal  date.\n\n").setFont(fontUtils.fontRegularItalic()));
        p.add(new Text("Mohon agar mengisi instruksi penyelesaian, menandatangani dan mengembalikan konfirmasi ini segera kepada BANK di no. faks : +6221-7453173 / +6221-7453248 / +6221-74860033 sebelum atau pada tanggal penyelesaian, yang mana tanggal lebih awal, pada jam 10.00 am. Konfirmasi ini dikeluarkan secara komputerisasi sehingga tidak memerlukan tanda tangan dari pihak yang menerbitkan(BANK).\n"));
        p.add(new Text("Please fill in your settlement instruction, sign, and return the document to the BANK Fax No. : +6221-7453173 / +6221-7453248 / +6221-74860033 prior to or on the settlement date, whichever is earlier, by 10.00 am. This is a computer generated confirmation and therefore does not require an issuer signature.\n\n").setFont(fontUtils.fontRegularItalic()));
        p.add(new Text("Kecuali NASABAH memberitahukan kepada BANK adanya kesalahan, ketidaksesuaian atau perbedaan atas konfirmasi ini dalam waktu 1 (satu) hari kerja setelah tanggal penerimaan konfirmasi ini, maka isi dari konfirmasi ini akan dianggap benar dan mengikat NASABAH dan NASABAH tidak diperkenankan untuk mengajukan keberatan atas isi konfirmasi.\n"));
        p.add(new Text("Unless the CUSTOMER notifies BANK for any error, irregularity or discrepancy in this confirmation within 1 (one) working day after the receiving date of this confirmation, thus the contents of this confirmation shall be deemed to be correct and conclusively binding upon CUSTOMER and CUSTOMER shall not be entitled to raise any objection here to.\n\n").setFont(fontUtils.fontRegularItalic()));
        p.add(new Text("Informasi yang tercantum pada faksimili ini bersifat rahasia dan hanya khusus dipergunakan oleh pihak yang dituju atau dialamatkan pada konfirmasi ini. Pihak yang menerima konfirmasi ini namun bukan pihak yang dituju atau dialamatkan, atau bukan merupakan salah satu dari staf atau perwakilan dari NASABAH, dilarang untuk menggunakan, menyebarluaskan, memperbanyak isi konfirmasi ini. Jika terjadi kesalahan pengiriman sehingga Anda menerima konfirmasi ini, maka kami sangat berterima kasih jika Anda dapat segera menghubungi BANK melalui telepon dan mengirimkan kembali konfirmasi ini kepada kami melalui pos.\n"));
        p.add(new Text("The information contained in this facsimile is confidential and is intended for the exclusive use of its addressee(s) of this confirmation . Any person who receives this confirmation but not the addressee(s), or one of employees or agents of the CUSTOMER ,  is prohibited to use, divulge or reproduce the content thereof. If you have received this communication in error, we would be grateful if you contact the BANK by telephone as soon as possible and send this confirmation back to us by mail.\n\n").setFont(fontUtils.fontRegularItalic()));
        p.add(new Text("Nasabah dengan ini telah memahami seluruh resiko yang mungkin timbul akibat pengiriman konfirmasi ini melalui faksimili atau e-mail atau media elektronik sejenis lainnya dan selanjutnya NASABAH dengan ini menyatakan dan menjamin BANK bahwa (i) konfirmasi ini dikirim oleh pihak yang berwenang dari NASABAH dan (ii) konfirmasi ini merupakan satu-satunya alat bukti yang sah atas pelaksanaan transaksi antara BANK dan NASABAH untuk digunakan baik di dalam maupun di luar pengadilan, apabila di kemudian hari terjadi sengketa atau permasalahan terkait transaksi yang diuraikan pada konfirmasi ini.\n"));
        p.add(new Text("The CUSTOMER hereby acknowledges and understand all risk might arise due to the transmission of this confirmation through facsmile or e-mail or other similar electronic media and hereinafter the CUSTOMER hereby represents and warrants the BANK that (i) confirmation herein is being transmitted by the authorized party of the CUSTOMER and (ii) Confirmation herein shall be the sole legally evidence of transaction performed by the BANK and the CUSTOMER to be utilized either inside or outside the court, if dispute occurs in the future in respect to such transaction as described on this confirmation.\n\n").setFont(fontUtils.fontRegularItalic()));
        p.add(new Text("Jika terjadi ketidakkonsistenan atau kontradiksi diantara Bahasa Indonesia dan Bahasa Inggris, maka hanya Bahasa Indonesia yang berlaku terhadap segala terjemahannya.\n"));
        p.add(new Text("Any inconsistency or contradiction between text in Indonesian and English version, the Indonesian version shall be prevailed against any translation.\n\n").setFont(fontUtils.fontRegularItalic()));

        return p;
    }

    public Table createMaterai() throws IOException{
        Table newTable = new Table(1);
        newTable.setFontSize(fontUtils.sizeFont());
        newTable.setWidth(UnitValue.createPercentValue(50));

        newTable.addCell(new Paragraph().add(new Text("BEA MATERAI LUNAS 10000")).setFont(fontUtils.fontMedium()).setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(1));
        newTable.startNewRow();
        newTable.addCell(new Paragraph().add(new Text("STAMP DUTY PAID 10000")).setFont(fontUtils.fontMedium()).setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(1));

        return newTable;
    }

    //    @Override
    public Map<String, String> getData() {
        return data2;
    }

    //    @Override
    public void setData(Map<String, String> data) {
        this.data2 = data;
    }
}
