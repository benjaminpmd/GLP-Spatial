package data.rocket;

/**
 * Class that contains information about the rocket engine.
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 (1.0.0)
 * @since 11.02.22
 */
public class RocketEngine {

    // kg.s^-1
    private double thrust;
    // kg/s
    private double propellantFlow;
    // kg
    private double weight;
    // s
    private int isp;

    /**
     * Constructor of RocketEngine.
     *
     * @param thrust         the thrust of the engine in Kg/s.
     * @param propellantFlow the amont of fuel in kg used per second.
     * @param weight         the weight of the engine.
     * @param isp            the specific impulse of the engine.
     */
    public RocketEngine(int isp, double thrust, double propellantFlow, double weight) {
        this.isp = isp;
        this.thrust = thrust;
        this.propellantFlow = propellantFlow;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RocketEngine{" +
                "thrust=" + thrust +
                ", propellantFlow=" + propellantFlow +
                ", weight=" + weight +
                ", isp=" + isp +
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIsp() {
        return isp;
    }

    public void setIsp(int isp) {
        this.isp = isp;
    }
}
