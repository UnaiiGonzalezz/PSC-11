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
    id_Plato INT PRIMARY KEY AUTO_INCREMENT,        
    nombre VARCHAR(255),  
    descripcion VARCHAR(500),
    precio DECIMAL(10,2),
    tamano VARCHAR(255),
    categoria VARCHAR(255) 
);

CREATE TABLE Pedidos (     
    ID_ped INT PRIMARY KEY AUTO_INCREMENT, 
    plato INT,       
    FOREIGN KEY (plato) REFERENCES Platos(id_Plato)   
);

CREATE TABLE Usuarios (     
    dni CHAR(9) NOT NULL PRIMARY KEY,        
    contrasena VARCHAR(255),     
    nombre VARCHAR(255), 
    correo VARCHAR(255),
    pedido INT,
    FOREIGN KEY (pedido) REFERENCES Pedidos(id),
    tipo VARCHAR(255)
);

-- Insertar datos en Platos
INSERT INTO Platos (nombre, descripcion, precio, tamano, categoria) VALUES 
('Arroz a la cubana', 'arroz con tomate y huevo', 5.50, 'grande', 'primero'),
('Mermelada', 'mermelada en tostada', 3.30, 'pequeño', 'postre');

-- Insertar datos en Pedidos
INSERT INTO Pedidos (plato) VALUES 
(1),
(2);

-- Insertar datos en Usuarios
INSERT INTO Usuarios (dni, contrasena, nombre, correo, pedido, tipo) VALUES 
('aaa111', 'a', 'iker', 'iker.cortajarena@opendeusto.es', 1, 'cliente'),
('aaa222', 'b', 'unai', 'unai.gonzalez@opendeusto.es', 2, 'administrador');