
import java.io.Serializable;


public class Arista implements Serializable{
   Vertice punteroaVertice;
   Arista ant,sig;
   
   public Arista(Vertice vertice){
       punteroaVertice = vertice;
       ant = sig = null;
   }
}
