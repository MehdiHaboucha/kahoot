package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import Kahoot.Partie;
public class Serveur extends Thread {
    private ServerSocket serverSocket;
    private final List<Connexion> connexions = new ArrayList<>();
    private final List<Connexion> connexionsEnAttente = new ArrayList<>();
    private Partie maPartie= new Partie();

    public Serveur() throws IOException {
        serverSocket = new ServerSocket(60000);

    }

    @Override
    public void run() {
        try {
			this.maPartie.avantLancement();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        @SuppressWarnings("unused")
		Checktimer monTimer = new Checktimer(this);
        while (!this.isInterrupted()) {
            try {

                Socket client = serverSocket.accept();
                synchronized (connexions) {
                    connexions.add(new Connexion(client, this));
                    connexions.get(connexions.size() - 1).start();
                }
                System.out.println("Nombre de connexion(s) : " + connexions.size());

            } catch (IOException e) {
                System.out.println("Fermeture du serveur");
                fermerSocketEcoute();
            }
        }

        fermerSocketEcoute();
        this.interrupt();
    }

    public Partie getMaPartie() {
        return maPartie;
    }

    private void fermerSocketEcoute() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Connexion> getConnexions() {
        return new ArrayList<>(connexions);
    }

    public List<Connexion> getConnexionsEnAttente() {
        return connexionsEnAttente;
    }

    public void setMaPartie(Partie maPartie) {
        this.maPartie = maPartie;
    }

    public static void main(String[] args) {
        try {
            Serveur serv = new Serveur();
            serv.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void removeConnexion(Connexion connexion) {
        connexions.remove(connexion);
    }
}
