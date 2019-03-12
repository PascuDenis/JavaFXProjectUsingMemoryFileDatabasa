package gui;

import controller.Controller;
import controller.Validators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.BerufsPosition;
import model.Mitarbeiter;
import repository.MitarbeiterDatabaseRepository;
import repository.MitarbeiterFileRepository;
import repository.MitarbeiterRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIMainpage implements Initializable {

    public TextField idTextField;
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField addressTextField;
    public TextField levelTextField;
    public TextField paymentTextField;
    public Button addButton;

    @FXML
    public ComboBox<String> positionComboBox;
    @FXML
    public ComboBox<Integer> dailyHoursComboBox;
    @FXML
    public RadioButton saveInMemoryRadioButton;
    @FXML
    public RadioButton saveInFileRadioButton;
    @FXML
    public ToggleGroup toggleGroup;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    @FXML
    public ComboBox salaryPositionComboBox;
    @FXML
    public ComboBox salaryLevelComboBox;
    @FXML
    public Button salaryEveryoneButton;
    @FXML
    public RadioButton showMemoryListRadioButton;
    @FXML
    public RadioButton showFileListRadioButton;
    @FXML
    public ToggleGroup showData;
    @FXML
    public TextArea showFileTextArea;
    @FXML
    public RadioButton showDatabaseListRadioButton;
    @FXML
    public RadioButton saveInDatabaseRadioButton;
    @FXML
    public Button refreshTableButton;

    @FXML
    TableColumn<Mitarbeiter, Long> idColumn;

    @FXML
    TableColumn<Mitarbeiter, String> lastNameColumn, firstNameColumn, addressColumn;

    @FXML
    TableColumn<Mitarbeiter, Integer> levelColumn, dailyHourColumn;

    @FXML
    TableColumn<Mitarbeiter, Double> paymentColumn;

    @FXML
    TableColumn<Mitarbeiter, BerufsPosition> positionColumn;

    @FXML
    TableView mainTableView;


    private Controller mitarbeiterController1 = new Controller(new MitarbeiterRepository(new ArrayList<>()));
    private Controller mitarbeiterController2 = new Controller(new MitarbeiterFileRepository(new ArrayList<>(), "C:\\Users\\Denis\\IdeaProjects\\FXGLGames-master\\_1DBtest\\src\\main\\java\\persons.txt"));
    private Controller mitarbeiterController3 = new Controller(new MitarbeiterDatabaseRepository(new ArrayList<>()));

    public GUIMainpage() throws Exception {
    }


    public void showMitarbeiterList(List<Mitarbeiter> mitarbeiterList) {
        Group group = new Group();

        final ObservableList<Mitarbeiter> oList = FXCollections.observableArrayList(
                mitarbeiterList);

        idColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Long>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("vorName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("nachName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, String>("adresse"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, BerufsPosition>("berufsPosition"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Integer>("erfahrungsNiveau"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Double>("stundenLohn"));
        dailyHourColumn.setCellValueFactory(new PropertyValueFactory<Mitarbeiter, Integer>("stundenProTag"));

        mainTableView.setItems(oList);
        mainTableView.refresh();

    }

    public void printMitarbeiterList() {
        if (showMemoryListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController1.findAll());
            System.out.println(mitarbeiterController1.findAll());
            mainTableView.refresh();
        } else if (showFileListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController2.findAll());
            System.out.println(mitarbeiterController2.findAll());
            mainTableView.refresh();
        } else if (showDatabaseListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController3.findAll());
            System.out.println(mitarbeiterController3.findAll());
            mainTableView.refresh();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        printMitarbeiterList();
        positionComboBoxSelect();
        dailyHourComboSelect();
        salaryPositionComboBox();
        salaryLevelComboBox();
        saveInFileRadioButton.setSelected(true);
//        saveInMemoryRadioButton.setSelected(true);
//        showMemoryListRadioButton.setSelected(true);
    }

    public void setPositionComboBox() {

    }

    public ComboBox<String> getPositionTextField() {
        return positionComboBox;
    }

    public void setPositionTextField(ComboBox<String> positionComboBox) {
        this.positionComboBox = positionComboBox;
    }


    public ComboBox<Integer> getDailyHoursComboBox() {
        return dailyHoursComboBox;
    }

    public void setDailyHoursComboBox(ComboBox<Integer> dailyHoursComboBox) {
        this.dailyHoursComboBox = dailyHoursComboBox;
    }

    @FXML
    public void positionComboBoxSelect() {

        positionComboBox.getItems().addAll(
                "Tester",
                "Entwickler",
                "TeamLeader",
                "ProjectManager");
        positionComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void dailyHourComboSelect() {
        dailyHoursComboBox.getItems().addAll(
                4, 5, 6, 7, 8, 9, 10, 11, 12);
        dailyHoursComboBox.getSelectionModel().select(0);
    }


    @FXML
    private void salaryPositionComboBox() {
        salaryPositionComboBox.getItems().addAll(
                "Tester",
                "Entwickler",
                "TeamLeader",
                "ProjectManager");
    }

    @FXML
    private void salaryLevelComboBox() {
        salaryLevelComboBox.getItems().addAll(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    private void clearTextFields() {
        idTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        positionComboBox.getSelectionModel().select(0);
        levelTextField.setText("");
        paymentTextField.setText("");
        dailyHoursComboBox.getSelectionModel().select(0);

    }

    public void addButton(ActionEvent actionEvent) throws Exception {
        if (validateFields() &&
                validate("[0-9][1-9]*", idTextField.getText(), "a valid ID!") &&
                validate("[A-Z][a-z]*", firstNameTextField.getText(), "a valid name!") &&
                validate("[A-Z][a-z]*", lastNameTextField.getText(), "a valid name!") &&
                validate("[A-Z][a-z]*", addressTextField.getText(), "a valid address") &&
                validate("[1-9]|10", levelTextField.getText(), "a valid level! (1-9)") &&
                validate("^[0-5]?[0-9]$", paymentTextField.getText(), "a valid payment! (1-59)")) {
            Mitarbeiter readedMitarbeiter = new Mitarbeiter(
                    Long.valueOf(idTextField.getText()),
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    addressTextField.getText(),
                    BerufsPosition.valueOf(positionComboBox.getSelectionModel().getSelectedItem()),
                    Integer.valueOf(levelTextField.getText()),
                    Double.valueOf(paymentTextField.getText()),
                    dailyHoursComboBox.getSelectionModel().getSelectedItem());
            try {
                if (saveInMemoryRadioButton.isSelected()) {
                    mitarbeiterController1.chooseRepository(1);
                    if (!Validators.idExistsValidation(Long.valueOf(idTextField.getText()), (List<Mitarbeiter>) mitarbeiterController1.findAll())){
                        System.out.println(mitarbeiterController1.findAll());
                        mitarbeiterController1.saveMitarbeiter(readedMitarbeiter);
                        mainTableView.getItems().add(readedMitarbeiter);
                    }
                    else throw new Exception();
                    System.out.println(mitarbeiterController1.findAll());
                } else if (saveInFileRadioButton.isSelected()) {
                    mitarbeiterController2.chooseRepository(2);
                    if (!Validators.idExistsValidation(Long.valueOf(idTextField.getText()), (List<Mitarbeiter>) mitarbeiterController2.findAll())){
                        mitarbeiterController2.saveMitarbeiter(readedMitarbeiter);
                        mainTableView.getItems().add(readedMitarbeiter);
                        System.out.println(mitarbeiterController2.findAll());
                    }
                    else throw new Exception();
                } else if (saveInDatabaseRadioButton.isSelected()) {
                    mitarbeiterController3.chooseRepository(3);
                    if (!Validators.idExistsValidation(Long.valueOf(idTextField.getText()), (List<Mitarbeiter>) mitarbeiterController3.findAll())){
                        mitarbeiterController3.saveMitarbeiter(readedMitarbeiter);
                        mainTableView.getItems().add(readedMitarbeiter);
                        System.out.println(mitarbeiterController3.findAll());
                    }
                    else throw new Exception();
                } else System.out.println("bai");
                clearTextFields();
            } catch (Exception e) {
                validate("", idTextField.getText(), "another id!~ ");
            }
        }
    }

    public void deleteButton(ActionEvent event) throws Exception {
        try {
            if (validateNumber()) {
                if (saveInMemoryRadioButton.isSelected()) {
                    Long readedId = Long.valueOf(idTextField.getText());
                    if (Validators.idExistsValidation(readedId, (List<Mitarbeiter>) mitarbeiterController1.findAll())){
                        Mitarbeiter temp = mitarbeiterController1.findMitarbeiterById(readedId);
                        mitarbeiterController1.chooseRepository(1);
                        mitarbeiterController1.deleteMitarbeiter(readedId);
                        mainTableView.getItems().remove(temp);
                        System.out.println(mitarbeiterController1.findAll());
                    }
                    else throw new Exception();
                } else if (saveInFileRadioButton.isSelected()) {
                    Long readedId = Long.valueOf(idTextField.getText());
                    if (Validators.idExistsValidation(readedId, (List<Mitarbeiter>) mitarbeiterController2.findAll())) {
                        Mitarbeiter temp = mitarbeiterController2.findMitarbeiterById(readedId);

                        mitarbeiterController2.chooseRepository(2);
                        mitarbeiterController2.deleteMitarbeiter(readedId);
                        mainTableView.getItems().remove(temp);
                        System.out.println(mitarbeiterController2.findAll());
                    }
                    else throw new Exception();
                } else if (saveInDatabaseRadioButton.isSelected()) {
                    Long readedId = Long.valueOf(idTextField.getText());
                    if (Validators.idExistsValidation(readedId, (List<Mitarbeiter>) mitarbeiterController3.findAll())){
                        Mitarbeiter temp = mitarbeiterController3.findMitarbeiterById(readedId);
                        mitarbeiterController3.chooseRepository(3);
                        mitarbeiterController3.deleteMitarbeiter(readedId);
                        mainTableView.getItems().remove(temp);
                        System.out.println(mitarbeiterController3.findAll());
                    }
                    else throw new Exception();
                } else System.out.println("bai");
            }
        } catch (Exception e) {
            validate("", idTextField.getText(), "another value!");
        }
    }

    public void updateButton(ActionEvent event) throws Exception {
        if (validateFields() &&
                validate("[0-9][1-9]*", idTextField.getText(), "a valid ID!") &&
                validate("[A-Z][a-z]*", firstNameTextField.getText(), "a valid name!") &&
                validate("[A-Z][a-z]*", lastNameTextField.getText(), "a valid name!") &&
                validate("[A-Z][a-z]*", addressTextField.getText(), "a valid address") &&
                validate("[1-9]|10", levelTextField.getText(), "a valid level! (1-10)") &&
                validate("^[0-5]?[0-9]$", paymentTextField.getText(), "a valid payment! (1-59)")) {

            Mitarbeiter readedMitarbeiter = new Mitarbeiter(
                    Long.valueOf(idTextField.getText()),
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    addressTextField.getText(),
                    BerufsPosition.valueOf(positionComboBox.getSelectionModel().getSelectedItem()),
                    Integer.valueOf(levelTextField.getText()),
                    Double.valueOf(paymentTextField.getText()),
                    dailyHoursComboBox.getSelectionModel().getSelectedItem());
            try {
                if (saveInMemoryRadioButton.isSelected()) {
                    mitarbeiterController1.chooseRepository(1);
                    if (!((List<Mitarbeiter>) mitarbeiterController1.findAll()).contains(mitarbeiterController1.findMitarbeiterById(Long.valueOf(idTextField.getText())))) {
                        throw new Exception("There is not id");
                    }
                    //Validators.idNotExistsValidation(Long.valueOf(idTextField.getText()), (List<Mitarbeiter>) mitarbeiterController1.findAll());
                    mitarbeiterController1.updateMitarbeiter(readedMitarbeiter);
                    System.out.println(mitarbeiterController1.findAll());

                } else if (saveInFileRadioButton.isSelected()) {
                    mitarbeiterController2.chooseRepository(2);
                    if (!((List<Mitarbeiter>) mitarbeiterController2.findAll()).contains(mitarbeiterController2.findMitarbeiterById(Long.valueOf(idTextField.getText())))) {
                        throw new Exception("There is not id");
                    }
                    //Validators.idNotExistsValidation(Long.valueOf(idTextField.getText()), (List<Mitarbeiter>) mitarbeiterController2.findAll());
                    mitarbeiterController2.updateMitarbeiter(readedMitarbeiter);
                    System.out.println(mitarbeiterController2.findAll());
                    //mainTableView.getItems().
                } else if (saveInDatabaseRadioButton.isSelected()) {
                    mitarbeiterController3.chooseRepository(3);
                    System.out.println("da1");
                    if (!((List<Mitarbeiter>) mitarbeiterController3.findAll()).contains(mitarbeiterController3.findMitarbeiterById(Long.valueOf(idTextField.getText())))) {
                        throw new Exception("There is no id");
                    }
                    mitarbeiterController3.updateMitarbeiter(readedMitarbeiter);
                    System.out.println(mitarbeiterController3.findAll());
                }
            } catch (Exception e) {
                validate("", idTextField.getText(), "another id value!~");
            }
        }
    }

    public void everyoneButton(ActionEvent event) throws Exception {
        showFileTextArea.clear();
        if (saveInMemoryRadioButton.isSelected()) {
            mitarbeiterController1.calculateSalaryForAllWorkers();
        } else if (saveInFileRadioButton.isSelected()) {
            mitarbeiterController2.calculateSalaryForAllWorkers();
        } else if (saveInDatabaseRadioButton.isSelected()) {
            mitarbeiterController3.calculateSalaryForAllWorkers();
        }
        showText();
    }

    public void levelButton(ActionEvent event) throws Exception {
        String output = salaryLevelComboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(output);
        if (saveInMemoryRadioButton.isSelected()) {
            mitarbeiterController1.calculateSalaryForAllExperianceLevels((Integer) salaryLevelComboBox.getSelectionModel().getSelectedItem());
        } else if (saveInFileRadioButton.isSelected()) {
            mitarbeiterController2.calculateSalaryForAllExperianceLevels((Integer) salaryLevelComboBox.getSelectionModel().getSelectedItem());
        } else if (saveInDatabaseRadioButton.isSelected()) {
            mitarbeiterController3.calculateSalaryForAllExperianceLevels((Integer) salaryLevelComboBox.getSelectionModel().getSelectedItem());
        }
        showText();
    }

    public void positionButton() throws Exception {
        String output = salaryPositionComboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(output);
        if (saveInMemoryRadioButton.isSelected()) {
            mitarbeiterController1.calculateSalaryBerufsPosition(BerufsPosition.valueOf((String) salaryPositionComboBox.getSelectionModel().getSelectedItem()));
        } else if (saveInFileRadioButton.isSelected()) {
            mitarbeiterController2.calculateSalaryBerufsPosition(BerufsPosition.valueOf((String) salaryPositionComboBox.getSelectionModel().getSelectedItem()));
        } else if (saveInDatabaseRadioButton.isSelected()) {
            mitarbeiterController3.calculateSalaryBerufsPosition(BerufsPosition.valueOf((String) salaryPositionComboBox.getSelectionModel().getSelectedItem()));
        }
        showText();
    }


    public void positionSelectionChanged(ActionEvent event) {

    }

    public void dailyHoursSelectionChangeed(ActionEvent event) {
    }

    public void levelSelectionChanged(ActionEvent event) {
    }

    private boolean validateFields() {
        if (idTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() ||
                firstNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                levelTextField.getText().isEmpty() || paymentTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Data Into The Fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateNumber() {
        Pattern p = Pattern.compile("([A-Za-z.][ ]*[0-9]*)");
        Matcher m = p.matcher(idTextField.getText());
        if (m.find() && m.group().equals(idTextField.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number!");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validate(String regex, String matcher, String exitMessage) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(matcher);
        if (m.find() && m.group().equals(matcher)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText("Please enter " + exitMessage);
            alert.showAndWait();
            return false;
        }
    }

    public void showText() {
        showFileTextArea.clear();
        try {
            Scanner s = new Scanner(new File("C:\\Users\\Denis\\IdeaProjects\\FXGLGames-master\\_1DBtest\\src\\main\\java\\salaries.txt"));
            while (s.hasNext()) {
                String line = s.nextLine();
                showFileTextArea.appendText(line + "\n");
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }


    public void refrashTable(ActionEvent event) {
        if (showMemoryListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController1.findAll());
            System.out.println(mitarbeiterController1.findAll());
            mainTableView.refresh();
        } else if (showFileListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController2.findAll());
            System.out.println(mitarbeiterController2.findAll());
            mainTableView.refresh();
        } else if (showDatabaseListRadioButton.isSelected()) {
            showMitarbeiterList((List<Mitarbeiter>) mitarbeiterController3.findAll());
            System.out.println(mitarbeiterController3.findAll());
            mainTableView.refresh();
        }
        System.out.println("Jaaaaas");
//        ObservableList items = mainTableView.getItems();
//        mainTableView.getItems().removeAll();
//        mainTableView.setItems(items);
//        mainTableView.refresh();
    }
}
