WeatherForecast -- RESTfull API service для агрегации данных о погоде.

+ Используются Spring Boot и Tomcat Server;
+ Используются 5 API из которых: 4 API для получения данных
о погоде и 1 API -- для определения координат города по
его названию;
+ Подключено логгирование с помощью библиотеки log4j 2;
+ Подключено Spring Cache;
+ Настроены ассинхронные запросы к разным API с помощью
CompletableFuture и ExecutorService;
+ Парсинг данных от разных API происходит с помощью библиотек:
org.json, jackson, XPath;

Доступные запросы:
http://localhost:8081/temperature/ua/Sumy
http://localhost:8081/cityCoordinates/ua/Sumy
http://localhost:8081/pressure/ua/Sumy
http://localhost:8081/windSpeed/ua/Sumy
http://localhost:8081/humidity/ua/Sumy
http://localhost:8081/fullWeather/ua/Sumy
http://localhost:8081/sunriseTime/ua/Sumy
http://localhost:8081/feelsLikeTemperature/ua/Sumy
http://localhost:8081/directionWind/ua/Sumy
http://localhost:8081/weatherDescription/ua/Sumy

Возвращаемые данные доступны в форматах: xml и json (по умолчанию).
Формат можно указать в строке браузера дописав в конце: .xml или .json.
Например, http://localhost:8081/temperature/ua/Sumy.xml
Если формат не задан "через точку", данные будут возвращены в формате json.
Также, в этом случае, формат может быть изменен добавлением header
accept:application/json или accept:application/xml к HTTP-запросу.