package Pong;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;
import javax.swing.JFrame;


public class Pong extends JFrame implements KeyListener {

    private int windowWidth = 800;
    private int windowHeight = 600;
    private Pelota pelota;
    private Kong kong;
    private Godzilla godzilla;
    private static final String RUTA = "src/Pong/";

    private int key = 0;
    private long goal;
    private long tiempoDemora = 8;

    private int MalasG;
    private int BuenasG;
    private int MalasK;
    private int BuenasK;


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

        AbrirObjetos();

        while(true) {
            pelota();
            sleep();
            fin();
        }

    }
    private void AbrirObjetos() {

        pelota = new Pelota(windowWidth/2, windowHeight/2, 5, -5);
        kong = new Kong(windowHeight/2, 80);
        godzilla = new Godzilla(windowHeight/2, 80);
    }

    private void pelota() {

        pelota.x = pelota.x + pelota.veloX;
        pelota.y = pelota.y + pelota.veloY;

        Colision();

        if(pelota.x <= 0){
            pelota.veloX = -pelota.veloX;
            MalasG++;
        }

        if(pelota.x >= windowWidth-40){
            pelota.veloX = -pelota.veloX;
            MalasK++;
        }

        if(pelota.y <= 20 || pelota.y >= (windowHeight - 40))
            pelota.veloY = -pelota.veloY;

        dibujoPantalla();
    }

    private void Colision(){
        if ( (pelota.x <= 75 && pelota.x >= 60) && pelota.y > godzilla.y && pelota.y < godzilla.y + godzilla.alto)
        {
            if (pelota.veloX < 0)
                BuenasG++;

            pelota.veloX = -pelota.veloX;
        }

        if ( (pelota.x >= 695 && pelota.x <= 710) && pelota.y > kong.y && pelota.y < kong.y + kong.alto)
        {
            if (pelota.veloX > 0)
                BuenasK++;

            pelota.veloX = -pelota.veloX;
        }
    }

    private void dibujoPantalla() {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {
            g = bf.getDrawGraphics();

            Toolkit t=Toolkit.getDefaultToolkit();
            Image i=t.getImage(RUTA+"fondo.jpeg");
            g.drawImage(i, 0,0,this);


            muestroPuntos(g);
            DibujoPelota(g);
            DibujoKong(g);
            DibujoGodzilla(g);

        } finally {
            g.dispose();
        }
        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }

    private void DibujoPelota(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(pelota.x, pelota.y, 20, 20);
    }

    private void DibujoKong(Graphics g) {

        switch (key){
            case KeyEvent.VK_UP:
                if (kong.y>23)
                    kong.y=kong.y-6;
                break;
            case KeyEvent.VK_DOWN:
                if (kong.y<windowHeight-78)
                    kong.y=kong.y+6;
                break;

        }

        g.setColor(Color.BLUE);
        g.fillRect(710, kong.y, 15, kong.alto);
    }

    private void DibujoGodzilla(Graphics g) {

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

        g.setColor(Color.GREEN);
        g.fillRect(75, godzilla.y, 15, godzilla.alto);
    }

    private void muestroPuntos(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Paradas: " + BuenasK, 700, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Falladas: " + MalasK, 700, 70);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Paradas: " + BuenasG, 20, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Falladas: " + MalasG, 20, 70);
    }

    private void fin(){
        if (MalasK >=15){
            JOptionPane.showMessageDialog(null, "Ha ganado Godzilla");
            System.exit(0);
        }
        if (MalasG >=15){
            JOptionPane.showMessageDialog(null, "Ha ganado King Kong");
            System.exit(0);
        }
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
