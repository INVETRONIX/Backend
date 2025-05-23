# Backend Invetronix

Backend para el sistema de gestión de inventario Invetronix.

## 🚀 Características

- Sistema de autenticación y autorización con JWT
- Gestión de productos
- Sistema de compras
- Gestión de usuarios
- Integración con Gemini AI
- Gestión de imágenes con Unsplash
- Documentación con Swagger

## 📋 Prerrequisitos

- Java 17 o superior
- Maven
- MariaDB
- Docker (opcional)


3. Configurar variables de entorno:
Crear un archivo `.env` en la raíz del proyecto con las siguientes variables:

    DB_URL=ruta base de datos
    DB_USER=usuario
    DB_PASS=contraseña
    JWT_SECRET=a0fcd1240ef32a13f09788e81befb94964448d1559053f169234dfcf8955e2ea
    GEMINI_API_KEY=api key de gemini


## 📁 Estructura del Proyecto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── invetronix/
│   │   │           └── backend/
│   │   │               ├── APIlogin/        # Autenticación y autorización
│   │   │               ├── APIproductos/    # Gestión de productos
│   │   │               ├── APIusuarios/     # Gestión de usuarios
│   │   │               ├── APIcompras/      # Sistema de compras
│   │   │               ├── APIGemini/       # Integración con Gemini AI
│   │   │               ├── APIimages/       # Gestión de imágenes
│   │   │               └── core/            # Componentes core
│   │   └── resources/
│   └── test/
└── pom.xml
```

## 🔐 Roles y Permisos

### Administrador
- Acceso total al sistema
- Gestión de usuarios
- Gestión de productos
- Gestión de compras

### Cliente
- Realizar compras
- Ver productos

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot
- MariaDB
- Maven
- JWT
- Swagger
- Retrofit
- Docker
- Azure
- Render.com
- Git y GitHub (GitFlow)
- Patrón Repository

## 📝 Notas Adicionales

- La aplicación requiere una conexión a internet para la integración con Gemini AI y Unsplash
- Se recomienda cambiar las credenciales del .env después de la instalación
- El backend está desplegado en Render.com
- La documentación de la API está disponible en `/swagger-ui.html`

## 🔍 Endpoints Principales

### Autenticación
- POST `/api/auth/login` - Iniciar sesión
- POST `/api/auth/register` - Registrar usuario

### Productos
- GET `/api/productos` - Listar productos
- POST `/api/productos` - Crear producto
- PUT `/api/productos/{id}` - Actualizar producto
- DELETE `/api/productos/{id}` - Eliminar producto

### Compras
- GET `/api/compras` - Listar compras
- POST `/api/compras` - Crear compra
- PUT `/api/compras/{id}` - Actualizar compra
- DELETE `/api/compras/{id}` - Eliminar compra

## 📚 Documentación

La documentación completa de la API está disponible en:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`
