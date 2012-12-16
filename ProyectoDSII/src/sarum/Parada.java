package sarum;


import java.awt.Point;



/**
 *
 * @author Wilmar
 */
public class Parada
{
    int tipo, indice;
    String nombre;
    Point coordenanda;

    public Parada()
    {
        coordenanda = new Point();
    }

    public void setTipo(int tipo)
    {
        this.tipo=tipo;
    }

    public int getTipo()
    {
        return this.tipo;
    }

    public void setIndice(int indice)
    {
        this.indice=indice;
    }

    public int getIndice()
    {
        return this.indice;
    }

    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setCoordenada(int x, int y)
    {
        this.coordenanda.x=x;
        this.coordenanda.y=y;
    }

    public Point getCoordenada()
    {
        return this.coordenanda;
    }
}
