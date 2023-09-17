package Game;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle extends JPanel{
    
    Rectangle obj;
    int angle;

    BufferedImage image;
    Image bottom;
    BufferedImage image2;
    Image middle;

    public Obstacle(Rectangle obj){
        this.obj = obj;

        try {
            image = ImageIO.read(new File("src/Game/bottom.png"));
            bottom = image.getScaledInstance(image.getWidth()*4,image.getHeight()*4,Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }
        try {
            image2 = ImageIO.read(new File("src/Game/middle.png"));
            middle = image2.getScaledInstance(image2.getWidth()*4,image2.getHeight()*4,Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }

        initialObj();
        createPolygon(300, 100);
        rotatePolygon(60,400, 300);

    }

    private void initialObj(){
        
        repaint();
    }

    Polygon p;
    Polygon k;

    int longPoint;
    int sizePoint;

    int[] x;
    int[] y;

    public void createPolygon(int longer, int size){

        this.longPoint = longer/2;
        this.sizePoint = size/2;

        this.x = new int[]{-longPoint,-longPoint,+longPoint,+longPoint};
        this.y = new int[]{-sizePoint,+sizePoint,+sizePoint,-sizePoint};

        this.p = new Polygon(x,y,4);
        this.k = new Polygon(x,y,4);
    }

    int offx;
    int offy;

    public void rotatePolygon(double angle, int offsetx, int offsety){

        int[] newX = new int[4];
        int[] newY = new int[4];

        double radiant = Math.toRadians(angle);

        for(int index = 0; index < 4; index++){
            newX[index] = (int) ((Math.cos(radiant)*x[index]) - (Math.sin(radiant)*y[index]));
            newY[index] = (int) ((Math.sin(radiant)*x[index]) + (Math.cos(radiant)*y[index]));
        }

        // k.xpoints = newX;
        // k.ypoints = newY;

        k = new Polygon(newX,newY,4);

        k.translate(offsetx, offsety);
        
    } 
    

    public void paint(Graphics gc){
        super.repaint();

        Graphics2D g = (Graphics2D)gc.create();

        // g.drawPolygon(p);
        g.drawPolygon(k);
        
        if(obj.contains(k.xpoints[0],k.ypoints[0]) || obj.contains(k.xpoints[1],k.ypoints[1])
        || obj.contains(k.xpoints[2],k.ypoints[2]) || obj.contains(k.xpoints[3],k.ypoints[3])
        ){
            g.setColor(Color.red);
        }else if(k.intersects(obj)){
            g.setColor(Color.blue);
        }

        g.drawRect((int)obj.getX(),(int)obj.getY(),(int)obj.getWidth(),(int)obj.getHeight());
        
        try {
            
            g.drawImage(bottom,400,400,this);

        } catch (Exception e) {
           
        }
        try {
            
            g.drawImage(middle,400,400+(16*4),this);
            g.drawImage(middle,400,400+(32*4),this);
        } catch (Exception e) {
           
        }

    }

}
