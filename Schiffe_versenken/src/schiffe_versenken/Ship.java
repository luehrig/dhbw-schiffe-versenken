package schiffe_versenken;

public abstract class Ship {

	// variables for all child classes
	private String name;
	private int[][] position;
	private final int positionLength = 2;
	private int size;
	
	public Ship( String iv_name, int iv_size ) {
		this.size = iv_size;
		this.name = iv_name;
		this.position = new int[positionLength][iv_size];
	}
	
	public String getName() {
		return this.name;
	}
	
	public int[][] getPosition() {
		return this.position;
	}
		
	public void sink() {
		
	}
	
	public int getSize() {
		return this.size;
	}
	
}
