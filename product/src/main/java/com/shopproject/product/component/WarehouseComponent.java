package com.shopproject.product.component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WarehouseComponent {

    @PersistenceContext //открывает и закрывает автоматически
    private EntityManager entityManager;

    @Scheduled(fixedRate = 300000) // 5 минут = 300000 миллисекунд; 10 секунд = 10000 миллисекунд
    @Transactional
    public void increaseAllQuantities() {
        entityManager.createNativeQuery("UPDATE warehouse SET quantity = quantity + 2 ").executeUpdate();
    }
}
