# FixFlow — Cloud-Based Service Request & Maintenance Management System

A beginner-friendly service-request platform built with Java, Spring Boot, Spring Data JPA, HTML, CSS and JavaScript. It runs locally with no database setup and can be deployed to AWS (EC2, RDS, S3).

## Tech Stack
- Java 17+
- Spring Boot 3.4 / Spring Data JPA
- H2 embedded database (default) or MySQL (optional / for AWS RDS)
- HTML, CSS, JavaScript
- REST APIs
- AWS EC2, RDS and S3 (optional)

## Main Features
- Create maintenance/service requests
- Priority-based SLA deadlines (HIGH 4h, MEDIUM 12h, LOW 24h)
- Staff assignment
- Status workflow: OPEN → ASSIGNED → IN_PROGRESS → RESOLVED
- Dashboard statistics
- Sample users and staff created automatically on first run
- AWS S3 storage service prepared for file uploads (optional)

## Quick Start (no database setup needed)

**Only requirement: Java 17 or newer.** Maven is not required; the included Maven Wrapper downloads it automatically (internet needed on the first run).

Linux / macOS:
```bash
./run-linux.sh
```

Windows: double-click `run-windows.bat`

Or manually from the project folder:
```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

Then open **http://localhost:8080**.

Data is stored in a local `data/` folder (H2 database). Delete that folder to reset to the sample data. You can inspect the database at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:file:./data/fixflow;AUTO_SERVER=TRUE`, user `sa`, empty password).

AWS is **not** needed locally; leave `aws.s3.bucket` blank in `application.properties`.

## Optional: Run with MySQL
MySQL is no longer required. To use it (locally or with RDS):

```bash
DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

On Windows (PowerShell):
```powershell
$env:DB_PASSWORD="your_password"; .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

Defaults: `DB_URL=jdbc:mysql://localhost:3306/fixflow?createDatabaseIfNotExist=true`, `DB_USERNAME=root`, empty password. The database is created automatically (`database/schema.sql` is optional reference). Settings live in `src/main/resources/application-mysql.properties`, so no passwords are written into project files.

## API Overview
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/requests` | List all requests |
| GET | `/api/requests/{id}` | Get one request |
| POST | `/api/requests` | Create a request |
| PUT | `/api/requests/{id}/status` | Update status, body `{"status":"IN_PROGRESS"}` |
| PUT | `/api/requests/{id}/assign/{staffId}` | Assign staff |
| DELETE | `/api/requests/{id}` | Delete a request |
| GET | `/api/staff` | List staff |
| GET | `/api/dashboard/stats` | Dashboard counts |

Valid statuses: `OPEN`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`. Errors return JSON like `{"message": "..."}` with a 4xx status (e.g. 404 for an unknown request, 400 for an invalid status).

## AWS Deployment
See `deployment/aws-ec2-rds.md` for the EC2/RDS setup and S3 configuration.

## Troubleshooting
- **`Java 17+ is not installed`**: install a JDK 17 or newer and make sure `java -version` works.
- **Port 8080 already in use**: change `server.port` in `src/main/resources/application.properties`.
- **Dependency download fails**: check your internet connection/proxy; the first run needs it.

## Important
Never commit real database passwords, AWS access keys, or other secrets to GitHub.
