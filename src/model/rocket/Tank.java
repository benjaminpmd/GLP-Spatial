package model.rocket;

/**
 * Class that contains informations about the tank
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 - thrust me (1.0.0)
 * @see Propellant
 * @since 11.02.22
 */
public class Tank {

    private Propellant propellant;
    // maximum propellant that can be stored in the tank, the unit is a volume (L)
    private int capacity;
    // the remaining volume of fuel in the tank (L)
    private int remainingPropellant;

    public Tank(Propellant propellant, int capacity, int remainingPropellant) {
        this.propellant = propellant;
        this.capacity = capacity;
        this.remainingPropellant = remainingPropellant;
    }

    public Tank(Propellant propellant, int capacity) {
        this(propellant, capacity, capacity);
    }

    @Override
    public String toString() {
        return "Tank{" +
                "propellant=" + propellant +
                ", capacity=" + capacity +
                ", remainingPropellant=" + remainingPropellant +
                '}';
    }

    public Propellant getPropellant() {
        return propellant;
    }

    public void setPropellant(Propellant propellant) {
        this.propellant = propellant;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRemainingPropellant() {
        return remainingPropellant;
    }

    public void setRemainingPropellant(int remainingPropellant) {
        this.remainingPropellant = remainingPropellant;
    }
}
