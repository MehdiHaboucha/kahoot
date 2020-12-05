package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Ecouteur extends Thread {
    BufferedReader reader;
    JTextArea textArea;

    public Ecouteur(BufferedReader reader, JTextArea textArea) {
        this.reader = reader;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (!Thread.currentThread().isInterrupted() && (msg = reader.readLine()) != null) {
                System.out.println("Client : " + msg);
                textArea.append(msg + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }
}
