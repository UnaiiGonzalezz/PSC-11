DROP SCHEMA IF EXISTS tiendaOnlineDB;
DROP USER IF EXISTS 'spq'@'localhost';
DROP USER IF EXISTS 'opled'@'localhost';


CREATE SCHEMA tiendaOnlineDB;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';
CREATE USER IF NOT EXISTS 'opled'@'localhost' IDENTIFIED BY 'opled';

USE tiendaOnlineDB;

GRANT ALL ON tiendaOnlineDB.* TO 'spq'@'localhost';
GRANT ALL ON tiendaOnlineDB.* TO 'opled'@'localhost';


CREATE TABLE Usuarios (     
    dni INT PRIMARY KEY AUTO_INCREMENT,        
    contrasena VARCHAR(255),     
    nombre VARCHAR(255), 
    correo VARCHAR(255)
);