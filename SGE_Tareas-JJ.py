import sys
import mysql.connector
from datetime import datetime as dt, datetime

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
            return conexion
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        return None


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
            fecha_vencimiento = input("Ingrese la fecha de vencimiento (formato DD-MM-YYYY): ")

            # Validar y convertir la fecha de vencimiento a formato datetime
            try:
                fecha_vencimiento = dt.strptime(fecha_vencimiento, "%d-%m-%Y").date()

            except ValueError:
                print("Formato de fecha incorrecto. Utilice el formato DD-MM-YYYY.")
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


def asociar_tarea(usuario):
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Mostrar la lista de tareas al usuario
            ver_lista_tareas()

            # Solicitar al usuario el ID de la tarea que desea asociar
            id_tarea = input("Ingrese el ID de la tarea que desea asociar: ")

            # Verificar si el ID de la tarea es válido
            if not id_tarea.isdigit():
                print("ID de tarea no válido.")
                return

            id_tarea = int(id_tarea)

            # Consultar la tarea seleccionada
            consulta_tarea = "SELECT nombre FROM Tareas WHERE id = %s"
            cursor.execute(consulta_tarea, (id_tarea,))
            tarea = cursor.fetchone()

            if not tarea:
                print("No se encontró la tarea con el ID proporcionado.")
                return

            nombre_tarea = tarea[0]

            # Solicitar al usuario el nombre de usuario al que desea asociar la tarea
            nombre_usuario_asociado = input("Ingrese el nombre de usuario al que desea asociar la tarea: ")

            # Verificar si el usuario existe en la base de datos
            consulta_usuario = "SELECT id FROM Usuarios WHERE nombre = %s"
            cursor.execute(consulta_usuario, (nombre_usuario_asociado,))
            usuario_existente = cursor.fetchone()

            if not usuario_existente:
                print(f"No se encontró el usuario '{nombre_usuario_asociado}'.")
                return

            # Consulta SQL para asociar la tarea a un usuario
            consulta_asociar = "INSERT INTO Asignaciones (id_usuario, id_tarea) VALUES (%s, %s)"
            parametros_asociar = (usuario_existente[0], id_tarea)

            # Ejecutar la consulta
            cursor.execute(consulta_asociar, parametros_asociar)

            # Confirmar la transacción
            conexion.commit()

            print(f"Tarea '{nombre_tarea}' asociada al usuario '{nombre_usuario_asociado}' exitosamente.")

        except mysql.connector.Error as err:
            # Revertir la transacción en caso de error
            print(f"Error al asociar tarea: {err}")
            conexion.rollback()

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()


