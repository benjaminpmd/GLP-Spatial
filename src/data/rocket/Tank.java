package data.rocket;

/**
 * Class that contains information about the propellant tank.
 *
 * @author Alice M, Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see data.rocket.Propellant
 * @since 22.02.11
 */
public class Tank {

    private Propellant propellant;
    // maximum propellant that can be stored in the tank, the unit is a volume (L)
    private int capacity;
    // the remaining volume of fuel in the tank (kg)
    private double remainingPropellant;

    /**
     * Constructor of the Tank.
     */
    public Tank(Propellant propellant, int capacity) {
        this.propellant = propellant;
        this.capacity = capacity;
        this.remainingPropellant = (int) (capacity * propellant.getDensity());
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

    public double getRemainingPropellant() {
        return remainingPropellant;
    }

    public void setRemainingPropellant(double remainingPropellant) {
        this.remainingPropellant = remainingPropellant;
    }
}
