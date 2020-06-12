/*The purpose of this class is to convert input to infix, then to outfix
 * Author:	Anh Nguyen
 * Course:	CS221
 * Assignment:	Project 2
 * Date: 10/13/2017
 */
import java.util.*;

public class InfixToPostfix {
	//This method reads in the input, converts it to infix, and stores it in a queue
	public static ArrayQueues parser (String input) throws InvalidOperandException,InvalidOperatorException,MissingOperandException,UnbalancedParenthesisException{
	
		ArrayQueues<Temp> reader = new ArrayQueues<Temp> ();
		boolean isOperator;
		boolean isOperand;
		boolean wasOperator = false;
		int index = 0;
		char variable;
		String value = "";
		for(index = 0; index < input.length(); index++){
			while(input.charAt(index) == ' ')
				index++;
			variable = input.charAt(index);
			isOperator = false;
			isOperand = false;
			
			
			if(isOperator(variable))
				isOperator = true;
			if(Character.isDigit(variable) || variable == '.') 
				isOperand = true;
			
			if(!isOperand && !value.equals("")){
				int decimal = 0;
				for(int i = 0; i < value.length(); i++){
					if(value.charAt(i) == '.')
						decimal++;
				}
				
				if(decimal > 1)
					throw new InvalidOperandException(value);
				Operand number = new Operand(Double.parseDouble(value));
				
				reader.enqueue(number);
				wasOperator = false;

				value = "";
			}
				

			if (Character.isAlphabetic(variable) || (!isOperator && !isOperand))
				throw new InvalidOperatorException(variable);
			else if(isOperator && index == 0 && (variable == '+' || variable == '-'))
				value += variable;
			else if (isOperator){
				if(index != input.length()-1 && Character.isDigit(input.charAt(index+1)) && wasOperator && input.charAt(index-1) != ')' && input.charAt(index-1) != '(')
					value+= variable;
				else{	
					Operator symbol = new Operator(variable); 
					reader.enqueue(symbol); // adds operator to queue
					wasOperator = true;
				}
			}
			else if (isOperand)
				value+= variable;
		}
		if(value != ""){
			Operand number = new Operand(Double.parseDouble(value));
			reader.enqueue(number);
			wasOperator = false;
		}
		if(wasOperator && input.charAt(input.length()-1) != ')' && input.charAt(input.length()-1) != '(')
			throw new MissingOperandException();

		return reader;
	}
	
	//method to convert an array in infix to postfix
	public static ArrayQueues<Temp> InfixToPostfix (ArrayQueues<Temp> queues) throws UnbalancedParenthesisException
	{
		ArrayQueues<Temp> newQueue = new ArrayQueues<Temp> ();
		ArrayStacks<Operator> stack = new ArrayStacks<Operator>();
		boolean parenthesis = false;
		int leftPCounter = 0;
		int rightPCounter = 0;
		int operandCounter = 0;
		int prevParenthesis = 0;
		int nextOperand = 0;

		while(queues.size!=0)
		{
			if(operandCounter == 2){
				newQueue.enqueue(stack.pop());
				operandCounter = 0;
			}
			if (prevParenthesis == 1 && stack.size != 0 && nextOperand == 1){
				newQueue.enqueue(stack.pop());
				prevParenthesis = 0;
				nextOperand = 0;
			}
				
			if ( queues.front().isOperand()){
				newQueue.enqueue(queues.dequeue());
				operandCounter++;
				if(prevParenthesis == 1)
					nextOperand++;
			}
			else {
				Operator tempOperator = (Operator) queues.dequeue() ;
				if (tempOperator.getValue() == '('){
					stack.push(tempOperator); 
					parenthesis = true;
					leftPCounter++;
				}
				else if (tempOperator.getValue() != ')')
					stack.push(tempOperator); 
				else if (tempOperator.getValue() == ')' ){
					prevParenthesis++;
					if (parenthesis){
						while(stack.peek().getValue() != '(')
							newQueue.enqueue(stack.pop());
						parenthesis = false;
					} else if(parenthesis)
							throw new UnbalancedParenthesisException('(');
					if(stack.size != 0)
						stack.pop();
					rightPCounter++;
				}
				else if(precedence(tempOperator.getValue())<= precedence(stack.peek().getValue())){
						newQueue.enqueue(stack.pop());
						stack.push(tempOperator); 
					}
			}
			
		}
		if (leftPCounter < rightPCounter)
			throw new UnbalancedParenthesisException(')');
		if (rightPCounter < leftPCounter)
			throw new UnbalancedParenthesisException('(');
		
		while(stack.size!=0)
			newQueue.enqueue(stack.pop());
		
		return newQueue;
	}

