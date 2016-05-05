package Main;

public class ForeignKeyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForeignKeyException(){
		super("FOREIGN KEY constraint failed");
	}
}
