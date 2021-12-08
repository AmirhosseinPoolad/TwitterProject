package main.java.org.ce.ap.server.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerUtil {
    /**
     * calculates SHA-256 hash of input and returns an array of bytes.
     *
     * @param input input string to be hashed
     * @return hashed input
     */
    public static byte[] getSHA(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md.digest(input.getBytes(StandardCharsets.UTF_8));

    }

    /**
     * converts a byte array to a hex string
     *
     * @param input hashed byte array
     * @return hashed hex string
     */
    public static String byteToString(byte[] input) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, input);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
