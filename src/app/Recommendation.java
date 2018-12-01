package app;

public class Recommendation {
    private String name;
    private String code;
    private double courseDifficulty;
    private double courseQuality;
    private double profQuality;
    private String description;

    public Recommendation(String name, String code, double courseDifficulty,
                          double courseQuality, double profQuality, String description) {
        this.code = code;
        this.name = name;
        this.courseDifficulty = courseDifficulty;
        this.courseQuality = courseQuality;
        this.profQuality = profQuality;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getCourseDifficulty() {
        return courseDifficulty;
    }

    public double getProfQuality() {
        return profQuality;
    }

    public String getCode() {
        return code;
    }

    public double getCourseQuality() {
        return courseQuality;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCourseDifficulty(double courseDifficulty) {
        this.courseDifficulty = courseDifficulty;
    }

    public void setCourseQuality(double courseQuality) {
        this.courseQuality = courseQuality;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfQuality(double profQuality) {
        this.profQuality = profQuality;
    }

    public void setName(String name) {
        this.name = name;
    }

}
