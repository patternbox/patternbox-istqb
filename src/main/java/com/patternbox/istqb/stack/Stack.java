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

/**
 * Stack implementation for state testing.
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class Stack {

	private static final int MAX_HEIGHT = 3;

	private final Item[] items = new Item[3];

	private int height = -1;

	/**
	 * Return stack state
	 */
	public State getState() {
		switch (height) {
		case -1:
			return State.CREATED;
		case Integer.MIN_VALUE:
			return State.DELETED;
		case 0:
			return State.EMPTY;
		case MAX_HEIGHT:
			return State.FULL;
		default:
			return State.FILLED;
		}
	}

	public void init() {
		if (State.CREATED.equals(getState())) {
			height = 0;
		} else {
			throw new IllegalStackStateException("init", getState());
		}
	}

	public void delete() {
		if (State.EMPTY.equals(getState())) {
			height = Integer.MIN_VALUE;
		} else {
			throw new IllegalStackStateException("delete", getState());
		}
	}

	public void push(Item item) {
		if (State.EMPTY.equals(getState()) || State.FILLED.equals(getState())) {
			items[height++] = item;
		} else {
			throw new IllegalStackStateException("push", getState());
		}
	}

	public Item pop() {
		if (State.FILLED.equals(getState()) || State.FULL.equals(getState())) {
			return items[--height];
		} else {
			throw new IllegalStackStateException("pop", getState());
		}
	}

	public Item top() {
		if (State.FILLED.equals(getState()) || State.FULL.equals(getState())) {
			return items[height];
		} else {
			throw new IllegalStackStateException("top", getState());
		}
	}
}
