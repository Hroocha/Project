# Shop Project (backend only)
Реализован бэкэнд магазина.  
Стек: java17, spring (boot, security, jpa, cloud), hibernate, postgresql, docker, rest api.

## Содержание
- [Описание проекта](#описание-проекта) 
- [Сервисы](#сервисы)
- [Установка проекта](#установка-проекта)
- [Тестирование проекта](#тестирование-проекта)

## Описание проекта
  
Реализован интернет магазин с возможностю покупки и возврата товаров.  
Реализованы несколько микросервисов, они общаются по рест апи, ниже серовисы будут более подробно описаны.  
##### Процесс покупки товаров подразумевает прохождение определенных этапов:  
-проверка наличия на складе (учитывается возможность конкурентного доступа к товарам) ->   
-проведение оплаты (платежный шлюз имитируется) ->  
-создание гарантии.  
##### Процесс возврата товара также построен на основе проходждения этапов:  
-проверка возможности возврата товара ->  
-возврат средств (платежный шлюз имитируется) ->  
-возврат товара на склад.  
  
Предусмотрены сценарии отказов, каждый сценарий обрабатывается. Обеспечена высокая надежность процесса покупки товара. Со статусной моделью прохождения процесса покупки можно ознакомиться на рисунке ниже, слева.  
  
У сервисов (User, Product, Purchase, Guarantee) свои базы данных с таблицами, используется Postgres, ознакомиться можно на рисунке ниже, справа.  
  
<img src="https://github.com/Hroocha/ShopProject/blob/master/State.jpg" width="50%" height="50%"> <img src="https://github.com/Hroocha/ShopProject/blob/master/%D0%92atabases.jpg" width="47%" height="47%">
  
### Сервисы:
- [User service](#user-service)
- [Product service](#product-service)
- [Purchase service](#purchase-service)
- [Guarantee service](#guarantee-service)
- [Report service](#report-service)
- [Gateway service](#gateway-service)
- [Eureka service](#eureka-service)

 ### User service

Сервис отвечает за реализацию логики работы с данными пользователей и аутентификацию.  
  
Сервис позволяет:
- Зарегистрироваться новому пользователю.  
При регистрации нового пользователя, на основании введенных данных создается новая запись в таблице пользователей.
- Аутентифицироваться уже зарегистрированным пользователям.  
Требует ввести логин и пароль, проверяет их, и выдает токен, если все верно.
- Получить информацию о зарегистрированном пользователе.  
По полученному в запросе токену узнает данные о пользователе и возвращает информацию о нем.
  
Подключен к базе данных Users, имеет таблицу users.

### Product service
  
Сервис отвечает за логику работы с товарами.  
Также в сервисе реализован метод, который раз в несколько минут увеличивает каждому товару количество на 2 (докладывает товары на склад).  
  
Сервис реализует:
- Просмотр товаров.  
Просит номер страницы и размер страницы и выводит страницу с товарами и подробностями о них.
- Просмотр подробностей о конкретном товаре по его ID.  
Просит ID товара, и по нему выводит подробности о товаре.
- Метод забора товара со склада по его ID.  
Просит ID товара, проверяет его наличие на складе, уменьшает количество на 1, возвращает некоторую информацию о товаре.
- Метод возврата товара со склада по его ID.  
Просит ID товара, проверяет его наличие на складе, увеличивает количество на 1, возвращает некоторую информацию о товаре.  
  
Подключен к базе данных Products, имеет таблицы products и warehouse.  
В products хранятся описания товаров, в warehouse количество товаров на складе.

### Purchase service

Сервис реализует логику работы с покупками.  
Покупки при создании получают определенный статус, далее методы по статусу находят покупку и изменяют состояние покупок, 
и в завершинии обновляют статус.  
  
Сервис реализует: 
- Покупка товара по его ID.  
По токуену узнает пользователя, совершает покупку товара по ID, создает запись о покупке.
- Возврат покупки по ее ID.  
По токуену узнает пользователя, проверяет возможен ли возврат, делает возврат товара.
- Просмотр покупок.  
По токуену узнает пользователя, выводит список его покупок с подробностими о них.
  
Подключен к базе данных Purchases, имеет таблицу purchases.  

### Guarantee service
Сервис реализуют работу с гарантиями на покупки.  
Сервис позволяет:
- Создать гарантию.  
По ID покупки создает гарантию на покупку.
- Остановить гарантию.  
По ID покупки останавливает действие гарантии.
- Узнать гарантию на конкретную покупку.  
По ID покупки дает информацию о ее гарантии.
  
Подключен к базе данных Guarantees, имеет таблицу guarantees.  

### Report service

Сервис отвечает за статистику.  

Позволяет:
- Получить статистику по продажам за период.  
Возвращает количество совершенных покупок и сумму.
- Получить статистику по продажам конкретного товара за период.  
Возвращает количество совершенных покупок по конкретному товару и сумму.
- Получить средний чек по продажам за период.  
Возвращает средний чек.
- Получить средний чек по продажам конкретного пользователя за период.  
Возвращает средний чек по конкретному товару.

### Gateway service

Сервис перенаправляет запросы к сервисам ответственным за реализацию.  
А также объединяет документацию сервисов, при развертывании проекта документация доступна по ссылке:  
http://localhost:8765/webjars/swagger-ui/index.html (Перечень сервисов содержится справа вверху).  
  
Endpoints:  
- /products
- /products/{id}
- /registration
- /auth
- /me
- /purchase/{id}
- /refund/{id}
- /orders
- /statistics/sales
- /statistics/sales/{product_id}
- /statistics/average_bill
- /statistics/average_bill/{user_id}

### Eureka service

Сервис подключает к себе все сервисы (и если есть их инстансы) и позволяет им обращаться к друг другу.

## Установка проекта

- В каждом сервисе запустить плагин jib:dockerBuild - Создадутся образы сервисов
- Запустить dockercompose
 ```sh
docker-compose up -d 
```

## Тестирование проекта

Для проверки этндпоинтов в Postman нужно нажать import и вставить туда cURL:  

Регистрация нового пользователя:
```
curl --location 'localhost:8765/registration' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "123",
    "password": "123",
    "confirmPassword": "123",
    "name": "123",
    "mail": "123@noMail.com"
}'
```

Аутентификация:
```
curl --location 'localhost:8765/auth' \
--header 'Content-Type: application/json' \
--data '{
    "login": "123",
    "password": "123"
}'
```

Информация о пользователе:  
(потребуется вставить актуальный токен, который выдается при аутентификации)
```
curl --location 'localhost:8765/me' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImQ4ZWY5ZTRjLTc4MzgtNGM3OC1iZDMyLWVkZTlmZTQ3YWI2OCIsImlhdCI6MTcxMjI0NTAzNywiZXhwIjoxNzEyMjQ2ODM3fQ.K5HIGDBLEaNSZfgT4ii0Yb_YOsT2X24-0zowPuUVP5k' \
--data ''
```

Просмотр всех продуктов:
```
curl --location 'localhost:8765/products?page=0&page_size=3' \
--data ''
```

Просмотр конкретного продукта:  
(потребуется указать ID продукта, найти ID можно при просмотре всех продуктов)
```
curl --location --request GET 'localhost:8765/products/6ec9b4cb-6274-437c-b96a-6e246d564ef8' \
--header 'Content-Type: application/json' \
--data '{
    "id": "6ec9b4cb-6274-437c-b96a-6e246d564ef8"
}'
```

Покупка продукта:  
(потребуется указать ID продукта и токен, найти ID можно при просмотре всех продуктов, а получить токен можно при аутентификации)
```
curl --location 'localhost:8765/purchase/6ec9b4cb-6274-437c-b96a-6e246d564ef8' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOYXN0aWExMjMiLCJpZCI6IjNkNzM5OTZjLTdkOWUtNDIyZi05NGZjLTM4ODQwZjAyMDJlOSIsImlhdCI6MTcxMTk2NDQ1MSwiZXhwIjoxNzExOTY2MjUxfQ.DLzmQGm1xh0hG8fTtkoRBQvWA5DGPZAkUq6C4-OYmBs' \
--data '{
    "id": "6ec9b4cb-6274-437c-b96a-6e246d564ef8"
}
'
```

Вернуть покупку:  
(потребуется указать ID покупки и токен, найти ID покупки можно при просмотре заказов, а получить токен можно при аутентификации)
```
curl --location 'localhost:8765/refund/0cddf6fb-f7a0-4d08-9e93-2a658540f973' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOYXN0aWExMjMiLCJpZCI6IjNkNzM5OTZjLTdkOWUtNDIyZi05NGZjLTM4ODQwZjAyMDJlOSIsImlhdCI6MTcxMTYwOTMwMywiZXhwIjoxNzExNjExMTAzfQ.0uTLmTfDSz06IFBJUBBSlr73yVv9fGTgNYNZSZrywUg' \
--data '{
    "purchase_id": "0cddf6fb-f7a0-4d08-9e93-2a658540f973"
}'
```

Посмотреть заказы:  
(потребуется указать токен, получить токен можно при аутентификации)
```
curl --location 'localhost:8765/orders?page=0' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImQ4ZWY5ZTRjLTc4MzgtNGM3OC1iZDMyLWVkZTlmZTQ3YWI2OCIsImlhdCI6MTcxMjI0NTc5OSwiZXhwIjoxNzEyMjQ3NTk5fQ.eWeXSX7mg_svWIXuLKFzR8dKHp3t8UW2w7mHLBvTcgY' \
--data ''
```

Статистика, сколько продано за период:  
(потребуется указать период и токен, получить токен можно при аутентификации)
```
curl --location 'localhost:8765/statistics/sales?date_from=2022-01-31&date_to=2024-04-02'
```

Статистика, сколько продано конкретного товара за период:  
(потребуется указать ID товара, период и токен, найти ID можно при просмотре всех продуктов, а получить токен при аутентификации)
```
curl --location 'localhost:8765/statistics/sales/6ec9b4cb-6274-437c-b96a-6e246d564ef8?date_from=2022-01-31&date_to=2024-04-02' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImQ4ZWY5ZTRjLTc4MzgtNGM3OC1iZDMyLWVkZTlmZTQ3YWI2OCIsImlhdCI6MTcxMjI0NzkwMiwiZXhwIjoxNzEyMjQ5NzAyfQ.vj9PI_Cg-RhrAblpnm9gOB8e2G0cfPGpzSNXwlUgX8w'
```

Статистика, средний чек за период:  
(потребуется указать период и токен, получить токен можно при аутентификации)
```
curl --location 'localhost:8765/statistics/average_bill?date_from=2022-01-31&date_to=2024-04-05' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImQ4ZWY5ZTRjLTc4MzgtNGM3OC1iZDMyLWVkZTlmZTQ3YWI2OCIsImlhdCI6MTcxMjI1MDEyMCwiZXhwIjoxNzEyMjUxOTIwfQ.7BgMbC8VZHUJSqNCDz7HE6fJXvXOwaZTC24RAuhqsSs'
```

Статистика, средний чек у конкретного пользователя за период:  
(потребуется указать ID пользователя, период и токен, ID пользователя можно узнать в информации о пользователе, а получить токен можно при аутентификации)
```
curl --location --request GET 'localhost:8765/statistics/average_bill/d8ef9e4c-7838-4c78-bd32-ede9fe47ab68?date_from=2022-01-31&date_to=2024-04-05' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImQ4ZWY5ZTRjLTc4MzgtNGM3OC1iZDMyLWVkZTlmZTQ3YWI2OCIsImlhdCI6MTcxMjI1MDEyMCwiZXhwIjoxNzEyMjUxOTIwfQ.7BgMbC8VZHUJSqNCDz7HE6fJXvXOwaZTC24RAuhqsSs' \
--data '{
    "user_id": "d8ef9e4c-7838-4c78-bd32-ede9fe47ab68"
}
'
```
