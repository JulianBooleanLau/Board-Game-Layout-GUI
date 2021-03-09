package gui;

import dnd.die.D10;
import dnd.models.Monster;
import dnd.models.Treasure;
import dungeon.Generator;

public class Controller {
    /**
     * The class that contains the level generation algorithm.
     */
    private Generator generator;

    /**
     * Generates the layout from the generator.
     */
    public Controller() {
        generator = new Generator();
        generator.createChambers(5);
        generator.setAvailableChambers();
        generator.assignChambers();
        generator.createPassages();
    }

    //====================================================
    // Getters for num of _____ .
    //====================================================

    /**
     * Gets the number of chambers.
     * @return int of number of chambers.
     */
    public int getNumChambers() {
        return generator.getChamberList().size();
    }

    /**
     * Gets the number of passages.
     * @return int of number of passages.
     */
    public int getNumPassages() {
        return generator.getPassageList().size();
    }

    /**
     * Gets the number of doors for the chamber.
     * @param chamberNum The chamber number.
     * @return Int of number of doors.
     */
    public int getChamberNumDoors(int chamberNum) {
        return generator.getChamberList().get(chamberNum).getDoorList().size();
    }

    /**
     * Gets the number of doors for the passage.
     * @param passageNum The passage number.
     * @return Int of number of doors.
     */
    public int getPassageNumDoors(int passageNum) {
        return generator.getPassageList().get(passageNum).getDoors().size();
    }

    //====================================================
    // Getters for descriptions.
    //====================================================

    /**
     * Gets the description of the chamber door.
     * @param chamberNum Chamber number.
     * @param doorNum Door number.
     * @return String of the door description.
     */
    public String getDoorDescription(int chamberNum, int doorNum) {
        return generator.getChamberList().get(chamberNum).getDoorList().get(doorNum).getDescription();
    }

    /**
     * Gets the description of the chamber.
     * @param chamberNum The chamber number.
     * @return String on the chamber description.
     */
    public String getChamberDescription(int chamberNum) {
        return generator.getChamberList().get(chamberNum).getDescription();
    }

    /**
     * Gets the description of the passage.
     * @param passageNum The passage number.
     * @return String of the passage description.
     */
    public String getPassageDescription(int passageNum) {
        return generator.getPassageList().get(passageNum).getDescription();
    }

    /**
     * Gets the description of the passage links.
     * @return String of the passage links.
     */
    public String getPassageLinks() {
        D10 d10 = new D10();
        int link1 = (d10.roll() % 5) + 1;
        int link2 = (d10.roll() % 5) + 1;
        while (link2 == link1) {
            link2 = (d10.roll() % 5) + 1;
        }
        return "The passage is linked to Chamber #" + link1 + " and Chamber #" + link2;
    }

    //====================================================
    // Add monsters methods.
    //====================================================

    /**
     * Adds a monster to the chamber.
     * @param chamberNum Chamber number.
     * @param roll Roll of the monster.
     */
    public void addChamberMonster(int chamberNum, int roll) {
        Monster m = new Monster();
        m.setType(roll);
        generator.getChamberList().get(chamberNum).addMonster(m);
    }

    /**
     * Adds a monster to the passage.
     * @param passageNum Passage number.
     * @param roll Roll of the monster.
     */
    public void addPassageMonster(int passageNum, int roll) {
        Monster m = new Monster();
        m.setType(roll);
        generator.getPassageList().get(passageNum).addMonster(m);
    }

    //====================================================
    // Add treasure methods.
    //====================================================

    /**
     * Adds a treasure to the chamber.
     * @param chamberNum Chamber number.
     * @param roll Roll of the treasure.
     */
    public void addChamberTreasure(int chamberNum, int roll) {
        Treasure t = new Treasure();
        t.chooseTreasure(roll);
        t.setContainer((roll % 20) + 1);
        generator.getChamberList().get(chamberNum).addTreasure(t);
    }

    /**
     * Adds a treasure to the passage.
     * @param passageNum Passage number.
     * @param roll Roll of the treasure.
     */
    public void addPassageTreasure(int passageNum, int roll) {
        Treasure t = new Treasure();
        t.chooseTreasure(roll);
        generator.getPassageList().get(passageNum).addTreasure(t);
    }

    //====================================================
    // Remove monster methods.
    //====================================================

    /**
     * Gets the number of monsters in the chamber.
     * @param chamberNum Chamber number.
     * @return Int of number of mosnters.
     */
    public int getChamberNumMonsters(int chamberNum) {
        int numMonsters = 0;
        numMonsters = generator.getChamberList().get(chamberNum).getMonsterList().size();
        return numMonsters;
    }

