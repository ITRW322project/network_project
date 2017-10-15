/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Stephan
 */
public class ClientV3 extends Application {
    
    private Stage pStage;
    private VBox chatLayout;
    public static Socket clientSock;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
          try 
        {
           clientSock = new Socket("169.1.39.136", 16000);
           //TaChat.append("Client connected to server\n");
           //listenings listenings = new listenings();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
          
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
//    private void initChat()
//    {
//        try
//        {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(ClientV3.class.getResource("FXMLDocument.fxml"));
//            fxmlLoader.setController(new FXMLDocumentController());
//            chatLayout = (VBox) fxmlLoader.load();
//            
//            Scene sc = new Scene(chatLayout);
//            pStage.setScene(sc);
//            pStage.show();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public class listenings implements Runnable{
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
            InputStream istream = clientSock.getInputStream();
            //receiving from server(receiveRead object)
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
            System.out.println("to Start the chat, type message and press Enter key");
            //client_gui.TaChat.append("to Start the chat, type message and press Enter key\n");
         
            String receiveMessage ;    
            while(true)
            {          
                if((receiveMessage=receiveRead.readLine())!=null)//receive from server
                {
                    System.out.println("server:>"+receiveMessage);//displaying message
                    //String text = client_gui.Decrypt(receiveMessage);
                    //client_gui.TaChat.append("server:>"+(text)+"\n");
                }          
            }
        }catch(Exception e){
            
        }  
    }   
}
    
}
