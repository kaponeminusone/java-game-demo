package Game;

public class Parabolic {

    double angle;
    int mx;
    int my;
    double vi; 
    
    double h;
    double k;

    static double g = 100;

    public Parabolic(double h,double k){

        this.h = h;
        this.k = k;

        this.angle = getAngle();
        this.vi = getInitialV();

    }

    public void changeHK(double h, double k){

        this.h = h;
        this.k = k;

        this.angle = getAngle();

    }

    public double equationParabolic(double x){

        // System.out.print("\n " + (-k/Math.pow(h,2)) + "x^2 + " + ((2*k)/h) + "x");
        return -k/Math.pow(h,2)*Math.pow(x, 2) + (2*k*x)/h;
    }

    public double equationLineDerived(double x, double x0, double y0){

        double slope = equationParabolicDerivative(x);

        // System.out.print("\n Line: " + (slope) + "x + (" + (-(slope*x0)+y0)+")");

        return slope*x + (-(slope*x0)+y0);
    }

    public double equationParabolicDerivative(double x){

        return (-2*k*x)/Math.pow(h, 2) + (2*k)/h;
    }

    public double getAngle(){

        return Math.toDegrees(Math.atan(equationParabolicDerivative(0)));
    }

    public double getInitialV(){

        return Math.sqrt((10*2)/Math.sin(Math.toRadians(2*angle)));
    }

    public double equationY(int t){

        double y = (Math.tan((Math.toRadians(angle)*t) 
                + ((g*Math.pow(t, 2))
                /(2*Math.pow(vi, 2)*Math.pow(Math.cos(Math.toRadians(angle)),2)))));

        return y;
    }
}
