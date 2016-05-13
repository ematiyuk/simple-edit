package simpleedit;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * IOManager.java
 *
 * @author Eugene Matiyuk (ematiyuk@gmail.com)
 * @year 2012
 */
public class IOManager {

    public static boolean saveFile(File filePath, String text) {
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(filePath);
            fWriter.write(text);
        } catch (IOException ioException1) {
            System.out.println("File writing error: " + ioException1);
            return false;
        } finally {
            if (fWriter != null) {
                try {
                    fWriter.close();
                } catch (IOException ioException2) {
                    System.out.println("File writing stream closing error: " + ioException2);
                }
            }
        }
        return true;
    }

    public static StringBuffer readFile(File filePath) {
        InputStream fileStream = null;
        StringBuffer strBuffer = new StringBuffer("");
        try {
            fileStream = new FileInputStream(filePath);
        } catch (FileNotFoundException exceptionFileNotFound) {
            System.out.println("File was not found while reading." + exceptionFileNotFound);
            return new StringBuffer("");
        }
        Scanner scanner = new Scanner(fileStream, "UTF-8");
        try {
            while (scanner.hasNextLine()) {
                strBuffer.append(scanner.nextLine() + '\n');
            }
        } finally {
            scanner.close();
        }
        return strBuffer;
    }

    public static boolean saveAsPDF(File filePath, String text) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(text));
        } catch (Exception exception) {
            System.out.println("Exception was occured while AsPDF file writing: " + exception);
            return false;
        } finally {
            document.close();
        }
        return true;
    }

    public static boolean saveAsHTML(File filePath, String text) {
        Document document = new Document();
        try {
            HtmlWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(text));
        } catch (DocumentException docException) {
            System.out.println("DocumentException was occured while AsHTML file writing: " + docException);
            return false;
        } catch (IOException ioException) {
            System.out.println("IOException was occured while AsHTML file writing: " + ioException);
            return false;
        } finally {
            document.close();
        }
        return true;
    }

    public static boolean saveAsRTF(File filePath, String text) {
        Document document = new Document();
        try {
            RtfWriter2.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(text));
        } catch (Exception exception) {
            System.out.println("Exception was occured while AsRTF file writing: " + exception);
            return false;
        } finally {
            document.close();
        }
        return true;
    }
}
