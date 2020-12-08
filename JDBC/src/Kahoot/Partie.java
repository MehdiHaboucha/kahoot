package Kahoot;

import server.Connexion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.RequetteBddKahoot;

public class Partie extends Thread implements Runnable{
    private int idPartie;
    private java.sql.Date date;
    private List<Connexion> mesConnexions = new ArrayList<>();
    private  int idCategorie;
    private int nbJoueurs;
    private boolean EnCours;
    private boolean creee;
    


    public Partie() {
        
    }

    public Partie(int idPartie,java.sql.Date date,int idCategorie,int nbJoueurs) throws ClassNotFoundException, SQLException {
    	this();
    	this.idPartie=idPartie;
    	this.date=date;
    	this.idCategorie=idCategorie;
    	this.nbJoueurs=nbJoueurs;
    }
    public int getIdPartie() {
		return idPartie;
	}

	public void setIdPartie(int idPartie) {
		this.idPartie = idPartie;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	public int getidPartie() {
        return idPartie;
    }

    public void setIdCategorie(int catChoisie) {
        this.idCategorie = idCategorie;
    }

    public Date getDate() {
        return date;
    }

    public void setidPartie(int idPartie) {
        idPartie = idPartie;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public boolean isEnCours() {
        return EnCours;
    }

    public boolean isCreee() {
        return creee;
    }

    public void ajoutConnexion(Connexion connexion){
        this.mesConnexions.add(connexion);
    }

    public List<Connexion> getMesConnexions() {
        return mesConnexions;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void avantLancement() throws ClassNotFoundException, SQLException {
    	this.creee = true;
    	List<Categorie>lc= new ArrayList<Categorie>();
        RequetteBddKahoot dao=new RequetteBddKahoot();
        lc=dao.listerCategorie();
        System.out.println(lc);
        Scanner sc = new Scanner(System.in);
        System.out.println("quel sera l'id de la categorie de la partie?");
        this.idCategorie=Integer.parseInt(sc.nextLine());
        System.out.println("combien de joueurs attendus dans la partie?");
        this.nbJoueurs = Integer.parseInt(sc.nextLine());
        
        this.idPartie=dao.addPartie(this.nbJoueurs , idCategorie);
        //System.out.println(this.idPartie);
    }
    @Override
    public void run(){
        this.EnCours = true;
        for (Connexion connexion : mesConnexions) {
        	 connexion.partieLance();
        	 
		}
     
        while (!Thread.currentThread().isInterrupted()) {

            System.out.println("Partie en cours");
            try {
                //TODO: ajout partie en bdd avec tout ce qui va bien
            	
               // System.out.println(nbJoueurs);
                //System.out.println(idCategorie);
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.interrupt();
            this.mesConnexions.clear();
        }
        System.out.println("sortie partie");
        this.EnCours = false;
        this.creee = false;
    }

	@Override
	public String toString() {
		return "Partie [idPartie=" + idPartie + ", date=" + date + ", idCategorie=" + idCategorie + ", nbJoueurs="
				+ nbJoueurs + "]";
	}
    



}