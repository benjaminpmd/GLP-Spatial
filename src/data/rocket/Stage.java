package data.rocket;

import data.MobileElement;

/**
 * Class that contains information about the stage.
 *
 * @author Alice M, Benjamin P
 * @version 22.03.06 (1.0.0)
 * @see data.rocket.RocketEngine
 * @see data.rocket.Tank
 * @since 11.02.22
 */
public class Stage extends MobileElement {

    private Tank tank;
    private RocketEngine engine;
    private int engineNb;
    private boolean isFiring;

    /**
     * Constructor of the Stage.
     */
    public Stage(Tank tank, RocketEngine engine, int engineNb, double mass) {
        super(mass);
        this.tank = tank;
        this.engine = engine;
        this.engineNb = engineNb;
        isFiring = false;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "tank=" + tank +
                ", engine=" + engine +
                ", engineNb=" + engineNb +
                "} " + super.toString();
    }

    public int getEngineNb() {
        return engineNb;
    }

    public void setEngineNb(int engineNb) {
        this.engineNb = engineNb;
    }

    public RocketEngine getEngine() {
        return engine;
    }

    public void setEngine(RocketEngine engine) {
        this.engine = engine;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
    }
}
