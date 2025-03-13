/****************************************************************************************************************
 * ManufacturedAmmoPDF.java 																						*
 * 																												*
 * Author: Todd Brenneman																						*
 * Version: 1.0																									*
 * Date: 2-20-25																								*
 *  																											*
 * This program generates the PDF version of the ammunition label and saves it to the hard drive.																								*
 * 																												*
 ***************************************************************************************************************/

package ManufacturedAmmoEvent;

import BaseClasses.CalendarTest;
import BaseClasses.FormatFloat;
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
import java.nio.file.Paths;
import javax.swing.JFileChooser;

public class ManufacturedAmmoPDF {
    private static String       FILE = "c:\\barcode Image\\myFirstPdf.pdf";
    private static Font         catFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    private static Font         titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    //private static Font         boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.BOLD);
    //private static Font         smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 4, Font.BOLD);
    private static PdfWriter    pdfWriter = null;
    private Document            document;
    private ManufacturedAmmo    ammo;
    private FormatFloat         ff = new FormatFloat();
    private CalendarTest        ct = new CalendarTest();
    
    public ManufacturedAmmoPDF() {
    
    }
    
    public ManufacturedAmmoPDF(ManufacturedAmmo ma){
        this.ammo = ma;
    }
    
    public void saveReloadningLabel(String path){
        try {
            if(!path.trim().isBlank()){
               FILE = path;
            } else {
                JFileChooser saveAs = new JFileChooser();
                saveAs.setSelectedFile(new File("Ammo Label "+ammo.getLotNumber()+".pdf"));
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
        
    private void addContent(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
 
        Paragraph p1 = new Paragraph("Ammunition Label", titleFont);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(p1);
        addEmptyLine(preface, 1);
 
        PdfPTable table = new PdfPTable(1);
        PdfPCell c1 = new PdfPCell(new Phrase("Caliber: "+ammo.getCaliber(),catFont));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Manufacturer: "+ammo.getManufacturer(),catFont));
        c2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Date Purchased: "+ct.formatedString(ct.convertDate(ammo.getDatePurchased())),catFont));
        c3.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Bullet: "+ammo.getBullet(),catFont));
        c4.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Bullet Weight: "+ff.floatConvert(ammo.getBulletWeight(), 0, 1),catFont));
        c5.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("Round Count: "+ammo.getCount(),catFont));
        c6.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Phrase("Lot Number: "+ammo.getLotNumber(),catFont));
        c7.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c7);
        
        preface.add(table);    
        document.add(preface);
    }
        
    private void generateDocument(Document document) throws Exception {
        // Get the page size in points
        Rectangle label = new Rectangle(PageSize.A8.rotate());
        //System.out.println(PageSize.A8);
        document = new Document(label);
        document.setMargins(0, 0, 0, 0);
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        
        addMetaData(document);
        addContent(document);
        addBarCodeImage(document, ammo.getLotNumber());
        document.close();
    }
    
    private void addMetaData(Document document) {
        document.addTitle("Ammunition Label");
        document.addSubject("Ammunition Data");
        document.addKeywords("Ammunition");
        document.addAuthor("Todd Brenneman");
        document.addCreator("Todd Brenneman");
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
            image1.scaleAbsolute(180, 20);
            
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
public static void main(String argv[]){
        ManufacturedAmmo rl = new ManufacturedAmmo();
        rl.setLotNumber("20270223jat123");         
        rl.setCaliber("223 Remington");           
        rl.setDatePurchased("2007-07-12");          
        rl.setManufacturer("Anyone");
        rl.setBullet("Twister back slide");
        rl.setBulletWeight((float)120.0);
        rl.setCount("50");             
        ManufacturedAmmoPDF malPDF = new ManufacturedAmmoPDF(rl);        
        String path = "";
        malPDF.saveReloadningLabel(path);
        System.exit(0);
    }}
