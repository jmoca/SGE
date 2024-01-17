USE sge_tareas;

CREATE TABLE IF NOT EXISTS Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
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
