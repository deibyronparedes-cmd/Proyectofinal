SISTEMA DE LENGUAS DE SEÑAS COLOMBIANAS 

Aplicación de escritorio desarrollada en Java con Swing que permite
visualizar y traducir la lengua de señas colombiana a texto.
Ademas de eso ofrece un contenido de palabras explicadas en lenguaje de señas y numeros.

DESCRIPCION
El sistema permite al usuario explorar señas del alfabeto (A-Z),
números (0-9), palabras comunes y frases básicas mediante una
interfaz gráfica intuitiva. Cada seña se muestra con su imagen
correspondiente y descripción detallada.

TECNOLOGIAS USADAS 
- Java Swing (interfaz gráfica)
- Maven (gestión del proyecto)
- MySQL (persistencia del historial)
- JUnit 5 (pruebas unitarias)
- Mockito (pruebas con mocks)
- PlantUML (diagrama de clases)

 ESTRUCTURA DEL PROYECTO
src/
├── main/java/
│   ├── model/          Clases abstractas, interfaces y entidades
│   ├── repository/      Acceso a datos y conexión MySQL
│   ├── controller/      Lógica de negocio
│   └── view/            Interfaz gráfica con Java Swing
├── main/resources/
│   └── imagenes/        Imágenes de las señas
└── test/java/           Pruebas unitarias y de integración

ARQUITECTURA

El proyecto implementa el patrón MVC(Modelo-Vista-Controlador):

- Modelo: Clase abstracta `Seña` con subclases `LetraSeña`,
  `NumeroSeña`, `PalabraSeña` y `FraseSeña`
- Vista: Paneles Swing (`GaleriaPanel`, `TraductorPanel`,
  `HistorialPanel`, `DetalleSeñaPanel`)
- Controlador: `SeñaController` como mediador entre modelo y vista

PRINCIPIOS DE PROGRAMACION ORIENTADA A OBJECTOS
- Herencia: `LetraSeña`, `NumeroSeña`, `PalabraSeña`, `FraseSeña`
  extienden la clase abstracta `Seña`
- Polimorfismo: cada subclase implementa `getDescripcion()` y
  `getTextoRepresentacion()` de forma diferente
- Abstracción: clase abstracta `Seña` e interfaz `ISeñaRepositorio`
- Encapsulamiento: atributos privados con getters y setters
- Sobrecarga: métodos `traducir()`, `buscar()`, `registrar()` y
  `obtenerHistorial()` con múltiples firmas

REQUISITOS
- JDK 21
- Maven 3.x
- MySQL 8.x (XAMPP recomendado)

CONFIGURACION DE LA BASE DE DATOS
1. Iniciar MySQL desde XAMPP
2. Crear la base de datos `nueva`
3. Ejecutar el siguiente SQL:
sql
CREATE TABLE historial_traducciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto_original VARCHAR(500) NOT NULL,
    cantidad_senas INT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP
);

EJECUCION
bash
mvn clean compile exec:java
O desde NetBeans: clic derecho en el proyecto → `Run`

PRUEBAS 
mvn test
Los resultados fueron:
27 pruebas pasando, 0 fallos, 0 errores.

DOCUMENTACION
- JavaDoc generado en `target/reports/apidocs/index.html`
- Diagrama de clases UML en `Diagrama de clases proyecto final.png`

**AUTORES**

DEIBY PAREDES RODRIGUEZ 
MATIAS ACEVEDO VALLE 