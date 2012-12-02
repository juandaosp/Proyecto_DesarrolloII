package sarum;


import javax.swing.JOptionPane;



/**
 *
 * @author Wilmar
 */
public class SemiRecorrido
{
    int indParadaAscenso, indParadaDescenso;
    String nombreRuta;
    int numero, tiempoRecorrido;
    public static String texto;

     public void setNumero(int numero)
    {
        this.numero = numero;
    }
    public int getNumero()
    {
        return this.numero;
    }

    public void setParadaAscenso(int paradaAscenso)
    {
        this.indParadaAscenso = paradaAscenso;
    }
    public int getParaAscenso()
    {
        return this.indParadaAscenso;
    }

    public void setParadaDescenso(int paradaDescenso)
    {
        this.indParadaDescenso = paradaDescenso;
    }
    public int getParaDecenso()
    {
        return this.indParadaDescenso;
    }

    public void setNombreRuta(String nombreRuta)
    {
        this.nombreRuta=nombreRuta;
    }
    public String getNombreRuta()
    {
        return this.nombreRuta;
    }

    public void setTiempoRecorrido(int tiempo)
    {
        this.tiempoRecorrido = tiempo;
    }
    public int getTiempoRecorrido()
    {
        return this.tiempoRecorrido;
    }
    public String imprimir()
    {
        texto="";
        //texto="Semirrecorrido: "+numero+" -Tiempo: "+tiempoRecorrido+"\n";
        texto+=indParadaAscenso+"#"+nombreRuta+"#"+indParadaDescenso;

        return texto;
    }
}
