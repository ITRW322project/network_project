/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Simeon
 */
public class PlayerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private MediaView mediaView;
    
    private MediaPlayer mediaPlayer;
    private String filePath;
    
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seeSlider;
    
    @FXML
    public Button btnOpenFile; 
    
    @FXML
    public Button btnPlay;
    
    @FXML
    public Button btnPause;
    
    @FXML
    public Button btnStop;
    
    @FXML
    public Button btnSlower;
    
    @FXML
    public Button btnSlow;
    
    @FXML
    public Button btnFastForward;
    
    @FXML
    public Button btnFastFastForward;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }  
    
    public void exit(){
        System.exit(0);
    }
    
    @FXML
    public void onClickOpen(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)",".mp4");
            //fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            
            if(filePath != null)
            {
                Media media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty hight = mediaView.fitHeightProperty();
                    
                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));
                    slider.setValue(mediaPlayer.getVolume() * 100);
                    slider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                       mediaPlayer.setVolume(slider.getValue()/100);
                        }
                    });
                
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                       seeSlider.setValue(newValue.toSeconds());
                    }
                });
                
                seeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mediaPlayer.seek(Duration.seconds(seeSlider.getValue()));
                    }
                });
                
                mediaPlayer.play();
            }
    }
    
    @FXML
    private void onClickPlay(ActionEvent event) throws IOException{
        mediaPlayer.play();
    }
    
     @FXML
    private void onClickStop(ActionEvent event) throws IOException{
        mediaPlayer.stop();
    }
    
    @FXML
    private void onClickBtnPause(ActionEvent event) throws IOException{
        mediaPlayer.pause();
    }
    
    @FXML
    private void onClickFastForward(ActionEvent event) throws IOException{
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void onClickFastFastForward(ActionEvent event) throws IOException{
        mediaPlayer.setRate(2);
    }
    
    @FXML
    private void onClickBtnSlow(ActionEvent event) throws IOException{
        mediaPlayer.setRate(0.75);
    }
    
    @FXML
    private void onClickBtnSlower(ActionEvent event) throws IOException{
        mediaPlayer.setRate(0.5);
    }
    
}
