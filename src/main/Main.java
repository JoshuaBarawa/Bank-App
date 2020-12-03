package main;
import gui.LoginForm;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main  extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		LoginForm loginform = new LoginForm();
		loginform.start(stage);
	}

}
