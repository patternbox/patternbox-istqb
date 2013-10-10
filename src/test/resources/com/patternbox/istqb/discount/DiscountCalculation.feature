Feature: Discount Calculation 

Scenario: No Discount
    Given you are buying a new car
    When the recommended retail price of the car is 14500 Euro
    Then you have to pay 14500 Euro
    
Scenario: Discount 5%
    Given you are buying a new car
    When the recommended retail price of the car is 16500 Euro
    Then you have to pay 15675 Euro
    
Scenario: Discount 7%
    Given you are buying a new car
    When the recommended retail price of the car is 24750 Euro
    Then you have to pay 23017.5 Euro
    
Scenario: Discount 8.5%
    Given you are buying a new car
    When the recommended retail price of the car is 31800 Euro
    Then you have to pay 29097 Euro
    
Scenario: Negative Price
    Given you are buying a new car
    When the recommended retail price of the car is -4000 Euro
    Then an IllegalPriceException is thrown
    
Scenario: Price too big
    Given you are buying a new car
    When the recommended retail price of the car is 1500800 Euro
    Then an IllegalPriceException is thrown
    
Scenario: Price is NaN
    Given you are buying a new car
    When the recommended retail price of the car is NaN Euro
    Then an IllegalPriceException is thrown
    
    
    
    
    
    