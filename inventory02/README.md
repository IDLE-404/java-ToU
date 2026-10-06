# inventory02

Простое консольное приложение «Склад» на Java (Maven).

## Структура

- `Item.java` — товар (название, количество, цена)
- `Inventory.java` — склад: добавление, удаление, поиск, общая стоимость
- `Main.java` — точка входа, демонстрация работы

## Запуск

```bash
mvn compile exec:java
```

Или собрать jar:

```bash
mvn package
java -jar target/inventory02-1.0-SNAPSHOT.jar
```
