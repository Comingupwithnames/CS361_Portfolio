import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PRNG {

    public static void main(String[] args)
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("[insert path to prng-service.txt]"));
            String myString = "";
            while (true)
            {
                myString = reader.readLine();
                if(myString == null)
                {
                    TimeUnit.SECONDS.sleep(5);
                }
                else if(myString.equals("run"))
                {
                    break;
                }
                else {
                    TimeUnit.SECONDS.sleep(5);
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("[insert path to prng-service.txt]"));
            int randInt;
            Random random = new Random();
            randInt = random.nextInt(4);
            writer.write("" + randInt);
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
