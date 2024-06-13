-- Eliminar esquema y usuarios si existen
DROP SCHEMA IF EXISTS TiendaOnlineDB;
DROP USER IF EXISTS 'spq'@'localhost';
DROP USER IF EXISTS 'opled'@'localhost';

-- Crear esquema y usuarios
CREATE SCHEMA TiendaOnlineDB;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';
CREATE USER IF NOT EXISTS 'opled'@'localhost' IDENTIFIED BY 'opled';

-- Otorgar permisos a los usuarios
GRANT ALL ON TiendaOnlineDB.* TO 'spq'@'localhost';
GRANT ALL ON TiendaOnlineDB.* TO 'opled'@'localhost';

-- Usar el esquema TiendaOnlineDB
USE TiendaOnlineDB;

-- Crear tablas en el orden correcto
CREATE TABLE Platos (     
    id INT PRIMARY KEY AUTO_INCREMENT,        
    nombre VARCHAR(255),  
    descr VARCHAR(500), 
    categoria VARCHAR(255), 
    precio DECIMAL(10,2),
    tam VARCHAR(255)  
);

CREATE TABLE Pedidos (     
    id INT PRIMARY KEY AUTO_INCREMENT, 
    plato INT,       
    FOREIGN KEY (plato) REFERENCES Platos(id)   
);

CREATE TABLE Usuarios (     
    dni CHAR(9) NOT NULL PRIMARY KEY,        
    contrasena VARCHAR(255),     
    nombre VARCHAR(255), 
    correo VARCHAR(255),
    tipo VARCHAR(255),
    pedido INT,
    FOREIGN KEY (pedido) REFERENCES Pedidos(id) 
);

-- Insertar datos en Platos
INSERT INTO Platos (nombre, descr, categoria, precio, tam) VALUES 
('Arroz a la cubana', 'arroz con tomate y huevo', 'primero', 5.50, 'grande'),
('Mermelada', 'mermelada en tostada', 'postre', 3.30, 'pequeño');

-- Insertar datos en Pedidos
INSERT INTO Pedidos (plato) VALUES 
(1),
(2);

-- Insertar datos en Usuarios
INSERT INTO Usuarios (dni, contrasena, nombre, correo, tipo, pedido) VALUES 
('aaa111', 'a', 'iker', 'iker.cortajarena@opendeusto.es', 'cliente', 1),
('aaa222', 'b', 'unai', 'unai.gonzalez@opendeusto.es', 'administrador', 2);

