package com.mycompany.western_interactive_map_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Class to use to navigate the maps
 * 
 * @version 1.0.0
 * @author Aun Mirza
 */
class BuildingList extends JFrame {

    // Components of the Form
    private Container c;

    // constructor, to initialize the components
    // with default values.
    public BuildingList(String userName, boolean isAdmin)
    {
        //Sets properties of the window
        setTitle("Western University Maps - Choose Building");
        setBounds(0, 0, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        
        //Western Image
        ImageIcon westerIcon = new ImageIcon("img/uwo.png");
        JLabel uwoImg = new JLabel(westerIcon);
        uwoImg.setLocation(50, 15);
        uwoImg.setSize(500, 150);
        
        //Map text
        JLabel name = new JLabel("Maps");
        name.setFont(new Font("Arial", Font.PLAIN, 100));
        name.setSize(300, 150);
        name.setLocation(580, 15);
        
        JLabel suggestion = new JLabel("Please select the desired building");
        suggestion.setFont(new Font("Arial", Font.PLAIN, 25));
        suggestion.setSize(500, 150);
        suggestion.setLocation(245, 115);
        
        //UCC image
        ImageIcon uccIcon = new ImageIcon("img/ucc/UCC_CoverImage.jpg");
        JLabel uccImg = new JLabel(uccIcon);
        uccImg.setLocation(30, 225);
        uccImg.setSize(274, 280);
        
        //Natural Science Image
        ImageIcon nscIcon = new ImageIcon("img/nsc/NSC_CoverImage.jpeg");
        JLabel nscImg = new JLabel(nscIcon);
        nscImg.setLocation(280, 225);
        nscImg.setSize(336, 280);
        
        //Middlesex Image
        ImageIcon mcIcon = new ImageIcon("img/mc/MC_CoverImage.jpg");
        JLabel mcImg = new JLabel(mcIcon);
        mcImg.setLocation(595, 225);
        mcImg.setSize(274, 280);
        
        //Labels for all of the centres
        JLabel uccLabel = new JLabel("University Community Centre");
        uccLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        uccLabel.setSize(350, 100);
        uccLabel.setLocation(30, 475);
        
        JLabel nscLabel = new JLabel("Natural Science Centre");
        nscLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nscLabel.setSize(300, 100);
        nscLabel.setLocation(350, 475);
        
        JLabel mcLabel = new JLabel("Middlesex College");
        mcLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mcLabel.setSize(300, 100);
        mcLabel.setLocation(650, 475);
        
        //Opens the map based on the picture that is clicked
        uccImg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Map("University Community Centre", "ucc", "admin", isAdmin).setVisible(true);
                dispose();
            }
        });
        
        nscImg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Map("Natural Science Centre", "nsc", "admin", isAdmin).setVisible(true);
                dispose();
            }
        });
        
        mcImg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Map("Middlesex College", "mc", "admin", isAdmin).setVisible(true);
                dispose();
            }
        });
        
        //Adds contents to the screen
        c.add(name);
        c.add(suggestion);
        c.add(uwoImg);
        c.add(uccImg);
        c.add(nscImg);
        c.add(mcImg);
        c.add(uccLabel);
        c.add(nscLabel);
        c.add(mcLabel);
    }
}
