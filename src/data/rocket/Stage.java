package data.rocket;

import data.MobileElement;

/**
 * Class that contains information about the stage
 *
 * @author Alice M, Benjamin P
 * @version 22.03.06
 * @see RocketEngine
 * @see Tank
 * @since 11.02.22
 */
public class Stage extends MobileElement {

    private Tank tank;
    private RocketEngine engine;
    private int engineNb;

    public Stage(Tank tank, RocketEngine engine, int engineNb) {
        super();
        this.engineNb = engineNb;
        this.engine = engine;
        this.tank = tank;
    }

    public Stage(Tank tank, RocketEngine engine, int engineNb, double mass) {
        super(mass);
        this.tank = tank;
        this.engine = engine;
        this.engineNb = engineNb;

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
}
