package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("8774-id");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);
        Car createdCar = carService.create(car);
        
        assertNotNull(createdCar);
        assertEquals(car.getCarId(), createdCar.getCarId());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> allCars = carService.findAll();

        assertNotNull(allCars);
        assertEquals(1, allCars.size());
        assertEquals(car.getCarName(), allCars.get(0).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("8774-id")).thenReturn(car);
        
        Car foundCar = carService.findById("8774-id");
        
        assertNotNull(foundCar);
        assertEquals("8774-id", foundCar.getCarId());
        verify(carRepository, times(1)).findById("8774-id");
    }

    @Test
    void testUpdate() {
        when(carRepository.update(eq("8774-id"), any(Car.class))).thenReturn(car);
        
        Car updatedCar = carService.update("8774-id", car);
        
        assertNotNull(updatedCar);
        verify(carRepository, times(1)).update("8774-id", car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("8774-id");
        
        carService.deleteCarById("8774-id");
        
        verify(carRepository, times(1)).delete("8774-id");
    }
}