Database Configuration need to run in Adminer at localhost:9000
user: root
password: toor

Sql Command:
create database productdb;
create user 'productservice'@'%' identified by 'ThePassword';
grant all on productdb.* to 'productservice'@'%';
flush privileges;

Kafka
bootstrap-server: kafka:29092
topic: pocTopic

ProductPort:
Get All
localhost:8080/products
Get By Id
localhost:8080/products/{id}
Post
localhost:8080/products
sample body:
{
    "name":"Product",
    "description":"TestProduct",
    "brand":"TestBrand",
    "price":"1.00",
    "quantity":"1"
}
Update By Id
localhost:8080/products/{id}
sample body:
{
    "name":"Product",
    "description":"TestProduct",
    "brand":"TestBrand",
    "price":"1.00",
    "quantity":"1"
}
Delete By Id
localhost:8080/products/{id}

View All Order
localhost:8080/orders
Post Order
localhost:8080/orders
{
    "productId":"1",
    "quantity":"1",
    "totalPrice":"1"
}

View Logger
localhost:8081/logger

Check health
localhost:8080/actuator/health
localhost:8081/actuator/health