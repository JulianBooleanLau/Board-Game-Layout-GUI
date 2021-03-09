/**
 * Read the README.md for more details.
 * @author Julian Lau
 */
package dungeon;

import java.util.ArrayList;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.die.D20;

public class Chamber extends Space {
    /**
     * Stores information about chamber shape and size.
     */
    private ChamberShape chamberShape;
    /**
     * Stores information about chamber contents.
     */
    private ChamberContents chamberContents;
    /**
     * Stores information about chamber monster.
     */
    private Monster monster;
    /**
     * Stores information about chamber trap.
     */
    private Trap trap;
    /**
     * Stores information about chamber stairs.
     */
    private Stairs stairs;
    /**
     * Stores all the monsters in the chamber.
     */
    private ArrayList<Monster> monsterList;
    /**
     * Stores all the treasure in the chamber.
     */
    private ArrayList<Treasure> treasureList;
    /**
     * Stores all the doors in the chamber.
     */
    private ArrayList<Door> doorList;
    /**
     * Description of the chamber.
     */
    private String description;

    /**
     * Chamber constructor.
     */
    public Chamber() {
        chamberContents = new ChamberContents();
        monster = new Monster();
        trap = new Trap();
        stairs = new Stairs();
        monsterList = new ArrayList<Monster>();
        treasureList = new ArrayList<Treasure>();
        doorList = new ArrayList<Door>();
        description = "";
        randomizeChamber();
        exitsToDoors();
    }

    /**
     * Returns the length of the chamber.
     * @return int of the length.
     */
    public int getLength() {
        return chamberShape.getLength();
    }

    /**
     * Returns the width of the chamber.
     * @return int of the width.
     */
    public int getWidth() {
        return chamberShape.getWidth();
    }

    /**
     * Randomizes all aspects of the chamber. The shape has to be square or rectangle.
     */
    private void randomizeChamber() {
        D20 d20 = new D20();
        chamberShape = ChamberShape.selectChamberShape(d20.roll());
        while (chamberShape.getNumExits() > 4 || chamberShape.getNumExits() < 2 || (!(chamberShape.getShape().contains("Square")) && !(chamberShape.getShape().contains("Rectangle")))) {
            chamberShape = ChamberShape.selectChamberShape(d20.roll());
        }
        generateContents();
        generateTreasure(d20.roll());
        monster.setType(((d20.roll() * d20.roll()) % 99) + 1); //# from 1-100.
        trap.chooseTrap(d20.roll());
        stairs.setType(d20.roll());
    }

    /**
     * Ensures contents does not contain roll 15.
     */
    private void generateContents() {
        D20 d20 = new D20();
        int roll = d20.roll();
        while (roll != 15) {
            roll = d20.roll();
        }
        chamberContents.chooseContents(roll);
    }

    /**
     * Generates the treasure for the chamber.
     * @param roll An int containing the roll for treasure.
     */
    private void generateTreasure(int roll) {
        if (chamberContents.getDescription() == "monster and treasure" || chamberContents.getDescription() == "treasure") {
            Treasure t = new Treasure();
            t.chooseTreasure(roll);
            t.setContainer(roll);
            treasureList.add(t);
        }
    }

    /**
     * Adds a monster to the chamber.
     * @param theMonster Adds a mosnter to the list of treasures.
     */
    public void addMonster(Monster theMonster) {
        monsterList.add(theMonster);
    }

    /**
     * Removes the mosnter from th monster list.
     * @param monsterNum The index of the monster.
     */
    public void removeMonster(int monsterNum) {
        monsterList.remove(monsterNum);
    }

    /**
     * Returns all the monsters in the chamber.
     * @return array list of monsters.
     */
    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    /**
     * Adds a treasure to the chamber.
     * @param theTreasure Adds a treasure to the list of treasures.
     */
    public void addTreasure(Treasure theTreasure) {
        treasureList.add(theTreasure);
    }

    /**
     * Removes a treasure from the chamber.
     * @param treasureNum The index of the treasure.
     */
    public void removeTreasure(int treasureNum) {
        treasureList.remove(treasureNum);
    }

    /**
     * Returns all the treasure in the chamber.
     * @return array list of treasures.
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasureList;
    }

    /**
     * Adds a door to the chamber.
     * @param newDoor The door being added.
     */
    @Override
    public void addDoor(Door newDoor) {
        doorList.add(newDoor);
    }

    /**
     * Returns all the doors in the chamber.
     * @return Array list of doors.
     */
    public ArrayList<Door> getDoorList() {
        return doorList;
    }

    /**
     * Converts all the exits into doors and adds them to the door list.
     */
    public void exitsToDoors() {
        for (int i = 0; i < chamberShape.getNumExits(); ++i) {
            addDoor(new Door());
        }
    }

    /**
     * Generates and returns the description of the chamber.
     * @return String of the full chamber description.
     */
    @Override
    public String getDescription() {
        getChamberShapeDescription();
        //getChamberContentsDescription();
        getTrapDescription();
        getMonsterDescription();
        getStairsDescription();
        getTreasureDescription();
        return description; //Returning the final formatted string.
    }

    /**
     * Concats the description of the chamber shape & exits to the description.
     */
    private void getChamberShapeDescription() {
        description = "The chamber is " + chamberShape.getShape() + " shape and is " + chamberShape.getArea() + " square ft." + "\n";
        try {
            description = description + "The chamber has a length of " + chamberShape.getLength() + " and a width of " + chamberShape.getWidth() + ".\n";
        } catch (UnusualShapeException ex) { //Unusual chamber shapes have no length/width.
            description = description + "The chamber has no length and width." + "\n";
        }
        description = description + "The number of exits (doors) is/are " + chamberShape.getNumExits() + ".\n";
    }

    /**
     * Concats the description of the chamber trap to the description.
     */
    private void getTrapDescription() {
        if (chamberContents.getDescription() == "trap") {
            description = description + "The trap is " + trap.getDescription() + ".\n";
        }
    }

    /**
     * Concats the description of the chamber monster to the description.
     */
    private void getMonsterDescription() {
        for (int i = 0; i < monsterList.size(); ++i) {
            if (chamberContents.getDescription() == "monster and treasure" || chamberContents.getDescription() == "monster" || chamberContents.getDescription() == "monster only") {
                description = description + "The monsters are " + monsterList.get(i).getMinNum() + " to " + monsterList.get(i).getMaxNum() + " " + monsterList.get(i).getDescription() + ".\n";
            }
        }
    }

    /**
     * Concats the description of the chamber stairs to the description.
     */
    private void getStairsDescription() {
        if (chamberContents.getDescription() == "stairs") {
            description = description + "The stairs go " + stairs.getDescription() + ".\n";
        }
    }

    /**
     * Concats the description of the chamber treasure to the description.
     */
    private void getTreasureDescription() {
        for (int i = 0; i < treasureList.size(); ++i) {
            if (chamberContents.getDescription() == "monster and treasure" || chamberContents.getDescription() == "treasure") {
                description = description + "The treasure is " + treasureList.get(i).getDescription() + " contained inside " + treasureList.get(i).getContainer() + ".\n";
                try {
                    description = description + "The treasure is guarded by " + treasureList.get(i).getProtection() + ".\n";
                } catch (NotProtectedException ex2) { //Treasure is not guarded
                    description = description + "The treasure is not guarded.\n";
                }
            }
        }
    }
}
