package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping("/request")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Recommendation> request(@RequestParam(value="courses", defaultValue = "") String courses,
                                        @RequestParam(value="courseHistory", defaultValue = "") String courseHistory,
                                        @RequestParam(value = "diff", defaultValue = "4") String diff,
                                        @RequestParam(value = "courseQual", defaultValue = "4") String courseQual,
                                        @RequestParam(value = "profQual", defaultValue = "4") String profQual) {
        List<Recommendation> recs = new ArrayList<Recommendation>();
        try ( GraphAccess graph = new GraphAccess( "bolt://localhost:7687", "sam", "sam" ) )
        {
            String response = graph.access( courses, courseHistory, diff, courseQual, profQual);
            JSONArray arr = (JSONArray) new JSONParser().parse(response);
            for (Object obj : arr) {
                JSONObject jo = (JSONObject) obj;
                Recommendation rec = new Recommendation(
                        (String) jo.get("name"),
                        (String) jo.get("aliases"),
                        Double.parseDouble((String) jo.get("difficulty")),
                        Double.parseDouble((String) jo.get("courseQuality")),
                        Double.parseDouble((String) jo.get("professorQuality")),
                        (String) jo.get("description")
                );
                recs.add(rec);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return recs;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
