package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.Timer;

import Kahoot.Partie;
import Kahoot.Question;
import Kahoot.Reponse;
import dao.RequetteBddKahoot;

public class JeuQuestion implements ActionListener{
	private List<Question> lq= new ArrayList<Question>();
	private Partie partie= new Partie();
	private List<Reponse> lr= new ArrayList<Reponse>();
	private List <Bouton> reponses=new ArrayList<Bouton>();
	private JLabel label = new JLabel();
	private JLabel score = new JLabel();
	private int idJoueur;
	private static int i;

	public JeuQuestion() {

	}

	public JeuQuestion(Partie partie,List<Question> lq, List<Reponse> lr, int i,List <Bouton> reponses,JLabel label,JLabel score,int idJoueur)  {
		this.partie=partie;
		this.lq = lq;
		this.lr = lr;
		this.i = i;
		this.reponses=reponses;
		this.label=label;
		this.score=score;
		this.idJoueur=idJoueur;
	}


	public List<Question> getLq() {
		return lq;
	}


	public void setLq(List<Question> lq) {
		this.lq = lq;
	}


	public List<Reponse> getLr() {
		return lr;
	}


	public void setLr(List<Reponse> lr) {
		this.lr = lr;
	}


	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public int AllumerBouton(List<Question>lq,List<Reponse>lr,List <Bouton>reponses) {
		
		//5 cas possible
		//1er cas le joueur appui sur le bouton A
		if(reponses.get(0).isEnabled()==true && reponses.get(0).getBackground()==Color.blue) {

			if(lr.get(0).getIdReponse()==lq.get(i).getidbonneReponse()) {
				System.out.println("bonne reponse");
				reponses.get(0).setBackground(Color.green);
				reponses.get(0).setEnabled(false);
				return lr.get(0).getIdReponse();
			}
			else {
				reponses.get(0).setBackground(Color.red);
				System.out.println(reponses.get(0).getText());
				reponses.get(0).setEnabled(false);
				System.out.println("mauvaise reponse");
				return lr.get(0).getIdReponse();
			}
			

		}
		//2eme case le joueur appui sur le bouton B
		if(reponses.get(1).isEnabled()==true && reponses.get(1).getBackground()==Color.blue) {

			if(lr.get(1).getIdReponse()==lq.get(i).getidbonneReponse()) {
				reponses.get(1).setBackground(Color.green);
				reponses.get(1).setEnabled(false);
				return lr.get(1).getIdReponse();	
			}
			else {
				reponses.get(1).setBackground(Color.red);
				reponses.get(1).setEnabled(false);
				return lr.get(1).getIdReponse();
			}
			
		}
		//3 eme cas le joueur appui sur le bouton c
		if(reponses.get(2).isEnabled()==true && reponses.get(2).getBackground()==Color.blue ) {

			if(lr.get(2).getIdReponse()==lq.get(i).getidbonneReponse()) {
				reponses.get(2).setBackground(Color.green);
				reponses.get(2).setEnabled(false);
				return lr.get(2).getIdReponse();
			}
			else {
				reponses.get(2).setBackground(Color.red);
				reponses.get(2).setEnabled(false);
				return lr.get(2).getIdReponse();
			}
				
		}
		//4eme cas le joueur appui sur le bouton d
		if(reponses.get(3).isEnabled()==true && reponses.get(3).getBackground()==Color.blue ) {

			if(lr.get(2).getIdReponse()==lq.get(i).getidbonneReponse()) {
				reponses.get(3).setBackground(Color.green);
				reponses.get(2).setEnabled(false);
				return lr.get(3).getIdReponse();
			}
			else {
				reponses.get(3).setBackground(Color.red);
				reponses.get(2).setEnabled(false);
				return lr.get(3).getIdReponse();
			}
		}
		//5 eme cas le joueur n'appui sur aucun bouton
			reponses.get(0).setEnabled(false);
			reponses.get(1).setEnabled(false);
			reponses.get(2).setEnabled(false);
			reponses.get(3).setEnabled(false);
		return -1;
	}
	@Override
	public void actionPerformed(ActionEvent e)  {
		// TODO Auto-generated method stub

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				i++;
				if (lq.size()>i) {
					try {
						lr=new RequetteBddKahoot().ListerReponseByQuestion(lq.get(i).getIdQuestion());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				int j=i+1;
				System.out.println("18s----------");
				//reponses.get(0).setBackground(Color.red);
				if(i<lq.size()) {
					System.out.println(i);
					label.setText("categorie:"+lq.get(i).getIdCategorie()+"    Q"+j+"- "+lq.get(i).getTextQuestion());
					reponses.get(0).setLabel(lr.get(0).getTextReponse());
					reponses.get(1).setLabel(lr.get(1).getTextReponse());
					reponses.get(2).setLabel(lr.get(2).getTextReponse());
					reponses.get(3).setLabel(lr.get(3).getTextReponse());
					for (int k = 0; k < reponses.size(); k++) {
						reponses.get(k).setBackground(Color.yellow);
						reponses.get(k).setForeground(Color.black);
						reponses.get(k).setEnabled(true);
					}
				}
				else{
					label.setText("vous avez fini");
					reponses.get(0).setVisible(false);
					reponses.get(1).setVisible(false);
					reponses.get(2).setVisible(false);
					reponses.get(3).setVisible(false);
					//partie fini
					
					((Timer) e.getSource()).stop();
					((Timer) evt.getSource()).stop(); 
				}
				((Timer) evt.getSource()).stop(); 
				((Timer) e.getSource()).start();
			}
		};
		Timer timer2= new Timer(3000, taskPerformer);
		int reponse;
		boolean verifreponse;
		if(lq.size()>i) {
			System.out.println("15s*****************");	
			
			System.out.println(lq.get(i));
			System.out.println(lr);
			RequetteBddKahoot dao = null;
			try {
				dao = new RequetteBddKahoot();
			} catch (ClassNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			

			try {
				lr=new RequetteBddKahoot().ListerReponseByQuestion(lq.get(i).getIdQuestion());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				dao.attribuerScore(700,3,1);
				System.out.println("vrai");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("faux");
			}
			
			
			//change la couleur de la réponse  si bonne réponse en vert et mauvaise réponse en rouge
			//renvoie l'id de la réponse que le joueur a choisi
			reponse=AllumerBouton(lq,lr,reponses);
			// si c'est une bonne réponse
			if (lq.get(i).getidbonneReponse()==reponse) {
				try {
					dao.attribuerScore(dao.getScore(idJoueur, partie.getidPartie())+3,idJoueur, partie.getidPartie());
					dao.Historique(idJoueur, partie.getidPartie(), lq.get(i).getIdQuestion(), reponse, 3);
					score.setText("Score Question:"+3+"Score Total"+dao.getScore(idJoueur, partie.getidPartie()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			//si c'est une mauvaise réponse
			else if (reponse== -1) {
				try {
					dao.attribuerScore(dao.getScore(idJoueur, partie.getidPartie())+0,idJoueur, partie.getidPartie());
					dao.Historique(idJoueur, partie.getidPartie(), lq.get(i).getIdQuestion(), 0, 0);
					score.setText("Score Question:"+0+"Score Total"+dao.getScore(idJoueur, partie.getidPartie()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			else {
				try {
					dao.attribuerScore(dao.getScore(idJoueur, partie.getidPartie())+0,idJoueur, partie.getidPartie());
					dao.Historique(idJoueur, partie.getidPartie(), lq.get(i).getIdQuestion(),reponse, 0);
					score.setText("Score Question:"+0+"--------Score Total"+dao.getScore(idJoueur, partie.getidPartie()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
				
			System.out.println(reponse);
			
			//affichage du score
			try {
				System.out.println("Votre score est :"+dao.getScore(idJoueur, partie.getidPartie()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			((Timer) e.getSource()).stop();

			timer2.start();
		}
	}


}

