/**
 * Read the README.md for more details.
 * @author Julian Lau
 */
package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public final class Generator {
    /**
     * Stores all the chambers.
     */
    private ArrayList<Chamber> chamberList;
    /**
     * Stores all the doors.
     */
    private ArrayList<Door> doorList;
    /**
     * Stores the list of all the connecting passages.
     */
    private ArrayList<Passage> passageList;
    /**
     * Stores all the available chabmers.
     */
    private ArrayList<Chamber> availableChambers;
    /**
     * Stores the connection between doors and chambers.
     */
    private HashMap<Door, Chamber> doorToChamber;
    /**
     * Stores the connection between doors and doors.
     */
    private HashMap<Door, Door> doorToDoor;
    /**
     * Stores the connection between doors and a list of chambers.
     */
    private HashMap<Door, ArrayList<Chamber>> doorToChambers;

    /**
     * Constructor for checkstyle.
     */
    public Generator() {
        chamberList = new ArrayList<Chamber>();
        doorList = new ArrayList<Door>();
        passageList = new ArrayList<Passage>();
        availableChambers = new ArrayList<Chamber>();
        doorToChamber = new HashMap<Door, Chamber>();
        doorToDoor = new HashMap<Door, Door>();
        doorToChambers = new HashMap<Door, ArrayList<Chamber>>();
    }

    /**
     *  This method creates as many chambers as required.
     *  @param count Number of chambers to be made.
     */
    public void createChambers(int count) {
        Chamber tempC;
        for (int i = 0; i < count; ++i) {
            tempC = new Chamber();
            chamberList.add(tempC);
        }
    }

    /**
     * This method returns an array list of all the chambers created.
     * @return The list of chambers.
     */
    public ArrayList<Chamber> getChamberList() {
        return chamberList;
    }

    /**
     * This method adds all the chambers to a list of available chambers.
     */
    public void setAvailableChambers() {
        for (Chamber tempC : chamberList) {
            for (int i = 0; i < tempC.getDoorList().size(); ++i) {
                availableChambers.add(tempC);
            }
        }
        Collections.shuffle(availableChambers);
    }

    /**
     * This method assigns a door to a chamber, filling up doorToChamber instance variable.
     */
    public void assignChambers() {
        int remaining = 1;
        while (remaining != 0) {
            for (Chamber tempC : chamberList) {
                for (int i = 0; i < tempC.getDoorList().size(); ++i) {
                    int k = 0;
                    for (Chamber tempCC : availableChambers) {
                        if (tempC != tempCC) {
                            doorList.add(tempC.getDoorList().get(i));
                            doorToChamber.put(tempC.getDoorList().get(i), tempCC);
                            availableChambers.remove(k);
                            break;
                        }
                        ++k;
                    }
                }
            }
            remaining = availableChambers.size();
        }
    }

    /**
     * This method creates all the passages.
     */
    public void createPassages() {
        Passage p;
        for (int i = 0; i < doorList.size() / 2; ++i) {
            p = new Passage();
            p.createDoors();
            p.addPassageSection(new PassageSection(1));
            p.addPassageSection(new PassageSection(1));
            passageList.add(p);
        }
    }

    /**
     * Returns the number of passages.
     * @return The list of passages.
     */
    public ArrayList<Passage> getPassageList() {
        return passageList;
    }

    /**
     * This method displays all the linked doors.
     */
    public void displayLinks() {
        System.out.println("***** LINKED DOORS *****");
        int i = 1;
        for (Chamber tempC : chamberList) {
            System.out.println("Chamber " + i + ":");
            ++i;
            int k = 1;
            for (Door tempD : tempC.getDoorList()) {
                System.out.print("Door " + k + ": ");
                for (int j = 0; j < 5; ++j) {
                    if (doorToChamber.get(tempD) == chamberList.get(j)) {
                        System.out.println("C" + (j + 1));
                    }
                }
                ++k;
            }
            System.out.println(""); //Formatting
        }
    }
}
