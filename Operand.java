//class to store operands

public class Operand implements Temp {


	public double value;

	public Operand(double number){ //stores number in operand value
		value = number;

	}

	public boolean isOperand(){ //method to check if object is operand
		return true;
	}
	
	public String toString(){ //converts value to string
		return "" + value;
	}
	public double getValue(){//returns value
		return value;
	}
}


