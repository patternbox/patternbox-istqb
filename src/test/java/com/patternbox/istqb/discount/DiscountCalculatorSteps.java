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
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author <a href='http://www.patternbox.com'>D. Ehms, Patternbox<a>
 */
public class DiscountCalculatorSteps {

	private static final double DELTA = 0.00001;

	private DiscountCalculator calc;

	private double price;

	@Given("^you are buying a new car$")
	public void createDiscountCalculator() {
		calc = new DiscountCalculator();
	}

	@When("^the recommended retail price of the car is (.+) Euro$")
	public void setPrice(String price) {
		this.price = Double.parseDouble(price);
	}

	@Then("^you have to pay (.+) Euro$")
	public void checkDiscount(String expectedPrice) {
		double actualPrice = calc.calculate(price);
		assertEquals(Double.parseDouble(expectedPrice), actualPrice, DELTA);
	}

	@Then("^an IllegalPriceException is thrown$")
	public void throwIllegalPriceException() {
		try {
			calc.calculate(price);
			throw new RuntimeException("Something went wrong.");
		} catch (IllegalPriceException e) {
			// everything is fine
		}
	}
}