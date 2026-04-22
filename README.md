# DeliverX
Агрегатор доставок и логистических тарифов.

Сервис по поиску оптимальных условий доставки для физических лиц. Проект включает веб-сайт (`frontend`) и backend, реализованный в виде микросервисной архитектуры.

---

## Что делает система

DeliverX позволяет пользователю:
- ввести параметры отправления (откуда/куда, вес, габариты);
- получить и сравнить тарифы от нескольких перевозчиков;
- авторизоваться и работать с профилем пользователя.

---

## Архитектура

Frontend обращается к backend через API Gateway.

Компоненты:
- **Frontend** — пользовательский интерфейс.
- **API Gateway** — единая точка входа, маршрутизация, проверка JWT.
- **Authentication Service** — аутентификация, выдача JWT.
- **User Service** — хранение и управление профилем пользователя (`/users/me`).
- **Rates Service** — агрегация/расчёт тарифов + кэширование популярных направлений.
- **Message Broker** — очередь для асинхронных вызовов API перевозчиков.

---

## Технологии

Backend:
- Java 25+
- Spring Boot, Spring Cloud Gateway
- Spring Security (JWT)
- PostgreSQL
- Maven
- RabbitMQ

Frontend:
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

## User Service API

`user-service` стартует на `http://localhost:8082`.

### 1) Получить профиль

`GET /users/me`

Заголовок:

```http
Authorization: Bearer <JWT_TOKEN>
```

Пример ответа:

```json
{
  "email": "misha.petrov@mailtest.com",
  "firstName": "Mikhail",
  "lastName": "Petrov",
  "phone": "+7 (900) 123-45-67",
  "address": "Moscow, Tverskaya 1"
}
```

### 2) Обновить профиль

`PUT /users/me`

Тело запроса:

```json
{
  "firstName": "Mikhail",
  "lastName": "Petrov",
  "phone": "+7 (900) 123-45-67",
  "address": "Moscow, Tverskaya 1"
}
```

---

## Как запустить проект после `git clone`

### 1. Поднять контейнеры

> Docker-образы для `auth-service` и `user-service` собираются multi-stage Dockerfile'ом (Maven внутри контейнера), поэтому локально `mvn package` перед `docker compose up --build` запускать не нужно.

Из корня репозитория:

```bash
docker compose up --build -d auth-postgres auth-service user-postgres user-service
```

Проверить статус:

```bash
docker compose ps
```

Логи сервиса:

```bash
docker compose logs -f user-service
```

### 2. Получить JWT token

```bash
curl -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"misha.petrov@mailtest.com","password":"SecurePass99"}'
```

Скопируй `accessToken` из ответа.

### 3. Проверить user-service

#### GET профиль

```bash
curl http://localhost:8082/users/me \
  -H "Authorization: Bearer <TOKEN>"
```

#### PUT профиль

```bash
curl -X PUT http://localhost:8082/users/me \
  -H 'Content-Type: application/json' \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "firstName":"Mikhail",
    "lastName":"Petrov",
    "phone":"+7 (900) 123-45-67",
    "address":"Moscow, Tverskaya 1"
  }'
```

### 4. Остановить контейнеры

```bash
docker compose down
```

Если нужно удалить volume базы:

```bash
docker compose down -v
```
