
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @version 1.0.0
 * @author Hussein Abdallah
 */
public class NewPOIDialog extends JDialog{
    private JTextField nameField, categoryField, roomField, descField;
    private JButton addButton, cancelButton;
    private ArrayList<String> nameAndCat;
    
    /**
     * Constructor to create a dialogue to add a new POI
     * 
     * @param o the JFrame that is its owner
     * @param nameAndCat an array list to store the name and category to view after closing popup
     */
    public NewPOIDialog(JFrame o, ArrayList<String> nameAndCat) {
        super(o,"Add New POI", Dialog.ModalityType.DOCUMENT_MODAL);
        this.nameAndCat = nameAndCat;
        
        JPanel panel = new JPanel();
        String s = new String();
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel row = new JPanel();
        row.setLayout(new GridLayout());
        JLabel l = new JLabel("POI name");
        row.add(l);
        nameField=new JTextField(""); 
        row.add(nameField); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(row,gbc);
        
        row = new JPanel();
        row.setLayout(new GridLayout());
        l = new JLabel("Category");
        row.add(l);
        categoryField=new JTextField(""); 
        row.add(categoryField); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(row,gbc);
        
        row = new JPanel();
        row.setLayout(new GridLayout());
        l = new JLabel("Description");
        row.add(l);
        descField=new JTextField(""); 
        row.add(descField); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(row,gbc);
        
        row = new JPanel();
        row.setLayout(new GridLayout());
        l = new JLabel("Room Number");
        row.add(l);
        roomField=new JTextField(""); 
        row.add(roomField); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(row,gbc);
        
        row = new JPanel();
        row.setLayout(new GridLayout());
        addButton = new JButton("<html>Add POI</html>");
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                nameAndCat.add(nameField.getText());
                nameAndCat.add(categoryField.getText());
                nameAndCat.add(descField.getText());
                nameAndCat.add(roomField.getText());
                dispose();
            }
        });
        
        row.add(addButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        row.add(cancelButton); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(row,gbc);
        
        add(panel);
        
        setSize(row.getPreferredSize().width*2,row.getPreferredSize().height*8);
    }
}
