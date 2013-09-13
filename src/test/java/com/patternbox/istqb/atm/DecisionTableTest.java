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
package com.patternbox.istqb.atm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 * 
 */
public class DecisionTableTest {

	private ATM atm;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		atm = new ATM();
	}

	/**
	 * Test case 1: Invalid bank card
	 */
	@Test
	public void testInvalidCard() {
		Card card = new Card(0, 500);
		card.setValid(false);
		// start process
		Card card2 = atm.process(card, new Callback() {

			public int getPIN() {
				fail("PIN requested");
				return 0;
			}

			public double getAmount() {
				fail("Amount of money requested");
				return 0;
			}

			public void dispenseCash(double amount) {
				fail("Dispense cash called");
			}
		});
		// check result
		assertEquals(card, card2);
		assertFalse("Card valid status wrong", card2.isValid());
	}

	/**
	 * Test case 2: Cancel process after PIN request
	 */
	@Test
	public void testCancelProcessAfterPinRequest() {
		Card card = new Card(1234, 500);
		// start process
		Card card2 = atm.process(card, new Callback() {

			private boolean pinFlag = false;

			public int getPIN() throws ProcessCanceledException {
				if (pinFlag) {
					throw new ProcessCanceledException();
				}
				pinFlag = true;
				return 1111;
			}

			public double getAmount() {
				fail("Amount of money requested");
				return 0;
			}

			public void dispenseCash(double amount) {
				fail("Dispense cash called");
			}
		});
		// check result
		assertEquals(card, card2);
		assertTrue("Card valid  status wrong", card2.isValid());
		assertEquals("PIN counter wrong", 1, card2.getPinCounter());
	}

	/**
	 * Test case 3: Confiscate bank card
	 */
	@Test
	public void testConfiscateCard() {
		Card card = new Card(1234, 500);
		// start process
		Card card2 = atm.process(card, new Callback() {

			private int cnt = 0;

			public int getPIN() {
				if (++cnt > 3) {
					fail("PIN more than 3 times requested");
				}
				return 1111;
			}

			public double getAmount() {
				fail("Amount of money requested");
				return 0;
			}

			public void dispenseCash(double amount) {
				fail("Dispense cash called");
			}
		});
		// check result
		assertNull("Card not confiscated", card2);
	}

	/**
	 * Test case 4: Requested amount is not available (too big)
	 */
	@Test
	public void testAmountNotAvailable() {
		Card card = new Card(1234, 500);
		// start process
		Card card2 = atm.process(card, new Callback() {

			private boolean amountFlag = false;

			public int getPIN() {
				return 1234;
			}

			public double getAmount() throws ProcessCanceledException {
				if (amountFlag) {
					throw new ProcessCanceledException();
				}
				amountFlag = true;
				return 1000;
			}

			public void dispenseCash(double amount) {
				fail("Dispense cash called");
			}
		});
		// check result
		assertEquals(card, card2);
		assertTrue("Card valid  status wrong", card2.isValid());
		assertEquals("PIN counter wrong", 0, card2.getPinCounter());
	}

	/**
	 * Test case 5: Happy day scenario
	 */
	@Test
	public void testHappyDayScenario() {
		Card card = new Card(1234, 500);
		final double cashRequest = 100;
		// start process
		Card card2 = atm.process(card, new Callback() {

			public int getPIN() {
				return 1234;
			}

			public double getAmount() throws ProcessCanceledException {
				return cashRequest;
			}

			public void dispenseCash(double amount) {
				assertEquals("Dispensed amount wrong", cashRequest, amount, 0.0001);
			}
		});
		// check result
		assertEquals(card, card2);
		assertTrue("Card valid  status wrong", card2.isValid());
		assertEquals("PIN counter wrong", 0, card2.getPinCounter());
	}
}