def ver_lista_tareas():
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para obtener la lista completa de tareas con detalles
            consulta = "SELECT t.id, t.nombre, t.fecha_vencimiento, t.prioridad, t.completada, u.nombre AS usuario_asociado \
                            FROM Tareas t \
                            LEFT JOIN Asignaciones a ON t.id = a.id_tarea \
                            LEFT JOIN Usuarios u ON a.id_usuario = u.id"
            cursor.execute(consulta)

            # Obtener el resultado de la consulta
            tareas = cursor.fetchall()

            # Mostrar la lista de tareas al usuario
            if not tareas:
                print("No hay tareas disponibles.")
            else:
                print("Lista completa de tareas:")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad | Completada | Usuario Asociado")
                print("-" * 95)
                for tarea in tareas:
                    completada = "Sí" if tarea[4] else "No"
                    usuario_asociado = tarea[5] if tarea[5] else "No asociada"
                    print(f"{tarea[0]}   |    {tarea[1]}    |   {tarea[2]}   |   {tarea[3]}   |   {completada}   |   {usuario_asociado}")

        except mysql.connector.Error as err:
            print(f"Error al obtener la lista de tareas: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()


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


def filtrar_por_prioridad():
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Solicitar al usuario que ingrese la prioridad deseada
            prioridad_deseada = input("Ingrese la prioridad deseada (Baja/Media/Alta): ")

            # Validar la prioridad ingresada
            if prioridad_deseada not in ["Baja", "Media", "Alta"]:
                print("Prioridad no válida. Utilice Baja, Media o Alta.")
                return

            # Consulta SQL para obtener las tareas filtradas por prioridad
            consulta = "SELECT id, nombre, fecha_vencimiento, prioridad FROM Tareas WHERE prioridad = %s"
            cursor.execute(consulta, (prioridad_deseada,))

            # Obtener el resultado de la consulta
            tareas_filtradas = cursor.fetchall()

            # Mostrar las tareas filtradas al usuario
            if not tareas_filtradas:
                print(f"No hay tareas con prioridad '{prioridad_deseada}'.")
            else:
                print(f"Tareas con prioridad '{prioridad_deseada}':")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad")
                print("-" * 50)
                for tarea in tareas_filtradas:
                    print(f"{tarea[0]} | {tarea[1]} | {tarea[2]} | {tarea[3]}")

        except mysql.connector.Error as err:
            print(f"Error al filtrar tareas por prioridad: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()


def recordatorio_tareas(usuario):
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Obtener la fecha actual
            fecha_actual = datetime.date.today()

            # Consulta SQL para obtener las tareas pendientes para el día actual
            consulta = "SELECT id, nombre, fecha_vencimiento, prioridad FROM Tareas WHERE DATE(fecha_vencimiento) = %s AND completada = FALSE"
            cursor.execute(consulta, (fecha_actual,))

            # Obtener el resultado de la consulta
            tareas_pendientes = cursor.fetchall()

            # Mostrar las tareas pendientes al usuario
            if not tareas_pendientes:
                print("No hay tareas pendientes para el día de hoy.")
            else:
                print("Tareas pendientes para el día de hoy:")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad")
                print("-" * 50)
                for tarea in tareas_pendientes:
                    print(f"{tarea[0]} | {tarea[1]} | {tarea[2]} | {tarea[3]}")

        except mysql.connector.Error as err:
            print(f"Error al obtener el recordatorio de tareas pendientes: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()


def imprimir_listados():
    # Conectar a la base de datos
    conexion = conectar_bd()

    if conexion:
        try:
            # Crear un cursor para ejecutar consultas SQL
            cursor = conexion.cursor()

            # Consulta SQL para obtener las tareas completadas
            consulta_completadas = "SELECT id, nombre, fecha_vencimiento, prioridad FROM Tareas WHERE completada = TRUE"
            cursor.execute(consulta_completadas)

            # Obtener el resultado de la consulta
            tareas_completadas = cursor.fetchall()

            # Mostrar las tareas completadas al usuario
            if not tareas_completadas:
                print("No hay tareas completadas.")
            else:
                print("Tareas Completadas:")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad")
                print("-" * 50)
                for tarea in tareas_completadas:
                    print(f"{tarea[0]} | {tarea[1]} | {tarea[2]} | {tarea[3]}")

            # Consulta SQL para obtener las tareas pendientes
            consulta_pendientes = "SELECT id, nombre, fecha_vencimiento, prioridad FROM Tareas WHERE completada = FALSE"
            cursor.execute(consulta_pendientes)

            # Obtener el resultado de la consulta
            tareas_pendientes = cursor.fetchall()

            # Mostrar las tareas pendientes al usuario
            if not tareas_pendientes:
                print("No hay tareas pendientes.")
            else:
                print("\nTareas Pendientes:")
                print("ID | Nombre de Tarea | Fecha de Vencimiento | Prioridad")
                print("-" * 50)
                for tarea in tareas_pendientes:
                    print(f"{tarea[0]} | {tarea[1]} | {tarea[2]} | {tarea[3]}")

        except mysql.connector.Error as err:
            print(f"Error al imprimir listados de tareas: {err}")

        finally:
            # Cerrar el cursor y la conexión
            cursor.close()


while True:
    menu_principal()
