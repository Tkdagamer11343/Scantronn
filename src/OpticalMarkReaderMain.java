import FileIO.PDFHelper;
import Filters.FixedThresholdFilter;
import core.DImage;

import javax.swing.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import processing.core.PConstants;
import processing.core.PImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.imageio.ImageIO;



// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain  {
    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        System.out.println("Loading pdf at " + pathToPdf);

        try {
            // Load the PDF
            PDDocument document = PDDocument.load(new File(pathToPdf));

            // ... rest of your code ...
            for (int i = 1; i < document.getNumberOfPages(); i++) {
                DImage img = new DImage(PDFHelper.getPageImage(pathToPdf, i));
                processPixels(img);

            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Create PrintWriter instances for the two CSV files
            PrintWriter csvWriter1 = new PrintWriter(new FileWriter("output1.csv"));
            PrintWriter csvWriter2 = new PrintWriter(new FileWriter("output2.csv"));

            // Write to the first CSV file
            csvWriter1.println("column1, column2, column3");
            csvWriter1.println("data1, data2, data3");
            // ... rest of your code ...

            // Write to the second CSV file
            csvWriter2.println("columnA, columnB, columnC");
            csvWriter2.println("dataA, dataB, dataC");
            // ... rest of your code ...

            // Close the PrintWriter instances
            csvWriter1.close();
            csvWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processPixels(DImage img) {
        // Create a new FixedThresholdFilter
        FixedThresholdFilter filter = new FixedThresholdFilter();

        // Apply the filter to the image
        DImage processedImage = filter.processImage(img);

        // Convert DImage to BufferedImage
        BufferedImage bimg = new BufferedImage(processedImage.getWidth(), processedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[] pixels = ((DataBufferInt) bimg.getRaster().getDataBuffer()).getData();
        processedImage.getRGB(0, 0, bimg.getWidth(), bimg.getHeight(), pixels, 0, bimg.getWidth());

        // Save the image
        String absolutePath = new File("processed_image.png").getAbsolutePath();
        File file = new File(absolutePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ImageIO.write(bimg, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
}
