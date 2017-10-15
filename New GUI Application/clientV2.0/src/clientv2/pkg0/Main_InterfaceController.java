/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;


import static clientv2.pkg0.ContactsController.privateKey;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedWriter;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author beste
 */
public class Main_InterfaceController extends ClientV20  implements Initializable {
    
    @FXML 
    public  JFXTextField messageToSend;
    @FXML
    public  JFXTextArea recMessage;
    
    public static String imageString;
    public static String videoString;
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
    
      public String Encrypt(String text){
        this.random1 = new Random((privateKey)); //Takes the private key as a seed and generate a new random number.
        publicKey = random1.nextInt(privateKey); //The public key is then created with the random number
        System.out.println("public key is:" +publicKey);   
        this.toHide  = new StringBuffer(text); //could have input  for original
        System.out.println("original"+ "  "+toHide);
        for (int i = 0; i<toHide.length(); i++)
        {
            int temp = 0;
            temp = (int) toHide.charAt(i);
            temp = (temp +  publicKey); //scrambles the string with the public key
            toHide.setCharAt(i, (char)temp); //overwrites the original message
        }
        System.out.println("encrypted" +"  " + toHide);
        String hide =String.valueOf(toHide);
        System.out.println("encrypted" +"  " + hide);
        return hide;       
    }
    
    
    
    
    
    @FXML
    public void handleButtonActionMain(ActionEvent event) throws IOException {
      /*  Stage stage = new Stage(); 
        Parent root;
            
        root = FXMLLoader.load(getClass().getResource("Contacts.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        try{
            OutputStream ostream = ClientV20.clientSock.getOutputStream();
            String sendMessage;
            String formattedtext;
            String encryptedM;
            PrintWriter pwrite=new PrintWriter(ostream,true);
            sendMessage = messageToSend.getText();
            String adress = String.valueOf(JOptionPane.showInputDialog(null,"Who do u want to send the message to"));
            encryptedM = Encrypt(sendMessage);
            formattedtext="T#"+adress+"#"+(encryptedM);
            
            //sending to server
            OutputStream os = ClientV20.clientSock.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(formattedtext);
            bw.flush();
            
            pwrite.println(formattedtext);
            System.out.println("me:>"+sendMessage+"\n");
            recMessage.appendText("me:>"+sendMessage+"\n");
            System.out.flush();
        }catch(IOException e)
        {
            
        }
         
     }
    
    @FXML
    public void handleAttachmentAction(MouseEvent event) throws FileNotFoundException, IOException {
        
        //Uncomment the code beneath when you have moved the above code to the Attachments_SectionController.java file
        
        Stage stage = new Stage(); 
        Parent root;
            
        root = FXMLLoader.load(getClass().getResource("Attachments_Selection.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
