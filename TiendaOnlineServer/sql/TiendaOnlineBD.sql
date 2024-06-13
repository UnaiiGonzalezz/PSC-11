DROP SCHEMA IF EXISTS TiendaOnlineDB;
DROP USER IF EXISTS 'spq'@'localhost';
DROP USER IF EXISTS 'opled'@'localhost';


CREATE SCHEMA TiendaOnlineDB;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';
CREATE USER IF NOT EXISTS 'opled'@'localhost' IDENTIFIED BY 'opled';

USE TiendaOnlineDB;

GRANT ALL ON TiendaOnlineDB.* TO 'spq'@'localhost';
GRANT ALL ON TiendaOnlineDB.* TO 'opled'@'localhost';


CREATE TABLE Usuarios (     
    dni CHAR(9) NOT NULL PRIMARY KEY,        
    contrasena VARCHAR(255),     
    nombre VARCHAR(255), 
    correo VARCHAR(255),
    tipo VARCHAR(255),
    pedido INT,
    FOREIGN KEY (pedido) REFERENCES Pedidos(id) 
);

CREATE TABLE Pedidos (     
    id INT PRIMARY KEY AUTO_INCREMENT, 
    plato INT,       
    FOREIGN KEY (plato) REFERENCES Platos(id)   
);

CREATE TABLE Platos (     
    id INT PRIMARY KEY AUTO_INCREMENT,        
    nombre VARCHAR(255),  
    descr VARCHAR(500), 
    categoria VARCHAR(255), 
    precio DECIMAL,
    tam VARCHAR(255)  
);

INSERT INTO Usuarios (dni, contrasena, nombre, correo, tipo, pedido) VALUES 
('aaa111', 'a', 'iker', 'iker.cortajarena@opendeusto.es', 'cliente', 1),
('aaa222', 'b', 'unai', 'unai.gonzalez@opendeusto.es', 'administrador', 1);

INSERT INTO Pedidos (id, plato) VALUES 
(1, 1),
(2, 2);

INSERT INTO Usuarios (id, nombre, descr, categoria, precio, tam) VALUES 
(1, 'Arroz a la cubana', 'arroz con tomate y huevo', 'primero', 5.5, 'grande'),
(2, 'Mermelada', 'mermelada en tostada', 'postre', 3.3, 'pequeño');

