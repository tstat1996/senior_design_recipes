package app;

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
    public List<Recommendation> request(@RequestParam(value="courses", defaultValue = "") String courses,
                                        @RequestParam(value = "diff", defaultValue = "4") String diff,
                                        @RequestParam(value = "courseQual", defaultValue = "4") String courseQual) {
        return new ArrayList<>();
    }

    @RequestMapping("/lol")
    @CrossOrigin(origins = "http://localhost:3000")
    public Recommendation rec() {
        return new Recommendation("Sam", "Sam", 0, 0, 0, "SAm");
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
