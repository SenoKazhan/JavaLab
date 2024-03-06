# Phonecode

Phonecode - это сервис, который использует SQL в качестве базы данных для хранения информации о странах.

## Как использовать

Для выполнения GET запроса используйте следующий формат URL:

    http://localhost:8080/countries/getCountryInfo?name=Беларусь
Сервер вернёт код страны и код номера телефона страны.

## Setup
1. Clone the repository: https://github.com/SenoKazhan/JavaLab1.git
2. Build this project

     * For macOS:
 
           ./mvnw clean install   
     * For Windows:
   
           mvnw clean install
## Code quality
https://sonarcloud.io/summary/overall?id=SenoKazhan_JavaLab1
