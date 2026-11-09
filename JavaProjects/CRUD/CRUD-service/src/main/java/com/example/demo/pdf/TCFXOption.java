package com.example.demo.pdf;

import com.example.demo.utils.ConfoUtils;
import com.example.demo.utils.FontUtils;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
//import org.apache.logging.log4j.Logger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Component
public class TCFXOption {
    private final static Logger log = Logger.getLogger(TCFXOption.class);
    private Map<String, String> data;
//    public FontUtils fontUtils = new FontUtils();

    @Autowired
    private FontUtils fontUtils;

    @Value("${app.pdf.directory}")
    private String dir;

    public String createPdf() {

        String password = "PASSWORDHEHE";

        String fileName = "TC FX Option";
        String baseFile = dir + fileName + ".pdf";
        String secFile = dir + fileName + "_secured.pdf";

        try {
            ConfoUtils.deleteTemp(baseFile);
            ConfoUtils.deleteTemp(secFile);
        }catch (Exception e){
            e.getCause();
        }
//
        Map<String,String> data = new HashMap<>();
        data.put("PASSWORD",password);

        createBaseFile(data, baseFile);
        String pw = null;
//
        try {
            pw = data.get("PASSWORD");
            log.info("----------------------------------------------------------------------- pasword +" + data.get("PASSWORD"));
        } catch (NullPointerException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
//
        if (!ConfoUtils.isNullOrEmpty(pw)) {
            ConfoUtils.securePdf(baseFile, secFile, data.get("PASSWORD"));
            return secFile;
        }

        return baseFile;
    }

    public void createBaseFile(Map<String, String> data, String file) {
        this.data = data;
        PdfWriter writer;

        try {
            writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);

            // memanggil class yang define header dan footer
//            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
//                    new HeaderFooterHandler(createHeaderTable(), createFooterPage()));
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                    new HeaderFooterAll(createHeaderTable(), createFooterPage()));

            Document document = new Document(pdfDoc, PageSize.A4, false);
            document.setMargins(fontUtils.marginsTop(), fontUtils.marginRight(), fontUtils.marginsBottom(), fontUtils.marginLeft());

            // HEADER

            // FOOTER

//            Paragraph footer = fontUtils.footer();
            // header data

            Table createDataHeader = createDataHeader();

            // Title
            Paragraph pTitle = new Paragraph().setTextAlignment(TextAlignment.CENTER).setMultipliedLeading(1.0f);
            Text titleInd = new Text("KONFIRMASI TRANSAKSI FX OPTION").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeTitle()).setUnderline();
            pTitle.add(titleInd);

            // PERIHAL
            Paragraph pPerihal = new Paragraph().setTextAlignment(TextAlignment.LEFT);
            Text perihalInd = new Text("Perihal : ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            Text isiPerihalInd = new Text(" Konfirmasi Transaksi FX Option - [Nomor Transaksi]").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeFont());
            pPerihal.add(perihalInd);
            pPerihal.add(isiPerihalInd);

//             Table Detail Trx
            Table tblDataDetail = createDataDetail();

            // document notes
            Paragraph pDocNotes = createDocNotes();

            Cell cell1 = new Cell().setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.LEFT).setTextAlignment(TextAlignment.LEFT);
            cell1.add(new Paragraph().add(new Text("\n")));

            // Build Document
//            document.add(logo);
//            document.add(fontUtils.line()).setFontSize(fontUtils.sizeFont());
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.05f));
//            document.add(address);
            document.add(pTitle);
            document.add(pPerihal);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.25f));
            document.add(createDataHeader);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));


            Paragraph pPengantarDetail = new Paragraph().setTextAlignment(TextAlignment.LEFT);

            Text textAwal = new Text("Terima kasih telah melakukan transaksi ")
                    .setFont(fontUtils.fontRegular())
                    .setFontSize(fontUtils.sizeFont());

            Text fxOption = new Text("FX Option")
                    .setFont(fontUtils.fontMedium())
                    .setFontSize(fontUtils.sizeFont());

            Text textAkhir = new Text(" dengan PT Bank Permata. Tbk. Berikut kami sampaikan detail transaksi yang telah diselesaikan:\n\n")
                    .setFont(fontUtils.fontRegular())
                    .setFontSize(fontUtils.sizeFont());

            pPengantarDetail.add(textAwal);
            pPengantarDetail.add(fxOption);
            pPengantarDetail.add(textAkhir);

            document.add(pPengantarDetail);
            document.add(tblDataDetail);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(pDocNotes);

            // Setting footer
