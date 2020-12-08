package kahootNonMultijoueur;	

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Kahoot.Partie;
import Kahoot.Question;
import Kahoot.Reponse;
import client.Bouton;
import client.JeuQuestion;
import dao.RequetteBddKahoot;


public class JeuKahoot extends JFrame implements ActionListener   {
	
	private JPanel panel = new JPanel();
	 private JLabel question = new JLabel();
	 private JLabel score = new JLabel();
    List <Bouton> reponses=new ArrayList<Bouton>();
   
	 public JeuKahoot(int idPartie,int idJoueur) throws ClassNotFoundException, SQLException{
	        
	        //*********************Jframe*************************
	   //Définit un titre pour notre fenêtre
	this.setTitle("Jeu Kahoot");
	//Définit sa taille : 400 pixels de large et 100 pixels de haut
	this.setSize(600, 300);
	//Nous demandons maintenant à notre objet de se positionner au centre
	this.setLocationRelativeTo(null);
	//Termine le processus lorsqu'on clique sur la croix rouge
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//empecher le redimensionnement de la fenetre
	this.setResizable(false);
	//garder la fenetre au premier plan
	//this.setAlwaysOnTop(true);
	//Retirer les contours et les boutons de contrôle
	//this.setUndecorated(true);	
	//Définition de sa couleur de fond
	//pan.setBackground(Color.ORANGE);
	panel.add(question);
	this.setContentPane(panel);
	//Ajout du bouton à notre content pane
	
	reponses.add(new Bouton("A"));
	reponses.add(new Bouton("B"));
	reponses.add(new Bouton("C"));
	reponses.add(new Bouton("D"));
    panel.add(reponses.get(0));
    this.setContentPane(panel);
    reponses.get(0).addActionListener(this);
    panel.add(reponses.get(1));
    this.setContentPane(panel);
    reponses.get(1).addActionListener(this);
    panel.add(reponses.get(2));
    reponses.get(2).addActionListener(this);
    this.setContentPane(panel);
    panel.add(reponses.get(3));
    this.setContentPane(panel);
    reponses.get(3).addActionListener(this);
    score.setText("Monsieur votre score est de:");
    panel.add(score);
	this.setContentPane(panel);
	 
    affichageQuestionReponse(idPartie,reponses,question,score,idJoueur);
	//Et enfin, la rendre visible
	this.setVisible(true); 
	
	}
	 
	 public static void main(String[] args) throws ClassNotFoundException, SQLException {
		 JeuKahoot j=new JeuKahoot(16,5);
		 
	}
	 
	 
	 
	 @SuppressWarnings("deprecation")
		public void affichageQuestionReponse(int idPartie,List <Bouton> reponses,JLabel question,JLabel score,int idJoueur) throws SQLException, ClassNotFoundException {
			 RequetteBddKahoot dao= new RequetteBddKahoot();
			 List<Question> lq= new ArrayList<Question>();	
			 List<Reponse> lr= new ArrayList<Reponse>();
			 Partie p=new Partie();
			 System.out.println(idPartie);
			p=dao.findPartieById(idPartie);
			System.out.println(p);
			System.out.println(p.getIdCategorie());
			 lq=dao.listerQuestionsByCategorie(p.getIdCategorie());
			 System.out.println(lq);
			//Teamps d'attente pour répondre
			  int delay = 6000;
			  int i=0;
			  
			  //class action listener laquelle on va gérer le temps entre les fonctions
			 JeuQuestion q= new JeuQuestion(p,lq,lr,i,reponses,question,score,idJoueur);
			 
			 Timer timer = new Timer( delay,q);
			 timer.setRepeats(true);
			 //récuperer les réponses pour chaque question
			 lr=dao.ListerReponseByQuestion(lq.get(0).getIdQuestion());
			 
			 //affichage initial: première question
			 question.setText("categorie:"+lq.get(0).getIdCategorie()+"    Q"+1+"- "+lq.get(0).getTextQuestion());
				 reponses.get(0).setLabel(lr.get(0).getTextReponse());
				  reponses.get(1).setLabel(lr.get(1).getTextReponse());
				  reponses.get(2).setLabel(lr.get(2).getTextReponse());
				  reponses.get(3).setLabel(lr.get(3).getTextReponse());
			 timer.start();
		 }
		 
		
		
		
		 
		 public void actionPerformed(ActionEvent arg0) {
			 if(arg0.getSource()==reponses.get(0)) {
				 // reponses.get(0).setBackground(Color.blue);
				 	
				 reponses.get(0).setBackground(Color.blue);
				//changer la couleur de text du bouton
				 reponses.get(0).setForeground(Color.WHITE);
				 reponses.get(1).setEnabled(false);
				 reponses.get(2).setEnabled(false);
				 reponses.get(3).setEnabled(false);
				
						
				 
			 }
			 if(arg0.getSource()== reponses.get(1)) {
				 reponses.get(1).setBackground(Color.blue);
				//changer la couleur de text du bouton
				 reponses.get(1).setForeground(Color.WHITE);
				 reponses.get(0).setEnabled(false);
				 reponses.get(2).setEnabled(false);
				 reponses.get(3).setEnabled(false);
			 }
			 if(arg0.getSource()== reponses.get(2)) {
				 // reponses.get(2).setBackground(Color.red);
				//changer la couleur de text du bouton
				 reponses.get(2).setForeground(Color.WHITE);
				 reponses.get(2).setBackground(Color.blue);
				 reponses.get(1).setEnabled(false);
				 reponses.get(0).setEnabled(false);
				 reponses.get(3).setEnabled(false);
			 }
			 if(arg0.getSource()== reponses.get(3)) {
				 // reponses.get(3).setBackground(Color.green);
				//changer la couleur de text du bouton
				 reponses.get(3).setForeground(Color.WHITE);
				 reponses.get(3).setBackground(Color.blue);
				 reponses.get(1).setEnabled(false);
				 reponses.get(2).setEnabled(false);
				 reponses.get(0).setEnabled(false);
			 }
		 }
	 
	 
}
