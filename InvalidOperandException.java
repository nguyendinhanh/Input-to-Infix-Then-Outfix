
//class for invalid operand
public class InvalidOperandException extends Exception {
	public InvalidOperandException (String symbol){
		System.out.println("Invalid operand: " + symbol);
	}
}
