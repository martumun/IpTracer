# IpTracer
Responde a 2 consultas:

**POST /tracer**  
Body:   
```json
{
    "ip": "85.214.132.117"
}
```
Response:  
```json
{
    "countryName": "Germany",
    "isoCode": "DEU",
    "currentTimes": [
        "00:03:07"
    ],
    "distanceToBuenosAires": 11565.036430409871,
    "currency": "EUR",
    "euroExchangeRate": 1.0
}
```


**GET /tracer/stats**

Response:  
```json
{
"shortestDistanceToBuenosAires": 520.3358607205181,
"longestDistanceToBuenosAires": 11565.036430409871,
"averageDistanceToBuenosAires": 8262.022683478106
}
```

Se necesita tener mongo instalado localmente ya que se utiliza para la persistencia de los datos.  
La app fue desarrollada en java 8 con springboot.
