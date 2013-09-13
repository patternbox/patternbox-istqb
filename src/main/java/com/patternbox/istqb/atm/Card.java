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

/**
 * The bank card implementation.
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class Card {

	private final int pin;

	private final double balance;

	private int counter = 0;

	private boolean valid = true;

	/**
	 * Constructor
	 * 
	 * @param pin
	 *          the card PIN
	 * @param balance
	 *          the current account balance
	 */
	public Card(int pin, double balance) {
		this.pin = pin;
		this.balance = balance;
	}

	/**
	 * Return <code>true</code> if the given PIN equals to the card PIN
	 */
	public boolean checkPIN(int pin) {
		return this.pin == pin;
	}

	/**
	 * Increase counter for wrong PIN input
	 */
	public void incPinCounter() {
		counter++;
	}

	/**
	 * Reset counter for wrong PIN input
	 */
	public void resetPinCounter() {
		counter = 0;
	}

	/**
	 * Return the counter for wrong PIN input
	 */
	public int getPinCounter() {
		return counter;
	}

	/**
	 * @return the account balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid
	 *          the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
