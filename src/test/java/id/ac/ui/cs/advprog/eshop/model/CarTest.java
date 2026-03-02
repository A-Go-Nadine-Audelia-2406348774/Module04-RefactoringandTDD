package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId("idtes");
        this.car.setCarName("Toyota Supra");
        this.car.setCarColor("Red");
        this.car.setCarQuantity(10);
    }

    @Test
    void testGetCarId() {
        assertEquals("idtes", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Supra", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Red", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(10, this.car.getCarQuantity());
    }

    @Test
    void testSetCarId() {
        String newId = "new-uuid";
        this.car.setCarId(newId);
        assertEquals(newId, this.car.getCarId());
    }

    @Test
    void testSetCarName() {
        String newName = "Honda Civic";
        this.car.setCarName(newName);
        assertEquals(newName, this.car.getCarName());
    }

    @Test
    void testSetCarColor() {
        String newColor = "Black";
        this.car.setCarColor(newColor);
        assertEquals(newColor, this.car.getCarColor());
    }

    @Test
    void testSetCarQuantity() {
        int newQuantity = 5;
        this.car.setCarQuantity(newQuantity);
        assertEquals(newQuantity, this.car.getCarQuantity());
    }
}