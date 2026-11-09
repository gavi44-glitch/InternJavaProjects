package com.example.demo.pdf;

import com.example.demo.utils.ConfoUtils;
import com.example.demo.utils.FontUtils;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
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
public class NotifikasiBondOption {
    private final static Logger log = Logger.getLogger(TCFXOption.class);
    private Map<String, String> data;
//    public FontUtils fontUtils = new FontUtils();

    @Autowired
    private FontUtils fontUtils;

    @Value("${app.pdf.directory}")
    private String dir;

    // Definisi warna dan border abu-abu
    DeviceRgb headerBgColor = new DeviceRgb(240, 240, 240);
    SolidBorder greyBorder = new SolidBorder(new DeviceRgb(220, 220, 220), 0.5f);

    public String createPdf() {

        String password = "PASSWORDHEHE";

        String fileName = "Notifikasi Bond Option";
        String baseFile = dir + fileName + ".pdf";
        String secFile = dir + fileName + "_secured.pdf";

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
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                    new HeaderFooterHandler(createHeaderTable(), createFooterPage()));

            Document document = new Document(pdfDoc, PageSize.A4, false);
            document.setMargins(fontUtils.marginsTop(), fontUtils.marginRight(), fontUtils.marginsBottom(), fontUtils.marginLeft());

            // HEADER
            // header data
//            Table createDataHeader = createDataHeader();

            // PERIHAL

            Paragraph pPerihal = new Paragraph().setTextAlignment(TextAlignment.LEFT);
            Text perihalInd = new Text("Perihal : ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            Text isiPerihalInd = new Text(" Pemberitahuan Status Eksekusi Transaksi Bond Option - ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            Text isiPerihal2Ind = new Text("[Nomor Transaksi]").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            pPerihal.add(perihalInd);
            pPerihal.add(isiPerihalInd);
            pPerihal.add(isiPerihal2Ind);

            Paragraph pSalamPembuka1 = new Paragraph();

            Text salamPembuka1 = new Text("Kepada Yth. Bapak/Ibu\n" +
                    "[Nama Nasabah],\n" +
                    "Di tempat").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
//            Text salamPembuka2 = new Text("\n").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            pSalamPembuka1.add(salamPembuka1);

            Paragraph pSalamPembuka2 = new Paragraph();
            Text salamPembuka2 = new Text("Dengan hormat, ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            pSalamPembuka2.add(salamPembuka2);

//            Paragraph pSalamPembuka3 = new Paragraph().setMarginBottom(2f);
//            Text salamPembuka3 = new Text("Dengan hormat,").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
//            pSalamPembuka3.add(salamPembuka3);

            Paragraph pIsiNotifikasi1 = new Paragraph().setTextAlignment(TextAlignment.LEFT);
            Text isiNotifikasiAwal = new Text("Kami informasikan bahwa transaksi ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            Text textfxOption = new Text("Bond Option ").setFont(fontUtils.fontRegular()).setFontSize(fontUtils.sizeFont());
            Text isiNotifikasiTengah = new Text("yang Bapak/Ibu lakukan dengan PT Bank Permata. Tbk\n" +
                    "            memiliki tanggal jatuh tempo pada [dd/mm/yyyy]. Berdasarkan kondisi pasar dan ketentuan dalam perjanjian " +
                    "transaksi, berikut kami sampaikan status akhir dari transaksi tersebut: ").setFontSize(fontUtils.sizeFont());

            pIsiNotifikasi1.add(isiNotifikasiAwal);
            pIsiNotifikasi1.add(textfxOption);
            pIsiNotifikasi1.add(isiNotifikasiTengah);

//             Table Detail Trx
            Table tblDataDetail = createDataDetail();

            // document notes
            Paragraph pDocNotes = createDocNotes();

            Cell cell1 = new Cell().setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.LEFT).setTextAlignment(TextAlignment.LEFT);
            cell1.add(new Paragraph().add(new Text("\n")));

            // Build Document
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.05f));

            document.add(pPerihal);

            document.add(pSalamPembuka1);
            document.add(pSalamPembuka2);
//            document.add(pSalamPembuka3);

            document.add(pIsiNotifikasi1);

            Paragraph pPengantarDetail = new Paragraph().setTextAlignment(TextAlignment.LEFT);


//            Text textAwal = new Text("Berdasarkan kondisi pasar dan ketentuan dalam perjaniian transaksi, berikut kami sampaikan ")
//                    .setFont(fontUtils.fontRegular())
//                    .setFontSize(fontUtils.sizeFont());
//
//            Text textTengah = new Text("status akhir" +
//                    "            dari transaksi ")
//                    .setFont(fontUtils.fontMedium())
//                    .setFontSize(fontUtils.sizeFont());
//
//            Text textAkhir = new Text("tersebut:\n")
//                    .setFont(fontUtils.fontRegular())
//                    .setFontSize(fontUtils.sizeFont());
//
//            pPengantarDetail.add(textAwal);
//            pPengantarDetail.add(textTengah);
//            pPengantarDetail.add(textAkhir);
//
//            document.add(pPengantarDetail);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(tblDataDetail);
            document.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

