
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class VerticeGrafica extends Canvas implements MouseListener,MouseMotionListener{
    private Point inicio;
    public boolean dentro, seleccionado;
    Vertice r;
    Lienzo li;
    
    public VerticeGrafica(Vertice referencia, Lienzo refer){
        r = referencia;
        li = refer;
        this.setBounds(r.x, r.y, 60, 60);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        dentro = false;
    }
    
 public void paint(Graphics g){
         Graphics2D g2 = (Graphics2D)g;
         g.setColor(Color.BLACK);
         if(dentro == true){g2.setStroke(new BasicStroke(2));}else{g2.setStroke(new BasicStroke());}
         g.drawOval(0,0, 60, 60);
         g.setColor(Color.RED);
         g.fillOval(0,0, 60, 60);
         g.setColor(Color.YELLOW);
         g.setFont(new Font("Tahoma",Font.BOLD,20));
         g.drawString(r.valor.toString(), 20, 34);
         if(seleccionado){
         g.setColor(Color.YELLOW);
         g.fillOval(25,10, 10,10);
         g.setColor(Color.BLACK);
         }
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(li.getCursor().getType() == Cursor.HAND_CURSOR){
            if(li.org == null){
                li.org = this;
                seleccionado = true;
                repaint();
            }else if(li.org != null && li.dest == null){
                li.dest = this;
            }
                li.notificar();
        }else
        if(li.pros == 1){
            li.pros = 0;
        li.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
       this.inicio = new Point(e.getLocationOnScreen().x - getLocation().x,e.getLocationOnScreen().y - getLocation().y);
        if(li.getCursor().getType() != Cursor.HAND_CURSOR){
       setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        r.x = getLocation().x;
        r.y = getLocation().y;
        li.repaint();
       setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(li.getCursor().getType() != Cursor.HAND_CURSOR){
        dentro = true;
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        repaint();
        }else{
        setCursor(new Cursor(Cursor.HAND_CURSOR));}
    }

    @Override
    public void mouseExited(MouseEvent e) {
        dentro = false;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point posicion = new Point(e.getLocationOnScreen().x-inicio.x,e.getLocationOnScreen().y-inicio.y);
       if(estaDentro(posicion)){
        setLocation(posicion);
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    
    public boolean estaDentro(Point p){
        if ((p.x >= 0) && (p.x+60 <= li.getWidth()) && (p.y >= 0) && (p.y+60 <= li.getHeight()))
        {
            return true;
        }

        return false;
    }
    
}
