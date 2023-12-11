
-- Crear tabla de productos
CREATE TABLE Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    cantidad_en_stock INT NOT NULL
);
-- Crear tabla de clientes
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    contacto VARCHAR(50),
    historial_compras TEXT
);
-- Crear tabla de proveedores
CREATE TABLE Proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    contacto VARCHAR(50),
    productos_suministrados TEXT,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES	productos(id)
);
-- Crear tabla de pedidos a proveedores
CREATE TABLE Pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id INT,
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES	productos(id),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);

CREATE TABLE Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    cliente_id INT,
    factura_id INT,
    proveedor_id INT,
    producto_id INT,
    pedido_id INT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id),
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(id)
    
    
);





