package paquete;


import paquete.*;
import java.util.PriorityQueue;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;


class Vertex implements Comparable<Vertex>
{
    public final String name;
    
    public Edge[] adjacencias;
    
    public double minimaDistancia = Double.POSITIVE_INFINITY;
    
    public Vertex anterior;
    
    
    
    public Vertex(String argName) {
        
        name = argName; 
    
    }
    
    @Override
    public String toString() { 
        
        return name; 
    
    }
    @Override
    public int compareTo(Vertex other)
    {
        return Double.compare(minimaDistancia, other.minimaDistancia);
    }

}


class Edge
{
    public final Vertex target;
    public final double peso;
    
    
    public Edge(Vertex argTarget, double argWeight)
    { 
        target = argTarget; peso = argWeight; 
    
    }
    
}

public class Voraz
{
    
public static void computePaths(Vertex inicio)
{
        inicio.minimaDistancia = 0.;
        
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        
	vertexQueue.add(inicio);

	while ( !vertexQueue.isEmpty() ) {
            
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for ( Edge e : u.adjacencias )
            {
                Vertex v = e.target;
                
                double weight = e.peso;
                
                double distanceThroughU = u.minimaDistancia + weight;
                
		if (distanceThroughU < v.minimaDistancia) {
                    
		    vertexQueue.remove(v);

		    v.minimaDistancia = distanceThroughU ;
                    
		    v.anterior = u;
                    
		    vertexQueue.add(v);

		}

            }
        }
    }

    public static List<Vertex> obtenerRutamamasCortaPara(Vertex target)
    {
        List<Vertex> listaVerticesRutas = new ArrayList<Vertex>();
        
        for (Vertex vertex = target; vertex != null; vertex = vertex.anterior)
            
            listaVerticesRutas.add(vertex);

        Collections.reverse(listaVerticesRutas);
        
        return listaVerticesRutas;
    }


    static private StringTokenizer tokens2;
    static private int w;
    static int [] RutaOpt;

    public  void principal(int[][] matriz, int origen, int destino)
    {
        
        
   
  String nom="";
  
  Vertex  j;
  
  Vertex[] vertices = new Vertex [matriz.length];
               
  Vector<Vertex> vectorDeVectores = new Vector(0,1);
               
      for(int i =0; i< matriz.length; i++){
          
          nom=Integer.toString(i);
          
       j = new Vertex(nom);
       vectorDeVectores.add(j);
   
      }
      
      Vector<Edge> edges = new Vector(0,1);
      
    for(int t = 0; t<matriz.length; t++){
        
        for(int h = 0; h<matriz.length; h++){
          
          if( matriz[ t ][ h ] > 0){
            
              edges.add( new Edge( vectorDeVectores.elementAt( h ) , matriz[ t ][ h ]) );
              
           }
   
        }
            vectorDeVectores.elementAt(t).adjacencias = hacerArreglo( edges );
            edges.clear();
      }
      
for(int i=0; i<vectorDeVectores.size()-1; i++){
    

 vertices[i]= vectorDeVectores.elementAt(i+1);
 //System.out.println("Valores de vertices"+vertices[i]);
 
}
       computePaths(vectorDeVectores.elementAt(origen));
       
       /* for (Vertex v : vertices)
	{
	    System.out.println("Distance to " + v + ": " + v.minimaDistancia);
	    List<Vertex> Ruta = obtenerRutamamasCortaPara(v);
	    System.out.println("Ruta: " + Ruta);
	}*/
       
       System.out.println("Distancia para la Estacion " + vertices[ origen-1 ] + ": hasta la parada "+vertices[ destino-1 ] +" el tiempo es : " +vertices[ destino-1 ].minimaDistancia);
       
       List<Vertex> Ruta = obtenerRutamamasCortaPara(vertices[ destino-1 ]);
      CreacionDelItinerario(Ruta);

      JOptionPane.showMessageDialog(null, "Tamaño de Rutas "+Ruta.size());

      RutaOpt = new int [Ruta.size()];

      for(int i=0; i<Ruta.size();i++){
          //JOptionPane.showMessageDialog(null,Ruta.get(i));
          RutaOpt[i]= Integer.parseInt(Ruta.get(i).toString());
          //JOptionPane.showMessageDialog(null,RutaOpt[i]);
      }

      System.out.println("Ruta: " + Ruta);
    }
    
  static  Edge[] vector;
    public static Edge[] hacerArreglo( Vector< Edge > hacer ){
    
        vector = new Edge[ hacer.size() ];
        
        int i = 0;
        
        for( Edge E : hacer ){
        
            vector[ i ] = E;
            i++;
        }
        return vector;
    }
    
   static  String temporarParadas[] = null;
   
   
    public static void CreacionDelItinerario(List<Vertex> R){
        
    for(Vertex vert : R){
    
        
        
        
        
        
        
        
        /*
        for( int i = 0; i < temporarParadas.length-1; i++ ){
            
           int coma1=temporarParadas[i].indexOf(',');
           
          // System.out.println("valor : "+temporarParadas[ i ].substring(coma1+1,temporarParadas[i].length()-1)+" "+"valorSigui "+vert.name);
           //int j=i;
          Vertex vert2 = R.get(i+1);
          
            if( (temporarParadas[ i ].substring(coma1+1,temporarParadas[i].length()-1).equals( vert.name ) ) && ( temporarParadas[ i+1 ].substring(coma1+1,temporarParadas[i+1].length()-1).equals( vert2.name ) )) {
             
                
                
                System.out.println("la ruta es "+i);
               
            }
          }
        */
    }
    
    }
    
public static void llenarAdyacensia(String listaCaminos){

    
    
    
    
tokens2=new StringTokenizer(listaCaminos, "-");
//temporarParadas = new String[tokens2.countTokens()];


 while(tokens2.hasMoreElements()){
	String punto=(tokens2.nextToken());
        
        //temporarParadas[w]=punto;
   w++;
}
w=0;

}
    public static void setVectorParadas( String para[]) {
        
       temporarParadas = para;
    }
     private static Vector<String> busRuta;
     
    public static void setVectorBusesRutas( Vector<String> ara) {
        
       busRuta = ara;
    }
    
    public void CreacionDelItinera(){
    
    
    }
}

