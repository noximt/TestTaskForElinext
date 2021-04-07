#Тестовое задание для Elinext

### Установка
* Для сборки требуется установить JDK и Maven
* Подробную инструкцию по установке для различных операционных систем можно найти на https://www.baeldung.com/install-maven-on-windows-linux-mac

### Cборка
Сборка происходит с помощью команды mvn package

###Тесты 
Для сборки написано 4 теста, покрывающие следующие ситуации:
* первый тест проверяет на класс и его зависимости на все binding'и, принадлежность к одному классу и на создание инстанса.
* второй тест проверяет Provider на null если нет соответствующего binding'а, а также если он есть.
* третий тест проверяет на создание Singleton'ов для зависимостей класса.
* четвертый тест проверяет на создание Singleton'а для основного класса и его зависимостей.







