/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

//Connect to the servre imports
import static clientv2.pkg0.client_gui.TaChat;
import static clientv2.pkg0.client_gui.clientSock;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.VBox;
/**
 *
 * @author beste
 */
public class ClientV20 extends Application {
    
    
    public static Socket clientSock;
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main_Interface.fxml"));
        Scene scene = new Scene(root);
        
        try 
        {
           clientSock = new Socket("169.1.39.136", 16000);
           //TaChat.append("Client connected to server\n");
           listenings listenings = new listenings();
        } catch (IOException ex) {
            Logger.getLogger(client_gui.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
            InputStream istream=clientSock.getInputStream();
            //receiving from server(receiveRead object)
            BufferedReader receiveRead=new BufferedReader(new InputStreamReader(istream));
            System.out.println("to Start the chat, type message and press Enter key");
            
           //recMessage.appendText("to Start the chat, type message and press Enter key\n");
         
            String receiveMessage ;    
            while(true)
            {          
                if((receiveMessage=receiveRead.readLine())!=null)//receive from server
                {
                    String text = Main_InterfaceController.Decrypt(receiveMessage);
                    System.out.println("server:>"+(text)+"\n");
                   // System.out.println("server:>"+receiveMessage);//displaying message
                   
                    
                   //recMessage.appendText("server:>"+(text)+"\n");
                }          
            }
        }catch(Exception e){
            
        }  
    } 
}
    
}
