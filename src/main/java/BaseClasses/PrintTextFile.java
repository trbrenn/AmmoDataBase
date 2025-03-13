package BaseClasses;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author trbrenn
 */

import javax.print.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class PrintTextFile {
    public static void main(String[] args) {
        try {
            // Specify the file to print
            File file = new File("C:\\Temp\\test.txt");
            InputStream inputStream = new FileInputStream(file);

            // Find the printer
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService myPrinter = null;

            for (PrintService printer : printServices) {
                if (printer.getName().equalsIgnoreCase("LABEL")) {
                    myPrinter = printer;
                    break;
                }
            }

            // Create a Doc object to print the file
            Doc doc = new SimpleDoc(inputStream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);

            // Create a PrintRequestAttributeSet
            PrintRequestAttributeSet printRequestAttributes = new HashPrintRequestAttributeSet();

            // Create a PrintJob
            DocPrintJob printJob = myPrinter.createPrintJob();

            // Print the document
            printJob.print(doc, printRequestAttributes);

            System.out.println("Printing initiated. Check your printer.");

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  
