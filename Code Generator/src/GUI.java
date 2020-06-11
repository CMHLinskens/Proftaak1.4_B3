import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;


public class GUI extends Application {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    DateTimeFormatter dtfT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DataController DC;
    private boolean isediting = false;


    @Override
    public void start(Stage primaryStage) throws Exception {

        DC = new DataController();
        Process backgroundProcess = new Process();
        backgroundProcess.setUpThread(DC);
        backgroundProcess.start();
        MQTTController.getInstance().connectToServer();
        StackPane stackPane = new StackPane();
        VBox base = new VBox();
        Label TitelLabel = new Label("Essteling's Story seekers: code Control");
        TitelLabel.setTextFill(Color.web("#0076a3"));
        TitelLabel.setFont(new Font("Arial", 25));
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = SettupCodeGrid();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        scrollPane.setContent(gridPane);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
        FileInputStream input = new FileInputStream("resources/background_image.jpg");
        Image image = new Image(input);
        ImageView background = new ImageView(image);

        Button ManualOverrideButton = new Button("Manual Override");
        ManualOverrideButton.setOnAction(event -> {
            if (!isediting) {
                ObservableList<Node> childrens = gridPane.getChildren();

                for (Node node : childrens) {
                    if (gridPane.getColumnIndex(node) == 2) {
                        TextField Codefield = (TextField) node;
                        Codefield.setEditable(!isediting);
                    }
                }
                isediting = !isediting;
                ManualOverrideButton.setText("Confirm");
            } else {
                ObservableList<Node> childrens = gridPane.getChildren();
                for (Node node : childrens) {
                    if (gridPane.getColumnIndex(node) == 2) {
                        TextField Codefield = (TextField) node;
                        String codeInput = Codefield.getText();
                        codeInput.trim();
                        Codefield.setEditable(!isediting);
                        int StoryID = gridPane.getRowIndex(node);
                        if (!codeInput.equals("")) {
                            DC.setCode(StoryID, codeInput);
                        } else {
                            Codefield.setText((DC.getCodeByID(StoryID)).getCode());
                        }
                    }
                }

                DC.saveCodes();
                isediting = !isediting;
                ManualOverrideButton.setText("Manual Override");
            }
        });

        Button toggleGenerateRandomlyButton = new Button();
        if (DC.isGeneratingRandomly()) {
            toggleGenerateRandomlyButton.setText("Toggle randomly generate: active");
        } else {
            toggleGenerateRandomlyButton.setText("Toggle randomly generate: inactive");
        }
        toggleGenerateRandomlyButton.setOnAction(event -> {

                    if (DC.isGeneratingRandomly()) {
                        toggleGenerateRandomlyButton.setText("Toggle randomly generate: inactive");
                    } else {
                        toggleGenerateRandomlyButton.setText("Toggle randomly generate: active");
                    }            DC.changeToggleGenerateRandomly();
                    DC.saveCodes();
                });


        base.getChildren().addAll(TitelLabel, scrollPane, ManualOverrideButton, toggleGenerateRandomlyButton );
        base.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(background,base);
        Scene scene = new Scene(stackPane);

        primaryStage.setTitle("Code Control");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public GridPane SettupCodeGrid() {
        GridPane gridPane = new GridPane();

        for (Code code : DC.getCodes()) {
            gridPane.add(new Label(code.getName()), 1, code.getStoryID());
            TextField CodeField = new TextField(code.getCode());
            CodeField.setEditable(false);
            gridPane.add(CodeField, 2, code.getStoryID());
        }
        return gridPane;
    }


}
