package gui;


import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import userdatabase.DataBase;
import userdatabase.User;

public class DashBoard {

	private Button checkBalanceButton;
	private Button withDrawButton;
	private Button depositButton;
	private Button checkProfileButton;
	private Button saveButton;

	private Button exitButton;

	private Stage window;
	private DataBase db = new DataBase();

	public void showDashBoard(Stage stage) {

		window = stage;


		checkBalanceButton = new Button("Balance", new ImageView("/images/icons8-wallet-64.png"));
		checkBalanceButton.setPrefSize(150, 70);
		checkBalanceButton.setOnAction(this::doAction);

		depositButton = new Button("Deposit", new ImageView("/images/icons8-deposit-64.png"));
		depositButton.setPrefSize(150, 70);
		depositButton.setOnAction(this::doAction);

		withDrawButton = new Button("WithDraw", new ImageView("/images/icons8-withdrawal-64.png"));
		withDrawButton.setPrefSize(150, 70);
		withDrawButton.setOnAction(this::doAction);

		checkProfileButton = new Button("Profile", new ImageView("/images/icons8-profiles-40.png"));
		checkProfileButton.setPrefSize(150, 70);
		checkProfileButton.setOnAction(this::doAction);

		saveButton = new Button("Save");
		saveButton.setPrefSize(90, 30);
		saveButton.setOnAction(this::doAction);

		exitButton = new Button("Exit");
		exitButton.setPrefSize(90, 30);
		exitButton.setOnAction(this::doAction);

		GridPane pane = new GridPane();
		pane.add(checkBalanceButton, 1, 1);
		pane.add(depositButton, 1, 2);
		pane.add(withDrawButton, 3, 1);
		pane.add(checkProfileButton, 3, 2);
		pane.add(saveButton, 3, 4);
		pane.add(exitButton, 1, 4);

		pane.setPrefSize(400, 250);
		pane.setVgap(40);
		pane.setHgap(20);
		pane.setAlignment(Pos.TOP_CENTER);

		BorderPane root = new BorderPane(pane);

		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("ATM SYSTEM");
		stage.show();

	}

