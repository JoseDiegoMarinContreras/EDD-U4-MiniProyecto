import java.io.Serializable;

public class Vertice implements Serializable{
    Object valor;
    Vertice ant, sig;
    Arista arista;
    int x,y;
    
    public Vertice(Object v,int posx, int posy){
        x = posx;
        y = posy;
        valor = v;
        ant = sig = null;
        arista = null;
    }
    
    public boolean exiteEnlaceCon(Vertice temp){
        for(Arista i = arista; i != null; i = i.sig){
            if(i.punteroaVertice == temp){
                return true;
            }
        }
        return false;
    }
    
    public boolean enlazar(Vertice destino){
        Arista nuevaArista = new Arista(destino);
        if(nuevaArista == null){return false;}
        //ini == null && fin == null
        if(arista == null){
            arista = nuevaArista;
            return true;
        }
        Arista t;
        for(t = arista; t.sig != null; t = t.sig){
            if(nuevaArista.punteroaVertice.valor.equals(t.punteroaVertice.valor)){
                return false;
            }
        }
        t.sig = nuevaArista;
        nuevaArista.ant = t;
        return true;
    }   
    
    public boolean existeA(Vertice dest){
        for(Arista a = arista; a != null; a = a.sig){
            if(a.punteroaVertice == dest){
                return true;
            }
        }
        return false;
    }
    
    public boolean desenlazar(Vertice destino){
        if(arista == null){//Ese nodo no tiene aristas
            return false;
        }
        if(arista.sig == null){
            if(arista.punteroaVertice.equals(destino)){
                arista.punteroaVertice = null;
                arista = null;
                return true;
            }
            return false;
        }
        //3 pases, si esta en arista, si esta en medio, al final
            Arista t = arista;
        if(arista.punteroaVertice.equals(destino)){
            arista = t.sig;
            arista.ant = null;
            t.sig = null;
            t.punteroaVertice = null;
            return true;
        }
        for(t = arista.sig;t != null;t = t.sig){
            if(t.punteroaVertice.equals(destino)){
                if(t.sig == null){
                    //Si estoy en el ultimo
                    t.ant.sig = null;
                    t.ant = null;
                    t.punteroaVertice = null;
                    return true;
                }else{
                    //o sino en medio
                    t.ant.sig = t.sig;
                    t.sig.ant = t.ant;
                    t.punteroaVertice = null;
                    t.ant = t.sig = null;
                    return true;
            }
            }
        }
        //DESPUES DEL CICLO FOR
        return false;
    }
}
