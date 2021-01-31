import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class TestService {

    private static final Logger logger = Logger.getLogger(TestService.class.getName());

    private static final String url = "http://localhost:8001";

    public static void main(String[] args) throws IOException {
        var map = new HashMap<String, String>();
        map.put("q","90201");
        map.put("format","json");
        String request = JsonObject.getParamsString(map);
        logger.info(new JsonObject(GeoService.getTimeZoneByZipCode(request)).toString());
        map = new HashMap<>();
        map.put("q", "almaty");
        request = JsonObject.getParamsString(map);
        logger.info(new JsonObject(WeatherService.getWeatherByCityName(request)).toString());
        logger.info(ResponseService.simulateNetworkIssues(url));
    }
}
