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
        'database': 'sge_tareas',
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
    usuario = input("Ingrese su nombre de usuario: ")
    contrasena = input("Ingrese su contraseña: ")

    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para buscar el usuario y la contraseña en la base de datos
            consulta = "SELECT nombre, contrasena FROM Usuarios WHERE nombre = %s AND contrasena = %s"
            parametros = (usuario, contrasena)

            # Ejecutar la consulta
            cursor.execute(consulta, parametros)

            # Obtener el resultado de la consulta
            resultado = cursor.fetchone()

            if resultado:
                print("Sesión iniciada")
                menu_tareas(usuario)
            else:
                print("Usuario o contraseña incorrectos")

        except mysql.connector.Error as err:
            print(f"Error: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)



def registrar_usuario():
    nuevo_usuario = input("Ingrese un nombre de usuario: ")
    nueva_contrasena = input("Ingrese una contraseña: ")

    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para insertar el nuevo usuario en la base de datos
            consulta = "INSERT INTO usuarios (nombre, contrasena) VALUES (%s, %s)"
            parametros = (nuevo_usuario, nueva_contrasena)

            # Ejecutar la consulta
            cursor.execute(consulta, parametros)

            # Confirmar la transacción
            conexion.commit()

            print("Usuario registrado exitosamente")

        except mysql.connector.Error as err:
            # Revertir la transacción en caso de error
            print(f"Error: {err}")
            conexion.rollback()

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)


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
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Solicitar al usuario los detalles de la nueva tarea
            nombre_tarea = input("Ingrese el nombre de la tarea: ")
            fecha_vencimiento = input("Ingrese la fecha de vencimiento (formato YYYY-MM-DD): ")

            # Validar y convertir la fecha de vencimiento a formato datetime
            try:
                fecha_vencimiento = datetime.strptime(fecha_vencimiento, "%Y-%m-%d").date()
            except ValueError:
                print("Formato de fecha incorrecto. Utilice el formato YYYY-MM-DD.")
                return

            # Solicitar la prioridad de la tarea
            prioridad = input("Ingrese la prioridad de la tarea (Baja/Media/Alta): ")

            # Validar la prioridad
            if prioridad not in ["Baja", "Media", "Alta"]:
                print("Prioridad no válida. Utilice Baja, Media o Alta.")
                return

            # Consulta SQL para insertar la nueva tarea en la base de datos
            consulta = "INSERT INTO Tareas (nombre, fecha_vencimiento, prioridad) VALUES (%s, %s, %s)"
            parametros = (nombre_tarea, fecha_vencimiento, prioridad)

            # Ejecutar la consulta
            cursor.execute(consulta, parametros)

            # Confirmar la transacción
            conexion.commit()

            print("Tarea agregada exitosamente")

        except mysql.connector.Error as err:
            # Revertir la transacción en caso de error
            print(f"Error al agregar tarea: {err}")
            conexion.rollback()

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)


def asociar_tarea(usuario):
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Solicitar al usuario los detalles de la nueva tarea
            nombre_tarea = input("Ingrese el nombre de la tarea: ")
            fecha_vencimiento = input("Ingrese la fecha de vencimiento (formato YYYY-MM-DD): ")

            # Validar y convertir la fecha de vencimiento a formato datetime
            try:
                fecha_vencimiento = datetime.strptime(fecha_vencimiento, "%Y-%m-%d").date()
            except ValueError:
                print("Formato de fecha incorrecto. Utilice el formato YYYY-MM-DD.")
                return

            # Solicitar la prioridad de la tarea
            prioridad = input("Ingrese la prioridad de la tarea (Baja/Media/Alta): ")

            # Validar la prioridad
            if prioridad not in ["Baja", "Media", "Alta"]:
                print("Prioridad no válida. Utilice Baja, Media o Alta.")
                return

            # Consulta SQL para insertar la nueva tarea en la base de datos
            consulta = "INSERT INTO Tareas (nombre, fecha_vencimiento, prioridad) VALUES (%s, %s, %s)"
            parametros = (nombre_tarea, fecha_vencimiento, prioridad)

            # Ejecutar la consulta
            cursor.execute(consulta, parametros)

            # Confirmar la transacción
            conexion.commit()

            print("Tarea agregada exitosamente")

        except mysql.connector.Error as err:
            # Revertir la transacción en caso de error
            print(f"Error al agregar tarea: {err}")
            conexion.rollback()

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)


def ver_lista_tareas():
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para obtener la lista completa de tareas con detalles
            consulta = "SELECT id, nombre, fecha_vencimiento, prioridad FROM Tareas"
            cursor.execute(consulta)

            # Obtener el resultado de la consulta
            tareas = cursor.fetchall()

            # Mostrar la lista de tareas al usuario
            if not tareas:
                print("No hay tareas disponibles.")
            else:
                print("Lista completa de tareas:")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad")
                print("-" * 50)
                for tarea in tareas:
                    print(f"{tarea[0]} | {tarea[1]} | {tarea[2]} | {tarea[3]}")

        except mysql.connector.Error as err:
            print(f"Error al obtener la lista de tareas: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)


def marcar_tarea_completada():
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Mostrar la lista de tareas disponibles para que el usuario elija cuál marcar
            ver_lista_tareas()

            # Solicitar al usuario el ID de la tarea que desea marcar como completada
            id_tarea = input("Ingrese el ID de la tarea que desea marcar como completada: ")

            # Verificar si el ID de la tarea es válido
            if not id_tarea.isdigit():
                print("ID de tarea no válido.")
                return

            # Consulta SQL para obtener la tarea seleccionada
            consulta_tarea = "SELECT nombre, completada FROM Tareas WHERE id = %s"
            cursor.execute(consulta_tarea, (int(id_tarea),))
            tarea_seleccionada = cursor.fetchone()

            if not tarea_seleccionada:
                print("No se encontró la tarea con el ID proporcionado.")
                return

            # Mostrar el estado actual de la tarea
            estado_actual = "completada" if tarea_seleccionada[1] else "no completada"
            print(f"La tarea '{tarea_seleccionada[0]}' está actualmente {estado_actual}")

            # Solicitar al usuario que marque la tarea como completada o no completada
            opcion = input("¿Desea marcar la tarea como completada? (1: Sí, 2: No): ")

            # Verificar la opción del usuario
            if opcion == "1":
                # Consulta SQL para marcar la tarea como completada
                consulta_completada = "UPDATE Tareas SET completada = TRUE WHERE id = %s"
            elif opcion == "2":
                # Consulta SQL para marcar la tarea como no completada
                consulta_completada = "UPDATE Tareas SET completada = FALSE WHERE id = %s"
            else:
                print("Opción no válida.")
                return

            # Ejecutar la consulta para marcar la tarea como completada o no completada
            cursor.execute(consulta_completada, (int(id_tarea),))

            # Confirmar la transacción
            conexion.commit()

            print("Tarea marcada exitosamente")

        except mysql.connector.Error as err:
            # Revertir la transacción en caso de error
            print(f"Error al marcar tarea como completada: {err}")
            conexion.rollback()

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()
            cerrar_conexion(conexion)


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
