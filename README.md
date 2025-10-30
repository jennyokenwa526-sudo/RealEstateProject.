#  RealEstate Project (Java)

This project simulates a **real estate management system** written in Java.  
It reads property data from a text file, calculates prices, applies discounts, and generates reports.  
Unit tests were created using **JUnit 5**.

---

##  Features

- **RealEstate class**
    - Handles general property data
    - Methods: `makeDiscount()`, `getTotalPrice()`, `averageSqmPerRoom()`
- **Panel class**
    - Extends `RealEstate`
    - Adds fields: `floor`, `isInsulated`
    - Overrides `getTotalPrice()`
    - Implements `PanelInterface`
- **RealEstateAgent**
    - Loads property data from a file (`data.txt`)
    - Calculates averages, totals, and writes results to an output file

---

##  Unit Tests (JUnit 5)

Three unit test classes verify all functionality:

| Test Class | Purpose |
|-------------|----------|
| `RealEstateTest` | Tests total price, discount, and average sqm per room |
| `PanelTest` | Tests floor/insulation modifiers and `hasSameAmount()` |
| `RealEstateAgentTest` | Tests file loading and output generation |

###  How to Run the Tests (in IntelliJ IDEA)

1. Open your project in IntelliJ
2. Ensure the `test` folder is **green** (marked as *Test Sources Root*)
3. Right-click the **`test`** folder → choose **Run 'All Tests'**
4. A **green bar** at the bottom means all tests passed successfully

---

##  Project Structure
RealEstateProject/
├── src/
│ ├── Main.java
│ ├── RealEstate.java
│ ├── Panel.java
│ ├── RealEstateAgent.java
│ ├── PanelInterface.java
│ ├── Genre.java
│ └── data.txt
├── test/
│ ├── RealEstateTest.java
│ ├── PanelTest.java
│ └── RealEstateAgentTest.java
├── README.md
└── .gitignore


##  How to Run the Program

1. Make sure `data.txt` is inside the `src/` folder
2. Run the main file:
    - In IntelliJ → open `Main.java`
    - Click the green  “Run” button at the top
3. The program will print property information in the **Run** window

---

## 
How to Compile Manually (optional)
JavaDoc was generated
If you prefer using the terminal:
```bash
javac src/*.java
java -cp src Main

Author:
Name: OKENWA JENNY CHIOMA
Date: 25 October 2025
Course: Programming Technology