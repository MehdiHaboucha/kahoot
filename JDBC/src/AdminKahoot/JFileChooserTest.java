package AdminKahoot;
import Kahoot.*;
import dao.RequetteBddKahoot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFileChooserTest {
    private final JFrame contentFrame;
    private final JButton buttonFichier;
    private final JButton buttonJoueur;
    private final JButton buttonEnvoyer;
    private final JTextField saisieTexte;	
    private final JLabel zoneMessage;
    private final JLabel zoneMessage2;

    public JFileChooserTest(){
        contentFrame= new JFrame("Gestion QuizzDB");
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentFrame.setLocationRelativeTo(null);
        contentFrame.setPreferredSize(new Dimension(600, 400));

        contentFrame.setLayout(null);
        buttonFichier = new JButton("Choix Fichier");
        buttonFichier.setBounds(100, 10, 130, 40);
        buttonJoueur= new JButton("Ajout Joueur");
        buttonJoueur.setBounds(300, 10, 130, 40);
        buttonEnvoyer= new JButton("Envoyer");
        buttonEnvoyer.setBounds(450, 150, 90, 30);

        contentFrame.add(buttonFichier);
        contentFrame.add(buttonJoueur);

        zoneMessage = new JLabel();
        zoneMessage.setBounds(100,70,500,50);
        zoneMessage.setFont (new Font("Sans Serif", Font.BOLD, 17));

        zoneMessage2 = new JLabel();
        zoneMessage2.setBounds(100,150,500,50);
        zoneMessage2.setFont (new Font("Sans Serif", Font.BOLD, 17));

        saisieTexte = new JTextField();
        saisieTexte.setBounds(100,110,300,30);
        saisieTexte.setFont (new Font("Sans Serif", Font.PLAIN, 20));
        saisieTexte.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    try {
                        ajoutJoueurBdd();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
        buttonFichier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    choixFichier();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        buttonJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajoutJoueur();
            }
        });
        buttonEnvoyer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ajoutJoueurBdd();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    public void choixFichier() throws SQLException, ClassNotFoundException {
        contentFrame.remove(zoneMessage);
        contentFrame.remove(saisieTexte);
        contentFrame.remove(buttonEnvoyer);
        contentFrame.remove(zoneMessage2);
        contentFrame.remove(buttonJoueur);
        fresh();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Json Documents (*.json)", "json"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            contentFrame.add(buttonJoueur);
            fresh();
            File selectedFile = fileChooser.getSelectedFile();
            String text ="Fichier : " + selectedFile.getName();
            zoneMessage.setText(text);
            contentFrame.add(zoneMessage);
            fresh();
            RequetteBddKahoot maRequette = new RequetteBddKahoot();
            if (maRequette.ImportJson(selectedFile.getAbsolutePath())) {
                text="Chargement Reussi !!";
                zoneMessage2.setText(text);
                zoneMessage2.setForeground(Color.BLUE);
                contentFrame.add(zoneMessage2);
                fresh();
            } else {
                text="Chargement Impossible !!";
                zoneMessage2.setText(text);
                zoneMessage2.setForeground(Color.red);
                contentFrame.add(zoneMessage2);
                fresh();
            }
        }else {
            contentFrame.add(buttonJoueur);
            fresh();
        }
    }

    public void fresh(){
        contentFrame.validate();
        contentFrame.repaint();
    }
    public void ajoutJoueur(){
        contentFrame.remove(zoneMessage);
        contentFrame.remove(zoneMessage2);
        zoneMessage.setText("Login :");
        contentFrame.add(zoneMessage);
        contentFrame.add(buttonEnvoyer);
        contentFrame.add(saisieTexte);
        fresh();
    }
    public void ajoutJoueurBdd() throws SQLException, ClassNotFoundException {
        RequetteBddKahoot maRequette = new RequetteBddKahoot();
        int res=maRequette.addJoueur(new Joueur(saisieTexte.getText()));
        if (res==-2){
            zoneMessage2.setText(saisieTexte.getText()+" exsite deja !!");
            zoneMessage2.setForeground(Color.red);
            contentFrame.add(zoneMessage2);
            fresh();
        }
        if (res==0){
            zoneMessage2.setText("Ajout Impossible!!");
            zoneMessage2.setForeground(Color.red);
            contentFrame.add(zoneMessage2);
            fresh();
        }if (res==1) {
            zoneMessage2.setText("Ajout Reussi !!");
            zoneMessage2.setForeground(Color.BLUE);
            contentFrame.add(zoneMessage2);
            fresh();
        }
        saisieTexte.setText("");
    }


    public static void main(String[] args) {
        JFileChooserTest myWindow = new JFileChooserTest();
        myWindow.contentFrame.pack();
        myWindow.contentFrame.setVisible(true);
    }
}