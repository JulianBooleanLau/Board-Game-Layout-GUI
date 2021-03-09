package dungeon;

public abstract class Space {

    /**
     * Gets the description of the space.
     * @return String of the description.
     */
    public abstract  String getDescription();

    /**
     * Sets the door in the space.
     * @param theDoor Sets the door to this.
     */
    public abstract void addDoor(Door theDoor);

}
