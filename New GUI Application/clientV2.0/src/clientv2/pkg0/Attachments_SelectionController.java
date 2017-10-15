/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import static clientv2.pkg0.Main_InterfaceController.imageString;
import static clientv2.pkg0.Main_InterfaceController.videoString;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.commons.codec.binary.Base64;

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
    @FXML
    private JFXTextField messageToSend;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleImageAction(MouseEvent event) throws FileNotFoundException, IOException {
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
            messageToSend.appendText(imageString);
        }catch(NullPointerException e)
        {
            System.out.print(e);
        }
    }

    @FXML
    private void handleVideoAction(MouseEvent event) throws FileNotFoundException, IOException {
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
            videoString = Base64.encodeBase64String(byteArray);

            System.out.print(videoString);
        }catch(NullPointerException e)
        {
            System.out.print(e);
        }
    }
    
}
