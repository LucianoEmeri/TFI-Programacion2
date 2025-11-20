# 🏢 Sistema de Gestión de Empresas y Domicilios Fiscales

**Trabajo Práctico Integrador - Programación 2**  
**Tecnicatura Universitaria en Programación - UTN**

---

## 👥 Integrantes del Grupo

- **Luciano Emerí - emeriluciano@gmail.com** 
- **Marcelo Gomez Armoa - marcelorodriog@gmail.com**
- **Sebastián Gossos - winigossos@gmail.com** 
- **Matias D’ Agostino - codewtato@gmail.com**
- **Facundo Cufré - facundocufre91@gmail.com**
  
---

## 📋 Descripción del Proyecto

Sistema de gestión desarrollado en **Java** que permite administrar empresas y sus domicilios fiscales mediante operaciones CRUD (Crear, Leer, Actualizar, Eliminar). El proyecto implementa una **relación 1→1 unidireccional** entre las entidades `Empresa` y `DomicilioFiscal`, utilizando el **patrón DAO** y **manejo de transacciones** con JDBC.

### ✨ Características principales:

- ✅ CRUD completo de Empresas y Domicilios Fiscales
- ✅ Relación 1→1 unidireccional (Empresa → DomicilioFiscal)
- ✅ Transacciones con commit/rollback
- ✅ Baja lógica (sin eliminación física de datos)
- ✅ Búsqueda por CUIT (campo único)
- ✅ Validaciones de datos (CUIT, email, campos obligatorios)
- ✅ Menú de consola interactivo
- ✅ Arquitectura en capas (Entities, DAO, Service, Main)

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21 (JDK 21+)
- **Base de Datos:** MySQL 8.0
- **Driver JDBC:** MySQL Connector/J 9.5.0
- **IDE:** NetBeans 24 / IntelliJ IDEA / Eclipse
- **Control de versiones:** Git + GitHub

---

## 📦 Estructura del Proyecto
```
TPI-Programacion2/
├── src/
│   ├── config/
│   │   └── DatabaseConnection.java      # Conexión a MySQL
│   ├── entities/
│   │   ├── Empresa.java                 # Entidad Empresa
│   │   └── DomicilioFiscal.java         # Entidad Domicilio
│   ├── dao/
│   │   ├── GenericDao.java              # Interfaz genérica
│   │   ├── EmpresaDao.java              # DAO de Empresa
│   │   └── DomicilioFiscalDao.java      # DAO de Domicilio
│   ├── service/
│   │   ├── GenericService.java          # Interfaz genérica
│   │   ├── EmpresaService.java          # Lógica de negocio + transacciones
│   │   └── DomicilioFiscalService.java  # Lógica de domicilios
│   └── main/
│       ├── Main.java                    # Punto de entrada
│       └── AppMenu.java                 # Menú interactivo
├── sql/
│   ├── crear_base.sql                   # Script de creación de BD
│   └── datos_prueba.sql                 # Datos de prueba
├── docs/
│   ├── informe.pdf                      # Informe del proyecto
│   └── UML.png                          # Diagrama UML de clases
└── README.md                            # Este archivo
```

---

## 🗄️ Modelo de Base de Datos

### Diagrama Entidad-Relación
```
┌─────────────────────┐           ┌──────────────────────┐
│  domicilio_fiscal   │           │      empresa         │
├─────────────────────┤           ├──────────────────────┤
│ id (PK)             │◄──────────│ domicilio_fiscal_id  │
│ eliminado           │   1    1  │ (FK, UNIQUE)         │
│ calle               │           ├──────────────────────┤
│ numero              │           │ id (PK)              │
│ ciudad              │           │ eliminado            │
│ provincia           │           │ razon_social         │
│ codigo_postal       │           │ cuit (UNIQUE)        │
│ pais                │           │ actividad_principal  │
└─────────────────────┘           │ email                │
                                  └──────────────────────┘
```

**Relación:** Una Empresa tiene UN DomicilioFiscal (1→1 unidireccional).

---

## 🚀 Instalación y Configuración

### Requisitos previos

- **Java JDK 21** o superior instalado
- **MySQL 8.0** o superior instalado y corriendo
- **Git** instalado (para clonar el repositorio)