//            int n = document.getPdfDocument().getNumberOfPages();
//            for (int i = 1; i <= n; i++) {
//                document.showTextAligned(
//                        new Paragraph().add(new Text((String.format("Halaman/Page 0%s /0%s\n", i, n))).setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeNotes())).setMultipliedLeading(3f)
//                        ,
//                        580, 70, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
//                document.showTextAligned(footer,580, 40, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
//            }

            document.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
    }


    public Table createDataHeader() throws IOException {

        Table tblDataHeader = new Table(UnitValue.createPointArray(new float[]{230f, 8f, 350f}))
                .useAllAvailableWidth();
        tblDataHeader.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());

        tblDataHeader.addCell(createCompactCell(new Paragraph("Kepada")));
        tblDataHeader.addCell(createCompactCell(new Paragraph(":")));
        tblDataHeader.addCell(createCompactCell(
                new Paragraph("[Nama Nasabah]").setFontColor(ColorConstants.GRAY)));

        tblDataHeader.addCell(createCompactCell(new Paragraph("Alamat")));
        tblDataHeader.addCell(createCompactCell(new Paragraph(":")));
        tblDataHeader.addCell(createCompactCell(new Paragraph("")));

        tblDataHeader.addCell(createCompactCell(new Paragraph("No.Telepon")));
        tblDataHeader.addCell(createCompactCell(new Paragraph(":")));
        tblDataHeader.addCell(createCompactCell(new Paragraph("")));

        tblDataHeader.addCell(createCompactCell(new Paragraph("E-mail")));
        tblDataHeader.addCell(createCompactCell(new Paragraph(":")));
        tblDataHeader.addCell(createCompactCell(new Paragraph("")));

        return tblDataHeader;
    }

    public Table createDataDetail() throws IOException {

        Table tblDataDetail = new Table(UnitValue.createPointArray(new float[]{230f, 8f, 350f}))
                .useAllAvailableWidth();
        tblDataDetail.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());
        tblDataDetail.setBorder(Border.NO_BORDER);

        tblDataDetail.addCell(createCompactCell(new Paragraph("Transaksi Nasabah")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Beli/Jual]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Jenis Opsi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Call/Put]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Option Style")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("European Style")));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Pasangan Mata Uang")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh : USD/IDR]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Transaksi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Valuta Transaksi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Valuta Premi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal dan waktu Fixing")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Jatuh Tempo")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Nominal Transaksi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: USD 1.000.000]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("*Nominal Terkonversi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Harga Strike")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Harga Premi (%)")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[5%]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Nominal Premi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: USD 500]").setFontColor(ColorConstants.GRAY)));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Status Transaksi")));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":")));
        tblDataDetail.addCell(createCompactCell(new Paragraph("Telah dikonfirmasi")));

        return tblDataDetail;
    }

    public Paragraph createDocNotes() throws IOException {
        Paragraph p = new Paragraph();
        p.setFontSize(fontUtils.sizeNotes());
        p.setFont(fontUtils.fontRegular()).setMultipliedLeading(1.0f);
        p.setTextAlignment(TextAlignment.JUSTIFIED);
        Text notesAwal = new Text( "*Nominal akan terkonversi apabila harga menyentuh atau melewati harga strike sesuai dengan ketentuan kontrak FX " +
                "Option\n\n" +
                "Konfirmasi ini tidak memerlukan balasan maupun tanda tangan. Apabila rincian transaksi di bawah ini tidak sesuai dengan " +
                "kesepakatan yang Bapak/ Ibu sudah lakukan, mohon segera menghubungi Bank Permata untuk klarifikasi lebih lanjut.\n\n" +
                "Demikian kami sampaikan, Terima kasih atas kepercayaan Bapak/ Ibu kepada Bank Permata.\n\n" +
                "Hormat kami\n").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
        Text notesTengah = new Text("Treasury Operations\n").setFont(fontUtils.fontMedium()).setFontSize(fontUtils.sizeFont());
        Text notesAkhir = new Text("PT Bank Permata Tbk\n").setFontSize(fontUtils.sizeFont());
        p.add(notesAwal);
        p.add(notesTengah);
        p.add(notesAkhir);

        return p;
    }

    public Table createFooterPage() throws IOException{
        Table newTable = new Table(UnitValue.createPointArray(new float[]{150f,345f}))
                .useAllAvailableWidth();
        newTable.setBorder(Border.NO_BORDER);
        newTable.setMarginBottom(1.27f);
        newTable.setMarginTop(1.27f);

        newTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("Restricted")

                        .setFontSize(fontUtils.sizeNotes()).setFontColor(ColorConstants.GRAY)
                        .setTextAlignment(TextAlignment.LEFT)))
                        .setFont(fontUtils.fontRegularItalic());

        newTable.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(fontUtils.footer()
                        .setFontColor(ColorConstants.GRAY)
                        .setTextAlignment(TextAlignment.RIGHT)));
        return newTable;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    private Cell createCompactCell(Paragraph paragraph) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(1f)
                .setPaddingBottom(1f)
                .setPaddingLeft(0f)
                .setPaddingRight(0f)
                .add(paragraph.setMultipliedLeading(0.9f).setFont(fontUtils.fontRegular()));
    }

    public Table createHeaderTable(){

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{50f, 50f}))
                .useAllAvailableWidth();

        headerTable.setMarginBottom(0f);
        Cell logoCell = new Cell()
                .add(fontUtils.headerLogoConven())
                .setBorder(Border.NO_BORDER) // Hilangkan border garis tabel
                .setVerticalAlignment(VerticalAlignment.MIDDLE); // Perataan vertikal (MIDDLE/TOP)

        Paragraph address = fontUtils.address()
                .setTextAlignment(TextAlignment.RIGHT);

        Cell addressCell = new Cell()
                .add(address)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(logoCell);
        headerTable.addCell(addressCell);

        return headerTable;
    }
}

