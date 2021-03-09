/**
 * Read the README.md for more details.
 * @author Julian Lau
 */
package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;

/**
 * A passage begins at a door and ends at a door.
 * It may have many other doors along the way.
 * You will need to keep track of which door is the "beginning" of the passage.
 * so that you know how to generate chambers.
 */
public class Passage extends Space {

    /**
     * Stores all the passage sections in this passage.
     */
    private ArrayList<PassageSection> thePassage;
    /**
     * Stores all the doors in this passage.
     */
    private ArrayList<Door> doors;
    /**
     * The list of all the monsters.
     */
    private ArrayList<Monster> monsterList;
    /**
     * The list of all the treasures.
     */
    private ArrayList<Treasure> treasureList;
    /**
     * Passage constructor.
     */
    public Passage() {
        monsterList = new ArrayList<Monster>();
        treasureList = new ArrayList<Treasure>();
        thePassage = new ArrayList<PassageSection>();
        doors = new ArrayList<Door>();
    }

    /**
     * Adds the passage section to the passageway.
     * @param toAdd The passage section being added.
     */
    public void addPassageSection(PassageSection toAdd) {
        thePassage.add(toAdd);
    }

    /**
     * Returns all the passage sections in the passage.
     * @return Returns an array list of all the passage sections.
     */
    public ArrayList<PassageSection> getPassages() {
        return thePassage;
    }

    /**
     * Sets the door at the current section.
     * @param newDoor The door being added to the passage section.
     */
    @Override
    public void addDoor(Door newDoor) {
        thePassage.get(getPassages().size() - 1).setDoor(newDoor);
    }

    /**
     * Creates two doors for the passage.
     */
    public void createDoors() {
        doors.add(new Door());
        doors.add(new Door());
    }

    /**
     * Gets all of the doors in the entire passage.
     * @return An array list of doors.
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * Gets the description of the entire passage.
     * @return A string of the description.
     */
    @Override
    public String getDescription() {
        String description = "";
        for (int i = 0; i < thePassage.size(); ++i) {
            description = description + thePassage.get(i).getDescription() + "\n";
        }
        for (int i = 0; i < treasureList.size(); ++i) {
            description = description + "The treasure is " + treasureList.get(i).getDescription() + " contained inside " + treasureList.get(i).getContainer() + ".\n";
        }
        for (int i = 0; i < monsterList.size(); ++i) {
            description = description + "The monsters are " + monsterList.get(i).getMinNum() + " to " + monsterList.get(i).getMaxNum() + " " + monsterList.get(i).getDescription() + ".\n";
        }
        return description;
    }

    /**
     * Gets the list of treasures.
     * @return The list of treasure.
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasureList;
    }

    /**
     * Adds a treasure to the passage.
     * @param newTreasure The treasure added.
     */
    public void addTreasure(Treasure newTreasure) {
        treasureList.add(newTreasure);
    }

    /**
     * Removes the treasure from the passage.
     * @param treasureNum The index of the treasure.
     */
    public void removeTreasure(int treasureNum) {
        treasureList.remove(treasureNum);
    }

    /**
     * Gets the list of monsters.
     * @return the list of mosnters.
     */
    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    /**
     * Adds a monster to the passage.
     * @param newMonster The mosnter added.
     */
    public void addMonster(Monster newMonster) {
        monsterList.add(newMonster);
    }

    /**
     * Removes a mosnter from the list of monsters.
     * @param monsterNum The index of the monster.
     */
    public void removeMonster(int monsterNum) {
        monsterList.remove(monsterNum);
    }
}
