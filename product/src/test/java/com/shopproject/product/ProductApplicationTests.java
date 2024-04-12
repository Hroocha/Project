package com.shopproject.product;

import com.shopproject.product.dto.ProductDto;
import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.repository.ProductRepository;
import com.shopproject.product.repository.WarehouseRepository;
import com.shopproject.product.service.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
@EnableAsync
class ProductApplicationTests {

	@ClassRule
	public static PostgreSQLContainer<PsqlContainer> container = PsqlContainer.getInstance();

	@Autowired
	private ProductsService productsService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Test
	void testHighConcurrencyOnPurchase() throws InterruptedException {
		final int users = 30;

		Product product = productRepository.save(Product.builder()
				.name("test")
				.guaranteePeriod(1)
				.price(BigDecimal.ONE)
				.build());
		Warehouse warehouse = warehouseRepository.save(Warehouse.builder()
				.id(product.getId())
				.quantity(users)
				.build());

		ExecutorService executorService = Executors.newFixedThreadPool(15);
		Callable<ProductDto> callableTask = () -> {
			try {
				var productDto = productsService.take(product.getId());
				log.info("Thread: {}. Callable Ok. Product ID: {}", Thread.currentThread().getName(), productDto.getId());
				return productDto;
			} catch (Exception e) {
				log.error("Error: ", e);
				throw e;
			}
		};
		List<Callable<ProductDto>> tasks = new ArrayList<>();
		for (int i = 0; i < users; i++) {
			tasks.add(callableTask);
		}
		executorService.invokeAll(tasks);

		Assertions.assertThat(getQuantity(warehouse)).isZero();
	}

	private Integer getQuantity(Warehouse warehouse) {
		return warehouseRepository.findAllById(List.of(warehouse.getId())).get(0).getQuantity();
	}


}
