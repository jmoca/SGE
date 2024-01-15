-- Script SQL para crear las tablas del sistema de gestión de tareas

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(50) NOT NULL
);

-- Tabla de Tareas
CREATE TABLE IF NOT EXISTS Tareas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    prioridad ENUM('Baja', 'Media', 'Alta') NOT NULL,
    completada BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabla de Asignaciones de Tareas a Usuarios
CREATE TABLE IF NOT EXISTS Asignaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_tarea INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id),
    FOREIGN KEY (id_tarea) REFERENCES Tareas(id)
);

-- Puedes agregar otras tablas o modificar la estructura según tus necesidades

-- Asegúrate de ajustar la longitud de los campos VARCHAR y otros detalles según tus necesidades
