/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author beste
 */
public class Attachments_SelectionController implements Initializable {

    @FXML
    private ImageView imgImage;
    @FXML
    private ImageView imgVideo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleImageAction(MouseEvent event) {
        //when image is selected import image
    }

    @FXML
    private void handleVideoAction(MouseEvent event) {
        //when video is selected import video
    }
    
}
