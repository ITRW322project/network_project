/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class EmailController implements Initializable {
    @FXML
    private TextField txtTo;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextArea txtMessage;
    @FXML
    private Button btnSend;
    
    String email, password;
    
    Main_InterfaceController mIC = new Main_InterfaceController();
    ClientV20 client = new ClientV20();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void handleSendAction(ActionEvent event) throws IOException{

        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Password", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        JTextField txtusername = new JTextField("");
        controls.add(txtusername);
        JPasswordField txtpassword = new JPasswordField();
        txtusername.addAncestorListener(new RequestFocusListener(false));
        controls.add(txtpassword);
        p.add(controls, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
            null, p, "Log In", JOptionPane.QUESTION_MESSAGE);
        
        String to = txtTo.getText();
        String subject = txtSubject.getText();
        String message = txtMessage.getText();
        System.out.println("okay " + txtusername.getText());
        System.out.println(txtpassword.getText());
        System.out.println("why" + to);
        System.out.println(subject);
        System.out.println(message);
        
        sendEmail(to,subject, message, txtusername.getText(), txtusername.getText(), txtpassword.getText());
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
