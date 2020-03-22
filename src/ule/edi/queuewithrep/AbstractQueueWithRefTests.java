package ule.edi.queuewithrep;


import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.*;

import ule.edi.exceptions.EmptyCollectionException;

public abstract class AbstractQueueWithRefTests {

	protected abstract <T> QueueWithRep<T> createQueueWithRep();
	

	private QueueWithRep<String> S1;

	private QueueWithRep<String> S2;
	
	@Before
	public void setupQueueWithReps() {

		this.S1 = createQueueWithRep();
		
		this.S2 = createQueueWithRep();
		
		S2.add("ABC", 5);
		S2.add("123", 5);
		S2.add("XYZ", 10);
	}

	@Test
	public void testConstructionIsEmpty() {
		assertTrue(S1.isEmpty());
		assertFalse(S2.isEmpty());
	}
	
	@Test
	//Las nuevas instancias del TAD tienen tamaño cero: 
	public void testConstructionCardinality() {
		assertEquals(S1.size(), 0);
	}

	@Test
	public void testToStringInEmpty() {
		assertTrue(S1.isEmpty());
		assertEquals(S1.toString(), "()");
	}
	
	@Test
	public void testToString1elem() {
		assertTrue(S1.isEmpty());
		S1.add("A",3);
		assertEquals(S1.toString(), "(A A A )");
	}
	
	@Test
	//Añadir elementos con una multiplicidad incrementa su contador y el tamaño de la cola: ")
	public void testAddWithCount() {
		S1.add("ABC", 5);
		assertEquals(S1.count("ABC"), 5);
		assertEquals(S1.size(), 5);
		S1.add("ABC", 5);
		assertEquals(S1.count("ABC"), 10);
		assertEquals(S1.size(), 10);
		S1.add("123", 5);
		assertEquals(S1.count("123"), 5);
		assertEquals(S1.count("ABC"), 10);
		assertEquals(S1.size(), 15);
	}
	
	
	@Test
	//Se pueden eliminar cero instancias de un elemento con remove(x, 0): ")
	public void testRemoveZeroInstances() {
		S1.add("ABC");
		S1.remove("ABC", 0);
	}
	// TODO AÑADIR MAS TESTS
	
	@Test (expected = NullPointerException.class)
	public void testAddNull() {
		S1.add(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddWithTimeNull() {
		S1.add(null,1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddIllegalArgumentException() {
		S1.add("ABC",-1);
	}
	
	@Test
	public void testAddExpandCapacity() {
		int capacityDefault=10;
		for (int i=0;i<capacityDefault+1;i++) {
			char caracter=(char) i;
			String cadena=("A"+ caracter);
			S1.add(cadena,1);
		}
		Assert.assertEquals(11, S1.size());
	}
	
	@Test
	public void testAddRepElem() {
		// Estos add distintos cubren a su vez posibilidades de los dos add
		S1.add("WWW",28);
		S1.add("ABC",3);
		S1.add("ABC");
		S1.add("ABC",2);
		S1.add("cba",10);
		Assert.assertEquals(6, S1.count("ABC"));
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testRemoveNonExistentElem() {
		S2.remove("WWWWWWW", 3);;
	}
	
	@Test (expected = NullPointerException.class)
	public void testRemoveNullELem() {
		S2.remove(null, 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveExcesiveTimes() {
		S2.remove("ABC", 8);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveNegativeTimes() {
		S2.remove("ABC", -1);
	}
	
	@Test
	public void testsRemoveNInstances() {
		S2.remove("ABC", 2);
		Assert.assertEquals(3, S2.count("ABC"));
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveElemOfAEmptyQueue() throws EmptyCollectionException {
		S1.remove();
	}
	
	@Test
	public void testRemoveFirstElem() throws EmptyCollectionException {
		S2.remove();
		Assert.assertEquals(0, S2.count("ABC"));
		Assert.assertEquals(15, S2.size());
	}
	
	@Test
	public void  testClear() {
		S2.clear();
		Assert.assertEquals(0, S2.size());
		Assert.assertEquals(true, S2.isEmpty());
	}
	
	@Test (expected = NullPointerException.class)
	public void testContainsNullElem() {
		S2.contains(null);
	}
	
	@Test
	public void testDontContainsElem() {
		Assert.assertFalse(S2.contains("WWWWWW"));
	}
	
	@Test
	public void testContainsElem() {
		Assert.assertTrue(S2.contains("123"));
	}
	
	@Test
	public void testIsEmpty() {
		Assert.assertTrue(S1.isEmpty());
	}
	
	@Test
	public void testIsNotEmpty() {
		Assert.assertFalse(S2.isEmpty());
	}
	
	@Test
	public void testSize() {
		Assert.assertEquals(0, S1.size());
		S1.add("ABC", 27);
		S1.add("WWWW",2);
		S1.add("QQQ");
		Assert.assertEquals(30, S1.size());
	}
	
	@Test
	public void testCount() {
		Assert.assertEquals(5, S2.count("123"));
		Assert.assertEquals(0, S2.count("QQQQQQQQ"));
	}
	
	@Test
	public void testToString() {
		S1.add("ASDF");
		S1.add("MOVIE",2);
		Assert.assertEquals("(ASDF MOVIE MOVIE )", S1.toString());
	}
	
}