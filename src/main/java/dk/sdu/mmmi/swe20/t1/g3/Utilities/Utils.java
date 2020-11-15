package dk.sdu.mmmi.swe20.t1.g3.Utilities;

public class Utils {
    public static boolean isIntelliJ() {
        // https://stackoverflow.com/a/37049420/6905947
        String data = System.getProperty("java.class.path");
        return data.contains("idea_rt.jar");
    }
}
