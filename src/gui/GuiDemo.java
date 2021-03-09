package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.File;

public class GuiDemo<toReturn> extends Application {
    /* Instance Variables */
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup editPopup;
    private Popup editAddMonsterPopup;
    private Popup editAddTreasurePopup;
    private Popup editRemoveMonsterPopup;
    private Popup editRemoveTreasurePopup;
    private ListView listView;
    private Stage primaryStage;  //The stage that is passed in on initialization
    private String description; // Description of the space + door
    private Text text; // Text that displays space description
    private ChoiceBox choiceBox; // Choice of doors for that space
    private GridPane imageGridPane;
    private BorderPane rootBorderPane;
    private int currentSpace = 0;
    private int currentDoor = 0;
    private boolean isChamber; // True if the current space is a chamber, false if its a passage.

    /* A call to start replaces a call to the constructor for a JavaFX GUI */
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller();
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
        root = setUpRoot();
        editPopup = createEditPopup(600, 300);
        editAddMonsterPopup = createAddMonsterPopup(800, 300);
        editAddTreasurePopup = createAddTreasurePopup(800, 300);
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Dungeon Generator Gui");
        doorListViewListener();
        choiceBoxListener(); // Updates the description based off of chamber and door selected

        // Displaying scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane setUpRoot() {
        rootBorderPane = new BorderPane();
        Node node;

        // Top
        node = setTopMenuBar();
        rootBorderPane.setTop(node);

        // Left
        node = setLeftPanel();
        rootBorderPane.setLeft(node);

        // Middle
        node = setMiddleTextPanel();
        rootBorderPane.setCenter(node);

        // Bottom
        imageGridPane = new ChamberView(0, 0, 0, 0, 0);
        rootBorderPane.setBottom(imageGridPane);

        // Right
        node = setRightChoiceBox();
        rootBorderPane.setRight(node);

