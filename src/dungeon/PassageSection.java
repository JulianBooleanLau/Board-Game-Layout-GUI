/**
 * Read the README.md for more details.
 * @author Julian Lau
 */
package dungeon;

import dnd.models.Monster;
import dnd.die.D20;

/* Represents a 10 ft section of passageway */
public class PassageSection {

    /**
     * The passage section description.
     */
    private String theDescription;
    /**
     * The monster in the passage section (if there is one).
     */
    private Monster monster;
    /**
     * Flag indiating if there is a monster.
     */
    private boolean monsterExist;
    /**
     * The door in the passage section (if there is one).
     */
    private Door door;

    /**
     * Sets up the 10 foot section with default settings.
     */
    public PassageSection() {
        randomizePassageSection(0);
    }

    /**
     * Sets up a specific passage based on the values sent in from modified table 1.
     * @param roll Sets the passage section description to roll inputted.
     */
    public PassageSection(int roll) {
        randomizePassageSection(roll); //Random settings
    }

    /**
     * Randomizes the description and settings of the passage section.
     * @param roll A # from 1-20, if 0 is passed, a random number is generated.
     */
    private void randomizePassageSection(int roll) {
        D20 d20 = new D20();
        setMonsterExist(false);
        if (roll == 0) {
            roll = d20.roll();
        }
        if (roll == 1 || roll == 2 || roll == 6 || roll == 7
            || roll == 8 || roll == 9 || roll == 10 || roll == 11
            || roll == 12 || roll == 13 || roll == 17 || roll == 18 || roll == 19) {
            setDescriptionNoDoor(roll);
        } else {
            setDescriptionDoor(roll);
        }
    }

    /**
     * Sets the description of the door based on the roll.
     * @param roll Roll that sets the description.
     */
    private void setDescriptionNoDoor(int roll) {
        if (roll == 1 || roll == 2) {
            setDescription("Passage goes straight for 10 ft.");
        } else if (roll == 6 || roll == 7) {
            setDescription("Archway (door) to right (main passage continues straight for 10 ft).");
        } else if (roll == 8 || roll == 9) {
            setDescription("Archway (door) to the left (main passage continues straight for 10 ft).");
        } else if (roll == 10 || roll == 11) {
            setDescription("Passage turns to the left and continues for 10 ft.");
        } else if (roll == 12 || roll == 13) {
            setDescription("Passage turns to the right and continues for 10 ft.");
        } else if (roll == 17) {
            setDescription("Stairs (passage continues straight for 10 ft).");
        } else if (roll == 18 || roll == 19) {
            setDescription("Dead End.");
        }
    }

    /**
     * Sets the description of the door based on the roll.
     * @param roll Roll that sets the description.
     */
    private void setDescriptionDoor(int roll) {
        if (roll == 3 || roll == 4 || roll == 5) {
            setDescription("Passage ends in Door to a Chamber.");
            setDoor(new Door());
        } else if (roll == 14 || roll == 15 || roll == 16) {
            setDescription("Passage ends in archway (door) to chamber.");
            setDoor(new Door());
            getDoor().setArchway(true);
        } else if (roll == 20) {
            setMonster(new Monster());
        }
    }

    /**
     * Sets the passage section description.
     * @param newDescription Sets the description to this.
     */
    private void setDescription(String newDescription) {
        theDescription = newDescription;
    }

    /**
     * Gets the description of the passage section.
     * @return A string of the description.
     */
    public String getDescription() {
        return theDescription;
    }

    /**
     * Returns true if a monster exists.
     * @return The state of the monster.
     */
    private boolean isMonster() {
        return monsterExist;
    }

    /**
     * Sets whether or not the passage section has monster.
     * @param flag Sets is to flag.
     */
    private void setMonsterExist(boolean flag) {
        monsterExist = flag;
    }

    /**
     * Sets the monster.
     * @param newMonster The monster its set to.
     */
    public void setMonster(Monster newMonster) {
        setMonsterExist(true);
        monster = newMonster;
        setDescription("Wandering Monster (passage continues straight for 10 ft).\nThe monsters are " + getMonster().getMinNum() + " to " + getMonster().getMaxNum() + " " + getMonster().getDescription() + ".");
    }

    /**
     * Gets the monster in the passage section.
     * @return A monster, if there isn't one, returns null.
     */
    public Monster getMonster() {
        return monster;
    }

    /**
     * Sets the door of the passage section.
     * @param newDoor Sets the door to this.
     */
    public void setDoor(Door newDoor) {
        door = newDoor;
    }

    /**
     * Gets the door in the passage section.
     * @return A door, if there isn't one, returns null.
     */
    public Door getDoor() {
        return door;
    }

}
