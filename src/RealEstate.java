import java.util.Objects;
// Ensure Genre, PropertyInterface are in the same package or imported

public class RealEstate implements PropertyInterface, Comparable<RealEstate> {

    // 1. Fields
    private String city;
    private double price; // price per square meter (real)
    private int sqm; // area in square meters (integer)
    private double numberOfRooms; // number of rooms (real)
    private Genre genre; // Type Genre

    // 2. Constructor
    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    // --- Public Getters (Fixes the red signs in RealEstateAgent.java) ---
    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }

    public int getSqm() {
        return sqm;
    }

    public double getNumberOfRooms() {
        return numberOfRooms;
    }

    public Genre getGenre() {
        return genre;
    }
    // -------------------------------------------------------------------

    // --- PropertyInterface Implementation ---
    @Override
    public void makeDiscount(int percentage) {
        if (percentage > 0 && percentage <= 100) {
            this.price *= (1.0 - percentage / 100.0);
        }
    }

    @Override
    public int getTotalPrice() {
        double basePrice = this.price * this.sqm;
        double multiplier = 1.0;

        // Apply city-based price modifiers
        if (city.equalsIgnoreCase("Budapest")) {
            multiplier += 0.30; // 30% more
        } else if (city.equalsIgnoreCase("Debrecen")) {
            multiplier += 0.20; // 20% more
        } else if (city.equalsIgnoreCase("Nyíregyháza")) {
            multiplier += 0.15; // 15% more
        }

        return (int) Math.round(basePrice * multiplier);
    }

    @Override
    public double averageSqmPerRoom() {
        if (numberOfRooms <= 0) return 0.0;
        return (double) sqm / numberOfRooms;
    }

    @Override
    public String toString() {
        return String.format(
                "RealEstate in %s (%s) | Price/sqm: %.2f | Rooms: %.1f | Area: %d sqm | Total Price: %d | Sqm/Room: %.2f",
                city, genre, price, numberOfRooms, sqm, getTotalPrice(), averageSqmPerRoom()
        );
    }

    // --- Comparable Implementation (Required for TreeSet) ---
    @Override
    public int compareTo(RealEstate other) {
        // Sort by Total Price primarily
        return Integer.compare(this.getTotalPrice(), other.getTotalPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealEstate that = (RealEstate) o;
        return Double.compare(that.price, price) == 0 && sqm == that.sqm && Double.compare(that.numberOfRooms, numberOfRooms) == 0 && Objects.equals(city, that.city) && genre == that.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, price, sqm, numberOfRooms, genre);
    }
}
