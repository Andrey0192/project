# DeliverX
Агрегатор доставок и логистических тарифов

Сервис по поиску оптимальных условий доставки для физических лиц. Проект включает веб-сайт (frontend) и backend, реализованный в виде микросервисной архитектуры.

---

## Что делает система

DeliverX позволяет пользователю:
- ввести параметры отправления (откуда/куда, вес, габариты);
- получить и сравнить тарифы от нескольких перевозчиков;
  
---

## Архитектура

Frontend обращается к backend через API Gateway.

Компоненты:
- **Frontend** — пользовательский интерфейс
- **API Gateway** — единая точка входа, маршрутизация, проверка JWT.
- **Authentication Service** - аутентификация, выдача JWT, refresh token, guest-access.
- **User Profile Service** — хранение и управление данными пользователя.
- **Rates Service** — агрегация/расчёт тарифов + кэширование популярных направлений.
- **Message Broker** - очередь для асинхронных вызовов API перевозчиков.


---

## Технологии

Backend:
- Java 25+
- Spring Boot, Spring Cloud Gateway
- Spring Security (JWT)
- PostgreSQL
- Maven
- RabbitMQ

Frontend :
- TypeScript
- React
- Next.js

Инфраструктура и качество:
- Docker 
- Unit tests 
- Postman 
- GitHub Flow
- CI/CD (GitHub Actions)
- Sonar (статический анализ качества кода)
---



## Как запустить auth-service + user-service локально

1. Собрать JAR-файлы сервисов:

```bash
mvn -f deliverx-backend-rates/auth-service/pom.xml clean package -DskipTests
mvn -f deliverx-backend-rates/user-service/pom.xml clean package -DskipTests
```

2. Поднять контейнеры:

```bash
docker compose up --build -d auth-postgres user-postgres auth-service user-service
```

3. Проверить, что контейнеры в статусе `healthy/running`:

```bash
docker compose ps
```

4. Получить JWT токен (логин через auth-service):

```bash
curl -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"qwerty123"}'
```

5. Проверить `GET /users/me`:

```bash
curl http://localhost:8082/users/me \
  -H 'Authorization: Bearer <JWT>'
```

6. Проверить `PUT /users/me`:

```bash
curl -X PUT http://localhost:8082/users/me \
  -H 'Authorization: Bearer <JWT>' \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Ира Савелова","phone":"+79991234567","city":"Moscow"}'
```

7. Смотреть логи сервиса:

```bash
docker compose logs -f user-service
```
