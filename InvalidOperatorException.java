
//class for invalid operator
public class InvalidOperatorException extends Exception {
	public InvalidOperatorException (char symbol){
		System.out.println("Invalid operator: " + symbol);
	}
}
