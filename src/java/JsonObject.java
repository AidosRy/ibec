import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject{
    private final Map<String, String> body = new LinkedHashMap<>();

    public JsonObject(String jsonString) {
        String[] arr = jsonString
                .replace("\r","")
                .replace("\n","")
                .replace("{","")
                .replace("}","")
                .replace("[","")
                .replace("]","")
                .replace(" ","")
                .replace("\t", "")
                .split(",");
        String[] temp;
        for(String s : arr) {
            String key="";
            String value="";
            try {
                temp = s.split(":");
                if (temp.length == 2) {
                    key = subString(temp[0]);
                    value = subString(temp[1]);
                } else {
                    key = subString(temp[1]);
                    value = subString(temp[2]);
                }
                body.put(key, value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String subString(String string) {
        if(string.matches(".*\\d.*"))
            return string;
        else if(string.length() > 2)
            return string.substring(1, string.length()-1);
        else
            return string;
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append("{\n");
        body.forEach(
                (key, value) -> sb
                        .append("\"")
                        .append(key)
                        .append("\"")
                        .append(" : ")
                        .append("\"")
                        .append(value)
                        .append("\"\n")
        );
        sb.append("\n}");
        return sb.toString();
    }

    public Map<String, String> getBody(){
        return body;
    }

    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
