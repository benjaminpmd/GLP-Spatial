package engine.process.calculations;

public class Calculation {

    public double degreeToRadian(double angle) {
        if ((angle >= 0) && (angle <= 360)) {
            return ((angle * Math.PI) / 180);
        }
        else return 0;
    }
}
