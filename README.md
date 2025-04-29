# API Online Shop
## Prasyarat

Pastikan Anda telah menginstal tools berikut di device Anda:

- Docker
- Docker Compose

### Build aplikasi Spring Boot

Pastikan proyek Spring Boot Anda sudah dibangun dan file .jar ada di dalam folder target/. Anda dapat membangunnya
menggunakan Maven atau Gradle.

Untuk Maven:

```bash
./mvnw clean package
```
Perintah ini akan menghasilkan file app.jar di folder target/.

### Konfigurasi ENV File (Optional)

Buat file .env di root proyek Anda (atau export variabel lingkungan di terminal). Berikut adalah contoh konfigurasi yang
bisa Anda gunakan:

```bash
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
DB_HOST=db
DB_PORT=5432
DB_NAME=online_shop_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
TAX_PERCENTAGE=10.00
SHIPPING_FEE=10000.00
```

### Menjalankan Aplikasi dengan Docker

Untuk menjalankan aplikasi dan database PostgreSQL menggunakan Docker Compose, gunakan perintah berikut:

```bash
docker-compose up --build
```

atau jika Anda tidak membuat .env file Anda bisa menjalankan perintah berikut:
```bash
SPRING_PROFILES_ACTIVE=prod SERVER_PORT=8080 DB_HOST=db DB_PORT=5432 DB_NAME=online_shop_db DB_USERNAME=postgres DB_PASSWORD=postgres TAX_PERCENTAGE=10.00 SHIPPING_FEE=10000.00 docker-compose up --build
```

Perintah ini akan:

- Membangun image Docker untuk aplikasi Spring Boot
- Menjalankan dua container: aplikasi Spring Boot dan PostgreSQL

### Mengakses Aplikasi

Setelah container berjalan, Anda dapat mengakses API Spring Boot di:

```
http://localhost:8080/swagger-ui/index.html
```

Aplikasi akan berjalan di port 8080 kecuali Anda ingin merubah env di file .env.

### Konfigurasi Docker

Berikut adalah isi Dockerfile yang digunakan untuk membangun image aplikasi Spring Boot:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

File JAR aplikasi akan disalin dari folder target/.

env SPRING_PROFILES_ACTIVE diatur ke prod secara default.

### Konfigurasi Docker Compose
Berikut adalah file docker-compose.yml untuk aplikasi ini:

```yml
services:
  online-shop-api:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: online-shop-api
    environment:
      - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}
      - SERVER_PORT=${SERVER_PORT:-8080}
      - DB_HOST=${DB_HOST:-db}
      - DB_PORT=${DB_PORT:-5432}
      - DB_NAME=${DB_NAME:-online_shop_db}
      - DB_USERNAME=${DB_USERNAME:-postgres}
      - DB_PASSWORD=${DB_PASSWORD:-postgres}
      - TAX_PERCENTAGE=${TAX_PERCENTAGE:-10.00}
      - SHIPPING_FEE=${SHIPPING_FEE:-10000.00}
    ports:
      - "${SERVER_PORT:-8080}:${SERVER_PORT:-8080}"
    depends_on:
      - db

  db:
    image: postgres:13
    container_name: postgres-db
    environment:
      POSTGRES_USER: ${DB_USERNAME:-postgres}
      POSTGRES_PASSWORD: ${DB_PASSWORD:-postgres}
      POSTGRES_DB: ${DB_NAME:-online_shop_db}
    volumes:
      - pgdata:/var/lib/postgresql/data
    ports:
      - "${DB_PORT:-5432}:${DB_PORT:-5432}"

volumes:
  pgdata:

```

### Stop Container
Untuk stop container, jalankan perintah berikut:

```bash
docker-compose down
```

Perintah ini akan menghentikan dan menghapus container, tetapi data di database akan tetap ada di volume pgdata.

### Postman Collection dan .env Postman

Aplikasi ini menyediakan Postman Collection yang dapat digunakan untuk menguji endpoint API. Anda dapat mendownload file
Postman Collection pada root project dengan nama **online_shop.postman_collection.json** dan **online_shop_env.postman_environment.json**

Jangan ragu untuk mengubah file docker-compose.yml dan .env sesuai dengan kebutuhan proyek Anda. Jika ada masalah atau
pertanyaan, silakan buka issue pada repositori ini atau hubungi kami untuk bantuan lebih lanjut.
