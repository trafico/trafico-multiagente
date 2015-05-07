package cars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

class Vertice implements Comparable<Vertice>
 {
    //Clase sencilla para modelar al vertice
    public final String etiqueta;
    public final int id;
    public List<Arista> adyacentes;     //Aqui se guardara la lista de adyacencia de cada vertice
    public double minDistancia = Double.POSITIVE_INFINITY;   //Se guarda la distancia actual al nodo inicio
    public Vertice previo;  //Vertice anterior para reconstruccion del Path
      
    public Vertice(String argName, int argId) { 
          etiqueta = argName; 
          id = argId;
          adyacentes = new LinkedList<Arista>();
    }    
    // Regresa 0 si son iguales
    // Menor a 0 si el primero es menor
    //Mayor a 0 si el segundo es mayor
    public int compareTo(Vertice otro)
    {
        return Double.compare(minDistancia, otro.minDistancia);
    }
    public String toString() { return etiqueta; }
    
    //Añade una adyacencia a este vertice
   public void addArista(Vertice fin, double peso){
       Arista ar = new Arista(fin,peso);
       adyacentes.add(ar);
   }
   public void setArista(int fin, double peso){  
       Arista aux;
       int index=-1;
       //Busca el arista meta
       do {        
           index++;
           aux=adyacentes.get(index);
       }
       while(aux.meta.id != fin );         
       aux.peso=peso;
   }
   
 }

//Clase sencilla para representar un arista.
class Arista{
    public Vertice meta;    //Solo se necesita saber la meta, ya que cada vertice
                            //guarda sus aristas
    public double peso;
    public Arista(Vertice Meta, double Peso){
        meta = Meta; 
        peso = Peso; 
    }
    
    public void setPeso(double pes){
        peso = pes;
    }
    public void setPeso(int pes){
        peso = pes*1.0;
    }
}

public class Grafo extends Exception{
    public List<Vertice> Vertices;  //Un grafo es una coleccion de Vertices y aristas
                                    //debido a que cada vertice guarda sus aristas
                                    //solo debemos guardar los vertices que nos interesan
    
    public Grafo(){
        Vertices = new LinkedList<Vertice>();
    }
    
    public void addVertice(Vertice c)
    {
        Vertices.add(c);    //Solo es necesario guardar el vertice
    }
    
    public void setArista(int idIni, int idFin, int peso){
        setArista(idIni, idFin, 1.0*peso);
    }
    
    //Sirve para actualizar los pesos de aristas
    public void setArista(int idIni, int idFin, double peso){
        for (Vertice v : Vertices)
           {
               if(v.id == idIni) {                                      
                    v.setArista(idFin,peso);
               }              
           }
    }
    
    public void addArista(int idIni, int idFin, double peso) throws Exception{
        //Para guardar un arista debemos buscar los dos vertices en cuestion
        //Y revisar que existan.
        //Este metodo lanza una excepcion si cualquiera de los dos vertices no existe.        
        Vertice ini=null;
        Vertice fin=null;
        boolean i = false ,f = false; //Banderas
        
        //Buscamos los vertices
         for (Vertice v : this.Vertices)
 	{
           
 	   if(idIni == v.id) {ini = v; i=true;}
           if(idFin == v.id) {fin = v; f=true;}
           //Sale del ciclo si ya encontro los dos
           if(i && f){break;}
 	}
        //Comprobar que ambos vertices existan
         if(!(i && f)){
             //Comprobar cual no existe
             if(!i){ throw new Exception("El vertice "+idIni +" no existe!");}
             if(!i){ throw new Exception("El vertice "+idFin +" no existe!");}
         } else {
             ini.addArista(fin, peso);
         }
         
    }
    
    //Este es el algoritmo principal
    //Pseudocodigo tomado de http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Using_a_priority_queue
    public void djisktra(int posicion){
        Vertice inicio = Vertices.get(posicion);
        
        //La distancia del nodo de inicio a si mismo es 0
        inicio.minDistancia=0;
        
        //Se crea una PriorityQueue debido a que ofrece un mejor rendimiento
        //que listas basicas debido a que se ordena al insertar nuevos elementos.
        //Se añade el elemento de inicio
        PriorityQueue<Vertice> ListaVertices = new PriorityQueue<Vertice>();
        ListaVertices.add(inicio);
        
        while (!ListaVertices.isEmpty()) {
            //Se devuelve el mejor vertice. Al quitarlo de la lista se considera
            //Visitado
            Vertice u = ListaVertices.poll();
            //Visitar vecinos 
            for (Arista e : u.adyacentes)
            {
                Vertice v = e.meta;
                double pesoArista = e.peso;
                //La distancia hasta el nuevo nodo es la distancia de u mas el
                //peso del arista
                double distPorU = u.minDistancia + pesoArista;
                
                //Si la nueva distancia es menor que que se tenia guardada
  		if (distPorU < v.minDistancia) {
                    //v se quita ya que hay que actualizar el peso
  		    ListaVertices.remove(v);  
                    //Se actualizan la distancia y el vertice preio
  		    v.minDistancia = distPorU ;
  		    v.previo = u;
                    //Se añande a la lista con el nuevo peso.
  		    ListaVertices.add(v);
  		}
              }
          }
    }
    
    //Devolver el camino a un vertice desitno
    public List<Vertice> camino(Vertice destino){
          List<Vertice> path = new ArrayList<Vertice>();
          //Se van añadiendo a una lista los vertices previos
          //hasta que se llega al inicio
          for (Vertice vert = destino; vert != null; vert = vert.previo){
              //Esto quiere decir que el nodo no se puede alcanzar
              if(vert.minDistancia==Double.POSITIVE_INFINITY){return null;}
              path.add(vert);
          }
          //Se invierte la lista y se devuelve
          Collections.reverse(path);
          return path;
    }
    
    public void imprimeGrafo(){
         for (Vertice v : this.Vertices)
 	{
            System.out.print("Vertice: "+v.etiqueta+" ID: "+v.id+" | ");
            for (Arista a : v.adyacentes) {
                System.out.print("=> "+a.meta.etiqueta+" "+a.peso+" ");
            }
             System.out.println();
 	}
    }
}