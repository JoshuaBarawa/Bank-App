package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import userdatabase.DataBase;
import userdatabase.User;

public class SignUpForm {

	private TextField userNames;
	private ToggleGroup genderGroup;
	private RadioButton maleButton;
	private RadioButton femaleButton;
	private Button finishButton;
	private TextField idNumber;
	private TextField accountNumber;
	private PasswordField pinNumber;
	private TextField accountBalance;
	private Label infoLabel;

	private Button backButton;
	Stage stage = new Stage();
	
	DataBase db = new DataBase();
	LoginForm login = new LoginForm();

	public void showSignUpForm(Stage window) {

		window = stage;

		userNames = new TextField();
		finishButton = new Button("Submit");
		accountNumber = new TextField();
		pinNumber = new PasswordField();
		accountBalance = new TextField("0");
		accountBalance.setEditable(false);
		backButton = new Button("Back");

		infoLabel = new Label("Enter your Details");

		idNumber = new TextField();

		genderGroup = new ToggleGroup();

		maleButton = new RadioButton("Male");
		maleButton.setToggleGroup(genderGroup);
		maleButton.setSelected(true);
		maleButton.setUserData("Male");

		femaleButton = new RadioButton("Female");
		femaleButton.setToggleGroup(genderGroup);
		femaleButton.setUserData("Female");

		GridPane pane = new GridPane();
		pane.addRow(1, new Label("Full Names >> "), userNames);
		pane.addRow(2, new Label("Gender >> "), maleButton);
		pane.addRow(2, femaleButton);
		pane.addRow(3, new Label("ID Number >> "), idNumber);
		pane.addRow(4, new Label("Account Number >> " + "(Your Choice)"), accountNumber);
		pane.addRow(5, new Label("Pin >> "), pinNumber);
		pane.addRow(6, new Label("Account Balance >> "), accountBalance);
		pane.add(backButton, 0, 0);
		pane.add(infoLabel, 1, 0);
		pane.add(finishButton, 1, 9);
		pane.setVgap(9);
		pane.setHgap(9);
		pane.setAlignment(Pos.CENTER);

		finishButton.setOnAction(this::doAction);
		backButton.setOnAction(this::doAction);

		Scene scene = new Scene(pane, 600, 300);
		stage.setScene(scene);
		stage.show();

	}

	public void doAction(ActionEvent e) {
		Object source = e.getSource();
		if (source == finishButton) {
			try {
				String names = userNames.getText();
				String gender = (String) genderGroup.getSelectedToggle().getUserData();
				int id = Integer.parseInt(idNumber.getText());
				int customerNumber = Integer.parseInt(accountNumber.getText());
				int pin = Integer.parseInt(pinNumber.getText());
				int balance = Integer.parseInt(accountBalance.getText());
				
				User user = new User();
	
				user.setUserName(names);
				user.setGender(gender);
				user.setIdNumber(id);
				user.setAccountNumber(customerNumber);
				user.setPinNumber(pin);
				user.setAccountBalance(balance);
				
			  if(db.saveToDataBase(user) == true) {
				  login.start(stage);
			  }
			  else {
				  infoLabel.setText("Account Number already exists");
				  infoLabel.setTextFill(Color.RED);
			  }
			}
			catch (Exception ex) {
				infoLabel.setText("Invalid user details");
				infoLabel.setTextFill(Color.RED);
				System.out.println(ex.getMessage());
			}
		} else if (source == backButton) {
			LoginForm loginform = new LoginForm();
			loginform.start(stage);

		}
		
	}
	
	
	
	
}
