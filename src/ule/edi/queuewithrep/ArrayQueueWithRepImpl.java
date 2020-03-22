package ule.edi.queuewithrep;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;

public class ArrayQueueWithRepImpl<T> implements QueueWithRep<T> {
	
	// atributos
	
    private final int capacityDefault = 10;
	
	ElemQueueWithRep<T>[] data;
    private int count;
    
	// Clase interna 
    
	@SuppressWarnings("hiding")
	public class ElemQueueWithRep<T> {
		T elem;
		int num;
		public ElemQueueWithRep (T elem, int num){
			this.elem=elem;
			this.num=num;
		}
	}

	
	///// ITERADOR //////////
	@SuppressWarnings("hiding")
	public class ArrayQueueWithRepIterator<T> implements Iterator<T> {
		
		private int count;
		private int current;
		private T[] items;
		
		public ArrayQueueWithRepIterator(ElemQueueWithRep<T>[] cola, int count){
			 items= (T[]) cola;
			 count = count;
			 current = 0; 
		}

		@Override
		public boolean hasNext() {
			return (current < count);
		}

		@Override
		public T next() throws NoSuchElementException {
			if (! hasNext()) throw new NoSuchElementException();
			current ++;
			return items[current -1];
		}
	}
	////// FIN ITERATOR
	
	
    // Constructores

	@SuppressWarnings("unchecked")
	public ArrayQueueWithRepImpl() {
		data =   new ElemQueueWithRep[capacityDefault];
		count=0;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayQueueWithRepImpl(int capacity) {
		data =  new ElemQueueWithRep[capacity];
		count=0;
	}
	
	
	 @SuppressWarnings("unchecked")
	 private void expandCapacity() {
			ElemQueueWithRep<T>[] nuevo= (ElemQueueWithRep<T>[]) new ElemQueueWithRep[data.length*2];
			for(int i=0; i<data.length;i++) {
				nuevo [i]=data[i];
			}
			data=nuevo;
	 }
	 
	
			@Override
			public void add(T element, int times) {
				if (element==null)throw new NullPointerException();
				if (times<0) throw new IllegalArgumentException();
				if (!(contains(element))) {
					if (count == data.length) expandCapacity();
					ElemQueueWithRep<T> tOb = new ElemQueueWithRep<T> (element,times);
					data[count]= tOb;
					count++;
				}
				else {
					for (int i=0;i<count; i++) {
						if(element.equals(data[i].elem)) data[i].num += times;
					}
				}
			}
			

			@Override
			public void add(T element) {
				if (element==null)throw new NullPointerException();
				if (!(contains(element))) {
					if (count == data.length) expandCapacity();
					ElemQueueWithRep<T> tOb = new ElemQueueWithRep<T> (element, 1);
					data[count]= tOb;
					count++;
				}
				else {
					for (int i=0;i<count; i++) {
						if(element.equals(data[i].elem)) data[i].num = data[i].num+1;
					}
				}
			}

			@Override
			public void remove(T element, int times) {
				if(element==null) throw new NullPointerException();
				if(contains(element)) {
					for (int i=0;i<count; i++) {
						if(element.equals(data[i].elem)) {
							if(data[i].num > times && times >= 0) data[i].num -= times;
							else throw new IllegalArgumentException();
						}
					}
				}
				else throw new NoSuchElementException();
			}

			@Override
			public int remove() throws EmptyCollectionException {
				int removedObj;
				if(!isEmpty()) {
					removedObj = data[0].num;
					for (int i=0;i<count;i++) {
						data[i]=data[i+1];
					}
					count--;
				}
				else throw new EmptyCollectionException("ERROR: THE QUEUE IS EMPTY");
				return removedObj;
			}

			@Override
			public void clear() {
				for (int i=0;i<count;i++) {
					data[i]=null;
				}
				count=0;
			}

			@Override
			public boolean contains(T element) {
				if (element==null)throw new NullPointerException();
				boolean contain=false;
				for (int i=0;i<count; i++) {
					if(element.equals(data[i].elem))contain=true;
				}
				return contain;
			}

			@Override
			public boolean isEmpty() {
				return (count==0);
			}

			@Override
			public long size() {
				long instacesNumber=0;
				for (int i=0;i<count;i++) {
					instacesNumber += data[i].num;
				}
				return instacesNumber;
			}

			@Override
			public int count(T element) {
				int instancias=0;
				if(contains(element)) {
					for(int i=0;i<count;i++) {
						if(data[i].elem.equals(element)) instancias=data[i].num;
					}
				}
				return instancias;
			}

			@Override
			public Iterator<T> iterator() {
				return new ArrayQueueWithRepIterator<T>(data, count);				
			}
			
			@Override
			public String toString() {
				
				final StringBuffer buffer = new StringBuffer();
				
				buffer.append("(");
				for (int i=0;i<count;i++) {
					for (int j=0;j<data[i].num;j++) {
						buffer.append(data[i].elem.toString() + " ");
					}
				}
				buffer.append(")");
				
				return buffer.toString();
			}

	
}
