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
package com.patternbox.istqb.discount;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Boundary value analysis for {@linkplain DiscountCalculator}.
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class BoundaryValueAnalysis {

	private static final double DELTA = 0.00001;

	private static final double ONE_CENT = 0.01;

	private DiscountCalculator calc;

	@Before
	public void setUp() {
		calc = new DiscountCalculator();
	}

	/**
	 * Discount given price parameter by discount rate
	 */
	private double discount(double price, double discount) {
		return price - (price * discount);
	}

	/**
	 * Test boundary for price = 15,000
	 */
	@Test
	public void testBoundary15K() {
		final double boundary = 15000;
		double price = boundary - ONE_CENT;
		assertEquals("Bondary 15K, no discount", price, calc.calculate(price), DELTA);
		price = boundary;
		assertEquals("Bondary 15K, discount 5%", discount(price, 0.05), calc.calculate(price), DELTA);
	}

	/**
	 * Test boundary for price = 20,000
	 */
	@Test
	public void testBoundary20K() {
		final double boundary = 20000;
		double price = boundary;
		assertEquals("Bondary 20K, discount 5%", discount(price, 0.05), calc.calculate(price), DELTA);
		price = boundary + ONE_CENT;
		assertEquals("Bondary 20K, discount 7%", discount(price, 0.07), calc.calculate(price), DELTA);
	}

	/**
	 * Test boundary for price = 25,000
	 */
	@Test
	public void testBoundary25K() {
		final double boundary = 25000;
		double price = boundary - ONE_CENT;
		assertEquals("Bondary 25K, discount 7%", discount(price, 0.07), calc.calculate(price), DELTA);
		price = boundary;
		assertEquals("Bondary 25K, discount 8.5%", discount(price, 0.085), calc.calculate(price), DELTA);
	}
}
