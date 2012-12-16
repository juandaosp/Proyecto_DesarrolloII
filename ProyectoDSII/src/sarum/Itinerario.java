package sarum;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wilmar
 */
public class Itinerario
{
    int numero, sumaTiempos;
    Vector<SemiRecorrido> recorrido;
    String tipoRuta;

    public Itinerario()
    {
        recorrido = new Vector<SemiRecorrido>();
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }
    public int getNumero()
    {
        return this.numero;
    }

    public void setSumaTiempo() {
        for(SemiRecorrido semiRecorrido : recorrido)
            this.sumaTiempos+=semiRecorrido.getTiempoRecorrido();

    }

    public int getSumaTiempo() {
                return this.sumaTiempos;
    }

    public void setRecorrido(Vector<SemiRecorrido> recorrido)
    {
        this.recorrido=recorrido;
    }
    public Vector<SemiRecorrido> getRecorrido()
    {
        return this.recorrido;
    }

    public void setTipoRuta(String tipoRuta)
    {
        this.tipoRuta=tipoRuta;
    }
    public String getTipoRuta()
    {
        return this.tipoRuta;
    }

    public String imprimir()
    {
        String texto="";
        for(SemiRecorrido semiRecorrido : recorrido)
            texto+=semiRecorrido.imprimir()+"\n";
//
//            {
//                salida.println("Linea No. "+numeroDeLinea+" Puntos de entrada: "+lineaLeiada+" \n");
//                if(!semiRecorrido.getNombreRuta().equals(rutaActual) && primeraParte){
//                    salida.println("Itinerario: "+ (num++)+"\n");
//                    //JOptionPane.showMessageDialog(null, "Imprime "+primeraParte);
//                    primeraParte=false;
//                }
//                else{
//                    primeraParte=true;
//                    //JOptionPane.showMessageDialog(null, "No imprime "+primeraParte);
//                }
//
//
//                salida.println(semiRecorrido.imprimir());
//                rutaActual=semiRecorrido.getNombreRuta();
//            }
//            SemiRecorrido.texto+="\n";
//            salida.close();
//
//        } catch (IOException ex) {
//            Logger.getLogger(Itinerario.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return texto;
    }

}
