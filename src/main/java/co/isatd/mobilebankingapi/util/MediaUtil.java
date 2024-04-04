package co.isatd.mobilebankingapi.util;

public class MediaUtil {
    public  static String extractExtension(String mediaNme){
        int lastDotIndex = mediaNme.lastIndexOf(".");
        return mediaNme.substring(lastDotIndex+1);

    }

}
