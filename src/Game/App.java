package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends Canvas implements Runnable{

    private static JFrame window;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static int ups;
    private static int fps;

    Parabolic p;
    PanelTrack paneltrack;
    PanelLines panel;

    Rectangle rectangle;
    Obstacle panelObstacle;

    public App(){
        initWindow();
    }

    private void initWindow(){

        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        window = new JFrame("Flappy");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());

        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){}

            @Override
            public void keyPressed(KeyEvent e) {
                moveSprite(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        rectangle = new Rectangle(0,0,112,300);
        // rectangle.translate(400-(300/2),300-(100/2));
        rectangle.translate(408,412);

        

        p = new Parabolic(100,500);
        panelObstacle = new Obstacle(rectangle);
        panel = new PanelLines(p);
        paneltrack = new PanelTrack(p);

        window.add(panelObstacle);
        
        panelObstacle.setBounds(0,0,WIDTH,HEIGHT);
        panelObstacle.setOpaque(false);
        panelObstacle.createPolygon(28, 28);

        window.add(paneltrack);

        paneltrack.setObstacleTrack(panelObstacle);
        paneltrack.setBounds(0,0, WIDTH, HEIGHT);
        paneltrack.setOpaque(false);
        // paneltrack.offsetx = 300;

        window.add(panel);
        p.equationParabolic(0);
        p.equationLineDerived(14, 14, 8.4);

        System.out.println("\n Angle: " + p.getAngle());
        System.out.println(" Velocity: " + p.getInitialV());

        panel.setLimits(0, 200);
        panel.setBounds(0,0, WIDTH, HEIGHT);
        panel.setOpaque(false);
        // panel.offsetx = 300;
        
        JPanel pane = new JPanel();

        pane.setBackground(new Color(133, 133, 173));
        pane.setBounds(0, 0, WIDTH, HEIGHT);
        window.add(pane);

        panel.repaint();
        
        window.add(this,BorderLayout.CENTER);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        setOffsetX(-200);

    }

    int offsetx = 0;

    private void setOffsetX(int x){
        
        offsetx = x;
        paneltrack.offsetx = (int)((WIDTH/2)-p.h)+x;
        panel.offsetx = (int)((WIDTH/2)-p.h)+x;

    }

    private static Thread thread;

    private void startThreah(){

        thread = new Thread(this,"Thread 1");
        thread.start();

    }

    private void render(){
        
        fps++;

    }

    int cont = 0;
    int speed = 2;

    private void logic(){

        if(cont >= (p.h*2)+50){
            cont = 0;
        }

        paneltrack.setPoint(cont, 150);
        paneltrack.repaint();

        panelObstacle.repaint();

        cont+=speed;

        ups++;

    }

    @Override
    public void run() {
        
        final int NANOSEC = 1000000000;
        final byte UPERSEC = 60;
        final double NAN_PER_UPER = NANOSEC / UPERSEC;

        long update_time= System.nanoTime();

        long time;
        double delta_time = 0;

        long ref_fps = System.nanoTime();

        while(true){
            
            long current_time = System.nanoTime();

            time = current_time - update_time;

            update_time = current_time;

            delta_time += time / NAN_PER_UPER;

            while(delta_time >= 1){
                
                logic();
                delta_time--;
            }

            render();
            
            if(current_time - ref_fps >= NANOSEC){

                ref_fps = current_time;
                window.setTitle("UPS: " + ups + "    FPS: " + fps);
                ups = 0;
                fps = 0;
            }

        }
    }

    private void moveSprite(KeyEvent key){

        if(key.getKeyCode() == KeyEvent.VK_SPACE){
            cont = 0;
        }
        if(key.getKeyCode() == KeyEvent.VK_RIGHT){
            speed++;
            System.out.print("\n Speed: " + speed);
        }
        if(key.getKeyCode() == KeyEvent.VK_LEFT){

            if(speed != 0){
                speed--;
            }
            
            System.out.print("\n Speed: " + speed);
        }

        if(key.getKeyCode() == KeyEvent.VK_UP){

            setOffsetX(offsetx+10);
            
        }
        if(key.getKeyCode() == KeyEvent.VK_DOWN){
            
            setOffsetX(offsetx-10);
           
        }

        if(key.getKeyCode() == KeyEvent.VK_L){
            
            p.changeHK(p.h+10,p.k);
            panel.b += 20;
            setOffsetX(offsetx);
           
        }
        if(key.getKeyCode() == KeyEvent.VK_J){
            
            p.changeHK(p.h-10,p.k);
            panel.b -= 20;
            setOffsetX(offsetx);
           
        }
        if(key.getKeyCode() == KeyEvent.VK_I){
            
            p.changeHK(p.h,p.k-10);
           
        }
        if(key.getKeyCode() == KeyEvent.VK_K){
            
            p.changeHK(p.h,p.k+10);
           
        }

    }

    public static void main(String[] args) throws Exception {

        System.out.println("Hello, World!");
        App app = new App();
        app.startThreah();
        
        // new WindowScene(800,600);
    }

}
