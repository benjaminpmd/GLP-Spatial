package model.rocket;

import javax.swing.*;

/**
 * Class that contains informations about the stage
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 - thrust me (1.0.0)
 * @see RocketEngine
 * @see Tank
 * @since 11.02.22
 */
public class Stage {

    private double emptyWeight;
    private int engineNb;
    private RocketEngine engine;
    private Tank tank;
    private Icon icon;

    /**
     * Constructor of the Stage object
     *
     * @param engineNb the number of engine that power the stage
     * @param engine   the engine type that are used {@link RocketEngine}
     * @param tank     the tank that contains the propellant {@link Tank}
     */
    public Stage(int engineNb, RocketEngine engine, Tank tank) {
        this.engineNb = engineNb;
        this.engine = engine;
        this.tank = tank;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "emptyWeight=" + emptyWeight +
                ", engineNb=" + engineNb +
                ", engine=" + engine +
                ", tank=" + tank +
                ", icon=" + icon +
                '}';
    }

    public double getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(double emptyWeight) {
        this.emptyWeight = emptyWeight;
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

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
