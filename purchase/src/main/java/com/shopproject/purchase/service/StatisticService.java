package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.SalesRequest;
import com.shopproject.purchase.dtos.SalesResponse;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис отвечает за логику получения статистики по продажам
 */
public interface StatisticService {

    /**
     * Получить список заказов за период времени
     *
     * @param from дата начала периода
     * @param to дата окончания периода
     * @return Информацию о покупках за указанный период
     */
    SalesResponse getSales(LocalDateTime from, LocalDateTime to);

    /**
     * Получить список заказов по конкретному товару за период времени
     *
     * @param productId Идентификатор товара
     * @param from дата начала периода
     * @param to дата окончания периода
     * @return Информацию о покупках конкретного товара за указанный период
     */
    SalesResponse getSalesByProductId(UUID productId, LocalDateTime from, LocalDateTime to);

    /**
     * Получить средний чек по продажам всех товаров за указанный период
     *
     * @param salesRequest Информация о периоде по которому нужна статистика
     * @return Средняя стоимость покупок за период
     */
    Double getAverageBill (SalesRequest salesRequest);

    /**
     * Получить средний чек по конкретоному пользователю за указанный период
     *
     * @param userId Идентификатор пользователя
     * @param salesRequest Информация о периоде по которому нужна статистика
     * @return Средняя стоимость покупок конкретного пользователя за период
     */
    Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest);
}
