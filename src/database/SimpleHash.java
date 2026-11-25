package database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

public class SimpleHash {

    private final static SimpleHash instance = new SimpleHash();

    public final SimpleHash getInstance(){
        return instance;
    }

    public static String simpleHash(String toHash) throws NoSuchAlgorithmException {
        String basicSalt = UUID.randomUUID().toString().substring(0,16);
        return basicSalt+":"+shaHash(basicSalt+toHash);
    }

    public static boolean verifyInput(String text, String storedHash) throws NoSuchAlgorithmException {
        String[] rawText = storedHash.split(":");
        return rawText.length == 2 && shaHash(rawText[0] + text).equals(rawText[1]);
    }

    private static String shaHash(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digest = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(digest);
    }
}
