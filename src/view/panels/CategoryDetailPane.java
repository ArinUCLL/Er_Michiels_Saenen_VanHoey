package view.panels;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.domain.Category;

public class CategoryDetailPane extends GridPane {
	private Button btnOK, btnCancel;
	private TextField titleField, descriptionField;
	private ComboBox categoryField;
	private Controller controller = Controller.getInstance();

	public CategoryDetailPane() {
		this.setPrefHeight(150);
		this.setPrefWidth(300);
		
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setVgap(5);
		this.setHgap(5);

		this.add(new Label("Title:"), 0, 0, 1, 1);
		titleField = new TextField();
		this.add(titleField, 1, 0, 1, 1);

		this.add(new Label("Description:"), 0, 1, 1, 1);
		descriptionField = new TextField();
		this.add(descriptionField, 1, 1, 1, 1);

		this.add(new Label("Main Category:"), 0, 2, 1, 1);
		categoryField = new ComboBox<>(controller.getCategoryNames());
		this.add(categoryField, 1, 2, 1, 1);

		btnCancel = new Button("Cancel");
		this.add(btnCancel, 0, 3, 1, 1);
		setCancelAction(new cancelAddCategoryHandler());
		btnOK = new Button("Save");
		setSaveAction(new addCategoryHandler());
		btnOK.isDefaultButton();
		this.add(btnOK, 1, 3, 1, 1);
	}

	public void setSaveAction(EventHandler<ActionEvent> saveAction) {
		btnOK.setOnAction(saveAction);
	}

	public void setCancelAction(EventHandler<ActionEvent> cancelAction) {
		btnCancel.setOnAction(cancelAction);
	}

	class addCategoryHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			String title = titleField.getText();
			String description = descriptionField.getText();
			controller.addCategory(new Category(title, description));
			titleField.clear();
			descriptionField.clear();
			Stage stage = (Stage) btnOK.getScene().getWindow();
			stage.close();
		}
	}

	class cancelAddCategoryHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e){
			titleField.clear();
			descriptionField.clear();
			Stage stage = (Stage) btnCancel.getScene().getWindow();
			stage.close();
		}
	}
}
