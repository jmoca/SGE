# -*- coding: utf-8 -*-
import sys
import mysql.connector
import datetime

sys.stdout.reconfigure(encoding='utf-8')


def conectar_bd():
    # Configuración de la conexión a la base de datos
    config = {
        'user': 'root',
        'password': 'root',
        'host': 'localhost',
        'database': 'SGE_Tareas',
        'raise_on_warnings': True
    }

    try:
        # Crear la conexión
        conexion = mysql.connector.connect(**config)

        # Comprobar si la conexión se estableció correctamente
        if conexion.is_connected():
            print("Conexión exitosa a la base de datos")
            return conexion
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        return None


def cerrar_conexion(conexion):
    # Cerrar la conexión a la base de datos
    if conexion.is_connected():
        conexion.close()
        print("Conexión cerrada")


def menu_principal():
    print("\n===== Menú Principal =====")
    print("1. Iniciar sesión")
    print("2. Registrarse")
    print("3. Salir")
    opcion = input("Seleccione una opción: ")
    if opcion == '1':
        iniciar_sesion()
    elif opcion == '2':
        registrar_usuario()
    elif opcion == '3':
        exit()
    else:
        print("Opción no válida. Intente de nuevo.")
        menu_principal()



def iniciar_sesion():
    # Solicitar al usuario que ingrese su nombre de usuario y contraseña
    usuario_input = input("Ingrese su nombre de usuario: ")
    contrasena_input = input("Ingrese su contraseña: ")

    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para buscar el usuario y la contraseña en la base de datos
            consulta = "SELECT usuario, contrasena FROM Usuarios WHERE usuario = %s AND contrasena = %s"
            parametros = (usuario_input, contrasena_input)

            # Ejecutar la consulta
            cursor.execute(consulta, parametros)

            # Obtener el resultado de la consulta
            resultado = cursor.fetchone()

            if resultado:
                print("Sesión iniciada")
            else:
                print("Usuario o contraseña incorrectos")

        except mysql.connector.Error as err:
            print(f"Error: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)
    pass


def registrar_usuario():
    # Implementar la lógica de registro de usuarios
    pass


def menu_tareas(usuario):
    while True:
        print("\n===== Menú de Tareas =====")
        print("1. Agregar tarea")
        print("2. Asociar tarea a usuario")
        print("3. Ver lista de tareas")
        print("4. Marcar tarea como completada")
        print("5. Filtrar tareas por prioridad")
        print("6. Recordatorio de tareas")
        print("7. Imprimir listado de tareas completadas y pendientes")
        print("8. Volver al menú principal")

        opcion = input("Seleccione una opción: ")
        if opcion == '1':
            agregar_tarea(usuario)
        elif opcion == '2':
            asociar_tarea(usuario)
        elif opcion == '3':
            ver_lista_tareas()
        elif opcion == '4':
            marcar_tarea_completada()
        elif opcion == '5':
            filtrar_por_prioridad()
        elif opcion == '6':
            recordatorio_tareas(usuario)
        elif opcion == '7':
            imprimir_listados()
        elif opcion == '8':
            break
        else:
            print("Opción no válida. Intente de nuevo.")


def agregar_tarea(usuario):
    # Implementar la lógica para agregar una tarea
    pass


def asociar_tarea(usuario):
    # Implementar la lógica para asociar una tarea a un usuario
    pass


def ver_lista_tareas():
    # Implementar la lógica para mostrar la lista de tareas
    pass


def marcar_tarea_completada():
    # Implementar la lógica para marcar una tarea como completada
    pass


def filtrar_por_prioridad():
    # Implementar la lógica para filtrar tareas por prioridad
    pass


def recordatorio_tareas(usuario):
    # Implementar la lógica para mostrar un recordatorio de tareas pendientes para el día actual
    pass


def imprimir_listados():
    # Implementar la lógica para imprimir listado de tareas completadas y pendientes
    pass


while True:
    menu_principal()
