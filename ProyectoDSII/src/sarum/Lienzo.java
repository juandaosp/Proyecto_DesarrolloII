package sarum;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Lienzo extends JPanel
{

    Grafo original;
    Grafo arbol;
    String nodoMovil;
    boolean nodoFlag;//indica si se debe mover un nodo
    double xactual;
    double yactual;


    double clicX1=0;
    double clicY1=0;

    double clicX2=0;
    double clicY2=0;
    int cantidadClic=0;
HallarPuntosMasCercano puntoOrigenDestino =new HallarPuntosMasCercano();

public void setClic(){
    
cantidadClic=0;

}

//
    ///////retornar los valores x y capturados por el mouse
 /*   public double getclicY(){

        return ((clicY-400)/4);

    }
 public double getclicX(){

        return ((clicX-600)/7);
        

    }*/

public Lienzo() {

this.setSize(2000, 2000);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    Interface inter = new Interface();
                    //JOptionPane.showMessageDialog(null, "activar escucha mouse : "+Interface.activarLienzo());
          if(Interface.activarLienzo()){
                    //llenarMatrizDeParadas();
                   cantidadClic++;
                    
               if((cantidadClic==1)){

                    clicX1 = (evt.getX()-800)/9;

                    clicY1=(evt.getY()-800)/9;
                    //JOptionPane.showMessageDialog(null, "escucha del mouse : "+"valr de x : "+clicX1+"  el valor de y : "+clicY1);
               }
                if(cantidadClic==2){

                    clicX2 = (evt.getX()-800)/9;

                    clicY2=(evt.getY()-800)/9;

                    //JOptionPane.showMessageDialog(null, "escucha del mouse : "+"valr de x2 : "+clicX2+"  el valor de y2 : "+clicY2);
                  
     
                    puntoOrigenDestino.iniciarPuntos(Interface.getMatriz(), clicX1 , clicY1, clicX2 , clicY2);
                    inter.escogerDesdeMouse=true;
                    inter.xOrigen=(int)clicX1;
                    inter.yOrigen=(int)clicY1;
                    inter.xDestino=(int)clicX2;
                    inter.yDestino=(int)clicY2;
                     }
               /* else{
                    if(cantidadClic>2){
                    cantidadClic=0;
                    }
                    }*/
                     
                   LienzoMousePressed(evt);
                }
                } catch (Exception ex) {
                    Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                LienzoMouseReleased(evt);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                LienzoMouseDragged(evt);
            }
        });
      
    }
    @Override
public Dimension getPreferredSize() {
return new Dimension(2000, 2000);
}

    public void LienzoMousePressed(MouseEvent evt) {
        double xM = evt.getX(), yM = evt.getY(), x, y;
        String nom;
        boolean ycondition;
        for (int i = 0; i < original.nombres.size(); i++) {
            nom = original.nombres.get(i);
            x = original.nodos.get(nom).punto.x;
            y = original.nodos.get(nom).punto.y;
            ycondition = yM > y && yM < y + 25;
            if (((xM > x && xM < x + 25) || (xM > x + 250 && xM < x + 250 + 25)) && ycondition) {
                nodoMovil = nom;
                nodoFlag = true;
                xactual = xM - x;
                yactual = yM - y;
                break;
            }
        }
         //repaint();
    }

    public void LienzoMouseReleased(MouseEvent evt) {
        nodoFlag = false;
    }

    public void LienzoMouseDragged(MouseEvent evt) {
        //repaint();
        if (nodoFlag) {
            original.nodos.get(nodoMovil).punto.cambiar(evt.getX() - xactual, evt.getY() - yactual);
            repaint();
        }
    }

    public void repaint(Grafo o, Grafo a) {

        repaint();
    }

    @Override
    public void paint(Graphics g) {

        update(g);
    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//antialiasing

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dbi = bi.createGraphics();
        pintarOffScreen(g2dbi);

        g2d.drawImage(bi, 0, 0, this);
    }

    public void pintarOffScreen(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//antialiasing
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 2000, 2000);
        if (original != null) {
            g2d.setColor(Color.blue);
            g2d.drawString("Grafico de las estaciones con sus conexiones", 20, 20);
          // g2d.drawString("Grafico ruta a seguir", 300, 20);
            if (arbol != null) {
               // g2d.drawString("Peso Minimal = " + arbol.pesoTotal, 350, 35);
                //g2d.drawString("Peso Total = " + original.pesoTotal, 70, 35);
                dibujarLineas(g2d);
            }
            dibujarNodos(g2d);
        }

    }

    public void dibujarNodos(Graphics2D g2d) {
        String nombre;
        Nodo nodo;
        for (int i = 0; i < original.nombres.size(); i++) {
            nombre = original.nombres.get(i);
            nodo = original.nodos.get(nombre);
            nodo.punto.dibujar(g2d);
        }
    }

    public void dibujarLineas(Graphics2D g2d) {
        double xstart, ystart, xend, yend;
        Arco ar;
        Nodo inicial;
        Nodo terminal;
        for (int i = 0; i < original.aristas.size(); i++) {
            ar = original.aristas.get(i);
            inicial = original.nodos.get(ar.getInicial());
            terminal = original.nodos.get(ar.getTerminal());
            xstart = inicial.punto.x + 25 / 2;
            ystart = inicial.punto.y + 25 / 2;
            xend = terminal.punto.x + 25 / 2;
            yend = terminal.punto.y + 25 / 2;
            g2d.setColor(Color.RED);
            g2d.drawLine((int) xstart, (int) ystart, (int) xend, (int) yend);
            g2d.setColor(Color.pink);
            g2d.drawString("" + ar.getPeso(), (int) (xstart + xend) / 2, (int) (ystart + yend) / 2);
        }

      /*  for (int i = 0; i < arbol.aristas.size(); i++) {
            ar = arbol.aristas.get(i);
            
            inicial = original.nodos.get(ar.getInicial());
            terminal = original.nodos.get(ar.getTerminal());

            xstart = inicial.punto.x + 25 / 2;
            ystart = inicial.punto.y + 25 / 2;

            xend = terminal.punto.x + 25 / 2;
            yend = terminal.punto.y + 25 / 2;

            g2d.setColor(Color.RED);
            g2d.drawLine((int) xstart + 250, (int) ystart, (int) xend + 250, (int) yend);

            g2d.setColor(Color.BLACK);
            g2d.drawString("" + ar.getPeso(), (int) (xstart + xend) / 2 + 250, (int) (ystart + yend) / 2);
        }*/
    }

}
