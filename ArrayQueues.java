
import java.util.NoSuchElementException;

//implementation of generic array queue
public class ArrayQueues<T> {

	// creates a generic array queue that can hold 20 objects
	T [] data = (T[]) new Object[20];
	int start = 0; // index of the current front item
	int size = 0;	//size of the array
	int tail = 0;  // index of next adding item 

	//put object at the end of a queue
	public void enqueue(T value) {
		if (size == data.length) {
			T[] temp = (T[]) new Object[size*2];
			for ( int i = 0; i < size; i++)
				temp[i] = data[(start + 1) % data.length];
			
			data = (T[]) data;
		}
		data[tail] = (T) value;
		tail = (tail + 1) % data.length;
		size++;
	}

	//delete the object from the beginning of the queue
	public T dequeue() {
		if (size == 0) 
			throw new NoSuchElementException("Cannot remove from empty queue");
		
		T item = data[start];
		start = (start + 1) % data.length;
		size--;
		return item;
	}

	//see the object at the first index of the queue
	public T front() {
		if (size == 0) 
			throw new NoSuchElementException("Cannot get the first variable into empty queue");
		
		return data[start];
	}

	//get the size the queue
	public int size() {
		return size;
	}

	//convert to a String
	public String toString()
	{
		String store = "";
		for( int i = start; i < (start+size)%data.length; i++){
			if(data[i] instanceof Operand)
				store += " " + ((((Operand) data[i]).getValue())) + " ";
			else
				store += " " + (((Operator) data[i]).getValue()) + " ";
		}



		return store;
	}


}
