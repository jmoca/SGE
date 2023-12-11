
-- Crear tabla de productos
CREATE TABLE Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    cantidadEnStock INT NOT NULL
);
-- Crear tabla de clientes
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    contacto VARCHAR(50),
    historialCompras TEXT
);
-- Crear tabla de proveedores
CREATE TABLE Proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    contacto VARCHAR(50),
    productosSuministrados TEXT,
    productoId INT,
    FOREIGN KEY (productoId) REFERENCES	productos(id)
);
-- Crear tabla de pedidos a proveedores
CREATE TABLE Pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedorId INT,
    productoId INT,
    FOREIGN KEY (productoId) REFERENCES	productos(id),
    FOREIGN KEY (proveedorId) REFERENCES Proveedores(id)
);

CREATE TABLE Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    clienteId INT,
    facturaId INT,
    proveedorId INT,
    productoId INT,
    pedidoId INT,
    FOREIGN KEY (clienteId) REFERENCES Clientes(id),
    FOREIGN KEY (proveedorId) REFERENCES Proveedores(id),
    FOREIGN KEY (pedidoId) REFERENCES Pedidos(id)
    
    
);





