package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *
 * @author MEHDI
 */
public  class Bouton extends JButton implements MouseListener{
    private String name;
    public Bouton(){
        
    }
    public Bouton(String str){
        super(str);
        this.name=str;	 
        this.setPreferredSize(new Dimension(250, 100));
        this.setBackground(Color.yellow);
        this.addMouseListener(this);
    }
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
/*
  public void paintComponent(Graphics g){
Graphics2D g2d = (Graphics2D)g;
GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);
g2d.setPaint(gp);
g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
g2d.setColor(Color.white); 
g2d.drawString(this.name,this.getWidth() / 10,(this.getHeight() / 2) + 5);

}
*/
  public void mouseClicked(MouseEvent event) {
//Inutile d'utiliser cette méthode ici
}
  //Méthode appelée lors du survol de la souris
public void mouseEntered(MouseEvent event) {
//this.name="HABOUCHA";
}
//Méthode appelée lorsque la souris sort de la zone du bouton
public void mouseExited(MouseEvent event) { 
//this.name="Mehdi";
}
//Méthode appelée lorsque l'on presse le bouton gauche de la souris
public void mousePressed(MouseEvent event) { 

}
//Méthode appelée lorsque l'on relâche le clic de souris
public void mouseReleased(MouseEvent event) {



}
}

