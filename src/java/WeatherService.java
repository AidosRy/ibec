import java.io.IOException;
import java.util.logging.Logger;

public class WeatherService {

    private static final Logger logger = Logger.getLogger(WeatherService.class.getName());

    private static final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";

    private static final String apiKey = "appid=e7d337a55bf59e16acb9933ff64aad2b";

    public static String getWeatherByCityName(String paramsString) throws IOException {
        logger.info("Weather service working");
        String url = apiUrl + "?" + apiKey + "&" + paramsString;
        return ResponseService.getResponse(url);
    }
}
