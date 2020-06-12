import java.util.NoSuchElementException;

//implementation of a generic array stack
public class ArrayStacks<T> {

	//creates aa generic array able to hold 20 objects
	T [] data = (T[]) new Object [20];
	int size = 0; // set size of that array to 0

	//method to put object on the stack
	public void push(T value) {
		if (size == data.length) {
			T[] temp = (T[]) new Object[size*2];
			for ( int i = 0; i < size; i++){
				temp[i]=data[i];
			}
			data = (T[]) temp ;
		} 
		data[size] = value;
		size++;

	}

	//method to delete object off of the stack
	public T pop() {
		if (size == 0) 
			throw new NoSuchElementException("Cannot pop from empty stack");
		size--;
		return  data[size];
	}

	// see the first thing on the stack
	public T peek() {

		if( size == 0 )
			throw new NoSuchElementException("Underflow Exception");
		return data[size-1];
	} 

	//get the size of the stack
	public int size() {
		return size ; 
	}

	//check if the stack is empty
	public boolean isEmpty() {
		return size == 0;
	}

	//convert to a String
	public String toString()
	{
		String store = "";
		for( int i = 0; i < size; i++)
			store += " " + data[i] + " ";

		return store;
	}
}


