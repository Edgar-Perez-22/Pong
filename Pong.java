package Pong;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;



public class Pong extends JFrame implements KeyListener {

    private int windowWidth = 800;
    private int windowHeight = 600;
    private Pelota pelota;
    private Kong kong;
    private Godzilla godzilla;

    private int key=0;
    private long goal;
    private long tiempoDemora=8;

    private int Buenas;
    private int Malas;


    public static void main(String[] args) {
        new Pong();
    }
    public Pong() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setResizable(false);
        this.setLocation(100, 100);
        this.setVisible(true);

        this.createBufferStrategy(2);

        this.addKeyListener(this);

        inicializoObjetos();

        while(true) {
            pelota();
            sleep();
        }

    }
    private void inicializoObjetos() {

        pelota = new Pelota(windowWidth/2, windowHeight/2, 5, -5);
        kong = new Kong(windowHeight/2, 80);
        godzilla = new Godzilla(windowHeight/2, 80);
    }

    private void pelota() {

        pelota.x = pelota.x + pelota.veloX;
        pelota.y = pelota.y + pelota.veloY;

        chequearColision();

        if(pelota.x <= 0 || pelota.x >= windowWidth-40){
            pelota.veloX = -pelota.veloX;
            Malas++;
        }

        if(pelota.y <= 20 || pelota.y >= (windowHeight - 40))
            pelota.veloY = -pelota.veloY;

        dibujoPantalla();
    }

    private void chequearColision(){
        if ( (pelota.x <= 75 && pelota.x >= 60) && pelota.y > godzilla.y && pelota.y < godzilla.y + godzilla.alto)
        {
            if (pelota.veloX < 0)
                Buenas++;

            pelota.veloX = -pelota.veloX;
        }

        if ( (pelota.x >= 695 && pelota.x <= 710) && pelota.y > kong.y && pelota.y < kong.y + kong.alto)
        {
            if (pelota.veloX > 0)
                Buenas++;

            pelota.veloX = -pelota.veloX;
        }
    }

    private void dibujoPantalla() {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {
            g = bf.getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, windowWidth, windowHeight);

            muestroPuntos(g);
            dibujoPelota(g);
            dibujoPaletas1(g);
            dibujoPaletas2(g);

        } finally {
            g.dispose();
        }
        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }

    private void dibujoPelota(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(pelota.x, pelota.y, 20, 20);
    }

    private void dibujoPaletas1(Graphics g) {

        switch (key){
            case KeyEvent.VK_UP:
                if (kong.y>23)
                    kong.y=kong.y-6;
                break;
            case KeyEvent.VK_DOWN:
                if (kong.y<windowHeight-78)
                    kong.y=kong.y+6;
                break;
            case KeyEvent.VK_X:
                System.exit(0);

        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(710, kong.y, 15, kong.alto);
    }

    private void dibujoPaletas2(Graphics g) {

        switch (key){
            case KeyEvent.VK_W:
                if (godzilla.y>23)
                    godzilla.y=godzilla.y-6;
                break;
            case KeyEvent.VK_S:
                if (godzilla.y<windowHeight-78)
                    godzilla.y=godzilla.y+6;
                break;
        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(75, godzilla.y, 15, godzilla.alto);
    }

    private void muestroPuntos(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Buenas: " + Buenas, 20, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Malas: " + Malas, 20, 70);
    }

    private void sleep(){
        goal = ( System.currentTimeMillis() + tiempoDemora );
        while(System.currentTimeMillis() < goal) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        key = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

    @Override
    public void keyTyped(KeyEvent e){

    }
}
