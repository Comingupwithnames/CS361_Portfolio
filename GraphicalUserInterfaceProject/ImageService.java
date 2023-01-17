import java.io.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class ImageService {

    public static void main(String[] args)
    {
        int randInt;
        String[] pathArr = {"[insert path to image1.jpeg]",
                            "[insert path to image2.jpeg]",
                            "[insert path to image3.jpeg]",
                            "[insert path to image4.jpeg]"};
        String chosenPath = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("[insert path to image-service.txt]"));
            String myString = "";
            while(true)
            {
                myString = reader.readLine();
                if(myString == null)
                {
                    TimeUnit.SECONDS.sleep(5);
                }
                else if(myString.length() == 1)
                {
                    break;
                }
                else
                {
                    TimeUnit.SECONDS.sleep(5);
                }
            }
            reader.close();
            randInt = parseInt(myString);
            chosenPath = pathArr[randInt];
            BufferedWriter writer = new BufferedWriter(new FileWriter("[insert path to image-service.txt]"));
            writer.write(chosenPath);
            writer.close();
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
    }
}
