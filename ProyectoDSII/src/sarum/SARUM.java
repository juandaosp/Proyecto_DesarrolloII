package sarum;


import java.*;
import java.awt.Point;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import paquete.Main;

public class SARUM
{
     Vector<Vector<Itinerario>> todosLosItinerarios;
//    public static Itinerario itinerario;
    Parada[] arregloDeParadas;
    Ruta rutasHabiles[];
    String texto="";
     Main mm;
    private String rutaArchivo1;
    public SARUM()
    {
       mm= new Main();
        todosLosItinerarios= new Vector<Vector<Itinerario>>();
    }

    public int contarParadas(String rutaArchivo) throws IOException
    {
       
        FileReader archivo = new FileReader(rutaArchivo);
        LineNumberReader numLinea = new  LineNumberReader(archivo);
        String lineaLeida="";
        int cantidaddeParadas=1;
        try{
            while(!(lineaLeida=numLinea.readLine()).equalsIgnoreCase("rutas"))
            {
                if(lineaLeida.equals(""));
                else
                    cantidaddeParadas++;
            }}
        catch (FileNotFoundException ex){}
        //JOptionPane.showMessageDialog(null, "cantidad de Paradas: "+cantidaddeParadas);
        return cantidaddeParadas-2;
    }

    public Parada[] capturarParadas(String rutaArchivo) throws IOException
    {
        arregloDeParadas = new Parada[contarParadas(rutaArchivo)];
        try
        {
            FileReader archivo = new FileReader(rutaArchivo);
            BufferedReader entrada = new BufferedReader(archivo);
            String lineaLeida="";
            int posArreglo = 0;
            while(!(lineaLeida=entrada.readLine()).equalsIgnoreCase("rutas"))
            {
                if(lineaLeida.equals(""));
                else if(!lineaLeida.equalsIgnoreCase("paradas"))
                {
                    Parada parada = new Parada();
                    String[] datosDeLaParada = lineaLeida.split("#");
                    parada.setTipo(Integer.parseInt(datosDeLaParada[0]));
                    parada.setIndice(Integer.parseInt(datosDeLaParada[1]));
                    parada.setNombre(datosDeLaParada[2]);
                    String[] coordenada = datosDeLaParada[3].split(",");
                    int x = Integer.parseInt(coordenada[0]);
                    int y = Integer.parseInt(coordenada[1]);
                    parada.setCoordenada(x, y);
                    arregloDeParadas[posArreglo] = parada;
                    posArreglo++;
                }
            }
        }
        catch (FileNotFoundException ex){System.out.println("Error al capturar las paradas: "+ex);}

        return arregloDeParadas;
    }

    public int contarRutas(String rutaArchivo) throws IOException
    {
        FileReader archivo = new FileReader(rutaArchivo);
        LineNumberReader numLinea = new  LineNumberReader(archivo);
         String lineaLeida="";
        int LineasContadas=0;
        try
        {
            while(!((lineaLeida=numLinea.readLine())==null)){
                if(lineaLeida.equals(""));
                else
                    LineasContadas++;
            }}
        catch (FileNotFoundException ex){}
        //JOptionPane.showMessageDialog(null, "Lineas contadas: "+LineasContadas);
        return LineasContadas-contarParadas(rutaArchivo)-2;
    }

    public Ruta[] capturarRutas(String rutaArchivo) throws IOException
    {
         rutaArchivo1=rutaArchivo;
        rutasHabiles = new Ruta[contarRutas(rutaArchivo)];
        //System.out.println(rutasHabiles.length);
        try
        {
            FileReader archivo = new FileReader(rutaArchivo);
            LineNumberReader numLinea = new  LineNumberReader(archivo);
            String lineaLeida="";
            int posAreglo=0;
            while(!((lineaLeida=numLinea.readLine())==null))
            {
                //JOptionPane.showMessageDialog(null, "Linea descartada: "+lineaLeida);
                if(lineaLeida.equals(""));
                    //JOptionPane.showMessageDialog(null, "Linea descartada: "+lineaLeida);
                else if(numLinea.getLineNumber() > (contarParadas(rutaArchivo) + 2))
                {
                    Ruta ruta = new Ruta();
                    SemiRecorrido sr = new SemiRecorrido();
                    String datosDeLaRuta[] = lineaLeida.split("#");
                    //System.out.println(datosDeLaRuta[0]);
                    ruta.setNombre(datosDeLaRuta[0]);
                    //JOptionPane.showMessageDialog(null, ruta.getNombre());
                    //System.out.println(datosDeLaRuta[1]);
                    ruta.setFrecuencia(Integer.parseInt(datosDeLaRuta[1]));
                    //System.out.println(datosDeLaRuta[2]);
                    String tiempoParada[] = datosDeLaRuta[2].split("-");
                    ruta.paradaTiempo = new int[tiempoParada.length][2];
                    for(int i=0; i<ruta.paradaTiempo.length;i++)
                    {
                        //String separarTiempoParada[] = tiempoParada[i].split(",");
                        StringTokenizer token = new StringTokenizer(tiempoParada[i]);
                        String tiempo = token.nextToken("( , ");
                        String parada = token.nextToken(" , )");
                        ruta.paradaTiempo[i][0]=Integer.parseInt(parada);
                        ruta.paradaTiempo[i][1]=Integer.parseInt(tiempo);
                        //ruta.parTiempoParada.put(parada, Integer.parseInt(tiempo));
                        //System.out.println("Mapa: "+ruta.parTiempoParada.get(parada));
                        //System.out.println("Mapa: "+ruta.parTiempoParada.size());
                    }
                    //System.out.println("Ruta: "+ruta.getNombre());
                    //ruta.imprimirParadas();
                    //System.out.println("Tiempo obtenido: "+ruta.obtenerTiempo(47));
                    rutasHabiles[posAreglo]=ruta;
                    //System.out.println("Nombre ruta actual"+rutasHabiles[posAreglo].getNombre());
                    posAreglo++;
                }
            }
        }
        catch (FileNotFoundException ex) {}
        return rutasHabiles;
    }


