package client;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class ApplicationClient extends JDialog {
    private JPanel contentPane;
    private JButton buttonAuthentification;
    private JButton buttonExit;
    private JTextArea textAreaInfo;
    private JTextField nameFieldPseudo;
    private BufferedReader reader;
    private ObjectInputStream ois;
    private PrintWriter writer;
    private Ecouteur listener;
    private int idPartie;

    private Socket client;

    public ApplicationClient() {

        setUpConnexion();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonAuthentification);

        buttonAuthentification.addActionListener(e -> onAuthentification());

        buttonExit.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        listener = new Ecouteur(ois,reader, textAreaInfo,this);
        listener.start();
    }

    public static void main(String[] args) {
        ApplicationClient dialog = new ApplicationClient();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    private void onAuthentification() {
        String pseudo = nameFieldPseudo.getText();
        writer.println(pseudo);
        writer.flush();
        nameFieldPseudo.setText("");
    }
    public void authentified() throws IOException {
        System.out.println("bite");
        textAreaInfo.setText(String.valueOf(this.idPartie));
    }

    private void onCancel() {
        dispose();
    }

    private void setUpConnexion(){
        try {
            client = new Socket("127.0.0.1", 60000);
            // read
            InputStreamReader input = new InputStreamReader(this.client.getInputStream());
            reader = new BufferedReader(input);
            // write
            OutputStreamWriter output = new OutputStreamWriter(this.client.getOutputStream());
            writer = new PrintWriter(output);
            // read
            InputStream is = client.getInputStream();
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            textAreaInfo.append("Impossible de se connecter à l'hôte...");
        }
    }



    @Override
    public void dispose() {
        try {
            writer.println("CONNEXION_CLOSED");
            writer.flush();
            client.close();
            listener.interrupt();
        } catch (IOException e) {
            System.out.println("Client already closed");
        }
        super.dispose();
    }
}
