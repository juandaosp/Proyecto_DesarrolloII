package sarum;

public class HallarRutas {

int ori;
int des;
int matrizAdya [][];

public void traerDatos(){

OrigenDestino ODs= new OrigenDestino();
ori=ODs.getOrigen();
des=ODs.getDestino();

Funcional Fun = new Funcional();

 matrizAdya = Fun.retornatMatrizAdyacencia();

 HallarRutaQueParteDelOrigen();
}



public void HallarRutaQueParteDelOrigen(){

    System.out.print("la estacion inicio es : "+ori+" tiene que ir hasta : "+des);


}



}
