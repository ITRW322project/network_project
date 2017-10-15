/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class ContactsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ListView attachments_ListView;
    
    protected List<String> attachments = new ArrayList<>();
    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    @FXML
    private Button btnOkayContacts;
   
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
    
}
