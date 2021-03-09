/**
 * Read the README.md for more details.
 * @author Julian Lau
 */
package dungeon;

import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D10;
import dnd.die.D20;

public class Door {

    /**
     * The spaces connected to the door.
     */
    private ArrayList<Space> spaces;
    /**
     * The description of the door.
     */
    private String description;
    /**
     * The trap in the door.
     */
    private Trap trap;
    /**
     * Indicates if the door is an archway.
     */
    private boolean archway;
    /**
     * Indicates if the door is open.
     */
    private boolean open;
    /**
     * Indicates if the door is locked.
     */
    private boolean locked;
    /**
     * Indicates if the door is trapped.
     */
    private boolean trapped;

    /**
     * Door constructor. Creates a door with random values.
     */
    public Door() {
        spaces = new ArrayList<Space>();
        trap = new Trap();
        randomizeDoor();
    }

    /**
     * Randomly sets the characteristics of the door based on modified table 1 values.
     */
    private void randomizeDoor() {
        D10 d10 = new D10();
        D20 d20 = new D20();
        int roll = d10.roll();
        randomizeArchway(roll); // 1/10 chance its an archway
        if (!isArchway()) {
            roll = d10.roll();
            randomizeOpen(roll); // 1/2 chance its open
            roll = d20.roll();
            randomizeLocked(roll); // 1/6 chance its locked
            roll = d20.roll();
            randomizeTrapped(roll); // 1/20 chance its trapped
        }
    }

    /**
     * Randomizes archway.
     * @param roll The roll passed to determine the result.
     */
    private void randomizeArchway(int roll) {
        if (roll == 1) {
            setArchway(true);
        } else {
            setArchway(false);
        }
    }

    /**
     * Randomizes open.
     * @param roll The roll passed to determine the result.
     */
    private void randomizeOpen(int roll) {
        if (roll == 1 || roll == 2 || roll == 3 || roll == 4 || roll == 5) {
            setOpen(true);
        } else {
            setOpen(false);
        }
    }

    /**
     * Randomizes locked.
     * @param roll The roll passed to determine the result.
     */
    private void randomizeLocked(int roll) {
        if (roll == 1 || roll == 2 || roll == 3) {
            setLocked(true);
            setOpen(false); //Locked doors need to be closed
        } else {
            setLocked(false);
        }
    }

    /**
     * Randomizes trapped.
     * @param roll The roll passed to determine the result.
     */
    private void randomizeTrapped(int roll) {
        if (roll == 1) {
            setTrapped(true);
        } else {
            setTrapped(false);
        }
    }

    /**
     * Sets whether or not the door is an archway. true == archway.
     * @param flag Sets archway to flag.
     */
    public void setArchway(boolean flag) {
        archway = flag;
        if (flag) { //Archways are open and not trapped.
            setOpen(true);
            setLocked(false);
            setTrapped(false);
        }
    }

    /**
     * Sets whether or not the door is open. true == open.
     * @param flag Sets open to flag.
     */
    private void setOpen(boolean flag) {
        if (isArchway()) { //Archways are always open.
            open = true;
        } else {
            open = flag;
        }
    }

    /**
     * Sets whether or not the door is locked. true == locked.
     * @param flag Sets locked to flag.
     */
    private void setLocked(boolean flag) {
        locked = flag;
    }

    /**
     * Sets whether or not the door is trapped. true == trapped. Trap must be rolled if no integer is given.
     * @param flag Sets trapped to flag.
     */
    private void setTrapped(boolean flag) {
        D20 d20 = new D20();
        if (flag) {
            setArchway(false);
        }
        trapped = flag;
        trap.chooseTrap(d20.roll());
    }

    /**
     * Returns the value of archway.
     * @return Returns the value of archway.
     */
    public boolean isArchway() {
        return archway;
    }

    /**
     * Returns the value of open.
     * @return Returns the value of open.
     */
    private boolean isOpen() {
        return open;
    }

    /**
     * Returns the value of locked.
     * @return Returns the value of locked.
     */
    private boolean isLocked() {
        return locked;
    }

    /**
     * Returns the value of trapped.
     * @return Returns the value of trapped.
     */
    private boolean isTrapped() {
        return trapped;
    }

    /**
     * Returns the descrtiption of the trap.
     * @return Returns the descrtiption of the trap.
     */
    private String getTrapDescription() {
        return trap.getDescription();
    }

    /**
     * Identifies a spaces with the door.
     * This method should also call the addDoor method from Space.
     * @param space Attaches this space to the door.
     */
    public void addSpace(Space space) {
        spaces.add(space);
        space.addDoor(this);
    }

    /**
     * Identifies two spaces with the door.
     * This method should also call the addDoor method from Space.
     * @param spaceOne Attaches this space to the door.
     * @param spaceTwo Attaches this space to the door.
     */
    public void addSpaces(Space spaceOne, Space spaceTwo) {
        spaces.add(spaceOne);
        spaces.add(spaceTwo);
        spaceOne.addDoor(this);
        spaceTwo.addDoor(this);
    }

    /**
     * Returns the spaces that are connected by the door.
     * @return The arrayList containing the attached spaces.
     */
    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    /**
     * Returns description of the door depending on the flags set beforehand.
     * @return String of the description
     */
    public String getDescription() {
        if (isArchway()) {
            description = "The door is an archway.\n";
        } else {
            if (isOpen()) {
                description = "The door is open.\n";
            } else {
                description = "The door is closed.\n";
            }
            if (isLocked()) {
                description = description + "The door is locked.\n";
            } else {
                description = description + "The door is unlocked.\n";
            }
            if (isTrapped()) {
                description = description + "The door is trapped with a " + getTrapDescription() + ".\n";
            } else {
                description = description + "The door is not trapped.\n";
            }
        }
        return description;
    }
}
