# binance_test_task

## Опис проекту: 

Написати простий контролер (Spring, JavaEE або інший фреймворк), який буде давати поточну ціну на криптовалютну пару (наприклад BTCUSDT, ETHUSDT).

Контролер повинен брати ціни зі сховища, яке наповнюється асинхронно, використовуючи метод https://binance-docs.github.io/apidocs/futures/en/#mark-price поле марк прайс

## Шляхи:

http://localhost:8080/api/v1/binance - головна сторінка

http://localhost:8080/api/v1/binance/get_price - запит на отримання ціни по певному символу

http://localhost:8080/swagger-ui/index.html - сваггер файл з документацію та можливостями тестування
