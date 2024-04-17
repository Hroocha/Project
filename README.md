# Shop Project (backend only)
Реализован бэкэнд магазина
## Содержание
- [Описание проекта](#описание-проекта)
- [Установка проекта](#установка-проекта)
- [Тестирование проекта](#тестирование-проекта)

## Описание проекта
Стек: java17, spring (boot, security, jpa, cloud), hibernate, postgresql, docker, rest api.
Реализован интернет магазин с возможностю покупки и возврата товаров. Реализованы несколько микросервисов, общаются по рест апи. 
Процесс покупки товаров подразумевает прохождение определенных этапов: проверка наличия на складе (учитывается возможность конкурентного доступа к товарам) -> проведение оплаты (платежный шлюз имитируется) -> создание гарантии.
Предусмотрены сценарии отказов, каждый сценарий обрабатывается. Обеспечена высокая надежность процесса покупки товара. Со статусной моделью прохождения процесса покупки можно ознакомиться на рисунке ниже .. 

У сервисов (User, Product, Purchase, Guarantee) свои базы данных с таблицами, используется Postgres.  
Для наглядности приложены схемы баз данных (файл DB.jpg) и схема изменения статусов покупки (файл State.jpg).  

сервисы:
- [User service](#user-service)
- [Product service](#product-service)
- [Purchase service](#purchase-service)
- [Guarantee service](#guarantee-service)
- [Report service](#report-service)
- [Gateway service](#gateway-service)
- [Eureka service](#eureka-service)

 ### User service

Подключен к базе данных Users, имеет таблицу users.  
  
Сервис позволяет:
- Зарегистрироваться новому пользователю:  
При регистрации нового пользователя, на основании введенных данных создается новая запись в таблице пользователей.
- Аутентифицироваться уже зарегистрированным пользователям:  
Требует ввести логин и пароль, проверяет их, и выдает токен, если все верно.
- Получить информацию о зарегистрированном пользователе:  
По полученному в запросе токену узнает данные о пользователе и возвращает информацию о нем.

### Product service

Подключен к базе данных Products, имеет таблицы products и warehouse.  
В products хранятся описания товаров, в warehouse количество товаров на складе.  
Также в сервисе реализован метод, который раз в несколько минут увеличивает каждому товару количество на 2 (докладывает товары на склад).  
  
Сервис реализует:
- Просмотр товаров:  
Просит номер страницы и размер страницы и выводит страницу с товарами и подробностями о них
- Просмотр подробностей о конкретном товаре по его ID:  
Просит ID товара, и по нему выводит подробности о товаре
- Метод забора товара со склада по его ID:  
Просит ID товара, проверяет его наличие на складе, уменьшает количество на 1, возвращает некоторую информацию о товаре.
- Метод возврата товара со склада по его ID:  
Просит ID товара, проверяет его наличие на складе, увеличивает количество на 1, возвращает некоторую информацию о товаре.

### Purchase service

Подключен к базе данных Purchases, имеет таблицу purchases.  
Сервис реализует логику работы с покупками.  
Покупки при создании получают определенный статус, далее методы по статусу находят покупку и изменяют состояние покупок, 
и в завершинии обновляют статус.  
  
Сервис реализует: 
- Покупка товара по его ID:  
По токуену узнает пользователя, совершает покупку товара по ID, создает запись о покупке
- Возврат покупки по ее ID:  
По токуену узнает пользователя, проверяет возможен ли возврат, делает возврат товара.
- Просмотр покупок:  
По токуену узнает пользователя, выводит список его покупок с подробностими о них.

### Guarantee service

Подключен к базе данных Guarantees, имеет таблицу guarantees.  
Сервис реализуют работу с гарантиями на покупкию

Сервис позволяет:
- Создать гарантию:  
По ID покупки создает гарантию на покупку.
- Остановить гарантию:  
По ID покупки останавливает действие гарантии.
- Узнать гарантию на конкретную покупку:  
По ID покупки дает информацию о ее гарантии.

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
http://localhost:8765/webjars/swagger-ui/index.html (Перечень сервисов содержится справа вверху)  
  
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
- Затем в докере включить повторно контейнеры сервисов, если они остановились

### Требования
Для установки и запуска проекта, необходимs
- [Intellij Idea](https://www.jetbrains.com/ru-ru/idea/).
- Docker
- Postman


```typescript

```
