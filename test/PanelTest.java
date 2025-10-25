import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PanelTest {

    @Test
    public void testHasSameAmount() {
        // Same city and same data, so their total price should match
        RealEstate r = new RealEstate("Budapest", 200000, 60, 3, Genre.FLAT);
        Panel p = new Panel("Budapest", 200000, 60, 3, Genre.FLAT, 4, false);

        assertTrue(p.hasSameAmount(r), "Panel and RealEstate with same total price should match");
    }


    @Test
    public void testRoomPriceCalculation() {
        Panel p = new Panel("Tiszaújváros", 120000, 75, 3, Genre.FLAT, 10, false);
        int result = p.roomprice();
        assertTrue(result > 0, "Room price should be positive");
    }

    @Test
    public void testGetTotalPrice_ModifiedByFloorAndInsulation() {
        Panel p = new Panel("Debrecen", 120000, 35, 2, Genre.FLAT, 1, true);
        int totalPrice = p.getTotalPrice();
        assertTrue(totalPrice > 0, "Total price should be positive after modifiers");
    }

    @Test
    public void testToStringContainsDetails() {
        Panel p = new Panel("Budapest", 180000, 70, 3, Genre.FLAT, 4, false);
        String s = p.toString();
        assertTrue(s.contains("Budapest"));
        assertTrue(s.contains("floor"));
        assertTrue(s.contains("insulated"));
    }
}

