import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = new Restaurant("Five Guys","Delta",
                LocalTime.of(10,0),
                LocalTime.of(20, 0));

        Restaurant mockedRestaurant = Mockito.spy(restaurant);

        Mockito.when(mockedRestaurant.getCurrentTime())
                .thenReturn(LocalTime.of(12,0));

        assertTrue(mockedRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant = new Restaurant("Old Spaghetti Factory","Vancouver",
                LocalTime.of(11,0),
                LocalTime.of(21, 0));

        Restaurant mockedRestaurant = Mockito.spy(restaurant);

        Mockito.when(mockedRestaurant.getCurrentTime())
                .thenReturn(LocalTime.of(22,0));

        assertFalse(mockedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // >>>>>>>>>>>>>>>>>>>>>>>>> TDD <<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void getTotalOrder_called_with_no_selected_items_should_return_zero() {
        ArrayList<String> selectedItems = new ArrayList<String>();
        int total = restaurant.getTotalOrder(selectedItems);

        assertThat(total, equalTo(0));
    }

    @Test
    public void getTotalOrder_called_with_some_selected_items_should_return_total() {
        ArrayList <String> selectedItems = new ArrayList<String>();

        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        int total = restaurant.getTotalOrder(selectedItems);

        assertThat(total, equalTo(388));
    }
    // <<<<<<<<<<<<<<<<<<<<<<<<<< TDD >>>>>>>>>>>>>>>>>>>>>>>>>>>
}