/****************************************************************************************************************
 * RelaodingLabelPDF.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 1-22-25																								*
 *  																											*
 * This program generates the PDF version of the reloading label and saves it to the hard drive.																								*
 * 																												*
 ***************************************************************************************************************/

package ReloadEvent;

import BaseClasses.CalendarTest;
import BaseClasses.FormatFloat;
import BulletsEvent.Bullets;
import CasesEvent.Cases;
import PowderEvent.Powder;
import PrimerEvent.Primer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JFileChooser;

public class RelaodingLabelPDF {
    private static String       FILE = "c:\\barcode Image\\myFirstPdf.pdf";
    private static Font         catFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
    private static Font         titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    private static Font         boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);
    private static Font         smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.BOLD);
    private static PdfWriter    pdfWriter = null;
    private Document            document;
    private ReloadList          reloadList;
    private FormatFloat         ff = new FormatFloat();
    private CalendarTest        ct = new CalendarTest();
    private ReloadList          al = new ReloadList();
    private Bullets             bs = new Bullets();
    private Cases               cs = new Cases();
    private Primer              pr = new Primer();
    private Powder              pw = new Powder();
    
    public RelaodingLabelPDF(){
    }
    
    public RelaodingLabelPDF(ReloadList rl){
        reloadList = rl;
        pw.setPowderLotNumber(al.getPowderLotNumber());
        bs.setLotNumber(al.getBulletLotNumber());
        cs.setLotNumber(al.getCaseLotNumber());
        pr.setLotNumber(al.getPrimerLotNumber()); 
    }
    
    public void printReloadningLabel() throws PrintException, FileNotFoundException{
        InputStream in = new FileInputStream(FILE);
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;

        // find the printing service
        AttributeSet attributeSet = new HashAttributeSet();
        attributeSet.add(new PrinterName("FX", null));
        attributeSet.add(new Copies(1));

        PrintService[] services = PrintServiceLookup.lookupPrintServices(
                DocFlavor.INPUT_STREAM.PDF, attributeSet);

        //create document
        Doc doc = new SimpleDoc(in, flavor, null);

        // create the print job
        PrintService service = services[0];
        DocPrintJob job = service.createPrintJob();

        // monitor print job events
    }
    
    public void saveReloadningLabel(String path){
        try {
            if(!path.trim().isBlank()){
               FILE = path;
            } else {
                JFileChooser saveAs = new JFileChooser();
                saveAs.setSelectedFile(new File("Relaoding Label "+reloadList.getLotNumber()+".pdf"));
                saveAs.showSaveDialog(new Frame());
                if(saveAs.getSelectedFile() != null ) {
                    File f = saveAs.getSelectedFile();
                    FILE = f.getPath();
                    //System.out.println(FILE);
                }
            }
            generateDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void generateDocument(Document document) throws Exception {
        // Get the page size A3 (297mm x 420mm) in points
        double width = 50;
        double height = 80;
        Rectangle label = new Rectangle(PageSize.A8);
        //System.out.println(PageSize.A8);
        document = new Document(label);
        document.setMargins(0, 0, 0, 0);
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        addMetaData(document);
        addContent(document);
        addBarCodeImage(document, reloadList.getLotNumber());
        document.close();
    }
    
    private void addMetaData(Document document) {
        document.addTitle("Reloading Label");
        document.addSubject("Reloading Data");
        document.addKeywords("Reloading");
        document.addAuthor("Todd Brenneman");
        document.addCreator("Todd Brenneman");
   }
    
    private void addContent(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
 
        Paragraph p1 = new Paragraph("Reloading Label", titleFont);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(p1);
        addEmptyLine(preface, 1);
 
        PdfPTable table = new PdfPTable(1);
        PdfPCell c1 = new PdfPCell(new Phrase(reloadList.getCaliber(),catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Caliber\n    ",smallBold));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase(bs.getBulletMaker()+", "+bs.getWeight()+" grains, "+ bs.getDescription(),catFont));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Bullet Weight, Brand, & Style\n    ",smallBold));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase(reloadList.getPowderWeight()+" grains, "+pw.getPowderMaker()+" "+pw.getPowderName(),catFont));
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("Powder Weight & Brand\n    ",smallBold));
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c6);
        preface.add(table);
            
        PdfPTable table2 = new PdfPTable(2);
        PdfPCell c7 = new PdfPCell(new Phrase(pr.getPrimerMaker(),catFont));
        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c7);
        PdfPCell c9 = new PdfPCell(new Phrase(cs.getCaseMaker(),catFont));
        c9.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c9);
        PdfPCell c8 = new PdfPCell(new Phrase("Primer\n    ",smallBold));
        c8.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c8);
        PdfPCell c10 = new PdfPCell(new Phrase("Case\n    ",smallBold));
        c10.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c10);
        PdfPCell c11 = new PdfPCell(new Phrase(" "+reloadList.getTimesLoaded(),catFont));
        c11.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c11);
        PdfPCell c13 = new PdfPCell(new Phrase(ff.floatConvert(reloadList.getCaseLength(),1,3),catFont));
        c13.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c13);
        PdfPCell c12 = new PdfPCell(new Phrase("Times loaded\n    ",smallBold));
        c12.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c12);
        PdfPCell c14 = new PdfPCell(new Phrase("Case Length\n    ",smallBold));
        c14.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c14);
        PdfPCell c15 = new PdfPCell(new Phrase(ff.floatConvert(reloadList.getOverAllLength(),1,3),catFont));
        c15.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c15);
        PdfPCell c16 = new PdfPCell(new Phrase(ct.formatedString(reloadList.getLoadDate()),catFont));
        c16.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c16);
        PdfPCell c17 = new PdfPCell(new Phrase("Overall Length\n    ",smallBold));
        c17.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c17);
        PdfPCell c18 = new PdfPCell(new Phrase("Date\n    ",smallBold));
        c18.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.addCell(c18);        
        preface.add(table2);
        
        PdfPTable table3 = new PdfPTable(1);
        PdfPCell c20 = new PdfPCell(new Phrase(reloadList.getLotNumber(),catFont));
        c20.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3.addCell(c20);
        PdfPCell c24 = new PdfPCell(new Phrase("Lot Number",smallBold));
        c24.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3.addCell(c24);
        preface.add(table3);        
        document.add(preface);
    }    
    
    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" ", catFont));
        }
    }
    
    private void addBarCodeImage(Document document, String barcode){
        try{
            // Digits for encode function of EAN_8
            String digits = barcode;

            String filepath = this.getClass().getResource(".").getPath();
            int index = filepath.indexOf("target");
            filepath = filepath.substring(1, index);
            filepath = filepath + "EANbarcode.jpg";
            System.err.println(filepath);
            // Barcode C128 writer object
            Code128Writer c128writer = new Code128Writer();

            BitMatrix matrix = c128writer.encode(digits, BarcodeFormat.CODE_128, 150, 20);

            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(filepath));

            //System.out.println("Barcode image file is created...");
            Image image1 = Image.getInstance(filepath);
            image1.setAlignment(Element.ALIGN_CENTER);
            image1.scaleAbsolute(150, 20);
            
            //Add to document
            document.add(image1);
            // Delete the File
            File file = new File(filepath);
            if (file.delete()) {
                //System.out.println("File deleted successfully");
            }
            else {
                System.err.println("Failed to delete the file");
            }
        }catch(Exception e){
            System.err.println("Error generatering the barcode: "+e);
        }
    }

    public static String getFILE() {
        return FILE;
    }

    public static void setFILE(String FILE) {
        RelaodingLabelPDF.FILE = FILE;
    }

    public ReloadList getReloadList() {
        return reloadList;
    }

    public void setReloadList(ReloadList reloadList) {
        this.reloadList = reloadList;
    }

    public Bullets getBullet() {
        return bs;
    }

    public void setBullet(Bullets bs) {
        this.bs = bs;
    }

    public Cases getCase() {
        return cs;
    }

    public void setCase(Cases cs) {
        this.cs = cs;
    }

    public Primer getPrimer() {
        return pr;
    }

    public void setPrimer(Primer pr) {
        this.pr = pr;
    }

    public Powder getPowder() {
        return pw;
    }

    public void setPowder(Powder pw) {
        this.pw = pw;
    }
     
    public static void main(String argv[]){
        ReloadList rl = new ReloadList();
        rl.setLotNumber(rl.getNewLotNumber());         
        rl.setCaliber("223 Remington");           
        rl.setLoadDate("2007-07-12");          
        rl.setBulletLotNumber("22020060001");   
        rl.setCaseLotNumber("8252006001r");     
        rl.setPowderLotNumber("09023452341");   
        rl.setPowderWeight("0.12");      
        rl.setPrimerLotNumber("PALD270");   
        rl.setTimesLoaded("12");       
        rl.setCaseLength("1.234");        
        rl.setOverAllLength("2.243");     
        rl.setFireArmID("1234");         
        rl.setChronoGraphDataID("12"); 
        rl.setCrimp("Roll");             
        rl.setCount("50");             
        rl.setNotes("");  
        
        RelaodingLabelPDF rlPDF = new RelaodingLabelPDF(rl);
        Cases cs = new Cases();	
            cs.setCaliber("9mm Parabelum");
            cs.setCaseMaker("Remington");
            cs.setCostPerCase("0.0");
            cs.setLotCost(0);
            cs.setLotCount("125");
            cs.setLotNumber("129120931029");
            cs.setType("Range Brass");
        rlPDF.setCase(cs);
        Powder pw = new Powder();
            pw.setPowderName("400 Hot Shot");
            pw.setPowderMaker("UMC");
            pw.setLotWeightInLbs(1);
            pw.setPowderLotNumber("12345323233");
            pw.setLotCost(22);
            pw.setEmpty(false);
            pw.setCostPerGrain();
            pw.setDatePurchased("2004-12-12");
        rlPDF.setPowder(pw);
        Primer pr = new Primer();
            pr.setPrimerSize("Small Pistol");
            pr.setPrimerMaker("CCI");
            pr.setModelNumber("500");
            pr.setLotNumber("1345265324123542");
            pr.setLotCount(1000);
            pr.setLotCost(36);
            pr.setEmpty(false);
            pr.setCostPerPrimer();  
        rlPDF.setPrimer(pr);
        Bullets bt = new Bullets();
            bt.setBulletMaker("MidwayUSA");
            bt.setBC((float)0.243);
            bt.setCaliber("0.223");
            bt.setWeight("55.0");
            bt.setDescription("FMJBT");
            bt.setLotNumber("20080425001");
            bt.setLotCount(500);
            bt.setLotCost((float)29.99);
            bt.setCostPerBullet((float)0.0);
            bt.setCastAlloy("");
            bt.setEmpty(false);
            bt.setMoldNumber("");
        rlPDF.setBullet(bt);
        String path = "";
        rlPDF.saveReloadningLabel(path);
        try{
            rlPDF.printReloadningLabel();
        } catch(Exception e){
            
        }
        System.exit(0);
    }
}
