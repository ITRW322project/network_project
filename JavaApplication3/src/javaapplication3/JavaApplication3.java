/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author Stephan
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //encode image to Base64 String
        File f = new File("C:/Users/Stephan/Pictures/Family/IMG-20150714-WA0003.jpg");  //change path of image according to you
        FileInputStream fis = new FileInputStream(f);
        byte byteArray[] = new byte[(int)f.length()];
        fis.read(byteArray);
        String imageString = Base64.encodeBase64String(byteArray);
        
        System.out.print(imageString);
        
         //decode Base64 String to image
        FileOutputStream fos = new FileOutputStream("C:/Users/Stephan/Desktop/Fam.jpg"); //change path of image according to you
        byteArray = Base64.decodeBase64(imageString);
        fos.write(byteArray);
        
         fis.close();
         fos.close();

    }
    
}
