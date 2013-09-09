/**************************** Copyright notice ********************************

Copyright (C)2013 by D. Ehms, http://www.patternbox.com
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.
 ******************************************************************************/
package com.patternbox.istqb.stack;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * State transition testing for {@linkplain Stack}.
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class StateTransitionTest {

	private Stack stack;

	@Before
	public void setUp() throws Exception {
		stack = new Stack();
	}

	/**
	 * Test transition path: init - pop
	 */
	@Test(expected = IllegalStackStateException.class)
	public void testTransitionPath1() {
		assertEquals("Inital Status", State.CREATED, stack.getState());
		stack.init();
		assertEquals("Empty Stack", State.EMPTY, stack.getState());
		stack.pop();
	}

	/**
	 * Test transition path: init - top
	 */
	@Test(expected = IllegalStackStateException.class)
	public void testTransitionPath2() {
		assertEquals("Inital Status", State.CREATED, stack.getState());
		stack.init();
		assertEquals("Empty Stack", State.EMPTY, stack.getState());
		stack.top();
		Item item1 = new Item();
		stack.push(item1);
		assertEquals("Filled Stack", State.FILLED, stack.getState());
		assertEquals("Item1", item1, stack.top());
		assertEquals("Item1", item1, stack.pop());
	}

	/**
	 * Test transition path: init - push - pop - delete
	 */
	@Test
	public void testTransitionPath3() {
		assertEquals("Inital Status", State.CREATED, stack.getState());
		stack.init();
		assertEquals("Empty Stack", State.EMPTY, stack.getState());
		Item item1 = new Item();
		stack.push(item1);
		assertEquals("Filled Stack", State.FILLED, stack.getState());
		assertEquals("Item1", item1, stack.pop());
		assertEquals("Empty Stack", State.EMPTY, stack.getState());
		stack.delete();
		assertEquals("Deleted Stack", State.DELETED, stack.getState());
	}

	/**
	 * Test transition path: init - push - push - push - pop - push
	 */
	@Test(expected = IllegalStackStateException.class)
	public void testTransitionPath4() {
		assertEquals("Inital Status", State.CREATED, stack.getState());
		stack.init();
		assertEquals("Empty Stack", State.EMPTY, stack.getState());
		Item item1 = new Item();
		stack.push(item1);
		assertEquals("Filled Stack", State.FILLED, stack.getState());
		Item item2 = new Item();
		stack.push(item2);
		assertEquals("Filled Stack", State.FILLED, stack.getState());
		Item item3 = new Item();
		stack.push(item3);
		assertEquals("Full Stack", State.FULL, stack.getState());
		assertEquals("Item3", item3, stack.pop());
		assertEquals("Filled Stack", State.FILLED, stack.getState());
		stack.push(item3);
		assertEquals("Full Stack", State.FULL, stack.getState());
		stack.push(new Item());
	}
}
