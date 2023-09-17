package Game;

import javax.swing.JPanel;
import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelTrack extends JPanel {
    
    Color background = Color.white;
    
    Parabolic parabolic;

    BufferedImage image;
    Image bottom;

    public PanelTrack(Parabolic p){

        try {
            image = ImageIO.read(new File("src/Game/feather.png"));
            bottom = image.getScaledInstance(image.getWidth()*2,image.getHeight()*2,Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }

        this.parabolic = p;
    }

    Obstacle obj;

    public void setObstacleTrack(Obstacle obj){
        this.obj = obj;
    }

    int x;
    int range;
    int offsetx = 0;

    public void setPoint(int x,int range){
        this.x = x;
        this.range = range;
        repaint();
    }

    public void paint(Graphics gc){

        super.paint(gc);
        Graphics2D g = (Graphics2D)gc.create();
        g.setColor(new Color(50, 50, 78));

        g.setStroke(new BasicStroke(7f));

        // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        double slope = parabolic.equationParabolicDerivative(x);

        // System.out.print("\n Line: " + (slope) + "x + (" + (-(slope*x0)+y0)+")");

        int y1 = (int)(slope*(x-range) + (-(slope*x)+ parabolic.equationParabolic(x)));
        int y2 = (int)(slope*(x+range) + (-(slope*x)+ parabolic.equationParabolic(x)));
        
        g.drawLine(offsetx+x-range,y1 , offsetx+x+range, y2);
        // range = 150;
        // g.rotate(Math.atan(slope));
        // g.translate(400, 300);

        int yt = (int)parabolic.equationParabolic(x);

        g.setColor(new Color(255, 51, 153));
        g.rotate(Math.atan(slope), offsetx+x, yt);

        g.drawLine(offsetx+-range+x,yt,offsetx+range+x,yt);
        
        try {
            
            g.drawImage(bottom,offsetx+x-(bottom.getWidth(this)/2),yt-(bottom.getHeight(this)/2),this);

        } catch (Exception e) {
           
        }

        obj.rotatePolygon(Math.toDegrees(Math.atan(slope)),offsetx+x, yt);

    }
    

}
