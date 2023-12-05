-- Crear tabla de Clientes
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT,
    contacto VARCHAR(50),
    historial_compras TEXT
);

-- Crear tabla de Productos
CREATE TABLE Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    cantidad_en_stock INT NOT NULL
);

-- Crear tabla de Ventas
CREATE TABLE Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    cliente_id INT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

-- Crear tabla de Facturas
CREATE TABLE Facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    FOREIGN KEY (venta_id) REFERENCES Ventas(id)
);

-- Crear tabla de ProductosVendidos
sgeCREATE TABLE ProductosVendidos (
    venta_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    PRIMARY KEY (venta_id, producto_id),
    FOREIGN KEY (venta_id) REFERENCES Ventas(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

--

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
    productos_suministrados TEXT
);
-- Crear tabla de pedidos a proveedores
CREATE TABLE Pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);
-- me he quedado por aqui
-- Crear tabla de ventas

CREATE TABLE Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    cliente_id INT,
    factura_id INT,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id),
    FOREIGN KEY (factura_id) REFERENCES Facturas(id)
);


-- Crear tabla de productos vendidos en una venta
CREATE TABLE ProductosVendidos (
    venta_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    PRIMARY KEY (venta_id, producto_id),
    FOREIGN KEY (venta_id) REFERENCES Ventas(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);







