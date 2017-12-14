import ch07.trees.BinarySearchTree;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SearchDialogController {
    Iterator<String> iterator;
    Business business = new Business();

    private Stage dialogStage;
    private boolean okClicked = false;

    private MainApp mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField filterTextField;

    @FXML
    private TableView<Business> businessSearchTable;

    @FXML
    private TableColumn<Business, String> businessNameColumn;

    @FXML
    private TableColumn<Business, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label businessNameLabel;

    @FXML
    private Label websiteLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phoneNumberLabel;
    FilteredList<Business> filterData = new FilteredList<>(mainApp.getBusinessData(), p ->true);


    @FXML
    private void initialize() {
        businessNameColumn.setCellValueFactory(cellData -> cellData.getValue().businessNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showBusinessDetail(null);
        businessSearchTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBusinessDetail(newValue));


        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(business -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (business.getBusinessName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (business.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Business> sortedData = new SortedList<>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(businessSearchTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        businessSearchTable.setItems(sortedData);

    }
    private void showBusinessDetail(Business business){
        if (business != null){
            firstNameLabel.setText(business.getFirstName());
            lastNameLabel.setText(business.getLastName());
            addressLabel.setText(business.getAddresss());
            websiteLabel.setText(business.getWebsiteName());
            emailLabel.setText(business.getEmail());
            businessNameLabel.setText(business.getBusinessName());
            phoneNumberLabel.setText(business.getPhoneNumber());
        }else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            addressLabel.setText("");
            businessNameLabel.setText("");
            websiteLabel.setText("");
            emailLabel.setText("");
            phoneNumberLabel.setText("");
        }
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        businessSearchTable.setItems(mainApp.getBusinessData());
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked(){
        return okClicked;
    }

}
