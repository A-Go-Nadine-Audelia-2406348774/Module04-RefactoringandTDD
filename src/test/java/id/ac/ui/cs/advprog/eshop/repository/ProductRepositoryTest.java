package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl();
    }

    @Test
    void testCreateAndFindAll() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product.setProductName("Sampo Cap Usep");
        productRepository.create(product);

        Product foundProduct = productRepository.findById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testUpdateSuccess() {
        Product product = new Product();
        product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product.setProductName("Sampo Cap Usep");
        product.setProductQuantity(100);
        productRepository.create(product);
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Usep Baru");
        updatedProduct.setProductQuantity(200);
        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Sampo Cap Usep Baru", result.getProductName());
        assertEquals(200, result.getProductQuantity());
        Product savedProduct = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Usep Baru", savedProduct.getProductName());
    }

    @Test
    void testUpdateNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product.setProductName("Sampo Cap Usep");
        productRepository.create(product);
        productRepository.delete(product.getProductId());
        Product foundProduct = productRepository.findById(product.getProductId());
        assertNull(foundProduct);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        productRepository.create(product1);
        Product product2 = new Product();
        product2.setProductId("id-2");
        productRepository.create(product2);
        productRepository.delete("id-1");
        assertNull(productRepository.findById("id-1"));
        assertNotNull(productRepository.findById("id-2"));
    }

    @Test
    void testUpdateWhenMultipleProductsExistButNotFirst() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Produk 1");
        productRepository.create(product1);
        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Produk 2");
        productRepository.create(product2);
        Product updatedProduct2 = new Product();
        updatedProduct2.setProductId("id-2");
        updatedProduct2.setProductName("Produk 2 Terupdate");
        updatedProduct2.setProductQuantity(50);
        Product result = productRepository.update(updatedProduct2);
        assertNotNull(result);
        assertEquals("Produk 2 Terupdate", result.getProductName());
        assertEquals("Produk 1", productRepository.findById("id-1").getProductName());
    }

    @Test
    void testUpdateWhenProductListIsEmpty() {
        productRepository = new ProductRepositoryImpl(); 
        Product someProduct = new Product();
        someProduct.setProductId("any-id");
        Product result = productRepository.update(someProduct);
        assertNull(result);
    }
}