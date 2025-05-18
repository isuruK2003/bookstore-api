# Bookstore API

A simple RESTful API for managing an online bookstore.

## Technologies Used
- Java 8
- JAX-RS (Jersey)
- JSON (via Jackson)

## API Endpoints

### Authors
- `GET    /api/authors` &mdash; List all authors  
- `GET    /api/authors/{id}` &mdash; Get author by ID  
- `GET    /api/authors/{id}/books` &mdash; List books by author  
- `POST   /api/authors` &mdash; Create author  
- `PUT    /api/authors/{id}` &mdash; Update author  
- `DELETE /api/authors/{id}` &mdash; Delete author  

### Books
- `GET    /api/books` &mdash; List all books  
- `GET    /api/books/{id}` &mdash; Get book by ID  
- `POST   /api/books` &mdash; Create book  
- `PUT    /api/books/{id}` &mdash; Update book  
- `DELETE /api/books/{id}` &mdash; Delete book  

### Customers
- `GET    /api/customers` &mdash; List all customers  
- `GET    /api/customers/{id}` &mdash; Get customer by ID  
- `POST   /api/customers` &mdash; Create customer  
- `PUT    /api/customers/{id}` &mdash; Update customer  
- `DELETE /api/customers/{id}` &mdash; Delete customer  

### Cart
- `GET    /api/customers/{customerId}/cart` &mdash; Get customer's cart  
- `POST   /api/customers/{customerId}/cart/items` &mdash; Add item to cart  
- `PUT    /api/customers/{customerId}/cart/items/{bookId}` &mdash; Update cart item quantity  
- `DELETE /api/customers/{customerId}/cart/items/{bookId}` &mdash; Remove item from cart  

### Orders
- `GET    /api/customers/{customerId}/orders` &mdash; List customer's orders  
- `GET    /api/customers/{customerId}/orders/{orderId}` &mdash; Get order by ID  
- `POST   /api/customers/{customerId}/orders` &mdash; Create order from cart  

## How to Run

1. Build the project using Maven:  
   ```
   mvn clean package
   ```
2. Deploy the generated WAR file (in the `target/` directory) to a servlet container like Apache Tomcat.

3. Access the API at:  
   [http://localhost:8080/bookstore/api/](http://localhost:8080/bookstore/api/)