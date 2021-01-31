import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class ResponseService {

    private static final Logger logger = Logger.getLogger(ResponseService.class.getName());

    public static String simulateNetworkIssues(String url) throws IOException {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setConnectTimeout(1);
            con.setReadTimeout(1);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream()));
            return in.readLine();
        }
        catch (Exception e) {
            logger.info("Network issues");
        }
        return "";
    }
    //get response from api
    public static String getResponse(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        //JsonObject jsonObject = new JsonObject(content.toString());
        //logger.info(jsonObject.toString());
        in.close();
        con.disconnect();
        return content.toString();
    }
}
