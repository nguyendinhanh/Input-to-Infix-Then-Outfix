// combine two case of missing parenthesis in one class
public class UnbalancedParenthesisException extends Exception {
	public UnbalancedParenthesisException (char symbol)
	{
		if (symbol == '(' )
			System.out.print("Unbalanced left parenthesis '('");
		else
			System.out.print("Unbalanced right parenthesis ')'");

		
	}

}
