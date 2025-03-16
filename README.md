# Spring Boot Game Sales and CSV Import API Assessment

A Spring Boot application that imports data from CSV files into a MySQL database using OpenCSV, and calculate, query Game Sales. The API supports tracks progress using a progress service.

This app doesn't require any middleware to achieve the desired outcome. However, we are using the prototype @Scope to create a new instance for each injection (request), allowing us to handle multiple imports simultaneously.

---

## 🚀 Features
- **CSV Import**: Import data from CSV files into the MySQL database.
- **Progress Tracking**: Track the status of the import (IN_PROGRESS, COMPLETED, FAILED).
- **Error Handling**: Detailed error messages for failed imports.
- **Game Sales API**: Retrieve and analyze game sales data.
- **Execution Time Tracking**: Logs execution times for API calls for performance insights.

---

## ⚙️ Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8+

---

## 📂 Project Structure

```
├── src/main/java
│   ├── net.assessment.springboot
│   │   ├── controller
│   │   │   └── ImportController.java
│   │   │   └── GameSaleController.java
│   │   ├── service
│   │   │   ├── ImportService.java
│   │   │   └── ProgressService.java
│   │   ├── model
│   │   │   ├── Game.java
│   │   │   └── Progress.java
│   │   └── repository
│   │       ├── GameRepository.java
│   │       └── ProgressRepository.java
├── src/main/resources
│   └── application.properties
└── pom.xml
```

---
## 📂 Database Structure

game table:

![image](https://github.com/user-attachments/assets/9597fba6-81c8-4e96-8e9d-f1e5f092c40c)


progress table:

![image-1](https://github.com/user-attachments/assets/2e645edd-0c07-4082-aa19-2c4d4d040206)


---

## ⚙️ Configuration

### `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
management.endpoints.web.exposure.include=*
```

---

## 🛠️ Installation and Running

1. **Clone the Repository**
```bash
git clone https://github.com/your-repo/csv-import-api.git
cd csv-import-api
```

2. **Build the Application**
```bash
mvn clean install
```

3. **Run the Application**
```bash
mvn spring-boot:run
```

---

## 📄 API Endpoints

### 1. **Import CSV**
- **URL**: `/api/import`
- **Method**: `POST`
- **Request**:
  - `multipart/form-data` with the CSV file.

```bash
curl -F "file=@/path/to/yourfile.csv" http://localhost:8080/api/import
```

- **Response**:
```json
{
  "message": "Import started successfully."
}
```

### 2. **Get Game Sales**
- **URL**: `/api/getGameSales`
- **Method**: `GET`
- **Query Parameters**:
  - `page` (default `0`)
  - `size` (default `100`)
  - `fromDate` (optional, format `yyyy-MM-dd'T'HH:mm:ss`)
  - `toDate` (optional, format `yyyy-MM-dd'T'HH:mm:ss`)
  - `price` (optional, for price filtering)
  - `priceCondition` (optional, `greater` or `less`)

- **Example**:
```bash
curl -X GET "http://localhost:8080/api/getGameSales?page=1&size=50"
curl -X GET "http://localhost:8080/api/getGameSales?price=50&priceCondition=greater"
curl -X GET "http://localhost:8080/api/getGameSales?fromDate=2024-01-01T00:00:00&toDate=2024-12-31T23:59:59"
```

### 3. **Get Total Sales**
- **URL**: `/api/getTotalSales`
- **Method**: `GET`
- **Query Parameters**:
  - `fromDate` (required, format `yyyy-MM-dd'T'HH:mm:ss`)
  - `toDate` (required, format `yyyy-MM-dd'T'HH:mm:ss`)
  - `gameNo` (optional, filter by game number)

- **Example**:
```bash
curl -X GET "http://localhost:8080/api/getTotalSales?fromDate=2024-01-01T00:00:00&toDate=2025-12-31T23:59:59&gameNo=2"
```

---

## 📂 Sample CSV Format
```csv
1,1001,GameName1,Code001,1,100.50,5.00,120.00,2025-03-16 12:00:00
2,1002,GameName2,Code002,2,150.00,7.50,170.00,2025-03-16 13:00:00
```

- **Columns**: `gameId, gameNo, gameName, gameCode, type, costPrice, tax, salePrice, dateOfSale`

---

## ⏱️ Execution Time Calculation
Each API endpoint measures execution time using the following method:

```java
long startTime = System.currentTimeMillis();
// Execute API logic here
long endTime = System.currentTimeMillis();
long executionTime = endTime - startTime;
System.out.println("Execution time for /api/endpoint: " + executionTime + " ms");
```

- The calculated execution time is logged to the console for performance monitoring.
- Execution time helps identify potential performance bottlenecks in API processing.

Result for each API:
- Execution time for getGameSales: 98 ms.
- Execution time for getTotalSales: 660 ms.
- Execution time for /api/endpoint: 1439412 ms (23.99minutes).

---

## ⚡ Performance Considerations
- Batch inserts to optimize MySQL writes.
- Using the prototype @Scope to create a new instance for each injection (request)

---

## 🐞 Error Handling
- Returns detailed error messages in case of failure.
- Tracks failed import status in the `Progress` table.

---

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙌 Contribution
Feel free to fork the project and create pull requests.

---

## 📧 Contact
For any inquiries, please contact [cyrus.esiang@gmail.com].

