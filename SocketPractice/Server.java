import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket mySocket = new ServerSocket(4999);
        Socket inSocket = mySocket.accept();
        System.out.println("Awaiting message");

        TimeUnit.SECONDS.sleep(5);

        InputStreamReader input = new InputStreamReader(inSocket.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        String message = reader.readLine();
        System.out.println("Received: " + message);
        reader.close();
        input.close();
    }

}
