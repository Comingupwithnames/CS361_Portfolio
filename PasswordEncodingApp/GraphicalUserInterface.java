import org.json.JSONObject;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This class will define the constructor for our GUI which will
 * have functionality to generate text, encode/decode that text,
 * and save that text to a new file or an existing one.
 *
 */
public class GraphicalUserInterface extends JFrame{
    private JPanel gridPanel;
    private JPanel genTextPanel;
    private JPanel genTextButtons;
    private JPanel enterPasswordPanel;
    private JPanel encryptDecryptButtonsPanel;
    private JPanel outputPasswordPanel;
    private JPanel fileCreatePanel;
    private JPanel fileSavePanel;
    private JPanel clearAllPanel;
    private JTextField genTextBox;
    private JTextField enterPasswordHereTextField;
    private JTextField encryptedDecryptedPasswordTextField;
    private JTextField enterNameForNewTextField;
    private JTextField enterFileNameToTextField;
    private JButton clearAllButton;
    private JButton genTextButton;
    private JButton clearGenTextButton;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton clearPassEnterTextButton;
    private JButton clearFileTextButton;
    private JButton createFileButton;
    private JButton clearEncDecTextButton;
    private JButton clearCreateFileTextButton;
    private JButton saveToFileButton;
    private JSlider lengthSlider;

    public int textLength = 30; // Used to keep track of the slider length any time it is changed

    /**
     * Constructor for our GUI with a default size of 1200px by 800px
     * and will utilize many helper methods to set up the action listeners
     * for each button and text field.
     *
     */
    public GraphicalUserInterface()
    {
        setContentPane(gridPanel);
        setTitle("Encoding App");
        setSize(1200, 800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        genText();
        clearGenText();
        changeSliderValue();
        encryptText();
        decryptText();
        clearPassword();
        clearEncryption();
        createFile();
        clearFileText();
        saveTextToFile();
        clearCreateFileText();
        clearAll();
    }

    /**
     * Contains the actionListener to generate random text by calling upon the
     * /random microservice by utilizing an HTTP request.
     *
     * @return void
     */
    public void genText() {
        genTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Initial local variable declaration an initialization */
                StringBuffer responseContent = new StringBuffer();
                BufferedReader br;
                String lineToRead;
                String randText;

                try {
                    URL quoteURL = new URL("http://localhost:7642/random");
                    /* Initialize the connection and set it to a get request */
                    HttpURLConnection connection = (HttpURLConnection) quoteURL.openConnection();
                    connection.setRequestProperty("accept", "application/json");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    /* Get the response code to the status and set our reader to the appropriate stream */
                    int status = connection.getResponseCode();
                    System.out.println("Status: " + status);
                    if (status > 299) {
                        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }

                    /* While wr still have text to read, append it to responseContent */
                    while ((lineToRead = br.readLine()) != null) {
                        responseContent.append(lineToRead);
                    }
                    br.close();

                    /* Create a new JSONObject that contains the text we need then parse it into randText */
                    JSONObject textObj = new JSONObject(responseContent.toString());
                    randText = textObj.getString("rs");

                    /* Grab only the specified number of characters as indicated by textLength's value */
                    randText = randText.substring(0, Math.min(randText.length(), textLength));
                    genTextBox.setText(randText);

                } catch (Exception exception) {
                    System.out.println("Some error occurred");
                    System.out.println(exception.toString());
                }
            }
        });
    }

    /**
     * Contains the actionListener to clear the genTextBox text box.
     *
     * @return void
     */
    public void clearGenText()
    {
        clearGenTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genTextBox.setText("");
            }
        });
    }

    /**
     * Contains the actionListener to change the value of textLength
     * to the current value held by lengthSlider.
     *
     * @return void
     */
    public void changeSliderValue()
    {
        lengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textLength = lengthSlider.getValue();
            }
        });
    }

    /**
     * Contains the actionListener to encrypt any text found in the
     * enterPasswordHereTextField text box using the
     * base64 library encode function.
     *
     * @return void
     */
    public void encryptText()
    {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = enterPasswordHereTextField.getText();

                /* Create a byte array for encoding then feed it through the encoding function from base64 */
                byte[] bytes = inputText.getBytes(StandardCharsets.UTF_8);
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                encryptedDecryptedPasswordTextField.setText(encodedString);
            }
        });
    }

    /**
     * Contains the actionListener to decrypt any text found in the
     * enterPasswordHereTextField text box using the
     * base64 library decode function.
     *
     * @return void
     */
    public void decryptText()
    {
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = enterPasswordHereTextField.getText();
                String decodedString = "";
                try {
                    /* Create a byte array for decoding then feed it through the decoding function from base64 */
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
    }

    /**
     * Contains the actionListener to clear the enterPasswordHereTextField text box.
     *
     * @return void
     */
    public void clearPassword()
    {
        clearPassEnterTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPasswordHereTextField.setText("");
            }
        });
    }

    /**
     * Contains the actionListener to clear the encryptedDecryptedPasswordTextField text box.
     *
     * @return void
     */
    public void clearEncryption()
    {
        clearEncDecTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptedDecryptedPasswordTextField.setText("");
            }
        });
    }

    /**
     * Contains the actionListener to create a file from the text in
     * the enterNameForNewTextField text box and will create a
     * default output.txt otherwise.
     *
     * @return void
     */
    public void createFile()
    {
        createFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = enterNameForNewTextField.getText();
                /* If the user just clicks create file without entering a name, set the default name to output.txt */
                if(fileName.equals("Enter Name for New File..."))
                {
                    fileName = new String("output");
                }
                fileName = fileName.concat(".txt");
                try {
                    /* Finally, write the text to our new file */
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                    writer.write(encryptedDecryptedPasswordTextField.getText());
                    writer.write("\n");
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Contains the actionListener to clear the enterNameForNewTextField text box.
     *
     * @return void
     */
    public void clearFileText()
    {
        clearFileTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNameForNewTextField.setText("");
            }
        });
    }

    /**
     * Contains the actionListener that will grab any text from
     * the enterFileNameToTextField text box and run it through
     * a file and, if it exists, will append the specified file
     * with the text in encryptedDecryptedPasswordTextField.
     *
     * @return void
     */
    public void saveTextToFile()
    {
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = enterFileNameToTextField.getText();
                /* If the user simply clicks the save button or clears the field, set the text to an appropriate message */
                if(fileName.equals("Enter File Name To Save To")
                        || fileName.equals("")
                        || fileName.equals("Enter a file name to save to"))
                {
                    enterFileNameToTextField.setText("Enter a file name to save to");
                    return;
                }
                try{
                    File testFile = new File(fileName);
                    /* If our file does not exist, set text to an appropriate message */
                    if(!testFile.exists())
                    {
                        enterFileNameToTextField.setText("File does not exist!");
                        return;
                    }
                    /* Otherwise, append the text to our existing file */
                    FileWriter appendFile = new FileWriter(testFile, true);
                    BufferedWriter appendWriter = new BufferedWriter(appendFile);
                    appendWriter.write(encryptedDecryptedPasswordTextField.getText() + "\n");
                    appendWriter.close();
                    appendFile.close();
                }
                catch(Exception error)
                {
                    throw new RuntimeException(error);
                }
            }
        });
    }

    /**
     * Contains the actionListener to clear the enterFileNameToTextField text box.
     *
     * @return void
     */
    public void clearCreateFileText()
    {
        clearCreateFileTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterFileNameToTextField.setText("");
            }
        });
    }

    /**
     * Contains the actionListener to clear every text field.
     *
     * @return void
     */
    public void clearAll()
    {
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
