package backend;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Helper {

	/*
	 * global constants
	 */
	public static final String fire = "FIRE";
	public static final String ok = "OK";
	public static final String resend = "RESEND";
	public static final String transmit = "TRANSMITFIELD";
	public static final String start = "START";
	public static final String server = "MASTERMIND";
	public static final String bye = "BYE";
	public static final String ping = "PING";
	public static final String result = "RESULT";
	public static final String hit = "HIT";
	public static final String nohit = "WATER";

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
	 * generate random integer between x and y
	 */
	public static int getRandomInteger(int x, int y) {
		return (int) (x + Math.floor(Math.random() * (y-x+1)));
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

	/*
	 * convert String command to Event object
	 */
	public static AWTEvent commandToEvent(String iv_command) {
		// variables
		AWTEvent rr_event = null;
		Action action;
		String[] commandParts;
		String origin = null;
		String key = null;
		int x_pos = 0;
		int y_pos = 0;
		String misc = "";

		// split command to single values (separated by ',')
		commandParts = iv_command.split("\\,");

		for (int i = 0; i < commandParts.length; i++) {
			switch (i) {
			case 0:
				// first value = origin
				origin = commandParts[i];
				break;
			case 1:
				// second value = keyword for command
				key = commandParts[i];
				break;
			case 2:
				// third value = x position
				x_pos = Integer.parseInt(commandParts[i]);
				break;
			case 3:
				// fourth value = y position
				y_pos = Integer.parseInt(commandParts[i]);
				break;
			default:
				// other values are optional parameters
				if (misc != "") {
					misc = misc.concat("," + commandParts[i]);
				}
				else {
					misc = misc.concat(commandParts[i]);
				}
				break;
			}
		}

		// build event object with information
		action = new Action(origin, key, x_pos, y_pos, misc);
		rr_event = new ActionEvent(action, 0, key);

		return rr_event;
	}

	/*
	 * convert Event object to String command
	 */
	public static String eventToCommand(AWTEvent ir_event) {

		Action action;

		action = (Action) ir_event.getSource();
		return action.toString();
	}

	/*
	 * convert AWTEvent object to own Action object
	 */
	public static Action awtEventToAction(AWTEvent ir_event) {
		return (Action) ir_event.getSource();
	}

}