    //Calcula recorridos para rutas que tengan las dos paradas e indica la de menor tiempo 
    public Vector<SemiRecorrido> rutaDirecta(int indParadaOrigen, int indParadaDestino, Ruta ruta)
    {
        Ruta mejorRuta= new Ruta();
        Vector<SemiRecorrido> semiRecorridos = new Vector<SemiRecorrido>();
        int posicOrigen=0, posicDestino=0, rutasEncontradas=0;
        int pesoRecorrido=0, mejorTiempo=1000000000;
        //JOptionPane.showMessageDialog(null, ruta);
        //JOptionPane.showMessageDialog(null, ruta.getNombre());

        {
            if(ruta.contieneParada(indParadaOrigen) && ruta.contieneParada(indParadaDestino))
            {
                posicOrigen=ruta.obtenerPosicParada(indParadaOrigen);
                posicDestino=ruta.obtenerPosicParada(indParadaDestino);
                SemiRecorrido penalizarFrecuencia = new SemiRecorrido();
                penalizarFrecuencia.setParadaAscenso(indParadaOrigen);
                penalizarFrecuencia.setNombreRuta("PenalizFrecuencia-"+ruta.getNombre());
                penalizarFrecuencia.setParadaDescenso(indParadaOrigen);
                penalizarFrecuencia.setTiempoRecorrido(ruta.getFrecuencia());
                semiRecorridos.add(penalizarFrecuencia);
                int numSemiRecorrido=0;
                if(posicOrigen<posicDestino)
                {
                    for(int j=posicOrigen; j<=posicDestino-1; j++)
                     {
                         SemiRecorrido semiRecorrido = new SemiRecorrido();
                         semiRecorrido.setNumero(numSemiRecorrido+1);
                         semiRecorrido.setParadaAscenso(ruta.paradaTiempo[j][0]);
                         semiRecorrido.setParadaDescenso(ruta.paradaTiempo[j+1][0]);
                         semiRecorrido.setNombreRuta(ruta.getNombre());
                         semiRecorrido.setTiempoRecorrido(ruta.paradaTiempo[j+1][1]);
                         semiRecorridos.add(semiRecorrido);
                    }
                    if(pesoRecorrido<mejorTiempo)
                    {
                        mejorTiempo=pesoRecorrido;
                        mejorRuta=ruta;
                    }
                    rutasEncontradas++;
                }
            }
       }

        return semiRecorridos;
    }

    public Ruta[] arregloContieneParada(int indiceOrigen, int indiceDestino, Ruta[] rutas)
    {
        //Calcular el tamaña del arreglo
        int contador=0;
        for(int i=0; i<rutas.length; i++)
            if(rutas[i].contieneParada(indiceOrigen) && !rutas[i].contieneParada(indiceDestino))
                contador++;

        //Guardar rutas que contengan el indice
        Ruta[] rutasObtenidas = new Ruta[contador];
        int posicion=0;
        for(int i=0; i<rutas.length; i++)
            if(rutas[i].contieneParada(indiceOrigen) && !rutas[i].contieneParada(indiceDestino))
            {
                rutasObtenidas[posicion]=rutas[i];
                posicion++;
            }

        return rutasObtenidas;
    }

