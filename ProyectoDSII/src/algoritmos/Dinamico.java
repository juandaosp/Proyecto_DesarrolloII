/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public final class Dinamico {

	private int nnodos;

	private int nodos[][][];

	private String nombres[];
       public static String rutaDina[] ;

      public  Dinamico() {
		
	}
        
        
	Dinamico(int n) {
            
            //System.out.println("el n vale :"+n);
		this.nnodos = n;
		this.nodos = new int[n][n][2];
		this.nombres = new String[n];
                
               //  System.out.println("el n vale :"+nnodos+" "+nodos.length+" "+nombres.length);
                
	}

    

	public void ingresarArco(int n1, int n2, int peso) {
		this.nodos[n1][n2][0] = peso;
		this.nodos[n2][n1][0] = peso;
		this.nodos[n1][n2][1] = n1;
		this.nodos[n2][n1][1] = n2;
	}

	public void ingresarNombre(int nodo, String letra) {
		this.nombres[nodo] = letra;
	}

	public void calcular() {
		int i, j, k;
		for (i = 0; i < this.nnodos; i++) {
			for (j = 0; j < this.nnodos; j++) {
				for (k = 0; k < this.nnodos; k++) {
					if (this.nodos[i][k][0] + this.nodos[k][j][0] < this.nodos[i][j][0]) {
						this.nodos[i][j][0] = this.nodos[i][k][0]
								+ this.nodos[k][j][0];
						this.nodos[i][j][1] = k;
					}
				}
			}
		}
	}

	public int pesominimo(int org, int des) {
		return this.nodos[org][des][0];
	}

	public String caminocorto(int org, int des) {
            
		String cam;
                
		if (org == des) {
                    
			cam = nombres[org];
                        
		} else {
                    
			cam = caminocorto(org, this.nodos[org][des][1]) + ","+ nombres[des];
		}
		return cam;
	}

	public String getNombre(int nodo) {
		return this.nombres[nodo];
	}
        
int matriz[][];

	public  void iniciar(int mat[][], int a,  int b, Vector vector) throws IOException {
		Dinamico g;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
		String temp;
                
		int res;
                
                matriz=mat;
               
		res = matriz.length;

		g = new Dinamico(res);
                
               // imprimirMatriz(mat);
                
		for (int i = 0; i < res-1; i++) {               
			g.ingresarNombre( i+1 , String.valueOf(vector.elementAt(i)) );
                       //System.out.println("i vale : "+(i+1)+" la estacione es : "+String.valueOf(vector.elementAt(i)) );
		}   
		for (int i = 0; i < res; i++) {
                    
			for (int j = 0; j < res; j++) {
                            
				if (i < j) {
					//System.out.println("El nodo " + g.getNombre(i)
						//	+ " esta conectado con el nodo " + g.getNombre(j)
						//	+ " (s/n)\n");
					//temp = br.readLine();
					//if (temp.charAt(0) == 's') {
                                    
                                        if(matriz[i][j] != 0){
                                            
						int peso;
                                                
						//System.out.println("Cual es el peso del arco:\n");
						//temp = br.readLine();
                                                
						peso = matriz[i][j];
                                                
						g.ingresarArco(i, j, peso);
                                                
					} else{
                                            g.ingresarArco(i, j, 110000);
					}
				}
			}
		}
		g.calcular();
		/*for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				if (i > j) {*/
					System.out.println("El camino mas corto entre los nodos:"
							+ g.getNombre(a) + "-" + g.getNombre(b) + " es: \n"
							+ g.caminocorto(a, b) + " y su peso es: "
							+ g.pesominimo(a, b));
                                       rutaDina =g.caminocorto(a, b).split(","); 
			/*	}
			}
		}*/
	}
        
        public void imprimirMatriz(int mat[][]){

    for(int h=0; h<mat.length; h++){

      for(int k=0;k<mat.length; k++){
          
          System.out.print(mat[h][k]);

      }
      System.out.print("\n");
    }

    }
}
