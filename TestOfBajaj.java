package bajaj;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TestOfBajaj {
	public TestOfBajaj() {
	}
	  public static void main(String[] args) {
	        String prnNumber = args[0].toLowerCase().replace(" ", "");
	        String filePath = args[1];
	        String randomString = generateRandomString(8);
	        try {
	            String json = new String(Files.readAllBytes(new File(filePath).toPath()));
	            String destination = findDestinationValue(json);
	            String concatenatedS = prnNumber + destination + randomString;
	            String md5Hash = computeMd5(concatenatedS);
	            System.out.println(md5Hash + ";" + randomString);
	        } catch (IOException e) {
	           System.out.println(e);
	        }
	    }

	    private static String findDestinationValue(String json) {
	        int index = json.indexOf("\"destination\"");
	        if (index == -1) {
	            return null;
	        }
	        int colonIndex = json.indexOf(":", index);
	        if (colonIndex == -1) {
	            return null;
	        }
	        int startQuote = json.indexOf("\"", colonIndex + 1);
	        int endQuote = json.indexOf("\"", startQuote + 1);
	        if (startQuote == -1 || endQuote == -1) {
	            return null;
	        }
	        return json.substring(startQuote + 1, endQuote);
	    }

	    private static String computeMd5(String input) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] digest = md.digest(input.getBytes());
	            StringBuilder sb = new StringBuilder();
	            for (byte b : digest) {
	                sb.append(String.format("%02x", b));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    private static String generateRandomString(int length) {
	        UUID uuid = UUID.randomUUID();
	        String uuidString = uuid.toString().replace("-", "");
	        return uuidString.substring(0, length);
	    }
	}