        return rootBorderPane;
    }

    private Node setTopMenuBar() {
        Menu menu = new Menu("File");
        FileChooser fileChooser = new FileChooser();

        // Creating the menu options.
        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> {
            File saveFile = fileChooser.showSaveDialog(primaryStage);
        });
        menu.getItems().add(save);

        MenuItem load = new MenuItem("Load");
        load.setOnAction(event -> {
            File loadFile = fileChooser.showOpenDialog(primaryStage);
        });
        menu.getItems().add(load);

        // Adding option to the menu bar.
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private Node setLeftPanel() {
        // Setup
        VBox vBox = createVBox();
        listView = new ListView();
        listView.setPrefWidth(125);
        listView.setPrefHeight(475);

        // Creating list of Chambers
        for (int i = 0; i < theController.getNumChambers(); ++i) {
            listView.getItems().add("Chamber #" + (i + 1));
        }

        // Creating list of Passages
        for (int i = 0; i < theController.getNumPassages(); ++i) {
            listView.getItems().add("Passage #" + (i + 1));
        }
        vBox.getChildren().add(listView);

        // Edit Buttons
        Button edit = createButton("Open Edit");
        edit.setGraphic(new ImageView("/res/edit.png"));
        edit.setOnAction((ActionEvent event) -> {
            if (!editPopup.isShowing()) {
                editPopup.show(primaryStage);
            } else {
                editPopup.hide();
            }
        });
        vBox.getChildren().add(edit);

        Button closeEdit = createButton("Close Edit");
        closeEdit.setOnAction((ActionEvent event) -> {
            editPopup.hide();
            editAddMonsterPopup.hide();
            editAddTreasurePopup.hide();
            editRemoveMonsterPopup.hide();
            editRemoveTreasurePopup.hide();
        });
        vBox.getChildren().add(closeEdit);

        return vBox;
    }

    private Node setMiddleTextPanel() {
        VBox vbox = createVBox();
        text = new Text();
        text.setText("Select a \"space\" on the left hand side to display its attributes!");
        text.setWrappingWidth(300);
        vbox.getChildren().add(text);
        return vbox;
    }

    private Node setRightChoiceBox() {
        VBox vbox = createVBox();
        choiceBox = new ChoiceBox();
        choiceBox.getItems().add("Select Space First");
        choiceBox.getSelectionModel().select(0); // Selects the first option first
        choiceBox.setPrefWidth(125);
        choiceBox.setPrefHeight(25);
        vbox.getChildren().add(choiceBox);
        return vbox;
    }

    private void doorListViewListener() {
        listView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            // Checks if the space is a passage or a chamber.
            if (listView.getSelectionModel().getSelectedItem().toString().charAt(0) == 'C') {
                isChamber = true;
                currentSpace = listView.getSelectionModel().getSelectedItem().toString().charAt(9) - 49;
            } else if (listView.getSelectionModel().getSelectedItem().toString().charAt(0) == 'P') {
                isChamber = false;
                currentSpace = listView.getSelectionModel().getSelectedItem().toString().charAt(9) - 49;
            } else {
                isChamber = true;
                System.out.println("error");
            }

            // Adds doors and description based on the space.
            choiceBox.getItems().clear();
            if (isChamber) { // Current space is a chamber.
                for (int i = 0; i < theController.getChamberNumDoors(currentSpace); ++i) {
                    choiceBox.getItems().add("Door #" + (i + 1));
                }
                createChamberDescription(currentSpace);
            } else { // Current space is not a chamber.
                for (int i = 0; i < theController.getPassageNumDoors(currentSpace); ++i) {
                    choiceBox.getItems().add("Door #" + (i + 1));
                }
                createPassageDescription(currentSpace);
            }
            text.setText(description);
            updateImage();
        });
    }

    private void choiceBoxListener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
            if (choiceBox.getValue().equals("Door #1")) {
                currentDoor = 0;
            } else if (choiceBox.getValue().equals("Door #2")) {
                currentDoor = 1;
            } else if (choiceBox.getValue().equals("Door #3")) {
                currentDoor = 2;
            } else if (choiceBox.getValue().equals("Door #4")) {
                currentDoor = 3;
            }
            text.setText(description + choiceBox.getValue() + ":\n" + theController.getDoorDescription(currentSpace, currentDoor));
        });
    }

    private Popup createEditPopup(int x, int y) {
        // Creates the popup with VBox.
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        VBox vbox = createVBox();

        // Add monster button.
        Button editAddMonster = createButton("Add Monster");
        editAddMonster.setOnAction((ActionEvent event) -> {
            editPopup.hide();
            editAddMonsterPopup.show(primaryStage);
        });
        vbox.getChildren().add(editAddMonster);

        // Add treasure button.
        Button editAddTreasure = createButton("Add Treasure");
        editAddTreasure.setOnAction((ActionEvent event) -> {
            editPopup.hide();
            editAddTreasurePopup.show(primaryStage);
        });
        vbox.getChildren().add(editAddTreasure);

        // Remove monster button.
        Button editRemoveMonster = createButton("Remove Monster");
        editRemoveMonster.setOnAction((ActionEvent event) -> {
            editPopup.hide();
            editRemoveMonsterPopup = createRemoveMonsterPopup(450, 300);
            editRemoveMonsterPopup.show(primaryStage);
        });
        vbox.getChildren().add(editRemoveMonster);

        // Remove treasure button.
        Button editRemoveTreasure = createButton("Remove Treasure");
        editRemoveTreasure.setOnAction((ActionEvent event) -> {
            editPopup.hide();
            editRemoveTreasurePopup = createRemoveTreasurePopup(450, 300);
            editRemoveTreasurePopup.show(primaryStage);
        });
        vbox.getChildren().add(editRemoveTreasure);

        popup.getContent().add(vbox);
        return popup;
    }

    private Popup createAddMonsterPopup(int x, int y) {
        // Creates the popup with VBox.
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        VBox vbox = createVBox();

        String[] names = {"Badger", "Dwarf", "Elf", "Gnome", "Kobold", "Orc", "Shrieker", "Skeleton"};
        int[] rolls = {4, 17, 18, 21, 54, 66, 96, 98};
        for (int i = 0; i < 8; ++i) {
            Button button = createButton(names[i]);
            int roll = rolls[i]; // Lambda notation needs this.
            button.setOnAction((ActionEvent event) -> {
                if (isChamber) { // Space is a chamber.
                    theController.addChamberMonster(currentSpace, roll);
                    updateText();
                } else { // Space is a passage.
                    theController.addPassageMonster(currentSpace, roll);
                    updateText();
                }
                editAddMonsterPopup.hide();
                updateImage();
            });
            vbox.getChildren().add(button);
        }

        // Returning the generated popup.
        popup.getContent().add(vbox);
        return popup;
    }

    private Popup createAddTreasurePopup(int x, int y) {
        // Creates the popup with VBox.
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        VBox vbox = createVBox();

        String[] names = {"1,000 copper pieces/level", "1,000 silver pieces/level", "750 electrum pieces/level", "250 gold pieces/level", "100 platinum pieces/level", "1-4 gems/level", "1 piece jewelry/level", "Magic (roll once on Magic ItemsTable)"};
        int[] rolls = {1, 26, 51, 66, 81, 91, 95, 98};
        for (int i = 0; i < 8; ++i) {
            Button button = createButton(names[i]);
            int roll = rolls[i]; // Lambda notation needs this.
            button.setOnAction((ActionEvent event) -> {
                if (isChamber) { // Space is a chamber.
                    theController.addChamberTreasure(currentSpace, roll);
                    updateText();
                } else { // Space is a passage.
                    theController.addPassageTreasure(currentSpace, roll);
                    updateText();
                }
                editAddTreasurePopup.hide();
                updateImage();
            });
            vbox.getChildren().add(button);
        }

        // Returning the generated popup.
        popup.getContent().add(vbox);
        return popup;
    }

    private Popup createRemoveMonsterPopup(int x, int y) {
        // Creates the popup with VBox.
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        VBox vbox = createVBox();

        if (isChamber) { // Space is a chamber.
            for (int i = 0; i < theController.getChamberNumMonsters(currentSpace); ++i) {
                Button button = createButton(theController.getChamberMonsterName(currentSpace, i));
                int finalI = i;
                button.setOnAction((ActionEvent event) -> {
                    theController.removeChamberMonster(currentSpace, finalI);
                    vbox.getChildren().remove(button); // Removing option once selected.
                    editRemoveMonsterPopup.hide();
                    updateText();
                    updateImage();
                });
                vbox.getChildren().add(button);
            }
        } else { // Space is a passage.
            for (int i = 0; i < theController.getPassageNumMonsters(currentSpace); ++i) {
                Button button = createButton(theController.getPassageMonsterName(currentSpace, i));
                int finalI = i;
                button.setOnAction((ActionEvent event) -> {
                    theController.removePassageMonster(currentSpace, finalI);
                    vbox.getChildren().remove(button); // Removing option once selected.
                    editRemoveMonsterPopup.hide();
                    updateText();
                    updateImage();
                });
                vbox.getChildren().add(button);
            }
        }

        // Returning the generated popup.
        popup.getContent().add(vbox);
        return popup;
    }

    private Popup createRemoveTreasurePopup(int x, int y) {
        // Creates the popup with VBox.
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        VBox vbox = createVBox();

        // Removing the treasure.
        if (isChamber) { // Space is a chamber.
            for (int i = 0; i < theController.getChamberNumTreasure(currentSpace); ++i) {
                Button button = createButton(theController.getChamberTreasureName(currentSpace, i));
                int finalI = i;
                button.setOnAction((ActionEvent event) -> {
                    theController.removeChamberTreasure(currentSpace, finalI);
                    vbox.getChildren().remove(button); // Removing option once selected.
                    editRemoveTreasurePopup.hide();
                    updateText();
                    updateImage();
                });
                vbox.getChildren().add(button);
            }
        } else { // Space is a passage.
            for (int i = 0; i < theController.getPassageNumTreasure(currentSpace); ++i) {
                Button button = createButton(theController.getPassageTreasureName(currentSpace, i));
                int finalI = i;
                button.setOnAction((ActionEvent event) -> {
                    theController.removePassageTreasure(currentSpace, finalI);
                    vbox.getChildren().remove(button); // Removing option once selected.
                    editRemoveTreasurePopup.hide();
                    updateText();
                    updateImage();
                });
                vbox.getChildren().add(button);
            }
        }

        // Returning the generated popup.
        popup.getContent().add(vbox);
        return popup;
    }

    /**
     * Generates the description of the chamber.
     * @param chamberNumber The chamber number.
     */
    private void createChamberDescription(int chamberNumber) {
        description = "Chamber Description\n==========================\n" + theController.getChamberDescription(chamberNumber) + "\n Door Description\n==========================\n";
    }

    /**
     * Generates the description of the passage.
     * @param passageNum The passage number.
     */
    private void createPassageDescription(int passageNum) {
        description = "Passage Description\n==========================\n" + theController.getPassageDescription(passageNum) + theController.getPassageLinks() + "\n\n Door Description\n==========================\n";
    }

    /**
     * Updates the main space description.
     */
    private void updateText() {
        if (isChamber) { // Space is a chamber.
            createChamberDescription(currentSpace);
        } else { // Space is not a chamber.
            createPassageDescription(currentSpace);
        }
        text.setText(description);
    }

    /**
     * Updates the current displayed image of the space.
     */
    private void updateImage() {
        if (isChamber) { // Space is a chamber.
            imageGridPane = new ChamberView(theController.getChamberLength(currentSpace), theController.getChamberWidth(currentSpace), theController.getChamberNumMonsters(currentSpace), theController.getChamberNumTreasure(currentSpace), theController.getChamberNumDoors(currentSpace));
        } else {
            imageGridPane = new ChamberView(6, 2, theController.getPassageNumMonsters(currentSpace), theController.getPassageNumTreasure(currentSpace), theController.getPassageNumDoors(currentSpace));
        }
        rootBorderPane.setBottom(imageGridPane);
    }

    /**
     * Creates a generic Button.
     * @param theText The name of the button.
     * @return The created button.
     */
    private Button createButton(String theText) {
        Button button = new Button();
        button.setText(theText);
        button.setPrefWidth(125);
        button.setPrefHeight(25);
        return button;
    }

    /**
     * Creates a generic VBox.
     * @return the created VBox.
     */
    private VBox createVBox() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
        return  vbox;
    }

    /**
     * Main that launches the GUI.
     * @param args Required for the main.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
