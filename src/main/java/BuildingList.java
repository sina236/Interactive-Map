import javax.swing.*;
import java.awt.*;


class BuildingList extends JFrame {

    // Components of the Form
    private Container c;

    // constructor, to initialize the components
    // with default values.
    public BuildingList()
    {
        setTitle("Western University Maps - Choose Building");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        ImageIcon icon = new ImageIcon("img/uwo.png");
        JLabel uwoImg = new JLabel(icon);
        uwoImg.setLocation(15, 15);
        uwoImg.setSize(500, 150);
        c.add(uwoImg);

        JLabel name = new JLabel("Maps");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(300, 150);
        name.setLocation(550, 15);
        c.add(name);

        setVisible(true);
    }
}
