package client;

import Kahoot.Connexion;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Ecouteur extends Thread {
    private ObjectInputStream ois;
    private JTextArea textArea;
    private ApplicationClient client;
    

    public Ecouteur(ObjectInputStream ois, JTextArea textArea,ApplicationClient client) {
        this.ois = ois;
        this.textArea = textArea;
        this.client=client;
    }

    @Override
    public void run() {
        Object obj;
        try {
            while (!Thread.currentThread().isInterrupted() && (obj = ois.readObject())!= null) {
		    if (obj instanceof Connexion){
		    Connexion con = (Connexion) obj;
		    monApp.setIdPartie(con.getIdPartie());
		    monApp.setIdPartie(con.getIdJoueur());
		    monApp.authentified();
                }
             
                if (obj instanceof String) {
			 String msg = (String) obj;
			if(msg == "LANCE"){
				client.partieLance();
			}
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
