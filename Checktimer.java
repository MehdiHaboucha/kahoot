package server;

import Kahoot.Partie;

import java.util.Timer;
import java.util.TimerTask;

public class Checktimer {
    private Serveur server;
    Timer timer = new Timer("Timer");

    public Checktimer(Serveur server) {
        this.server = server;
        long delay  = 0;
        long period = 6000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                if (!server.getMaPartie().isCreee()) {
                    System.out.println("creation nouvelle partie");
                    Partie maPartie = new Partie();
                    server.setMaPartie(maPartie);
                }

                if (!server.getMaPartie().isEnCours()) {
                    System.out.println("ma partie :"+server.getMaPartie());
                    System.out.println("co dans partie: "+server.getMaPartie().getMesConnexions().size()+ "nb joueurs requis"+server.getMaPartie().getNbJoueurs());
                    if (server.getMaPartie().getMesConnexions().size() == server.getMaPartie().getNbJoueurs()) {
                        server.getMaPartie().start();
                        System.out.println("encours ? :"+server.getMaPartie().isEnCours());
                    } else {
                        if (!server.getConnexionsEnAttente().isEmpty()) {
                            System.out.println("debut vidage" + server.getConnexionsEnAttente());
                            System.out.println(server.getConnexions().toString());
                            // on check le nb de joueurs demandes dans la prochaine partie
                            for (int i = 0; i < (server.getMaPartie().getNbJoueurs()); i++) {
                                //check si la liste d'attente est vide
                                if (server.getConnexionsEnAttente().size() == 0){
                                    return;
                                }
                                // ajout d'une connexion en attente à la partie
                                server.getMaPartie().ajoutConnexion(server.getConnexionsEnAttente().get(0));
                                for (int j = 0; j < (server.getConnexionsEnAttente().size() - 1); j++) {
                                    // décalage des connexions en attente vers la gauche
                                    server.getConnexionsEnAttente().set(j, server.getConnexionsEnAttente().get(j + 1));
                                }
                                // on supprime le dernier de la liste d'attente
                                server.getConnexionsEnAttente().remove(server.getConnexionsEnAttente().get(server.getConnexionsEnAttente().size() - 1));
                                System.out.println(server.getConnexionsEnAttente().toString());
                            }
                            System.out.println("fin vidage" + server.getConnexionsEnAttente());
                        }
                    }
                }
            }
        };


}
