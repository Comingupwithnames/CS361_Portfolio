import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class GUI implements ActionListener {

    private int randNum;
    private String pathString = "[insert path to defaultImage.jpeg]";
    private JFrame window;
    private JButton button;
    private ImageIcon displayImage;
    private JLabel displayLabel;
    private JLabel numLabel;
    private JLabel pathLabel;
    private JPanel myPanel;
    public GUI ()
    {
        window = new JFrame();

        button = new JButton("Click to generate a random number");
        button.addActionListener(this);

        numLabel = new JLabel("Number generated: ");
        pathLabel = new JLabel("Path to image: ");

        displayImage = new ImageIcon(pathString);
        displayLabel = new JLabel("");

        myPanel = new JPanel();
        myPanel.setBorder(BorderFactory.createEmptyBorder(250,250,250,250));
        myPanel.setLayout(new GridLayout(2,2));
        myPanel.add(button);
        myPanel.add(numLabel);
        myPanel.add(pathLabel);
        myPanel.add(displayLabel);
        displayLabel.setIcon(displayImage);

        window.add(myPanel, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Sample GUI");
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        new GraphicalUserInterface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            BufferedWriter initialWriter = new BufferedWriter(new FileWriter("[insert path to prng-service.txt]"));
            initialWriter.write("run");
            initialWriter.close();
            TimeUnit.SECONDS.sleep(4);
            BufferedReader randReader = new BufferedReader((new FileReader("[insert path to prng-service.txt]")));
            randNum = parseInt(randReader.readLine());
            numLabel.setText("Number generated: " + randNum);
            randReader.close();
            BufferedWriter randWriter = new BufferedWriter(new FileWriter("[insert path to image-service.txt]"));
            randWriter.write("" + randNum);
            randWriter.close();
            TimeUnit.SECONDS.sleep(7);
            BufferedReader pathReader = new BufferedReader(new FileReader("[insert path to image-service.txt]"));
            pathString = pathReader.readLine();
            pathLabel.setText("Path to image: " + pathString);
            pathReader.close();
            displayLabel.setIcon(new ImageIcon(pathString));
        }
        catch (java.lang.Exception error) {
            error.printStackTrace();
        }


    }
}