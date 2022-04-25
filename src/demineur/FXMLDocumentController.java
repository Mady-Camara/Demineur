package demineur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Mady
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
        Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();
        app.close();
    }
    
    @FXML
    private void handleButtonAction2(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDebutant.fxml"));
        Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();
        app.close();
        
    }
    
    @FXML
    private void handleButtonAction3(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLExpert.fxml"));
        Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();
        app.close();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }
    

    
}
