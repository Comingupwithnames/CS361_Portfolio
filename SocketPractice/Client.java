import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        Socket mySocket = new Socket("localhost", 4999);

        PrintWriter writer = new PrintWriter(mySocket.getOutputStream());
        String message = "A message from CS361";
        System.out.println("Message to send: " + message);
        writer.println(message);
        writer.flush();
        writer.close();
    }
}
