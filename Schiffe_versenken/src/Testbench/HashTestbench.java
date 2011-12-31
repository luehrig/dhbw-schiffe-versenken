package Testbench;

import schiffe_versenken.Helper;

import java.io.UnsupportedEncodingException;
import java.security.*;

import network.Client;

public class HashTestbench {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		MessageDigest hashProcessor;

		String word = "github";
		String commandToSend = null;
		boolean result;
		byte[] bytesOfCommand;
		byte[] calculatedHashedValues;

		try {
			// get instance of hash processor for MD5
			hashProcessor = MessageDigest.getInstance("MD5");
			hashProcessor.reset();
			
			// calculate hash
			bytesOfCommand = word.getBytes("UTF-8");
			calculatedHashedValues = hashProcessor.digest(bytesOfCommand);
			commandToSend = word + "," + Helper.ByteArraytoInt(calculatedHashedValues);
		} catch (NoSuchAlgorithmException e) {
			return;
		} catch (UnsupportedEncodingException e) {
			return;
		}

		
		// build client object and test
		Client testClient = new Client("127.0.0.1", 6200);
		result = testClient.isCommandValid(commandToSend);

		if (result == true) {
			System.out.println("method is correct implemented!");
		} else {
			System.err.println("method returns the wrong answer!");
		}
	}

}
