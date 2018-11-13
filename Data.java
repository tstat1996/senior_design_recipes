import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Data {
    public static void main(String[] args) throws IOException {
        String token = "?token=q0qeptSxy3HmiIOHlal3Qrx18I5xKB";
        String url2 = "https://api.penncoursereview.com";
        String extra = "/v1/depts/CIS";
        URL url = new URL(url2 + extra + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (con.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        con.disconnect();

    }

}
