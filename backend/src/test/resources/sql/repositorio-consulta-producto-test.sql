-- FABRICANTE
INSERT INTO fabricante (id_fabricante, identificador, nombre, telefono_contacto,
                        correo_electronico, calle, numero, ciudad, pais)
VALUES ('fab-t-001', 'FT1', 'Lab Test', NULL, NULL, NULL, NULL, NULL, NULL);

-- PRODUCTOS
INSERT INTO producto (id_producto, nombre_comercial, nombre_generico, presentacion,
                      dosificacion, unidad_medida, stock_minimo, stock_maximo,
                      activo, categoria, url_foto, id_fabricante)
VALUES
('prod-t-001', 'Paracetamol Test', 'Paracetamol', 'Tabletas', 500, 'mg', 10, 100,
 TRUE, 'ANALGESICOS_ANTIINFLAMATORIOS', 'url1', 'fab-t-001'),
('prod-t-002', 'Ibuprofeno Test', 'Ibuprofeno', 'Cápsulas', 400, 'mg', 5, 50,
 TRUE, 'ANALGESICOS_ANTIINFLAMATORIOS', 'url2', 'fab-t-001');

-- CODIGOS
INSERT INTO codigo (id_codigo, codigo_barra, tipo_codigo, activo, id_producto)
VALUES
('cod-t-001', '111', 'EAN13', TRUE, 'prod-t-001'),
('cod-t-002', '222', 'EAN13', TRUE, 'prod-t-001'),
('cod-t-003', '333', 'EAN13', TRUE, 'prod-t-002');

-- LOTES (stocks distintos para probar sumas)
INSERT INTO lote (id_lote, fecha_elaboracion, fecha_vencimiento, numero_lote,
                  estado, precio_unitario, limite_merma, porcentaje_oferta,
                  stock_inicial, stock_actual, stock_reservado,
                  id_codigo, id_guia_ingreso)
VALUES
('lot-t-001', '2024-01-01', '2026-01-01', 'L1', 'DISPONIBLE', 1000, 5, 0, 50, 40, 5, 'cod-t-001', NULL),
('lot-t-002', '2024-01-01', '2026-01-01', 'L2', 'DISPONIBLE', 1000, 5, 0, 50, 30, 10, 'cod-t-002', NULL),
('lot-t-003', '2024-01-01', '2026-01-01', 'L3', 'DISPONIBLE', 1000, 5, 0, 80, 70, 20, 'cod-t-003', NULL);

-- PRODUCTO SIN LOTES
INSERT INTO producto (id_producto, nombre_comercial, nombre_generico, presentacion,
                      dosificacion, unidad_medida, stock_minimo, stock_maximo,
                      activo, categoria, url_foto, id_fabricante)
VALUES
('prod-sin-lote', 'Producto sin Lotes', 'Generico sin lote', 'Tabletas',
 500, 'mg', 10, 100,
 TRUE, 'ANALGESICOS_ANTIINFLAMATORIOS', 'url-sin-lote', 'fab-t-001');