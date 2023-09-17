package Game;

import javax.swing.JPanel;
import java.awt.*;

public class PanelLines extends JPanel{
    
    Color background = Color.white;

    Parabolic parabolic;

    public PanelLines(Parabolic p){

        this.parabolic = p;
    }

    int a;
    int b;
    int offsetx = 0;

    public void setLimits(int a, int b){
        this.a = a;
        this.b = b;
    }

    public void paint(Graphics gc){

        super.paint(gc);
        Graphics2D g = (Graphics2D)gc.create();
        g.setColor(background);
        g.setColor(new Color(224, 224, 235));
        // g.setRenderingHint(
        //     RenderingHints.KEY_ANTIALIASING,
        //     RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(7f));

        for(double x = a; x <= b; x++){
            // System.out.println(x);
            g.drawLine((int)(offsetx+x),(int)parabolic.equationParabolic(x),(int)(offsetx+x),(int)parabolic.equationParabolic(x));
        }

     

    }
    

}
