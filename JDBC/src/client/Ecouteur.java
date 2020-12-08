package client;

import javax.swing.*;

import Kahoot.Joueur;
import Kahoot.Partie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class Ecouteur extends Thread {
    private ObjectInputStream reader;
    private JTextArea textArea;
    private ApplicationClient client;
    

    public Ecouteur(ObjectInputStream reader, JTextArea textArea,ApplicationClient client) {
        this.reader = reader;
        this.textArea = textArea;
        this.client=client;
    }

    @Override
    public void run() {
        Object obj;
        try {
            while (!Thread.currentThread().isInterrupted() && (obj = reader.readObject())!= null) {
                //System.out.println("Client : " + msg);
                //textArea.append(msg + "\n");
                if (obj instanceof Joueur) {
                	Joueur joueur=(Joueur)obj;
                	client.setIdJoueur(joueur.getIdJoueur());
                	client.Authentified();
				}
                if (obj instanceof String ) {
                	String msg=(String)obj;
                	if (msg.equals("LANCE") ) {
                		client.partieLance();
					}

                	if (msg.equals("FINI")) {
                		client.partieFini();
					}
                	
				}
                if (obj instanceof Integer ) {
                	Integer partie=(Integer)obj;
                	client.setIdPartie(partie);
                	//client.Authentified();
				}
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.interrupt();
    }
}
