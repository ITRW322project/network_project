/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author Stephan
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXButton btnSend;
    @FXML
    private JFXButton btnLogOff;
    @FXML
    private JFXButton btnLogIn;
    @FXML
    private TextFlow recMessages;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField messageToSend;
    @FXML
    private JFXListView<String> listUsers;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXScrollPane sp;
    
    public String imageString;
    
    private ObservableList<String> users;
    
    byte[] buffer = new byte[16384];    
    private static int byteread;
    private static int current = 0;
    
    private boolean connected;
    private String server, username;
    private int port;
    
    private ObjectInputStream sInput;		
    private ObjectOutputStream sOutput;		
    private Socket socket;
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
    
    @FXML
    private ImageView attachmentImage;
    @FXML
    private ImageView emailImage;
    @FXML
    private ImageView calendarImage;
    @FXML
    private javafx.scene.control.Label lblUsers;
    @FXML
    private javafx.scene.control.Label lblPassword;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try{
            OutputStream ostream = ClientV3.clientSock.getOutputStream();
            String sendMessage;
            String formattedtext;
            String encryptedM;
            PrintWriter pwrite=new PrintWriter(ostream,true);
            sendMessage = messageToSend.getText();
            
            String[] tempS = {String.format("me:> "+sendMessage)};
            Text sendingText = new Text(tempS[0]);
            
            String adress = String.valueOf(JOptionPane.showInputDialog(null,"Who do u want to send the message to"));
            encryptedM = Encrypt(sendMessage);
            formattedtext="T#"+adress+"#"+(encryptedM);
            
            //sending to server
            OutputStream os = ClientV3.clientSock.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(formattedtext);
            bw.flush();
            
            pwrite.println(formattedtext);
            System.out.println("me:>"+sendMessage+"\n");
            recMessages.getChildren().add(sendingText);
            System.out.flush();
        }catch(IOException e)
        {
            
        }
    }
    @FXML
    public void handleAttachmentAction(MouseEvent event) throws FileNotFoundException, IOException{
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterMP4 = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.MP4");
        FileChooser.ExtensionFilter extFilterMP3 = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.MP3");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterMP4, extFilterMP3);
        
        File selectedFile = fc.showOpenDialog(null);
        
        String sF= selectedFile.getName();
        String extension = sF.substring(sF.lastIndexOf(".") + 1, sF.length());
        
        //Send Image File
        if(extension.equals("jpg"))
        {
            ImageView selectedI = new ImageView();
            selectedI.setFitHeight(150);
            selectedI.setFitWidth(200);
            selectedI.setPreserveRatio(true);
            
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                selectedI.setImage(image);
            } catch (IOException ex) {
            }
         
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
               messageToSend.appendText(imageString);
           }catch(NullPointerException e)
           {
               System.out.print(e);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }

           try
           {
               OutputStream ostream = ClientV3.clientSock.getOutputStream();
               String sendMessage;
               String formattedtext;
               String encryptedM;

               PrintWriter pwrite=new PrintWriter(ostream,true);
               sendMessage = messageToSend.getText();

               /*String[] tempS = {sendMessage};
               Text text = new Text(tempS[0]);*/

               String adress = String.valueOf(JOptionPane.showInputDialog(null,"Who do u want to send the message to"));
               encryptedM = Encrypt(sendMessage);
               formattedtext="T#"+adress+"#"+(encryptedM);

               //sending to server
               OutputStream os = ClientV3.clientSock.getOutputStream();
               OutputStreamWriter osw = new OutputStreamWriter(os);
               BufferedWriter bw = new BufferedWriter(osw);
               bw.write(formattedtext);
               bw.flush();

               pwrite.println(formattedtext);
               recMessages.getChildren().add(selectedI);
               System.out.flush();
           }catch(IOException e){

           }     
        }
        
        
        //Send video file
        else if(extension.equals("mp4"))
        {
            InputStream is = socket.getInputStream();
            selectedFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(selectedFile);
            BufferedOutputStream out = new BufferedOutputStream(fos);
            byteread = is.read(buffer, 0, buffer.length);
            current = byteread;
            
            while ((byteread = is.read(buffer, 0, buffer.length)) != -1) 
            {
                out.write(buffer, 0, byteread);
            }
            out.write(buffer, 0, current);
            out.flush();

            socket.close();
            fos.close();
            is.close();

        }
        
        else if(extension.equals("mp3"))
        {
            try
            {
                Socket obj_Client=new Socket(InetAddress.getByName("169.1.39.136"), 16000);
                DataInputStream din=new DataInputStream(obj_Client.getInputStream());
                DataOutputStream dout=new DataOutputStream(obj_Client.getOutput)
            }catch(UnknownHostException e){
                System.out.println(e);
            }catch(IOException e){
                System.out.println(e);
            }
            
        }  
    }
    
    @FXML
    private void handleEmailAction(MouseEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("EmailFXML.fxml"));
        
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void handleCalendarAction(MouseEvent event) {
    }
    
    @FXML
    private void handleLoginAction(MouseEvent event){
       
    }
    @FXML
    private void handleLogOutAction(MouseEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