	public void doAction(ActionEvent e) {
		Object source = e.getSource();

		if (source == checkBalanceButton) {

			TextField accountNumber = new TextField();
			accountNumber.setFont(Font.font(15));
			
			TextField accountBalance = new TextField();
			accountBalance.setFont(Font.font(15));
			accountBalance.setEditable(false);

			Button loadButton = new Button("CHECK");
			loadButton.setOnAction(ex ->{
				
				try {
					int accoutN = Integer.parseInt(accountNumber.getText());
					User user = db.selectUser(accoutN);
		
					accountBalance.appendText(Integer.toString(user.getAccountBalance()));
					
					loadButton.setDisable(true);
					accountNumber.setEditable(false);
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			GridPane root = new GridPane();
			root.addRow(1, new Label("Account Number >>"), accountNumber);
			root.addRow(2, new Label("Account Balance >> "), accountBalance);
			root.add(loadButton, 2, 1);
			root.setVgap(10);
			root.setHgap(10);
			root.setAlignment(Pos.CENTER);

			Scene scene1 = new Scene(root, 400, 200);
			Stage stage = new Stage();
			stage.setScene(scene1);
			stage.showAndWait();

		}

		else if (source == withDrawButton) {

			TextField withdrawInput = new TextField();
			TextField accountNumber = new TextField();
			Stage stage = new Stage();
			Button withdraw = new Button("WithDraw", new ImageView("/images/icons8-money-40.png"));
			withdraw.setPrefSize(150, 30);

			withdraw.setOnAction(a -> {
				try {
					int accountN = Integer.parseInt(accountNumber.getText());
					User user = db.selectUser(accountN);
					int withDrawamount = Integer.parseInt(withdrawInput.getText());

					if (withDrawamount <= user.getAccountBalance()) {

						Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
								"Confirm Your WithDraw of " + withDrawamount);
						Optional<ButtonType> responce = alert.showAndWait();

						if (responce.get() == ButtonType.OK) {
							user.calcCheckingWithDraw(withDrawamount);
							db.updateAccount(user);
							stage.close();
						}

						else {
							stage.close();
						}

					}

					else if (withDrawamount > user.getAccountBalance()) {
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have insufficent Amount to Withdraw "
								+ "\n" + "Balance Account: " + user.getAccountBalance());
						alert.showAndWait();
						stage.close();

					}

				} catch (Exception ex) {
					System.out.println(ex.toString());
				}

			});

			GridPane pane2 = new GridPane();
			pane2.addRow(1, new Label("Enter Account NO >>"), accountNumber);
			pane2.addRow(2, new Label("Enter Amount >>"), withdrawInput);
			pane2.add(withdraw, 1, 3);
			pane2.setVgap(10);
			pane2.setAlignment(Pos.CENTER);

			Scene scene3 = new Scene(pane2, 300, 200);
			stage.setScene(scene3);
			stage.showAndWait();
		} else if (source == depositButton) {

			Stage stage = new Stage();
			TextField depositInput = new TextField();
			TextField accountNumber = new TextField();

			Button deposit = new Button("Deposit", new ImageView("/images/icons8-money-box-64.png"));
			deposit.setPrefSize(150, 30);

			deposit.setOnAction(a -> {
				try {
					int depositamount = Integer.parseInt(depositInput.getText());
					int accountN = Integer.parseInt(accountNumber.getText());
					User user = db.selectUser(accountN);

					if (depositamount > 0) {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
								"Confirm Your Deposit of " + depositamount);
						Optional<ButtonType> responce = alert.showAndWait();

						if (responce.get() == ButtonType.OK) {
							user.calcDepositCash(depositamount);
						    db.updateAccount(user);
							stage.close();
						}

						else {
							stage.close();
						}
					}

					else {
						Alert alert = new Alert(Alert.AlertType.ERROR, "Amount cannot be NEGATIVE or ZERO");
						alert.showAndWait();
					}
				}

				catch (Exception ex) {
					System.out.println(ex.toString());
				}
			});

			GridPane pane = new GridPane();
			pane.addRow(1, new Label("Enter Account NO >>"), accountNumber);
			pane.addRow(2, new Label("Enter Amount >>"), depositInput);
			pane.add(deposit, 1, 3);
			pane.setVgap(10);
			pane.setAlignment(Pos.CENTER);

			Scene scene2 = new Scene(pane, 300, 200);
			stage.setScene(scene2);
			stage.showAndWait();

		}

		else if (source == checkProfileButton) {
			TextField nameField = new TextField();
			nameField.setFont(Font.font(15));
			nameField.setEditable(false);

			TextField genderField = new TextField();
			genderField.setFont(Font.font(15));
			genderField.setEditable(false);

			TextField IDField = new TextField();
			IDField.setFont(Font.font(15));
			IDField.setEditable(false);

			TextField accountNumber = new TextField();
			accountNumber.setFont(Font.font(15));

			TextField pinField = new TextField();
			pinField.setFont(Font.font(15));
			pinField.setEditable(false);
			
			Button loadButton = new Button("LOAD INFO");
			loadButton.setOnAction(ex ->{

				try {
					int accountN = Integer.parseInt(accountNumber.getText());
					User user = db.selectUser(accountN);
					
					nameField.appendText(user.getUserName());
					genderField.appendText(user.getGender());
					IDField.appendText(Integer.toString(user.getIdNumber()));
					pinField.appendText(Integer.toString(user.getPinNumber()));
			      
					loadButton.setDisable(true);
					accountNumber.setEditable(false);
					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			

			GridPane root = new GridPane();
			root.addRow(1, new Label("Enter Account Number >> "), accountNumber);
			root.addRow(2, new Label("Full Names >>"), nameField);
			root.addRow(3, new Label("Gender >> "), genderField);
			root.addRow(4, new Label("ID NO >> "), IDField);
			root.addRow(5, new Label("Pin NO >> "), pinField);
			root.add(loadButton, 2, 1);
			root.setVgap(10);
			root.setHgap(10);
			root.setAlignment(Pos.CENTER);
			Scene scene4 = new Scene(root, 500, 400);
			Stage stage = new Stage();
			stage.setScene(scene4);
			stage.showAndWait();

		}

		else if (source == exitButton) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit?");
			Optional<ButtonType> responce = alert.showAndWait();
			if (responce.get() == ButtonType.OK) {
				window.close();
			}
		}

	}
}
