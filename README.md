## Setup & Run

### 1. Setup Configuration
```bash
# Copy the template and add your actual values
cp config/application-external.properties.template config/application-external.properties
```

Edit `config/application-external.properties` with your database password and JWT secret.

### 2. Run Application

**From IntelliJ IDEA:**
1. Open Run/Debug Configurations
2. Add VM options: `-Dspring.config.additional-location=file:./config/application-external.properties`
3. Click Run

**From Terminal:**
```bash
# Using Maven
export $(cat config/application-external.properties | xargs)
mvn spring-boot:run

# Using JAR
export $(cat config/application-external.properties | xargs)
java -jar target/your-app.jar
```

**Windows (PowerShell):**
```powershell
Get-Content config\application-external.properties | ForEach-Object {
    if ($_ -match '^([^#].+?)=(.+)$') { 
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], "Process")
    }
}
mvn spring-boot:run
```

### 3. Access Application
- API: http://localhost:8080
- Database: PostgreSQL on localhost:5432
- Redis: localhost:6379
