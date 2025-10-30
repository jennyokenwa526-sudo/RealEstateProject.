import java.util.logging.*;

/**
 * Represents a panel apartment that extends the general {@link RealEstate} class.
 * Adds extra details such as floor level and insulation status, and modifies
 * total price calculations accordingly.
 */
public class Panel extends RealEstate implements PanelInterface {

    /** Floor level of the panel apartment. */
    protected int floor;

    /** Indicates whether the panel is insulated. */
    protected boolean isInsulated;

    /** Logger for recording method calls and errors. */
    private static final Logger logger = Logger.getLogger(Panel.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("realEstateApp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Failed to initialize logger for Panel: " + e.getMessage());
        }
    }

    /**
     * Constructs a new {@code Panel} object with all property details.
     *
     * @param city          the city name
     * @param price         price per square meter
     * @param sqm           total area in square meters
     * @param numberOfRooms number of rooms
     * @param genre         type of property
     * @param floor         floor number
     * @param isInsulated   whether the panel is insulated
     */
    public Panel(String city, double price, int sqm, double numberOfRooms,
                 Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
        logger.info("Created Panel apartment in " + city + " | floor: " + floor +
                " | insulated: " + isInsulated);
    }

    /**
     * Calculates the total price considering floor level and insulation adjustments.
     *
     * @return total price as integer
     */
    @Override
    public int getTotalPrice() {
        logger.info("Calculating total price for panel in " + getCity());
        int basePrice = super.getTotalPrice();
        double modified = basePrice;

        try {
            // Floor-based modification
            if (floor >= 0 && floor <= 2) {
                modified *= 1.05;  // +5% for low floors
            } else if (floor == 10) {
                modified *= 0.95;  // -5% for top floor
            }

            // Insulation adds 5%
            if (isInsulated) {
                modified *= 1.05;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error calculating total price for panel", e);
        }

        return (int) modified;
    }

    /**
     * Checks whether this panel has the same total price as another property.
     *
     * @param other another {@code RealEstate} instance
     * @return {@code true} if total prices match, {@code false} otherwise
     */
    @Override
    public boolean hasSameAmount(RealEstate other) {
        logger.info("Comparing total prices between panel and another property");
        return this.getTotalPrice() == other.getTotalPrice();
    }

    /**
     * Calculates the price per room for this panel.
     *
     * @return price per room as integer
     */
    @Override
    public int roomprice() {
        logger.info("Calculating room price for panel in " + getCity());
        return (int) ((getPrice() * getSqm()) / getNumberOfRooms());
    }

    /**
     * Returns a formatted string describing the panel property.
     *
     * @return string with panel details
     */
    @Override
    public String toString() {
        return String.format(
                "Panel [city=%s, price/sqm=%.0f, sqm=%d, rooms=%.1f, genre=%s, floor=%d, insulated=%b, totalPrice=%d, avgSqmPerRoom=%.2f]",
                getCity(), getPrice(), getSqm(), getNumberOfRooms(), getGenre(),
                floor, isInsulated, getTotalPrice(), averageSqmPerRoom());
    }
}
