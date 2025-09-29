public class RealEstate implements PropertyInterface {
    // 🔹 protected instead of private
    protected String city;
    protected double price;       // price per sqm
    protected int sqm;
    protected double numberOfRooms;
    protected Genre genre;

    // Constructor
    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    @Override
    public void makeDiscount(int percent) {
        price -= price * percent / 100.0;
    }

    @Override
    public int getTotalPrice() {
        double total = price * sqm;

        if (city.equalsIgnoreCase("Budapest")) {
            total *= 1.3;
        } else if (city.equalsIgnoreCase("Debrecen")) {
            total *= 1.2;
        } else if (city.equalsIgnoreCase("Nyíregyháza")) {
            total *= 1.15;
        }
        return (int) total;
    }

    @Override
    public double averageSqmPerRoom() {
        return sqm / numberOfRooms;
    }

    @Override
    public String toString() {
        return String.format("RealEstate [city=%s, price/sqm=%.0f, sqm=%d, rooms=%.1f, genre=%s, totalPrice=%d, avgSqmPerRoom=%.2f]",
                city, price, sqm, numberOfRooms, genre, getTotalPrice(), averageSqmPerRoom());
    }
}
