create table users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) not null  unique,
    password varchar(200) not null,
    enabled boolean not NULL default true,
    created_at TIMESTAMP default CURRENT_TIMESTAMP on UPDATE CURRENT_TIMESTAMP
);