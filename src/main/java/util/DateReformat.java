package util;

/**
 * Created by homie on 16.10.16.
 */
public class DateReformat {
    public static String forDataBase(String old){
        try {
            String[] date = old.split("\\.");
            return String.format("%s-%s-%s",date[2],date[1],date[0]);
        }
        catch (Exception e){
            return "";
        }
    }
    public static String forHtmlPage(String old){
        try {
            String[] date = old.split("-");
            return String.format("%s.%s.%s",date[2],date[1],date[0]);
        }
        catch (Exception e){
            return "";
        }
    }
}
