package data.rocket;

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
    private double capacity;
    // the remaining volume of fuel in the tank (kg)
    private double remainingPropellant;

    public Tank(Propellant propellant, int capacity) {
        this.propellant = propellant;
        this.capacity = capacity * propellant.getDensity();
        this.remainingPropellant = this.capacity;
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

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getRemainingPropellant() {
        return remainingPropellant;
    }

    public void setRemainingPropellant(double remainingPropellant) {
        this.remainingPropellant = remainingPropellant;
    }
}
