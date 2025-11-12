-- Script para crear la base de datos
-- Por las dudas borro la base si ya existe
DROP DATABASE IF EXISTS empresas_db;

-- Creo la base nueva
CREATE DATABASE empresas_db;
USE empresas_db;

-- Tabla de domicilios fiscales
CREATE TABLE domicilio_fiscal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN DEFAULT 0,
    calle VARCHAR(100) NOT NULL,
    numero INT,
    ciudad VARCHAR(80) NOT NULL,
    provincia VARCHAR(80) NOT NULL,
    codigo_postal VARCHAR(10),
    pais VARCHAR(80) NOT NULL
);

-- Tabla de empresas
-- El domicilio_fiscal_id tiene que ser UNIQUE para la relacion 1 a 1
CREATE TABLE empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN DEFAULT 0,
    razon_social VARCHAR(120) NOT NULL,
    cuit VARCHAR(13) NOT NULL UNIQUE,
    actividad_principal VARCHAR(80),
    email VARCHAR(120),
    domicilio_fiscal_id BIGINT UNIQUE,
    FOREIGN KEY (domicilio_fiscal_id) REFERENCES domicilio_fiscal(id)
);

-- Esto es para que las busquedas sean mas rapidas
CREATE INDEX idx_cuit ON empresa(cuit);

SELECT 'Base de datos creada OK' AS Estado;