### Paso 1: Clonar el repositorio
```bash
git clone https://github.com/[tu-usuario]/TPI-Programacion2.git
cd TPI-Programacion2
```

### Paso 2: Crear la base de datos

Ejecutar los scripts SQL en orden:

1. **Crear la base y las tablas:**
```bash
   mysql -u root -p < sql/crear_base.sql
```

2. **Insertar datos de prueba:**
```bash
   mysql -u root -p < sql/datos_prueba.sql
```

   O desde MySQL Workbench:
   - Abrir `crear_base.sql` → Ejecutar
   - Abrir `datos_prueba.sql` → Ejecutar

### Paso 3: Configurar credenciales de MySQL

Editar el archivo `src/config/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/empresas_db";
private static final String USER = "root";          // ← Cambiar si es necesario
private static final String PASSWORD = "tu_password"; // ← Poner tu contraseña
```

### Paso 4: Compilar y ejecutar

**Opción A - Desde NetBeans/IntelliJ/Eclipse:**
1. Abrir el proyecto en el IDE
2. Agregar el driver MySQL Connector/J a las librerías
3. Ejecutar `Main.java`

**Opción B - Desde línea de comandos:**
```bash
# Compilar
javac -cp ".:mysql-connector-j-9.5.0.jar" -d bin src/**/*.java

# Ejecutar
java -cp "bin:mysql-connector-j-9.5.0.jar" main.Main
```

---

## 📖 Guía de Uso

### Menú Principal

Al ejecutar el programa, verás el siguiente menú:
```
╔════════════════════════════════════════╗
║         MENÚ PRINCIPAL                 ║
╚════════════════════════════════════════╝
┌────────────────────────────────────────┐
│  EMPRESAS                              │
├────────────────────────────────────────┤
│  1. Crear nueva empresa                │
│  2. Buscar empresa por ID              │
│  3. Buscar empresa por CUIT            │
│  4. Listar todas las empresas          │
│  5. Actualizar empresa                 │
│  6. Eliminar empresa (baja lógica)     │
├────────────────────────────────────────┤
│  DOMICILIOS FISCALES                   │
├────────────────────────────────────────┤
│  7. Listar todos los domicilios        │
│  8. Buscar domicilio por ID            │
├────────────────────────────────────────┤
│  9. Salir                              │
└────────────────────────────────────────┘
```

### Ejemplos de uso

#### Crear una empresa:
```
Opción: 1
Razón Social: PANADERIA LA ESQUINA SRL
CUIT: 30-66778899-4
Actividad Principal: Panadería
Email: contacto@laesquina.com.ar

--- Domicilio Fiscal ---
Calle: Libertad
Número: 123
Ciudad: Paraná
Provincia: Entre Ríos
Código Postal: 3100
País: Argentina

✓ Empresa creada exitosamente con ID: 6
```

#### Buscar por CUIT:
```
Opción: 3
Ingrese CUIT: 30-12345678-9

ID: 1
Razón Social: MOLINO SAN JOSE SRL
CUIT: 30-12345678-9
Actividad: Industria Alimenticia
Email: info@molinosj.com.ar
Domicilio Fiscal: San Martin 450, Paraná, Entre Ríos (Argentina)
```

---

## 🔄 Transacciones y Rollback

El sistema implementa **transacciones ACID** para garantizar la integridad de los datos:

### Ejemplo de transacción exitosa:
```java
conn.setAutoCommit(false);
// 1. Crear domicilio fiscal
domicilioDao.crear(domicilio, conn);
// 2. Crear empresa con referencia al domicilio
empresaDao.crear(empresa, conn);
conn.commit(); // ✓ Todo OK → Guardar cambios
```

### Ejemplo de rollback:
Si intentas crear una empresa con un **CUIT duplicado**, el sistema ejecuta un rollback:
```
→ Domicilio fiscal creado (ID: 10)
✗ ERROR - Rollback realizado: Ya existe una empresa con ese CUIT: 30-12345678-9
```

**Resultado:** Ni el domicilio ni la empresa se guardan (ambos revertidos).

---

## 🧪 Pruebas Realizadas

### Casos de prueba implementados:

1. ✅ **Crear empresa con domicilio** → Transacción exitosa
2. ✅ **Crear empresa con CUIT duplicado** → Rollback correcto
3. ✅ **Buscar empresa por CUIT** → Retorna empresa correcta
4. ✅ **Actualizar datos de empresa** → Cambios persistidos
5. ✅ **Eliminar empresa (baja lógica)** → Campo `eliminado = true`
6. ✅ **Listar empresas** → Solo muestra no eliminadas
7. ✅ **Validación de formato CUIT** → Rechaza formatos inválidos
8. ✅ **Validación de email** → Rechaza emails inválidos

---

## 📹 Video Demostrativo

**🎥 [Ver video explicativo del proyecto](https://youtu.be/1os7mgmLZAw)**

En el video se muestra:
- Presentación de los 5 integrantes
- Demostración del CRUD completo
- Explicación del código por capas
- Demostración de rollback ante error

---

## 📚 Documentación Adicional

**[Informe completo](https://docs.google.com/document/d/1hdfPpRVQ505ln2NPSDFAT_6AHoV53xA0ASOPWFJQNeg/edit?usp=sharing)**
  
---

## 🔧 Arquitectura del Sistema

### Patrón de Capas
```
┌─────────────────────────────────────┐
│         CAPA DE PRESENTACIÓN        │  ← AppMenu (interacción usuario)
├─────────────────────────────────────┤
│         CAPA DE LÓGICA DE NEGOCIO   │  ← Services (validaciones + transacciones)
├─────────────────────────────────────┤
│         CAPA DE PERSISTENCIA        │  ← DAOs (acceso a BD con JDBC)
├─────────────────────────────────────┤
│         CAPA DE DATOS               │  ← Entities (POJOs)
├─────────────────────────────────────┤
│         BASE DE DATOS               │  ← MySQL
└─────────────────────────────────────┘
```

### Responsabilidades:

- **Entities:** Clases con atributos, getters/setters y toString()
- **DAO:** Operaciones CRUD con PreparedStatement
- **Service:** Validaciones, transacciones (commit/rollback)
- **AppMenu:** Interacción con el usuario vía consola

---

## ⚠️ Decisiones de Diseño

### 1. Relación 1→1 Unidireccional
- Solo `Empresa` conoce a `DomicilioFiscal`
- En BD: `domicilio_fiscal_id` con constraint `UNIQUE`

### 2. Baja Lógica
- No se eliminan registros físicamente (DELETE)
- Se marca campo `eliminado = true` (UPDATE)
- Ventaja: Mantiene histórico y evita pérdida de datos

### 3. Transacciones
- Misma conexión compartida entre DAOs
- Orden: crear domicilio → crear empresa
- Rollback ante cualquier error

### 4. Validaciones en Service
- CUIT: formato XX-XXXXXXXX-X
- Email: expresión regular
- Campos obligatorios verificados antes de insertar

---

## 🐛 Solución de Problemas

### Error: "Access denied for user"
**Causa:** Usuario o contraseña incorrectos  
**Solución:** Verificar credenciales en `DatabaseConnection.java`

### Error: "Unknown database 'empresas_db'"
**Causa:** Base de datos no creada  
**Solución:** Ejecutar `crear_base.sql`

### Error: "No suitable driver found"
**Causa:** Driver MySQL no agregado al proyecto  
**Solución:** Agregar `mysql-connector-j-9.5.0.jar` a las librerías

### Error: "Duplicate entry for key 'cuit'"
**Causa:** CUIT ya existe en la BD  
**Solución:** Usar otro CUIT o buscar la empresa existente

---

## 📝 Notas de Desarrollo

### Herramientas utilizadas:
- **IDE:** NetBeans 24
- **Gestor BD:** MySQL Workbench 8.0
- **Control de versiones:** Git + GitHub
- **Diagramas UML:** draw.io

### Fuentes consultadas:
- Documentación oficial de Java 21: https://docs.oracle.com/en/java/
- JDBC Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/
- MySQL Reference Manual: https://dev.mysql.com/doc/
- Apuntes de la cátedra Programación 2 - UTN

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos para la materia **Programación 2** de la **Tecnicatura Universitaria en Programación - UTN**.
