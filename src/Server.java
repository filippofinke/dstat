
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author filippofinke
 */
public class Server extends Thread {


    private RequestsListener listener;

    private int port;

    private int requests = 0;

    public int getRequests() {
        return requests;
    }

    public Server(RequestsListener listener, int port) {
        this.listener = listener;
        this.port = port;
        timer();
    }

    private void timer() {
        Thread timer = new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - start >= 1000) {
                        start = System.currentTimeMillis();
                        listener.updateRequests(requests);
                        requests = 0;
                    }
                }
            }
        });
        timer.start();
    }

    @Override
    public void run() {
        System.out.println("Starting dstat server on port " + port + "!");
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Dstat listening on port " + port + "!");
            while (true) {
                Socket request = server.accept();
                request.close();
                requests++;
            }

        } catch (IOException ex) {
            System.out.println("Error starting dstat server on port " + port + ":" + ex.getMessage() + "!");
        }
    }

}
