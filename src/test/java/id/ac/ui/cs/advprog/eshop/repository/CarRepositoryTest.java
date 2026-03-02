package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
    }

    @Test
    void testCreateWithSpecifiedId() {
        Car car = new Car();
        car.setCarId("8774-id");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);

        Car result = carRepository.create(car);

        assertEquals("8774-id", result.getCarId());
        assertEquals(car.getCarName(), result.getCarName());
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarName("Honda Civic");
        car.setCarColor("White");
        car.setCarQuantity(5);

        Car result = carRepository.create(car);

        assertNotNull(result.getCarId());
        assertFalse(result.getCarId().isEmpty());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarName("Car 1");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Car 2");
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        carIterator.next();
        assertTrue(carIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarId("test-id");
        car.setCarName("Mazda RX-7");
        carRepository.create(car);

        Car foundCar = carRepository.findById("test-id");
        assertNotNull(foundCar);
        assertEquals("test-id", foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("invalid-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarId("8774-update");
        car.setCarName("Nissan GT-R");
        car.setCarColor("Silver");
        car.setCarQuantity(2);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Nissan GT-R Nismo");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(1);

        Car result = carRepository.update("8774-update", updatedCar);

        assertNotNull(result);
        assertEquals("Nissan GT-R Nismo", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(1, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("New Info");
        Car result = carRepository.update("non-existent", updatedCar);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("8774-delete");
        car.setCarName("Mitsubishi Lancer");
        carRepository.create(car);
        carRepository.delete("8774-delete");
        Car foundCar = carRepository.findById("8774-delete");
        assertNull(foundCar);
    }

    @Test
    void testFindByIdEmptyList() {
        carRepository = new CarRepositoryImpl(); 
        Car foundCar = carRepository.findById("any-id");
        assertNull(foundCar);
    }

    @Test
    void testFindByIdWhenMultipleCarsExistButNotFirst() {
        Car car1 = new Car();
        car1.setCarId("8774-id-1");
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setCarId("8774-id-2");
        car2.setCarName("Mobil Dicari");
        carRepository.create(car2);
        Car foundCar = carRepository.findById("8774-id-2");
        
        assertNotNull(foundCar);
        assertEquals("8774-id-2", foundCar.getCarId());
    }

    @Test
    void testUpdateWhenMultipleCarsExistButNotFirst() {
        Car car1 = new Car();
        car1.setCarId("8774-id-1");
        carRepository.create(car1);
        Car car2 = new Car();
        car2.setCarId("8774-id-2");
        carRepository.create(car2);
        Car updatedInfo = new Car();
        updatedInfo.setCarName("Updated Name");
        Car result = carRepository.update("8774-id-2", updatedInfo);
        assertNotNull(result);
        assertEquals("Updated Name", result.getCarName());
    }
}