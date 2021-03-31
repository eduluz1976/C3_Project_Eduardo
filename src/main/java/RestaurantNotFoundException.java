public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String restaurantName) {
        super(restaurantName);
    }
}
