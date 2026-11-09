package com.example.demo.utils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFont;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.logging.Logger;


public class DocxUtils {

    public static void setPageSizeAndMargins(XWPFDocument doc,
                                             double bodyTopCm,
                                             double leftRightCm,
                                             double bodyBottomCm,
                                             double headerTopCm,
                                             double footerBottomCm ){

            CTSectPr sectPr = doc.getDocument().getBody().isSetSectPr()
                    ? doc.getDocument().getBody().getSectPr()
                    : doc.getDocument().getBody().addNewSectPr();

            CTPageMar pageMar = sectPr.isSetPgMar() ? sectPr.getPgMar() : sectPr.addNewPgMar();

            // 1. atur margin header dan footer
            pageMar.setHeader(BigInteger.valueOf((long) (headerTopCm * 567)));    // 1.0 cm = 567 dxa
            pageMar.setFooter(BigInteger.valueOf((long) (footerBottomCm * 567))); // 1.0 cm = 567 dxa

            // 2. atur margin kanan kiri untuk isi dokumen dan header
            pageMar.setLeft(BigInteger.valueOf((long) (leftRightCm * 567)));       // 0.6 cm = 340 dxa
            pageMar.setRight(BigInteger.valueOf((long) (leftRightCm * 567)));      // 0.6 cm = 340 dxa

            // 3. atur margin isi dokumen
            pageMar.setTop(BigInteger.valueOf((long) (bodyTopCm * 567)));          // e.g. 2.0 cm = 1134 dxa
            pageMar.setBottom(BigInteger.valueOf((long) (bodyBottomCm * 567)));    // e.g. 2.0 cm = 1134 dxa
        }

    // Utility untuk membaca objek File menjadi objek java.awt.Font
    // supaya file fisik.ttf -> jadi objek Font -> bisa dipanggil di Main

    public static String loadFontObject(File file) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, file);

            // mendaftarkan ke JVM agar sistem mengenali bentuk font-nya
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);

//            String fontName = customFont.getFontName();
//            String fontFamliy = customFont.getFamily();
//
//            System.out.println("Nama font : "+fontName + " Nama family font : " + fontFamliy);

            // bbl medium didalam family dibaca sbg bbl 05 medium
            // bbl regular dalam family dibaca sbg bbl 05 aja, tidak ada regular-> menyebabkan path tidak ketemu.
            return customFont.getFontName();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Gagal memuat font " + file.getName() + e.getMessage());
            return null;
        }
    }
    // Helper: Konversi milimeter (cm) ke dxa (1/1440pt)
//    private static long cmToDxa(double cm) {
//        return Math.round(cm * 566.929f);
//    }
    public String fontBBLRegular(){

        File fontFileRegular = new File("C:\\Program Files\\apache-tomcat-9.0.16\\webapps\\portaluser\\fonts\\BBL\\TTF\\BBLSans05-Regular.ttf");
        String fontBBLRegular = loadFontObject(fontFileRegular);

        return fontBBLRegular;
    }

    public String fontBBLMedium(){

        File fontFileMedium = new File ("C:\\Program Files\\apache-tomcat-9.0.16\\webapps\\portaluser\\fonts\\BBL\\TTF\\BBLSans05-Medium.ttf");
        String fontBBLMedium = loadFontObject(fontFileMedium);

        return fontBBLMedium;
    }

    public String fontBBLRegularItalic(){

        File fontFileRegularItalic = new File("C:\\Program Files\\apache-tomcat-9.0.16\\webapps\\portaluser\\fonts\\BBL\\TTF\\BBLSans05-RegularItalic.ttf");
        String fontBBLRegularItalic = loadFontObject(fontFileRegularItalic);

        return fontBBLRegularItalic;
    }

    public String fontBBLMediumItalic(){

        File fontFileMediumItalic = new File("bblMediumItalic = C:\\\\Program Files\\\\apache-tomcat-9.0.16\\\\webapps\\\\portaluser\\\\fonts\\\\BBL\\\\TTF\\\\BBLSans05-MediumItalic.ttf\n");
        String fontBBLMediumItalic = loadFontObject(fontFileMediumItalic);

        return fontBBLMediumItalic;
    }
}
