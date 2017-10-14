/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class EmailController implements Initializable {
    /*@FXML private TextField txtTo;
    @FXML private TextField txtSubject;
    @FXML private TextField txtMessage;*/
    String email = "";
    String password = "";
    public String i = "signed_Out";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void handleSendAction(ActionEvent event) throws IOException{
        System.out.println(i);
        if(i != "signed_In")
        {
            Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Sign_Up.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            sendEmail("besterjennifer5@gmail.com","hope","happyness"/*txtTo.getText(), txtSubject.getText(), txtMessage.getText()*/, "besterjennifer5@gmail.com", email, password);
        }
    }
    
    public void Sign_Up(String e, String p)
    {
        email = e;
        password = p;
        sendEmail("besterjennifer5@gmail.com","hope","happyness"/*txtTo.getText(), txtSubject.getText(), txtMessage.getText()*/, "besterjennifer5@gmail.com", e, p);
        i = "signed_In";
    }
    
    public static void sendEmail(String to, String subject, String msg,
         String from, String userName, String password)
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() 
        {
            System.out.print("signed in");
            return new PasswordAuthentication(userName, password);
         }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Message send successfully....");
        } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
    
}
