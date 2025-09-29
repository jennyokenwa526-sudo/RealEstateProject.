public interface PropertyInterface {
    void makeDiscount(int percent);      // reduce price per sqm
    int getTotalPrice();                 // total price with city modifier
    double averageSqmPerRoom();          // sqm / rooms
    String toString();                   // summary of property
}
