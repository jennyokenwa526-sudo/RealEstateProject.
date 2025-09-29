import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<RealEstate> properties = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts[0].equals("REALESTATE")) {
                    RealEstate r = new RealEstate(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Genre.valueOf(parts[5])
                    );
                    properties.add(r);
                } else if (parts[0].equals("PANEL")) {
                    Panel p = new Panel(
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Genre.valueOf(parts[5]),
                            Integer.parseInt(parts[6]),
                            parts[7].equalsIgnoreCase("yes")
                    );
                    properties.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print all properties
        for (RealEstate r : properties) {
            System.out.println(r);
        }
    }
}
