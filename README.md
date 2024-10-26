# Proyecto AWS con S3 en Java

Este proyecto proporciona una implementación de varias operaciones de administración de un bucket de Amazon S3 usando Java. Permite la creación de un bucket, subir y descargar archivos, configurar políticas de acceso y habilitar un sitio web estático, entre otras operaciones.

## Requisitos previos

- **Java 8** o superior.
- **Maven** (para la gestión de dependencias).
- **Credenciales de AWS**: Necesitas configurar tus credenciales de AWS para acceder a los servicios de S3. Puedes hacerlo a través del archivo `~/.aws/credentials` o estableciendo las variables de entorno correspondientes.
- **AWS SDK para Java** (se gestiona mediante Maven).

## Estructura del Proyecto

- **Main.java**: Contiene el menú principal desde el cual se pueden seleccionar las distintas operaciones a realizar en el bucket de S3.

## Funcionalidades

1. **Crear un Bucket**
   - Permite la creación de un bucket en Amazon S3 si el nombre proporcionado es válido y no existe otro bucket con el mismo nombre.
   
2. **Deshabilitar Bloqueo de Acceso Público**
   - Desactiva el bloqueo de acceso público del bucket para poder aplicar políticas de lectura global.

3. **Subir Archivo**
   - Permite cargar un archivo local en el bucket. Se debe proporcionar el nombre del objeto en S3 y la ruta del archivo local.

4. **Descargar Archivo**
   - Descarga un archivo del bucket al equipo local. Se requiere el nombre del archivo almacenado en S3.

5. **Permitir Lectura Pública del Bucket**
   - Aplica una política de acceso público al bucket, necesaria para habilitar el sitio web estático.

6. **Indicar Página de Índice y de Error**
   - Configura el archivo de índice (`index.html`) y una posible página de error para el sitio web estático en el bucket.

7. **Salir**
   - Finaliza el programa.

## Uso

Al ejecutar el programa, verás un menú con las opciones mencionadas anteriormente. Simplemente ingresa el número de la opción que deseas ejecutar y sigue las instrucciones en la terminal.

## Créditos

Este proyecto fue desarrollado como parte de una tarea académica. 

Autor: **Diego Sesmero Fernández**

