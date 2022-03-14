package client.graphics;

public class CSSFactory {
    private static String DIR_CSS = "css/";

    public static String getStyleSheet(String filename){
        return CSSFactory.class.getResource(DIR_CSS + filename).toExternalForm();
    }
}
