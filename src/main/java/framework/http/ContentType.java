package framework.http;

import java.util.Map;

public enum ContentType {

    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "application/javascript"),
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpeg"),
    PLAIN("", "text/plain");

    private final String extension;
    private final String mimeType;

    private static final Map<String, ContentType> EXTENSIONS = Map.of(
            ".html", HTML,
            ".css", CSS,
            ".js", JS,
            ".png", PNG,
            ".jpg", JPG

    );

    ContentType(String extension, String mimeType){
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static ContentType fromPath(String path){
        int indexOfDot = path.lastIndexOf(".");

        if(indexOfDot==-1){
            return PLAIN;
        }
        String pathWithDot = path.substring(indexOfDot);

        return EXTENSIONS.getOrDefault(pathWithDot, PLAIN);
    }

    public String getMimeType(){
        return mimeType;
    }
}
