package app;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Array;
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
                                        @RequestParam(value = "courseQual", defaultValue = "4") String courseQual) {
        List<Recommendation> recs = new ArrayList<Recommendation>();
        Recommendation rec1 = new Recommendation("Algos with Rajiv", "CIS 121", 3.5, 3.6, 3.8, "Algorithms in Java");
        Recommendation rec2 = new Recommendation("Code with Swap", "CIS 120", 2.0, 3.3, 3.5, "Java and OCaml");
        recs.add(rec1);
        recs.add(rec2);
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
