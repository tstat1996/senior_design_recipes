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

public class PythonAccess {
    // TODO: make into a method that Controller calls rather than main method
    public static void main(String[] args) {
        try {
            // TODO: add ability to send data / recieve data - ask andre to fix python side?
            URL url = new URL("http://127.0.0.1:5002/getrecs");
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
                System.out.println(output);
                x = output;
            }
            conn.disconnect();

            JSONParser parse = new JSONParser();
            System.out.println(parse.parse(x));

            // TODO: fix parsing JSON? for some reason it cannot parse into a json object???
            JSONObject jobj = (JSONObject)parse.parse(x);

            System.out.println(jobj.toJSONString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
