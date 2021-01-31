import java.io.IOException;
import java.util.logging.Logger;

public class GeoService {

    private static final Logger logger = Logger.getLogger(GeoService.class.getName());

    private static final String apiUrl = "http://api.worldweatheronline.com/premium/v1/tz.ashx";

    private static final String apiKey = "key=f8b2460d2b9f45caa63121033213101";

    public static String getTimeZoneByZipCode(String paramsString) throws IOException {
        logger.info("Geo service working");
        String url = apiUrl + "?" + apiKey + "&" + paramsString;
        return ResponseService.getResponse(url);
    }
}
