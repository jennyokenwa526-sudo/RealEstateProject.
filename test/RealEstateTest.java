import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RealEstateTest {

    @Test
    public void testGetTotalPrice_Budapest() {
        RealEstate r = new RealEstate("Budapest", 250000, 100, 4, Genre.FLAT);
        assertEquals(32500000, r.getTotalPrice(), "Budapest adds 30% to base price");
    }

    @Test
    public void testGetTotalPrice_Debrecen() {
        RealEstate r = new RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE);
        assertEquals(31680000, r.getTotalPrice(), "Debrecen adds 20% to base price");
    }

    @Test
    public void testAverageSqmPerRoom() {
        RealEstate r = new RealEstate("Nyíregyháza", 110000, 60, 2, Genre.FARM);
        assertEquals(30.0, r.averageSqmPerRoom(), 0.001);
    }

    @Test
    public void testMakeDiscount() {
        RealEstate r = new RealEstate("Kisvárda", 200000, 50, 2, Genre.FLAT);
        r.makeDiscount(10);
        assertEquals(180000, r.getPrice());
    }

    @Test
    public void testToStringContainsCityAndGenre() {
        RealEstate r = new RealEstate("Budapest", 250000, 100, 4, Genre.FLAT);
        String s = r.toString();
        assertTrue(s.contains("Budapest"));
        assertTrue(s.contains("FLAT"));
    }
}