	//Method to determine importance of operators

	public static double precedence ( char value)
	{
		if( value == '(')
			return 1;
		else if ( value == '+'||value == '-' )
			return 2;
		else
			return 3;
	}

	// Method returns true if character is any legal operator or parenthesis
	public static boolean isOperator(char character){ // accounts for valid operators or right parenthesis
		if (character == '/' || character == '*' || character == '+' || character == '-' || character == ')' || character == '(')
			return true;
		else 
			return false;
	}
 
	// Main method that asks the user for input, then prints out standard infix, postfix, and the answer to the equation

	public static void main(String[] args) throws InvalidOperandException, InvalidOperatorException, MissingOperandException, UnbalancedParenthesisException
	{
		Scanner in = new Scanner(System.in);//ask for input and reads it in

		System.out.print("Enter infix expression: ");
		String input = in.nextLine();

		// creates a queue, stores the value of the parsed input, then prints it out

		ArrayQueues parsedInput = parser(input);
		System.out.println("Standardized infix: "+" "+parsedInput.toString() + " "); 
		
		// creates a queue, stores the value of the infix converted to postfix, then prints it out
		ArrayQueues postOutput = InfixToPostfix(parsedInput);
		System.out.println("Postfix expression: " + postOutput.toString() + " " );
		
		//calculates the answer and stores it in a variable
		double finalValue = calculator(postOutput);
		try{
			ArrayQueues parsedinput =  parser(input);
			InfixToPostfix (parsedinput);
		}
		catch (InvalidOperandException e){
			System.out.print("Invalid operand");
		}
		catch (InvalidOperatorException e){

		}
		catch (MissingOperandException e){
			System.out.print("Missing operand");
		}
		catch (UnbalancedParenthesisException e)
		{

		}
		
		//prints out final answer after checking error cases
		
		System.out.print("Answer: " + finalValue );
	}
	
	public static double calculator (ArrayQueues<Temp> queues){
		
		/* This method is to read input and convert them into output which included all the operation results( type double)
		 * we use the queues that have InfixToPostfix method to process the output.
		 */
		
		ArrayStacks<Operand> newOperand = new ArrayStacks<Operand> (); 
		double sum; // result for summary operators
		double multi; // result for  multiply operators
		double subtract; // result for subtract operators
		double divide ; // result for divide operators
		while (queues.size()!=0){ // loop run when size of queue is not empty
		
			if(queues.front().isOperand()){
				// if the first thing in that queue is a operand, dequeue it and put in stack
				Operand tempOperand = (Operand) queues.dequeue() ;
				newOperand.push(tempOperand);
			}
			else{ // if it is not a operand
			
				//make two values to be able to process a math operation. Get the value of it from a stack and store into a double variable
				double first=newOperand.pop().getValue();
				double second=newOperand.pop().getValue();
				//read from queue the math operation that in input ( convert to String to be able to read )
				if (queues.front().toString().equals("+")){
					sum = first + second;
					//do the math operation and put in to a stack
					newOperand.push(new Operand(sum));
				}
				//read from queue the math operation that in input ( convert to String to be able to read )

				if (queues.front().toString().equals("/")){
					divide = second / first;
					//do the math operation and put in to a stack

					newOperand.push(new Operand(divide));
				}
				//read from queue the math operation that in input ( convert to String to be able to read )

				if (queues.front().toString().equals("-")){
					subtract = second - first;
					//do the math operation and put in to a stack

					newOperand.push(new Operand(subtract));
				}
				//read from queue the math operation that in input ( convert to String to be able to read )

				if (queues.front().toString().equals("*")){
					multi = first * second;
					//do the math operation and put in to a stack

					newOperand.push(new Operand(multi));
				}

				queues.dequeue();

			}
		}
		// check the first thing on the stack and get the value from it, return it to method.
		return newOperand.peek().getValue();
	}

}






