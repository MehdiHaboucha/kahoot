package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Connexion extends Thread {
    private Serveur server;
    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    private String id;

    public Connexion(Socket client, Serveur server) throws IOException {
        this.server = server;
        this.client = client;
        // read
        InputStreamReader input = new InputStreamReader(this.client.getInputStream());
        reader = new BufferedReader(input);
        // write
        OutputStreamWriter output = new OutputStreamWriter(this.client.getOutputStream());
        writer = new PrintWriter(output);
    }

    private synchronized void envoyerMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    private void entrerDansPartie(){
        server.getMaPartie().ajoutConnexion(this);
        System.out.println(server.getMaPartie().getMesConnexions());
    }
    private synchronized boolean authentification(String pseudo) {
        if(pseudo.equals("admin")){
            //TODO : verif bdd plus info vers appli client comme quoi log plus impossible de se relog
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        String msg;
        try {
            while ((msg = reader.readLine()) != null) {
                System.out.println("Serveur : " + msg);
                if (msg.equals("CONNEXION_CLOSED")) {
                    client.close();
                    server.removeConnexion(this);
                    return;
                    //TODO : potentiellement faire cela quand la partie se termine avec les connexions de la partie
                }
                if (this.authentification(msg)) {
                    if (!server.getMaPartie().isEnCours()) {
                        if (server.getConnexionsEnAttente().isEmpty()) {
                            if (server.getMaPartie().getMesConnexions().size() < server.getMaPartie().getNbJoueurs()){
                                this.entrerDansPartie();
                            }else{
                                // si la partie est pleine et donc prete a etre lancee on part en attente
                                server.getConnexionsEnAttente().add(this);
                                System.out.println("co en attente:"+server.getConnexionsEnAttente());
                            }

                        }else{
                            // si il y a d'autres connexions en attente on part en file d'attente
                            server.getConnexionsEnAttente().add(this);
                            System.out.println("co en attente:"+server.getConnexionsEnAttente());
                        }
                    }else{
                        // si une partie est en cours on part en file d'attente
                        server.getConnexionsEnAttente().add(this);
                        System.out.println("co en attente:"+server.getConnexionsEnAttente());
                    }

                }
            }

        } catch (IOException e) {
            if (e instanceof SocketException) {
                try {
                    client.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                finally {
                    server.removeConnexion(this);
                }
                return;
            }
            e.printStackTrace();
        }
    }

    public synchronized void setId(String id) {
        this.id = id;
    }
}
