package app;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import javax.script.*;
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
            JSONArray array = (JSONArray) obj.get("0");
            for (Object o : array) {
                String[] strs = o.toString().split(" ");
                Recommendation rec = new Recommendation(strs[1], strs[0], 3.5, 3.4, 3.7, "cool class");
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
        }
        return new ArrayList<Recommendation>();
    }
}
