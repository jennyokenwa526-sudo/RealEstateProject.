# RealEstateProject

This project simulates a real estate management system in Java.  
It reads property data from a file and calculates prices and averages for different types of properties.

## Features
- `RealEstate` class for general properties:
    - Fields: city, price per sqm, sqm, number of rooms, genre
    - Methods: makeDiscount, getTotalPrice, averageSqmPerRoom, toString
- `Panel` class for panel apartments:
    - Fields: floor, isInsulated
    - Overrides: getTotalPrice, toString
    - Methods: hasSameAmount, roomprice
- Reads property data from `data.txt`
- Prints details of each property with total price and average sqm per room

## How to run
1. Make sure you have Java installed.
2. Place `data.txt` in the project folder.
3. Compile all `.java` files:
   ```bash
   javac *.java

this is where you can find my screenshot :screenshots/realestate – Main.java 9_29_2025 1_37_58 PM.png