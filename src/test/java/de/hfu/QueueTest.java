package de.hfu;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class QueueTest {
	
	private final int MAX_QUEUE_SIZE = 3;
	private Queue queue;
	
	@Before
	public void setUp() {
		assertEquals("Don't modify queue size, tests are designed for MAX_QUEUE_SIZE of 3", MAX_QUEUE_SIZE, 3);
		
		queue = new Queue(MAX_QUEUE_SIZE);
	}
	
	@Test
	public void testEnqueue() {
		// enqueue adds 1 to Queue
		queue.enqueue(1);
		assertTrue(queue.queue[queue.head] == 1);
	}
	
	@Test
	public void testQueueOverflow() {
		/*
		 *  full queue overwrites last Element
		 *  0, 1,..,4
		 *  queue[MAX_QUEUE_SIZE-1] = 3, 4
		 */
		queue.enqueue(0);
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		assertTrue(queue.queue[queue.tail] == 3);
		queue.enqueue(4);
		assertTrue(queue.queue[queue.tail] == 4);
	}
	
	@Test
	public void TestDequeue() {
		queue.enqueue(0);
		queue.enqueue(1);
		queue.enqueue(2);

		assertTrue(queue.dequeue() == 0);
		
		assertTrue(queue.queue[queue.head] == 1);
	}
	
	@Test
	public void TestLogicalRingBuffer() {
		queue.enqueue(0);
		queue.enqueue(1);
		queue.enqueue(2);
		
		// dequeue moves head to queue[1]
		queue.dequeue();
		
		assertTrue(queue.head == 1);
		
		// enqueue should set queue[0]
		queue.enqueue(3);
		
		assertTrue(queue.queue[0] == 3);
	}
	
	

}
