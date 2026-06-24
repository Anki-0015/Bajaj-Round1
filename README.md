# BFHL REST API

This is my submission for the Bajaj Finserv Health hiring challenge — a Spring Boot REST API that takes in a mixed array of strings and breaks them down into numbers, alphabets, and special characters, along with some extra computed fields.

---

## What does it do?

You send it a POST request with an array like this:

{
  "data": ["a", "1", "334", "4", "R", "$"]
}

And it gives you back a structured response with:
1. Even and odd numbers (as strings)
2. Alphabets in uppercase
3. Special characters
4. Sum of all numbers
5. A fun `concat_string` — all alphabetic characters reversed and alternating caps

{
  "is_success": true,
  "user_id": "ankit_bansal_15072005",
  "email": "bansalankit1575@gmail.com",
  "roll_number": "2310992106",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}


## Tech Stack

1. **Java 17** (compiled target) with **Java 24 JDK**
2. **Spring Boot 3.3.4**
3. **Maven** for builds
4. **Docker** for deployment
5. Hosted on **Render**


## Running it locally

The server starts at `http://localhost:8080`.

