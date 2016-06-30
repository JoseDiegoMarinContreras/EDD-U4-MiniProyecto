
import java.util.ArrayList;




public class Grafo {
    protected Vertice ini,fin;
    
    public Grafo(){
        ini = fin = null;
    }
    
    public boolean insertar(Object valor, int posx, int posy){
        Vertice temp = new Vertice(valor,posx,posy);
        if(temp == null){return false;}
        if(ini == null && fin == null){
            ini = fin = temp;
            return true;
        }
        Vertice repetido = buscarVertice(valor);
        if(repetido != null){return false;}
        fin.sig = temp;
        temp.ant = fin;
        fin = temp;
        return true;
    }
    
    public Vertice buscarVertice(Object valor){
        if(ini == null && fin == null){return null;}
        for(Vertice temp = ini; temp != null; temp = temp.sig){
            if(temp.valor.equals(valor)){
                return temp;
            }
        }
        return null;
    }
    
    public String busquedaCamino(Object org,Object dest){
        if(estaVacio()){return null;}
        Vertice o = buscarVertice(org);
        Vertice d = buscarVertice(dest);
        if(o == null || d == null || o.arista == null){return null;}
        if(org.equals(dest)){return"/"+org;}
        String path = busquedaCamino(o,d,"/"+dest);
        if(path == null){return null;}else{
            return path+"/"+dest;
        }
    }
    
    private String busquedaCamino(Vertice org,Vertice dest,String path){
        String p;
        if(org.exiteEnlaceCon(dest)){return "/"+org.valor;}
        for(Vertice temp = ini; temp != null; temp = temp.sig){
            if(temp.exiteEnlaceCon(dest)){
                if(temp == org){ return "/"+org.valor;}
                if(path.contains(temp.valor+"/"+dest.valor)){return null;} 
                p = busquedaCamino(org,temp,"/"+temp.valor+path);
                if(p != null){ return p+"/"+temp.valor;}
            }
        }
        return null;
    }
    
    public boolean crearArista(Object origen, Object destino){
        if(ini == null && fin == null){return false;}
        Vertice NodoOrigen = buscarVertice(origen);
        Vertice NodoDestino = buscarVertice(destino);
        if(NodoOrigen == null || NodoDestino == null){return false;}
        if(NodoOrigen.existeA(NodoDestino)){return false;}
        return NodoOrigen.enlazar(NodoDestino);
    }
    

    
    public boolean eliminarArista(Object origen, Object destino){
        if(ini == null && fin == null){return false;}
        Vertice NodoOrigen = buscarVertice(origen);
        Vertice NodoDestino = buscarVertice(destino);
        if(NodoOrigen == null || NodoDestino == null){return false;}
        return NodoOrigen.desenlazar(NodoDestino);
    }
    
    public boolean eliminarVertice(Object valor){
        if(ini == null && fin ==null){
            return false;
        }
        if(ini==fin){
            if(ini.valor.equals(valor)){
                //Este ciclo elimina todas las aristas del vértice
                while(ini.arista!=null){
                    ini.desenlazar(ini.arista.punteroaVertice);
                }
                ini=fin=null;
                return true;
            }
        }
        Vertice nodoAEliminar = buscarVertice(valor);
        if(nodoAEliminar == null) return false; //No hay ese vertice que segun se eliminara
        
        //Los 3 casos: al inicio, al final, en medio, pero antes ...
        //Inicia ciclo para que LOS VERTICES Quiten arista al que se eliminara
        for(Vertice temp = ini; temp!=null; temp=temp.sig){
            temp.desenlazar(nodoAEliminar);
        }
        //Ahora se eliminan todas las aristas del vertice a eliminar
         while(nodoAEliminar.arista!=null){
             nodoAEliminar.desenlazar(nodoAEliminar.arista.punteroaVertice);
         }
        //Preguntar si el nodoAEliminar esta al inicio
        if( nodoAEliminar == ini){
           ini = ini.sig;
           nodoAEliminar.sig= null;
           ini.ant = null;
           return true;
        }
        //sino ver si esta al final
        if(nodoAEliminar == fin){
            fin = fin.ant;
            fin.sig = null;
            nodoAEliminar.ant = null;
            return true;
        }
        //Si no, tons está en medio!
        nodoAEliminar.ant.sig = nodoAEliminar.sig;
        nodoAEliminar.sig.ant = nodoAEliminar.ant;
        nodoAEliminar.sig = nodoAEliminar.ant = null;
        return true; 
    }
    
