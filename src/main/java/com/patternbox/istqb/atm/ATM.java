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
 * The ATM (automatic teller machine) implementation
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class ATM {

	private ProcessStatus status = ProcessStatus.IDLE;

	public Card process(Card card, Callback callback) {
		if (card.isValid()) {
			try {
				if (requestPIN(card, callback)) {
					dispenseCash(callback, requestAmount(card, callback));
				} else {
					return null;
				}
			} catch (ProcessCanceledException e) {
				// nothing to do here
			} finally {
				status = ProcessStatus.IDLE;
			}
		}
		return card;
	}

	/**
	 * Request PIN from card holder and adjust PIN counter
	 * 
	 * @param card
	 *          the card instance
	 * @param callback
	 *          the callback interface
	 * @return <code>true</code>, if PIN was correct
	 * @throws ProcessCanceledException
	 *           the card holder canceled the process
	 */
	private boolean requestPIN(Card card, Callback callback) throws ProcessCanceledException {
		status = ProcessStatus.REQUEST_PIN;
		while (card.getPinCounter() < 3) {
			if (card.checkPIN(callback.getPIN())) {
				card.resetPinCounter();
				return true;
			} else {
				card.incPinCounter();
			}
		}
		return false;
	}

	/**
	 * Request amount of money from card holder and check against available account balance.
	 * 
	 * @param card
	 *          the card instance
	 * @param callback
	 *          the callback interface
	 * @return the amount of money
	 * @throws ProcessCanceledException
	 *           the card holder canceled the process
	 */
	private double requestAmount(Card card, Callback callback) throws ProcessCanceledException {
		status = ProcessStatus.REQUEST_AMOUNT;
		double amount;
		for (;;) {
			amount = callback.getAmount();
			if (amount <= card.getBalance()) {
				return amount;
			}
		}
	}

	/**
	 * Dispense requested amount of money in cash.
	 * 
	 * @param callback
	 *          the callback interface
	 * @param amount
	 *          the amount of money
	 */
	private void dispenseCash(Callback callback, double amount) {
		status = ProcessStatus.PERFORM_TRANSACTION;
		callback.dispenseCash(amount);
	}

	/**
	 * Return the ATM status
	 */
	public ProcessStatus getStatus() {
		return status;
	}
}
