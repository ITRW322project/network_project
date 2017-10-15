/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import static clientv2.pkg0.Main_InterfaceController.privateKey;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class ContactsController  implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ListView attachments_ListView;
    
    protected List<String> attachments = new ArrayList<>();
    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    @FXML
    private Button btnOkayContacts;
    
    StringBuffer  toHide; //initialize the string buffer to encrypt   
    Random random1;
    static int privateKey = 123456789;     //This is a private key generated from the user's number
    static int publicKey;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        attachments.add("Bruce Wayne");
        attachments.add("Diana Prince");
        attachments.add("Kent Clark");
        attachments.add("Kara Clark");
        attachments_ListView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(attachments));
    }    

    @FXML
    private void handleItemsAttachmentsAction(javafx.event.ActionEvent event) {
        //When contact name is selected and okay is pressed you should dtermine which action should do what... to whom message is send
        System.out.println("Send message to that person");
    }
    
    /*private void handleButtonAction(javafx.event.ActionEvent event) 
    {
        try
        {
            OutputStream ostream = ClientV20.clientSock.getOutputStream();
            String sendMessage;
            String formattedtext;
            String encryptedM;
            PrintWriter pwrite=new PrintWriter(ostream,true);
            sendMessage = Main_InterfaceController.messageToSend.getText();
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
            recMessage.appendText("me:>"+sendMessage+"\n");
            System.out.flush();
            
        }catch(IOException e){

        }     
    }*/
    
    public String Encrypt(String text)
    {
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
    
   
    
    public String messageToBeSent()
    {
        String COnvertPublic = publicKey+"";
        String Sender = COnvertPublic+toHide;
        return Sender;
    }
    
}
