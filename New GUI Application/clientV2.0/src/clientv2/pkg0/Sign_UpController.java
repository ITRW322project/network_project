/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class Sign_UpController implements Initializable {
    @FXML private JFXTextField txtEmail;
    @FXML private JFXTextField txtPassword;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private Button btnSignUp; 
    @FXML
    private void buttonSignUpEvent(ActionEvent event) throws IOException{
        EmailController eC = new EmailController();
        eC.Sign_Up("besterjennifer5@gmail.com"  /*txtEmail.getText()*/, "skaapbouter" /*txtPassword.getText()*/);
        
        
        ((Node)(event.getSource())).getScene().getWindow().hide();  
        eC.i = "signed_In";
    }
}
