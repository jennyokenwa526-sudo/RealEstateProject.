import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class RealEstateAgentTest {

    @Test
    public void testLoadFromFileDoesNotThrow() {
        assertDoesNotThrow(() -> RealEstateAgent.loadFromFile("src/data.txt"),
                "Loading data file should not throw exceptions");
    }

    @Test
    public void testDisplayResultsCreatesOutputFile() {
        String outputFile = "test_output.txt";
        RealEstateAgent.loadFromFile("src/data.txt");
        RealEstateAgent.displayResults(outputFile);

        File f = new File(outputFile);
        assertTrue(f.exists(), "Output file should be created");
        assertTrue(f.length() > 0, "Output file should not be empty");

        // cleanup
        f.delete();
    }
}

