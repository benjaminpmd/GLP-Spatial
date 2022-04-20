package data.rocket;

/**
 * Class that contains information about the rocket engine.
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 (1.0.0)
 * @since 11.02.22
 */
public class RocketEngine {

    // N
    private double thrust;
    // kg/s
    private double propellantFlow;
    // kg
    private double mass;
    //m.s^-1
    private int exhaustVelocity;

    /**
     * Constructor of RocketEngine.
     *
     * @param thrust          the thrust of the engine in Kg/s.
     * @param propellantFlow  the amont of fuel in kg used per second.
     * @param mass            the weight of the engine.
     * @param exhaustVelocity the ejection rate of the engine.
     */
    public RocketEngine(double thrust, double propellantFlow, double mass, int exhaustVelocity) {
        this.thrust = thrust;
        this.propellantFlow = propellantFlow;
        this.mass = mass;
        this.exhaustVelocity = exhaustVelocity;
    }

    @Override
    public String toString() {
        return "RocketEngine{" +
                "thrust=" + thrust +
                ", propellantFlow=" + propellantFlow +
                ", mass=" + mass +
                ", exhaustVelocity=" + exhaustVelocity +
                '}';
    }

    public double getThrust() {
        return thrust;
    }

    public void setThrust(int thrust) {
        this.thrust = thrust;
    }

    public double getPropellantFlow() {
        return propellantFlow;
    }

    public void setPropellantFlow(int propellantFlow) {
        this.propellantFlow = propellantFlow;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getExhaustVelocity() {
        return exhaustVelocity;
    }

    public void setExhaustVelocity(int exhaustVelocity) {
        this.exhaustVelocity = exhaustVelocity;
    }
}
