package com.excape;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.File;

/**
 * FXML Tester Application
 * A simple tool to load and display FXML files for testing purposes
 */
public class FXMLTester extends Application {
    
    private BorderPane mainLayout;
    private TextField filePathField;
    private Label statusLabel;
    private ScrollPane contentPane;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FXML Tester");
        
        // Create main layout
        mainLayout = new BorderPane();
        
        // Create top control panel
        VBox topPanel = createTopPanel();
        mainLayout.setTop(topPanel);
        
        // Create center content area
        contentPane = new ScrollPane();
        contentPane.setFitToWidth(true);
        contentPane.setFitToHeight(true);
        contentPane.setStyle("-fx-background-color: #f0f0f0;");
        
        Label placeholderLabel = new Label("Load an FXML file to preview it here");
        placeholderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888888;");
        contentPane.setContent(placeholderLabel);
        
        mainLayout.setCenter(contentPane);
        
        // Create bottom status bar
        HBox bottomPanel = createBottomPanel();
        mainLayout.setBottom(bottomPanel);
        
        // Create and show scene
        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Creates the top control panel with file input and load button
     */
    private VBox createTopPanel() {
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(15));
        topPanel.setStyle("-fx-background-color: #2c3e50;");
        
        Label titleLabel = new Label("FXML File Tester");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label fileLabel = new Label("FXML File Path:");
        fileLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        filePathField = new TextField();
        filePathField.setPromptText("Enter FXML file path (e.g., scene1.fxml or /path/to/file.fxml)");
        filePathField.setPrefWidth(500);
        HBox.setHgrow(filePathField, Priority.ALWAYS);
        
        // Load on Enter key
        filePathField.setOnAction(e -> loadFXMLFile());
        
        Button loadButton = new Button("Load FXML");
        loadButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        loadButton.setOnAction(e -> loadFXMLFile());
        
        Button reloadButton = new Button("Reload");
        reloadButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        reloadButton.setOnAction(e -> loadFXMLFile());
        
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        clearButton.setOnAction(e -> clearDisplay());
        
        inputBox.getChildren().addAll(fileLabel, filePathField, loadButton, reloadButton, clearButton);
        
        topPanel.getChildren().addAll(titleLabel, inputBox);
        
        return topPanel;
    }
    
    /**
     * Creates the bottom status bar
     */
    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox();
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setStyle("-fx-background-color: #34495e;");
        
        statusLabel = new Label("Ready to load FXML file");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        
        bottomPanel.getChildren().add(statusLabel);
        
        return bottomPanel;
    }
    
    /**
     * Loads and displays the FXML file
     */
    private void loadFXMLFile() {
        String filePath = filePathField.getText().trim();
        
        if (filePath.isEmpty()) {
            showError("Please enter a file path");
            return;
        }
        
        try {
            // Check if file exists
            File fxmlFile = new File(filePath);
            if (!fxmlFile.exists()) {
                showError("File not found: " + filePath);
                return;
            }
            
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
            Parent root = loader.load();
            
            // Display the loaded content
            contentPane.setContent(root);
            
            // Update status
            statusLabel.setText("Successfully loaded: " + filePath);
            statusLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 12px;");
            
        } catch (Exception e) {
            showError("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Clears the display area
     */
    private void clearDisplay() {
        Label placeholderLabel = new Label("Load an FXML file to preview it here");
        placeholderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888888;");
        contentPane.setContent(placeholderLabel);
        
        filePathField.clear();
        statusLabel.setText("Display cleared - Ready to load FXML file");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
    }
    
    /**
     * Shows an error message
     */
    private void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Failed to Load FXML");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}