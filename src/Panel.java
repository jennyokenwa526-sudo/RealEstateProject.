public class Panel extends RealEstate implements PanelInterface {
    protected int floor;
    protected boolean isInsulated;

    public Panel(String city, double price, int sqm, double numberOfRooms, Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public int getTotalPrice() {
        int basePrice = super.getTotalPrice();
        double modified = basePrice;

        // Modify based on floor
        if (floor >= 0 && floor <= 2) {
            modified *= 1.05;
        } else if (floor == 10) {
            modified *= 0.95;
        }

        // Modify if insulated
        if (isInsulated) {
            modified *= 1.05;
        }

        return (int) modified;
    }

    @Override
    public String toString() {
        return String.format(
                "Panel [city=%s, price/sqm=%.0f, sqm=%d, rooms=%.1f, genre=%s, floor=%d, insulated=%b, totalPrice=%d, avgSqmPerRoom=%.2f]",
                getCity(),           //  use getter
                getPrice(),          //  use getter
                getSqm(),            //  use getter
                getNumberOfRooms(),  //  use getter
                getGenre(),          //  use getter
                floor,
                isInsulated,
                getTotalPrice(),
                averageSqmPerRoom()
        );
    }

    @Override
    public boolean hasSameAmount(RealEstate other) {
        //  this is fine, since both use public methods
        return this.getTotalPrice() == other.getTotalPrice();
    }

    @Override
    public int roomprice() {
        //  use getters instead of direct private fields
        return (int) ((getPrice() * getSqm()) / getNumberOfRooms());
    }
}
