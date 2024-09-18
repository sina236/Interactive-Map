package com.mycompany.western_interactive_map_application;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * Class to use to view maps, using floor plan images and a POI system
 * 
 * @version 1.0.0
 * @author Hussein Abdallah
 */
public class Map extends JFrame {

    private Container c;
    private Point mouseOrigin = new Point(0, 0); //used for panning
    private Point viewOrigin = new Point(0, 0); //used for panning
    private POISystem poiSystem; //system to load and save POIs
    private int numOfFloors = 0, curFloor = 0; //building info
    private JPanel optionsPanel; //panel on left with POIs
    private JScrollPane map; //map on left
    private float zoom = 1; //zoom of map
    private JComboBox floorSelector; //floor selection dropdown
    private JButton //zoomIn, zoomOut, 
            back; 
    private String curSearch = ""; //current search, empty string implies no search
    private boolean isAdmin; //if user is admin
    private String userName;
    
    /**
     * Creates the map on the left
     *
     * @param mapPath the folder the floor plans are in
     * @return a JScrollPane representing the map
     */
    private JScrollPane createMap(String mapPath) {
        Map curMap = this; //this JFrame is needed for later
        Icon ico = new ImageIcon(mapPath+"/0.jpg");
        JLabel map = new JLabel(ico);
        JScrollPane scrollPane = new JScrollPane(map) {
            @Override
            public void paintComponent(Graphics g) { //override the way component is painted so we can zoom
                //setSize((int) (getWidth()/zoom), (int) (getHeight()/zoom));
                if(floorSelector!=null) floorSelector.setBounds(this.getViewport().getViewPosition().x+this.getViewport().getWidth()-150, this.getViewport().getViewPosition().y+25, 100, 25);
                //if(zoomIn!=null) zoomIn.setBounds(this.getViewport().getViewPosition().x+this.getViewport().getWidth()-50, this.getViewport().getViewPosition().y+this.getViewport().getHeight()-85, 35, 35);
                //if(zoomOut!=null) zoomOut.setBounds(this.getViewport().getViewPosition().x+this.getViewport().getWidth()-50, this.getViewport().getViewPosition().y+this.getViewport().getHeight()-50, 35, 35);
                if(back!=null) back.setBounds(this.getViewport().getViewPosition().x+35, this.getViewport().getViewPosition().y+35, 35, 35);
                ((Graphics2D) g).scale(zoom, zoom);
                super.paintComponent(g);
            }
        };
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
        scrollPane.setAutoscrolls(true);
        
        // INITIALIZE AND ADD FLOOR SELECTOR
        String[] floors = new String[numOfFloors];
        for (int i = 0; i < numOfFloors; i++) {
            floors[i] = Integer.toString(i);
        }
        floorSelector = new JComboBox(floors);
        floorSelector.setSelectedIndex(0);
        floorSelector.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                curFloor = Integer.parseInt((String) floorSelector.getSelectedItem());
                Icon ico = new ImageIcon(mapPath+"/"+floorSelector.getSelectedItem()+".jpg");
                JLabel mapLabel = new JLabel(ico);
                mapLabel.add(floorSelector);
                //mapLabel.add(zoomIn);
                //mapLabel.add(zoomOut);
                mapLabel.add(back);
                scrollPane.getViewport().setView(mapLabel);
                scrollPane.revalidate();
                updateOptionsPanel();
            }
        });
        map.add(floorSelector);
        
        
        /*INITIALIZE ZOOM BUTTONS
        zoomIn = new JButton("<html>+</html>");
        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoom+=0.25;
                if (zoom==0) zoom+=0.25;
                scrollPane.repaint();
            }
        });
        map.add(zoomIn);
        zoomOut = new JButton("<html>-</html>");
        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoom-=0.25;
                if (zoom==0) zoom+=0.25;
                scrollPane.repaint();
            }
        });
        map.add(zoomOut);*/
        
        back = new JButton("<html>←</html>");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BuildingList(userName, isAdmin).setVisible(true);
                dispose();
            }
        });
        map.add(back);
        
        
        scrollPane.addMouseListener(mapClickAdapter());
        scrollPane.addMouseMotionListener(mapPanAdapter());
        return scrollPane;
    }
    
   /**
     * The functionality of clicking on map
     */
    private MouseAdapter mapClickAdapter() {
        JFrame curMap = this; //current frame for later
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //for panning, save original mouse position
                mouseOrigin = new Point(e.getPoint());
                JViewport viewPort = map.getViewport();
                if (viewPort != null) {
                    viewOrigin = viewPort.getViewPosition();
                }
                
                //to open right click menu
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Add POI");
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) { //if add poi button pressed, open dialogue menu to add poi, wait until finished, then update pane
                            ArrayList<String> nameAndCat = new ArrayList<String>();
                            NewPOIDialog d = new NewPOIDialog(curMap, nameAndCat);
                            d.setVisible(true);
                            if (!nameAndCat.isEmpty()) {
                                ImageIcon icon = new ImageIcon(new ImageIcon("img/greenLoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                                JLabel img = new JLabel(icon);
                                Dimension size = img.getPreferredSize();
                                Point coord = new Point(viewOrigin.x+mouseOrigin.x-size.width/2,viewOrigin.y+mouseOrigin.y-size.height);
                                String name = nameAndCat.get(0);
                                String category = nameAndCat.get(1);
                                String desc = nameAndCat.get(2);
                                String roomNumber = nameAndCat.get(3);
                                if (!name.isBlank() && !category.isBlank()) poiSystem.addPOI(coord, name, category, roomNumber, desc, (short) curFloor);
                                updateOptionsPanel();
                            }
                        }
                      });
                    popup.add(item);
                    popup.show(map, WIDTH, WIDTH);
                    popup.setLocation(e.getXOnScreen(),e.getYOnScreen());
                }
            }
        };
    }
    
    /**
     * Map panning functionality
     */
    private MouseMotionAdapter mapPanAdapter() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){ //manipulate viewport and pan if left mouse button dragged
                    JViewport viewPort = map.getViewport();
                    if (viewPort != null) {
                        int deltaX = mouseOrigin.x - e.getX();
                        int deltaY = mouseOrigin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        if ((int) (viewOrigin.x + deltaX) > 0 && (int) (viewOrigin.x + deltaX) < viewPort.getView().getWidth()-viewPort.getWidth())
                            view.x = (int) (viewOrigin.x + deltaX);
                        if ((int) (viewOrigin.y + deltaY) > 0 && (int) (viewOrigin.y + deltaY) < viewPort.getView().getHeight()-viewPort.getHeight())
                            view.y = (int) (viewOrigin.y + deltaY);
                        
                        ((JLabel) viewPort.getView()).scrollRectToVisible(view);
                    }
                }
            }
        };
    }

    /**
     * Function used to update POIs on every change in POIs, whether adding, removing, or searching
     */
    private void updateOptionsPanel() {
        optionsPanel.removeAll(); //remove all original elements
        
        // INITIALIZE SEARCH BAR
        JPanel helperPanel = new JPanel();
        JTextField searchBar = new JTextField(curSearch);
        if (curSearch.isBlank()) searchBar.setText("Enter Search Here");
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Enter Search Here")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                    curSearch = "";
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isBlank()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText("Enter Search Here");
                    curSearch = "";
                } else {
                    curSearch = searchBar.getText();
                }
            }
            });
        JButton searchButton = new JButton("search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                    ((JLabel) map.getViewport().getView()).removeAll();
                    ((JLabel) map.getViewport().getView()).add(floorSelector);
                    //((JLabel) map.getViewport().getView()).add(zoomIn);
                    //((JLabel) map.getViewport().getView()).add(zoomOut);
                    ((JLabel) map.getViewport().getView()).add(back);
                    updateOptionsPanel();
                }
            });
        JButton helpButton = new JButton("help");
        JFrame curMap = this;
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(curMap, "<html>To search, simply type your search and press search.<br>"
                        + "To add a POI, you can right click on the location, and press \"Add POI\".<br>"
                        + "To remove a POI, press on a POI that you have added and hit \"Remove POI\".<br>"
                        + "To favourite or unfavourite a POI, press on a POI and select \"Favourite\", or \"Unfavourite\".<br>"
                        + "To select floor, use the drop down in the top right of the map.<br>"
                        + "VERSION 1.0.0<br>"
                        + "Contact Info:<br>"
                        + "Hussein Abdallah: habdal@uwo.ca<br>"
                        + "Aun Mirza: amirza52@uwo.ca<br>"
                        + "Seyed Amir Sina Naziri (Sina): snaziri2@uwo.ca<br>"
                        + "Chi Ho Li (Sunny): cli2336@uwo.ca<br>"
                        + "Jacob Melroy Price: jprice83@uwo.ca</html>");
            }
        });
        helperPanel.add(searchBar);
        helperPanel.add(searchButton);
        helperPanel.add(helpButton);
        helperPanel.setMaximumSize( helperPanel.getPreferredSize() );
        optionsPanel.add(helperPanel);
        
        //INITIALIZE BOOKMARKED POIs
        JLabel name;
        if (poiSystem.getBookmarkedPOIs()!= null && !poiSystem.getBookmarkedPOIs().get(curFloor).isEmpty()){
            helperPanel = new JPanel();
            name = new JLabel("Favourite POIs", JLabel.CENTER);
            name.setFont(new Font("Arial", Font.BOLD, 20));
            helperPanel.add(name);
            helperPanel.setMaximumSize( helperPanel.getPreferredSize() );
            optionsPanel.add(helperPanel);
            for (POI POIObj : poiSystem.getBookmarkedPOIs().get(curFloor)) {
                if(curSearch.isBlank() || POIObj.getName().contains(curSearch) || POIObj.getCategory().contains(curSearch)){
                    addPOIToPanel(POIObj);
                }
            }
        }
        
        //INITIALIZE UNIVERSAL/ADMIN ADDED POIs
        helperPanel = new JPanel();
        name = new JLabel("POIs", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        helperPanel.add(name);
        helperPanel.setMaximumSize( helperPanel.getPreferredSize() );
        optionsPanel.add(helperPanel);
        if (poiSystem.getUniversalPOIs() != null)
            for (POI POIObj : poiSystem.getUniversalPOIs().get(curFloor)) {
                if(curSearch.isBlank() || POIObj.getName().contains(curSearch) || POIObj.getCategory().contains(curSearch)){
                    addPOIToPanel(POIObj);
                }
            }
        
        //INITIALIZE CUSTOM ADDED POIs
        helperPanel = new JPanel();
        name = new JLabel("Custom POIs", JLabel.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, 20));
        helperPanel.add(name);
        helperPanel.setMaximumSize( helperPanel.getPreferredSize() );
        optionsPanel.add(helperPanel);
        if (poiSystem.getUserPOIs()!= null)
            for (POI POIObj : poiSystem.getUserPOIs().get(curFloor)) {
                if(curSearch.isBlank() || POIObj.getName().contains(curSearch) || POIObj.getCategory().contains(curSearch)){
                    addPOIToPanel(POIObj);
                }
            }
        
        map.repaint();
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
    
    /**
     * Function used to add a POI to the panel and the map
     */
    private void addPOIToPanel(POI POIObj) {
        //ADD MAP ICON
        ImageIcon icon = new ImageIcon(new ImageIcon("img/greenLoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        if (POIObj.isCustom()) icon = new ImageIcon(new ImageIcon("img/blueLoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JLabel img = new JLabel(icon);
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) { //if POI is pressed on, open viewing menu where you can favourite or delete
                JPopupMenu popup = new JPopupMenu();
                JMenuItem item = new JMenuItem(POIObj.getName());
                popup.add(item);
                item = new JMenuItem(POIObj.getCategory());
                popup.add(item);
                item = new JMenuItem(POIObj.getDesc());
                if (POIObj.getDesc().isBlank()) item = new JMenuItem("No description");
                popup.add(item);
                item = new JMenuItem(POIObj.getRoom());
                if (POIObj.getRoom().isBlank()) item = new JMenuItem("No room number");
                popup.add(item);
                if (!POIObj.isBookmarked()) item = new JMenuItem("Favourite");
                else item = new JMenuItem("Unfavourite");
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                            poiSystem.bookmarkPOI(POIObj);
                            updateOptionsPanel();
                        }
                    });
                popup.add(item);
                if (isAdmin || POIObj.isCustom()){
                    item = new JMenuItem("Delete POI");
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                poiSystem.removePOI(POIObj);
                                ((JLabel) map.getViewport().getView()).removeAll();
                                ((JLabel) map.getViewport().getView()).add(floorSelector);
                                //((JLabel) map.getViewport().getView()).add(zoomIn);
                                //((JLabel) map.getViewport().getView()).add(zoomOut);
                                ((JLabel) map.getViewport().getView()).add(back);
                                updateOptionsPanel();
                            }
                        });
                    popup.add(item);
                }
                popup.show(map, WIDTH, WIDTH);
                popup.setLocation(e.getXOnScreen(),e.getYOnScreen());
            }
        });
        Dimension size = img.getPreferredSize();
        img.setBounds(POIObj.getCoord().x, POIObj.getCoord().y, size.width, size.height);
        ((JLabel) map.getViewport().getView()).add(img);

        
        //ADD TO PANEL ON THE RIGHT
        JPanel helperPanel = new JPanel();
        JLabel name = new JLabel(POIObj.getName(), JLabel.CENTER);
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        helperPanel.add(name);
        helperPanel.setMaximumSize( helperPanel.getPreferredSize() );
        helperPanel.addMouseListener(new MouseAdapter() { //when clicked on, pan to location and open menu to favourite or remove poi
            @Override
            public void mousePressed (MouseEvent e) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem item = new JMenuItem(POIObj.getName());
                popup.add(item);
                item = new JMenuItem(POIObj.getCategory());
                popup.add(item);
                item = new JMenuItem(POIObj.getDesc());
                if (POIObj.getDesc().isBlank()) item = new JMenuItem("No description");
                popup.add(item);
                item = new JMenuItem(POIObj.getRoom());
                if (POIObj.getRoom().isBlank()) item = new JMenuItem("No room number");
                popup.add(item);
                if (!POIObj.isBookmarked()) item = new JMenuItem("Favourite");
                else item = new JMenuItem("Unfavourite");
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                            poiSystem.bookmarkPOI(POIObj);
                            updateOptionsPanel();
                        }
                    });
                popup.add(item);
                JViewport viewPort = map.getViewport();
                if (isAdmin) item = new JMenuItem("Delete POI");
                if (viewPort != null) {
                    if (isAdmin){
                        item.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                    poiSystem.removePOI(POIObj);
                                    ((JLabel) viewPort.getView()).removeAll();
                                    ((JLabel) viewPort.getView()).add(floorSelector);
                                    //((JLabel) viewPort.getView()).add(zoomIn);
                                    //((JLabel) viewPort.getView()).add(zoomOut);
                                    ((JLabel) viewPort.getView()).add(back);
                                    updateOptionsPanel();
                                }
                              });
                        popup.add(item);
                    }
                    Rectangle view = viewPort.getViewRect();
                    if ((int) (POIObj.getCoord().x-viewPort.getWidth()/2) > 0)
                        view.x = (int) (POIObj.getCoord().x-viewPort.getWidth()/2);
                    else 
                        view.x = 0;
                    if ((int) (POIObj.getCoord().y-viewPort.getHeight()/2) > 0)
                        view.y = (int) (POIObj.getCoord().y-viewPort.getHeight()/2);
                    else
                        view.y = 0;

                    ((JLabel) viewPort.getView()).scrollRectToVisible(view);
                }
                popup.show(map, WIDTH, WIDTH);
                Point location = new Point(POIObj.getCoord().x+size.width/2, POIObj.getCoord().y+size.height);
                SwingUtilities.convertPointToScreen(location, viewPort.getView());
                popup.setLocation(location);
            }
        });
        optionsPanel.add(helperPanel);
    }
    
    /**
     * Constructor to create map
     *
     * @param buildingName the name of the building, must match the field in the POIs.json file
     * @param buildingFolder the folder name to building floor maps
     * @param userName the username of the user
     * @param isAdmin whether the user is admin or not
     */
    public Map(String buildingName, String buildingFolder, String userName, boolean isAdmin)
    {
        super("Western University Maps - "+ buildingName);
        setSize(900, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        
        c = getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        this.isAdmin = isAdmin;
        this.userName = userName;
        poiSystem = new POISystem(buildingName, userName, isAdmin);
        numOfFloors = poiSystem.getNumOfFloors();
        
        map = createMap("img/"+buildingFolder);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        c.add(map, gbc);
        
        optionsPanel = new JPanel();
        JScrollPane scrollingPane = new JScrollPane(optionsPanel);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateOptionsPanel();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4;
        gbc.weighty = 0;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        c.add(scrollingPane, gbc);
    }
    
}
