/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import com.jfoenix.controls.JFXTextArea;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author beste
 */
public class Main_InterfaceController implements Initializable {
    
    public static String imageString;
    
    //Sockets
    public static Socket clientSock;
    public SocketAddress address;
    public InetSocketAddress inet;
    public Proxy proxy;
    public String proxyHost;
    public int proxyPort;
    
    public String username, password;
    public int z = 0;
    
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
    private TextField txtProxyHost;
    @FXML
    private TextField txtProxyPort;
    @FXML
    private Button btnSend;
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
    @FXML
    private CheckBox chkProxy;
    
    //To add contacts
    protected List<String> attachments = new ArrayList<>();
    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    
    //ClientV20 client = new ClientV20();
    
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
        //btnSend .setDisable(true);
               
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
    
    public void ConnectToServer()
    {
        if(!chkProxy.isSelected())
        {
            try {
                
                clientSock = new Socket("169.1.39.136", 16000);
                
            } catch (IOException ex) {
            } 
        }
        else
        {
            try {
                
                proxyHost = txtProxyHost.getText();
                proxyPort=Integer.parseInt(txtProxyPort.getText());
                
                address = new InetSocketAddress(proxyHost, proxyPort);
                
                proxy = new Proxy(Proxy.Type.SOCKS, address);
                
                clientSock = new Socket(proxy);
                inet = new InetSocketAddress("169.1.39.136", 16000);
                clientSock.connect(inet);
                
            } catch (IOException ex) {
                System.out.println(ex);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Needed Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Entered all required information please!/n" + ex);

                alert.showAndWait();
            } 
        }
        
    }
    
    @FXML
    private void handleEnableProxy()
    {
        if(chkProxy.isSelected())
        {
            chkProxy.setSelected(true);
            txtProxyHost.setDisable(false);
            txtProxyPort.setDisable(false);
        }else
        {
            chkProxy.setSelected(false);
            txtProxyHost.setDisable(true);
            txtProxyPort.setDisable(true);
        }
    }
    
    @FXML
    public void handleButtonActionMain(ActionEvent event) throws IOException {
        try{
            OutputStream ostream = clientSock.getOutputStream();
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
            OutputStream os = clientSock.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(formattedtext);
            bw.flush();
            
            pwrite.println(formattedtext);
            System.out.println("me:>"+sendMessage+"\n");
            txtfChatRoom.getChildren().add(sendingText);
            System.out.flush();
        }catch(IOException e)
        {
            
        }
    }
    
    @FXML
    public void handleAttachmentAction(MouseEvent event) throws FileNotFoundException, IOException, InterruptedException {
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
               txtEnterMessage.appendText(imageString);
           }catch(NullPointerException e)
           {
               System.out.print(e);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(Main_InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(Main_InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
           }

           try
           {
               OutputStream ostream = clientSock.getOutputStream();
               String sendMessage;
               String formattedtext;
               String encryptedM;

               PrintWriter pwrite = new PrintWriter(ostream,true);
               sendMessage = txtEnterMessage.getText();

               String adress = String.valueOf(JOptionPane.showInputDialog(null,"Who do u want to send the message to"));
               encryptedM = Encrypt(sendMessage);
               formattedtext="T#"+adress+"#"+(encryptedM);

               //sending to server
               OutputStream os = clientSock.getOutputStream();
               OutputStreamWriter osw = new OutputStreamWriter(os);
               BufferedWriter bw = new BufferedWriter(osw);
               bw.write(formattedtext);
               bw.flush();

               pwrite.println(formattedtext);
               txtfChatRoom.getChildren().add(selectedI);
               System.out.flush();
           }catch(IOException e){

           }     
        }
        
        
        //Send video or audio file
        
        else if(extension.equals("mp4"))
        {
            //File compressed = compress(selectedFile, selectedFile.getName());
            
            DataOutputStream dout = new DataOutputStream(clientSock.getOutputStream());
            FileInputStream fin = new FileInputStream(selectedFile);
            byte[] buffer = new byte[4096];
		
            while (fin.read(buffer) > 0) {
                dout.write(buffer);
                System.out.println(buffer + " bytes sent");
                //Thread.sleep(50);
            }
            dout.close();
            fin.close();
            
            /*Main_InterfaceController obj = new Main_InterfaceController();   
            DataInputStream din = new DataInputStream(client.clientSock.getInputStream());
            DataOutputStream dout = new DataOutputStream(client.clientSock.getOutputStream());
            byte[] buffer = obj.CreateDataPacket("Video Received".getBytes("UTF8"));
            dout.write(buffer);
            System.out.println("obj sent");*/
        }
    }
    
