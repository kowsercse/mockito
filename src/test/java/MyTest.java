import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class MyTest {

	List<Object> mockedList = mock(List.class);

	@Test
	public void test() {
		Person person;

//		person = mock(Person.class);
//		System.out.println(person.getName());

		person = new Person();
		System.out.println(person.getName());

		Person spyPerson = spy(person);
		doReturn("spy name").when(spyPerson).getName();
		System.out.println(person.getName());
	}

	@Test
	public void test8() {
		List list = new LinkedList();
		List spy = spy(list);

		// Impossible: real method is called so spy.get(0) throws
		// IndexOutOfBoundsException (the list is yet empty)
		when(spy.get(0)).thenReturn("foo");

		// You have to use doReturn() for stubbing
		doReturn("foo").when(spy).get(0);
		when(spy.get(0)).thenReturn("foo");
	}

	@Test
	public void test7() {
		List list = new LinkedList();
		List spy = spy(list);

		// optionally, you can stub out some methods:
		when(spy.size()).thenReturn(100);

		// using the spy calls real methods
		spy.add("one");
		spy.add("two");

		// prints "one" - the first element of a list
		System.out.println(spy.get(0));

		// size() method was stubbed - 100 is printed
		System.out.println(spy.size());

		// optionally, you can verify
		verify(spy).add("one");
		verify(spy).add("two");
	}

	@Test
	public void test6() {
		List firstMock = mock(List.class);
		List secondMock = mock(List.class);

		// using mocks
		secondMock.add("was called second");
		firstMock.add("was called first");

		// create inOrder object passing any mocks that need to be verified in
		// order
		InOrder inOrder = inOrder(firstMock, secondMock);

		// following will make sure that firstMock was called before secondMock
		inOrder.verify(firstMock).add("was called first");
		inOrder.verify(secondMock).add("was called second");
	}

	@Test
	public void test5() {
		doThrow(new RuntimeException()).when(mockedList).clear();

		// following throws RuntimeException:
		mockedList.clear();
	}

	@Test
	public void test4() {
		// using mock
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		// following two verifications work exactly the same - times(1) is used
		// by default
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");

		// exact number of invocations verification
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		// verification using never(). never() is an alias to times(0)
		verify(mockedList, never()).add("never happened");

		// verification using atLeast()/atMost()
		verify(mockedList, atLeastOnce()).add("three times");
		verify(mockedList, atLeast(2)).add("five times");
		verify(mockedList, atMost(5)).add("three times");
	}

	@Test
	public void test3() {
		// stubbing using built-in anyInt() argument matcher
		when(mockedList.get(anyInt())).thenReturn("element");

		// stubbing using hamcrest (let's say isValid() returns your own
		// hamcrest matcher):

		// TODO: plz uncomment this line
		// when(mockedList.contains(argThat(isValid()))).thenReturn("element");

		// following prints "element"
		System.out.println(mockedList.get(999));

		// you can also verify using an argument matcher
		verify(mockedList).get(anyInt());

		System.out.println("done");
	}

	@Test
	public void test2() {
		// You can mock concrete classes, not only interfaces
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing
		when(mockedList.get(0)).thenReturn("first");
		// when(mockedList.get(1)).thenThrow(new RuntimeException());

		// following prints "first"
		System.out.println(mockedList.get(0));

		// following throws runtime exception
		System.out.println(mockedList.get(1));

		// following prints "null" because get(999) was not stubbed
		System.out.println(mockedList.get(999));

		// Although it is possible to verify a stubbed invocation, usually it's
		// just redundant
		// If your code cares what get(0) returns then something else breaks
		// (often before even verify() gets executed).
		// If your code doesn't care what get(0) returns then it should not be
		// stubbed. Not convinced? See here.
		verify(mockedList).get(0);

	}

	@Test
	public void test1() {
		// using mock object
		mockedList.add("one");
		mockedList.clear();

		// verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

}
