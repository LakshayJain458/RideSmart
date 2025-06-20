# 🚗 **RideSmart**

**RideSmart** is a Spring Boot backend for a ride-sharing and pooling platform.
It provides robust APIs for user registration, authentication (with JWT), driver onboarding, ride booking, ride management, and more.

---

## ✨ **Features**

* ✅ User sign-up, login, and profile management
* ✅ Driver sign-up, login, and profile management
* ✅ Ride booking using **Google Maps API** for distance and duration
* ✅ Dynamic ride cost calculation (base fare + per km fare)
* ✅ Driver assignment, ride acceptance, and cancellation logic
* ✅ User and driver ride history endpoints
* ✅ JWT-based stateless authentication
* ✅ Clean layered architecture (Controller, Service, Repository, Entity, DTO)

---

## ⚙ **Technologies Used**

* **Spring Boot**
* **Spring Security (JWT)**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL**
* **Google Distance Matrix API**
* **Lombok**
* **Maven**

---

## 🚀 **How to Run**

1️⃣ **Clone the repository**

```bash
git clone https://github.com/your-username/your-repo.git
cd your-repo
```

2️⃣ **Configure environment variables / `application.properties`**

```properties
google.maps.api.key=YOUR_GOOGLE_MAPS_API_KEY
ride.baseFare=50
ride.perKmFare=10
jwt.secret=YOUR_SECRET_KEY
```

3️⃣ **Run the Spring Boot app**

```bash
mvn spring-boot:run
```

---

## 📂 **API Endpoints (Sample)**

| Endpoint                          | Method | Description                     |
| --------------------------------- | ------ | ------------------------------- |
| `/User/SignUp`                    | POST   | Register a new user             |
| `/User/Login`                     | POST   | Login user, returns JWT token   |
| `/Driver/SignUp`                  | POST   | Register a new driver           |
| `/Driver/Login`                   | POST   | Login driver, returns JWT token |
| `/Ride/book`                      | POST   | Book a new ride                 |
| `/Ride/assign/{rideId}`           | POST   | Assign a driver to a ride       |
| `/Ride/accept`                    | POST   | Driver accepts ride             |
| `/Ride/cancel`                    | POST   | Driver cancels ride             |
| `/Ride/user/history/{userId}`     | GET    | Get ride history for a user     |
| `/Ride/driver/history/{driverId}` | GET    | Get ride history for a driver   |

---

## 🛣 **Roadmap**

✅ Backend API development
🛠 Frontend (React) → *To be added during the development stage*
🚀 Integration & Deployment

---

## 🤝 **Contributing**

You’re welcome to contribute!
👉 Submit issues or open pull requests to help improve RideSmart.

---

## 👤 **Author**

**Lakshay Jain and Lov Gupta**

---

## 📜 **License**

This project is licensed under the [MIT License](LICENSE).
