### DataBase Pre-Request: <br>
1. Docker <br>

### Backend Pre-Request: <br>
1. Java 8 <br>

### Frontend Pre-Request: <br>
1. Docker <br>
<br>

### Start up Database: <br>
1. cd db <br>
2. docker-compose up <br>

\* Reminder: Data dictionary having lower_case_table_names=0 which only be used on Unix. <br>
For Windows, it would need to re-initializing the database and re-insert the data by mysqldump. <br>
Ref - https://dev.mysql.com/doc/refman/8.0/en/identifier-case-sensitivity.html <br>

### Start up Backend: <br>
1. cd backend <br>
2. java -jar Warehouse-Inventory-System-0.0.1.jar <br>
3. Verify with - http://127.0.0.1:8080/location/ <br>

### Start up Frontend: <br>
1. cd frontend <br>
2. docker-compose up <br>
3. Open - http://127.0.0.1:3000/ <br>


### Day 1
1. Design data and system structure
2. Select for Database - MySQL
3. Select for JAVA Framework - Spring Boot
4. Select for Frontend Framework - Reactjs with CoreUI

### Day 2
1. Setup MySQL with Docker
2. Search for Restful API plugin selection with Spring Boot
3. Setup Spring Boot
4. Learn for Spring Boot project file structure
5. Setup and test API connect with Database
6. Setup Reactjs with Local Machine
7. Build for frontend UI
8. Call Restful API in frontend

### Day 3
1. Learn React Hook
2. Build more UI and Call more API
3. Select for csv parse library - papaparse
4. Add more APIs
5. Fix for CORS issue for local development
6. Pack Reactjs project with Docker
7. Fix CORS with global config in Spring Boot
8. Update README
