DROP TABLE empleado CASCADE;
DROP TABLE bus CASCADE;
DROP TABLE ruta CASCADE;
DROP TABLE programacion CASCADE;
DROP TABLE estacion CASCADE;
DROP TABLE ruta_estacion CASCADE;
DROP TABLE solicitud CASCADE;
DROP TABLE usuario CASCADE;
DROP TABLE tarjeta CASCADE;
DROP TABLE usuario_ingresa_estacion CASCADE;

CREATE TABLE empleado
(
id VARCHAR(50) PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
login VARCHAR(20) NOT NULL,
contrasena VARCHAR(30) NOT NULL,
eps VARCHAR(40) NOT NULL,
salario INT  NOT NULL,
licencia VARCHAR(50) NOT NULL,
genero CHAR(1) NOT NULL,
estado_civil VARCHAR(20) NOT NULL,
fecha_nac DATE NOT NULL,
cargo VARCHAR(30) NOT NULL,
estado VARCHAR(30) NOT NULL 
);

CREATE TABLE bus
(
matricula VARCHAR(30) PRIMARY KEY,
estado VARCHAR(30) NOT NULL,
año VARCHAR(10) NOT NULL,
fabricante VARCHAR(40) NOT NULL,
capacidad VARCHAR(30) NOT NULL,
cilindrinaje VARCHAR(40) NOT NULL,
chasis VARCHAR(30) NOT NULL,
tipo_bus VARCHAR(50) NOT NULL
);

CREATE TABLE ruta
(
nombre_ruta VARCHAR(40) PRIMARY KEY,
descripcion VARCHAR(30) NOT NULL,
estado VARCHAR(40) NOT NULL
);

CREATE TABLE programacion
(
hora_inicio VARCHAR(10) NOT NULL,
hora_fin VARCHAR(10) NOT NULL,
fecha DATE NOT NULL, 
id_empleado VARCHAR(50)
);

CREATE TABLE estacion
(
nombre_estacion VARCHAR(40) PRIMARY KEY,
ubicacion VARCHAR(50) NOT NULL,
estado VARCHAR(30) NOT NULL,
id_empleado VARCHAR(50) NOT NULL,
FOREIGN KEY (id_empleado) REFERENCES empleado(id)
);

CREATE TABLE ruta_estacion
(
nombre_ruta VARCHAR(40) NOT NULL,
nombre_estacion VARCHAR(40) NOT NULL,
FOREIGN KEY (nombre_ruta) REFERENCES ruta(nombre_ruta),
FOREIGN KEY (nombre_estacion) REFERENCES estacion(nombre_estacion)
);

CREATE TABLE tarjeta
(
pin INT PRIMARY KEY NOT NULL,
costo VARCHAR(20) NOT NULL,
tipo VARCHAR(30) NOT NULL,
estado VARCHAR(30) NOT NULL,
pasajes VARCHAR (10) NOT NULL,
nombre_estacion VARCHAR (40) NOT NULL,
FOREIGN KEY (nombre_estacion) REFERENCES estacion(nombre_estacion)
);
CREATE TABLE usuario
(
id_usuario INT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
ciudad VARCHAR(40) NOT NULL,
direccion VARCHAR(40) NOT NULL,
telefono VARCHAR (30) NOT NULL,
pin_tarjeta INT NOT NULL,
FOREIGN KEY (pin_tarjeta) REFERENCES tarjeta(pin)
);

CREATE TABLE solicitud
(
ticket VARCHAR(40) PRIMARY KEY,
motivo VARCHAR(50) NOT NULL,
descripcion VARCHAR(120) NOT NULL,
hora VARCHAR (20) NOT NULL,
fecha DATE NOT NULL,
id_usuario INT NOT NULL,
nombre_estacion VARCHAR(30) NOT NULL,
FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
FOREIGN KEY (nombre_estacion) REFERENCES estacion(nombre_estacion)
);



CREATE TABLE usuario_ingresa_estacion
(
nombre_estacion VARCHAR(50) NOT NULL,
id_usuario INT NOT NULL,
fecha DATE NOT NULL,
hora VARCHAR(10) NOT NULL,
FOREIGN KEY (nombre_estacion) REFERENCES estacion(nombre_estacion),
FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

SELECT* FROM EMPLEADO;