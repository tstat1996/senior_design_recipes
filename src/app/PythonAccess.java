package app;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PythonAccess {
    public static List<Recommendation> getRecs(Map<String, String> ratings) {
        try {
//            String urlString = "https://pcpry-api.herokuapp.com/getrecs?";
            String urlString = "http://127.0.0.1:5002/getrecs?";
            int ind = 1;
            for (String key : ratings.keySet()) {
                urlString += "course" + ind + "=" + key + "&rating" + ind + "=" + ratings.get(key);
                if (ind < 5) {
                    ind++;
                    urlString += "&";
                } else {
                    break;
                }
            }
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output = null;
            String x = null;
            while ((output = br.readLine()) != null) {
                x = output;
            }
            conn.disconnect();

            List<Recommendation> recs = new ArrayList<>();

            JSONParser parse = new JSONParser();
            String parsed = parse.parse(x).toString();
            JSONObject obj = (JSONObject) parse.parse(parsed);
            String student = obj.keySet().iterator().next().toString();
            JSONArray array = (JSONArray) obj.get(student);
            for (Object o : array) {
                JSONObject recObj = (JSONObject) parse.parse(o.toString());
                String code = recObj.get("code").toString();
                String name = recObj.get("name").toString();
                Double courseQuality = Double.parseDouble(recObj.get("courseQuality").toString());
                Double difficulty = Double.parseDouble(recObj.get("difficulty").toString());
                Double instructorQuality = Double.parseDouble(recObj.get("instructorQuality").toString());
                String description = recObj.get("description").toString();
                Recommendation rec = new Recommendation(code, name, courseQuality, difficulty, instructorQuality, description);
                recs.add(rec);
            }
            return recs;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new ArrayList<Recommendation>();
    }
}
