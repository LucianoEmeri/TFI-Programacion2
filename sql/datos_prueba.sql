USE empresas_db;

-- Desactivo el modo seguro temporalmente
SET SQL_SAFE_UPDATES = 0;

-- Limpio las tablas por si ya habia algo
DELETE FROM empresa;
DELETE FROM domicilio_fiscal;

-- Reseteo los IDs para que empiecen desde 1
ALTER TABLE empresa AUTO_INCREMENT = 1;
ALTER TABLE domicilio_fiscal AUTO_INCREMENT = 1;

-- Reactivo el modo seguro
SET SQL_SAFE_UPDATES = 1;

-- Inserto algunos domicilios de prueba
INSERT INTO domicilio_fiscal (calle, numero, ciudad, provincia, codigo_postal, pais) VALUES
('San Martin', 450, 'Paraná', 'Entre Ríos', '3100', 'Argentina'),
('Rivadavia', 1234, 'Concordia', 'Entre Ríos', '3200', 'Argentina'),
('Urquiza', 789, 'Gualeguaychú', 'Entre Ríos', '2820', 'Argentina'),
('Belgrano', 567, 'Colón', 'Entre Ríos', '3280', 'Argentina'),
('9 de Julio', 321, 'Gualeguay', 'Entre Ríos', '2840', 'Argentina');

-- Inserto empresas de ejemplo
INSERT INTO empresa (razon_social, cuit, actividad_principal, email, domicilio_fiscal_id) VALUES
('MOLINO SAN JOSE SRL', '30-12345678-9', 'Industria Alimenticia', 'info@molinosj.com.ar', 1),
('FRIGORIFICO EL LITORAL SA', '30-98765432-1', 'Frigorífico', 'contacto@frigolitoral.com', 2),
('TRANSPORTES DEL ESTE SRL', '30-55667788-3', 'Transporte', 'ventas@transeste.com.ar', 3),
('AGROSERVICIOS PARANA SA', '33-44556677-8', 'Agropecuaria', 'info@agroparana.com', 4),
('DISTRIBUIDORA ENTRERRIANA SRL', '30-11223344-5', 'Comercio', 'ventas@distentrerriana.com.ar', 5);

-- Verifico que se haya cargado todo bien
SELECT COUNT(*) AS 'Total Domicilios' FROM domicilio_fiscal;
SELECT COUNT(*) AS 'Total Empresas' FROM empresa;

SELECT 'Datos cargados exitosamente' AS Resultado;