    public boolean exactamenteIgualA(Grafo p){
        for(Vertice t1 = this.ini, t2 = p.ini; t1 != null || t2 != null; t1 = t1.sig, t2 = t2.sig){
                if(t1 == null && t2 != null){return false;} 
                if(t2 == null && t1 != null){return false;} 
                if(! t1.valor.equals(t2.valor)){return false;}
            for(Arista a1 = t1.arista, a2 = t2.arista; a1 != null; a1 = a1.sig, a2 = a2.sig){
                    if(a1 == null && a2 != null){return false;} 
                    if(a2 == null && a1 != null){return false;} 
                    if(!a1.punteroaVertice.valor.equals(a2.punteroaVertice.valor)){return false;}
        }
        }
        return true;
    }
    
    public boolean estaVacio(){
        return ini == null && fin == null;
    }
    
    public String matrizDeAdyacencia(){
        if(ini == null && fin == null){return null;}
        String d="",m="        ";
        for(Vertice t1 = ini; t1 != null; t1 = t1.sig){
            m += t1.valor+" ";
            d += t1.valor+"    -";
            for(Vertice t2 = ini; t2 != null; t2 = t2.sig){
                if(t1.exiteEnlaceCon(t2)){
                    d+="1-";
                }else{
                    d+="0-";
                }
            }
            d+="\n";
        }
        return m+"\n"+d;
    }
    
    public String[] conjuntos(){
        if(ini == null && fin == null){return null;}
        String conj = "-";
        ArrayList<String> conjSolos = new ArrayList<String>();
        for(Vertice v = ini; v != null; v = v.sig){
            if(esUnVerticeSinAristas(v)){
                conjSolos.add("-("+v.valor+")-");
            }
            for(Arista a = v.arista; a != null; a = a.sig){
            conj += "("+v.valor+" , "+a.punteroaVertice.valor+")-";
            }
        }
        if(conj.equals("-")){
            if(!conjSolos.isEmpty()){
                    return conjSolos.toArray(new String[conjSolos.size()]);
                }
            return null;
            }
        String []temp =  conjuntos1(conj,"");
        if(temp == null && conjSolos.isEmpty()){return null;}
        if(temp == null && !conjSolos.isEmpty()){return conjSolos.toArray(new String[conjSolos.size()]);}
        if(temp != null && conjSolos.isEmpty()){return temp;}
        String []r = new String[temp.length+conjSolos.size()];
        int i;
        for( i = 0 ; i < temp.length ; i++ ){ 
            r[i] = temp[i]; 
        }        
        for( i=0; i<conjSolos.size(); i++){ 
            r[temp.length + i] = conjSolos.get(i); 
        } 
        return r;
    }
    
    public boolean esUnVerticeSinAristas(Vertice temp){
        if(temp.arista != null){return false;}
        for(Vertice v = ini; v != null; v = v.sig){
            if(v.existeA(temp)){
                return false;
            }
        }
        return true;
    }
    
    private String[] conjuntos1(String con,String herr){
        String []r = conjuntos2(con,"");
        if(!r[1].equals("")){herr+=r[1]+"\n";}
        if(r[0].equals("")){
            return herr.split("\n");
        }
        return conjuntos1(r[0],herr);
    }
    
    private String[] conjuntos2(String conj,String herr){
        if(conj.equals("")){
    String[] d={conj,herr};
        return d; 
        }
        if(herr.equals("")){
            herr = "-"+conj.split("-")[1]+"-";
            conj = conj.replace(herr,"");
            return conjuntos2(conj,herr);
        }
        String m[] = conj.split("-");
        for(int i = 0; i< m.length; i++){
            String a[] = m[i].split(" , ");
            a[0] = a[0].replace("(","");
            a[1] = a[1].replace(")","");
            if(herr.contains(a[0]+" ,") || herr.contains(a[1]+" ,") || herr.contains(", "+a[0]) || herr.contains(", "+a[1])){
            herr += m[i]+"-";
            conj = conj.replace(m[i]+"-","");
            return conjuntos2(conj,herr);
            }
        }
    String[] d={("-"+conj),herr};
        return d;   
    }
    
    public String matrizDeIncidencia(){
        if(ini == null && fin == null){return null;}
        String d="",m="            ";
        for(Vertice t1 = ini; t1 != null; t1 = t1.sig){
            m += t1.valor+"     ";
            d += t1.valor+"    -";
            for(Vertice t2 = ini; t2 != null; t2 = t2.sig){
                if((t1.exiteEnlaceCon(t2) && t1 == t2) || (t2.exiteEnlaceCon(t1) && t1.exiteEnlaceCon(t2))){
                    d+="(±1)-";
                }else if(t2.exiteEnlaceCon(t1)){
                    d+="(-1)-";
                }else if(t1.exiteEnlaceCon(t2)){
                    d+="(+1)-";
                }else {
                    d+="( 0)-";
                }
            }
            d+="\n";
        }
        return m+"\n"+d;
    }
}