    /**
     * Gets the name of the monster in the chamber.
     * @param chamberNum Chamber number.
     * @param monsterNum Monster number.
     * @return String of the monster name.
     */
    public String getChamberMonsterName(int chamberNum, int monsterNum) {
        String monsterName = "";
        monsterName = generator.getChamberList().get(chamberNum).getMonsterList().get(monsterNum).getDescription();
        return monsterName;
    }

    /**
     * Removes a monster from the chamber.
     * @param chamberNum Chamber number.
     * @param monsterNum Monster number.
     */
    public void removeChamberMonster(int chamberNum, int monsterNum) {
        generator.getChamberList().get(chamberNum).removeMonster(monsterNum);
    }

    /**
     * Gets the number of monsters in the passage.
     * @param passageNum Passage number.
     * @return Int of the number of monsters.
     */
    public int getPassageNumMonsters(int passageNum) {
        int numMonsters = 0;
        numMonsters = generator.getPassageList().get(passageNum).getMonsterList().size();
        return numMonsters;
    }

    /**
     * Gets the name of the monster in the passage.
     * @param passageNum Passage number.
     * @param monsterNum Monster number.
     * @return String of the monster name.
     */
    public String getPassageMonsterName(int passageNum, int monsterNum) {
        String monsterName = "";
        monsterName = generator.getPassageList().get(passageNum).getMonsterList().get(monsterNum).getDescription();
        return monsterName;
    }

    /**
     * Removes a monster from the passage.
     * @param passageNum Passage number.
     * @param monsterNum Monster number.
     */
    public void removePassageMonster(int passageNum, int monsterNum) {
        generator.getPassageList().get(passageNum).removeMonster(monsterNum);
    }

    //====================================================
    // Remove treasure methods.
    //====================================================

    /**
     * Gets the name of the treasure in the chamber.
     * @param chamberNum Chamber number.
     * @return Int of the number of treasure in the chamber.
     */
    public int getChamberNumTreasure(int chamberNum) {
        int numTreasure = 0;
        numTreasure = generator.getChamberList().get(chamberNum).getTreasureList().size();
        return numTreasure;
    }

    /**
     * Gets the name of the treasure in the chamber.
     * @param chamberNum Chamber number.
     * @param treasureNum Treasure number.
     * @return String of the treasure name.
     */
    public String getChamberTreasureName(int chamberNum, int treasureNum) {
        String treasureName = "";
        treasureName = generator.getChamberList().get(chamberNum).getTreasureList().get(treasureNum).getDescription();
        return treasureName;
    }

    /**
     * Removes the treasure in the chamber.
     * @param chamberNum Chamber number.
     * @param treasureNum Treasure number.
     */
    public void removeChamberTreasure(int chamberNum, int treasureNum) {
        generator.getChamberList().get(chamberNum).removeTreasure(treasureNum);
    }

    /**
     * Gets the number of treasure in a passage.
     * @param passageNum Passage number.
     * @return Int of the number of treasures.
     */
    public int getPassageNumTreasure(int passageNum) {
        int numTreasure = 0;
        numTreasure = generator.getPassageList().get(passageNum).getTreasureList().size();
        return numTreasure;
    }

    /**
     * Returns the name of the passage treasure.
     * @param passageNum Passage number.
     * @param treasureNum treasure number.
     * @return String of the treasure name.
     */
    public String getPassageTreasureName(int passageNum, int treasureNum) {
        String treasureName = "";
        treasureName = generator.getPassageList().get(passageNum).getTreasureList().get(treasureNum).getDescription();
        return treasureName;
    }

    /**
     * Removes the treasure from the passage.
     * @param passageNum Passage number.
     * @param treasureNum Treasure number.
     */
    public void removePassageTreasure(int passageNum, int treasureNum) {
        generator.getPassageList().get(passageNum).removeTreasure(treasureNum);
    }

    //====================================================
    // Required methods for the chamberView.
    //====================================================

    /**
     * Gets the chamber length.
     * @param chamberNum The chamber number.
     * @return Int of the length.
     */
    public int getChamberLength(int chamberNum) {
        return generator.getChamberList().get(chamberNum).getLength() / 5;
    }

    /**
     * Gets the chamber width.
     * @param chamberNum The chamber number.
     * @return Int of the width.
     */
    public int getChamberWidth(int chamberNum) {
        return generator.getChamberList().get(chamberNum).getWidth() / 5;
    }
}