            document.add(pDocNotes);


            document.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
    }

    public Table createDataDetail() throws IOException {

        Table tblDataDetail = new Table(UnitValue.createPointArray(new float[]{230f, 4f, 270f}))
                .useAllAvailableWidth();
        tblDataDetail.setFontSize(fontUtils.sizeFont()).setFont(fontUtils.fontRegular());
//        tblDataDetail.setBorder(Border.NO_BORDER);

        tblDataDetail.addCell(createCompactCell(new Paragraph("Detail Transaksi"), true));
        tblDataDetail.addCell(createCompactCell(new Paragraph(""), true));
        tblDataDetail.addCell(createCompactCell(new Paragraph("Keterangan"), true));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Transaksi Nasabah"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Beli / Jual]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Jenis Opsi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Call / Put]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Option Style"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("European Style"), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Nama Obligasi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: FR0102]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Kode ISIN"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: IDG000032XXXX]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Nama Penerbit"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("Pemerintah Republik Indonesia"), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Transaksi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Valuta Transaksi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Valuta Premi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal dan Waktu Fixing"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Jatuh Tempo"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Nominal Transaksi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: USD 100,000]").setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Harga Strike"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: 102.50% dari nilai nominal]").setFontColor(ColorConstants.GRAY), false));

       Paragraph pFixingLabel = new Paragraph("*Harga Fixing ")
                .add(new Text("(Fixing Rate)").setFont(fontUtils.fontRegularItalic()));

        tblDataDetail.addCell(createCompactCell(pFixingLabel, false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[Contoh: 101.00% dari nilai nominal]")
                .setFontColor(ColorConstants.GRAY), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Status Eksekusi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[EXERCISE / EXPIRY]")
                .setFont(fontUtils.fontRegularItalic()), false));

        tblDataDetail.addCell(createCompactCell(new Paragraph("Tanggal Eksekusi"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph(":"), false));
        tblDataDetail.addCell(createCompactCell(new Paragraph("[DD/MM/YYYY atau -]")
                .setFontColor(ColorConstants.GRAY), false));

        return tblDataDetail;
    }

    public Paragraph createDocNotes() throws IOException {
        Paragraph p = new Paragraph();
        p.setFontSize(fontUtils.sizeNotes());
        p.setFont(fontUtils.fontRegular()).setMultipliedLeading(1.0f);
        p.setTextAlignment(TextAlignment.JUSTIFIED);
//        Paragraph pNotesAwal = new Paragraph();
       Text notesAwal = new Text("*Fixing rate merupakan harga yang dinformasikan oleh Bank kepada Nasabah berdasarkan IDMA Mid-Rate dari " +
               "Bloomberg di tanggal dan waktu Fixing pukul 11:00 WIB.\n\n").setFontSize(8.5f).setFont(fontUtils.fontRegularItalic());

        Text notesTengah= new Text( "Untuk informasi lebih lanjut, Bapak Ibu dapat menghubungi Treasury Sales kami atau Relationship"+
                " Manager yang biasa menangani akun Bapak Ibu.\n\n" +
                "Demikian kami sampaikan, Terima kasih atas kepercayaan Bapak/ Ibu kepada Bank Permata.\n\n" +
                "Hormat kami\n" +
                "Treasury Operations\n" +
                "PT Bank Permata Tbk\n\n").setFont(fontUtils.fontRegular()).setFontSize(8.5f);
//        Text notesTengah = new Text("Treasury Operations\n").setFont(fontUtils.fontRegular()).setFontSize(7.5f);
//        Text notesAkhir = new Text("PT Bank Permata Tbk\n").setFontSize(7.5f);
        Text notesAkhir = new Text("*Notifikasi tidak memerlukan balasan maupun tanda tangan Nasabah").setFont(fontUtils.fontRegularItalic()).setFontSize(7.5f);
        p.add(notesAwal);
        p.add(notesTengah);
        p.add(notesAkhir);
//        p.add(notesAkhir);

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


    private Cell createCompactCell(Paragraph paragraph, boolean isHeader) {
        Cell cell = new Cell()
                .setBorder(greyBorder)
                .setPaddingTop(2f)
                .setPaddingBottom(2f)
                .setPaddingLeft(4f)
                .setPaddingRight(4f);

        if (isHeader) {
            cell.setBackgroundColor(headerBgColor);
            cell.setFont(fontUtils.fontMedium());
        } else {
            cell.setFont(fontUtils.fontRegular());
        }

        cell.add(paragraph.setMultipliedLeading(0.9f));

        return cell;
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

