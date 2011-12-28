package schiffe_versenken;

public class Helper {

	public static int  ByteArraytoInt( byte[] bytes ) {
	    int result = 0;
	    for (int i=0; i<4; i++) {
	      result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
	    }
	    return result;
	  }

	
}
