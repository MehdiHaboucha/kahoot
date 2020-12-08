package client;

import javax.swing.*;

import Kahoot.Partie;
import Kahoot.Question;
import Kahoot.Reponse;
import dao.RequetteBddKahoot;
import kahootNonMultijoueur.JeuKahoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationClient extends JFrame implements ActionListener {
    private JPanel panel= new JPanel();
    private JButton buttonAuthentification=new JButton("Authentification");
    private JButton buttonExit= new JButton("Exit");
    private JLabel pseudo=new JLabel("Pseudo");
    private JTextArea textAreaInfo= new JTextArea();
    private JTextField nameFieldPseudo=new JTextField("");
    private ObjectInputStream ois;
    private PrintWriter writer;
    private Ecouteur listener;
    private int idPartie;
    private int idJoueur;
    private JLabel question = new JLabel();
	 private JLabel score = new JLabel();
   List <Bouton> reponses=new ArrayList<Bouton>();
   
   
   
  



	private Socket client;

    public ApplicationClient() throws ClassNotFoundException, SQLException, IOException {
    	
    	
    	
        setUpConnexion();
        //Définit un titre pour notre fenêtre
    	this.setTitle("Authentification");
    	//Définit sa taille : 400 pixels de large et 100 pixels de haut
    	this.setSize(600, 300);
    	//Nous demandons maintenant à notre objet de se positionner au centre
    	this.setLocationRelativeTo(null);
    	//Termine le processus lorsqu'on clique sur la croix rouge
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	//empecher le redimensionnement de la fenetre
    	this.setResizable(false);
    	
    	
        //JeuKahoot j=new JeuKahoot();
        setContentPane(panel);
        panel.add(this.pseudo);
        this.nameFieldPseudo.setPreferredSize(new Dimension(100,26));
        panel.add(this.nameFieldPseudo);
        panel.add(this.textAreaInfo);
        panel.add(buttonAuthentification);
        panel.add(this.buttonExit);
       
       
        getRootPane().setDefaultButton(buttonAuthentification);

        buttonAuthentification.addActionListener(e -> onAuthentification());

        buttonExit.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        panel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        
        listener = new Ecouteur(ois, textAreaInfo,this);
        listener.start();	
      //Et enfin, la rendre visible
    	this.setVisible(true); 
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
    	
        ApplicationClient appClient = new ApplicationClient();
        //JeuKahoot j=new JeuKahoot(appClient.idPartie,appClient.idJoueur);
    }
    
    public void partieLance() throws ClassNotFoundException, SQLException {
    	this.panel.removeAll();
    	//Jeu Kahoot
    	jeuKahoot(this.idPartie,this.idJoueur);
    }

    public void Authentified() {
    	//this.setVisible(false);
    	buttonAuthentification.setEnabled(false);
    	buttonAuthentification.setVisible(false);
    }
    public void onAuthentification() {
        String pseudo = nameFieldPseudo.getText();
        writer.println(pseudo);
        writer.flush();
        nameFieldPseudo.setText("");
        // supprimer l'affichage
    }

    public void onCancel() {
        dispose();
    }

    public void setUpConnexion(){
        try {
            client = new Socket(InetAddress.getLocalHost(), 60000);
            // read
            InputStream is = client.getInputStream();
            ois = new ObjectInputStream(is);
            // write
            OutputStreamWriter output = new OutputStreamWriter(this.client.getOutputStream());
            writer = new PrintWriter(output);
        } catch (IOException e) {
            textAreaInfo.append("Impossible de se connecter Ã  l'hÃ´te...");
        }
    }

    public void jeuKahoot(int idPartie,int idJoueur) throws ClassNotFoundException, SQLException {
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
 	 
     affichageQuestionReponse(idPartie,idJoueur,reponses,question);
 	//Et enfin, la rendre visible
 	this.setVisible(true); 
    }
    
    public int getIdPartie() {
  		return idPartie;
  	}

  	public void setIdPartie(int idPartie) {
  		this.idPartie = idPartie;
  	}
	public int getIdJoueur() {
  		return idJoueur;
  	}

  	public void setIdJoueur(int idJoueur) {
  		this.idJoueur = idJoueur;
  	}
    
    @SuppressWarnings("deprecation")
   	public void affichageQuestionReponse(int idPartie,int idJoueur,List <Bouton> reponses,JLabel question) throws SQLException, ClassNotFoundException {
   		 RequetteBddKahoot dao= new RequetteBddKahoot();
   		 List<Question> listeQuestion= new ArrayList<Question>();	
   		 List<Reponse> listeReponse= new ArrayList<Reponse>();
   		 Partie p=new Partie();
   		p=dao.findPartieById(idPartie);
   		listeQuestion=dao.listerQuestionsByCategorie(p.getIdCategorie());
   		 System.out.println(listeQuestion);
   		//Teamps d'attente pour répondre
   		  int delay = 6000;
   		  int i=0;
   		  
   		  //class action listener laquelle on va gérer le temps entre les fonctions
   		 JeuQuestion q= new JeuQuestion(p,listeQuestion,listeReponse,i,reponses,question,score,idJoueur);
   		 
   		 Timer timer = new Timer( delay,q);
   		 timer.setRepeats(true);
   		 //récuperer les réponses pour chaque question
   		listeReponse=dao.ListerReponseByQuestion(listeQuestion.get(0).getIdQuestion());
   		 
   		 //affichage initial: première question
   		 question.setText("categorie:"+listeQuestion.get(0).getIdCategorie()+"    Q"+1+"- "+listeQuestion.get(0).getTextQuestion());
   			 reponses.get(0).setLabel(listeReponse.get(0).getTextReponse());
   			  reponses.get(1).setLabel(listeReponse.get(1).getTextReponse());
   			  reponses.get(2).setLabel(listeReponse.get(2).getTextReponse());
   			  reponses.get(3).setLabel(listeReponse.get(3).getTextReponse());
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

    @Override
    public void dispose() {
        try {
            writer.println("CONNEXION_CLOSED");
            writer.flush();
            client.close();
            listener.interrupt();
        } catch (IOException e) {
            System.out.println("Client already closed");
        }
        super.dispose();
    }
    
}
