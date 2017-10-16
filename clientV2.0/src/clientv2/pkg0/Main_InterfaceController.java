/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import com.jfoenix.controls.JFXTextArea;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author beste
 */
public class Main_InterfaceController implements Initializable {
    
    public static String imageString;
    
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
    private ImageView imgLogo;
    @FXML
    private TextFlow txtfChatRoom;
    @FXML
    private TextFlow txtfContacts;
    @FXML
    private TextField txtEnterMessage;
    @FXML
    private Button btnSend;
    @FXML
    public static TextArea txtAChatRoom;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblUserEmail;
    @FXML
    private Label lblOpponentUser;
    @FXML
    private Label lblLogin;
    @FXML
    private Label lblLUsername;
    @FXML
    private Label lblLPassword;
    @FXML
    private ListView<String> lvContacts;
    
    //To add contacts
    protected List<String> attachments = new ArrayList<>();
    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    
    ClientV20 client = new ClientV20();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //DISABLE
        attachmentImage.setDisable(true);
        emailImage.setDisable(true); 
        calendarImage.setDisable(true); 
        txtEnterMessage.setDisable(true);
        //txtAChatRoom.setDisable(true); 
        txtfChatRoom.setDisable(true); 
        btnSend .setDisable(true);
               
        //VISIBLE    
        txtfContacts.setVisible(false); 
        txtUsername.setVisible(true);  
        txtPassword.setVisible(true); 
        btnLogin.setVisible(true); 
        lblUsername.setVisible(false); 
        lblUserEmail.setVisible(false);
        lblLogin.setVisible(true);
        lblLUsername.setVisible(true);
        lblLPassword.setVisible(true);
        lblOpponentUser.setVisible(false);
        lvContacts.setVisible(true);
        
        attachments.add("Bruce Wayne");
        attachments.add("Diana Prince");
        attachments.add("Kent Clark");
        attachments.add("Sara Lance");
        lvContacts.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(attachments));
    }  
    
    @FXML
    public void handleButtonActionMain(ActionEvent event) throws IOException {
        try{
            OutputStream ostream = ClientV20.clientSock.getOutputStream();
            String sendMessage;
            String formattedtext;
            String encryptedM;
            PrintWriter pwrite=new PrintWriter(ostream,true);
            sendMessage = txtEnterMessage.getText();
            
            String[] tempS = {String.format("me:> "+sendMessage)};
            Text sendingText = new Text(tempS[0]);
            
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
            recMessages.getChildren().add(sendingText);
            System.out.flush();
        }catch(IOException e)
        {
            
        }
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
    }
    
    @FXML
    private void handleEmailAction(MouseEvent event) throws IOException{
        
        if(client.i == 0)
        {
            Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Sign_Up.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            client.i = 1;
            stage.show(); 
        }
        else
        {
            Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Email.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    @FXML
    private void handleCalendarAction(MouseEvent event) {
        System.out.println("You clicked me!");
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
     
     @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        userExists(username,password);
            
    }
    
    public void userExists(String user, String pass)
    {
        //set u to each username in database when comparing and p to each password.
        //Trust me it doesn't work unless you do the above as such
        String u = "Jennifer";
        String p = "itrw322";
       if(u.equals(user) && p.equals(pass))  
        {
            attachmentImage.setDisable(false);
            emailImage.setDisable(false); 
            calendarImage.setDisable(false); 
            txtEnterMessage.setDisable(false);
            //txtAChatRoom.setDisable(false); 
            txtfChatRoom.setDisable(false); 
            btnSend .setDisable(false);

            //VISIBLE    
            txtfContacts.setVisible(true); 
            txtUsername.setVisible(false);  
            txtPassword.setVisible(false); 
            btnLogin.setVisible(false); 
            lblUsername.setVisible(true); 
            lblUserEmail.setVisible(true);
            lblLogin.setVisible(false);
            lblLUsername.setVisible(false);
            lblLPassword.setVisible(false);
            lblOpponentUser.setVisible(true);
            lvContacts.setVisible(true);
        }    
        else
            JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Invalid Credentials", JOptionPane.WARNING_MESSAGE);   
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
