/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.TextField;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author beste
 */
public class Main_InterfaceController implements Initializable {
    
    @FXML 
    public static TextField messageToSend;
    @FXML
    public static JFXTextArea recMessage;
    
    public static String imageString;
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
    
    @FXML
    public void handleButtonActionMain(ActionEvent event) throws IOException {
        Stage stage = new Stage(); 
        Parent root;
            
        root = FXMLLoader.load(getClass().getResource("Contacts.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void handleAttachmentAction(MouseEvent event) throws FileNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null){
            System.out.println(selectedFile.getName());
        } else {
            System.out.println("File not valid");
        }
        
        try
        {
            FileInputStream fis = new FileInputStream(selectedFile);
            byte byteArray[] = new byte[(int)selectedFile.length()];
            fis.read(byteArray);
            imageString = Base64.encodeBase64String(byteArray);

            System.out.print(imageString);
        }catch(NullPointerException e)
        {
            System.out.print(e);
        }
        
        //Uncomment the code beneath when you have moved the above code to the Attachments_SectionController.java file
        
        /*Stage stage = new Stage(); 
        Parent root;
            
        root = FXMLLoader.load(getClass().getResource("Attachments_Selection.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }
    
     public static String Decrypt(String hide){
        StringBuffer unhide= new StringBuffer(hide);
        for (int i = 0; i<unhide.length(); i++)
        {
            int temp = 0;
            temp = (int) unhide.charAt(i);
            temp = temp - publicKey; //inverse of the scramble to get the plain text
            unhide.setCharAt(i, (char)temp);
        }
        System.out.println("After decryption: " + " " + unhide);
        String hiden = String.valueOf(unhide);
        return hiden;
    }
    
    @FXML
    private void handleEmailAction(MouseEvent event) throws IOException{
        Stage stage = new Stage(); 
        Parent root = null;
            
        root = FXMLLoader.load(getClass().getResource("Email.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleCalendarAction(MouseEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     
    
//    class listenings implements Runnable{
//        Thread runner;
//        listenings(){
//            if(runner == null){
//                runner = new Thread(this);
//                runner.start();
//            }
//        }
//     
//        public void run(){
//            try{   
//                // Communication stream assosiated with socket      
//                InputStream istream=ClientV20.clientSock.getInputStream();
//                //receiving from server(receiveRead object)
//                BufferedReader receiveRead=new BufferedReader(new InputStreamReader(istream));
//                System.out.println("to Start the chat, type message and press Enter key");
//                TaChat.appendText("to Start the chat, type message and press Enter key\n");
//
//                String receiveMessage ;    
//                while(true)
//                {          
//                    if((receiveMessage=receiveRead.readLine())!=null)//receive from server
//                    {
//                        System.out.println("server:>"+receiveMessage);//displaying message
//                        String text = Decrypt(receiveMessage);
//                        TaChat.appendText("server:>"+(text)+"\n");
//                    }          
//                }
//            }catch(Exception e){
//
//            }  
//        }
//    }
}
