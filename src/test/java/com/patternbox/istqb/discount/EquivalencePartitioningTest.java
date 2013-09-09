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
 * Equivalence class partitioning testing for {@linkplain DiscountCalculator}.
 * 
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class EquivalencePartitioningTest {

	private static final double DELTA = 0.00001;

	private DiscountCalculator calc;

	@Before
	public void setUp() {
		calc = new DiscountCalculator();
	}

	/**
	 * Discount given price by discount rate
	 */
	private double discount(double price, double discount) {
		return price - (price * discount);
	}

	/**
	 * Test valid equivalence class vEC-1: 0 <= price < 15000
	 */
	@Test
	public void testNoDiscount() {
		double price = 14500;
		assertEquals("No discount", price, calc.calculate(price), DELTA);
	}

	/**
	 * Test valid equivalence class vEC-2: 15000 <= price <= 20000
	 */
	@Test
	public void testDiscount5Percent() {
		double price = 16500;
		assertEquals("Discount 5%", discount(price, 0.05), calc.calculate(price), DELTA);
	}

	/**
	 * Test valid equivalence class vEC-3: 20000 < price < 25000
	 */
	@Test
	public void testDiscount7Percent() {
		double price = 24750;
		assertEquals("Discount 7%", discount(price, 0.07), calc.calculate(price), DELTA);
	}

	/**
	 * Test valid equivalence class vEC-4: price >= 25000
	 */
	@Test
	public void testDiscount8Dot5Percent() {
		double price = 31800;
		assertEquals("Discount 8.5%", discount(price, 0.085), calc.calculate(price), DELTA);
	}

	/**
	 * Test invalid equivalence class iEC-1: price < 0
	 */
	@Test(expected = IllegalPriceException.class)
	public void testNegativePrice() {
		double price = -4000;
		assertEquals("Negative price", price, calc.calculate(price), DELTA);
	}

	/**
	 * Test invalid equivalence class iEC-2: price > MAX_PRICE
	 */
	@Test(expected = IllegalPriceException.class)
	public void testPriceTooBig() {
		double price = 1500800;
		assertEquals("Price to big", price, calc.calculate(price), DELTA);
	}

	/**
	 * Test invalid equivalence class iEC-3: price is NaN (not a number)
	 */
	@Test(expected = IllegalPriceException.class)
	public void testPriceIsNaN() {
		double price = Double.NaN;
		assertEquals("Price is NaN", price, calc.calculate(price), DELTA);
	}
}
