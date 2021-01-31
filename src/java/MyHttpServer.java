import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class MyHttpServer {
    private static final Logger logger = Logger.getLogger(MyHttpServer.class.getName());

    public static void main(String[] args) {
        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/", new HttpService());
            server.setExecutor(threadPoolExecutor);
            server.start();
            logger.info(" Server started on port 8001");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
