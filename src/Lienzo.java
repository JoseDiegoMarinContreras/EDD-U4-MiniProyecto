
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Lienzo extends JPanel implements MouseListener,MouseMotionListener{
    protected Grafo g;
    public VerticeGrafica org,dest;
    ArrayList<VerticeGrafica> vg;
    protected int pros;
    
    public Lienzo(int ancho, int alto, Grafo gf){
        g = gf;
        org = dest = null;
        vg = new ArrayList<VerticeGrafica>();
        pros = 0;
        setSize(ancho,alto);
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void notificar(){
        switch(pros){
            case 2:{
                if(org != null && dest != null){
                    if(g.crearArista(org.r.valor, dest.r.valor)){
                    JOptionPane.showMessageDialog(this,"Enlazado "+org.r.valor+" con "+dest.r.valor);
                    repaint();
                    }
                    org.seleccionado =  false;
                    org.repaint();
                    org = dest = null;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    pros = 0;
                }
            }break;
            case 3:{
                if(org != null){
                g.eliminarVertice(org.r.valor);
                repaint();
                for(int i = 0; i < vg.size(); i++){
                    if(org == vg.get(i)){
                        vg.remove(i);
                        break;
                    }
                }
                this.remove(org);
                org.li = null;
                org.r = null;
                org =null;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pros = 0;
                }
            }break;
            case 4:{
                if(org != null && dest != null){
                    org.r.desenlazar(dest.r);
                    repaint();
                    org.seleccionado =  false;
                    org.repaint();
                    org = dest = null;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    pros = 0;
                }
            }break;
    }
    }
    
    
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D)g;
         g.setColor(Color.BLACK);
         g2.setStroke(new BasicStroke(2));
        Double angulo; 
        int x,y,x1,y1;
        String nomA="-";
        for(Vertice temp = this.g.ini; temp != null; temp = temp.sig){
            for(Arista ar = temp.arista; ar != null; ar = ar.sig){
                if(temp == ar.punteroaVertice){
                    g.drawArc(temp.x-30, temp.y-30, 60, 60, 0, 270);
                    g.drawLine(temp.x+30, temp.y,temp.x+50,temp.y-10);
                    g.drawLine(temp.x+30, temp.y,temp.x+10,temp.y-10);
                }else{
                    
                    double tx = (ar.punteroaVertice.x - temp.x);
                    double ty = (ar.punteroaVertice.y - temp.y);
                    angulo = Math.atan(ty/tx); 
                     if(tx<0){angulo+=Math.PI;}
                    x = (int)(Math.cos(angulo)*44); 
                    y = (int)(Math.sin(angulo)*44);
                    g.drawLine(temp.x+30, temp.y+30, ar.punteroaVertice.x+30, ar.punteroaVertice.y+30);
                    x1 = (int)((ar.punteroaVertice.x+30-x)-25*Math.cos (angulo-Math.toRadians (45)));
                    y1 = (int)((ar.punteroaVertice.y+30-y)-25*Math.sin (angulo-Math.toRadians (45)));
                    g.drawLine(ar.punteroaVertice.x+30-x,ar.punteroaVertice.y+30-y,x1,y1);
                    x1 = (int)((ar.punteroaVertice.x+30-x)-25*Math.cos (angulo+Math.toRadians (45)));
                    y1 = (int)((ar.punteroaVertice.y+30-y)-25*Math.sin (angulo+Math.toRadians (45)));
                    g.drawLine(ar.punteroaVertice.x+30-x,ar.punteroaVertice.y+30-y,x1,y1);
                }
            }
        }
        g2.setStroke(new BasicStroke());
    }
    
    public void limpiarPantalla(){
        for(int i = 0; i < vg.size(); i++){
            remove(vg.get(i));
        }
    } 
    
    public void acomodar(){
        int c = 0;
        for(Vertice temp = g.ini; temp != null; temp = temp.sig){
            vg.get(c).setLocation(temp.x, temp.y);
            c++;
        }
    }
    
    public void nuevasVertices(){
        int i=0;
        for(Vertice temp = g.ini; temp != null; temp = temp.sig){
            vg.add(new VerticeGrafica(temp,this));
            add(vg.get(i));
            i++;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(pros == 1){
            crearVertice(e);
        }
        if(getCursor().getType() == Cursor.HAND_CURSOR){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if(org != null){
                    org.seleccionado = false;
                    org.repaint();
                    org = null;
            }
            pros = 0;
            
        }
    }
    
    private void crearVertice(MouseEvent e){
            Object v = JOptionPane.showInputDialog(this,"Valor para el vertice...");
            if(v == null){
                pros = 0;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                return;
            }
            if(g.insertar(v,e.getX()-30,e.getY()-30)){
                if(vg.add(new VerticeGrafica(g.buscarVertice(v),this))){
                    this.add(vg.get(vg.size()-1));
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    pros = 0;
                }
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
}