    public void itinerariosArchivo(String archivoParadasRutas, String archivoOrigenDestino) throws IOException
    {
        todosLosItinerarios.clear();
        int indiceOrigen=0, indiceDestino=0, contador=1,rutasEncotradas=0;
        Parada[] arregloParada = capturarParadas(archivoParadasRutas);
        Ruta[] rutas = capturarRutas(archivoParadasRutas);
        try
        {
            FileReader archivo = new FileReader(archivoOrigenDestino);
            BufferedReader entrada = new BufferedReader(archivo);
            String lineaLeida="";
            while(!((lineaLeida=entrada.readLine())==null))
            {
                if(lineaLeida.equals(""));
                else{
                Vector<Itinerario> itinerariosPorLinea = new Vector<Itinerario>();
                texto+="Linea "+contador+": "+lineaLeida+"\n";

                StringTokenizer token = new StringTokenizer(lineaLeida);
                int xOrigen = Integer.parseInt(token.nextToken("( ,"));

                int yOrigen = Integer.parseInt(token.nextToken(", )"));

                indiceOrigen= buscarPuntoCercano(xOrigen, yOrigen, arregloParada);
                texto+="Parada mas cercana al origen: "+indiceOrigen+"\n";
                    
                int xDestino=0;
                if(token.nextToken(") ( ,").contains("--")){
                    xDestino = Integer.parseInt(token.nextToken(") -( ,"));
                } else{
                    xDestino = Integer.parseInt(token.nextToken(") ( ,"));
                }
                int yDestino = Integer.parseInt(token.nextToken("( , )"));
                indiceDestino = buscarPuntoCercano(xDestino, yDestino, arregloParada);
                texto+="Parada mas cercana al destino: "+indiceDestino+"\n\n";
                int numItinerario=0;
                //JOptionPane.showMessageDialog(null, rutas.length);
                for (int i = 0; i < rutas.length; i++)
                {
                    //JOptionPane.showMessageDialog(null, rutas[i].getNombre());
                    Itinerario itinerario = new Itinerario();
                    itinerario.recorrido.addAll(rutaDirecta(indiceOrigen, indiceDestino, rutas[i]));
                    if(itinerario.recorrido.size()>1)
                    {
                        itinerario.setTipoRuta("Directa");
                        itinerario.setNumero(numItinerario+=1);
                        itinerario.setSumaTiempo();
                        itinerariosPorLinea.add(itinerario);
                    }
                }

                Ruta[] rutasParadaInicial = arregloContieneParada(indiceOrigen, indiceDestino, rutas);
                Ruta[] rutasParadaFinal = arregloContieneParada(indiceDestino, indiceOrigen, rutas);
                for(int i=0; i<rutasParadaInicial.length; i++)
                {
                    for(int j=0; j<rutasParadaInicial[i].paradaTiempo.length; j++)
                    {
                        int indiceActual = rutasParadaInicial[i].paradaTiempo[j][0];
                        for(int k=0; k < rutasParadaFinal.length; k++)
                            if(rutasParadaFinal[k].contieneParada(indiceActual))
                            {
                                Itinerario itinerario = new Itinerario();
                                itinerario.recorrido.addAll(rutaDirecta(indiceOrigen, indiceActual, rutasParadaInicial[i]));
                                if(itinerario.recorrido.size()>1 && !(itinerario.recorrido.lastElement().getParaDecenso()==indiceDestino))
                                {
                                    itinerario.recorrido.addAll(rutaDirecta(indiceActual, indiceDestino, rutasParadaFinal[k]));
                                    if(itinerario.recorrido.lastElement().getParaDecenso()==indiceDestino)
                                    {
                                        itinerario.setTipoRuta("Transbordo");
                                        itinerario.setNumero(numItinerario+=1);
                                        itinerario.setSumaTiempo();
                                        itinerariosPorLinea.add(itinerario);
                                    }
                                }
                            }
                    }
                }
                contador++;
                todosLosItinerarios.add(itinerariosPorLinea);
                }
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(SARUM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int buscarPuntoCercano(int x, int y, Parada[] paradas)
    {
        Point punto = new Point(x, y);
        int indice=0;
        double distancia=0, menordistacia=1000000.0;
        for(int i=0; i<paradas.length; i++)
        {
            distancia=paradas[i].coordenanda.distance(punto);
            if(menordistacia>distancia)
            {
                menordistacia=distancia;
                indice=paradas[i].getIndice();
            }
        }
        return indice;
    }

    public void itinerariosMouse(int xOrigen, int yOrigen, int xDestino, int yDestino) throws FileNotFoundException, IOException
    {
        todosLosItinerarios.clear();
        Vector<Itinerario> itinerariosMouse = new Vector<Itinerario>();
        texto+="Puntos escogidos : ("+xOrigen+","+yOrigen+")-("+xDestino+","+yDestino+")\n\n";
        int indiceOrigen = buscarPuntoCercano(xOrigen, yOrigen, arregloDeParadas);
        texto+="Parada mas cercana al origen:sadas "+indiceOrigen+"\n";
        int indiceDestino = buscarPuntoCercano(xDestino, yDestino, arregloDeParadas);
        texto+="Parada mas cercana al destino: "+indiceDestino+"\n";
        
        int numItinerario=0;
        JOptionPane.showMessageDialog(null, "Tamaño rutas: "+ rutasHabiles.length);

                for (int i = 0; i < rutasHabiles.length; i++)
                {
                    Itinerario itinerario = new Itinerario();
                    itinerario.recorrido.addAll(rutaDirecta(indiceOrigen, indiceDestino, rutasHabiles[i]));
                    if(itinerario.recorrido.size()>1)
                    {
                        itinerario.setTipoRuta("Directa");
                        itinerario.setNumero(numItinerario+=1);
                        itinerario.setSumaTiempo();
                        itinerariosMouse.add(itinerario);
                    }
                }
        Ruta[] rutasParadaInicial = arregloContieneParada(indiceOrigen, indiceDestino, rutasHabiles);
        Ruta[] rutasParadaFinal = arregloContieneParada(indiceDestino, indiceOrigen, rutasHabiles);
        for(int i=0; i<rutasParadaInicial.length; i++)
                {
                    for(int j=0; j<rutasParadaInicial[i].paradaTiempo.length; j++)
                    {
                        int indiceActual = rutasParadaInicial[i].paradaTiempo[j][0];
                        for(int k=0; k < rutasParadaFinal.length; k++)
                            if(rutasParadaFinal[k].contieneParada(indiceActual))
                            {
                                Itinerario itinerario = new Itinerario();
                                itinerario.recorrido.addAll(rutaDirecta(indiceOrigen, indiceActual, rutasParadaInicial[i]));
                                if(itinerario.recorrido.size()>1 && !(itinerario.recorrido.lastElement().getParaDecenso()==indiceDestino))
                                {
                                    itinerario.recorrido.addAll(rutaDirecta(indiceActual, indiceDestino, rutasParadaFinal[k]));
                                    if(itinerario.recorrido.lastElement().getParaDecenso()==indiceDestino)
                                    {
                                        itinerario.setTipoRuta("Transbordo");
                                        itinerario.setNumero(numItinerario+=1);
                                        itinerario.setSumaTiempo();
                                        itinerariosMouse.add(itinerario);
                                    }
                                }
                            }
                    }
                }
      todosLosItinerarios.add(itinerariosMouse);
      
       mm.obtenerRuta(rutaArchivo1);
        JOptionPane.showMessageDialog(null,rutaArchivo1,"ruta archivo",JOptionPane.PLAIN_MESSAGE);
       mm.obtenePuntos(indiceOrigen,indiceDestino);
       JOptionPane.showMessageDialog(null,indiceOrigen,"origen origen",JOptionPane.PLAIN_MESSAGE);
       JOptionPane.showMessageDialog(null,indiceDestino,"origen destino ",JOptionPane.PLAIN_MESSAGE);
       mm.iniciar();
      
      
    }

    public int obtenerMejorItinerario(Vector<Itinerario> itinerarios)
    {
        int mejorTiempo=10000000, indiceMejorItinerario=0;
        for (Itinerario itinerario : itinerarios)
        {
            if(itinerario.getSumaTiempo()<mejorTiempo)
            {
                mejorTiempo=itinerario.getSumaTiempo();
                indiceMejorItinerario=itinerario.getNumero();
            }
        }
        return indiceMejorItinerario;
    }
    public void imprimirEnArchivo()
    {

        try
        {
            FileWriter archivo = new FileWriter("src/Salida/Resultados.txt");
            BufferedWriter bufer = new BufferedWriter(archivo);
            PrintWriter salida = new PrintWriter(bufer);
            for (Vector<Itinerario> itinerarios : todosLosItinerarios)
            {
                
                for (Itinerario itinerario : itinerarios)
                {
                    texto+="Itinerario: "+itinerario.getNumero()+"\n";
                    texto+="Tipo de ruta"+itinerario.getTipoRuta()+"\n";
                    texto+=itinerario.imprimir();
                    texto+="Tiempo recorrido: "+itinerario.getSumaTiempo()+"\n\n";
                }
                int mejorItinerario=obtenerMejorItinerario(itinerarios);
                System.out.println("********************* "+mejorItinerario );
                texto+="Mejor itinerario: "+mejorItinerario+
                        ", Tiempo " +itinerarios.elementAt(mejorItinerario-1).getSumaTiempo()+"\n\n";
            }
            salida.println(texto);
            salida.close();
            texto="";
        } catch (IOException ex) {
            Logger.getLogger(SARUM.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Puede revisar los resultados en: src/Salida/Resultados.txt");
    }

}
