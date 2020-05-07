package structures;

import java.util.EmptyStackException;
import java.util.Iterator;

import structures.LinkedListOfGeneric;

public class StackSimpleLinked<E> implements StackTAD<E>
{
	private int size;
	private LinkedListOfGeneric<E> lista;
	
	public StackSimpleLinked() 
	{
		size = 0;
		lista = new LinkedListOfGeneric<E>();
	}
	@Override
	public int size() 
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		if (size!=0)
			return false;
		return true;
	}

	@Override
	public E top() throws EmptyStackException 
	{
		return lista.getFirst();
	}

	@Override
	public void push(E element) {
		lista.addFirst(element);
		size++;
	}
	
	public E peek() throws EmptyStackException 
	{
		return lista.getFirst();
	}

	@Override
	public E pop() throws EmptyStackException 
	{
		E ret = lista.removeFirst();
		size--;
		return ret;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Iterator<E> it = lista.iterator();
		
		while (it.hasNext())
		{
			sb.append(it.next()+",");
		}
		if(sb.length()>1) 
		{
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public String reversetoString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.toString());
		return sb.reverse().toString();
	}

}
