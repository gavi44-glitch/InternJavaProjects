package com.example.demo.pdf;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
//import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Table;
import com.example.demo.utils.DocxUtils;


public class HeaderFooterHandler implements IEventHandler {
    private Table headerTable;
    private Table footerTable;

    public HeaderFooterHandler(Table headerTable, Table footerTable) {
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
}