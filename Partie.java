package Kahoot;

import server.Connexion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Partie extends Thread implements Runnable{
    private int IdPartie;
    private java.sql.Date date;
    private List<Connexion> mesConnexions = new ArrayList<>();
    private String catChoisie;
    private int nbJoueurs;
    private boolean EnCours;
    private boolean creee;


    public Partie() {
        this.creee = true;

        System.out.println("combien de joueurs attendus dans la partie?");
        Scanner sc = new Scanner(System.in);
        this.nbJoueurs = Integer.parseInt(sc.nextLine());

        System.out.println("quel sera le thème de la partie?");
        this.catChoisie = sc.nextLine();
    }

    public int getIdPartie() {
        return IdPartie;
    }

    public void setCatChoisie(String catChoisie) {
        this.catChoisie = catChoisie;
    }

    public Date getDate() {
        return date;
    }

    public void setIdPartie(int idPartie) {
        IdPartie = idPartie;
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

    @Override
    public void run(){
        this.EnCours = true;
        while (!Thread.currentThread().isInterrupted()) {

            System.out.println("Partie en cours");
            try {
                //TODO: ajout partie en bdd avec tout ce qui va bien
                System.out.println(nbJoueurs);
                System.out.println(catChoisie);
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



}
