package view.panels;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextArea statementsArea;
	private TextField questionField, statementField, feedbackField;
	private Button btnAdd, btnRemove;
	private ComboBox categoryField;

	private Controller controller = Controller.getInstance();
	private ArrayList<String> statements = new ArrayList<String>();

	public QuestionDetailPane() {
		this.setPrefHeight(300);
		this.setPrefWidth(320);
		
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        
		add(new Label("Question: "), 0, 0, 1, 1);
		questionField = new TextField();
		add(questionField, 1, 0, 2, 1);
		
		add(new Label("Statement: "), 0, 1, 1, 1);
		statementField = new TextField();
		add(statementField, 1, 1, 2, 1);

		add(new Label("Statements: "), 0, 2, 1, 1);
		statementsArea = new TextArea();
		statementsArea.setPrefRowCount(5);
		statementsArea.setEditable(false);
		add(statementsArea, 1, 2, 2, 5);

		Pane addRemove = new HBox();
		btnAdd = new Button("add");
		btnAdd.setOnAction(new AddStatementListener());
		addRemove.getChildren().add(btnAdd);

		btnRemove = new Button("remove");
		btnRemove.setOnAction(new RemoveStatementListener());
		addRemove.getChildren().add(btnRemove);
		add(addRemove, 1, 8, 2, 1);

		add(new Label("Category: "), 0, 9, 1, 1);
		categoryField = new ComboBox(controller.getCategoryNames());
		add(categoryField, 1, 9, 2, 1);

		add(new Label("Feedback: "), 0, 10, 1, 1);
		feedbackField = new TextField();
		add(feedbackField, 1, 10, 2, 1);

		btnCancel = new Button("Cancel");
		btnCancel.setText("Cancel");
		add(btnCancel, 0, 11, 1, 1);

		btnOK = new Button("Save");
		btnOK.isDefaultButton();
		btnOK.setText("Save");
		add(btnOK, 1, 11, 2, 1);
		setSaveAction(new addQuestionHandler());
		
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

	class AddStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String statement = statementField.getText();
			statements.add(statement);
			statementsArea.appendText(statement + "\n");
			statementField.clear();
		}
	}

	class RemoveStatementListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			statementsArea.replaceSelection("");
			String test = statementsArea.getText().trim();
			statementsArea.clear();
			statementsArea.appendText(test + "\n");
		}
	}

	class addQuestionHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			String question = questionField.getText();
			String correctStatement = statements.get(0);
			ObservableList<CharSequence> statements = statementsArea.getParagraphs();
			String category = categoryField.getValue().toString();
			String feedback = feedbackField.getText();

			Question q = new Question(question, correctStatement, statements, category, feedback);
			controller.addQuestion(q);

			Stage stage = (Stage) btnOK.getScene().getWindow();
			stage.close();
		}
	}
}