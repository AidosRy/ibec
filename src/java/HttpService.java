import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Logger;

public class HttpService implements HttpHandler {

    private static final Logger logger = Logger.getLogger(HttpService.class.getName());

    private static final String notConfigured = "Server is not configured for this response";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String requestParamValue = null;
            logger.info("connection from ip: " + httpExchange.getRemoteAddress().toString());
            httpExchange.getResponseHeaders().set("Content-type", "application/json");
            if ("GET".equals(httpExchange.getRequestMethod())) {
                requestParamValue = handleGetRequest(httpExchange);
            } else if ("POST".equals(httpExchange.getRequestMethod())) {
                requestParamValue = handlePostRequest(httpExchange);
            }
            handleResponse(httpExchange, Objects.requireNonNull(requestParamValue));
        }
        catch (Exception e) {
            e.printStackTrace();
            OutputStream outputStream = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(500, 30);
            outputStream.write("Internal server error".getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }

    private JsonObject getJsonFromHttp(HttpExchange httpExchange) {
        try (InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)){
            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }
            return new JsonObject(buf.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeJsonToHttp(HttpURLConnection conn, String request) {
        try (OutputStreamWriter osr = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
             BufferedWriter br = new BufferedWriter(osr)){
            br.write(request);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        logger.info("POST request for " + uri);
        if(uri.contains("/geo")){
            logger.info("calling geo-service");
            JsonObject jsonObject = getJsonFromHttp(httpExchange);
            return GeoService.getTimeZoneByZipCode(JsonObject.getParamsString(Objects.requireNonNull(jsonObject).getBody()));
        }
        return notConfigured;
    }

    private String handleGetRequest(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        logger.info("GET request for " + uri);
        if(uri.contains("/weather")) {
            logger.info("calling weather service");
            JsonObject jsonObject = getJsonFromHttp(httpExchange);
            return WeatherService.getWeatherByCityName(JsonObject.getParamsString(Objects.requireNonNull(jsonObject).getBody()));
        }
        return notConfigured;
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        logger.info("handling");
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(200, requestParamValue.length());
        outputStream.write(requestParamValue.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

