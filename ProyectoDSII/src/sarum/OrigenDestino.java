

package sarum;


/*compartir los valores y devolverlos a la clase Hallar */

public class OrigenDestino {
    
    int posicionOrigen;
    int posicionDestino;

    public void setOrigen(int posO){

    posicionOrigen=posO;
    
    }

    public void setDestino(int posD){

    posicionDestino=posD;

    }

    public int getOrigen(){

        return posicionOrigen;
    }


    public int getDestino(){

        return posicionDestino;

    }

}
