//class to store operators
public class Operator implements Temp {

	public char value;
	
	public Operator(char symbol){
		value = symbol;//stores symbol in value
		
	}
	public char getValue(){//returns value
		return value;
	}
	
	public boolean isOperand(){//checks if object is operand, returns false
		return false;
	}
	
	public String toString(){//converts value to string
		return "" + value;
	}
}


