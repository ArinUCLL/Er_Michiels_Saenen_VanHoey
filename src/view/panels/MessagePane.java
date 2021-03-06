package view.panels;

import java.io.IOException;
import java.util.Observer;

import controller.Controller;
import handler.processAnswerHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessagePane extends GridPane {
	private Button testButton;
	private Controller controller = Controller.getInstance();
	private Text l;
	private static MessagePane single_instance;

	public MessagePane() throws IOException {
	    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

		testButton = new Button("Evaluate");
		l = new Text();
		this.showEvaluation();
		add(l, 0,0,1,1);
		add(testButton, 0,1,1,1);
		setHalignment(testButton, HPos.CENTER);
	}

	public static MessagePane getInstance() throws IOException{
		if(single_instance == null){
			synchronized (MessagePane.class){
				if(single_instance == null){
					single_instance = new MessagePane();
				}
			}
		}
		return single_instance;
	}

	public void setStartAction(EventHandler<ActionEvent> startAction){
		testButton.setOnAction(startAction);
	}


	public void showEvaluation() throws IOException {
		if (controller.getEvaluation().getPropertyValue("test").equals("false")) {
			l.setText("You never did this Test!");
		}
		if (controller.getEvaluation().getPropertyValue("test").equals("true")) {
			l.setText("You already did this Test!");
		}
		System.out.println(controller.getQuestions().size() + " - " + controller.getQuestionNumber());
		if (controller.getQuestions().size() > 0 && controller.getQuestionNumber() == controller.getQuestions().size()) {
			System.out.println("halloooo");
			if (controller.getEvaluation().getPropertyValue("evaluation.mode").equals("score")) {
				l.setText(controller.getScoreFeedback().toString());
				controller.getEvaluation().setProperty("evaluation.mode", "score");
			}
			if (controller.getEvaluation().getPropertyValue("evaluation.mode").equals("feedback")) {
				l.setText(controller.getScoreFeedback().toStringFeedback());
				controller.getEvaluation().setProperty("evaluation.mode", "feedback");

			}

		}
	}

}
