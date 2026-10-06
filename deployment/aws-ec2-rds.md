# AWS EC2 + RDS deployment

Local development needs none of this (it uses an embedded H2 database). These steps are only for deploying to AWS.

## 1. Create RDS MySQL
- Create an RDS MySQL database.
- Allow the EC2 security group to connect to port 3306.
- The `fixflow` database is created automatically on first start (`createDatabaseIfNotExist=true`), or create it yourself.

## 2. Create EC2
Install Java 17 and copy the project to the server.

Build:
```bash
./mvnw clean package
```

Run with the `mysql` profile and environment variables (never commit real passwords):
```bash
export DB_URL="jdbc:mysql://RDS-ENDPOINT:3306/fixflow"
export DB_USERNAME=admin
export DB_PASSWORD=YOUR_PASSWORD
java -jar target/fixflow-1.0.0.jar --spring.profiles.active=mysql
```

## 3. S3
Create an S3 bucket for request photos.
Give the EC2 instance an IAM role with only the required S3 permissions (no access keys in files).
Set `aws.s3.bucket` and `aws.region` (e.g. `--aws.s3.bucket=my-bucket` or `AWS_S3_BUCKET` env var).
