/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

//Connect to the servre imports
import static clientv2.pkg0.client_gui.TaChat;
import static clientv2.pkg0.client_gui.clientSock;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
/**
 *
 * @author beste
 */
public class ClientV20 extends Application {
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
    
    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("Main_Interface.fxml"));
        Scene scene = new Scene(root);
        
        try {
            clientSock = new Socket("169.1.39.136", 16000);
           //TaChat.append("Client connected to server\n");
           clientv2.pkg0.listenings listenings = new clientv2.pkg0.listenings();
        } catch (IOException ex) {
            Logger.getLogger(client_gui.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
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
    
    public String messageToBeSent()
    {
        String COnvertPublic = publicKey+"";
        String Sender = COnvertPublic+toHide;
        return Sender;
    }
    
    class listenings implements Runnable{
    Thread runner;
    listenings(){
        if(runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
     
    public void run(){
        try{   
            // Communication stream assosiated with socket      
            InputStream istream=client_gui.clientSock.getInputStream();
            //receiving from server(receiveRead object)
            BufferedReader receiveRead=new BufferedReader(new InputStreamReader(istream));
            System.out.println("to Start the chat, type message and press Enter key");
            client_gui.TaChat.append("to Start the chat, type message and press Enter key\n");
         
            String receiveMessage ;    
            while(true)
            {          
                if((receiveMessage=receiveRead.readLine())!=null)//receive from server
                {
                    System.out.println("server:>"+receiveMessage);//displaying message
                    String text = client_gui.Decrypt(receiveMessage);
                    client_gui.TaChat.append("server:>"+(text)+"\n");
                }          
            }
        }catch(Exception e){
            
        }  
    } 
}
    
}
