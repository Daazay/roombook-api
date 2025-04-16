docker c# API Documentation

This document provides an overview of the API endpoints, including paths, HTTP methods, parameters, request bodies, authentication requirements, and responses.

---

## Auth Routes

### POST /auth/signup
- Description: Register a new user.
- Request Body (pseudo-JSON):
```json
{
  "username": "text",
  "email": "text",
  "password": "text"
}
```
- Response (pseudo-JSON):
```json
{
  "user": { /* user data */ },
  "token": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### POST /auth/login
- Description: Login a user.
- Request Body (pseudo-JSON):
```json
{
  "email": "text",
  "password": "text"
}
```
- Response (pseudo-JSON):
```json
{
  "user": { /* user data */ },
  "token": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /auth/logout
- Description: Logout the authenticated user.
- Authentication: Required (JWT)
- Response: Empty body
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

---

## Booking Routes

### POST /bookings/
- Description: Create a new booking.
- Request Body (pseudo-JSON):
```json
{
  "user_id": "text",
  "room_id": "text",
  "start_time": "text",
  "end_time": "text",
  "status": "text" # optional
}
```
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "user_id": "text",
  "room_id": "text",
  "start_time": "text",
  "end_time": "text",
  "status": "text"
}
```
- Status Codes:
  - 201 Created: Success
  - 400 Bad Request: { "error": "text" }

### GET /bookings/{id}
- Description: Get booking by ID.
- Path Parameters:
  - `id`: Booking ID
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "user_id": "text",
  "room_id": "text",
  "start_time": "text",
  "end_time": "text",
  "status": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /bookings/all
- Description: Get all bookings.
- Response: Array of booking objects (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### PUT /bookings/{id}
- Description: Update booking by ID.
- Path Parameters:
  - `id`: Booking ID
- Request Body (pseudo-JSON):
```json
{
  "user_id": "text" # optional,
  "room_id": "text" # optional,
  "start_time": "text" # optional,
  "end_time": "text" # optional,
  "status": "text" # optional
}
```
- Response: Updated booking object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### DELETE /bookings/{id}
- Description: Delete booking by ID.
- Path Parameters:
  - `id`: Booking ID
- Authentication: Admin required
- Response: Deleted booking object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

---

## Building Routes

### POST /buildings/
- Description: Create a new building.
- Request Body (pseudo-JSON):
```json
{
  "name": "text",
  "address": "text"
}
```
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "name": "text",
  "address": "text"
}
```
- Status Codes:
  - 201 Created: Success
  - 400 Bad Request: { "error": "text" }

### GET /buildings/{id}
- Description: Get building by ID.
- Path Parameters:
  - `id`: Building ID
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "name": "text",
  "address": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /buildings/all
- Description: Get all buildings.
- Response: Array of building objects (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### PUT /buildings/{id}
- Description: Update building by ID.
- Path Parameters:
  - `id`: Building ID
- Request Body (pseudo-JSON):
```json
{
  "name": "text" # optional,
  "address": "text" # optional
}
```
- Response: Updated building object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### DELETE /buildings/{id}
- Description: Delete building by ID.
- Path Parameters:
  - `id`: Building ID
- Authentication: Admin required
- Response: Deleted building object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

---

## Room Routes

### POST /rooms/
- Description: Create a new room.
- Request Body (pseudo-JSON):
```json
{
  "building_id": "text",
  "name": "text",
  "capacity": "number",
  "type": "text",
  "status": "text" # optional
}
```
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "building_id": "text",
  "name": "text",
  "capacity": "number",
  "type": "text",
  "status": "text"
}
```
- Status Codes:
  - 201 Created: Success
  - 400 Bad Request: { "error": "text" }

### GET /rooms/{id}
- Description: Get room by ID.
- Path Parameters:
  - `id`: Room ID
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "building_id": "text",
  "name": "text",
  "capacity": "number",
  "type": "text",
  "status": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /rooms/building/{buildingId}
- Description: Get rooms by building ID.
- Path Parameters:
  - `buildingId`: Building ID
- Response: Array of room objects (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /rooms/all
- Description: Get all rooms.
- Response: Array of room objects (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### PUT /rooms/{id}
- Description: Update room by ID.
- Path Parameters:
  - `id`: Room ID
- Request Body (pseudo-JSON):
```json
{
  "building_id": "text" # optional,
  "name": "text" # optional,
  "capacity": "number" # optional,
  "type": "text" # optional,
  "status": "text" # optional
}
```
- Response: Updated room object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### DELETE /rooms/{id}
- Description: Delete room by ID.
- Path Parameters:
  - `id`: Room ID
- Authentication: Admin required
- Response: Deleted room object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

---

## User Routes

### GET /users/{id}
- Description: Get user by ID.
- Path Parameters:
  - `id`: User ID
- Response (pseudo-JSON):
```json
{
  "id": "text",
  "username": "text",
  "email": "text",
  "role": "text"
}
```
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### GET /users/all
- Description: Get all users.
- Response: Array of user objects (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### PUT /users/{id}
- Description: Update user by ID.
- Path Parameters:
  - `id`: User ID
- Request Body (pseudo-JSON):
```json
{
  "email": "text" # optional,
  "password": "text" # optional
}
```
- Response: Updated user object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }

### DELETE /users/{id}
- Description: Delete user by ID.
- Path Parameters:
  - `id`: User ID
- Authentication: Admin required
- Response: Deleted user object (as above)
- Status Codes:
  - 200 OK: Success
  - 400 Bad Request: { "error": "text" }
