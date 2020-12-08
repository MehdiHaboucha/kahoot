package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class Ecouteur extends Thread {
    private BufferedReader reader;
    private JTextArea textArea;
    private ApplicationClient client;
    

    public Ecouteur(BufferedReader reader, JTextArea textArea,ApplicationClient client) {
        this.reader = reader;
        this.textArea = textArea;
        this.client=client;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (!Thread.currentThread().isInterrupted() && (msg = reader.readLine()) != null) {
                System.out.println("Client : " + msg);
                //textArea.append(msg + "\n");
                if (Integer.parseInt(msg)>=0) {
                	client.setIdPartie(Integer.parseInt(msg));
					client.Authentified();
				}
                if (msg=="LANCE") {
					client.partieLance();
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
