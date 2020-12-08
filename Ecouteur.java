package client;

import Kahoot.Joueur;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Ecouteur extends Thread {
    private BufferedReader reader;
    private ObjectInputStream ois;
    private JTextArea textArea;
    private ApplicationClient monApp;

    public Ecouteur(ObjectInputStream ois, BufferedReader reader, JTextArea textArea, ApplicationClient monApp) {
        this.reader = reader;
        this.textArea = textArea;
        this.monApp = monApp;
        this.ois = ois;
    }


    @Override
    public void run() {
        String msg;
        Object obj;
        try {
            while (!Thread.currentThread().isInterrupted()/* && (obj = ois.readObject())!= null*/) {
                /*System.out.println("Client : " + msg);
                textArea.append(msg + "\n");*/
                /*Joueur joueur = (Joueur) obj;
                monApp.setIdPartie(joueur.getIdJoueurs());
                monApp.authentified();*/
                sleep(10);
            }
        } catch (InterruptedException/*IOException |  | ClassNotFoundException*/ e) {
            e.printStackTrace();
        }
        this.interrupt();
    }
}
