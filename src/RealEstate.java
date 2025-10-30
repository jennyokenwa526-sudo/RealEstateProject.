import java.util.Objects;
import java.util.logging.*;

/**
 * Represents a general real estate property with basic details
 * such as city, price, size, number of rooms, and property genre.
 * <p>
 * Provides methods for calculating total price, applying discounts,
 * and computing average square meters per room.
 * </p>
 */
public class RealEstate implements PropertyInterface, Comparable<RealEstate> {

    /** City where the property is located. */
    private String city;

    /** Price per square meter. */
    private double price;

    /** Size of the property in square meters. */
    private int sqm;

    /** Number of rooms in the property. */
    private double numberOfRooms;

    /** Genre/type of property (e.g., FLAT, FAMILYHOUSE). */
    private Genre genre;

    /** Logger for tracking method calls and errors. */
    private static final Logger logger = Logger.getLogger(RealEstate.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("realEstateApp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Failed to initialize logger for RealEstate: " + e.getMessage());
        }
    }

    /**
     * Constructs a new {@code RealEstate} object.
     *
     * @param city          the city name
     * @param price         price per square meter
     * @param sqm           total area in square meters
     * @param numberOfRooms number of rooms
     * @param genre         type of property
     */
    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
        logger.info("Created RealEstate object in " + city + " (" + genre + ")");
    }

    // Getters
    public String getCity() { return city; }
    public double getPrice() { return price; }
    public int getSqm() { return sqm; }
    public double getNumberOfRooms() { return numberOfRooms; }
    public Genre getGenre() { return genre; }

    /**
     * Applies a percentage discount to the price per square meter.
     *
     * @param percentage discount percentage (1–100)
     */
    @Override
    public void makeDiscount(int percentage) {
        logger.info("Applying " + percentage + "% discount to property in " + city);
        if (percentage > 0 && percentage <= 100) {
            this.price *= (1.0 - percentage / 100.0);
        } else {
            logger.warning("Invalid discount percentage: " + percentage);
        }
    }

    /**
     * Calculates the total price considering city-based multipliers.
     *
     * @return total property price
     */
    @Override
    public int getTotalPrice() {
        logger.info("Calculating total price for property in " + city);
        double basePrice = this.price * this.sqm;
        double multiplier = 1.0;

        try {
            if (city.equalsIgnoreCase("Budapest")) {
                multiplier += 0.30;
            } else if (city.equalsIgnoreCase("Debrecen")) {
                multiplier += 0.20;
            } else if (city.equalsIgnoreCase("Nyíregyháza")) {
                multiplier += 0.15;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error calculating total price multiplier for " + city, e);
        }

        return (int) Math.round(basePrice * multiplier);
    }

    /**
     * Calculates the average number of square meters per room.
     *
     * @return the average sqm per room, or 0 if invalid
     */
    @Override
    public double averageSqmPerRoom() {
        logger.info("Calculating average sqm per room for property in " + city);
        if (numberOfRooms <= 0) return 0.0;
        return (double) sqm / numberOfRooms;
    }

    /**
     * Returns a formatted string representing the property details.
     *
     * @return formatted property information
     */
    @Override
    public String toString() {
        return String.format(
                "RealEstate in %s (%s) | Price/sqm: %.2f | Rooms: %.1f | Area: %d sqm | Total Price: %d | Sqm/Room: %.2f",
                city, genre, price, numberOfRooms, sqm, getTotalPrice(), averageSqmPerRoom());
    }

    /**
     * Compares this property to another based on total price.
     *
     * @param other another {@code RealEstate} object
     * @return negative if cheaper, positive if more expensive, or zero if equal
     */
    @Override
    public int compareTo(RealEstate other) {
        return Integer.compare(this.getTotalPrice(), other.getTotalPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstate that)) return false;
        return Double.compare(that.price, price) == 0 && sqm == that.sqm &&
                Double.compare(that.numberOfRooms, numberOfRooms) == 0 &&
                Objects.equals(city, that.city) && genre == that.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, price, sqm, numberOfRooms, genre);
    }
}
