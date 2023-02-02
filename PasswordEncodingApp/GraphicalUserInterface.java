import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GraphicalUserInterface extends JFrame{
    private JPanel gridPanel;
    private JTextField genTextBox;
    private JTextField enterPasswordHereTextField;
    private JTextField encryptedDecryptedPasswordTextField;
    private JTextField enterNameForNewTextField;
    private JTextField enterFileNameToTextField;
    private JButton clearAllButton;
    private JPanel genTextPanel;
    private JPanel genTextButtons;
    private JButton genTextButton;
    private JButton clearGenTextButton;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton clearPassEnterTextButton;
    private JButton clearFileTextButton;
    private JButton createFileButton;
    private JPanel enterPasswordPanel;
    private JPanel encryptDecryptButtonsPanel;
    private JPanel outputPasswordPanel;
    private JButton clearEncDecTextButton;
    private JPanel fileCreatePanel;
    private JPanel fileSavePanel;
    private JPanel clearAllPanel;
    private JButton clearCreateFileTextButton;
    private JButton saveToFileButton;

    /*
     *
     *
     */
    public GraphicalUserInterface()
    {
        setContentPane(gridPanel);
        setTitle("Encoding App");
        setSize(1200, 800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        /*
         *
         *
         */
        genTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO: Implement the generate text micro service */
            }
        });

        /*
         *
         *
         */
        clearGenTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genTextBox.setText("");
            }
        });

        /*
         *
         *
         */
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = enterPasswordHereTextField.getText();
                byte[] bytes = inputText.getBytes(StandardCharsets.UTF_8);
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                encryptedDecryptedPasswordTextField.setText(encodedString);
            }
        });

        /*
         *
         *
         */
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = enterPasswordHereTextField.getText();
                String decodedString = "";
                try {
                    byte[] decodedText = Base64.getDecoder().decode(inputText);
                    decodedString = new String(decodedText, StandardCharsets.UTF_8);
                }
                catch (IllegalArgumentException err)
                {
                    System.out.println("Could not decrypt text!");
                    decodedString = "Decrypt failed!";
                }
                encryptedDecryptedPasswordTextField.setText(decodedString);
            }
        });

        /*
         *
         *
         */
        clearPassEnterTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPasswordHereTextField.setText("");
            }
        });

        /*
         *
         *
         */
        clearEncDecTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptedDecryptedPasswordTextField.setText("");
            }
        });

        /*
         *
         *
         */
        createFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = enterNameForNewTextField.getText();
                fileName = fileName.concat(".txt");
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                    writer.write(encryptedDecryptedPasswordTextField.getText());
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        /*
         *
         *
         */
        clearFileTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNameForNewTextField.setText("");
            }
        });

        /*
         *
         *
         */
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO: Implement saving the text to a file */
            }
        });

        /*
         *
         *
         */
        clearCreateFileTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterFileNameToTextField.setText("");
            }
        });

        /*
         *
         *
         */
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genTextBox.setText("");
                enterPasswordHereTextField.setText("");
                encryptedDecryptedPasswordTextField.setText("");
                enterNameForNewTextField.setText("");
                enterFileNameToTextField.setText("");
            }
        });

    }
}
