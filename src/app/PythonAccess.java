package app;

import javax.script.*;
import java.io.*;

public class PythonAccess {
    public static void main(String[] args) {
        StringWriter writer = new StringWriter(); //ouput will be stored here

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();

        context.setWriter(writer); //configures output redirection
        ScriptEngine engine = manager.getEngineByName("python");
        try {
            engine.eval(new FileReader("pcrpy/surpriseRecommender.py"), context);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(writer.toString());



    }
}
