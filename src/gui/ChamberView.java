package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChamberView extends GridPane {
    private String floor;
    private String monster;
    private String treasure;
    private String door;
    private int length;
    private int width;
    private int numMonster;
    private int numTreasure;
    private int numDoor;

    /**
     * Creates the image view of the current space.
     * @param spaceLen length of the space.
     * @param spaceWid width of the space.
     * @param monsterNum number of monster in the space.
     * @param treasureNum number of treasure in the space.
     * @param doorNum number of doors in the space.
     */
    public ChamberView(int spaceLen, int spaceWid, int monsterNum, int treasureNum, int doorNum) {
        floor = "/res/floor.png";
        monster = "/res/monster.png";
        treasure = "/res/treasure.png";
        door = "/res/door.png";
        length = spaceLen;
        width = spaceWid;
        numMonster = monsterNum;
        numTreasure = treasureNum;
        numDoor = doorNum;

        Node[] tiles = makeTiles(); // creates all the needed tiles.
        // Generating floor.
        int k = 0;
        for (int i = 0; i < spaceLen; ++i) {
            for (int j = 0; j < spaceWid; ++j) {
                add(tiles[k], i, j, 1, 1);
                ++k;
            }
        }
        // Generating Monster.
        for (int i = 0; i < numMonster; ++i) {
            add(tiles[k], i + 15, 0, 1, 1);
            ++k;
        }
        // Generating Treasure.
        for (int i = 0; i < numTreasure; ++i) {
            add(tiles[k], i + 15, 1, 1, 1);
            ++k;
        }
        // Generating Door.
        for (int i = 0; i < numDoor; ++i) {
            add(tiles[k], i + 15, 2, 1, 1);
            ++k;
        }
    }

    /**
     * Creates all the tiles needed for the chamber view.
     * @return a node containing all the tiles.
     */
    private Node[] makeTiles() { //should have a parameter and a loop
        Node[] toReturn = new Node[200];
        int num = 0;
        int temp = 0;
        // Creating floors.
        temp += length * width; // Stops infinite loop.
        for (int i = num; i < temp; ++i) {
            toReturn[i] = imageFactory(floor);
            ++num;
        }
        // Creating monsters.
        temp += numMonster; // Stops infinite loop.
        for (int i = num; i < temp; ++i) {
            toReturn[i] = imageFactory(monster);
            ++num;
        }
        // Creating treasures.
        temp += numTreasure; // Stops infinite loop.
        for (int i = num; i < temp; ++i) {
            toReturn[i] = imageFactory(treasure);
            ++num;
        }
        // Creating doors
        temp += numDoor;
        for (int i = num; i < temp; ++i) {
            toReturn[i] = imageFactory(door);
            ++num;
        }
        //Returning the tiles.
        return toReturn;
    }

    /**
     * Creates the tile for the makeTiles() function.
     * @param newImage image of the tile being created.
     * @return the label containing the image.
     */
    public Node imageFactory(String newImage) {
        Image image = new Image(getClass().getResourceAsStream(newImage));
        Label label = new Label();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        label.setGraphic(imageView);
        return label;
    }
}
