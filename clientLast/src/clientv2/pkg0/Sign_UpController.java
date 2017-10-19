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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class Sign_UpController implements Initializable {
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    
    @FXML
    private ImageView imgLogo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private Button btnSignUp; 
    
    @FXML
    private void buttonSignUpEvent(ActionEvent event) throws IOException{
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        
        EmailController eC = new EmailController();
        eC.email = email;
        eC.password = password;
        
        Stage stage = new Stage(); 
            Parent root = null;

            root = FXMLLoader.load(getClass().getResource("Email.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
