
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;


public class BaseDeDatos {
    protected Grafo g;
    protected String url;
    
    BaseDeDatos(){
    url = null;
    g = new Grafo();
    }
    
    BaseDeDatos(String url){
        this.url = url;
    try{
    ObjectInputStream io = new ObjectInputStream(new FileInputStream(url));
    g = new Grafo();
    for(Vertice temp = (Vertice)io.readObject();temp!=null;temp = (Vertice)io.readObject()){
        g.insertar(temp.valor,temp.x,temp.y);
    }
    Vertice temp = g.ini;
    for(String aristas = (String)io.readObject();aristas !=null ;aristas = (String)io.readObject(), temp = temp.sig){
        String[] ar = aristas.split("-");
        for(int i = 0; i< ar.length; i++){
            if(!ar[i].equals("")){
            g.crearArista(temp.valor,ar[i]);
            }
        }
    }
    io.close();
    }catch(FileNotFoundException err){} 
    catch (IOException ex) {} 
    catch (ClassNotFoundException ex) {}     
    }
    
    public boolean guardar(){
    try{    
    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(url));
    for(Vertice temp = g.ini;temp != null;temp = temp.sig){
    os.writeObject(temp);
    }
    os.writeObject(null);
    for(Vertice temp = g.ini;temp != null;temp = temp.sig){
     String ar ="-";
        for(Arista t = temp.arista; t != null; t = t.sig){
            ar+=t.punteroaVertice.valor+"-";
        }
        os.writeObject(ar);
    }
    os.close();}catch(FileNotFoundException err){} catch (IOException ex) {return false;}
    return true;
}
}
