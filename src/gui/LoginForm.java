package gui;



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import userdatabase.DataBase;
import userdatabase.LoginDetails;

public class LoginForm extends Application {

	
	private TextField accountNumber;
	private PasswordField pinNumber;
	private Button loginButton;
	private Button signUpButton;

	private Label infoLabel;

	DataBase db = new DataBase();

	public void start(Stage stage) {
		
		accountNumber = new TextField();
		pinNumber = new PasswordField();
		infoLabel = new Label("");

		loginButton = new Button("Login");
		
		
		loginButton.setOnAction(e -> {
			try {
			int accountN = Integer.parseInt(accountNumber.getText());
			int pinN = Integer.parseInt(pinNumber.getText());
			LoginDetails login = new LoginDetails(accountN, pinN);
		
			if(db.login(login)) {
				DashBoard atm = new DashBoard();
				atm.showDashBoard(stage);
		}
			
			else {
				
				infoLabel.setText("Invalid account/pin number");
				infoLabel.setTextFill(Color.RED);
			}
		
		}
		catch(Exception ex) {
			infoLabel.setText("Invalid account/pin number");
			infoLabel.setTextFill(Color.RED);
		}
		
			});
	

		signUpButton = new Button("Create Account");
		signUpButton.setOnAction(e -> {
			SignUpForm signForm = new SignUpForm();
			signForm.showSignUpForm(stage);
			stage.close();
		});

		GridPane pane = new GridPane();

		pane.addRow(2, new Label("Account Number >> "), accountNumber);
		pane.addRow(2, new Label("Pin Number >> "), pinNumber);
		pane.add(loginButton, 1, 3);
		pane.add(signUpButton, 3, 3);
		pane.add(infoLabel, 1, 4);
		pane.setAlignment(Pos.CENTER);

		pane.setVgap(10);
		pane.setHgap(10);

		Scene scene = new Scene(pane, 600, 300);
		stage.setScene(scene);
		stage.show();

	}

}
