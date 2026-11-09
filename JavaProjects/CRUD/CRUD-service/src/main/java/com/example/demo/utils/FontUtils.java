package com.example.demo.utils;

//import com.idm.portal.web.action.core.Base;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

import static sun.management.Agent.getText;


@Component
public class FontUtils{

    private final static Logger log = Logger.getLogger(FontUtils.class);

    @Value("${bblMedium}")
    public String pathMedium ; //Path BBL Sans Mendium File

    @Value("${bblMediumItalic}")
    public String pathMediumItalic ;// Path BBL Sans Mendium Italic File

    @Value("${bblRegular}")
    public String pathRegular ;// Path BBL Sans Regular File

    @Value("${bblRegularItalic}")
    public String pathRegularItalic; // Path BBL Sans Regular Italic File


//    public String pathMedium = getText("bblMedium"); // Path BBL Sans Mendium File
//    public String pathMediumItalic = getText("bblMediumItalic"); // Path BBL Sans Mendium Italic File
//    public String pathRegular = getText("bblRegular"); // Path BBL Sans Regular File
//    public String pathRegularItalic = getText("bblRegularItalic"); // Path BBL Sans Regular Italic File


    // FONT BOLD
    public PdfFont fontMedium(){
        PdfFont font = null;
//        log.info("Path medium : " + pathMedium);
        try{
            font = PdfFontFactory.createFont(pathMedium, PdfEncodings.IDENTITY_H);
        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return font;
    }

    // FONT BOLD ITALIC
    public PdfFont fontMediumItalic(){
        PdfFont font = null;
        try{
            font = PdfFontFactory.createFont(pathMediumItalic, PdfEncodings.IDENTITY_H);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return font;
    }

    // FONT BIASA
    public PdfFont fontRegular(){
        PdfFont font = null;
        try{
//            log.info("Path regular berada di : " + pathRegular);
            font = PdfFontFactory.createFont(pathRegular, PdfEncodings.IDENTITY_H);

        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return font;
    }

    // FONT BIASA ITALIC
    public PdfFont fontRegularItalic(){
        PdfFont font = null;
        log.info("Path regular italic : " + pathRegularItalic);
        try{
            font = PdfFontFactory.createFont(pathRegularItalic, PdfEncodings.IDENTITY_H);
        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return font;
    }

    // SETTING WARNA MENGUUNAKAN HEX
    public DeviceRgb hexToRgb(String hexColor) {
        // Remove the leading "#" if present
        hexColor = hexColor.replace("#", "");

        // Parse the hex color code
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        return new DeviceRgb(r, g, b);
    }

    // WARNA BIRU AKTIVE
    public Color activeBlueColor(){
        Color color = null;
        try{
            color = hexToRgb("#0064FF");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }

        return color;
    }

    // WARNA BIRU MUDA
    public Color blueColor(){
        Color color = null;
        try{
            color = hexToRgb("#CCE0FF");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return color;
    }

    // WARNA PUTIH
    public Color whiteColor(){
        Color color = null;
        try{
            color = hexToRgb("#FFFFFF");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return color;
    }

    // WARNA HITAM
    public Color blackColor(){
        Color color = null;
        try{
            color = hexToRgb("#000000");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return color;
    }

    // WARNA ABU MUDA
    public Color lightGreyColor(){
        Color color = null;
        try{
            color = hexToRgb("#F3F3F3");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return color;
    }


    // LINE
    public LineSeparator line(){
        SolidLine line = new SolidLine(0.5f);
        LineSeparator ls = new LineSeparator(line);

        return ls;
    }

    // FOOTER DIBAWAH HALAMAN
    public Paragraph footer(){
        Paragraph footer = null;
        try{
            footer = new Paragraph();
//            footer.add((new Text("PermataBank.com\n").setFont(fontMedium()).setFontSize(8.5f))).setMultipliedLeading(1)
//                    .add(new Text("PT Bank Permata, Tbk. berizin dan diawasi oleh Otoritas Jasa Keuangan dan Bank Indonesia").setFont(fontRegular()).setFontSize(5.5f)).setMultipliedLeading(1);

            footer.add(new Text("PT Bank Permata, Tbk. berizin dan diawasi oleh Otoritas Jasa Keuangan dan Bank Indonesia").setFont(fontRegular()).setFontSize(7.5f)).setMultipliedLeading(1);

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }

        return footer;
    }


    // LOGO KONVEN
    public Table headerLogoConven(){
        Table tblHeader = null;
        try{
            Style headerStyle = new Style();
            headerStyle.setFont(fontRegular()).setFontSize(8.5f).setTextAlignment(TextAlignment.RIGHT);

            Image image = new Image(ImageDataFactory.create("resources/confo/permata_pdf.png"));
//            Image image = new Image(ImageDataFactory.create("permata_pdf.png"));
            image.setWidth(UnitValue.createPercentValue(57));
//            image.setMarginLeft(10);

            tblHeader = new Table(2);
            tblHeader.setWidth(UnitValue.createPercentValue(100));
            tblHeader.setBorder(Border.NO_BORDER);

            Cell tblHeaderC1 = new Cell();
            tblHeaderC1.add(image);
            tblHeaderC1.add((new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f)));
            tblHeaderC1.setWidth(UnitValue.createPercentValue(57));
            tblHeaderC1.setBorder(Border.NO_BORDER);

            Cell tblHeaderC2 = new Cell();
            tblHeaderC2.setBorder(Border.NO_BORDER);

            tblHeader.addCell(tblHeaderC1);
            tblHeader.addCell(tblHeaderC2);

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }

        return tblHeader;
    }

    // LOGO SYARIAH
    public Table headerLogoSyariah(){
        Table tblHeader = null;
        try{
            Style headerStyle = new Style();
            headerStyle.setTextAlignment(TextAlignment.RIGHT);

            Image image = new Image(ImageDataFactory.create("../resources/confo/permataSyr_pdf.png"));
//            Image image = new Image(ImageDataFactory.create("permataSyr_pdf.png"));
            image.setWidth(UnitValue.createPercentValue(57));
            image.setMarginLeft(10);

            tblHeader = new Table(2);
            tblHeader.setWidth(UnitValue.createPercentValue(100));
            tblHeader.setBorder(Border.NO_BORDER);

            Cell tblHeaderC1 = new Cell();
            tblHeaderC1.add(image);
            tblHeaderC1.add((new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f)));
            tblHeaderC1.setWidth(UnitValue.createPercentValue(57));
            tblHeaderC1.setBorder(Border.NO_BORDER);

            Cell tblHeaderC2 = new Cell();
            tblHeaderC2.setBorder(Border.NO_BORDER);

            tblHeader.addCell(tblHeaderC1);
            tblHeader.addCell(tblHeaderC2);

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }

        return tblHeader;
    }

    // IMAGE KONVEN
    public Image logoConven(){
        Image image = null;
        try{
            image = new Image(ImageDataFactory.create("resources/confo/permata_pdf.png"));
//            image = new Image(ImageDataFactory.create("permata_pdf.png"));
        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
        }
        return image;
    }

    // IMAGE SYARIAH
    public Image logoSyr(){
        Image image = null;
        try{
            image = new Image(ImageDataFactory.create("../resources/confo/permataSyr_pdf.png"));
        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
        }
        return image;
    }

    // ALAMAT BAGIAN BAWAH
    public Paragraph address(){
        Style headerStyle = new Style();
        headerStyle.setTextAlignment(TextAlignment.LEFT);

        Paragraph pHeader = new Paragraph().setMultipliedLeading(1f);
//        pHeader.add(new Text("PT Bank Permata Tbk.\n").setFont(fontMedium()).setFontSize(9f));
//        pHeader.add(new Text("Treasury Operations.\n").setFont(fontMedium()).setFontSize(9f));
//        pHeader.add(new Text("PermataBank Tower III. Jl. M.H. Thamrin Blok B II No.1 - 11th Floor.\n" +
//                "Bintaro Jaya Sektor VII-CBD, Pondok Aren, Tangerang 15224. Indonesia.\n").setFont(fontRegular()).setFontSize(8.5f));
//        pHeader.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

//        pHeader.setMarginRight(15f);
        pHeader.add(new Text("PermataBank Tower III Tbk.\n").setFont(fontMedium()).setFontSize(7f)).setFontColor(ColorConstants.GRAY);
        pHeader.add(new Text("Treasury Operations.\n").setFont(fontRegular()).setFontSize(7f)).setFontColor(ColorConstants.GRAY);
        pHeader.add(new Text("Jl. M.H. Thamrin Blok B II No.1 - 11th Floor\n" +
                "Bintaro Jaya Sektor VII-CBD \n " +
                "Pondok Aren, Tangerang 15224 \n" +
                "Indonesia.\n").setFont(fontRegular()).setFontColor(ColorConstants.GRAY).setFontSize(6.5f)).setFontColor(ColorConstants.GRAY);
        pHeader.add(new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f));

        return pHeader;
    }

    public Table logoAddress(){
        Table tblHeader = null;
        try{
            Image image = logoConven();
            image.setWidth(UnitValue.createPercentValue(100));
            image.setMarginLeft(10);

            Paragraph pTitle = new Paragraph().setFontSize(sizeTitle()).setMultipliedLeading(1f).setTextAlignment(TextAlignment.RIGHT).setMarginRight(10f);
            Text titleId = new Text("KONFIRMASI TRANSAKSI VALUTA ASING\n").setFont(fontMedium()).setFontSize(13f);
            Text titleEn = new Text("FOREIGN EXCHANGE CONFIRMATION").setFont(fontMediumItalic()).setFontSize(8.5f);
            pTitle.add(titleId).add(titleEn);

            tblHeader = new Table(2);
            tblHeader.setWidth(UnitValue.createPercentValue(100));
            tblHeader.setBorder(Border.NO_BORDER);

            Cell tblHeaderC1 = new Cell();
            tblHeaderC1.add(image);
            tblHeaderC1.add((new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f)));
            tblHeaderC1.setWidth(UnitValue.createPercentValue(33));
            tblHeaderC1.setBorder(Border.NO_BORDER);

            Cell tblHeaderC2 = new Cell();
            tblHeaderC2.add(pTitle);
            tblHeaderC2.add((new Paragraph().add(new Text("\n")).setMultipliedLeading(0.5f)));
            tblHeaderC2.setBorder(Border.NO_BORDER);
            tblHeaderC2.setWidth(UnitValue.createPercentValue(67));

            tblHeader.addCell(tblHeaderC1);
            tblHeader.addCell(tblHeaderC2);

        }  catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
        }
        return tblHeader;
    }

//    public static float marginsTop(){
//        return (25.4f);
//    }
//
//    public static float marginsBottom(){
//        return (2f * 25.4f);
//    }
//
//    public static float marginRight(){
//        return (0.6f * 25.4f);
//    }
//
//    public static float marginLeft(){
//        return (0.6f * 25.4f);
//    }

//    public static float marginsTop() {
//        return 49.88f;
//    }

    public static float marginsTop() {
        return 80f;
    }

    public static float marginsBottom() {
        return 49.88f;
    }

    public static float marginRight() {
        return 72f;
    }

    public static float marginLeft() {
        return 72f;
    }

    public static float sizeTitle(){return (12.5f);}

    public static float sizeFont(){return (9.5f);}

    public static float sizeNotes(){return (7.5f);}
}