    private byte[] CreateDataPacket(byte[] data)
    {
        byte[] packet = null;
        try {
            byte[] initialize = new byte[1];
            initialize[0] = 2;
            byte[] seperator = new byte[1];
            seperator[0] = 4;
            byte[] data_length = String.valueOf(data.length).getBytes("UTF8");
            packet = new byte[initialize.length+seperator.length+data_length.length+data.length];
            
            System.arraycopy(initialize, 0, packet, 0, initialize.length);
            System.arraycopy(data_length, 0, packet, initialize.length, data_length.length);
            System.arraycopy(seperator, 0, packet, initialize.length+data_length.length, seperator.length);
            System.arraycopy(data, 0, packet, initialize.length+data_length.length+seperator.length, data.length);
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main_InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packet;
    }
    
    @FXML
    private void handleVideoAction(MouseEvent event) throws IOException{
        Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Player.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    private void handleEmailAction(MouseEvent event) throws IOException{
        
        if(z == 0)
        {
            Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Sign_Up.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            z = 1;
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
     
    public static File compress(File source, String name) throws IOException
    {
        byte[] buffer = new byte[1024];
        File output = new File(name);
	FileInputStream fis = new FileInputStream(source);
	FileOutputStream fos = new FileOutputStream(output);
	GZIPOutputStream gzos = new GZIPOutputStream(fos);
	int read;
		
	while((read = fis.read(buffer)) != -1)
	{
            gzos.write(buffer, 0, read);
	}
		
	gzos.finish();
	gzos.close();
	fos.close();
	fis.close();
        
        return output;
    }
     
    public static void decompress(File source, File destination) throws IOException
    {
	byte[] buffer = new byte[1024];
	FileInputStream fin = new FileInputStream(source);
	GZIPInputStream gzin = new GZIPInputStream(fin);
	FileOutputStream fout = new FileOutputStream(destination);
	int read;
		
	while((read = gzin.read(buffer)) != -1)
	{
            fout.write(buffer, 0, read);
	}
        
	gzin.close();
	fout.close();
	fin.close();
    }
     
     @FXML
    private void handleLoginAction(ActionEvent event) {
        ConnectToServer();
        new login();
    }
    
    public void userExists()
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
            chkProxy.setVisible(false);
            txtProxyHost.setVisible(false);
            txtProxyPort.setVisible(false);
            
            //listenings listenings = new listenings();
    }
    
    public class login implements Runnable{
    Thread runner;
    String username = txtUsername.getText();
    String password = txtPassword.getText();
    String receiveMessage;
    boolean flag = false;
    //Constructor
    public login(){
        if(runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void run(){
        try
        {   
            //Communication stream assosiated with socket      
            InputStream istream = clientSock.getInputStream();
            OutputStream ostream = clientSock.getOutputStream();                   
            PrintWriter pwrite = new PrintWriter(ostream,true);               
            while(true)
            {    
                //receiving from server(receiveRead object)
                BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                System.out.println("Login thread running");
                //client_gui.TaChat.append("to Start the chat, type message and press Enter key\n");
               // receiveMessage = receiveRead.readLine();                              
                while(flag == false)
                {                  
                    //send to the server for validation
                    
                    String sendMessage = "L#" + username + "#" + password;                
                    pwrite.println(sendMessage);
                    receiveMessage = receiveRead.readLine();
                     System.out.println(receiveMessage);
                    if(receiveMessage.contentEquals("false"))//receive from server
                    {
                        JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Invalid Credentials", JOptionPane.WARNING_MESSAGE);                        
                        runner.stop();
                        
                    }else{
                        
                        flag = true;
                       // lblLUsername.setText(txtUsername.getText());
                        System.out.println(receiveMessage);
                        userExists();
                        runner.stop();
                    }
                }
            }
        }catch(Exception e){
            
        }  
    }   
}
    
    public class listenings implements Runnable{
        Thread runner;
        String receiveText;
        String[] chatArr;
        Text messageText;

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
                
                DataInputStream dis = new DataInputStream(clientSock.getInputStream());
                //receiving from server(receiveRead object)
                BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                receiveText = receiveRead.readLine();
                System.out.println(receiveText);
                chatArr[0] = receiveText;
                messageText = new Text(chatArr[0]);
                System.out.println("to Start the chat, type message and press Enter key");
                txtfChatRoom.getChildren().add(messageText); //appendText("to Start the chat, type message and press Enter key\n");
                while(true)
                {          
                    System.out.println("server:>"+receiveText);//displaying message
                    if((receiveText=receiveRead.readLine())!=null)//receive from server
                    {
                        System.out.println("server:>"+receiveText);//displaying message
                        if(!(receiveText.equals("V"))){
                            System.out.println("server:>"+receiveText);//displaying message
                            String text = Decrypt(receiveText);

                            chatArr[0] = text;
                            messageText = new Text(chatArr[0]);
                            txtfChatRoom.getChildren().add(messageText); //appendText("server:>"+(text)+"\n");
                        
                        }else{
                        //code to recive video
                            System.out.println("server:>"+receiveText);//displaying message
                            
                            FileOutputStream fos = new FileOutputStream("C:/Files/testfile.mp4");
                            byte[] buffer = new byte[4096];
                		
                            int filesize = 10000000; // Send file size in separate msg
                            int read = 0;
                            int totalRead = 0;
                            int remaining = filesize;
                            while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                                totalRead += read;
                                remaining -= read;
                                System.out.println("read " + totalRead + " bytes.");
                                fos.write(buffer, 0, read);
                            }
                		
                            fos.close();
                            dis.close();
                    }
                    }          
                }
            }catch(Exception e){
                System.out.println(e);
            }  
        } 
    }
    
}
