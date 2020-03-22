package ule.edi.queuewithrep;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;
import ule.edi.queuewithrep.ArrayQueueWithRepImpl.ElemQueueWithRep;


public class LinkedQueueWithRepImpl<T> implements QueueWithRep<T> {
 
	// Atributos
	private QueueWithRepNode<T> front;
	private QueueWithRepNode<T> current;
	int count;
	
	
	// Clase interna
	@SuppressWarnings("hiding")
	public class QueueWithRepNode<T> {
		T elem;
		int num;
		QueueWithRepNode<T> next;
		
		public QueueWithRepNode (T elem, int num){
			this.elem=elem;
			this.num=num;
		}
		
	}
	
	///// ITERADOR //////////
	@SuppressWarnings("hiding")
	public class LinkedQueueWithRepIterator<T> implements Iterator<T> {
		private int count;
		private  QueueWithRepNode<T> current;	
       	
		public LinkedQueueWithRepIterator(QueueWithRepNode<T> nodo) {
			current=front;
			count=size;
		}
		
		@Override
		public boolean hasNext() {
			return (current!=null);
		}

		@Override
		public T next() {
			QueueWithRepNode<T> result=null;
			if(hasNext()) {
				result= current.elem;
				current=current.next;
			}
			return (T) result;
		}

		

	}
	////// FIN ITERATOR
	
	public LinkedQueueWithRepImpl() {
		count=0;
		front= current = null;
	}

	/////////////
	@Override
	public void add(T element) {
		if(element==null) throw new NullPointerException();
		QueueWithRepNode<T> node = new QueueWithRepNode<T>(element, 1);	
		if(isEmpty()) {
			front=node;
			current=node;
			current.next=null;
			count++;
		}
		else {
			if(!contains(element)) {
				current.next=node;
				current=node;
				current.next=null;
				count++;
			}
			else {
				current=front;
				while(current!=null) {
					if (current.elem.equals(element)) current.num = current.num+1;
					current= current.next;
				}
			}
		}
	}
	
	@Override
	public void add(T element, int times) {
		if(element==null) throw new NullPointerException();
		if (times<0) throw new IllegalArgumentException();
		QueueWithRepNode<T> node = new QueueWithRepNode<T>(element, times);	
		if(isEmpty()) {
			front=node;
			current=node;
			current.next=null;
			count++;
		}
		else {
			if(!contains(element)) {
				current.next=node;
				current=node;
				current.next=null;
				count++;
			}
			else {
				current=front;
				while(current!=null) {
					if (current.elem.equals(element)) current.num += times;
					current= current.next;
				}
			}
		}
	}


	@Override
	public void remove(T element, int times) {
		if(element==null) throw new NullPointerException();
		if(!contains(element)) throw new NoSuchElementException();
		current=front;
		while(current!=null) {
			if(current.elem.equals(element)) {
				if(times < 0 || times >= current.num ) throw new IllegalArgumentException();
				current.num -= times;
			}
			current=current.next;
		}
	}

	
	@Override
	public boolean contains(T element) {
		if(element==null) throw new NullPointerException();
		boolean contains=false;
		current=front;
		while (current!=null) {
			if(current.elem.equals(element)) contains=true;
			current=current.next;
		}
		return contains;
	}

	@Override
	public long size() {
		long size=0;
		current=front;
		while (current!=null) {
			size += current.num;
			current=current.next;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (count==0);
	}

	@Override
	public int remove() throws EmptyCollectionException {
		if(isEmpty()) throw new EmptyCollectionException("ERROR: THE QUEUE IS EMPTY");
		int instancias=0;
		instancias = front.num;
		front=front.next;
		count--;
		return instancias;
	}

	@Override
	public void clear() {
		count = 0;
		front = current = null;
	}

	@Override
	public int count(T element) {
		int counter=0;
		if(element==null) throw new NullPointerException();
		current=front;
		while (current!=null) {
			if(current.elem.equals(element)) counter = current.num;
			current=current.next;
		}
		return counter;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LinkedQueueWithRepIterator<T>(front);
	}


	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("(");
		current=front;
		while (current!=null) {
			buffer.append(current.elem.toString() + " ");
			current=current.next;
		}		
		buffer.append(")");
		return buffer.toString();
	}

	
	

}
