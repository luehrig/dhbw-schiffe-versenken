package schiffe_versenken;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {

	/*
	 * convert a byte array in integer value
	 */
	public static int ByteArraytoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	/*
	 * check if command has no modification
	 */
	public static boolean isCommandValid(String iv_command) {

		MessageDigest hashProcessor;
		String commandToHash;
		String receivedHashValue;
		String calculatedHashValue;
		String[] receivedHash;
		byte[] bytesOfCommand;
		byte[] calculatedHashedValues;

		// extract hash from received command
		receivedHash = iv_command.split(",");
		receivedHashValue = receivedHash[receivedHash.length - 1];

		commandToHash = iv_command.replace(","
				+ receivedHash[receivedHash.length - 1], "");

		// do new hashing
		try {
			// get instance of hash processor for MD5
			hashProcessor = MessageDigest.getInstance("MD5");
			hashProcessor.reset();
			// convert incoming command to single bytes
			bytesOfCommand = commandToHash.getBytes("UTF-8");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return false;
		}

		// calculate hash
		calculatedHashedValues = hashProcessor.digest(bytesOfCommand);
		calculatedHashValue = ""
				+ Helper.ByteArraytoInt(calculatedHashedValues);

		if (receivedHashValue.equals(calculatedHashValue)) {
			return true;
		} else {
			return false;
		}
	}

}
