/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author ernsferrari
 */
public class PDFReader {

    private static String path;
    private static boolean operationSuccess;

    private static ArrayList<String> files() {

        ArrayList<String> validFiles = new ArrayList<String>();

        Scanner scan = new Scanner(System.in);
        System.out.print("Indica la ruta del directorio donde se encuentran los archivos pdf: ");
        path = scan.next();

        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getAbsolutePath().endsWith("pdf")) {
                    validFiles.add(files[i].getName());
                }
            }
        } else {
            System.out.println("No hay archivos con extensi\u00f3n pdf en este directorio");
            operationSuccess = false;
        }

        return validFiles;
    }

    private static String readFile(String fileName) throws IOException {

        PDDocument pd = null;
        COSDocument cd = null;
        PDFParser parser;
        String text = "";
        File input;
        try {
            if (path.substring(path.length()).equals("/")) {
                input = new File(path + fileName);
            } else {
                input = new File(path + "/" + fileName);
            }

            parser = new PDFParser(new FileInputStream(input));
            parser.parse();

            cd = parser.getDocument();
            pd = new PDDocument(cd);

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(pd.getNumberOfPages());

            text = processText(stripper.getText(pd));
                        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            if(pd != null) {
                pd.close();
            } else if (cd != null) {
                cd.close();
            }
        }

        return text;
    }

    private static String processText(String text) {

        String finalText = "";
        String[] lines = text.split(System.getProperty("line.separator"));

        boolean print = false;

        for (String line : lines) {
            if (line.toLowerCase().contains("soldaduras") || print) {

                if (line.equals(line.toUpperCase()) && !line.toLowerCase().contains("soldaduras")) {
                    break;
                } else {
                    finalText = finalText + line + "\n";
                    print = true;
                }
            }
        }

        return finalText;
    }

    private static void writeFile(String text, boolean overwrite) throws IOException {

        File dir;

        if (path.substring(path.length()).equals("/")) {
            dir = new File(path + "final");
        } else {
            dir = new File(path + "/final");
        }

        if (!dir.exists()) {
            dir.mkdir();
        }

        File file;

        if (path.substring(path.length()).equals("/")) {
            file = new File(path + "final/soldaduras.txt");
        } else {
            file = new File(path + "/final/soldaduras.txt");
        }

        if (!file.exists()) {
            file.createNewFile();
        } else if (overwrite) {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(text);

            bw.close();
        } else {

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(text);

            bw.close();
        }
    }

    public static void main(String[] args) throws IOException {

        operationSuccess = true;

        ArrayList<String> fileNames = files();

        boolean overwrite = true;
        
        for (String name : fileNames) {
            String text = name + ":\n" + readFile(name) + "\n";
            writeFile(text, overwrite);
            overwrite = false;
        }
        
        if(operationSuccess){
            System.out.println("Operaci\u00f3n realizada con exito");
        }

    }
}
