-- obviously missing foreign keys and indexes
-- skipping for the sake of simplicity

DROP TABLE IF EXISTS instructor;
CREATE TABLE IF NOT EXISTS instructor (
    id VARCHAR(100) not null,
    first_name VARCHAR(200) not null,
    last_name VARCHAR(200) not null,
    bio VARCHAR(4000),
    age int,
    keywords ARRAY[1000],
    primary key (id)

);

DROP TABLE IF EXISTS course;
CREATE TABLE IF NOT EXISTS course (
    id VARCHAR(100) not null,
    title VARCHAR(200) not null,
    description VARCHAR(200) not null,
    duration VARCHAR(4000),
    keywords ARRAY[1000],
    amount DECIMAL(20, 2),
    currency VARCHAR(100),
    primary key (id)
);

DROP TABLE IF EXISTS user_account;
CREATE TABLE IF NOT EXISTS user_account (
    id VARCHAR(100) not null,
    first_name VARCHAR(200) not null,
    last_name VARCHAR(200) not null,
    email VARCHAR(200),
    password VARCHAR(4000),
    enabled BIT,
    role VARCHAR(100),
    primary key (id)
);

DROP TABLE IF EXISTS cart;
CREATE TABLE IF NOT EXISTS cart (
    id VARCHAR(100) not null,
    user_id VARCHAR(100) not null,
    primary key (id)
);

DROP TABLE IF EXISTS cart_course;
CREATE TABLE IF NOT EXISTS cart_course (
    cart_id VARCHAR(100) not null,
    course_id VARCHAR(100) not null,
    primary key (cart_id, course_id)
);

DROP TABLE IF EXISTS customer_order;
CREATE TABLE IF NOT EXISTS customer_order (
    id VARCHAR(100) not null,
    user_id VARCHAR(100)  not null,
    ordered_at DATE  not null,
    primary key (id)
);

DROP TABLE IF EXISTS order_line;
CREATE TABLE IF NOT EXISTS order_line (
    id VARCHAR(100) not null,
    ORDER_ID VARCHAR(100) not null,
    COMMENT VARCHAR(1000) ,
    amount DECIMAL(20, 2),
    currency VARCHAR(100),
    course_id VARCHAR(100) not null,
    primary key (id)
);
