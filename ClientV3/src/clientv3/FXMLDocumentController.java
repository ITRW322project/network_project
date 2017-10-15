/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
    private JFXTextField txtHostIP;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtPassword;
    @FXML
    private JFXTextField messageToSend;
    @FXML
    private JFXListView<String> listUsers;
    
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
    
//    public void login() {
//		port = 16000;
//		server = "169.1.39.136";
//		System.out.println(server);
//		username = txtUsername.getText();
//		// test if we can start the connection to the Server
//		// if it failed nothing we can do
//		if(!start())
//			return;
//		connected = true;
//		btnLogin.setDisable(true);
//		btnLogout.setDisable(false);
//		txtUsername.setEditable(false);
//		txtHostIP.setEditable(false);
//    }
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
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
        if(extension.equals("jpg"))
        {
            ImageView selectedI = new ImageView();
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
            
        }
        
        
    }
    @FXML
    private void handleEmailAction(MouseEvent event){
       
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
