-- ============================================
-- ============== FABRICANTES ==================
-- ============================================

INSERT INTO fabricante (id_fabricante, identificador, nombre, telefono_contacto,
                        correo_electronico, calle, numero, ciudad, pais)
VALUES ('fab-001', 'F001', 'Laboratorio Chile', '+56 2 555 1111', 'contacto@labchile.cl', 'Av. Providencia', '1234',
        'Santiago', 'Chile'),
       ('fab-002', 'F002', 'Bayer', '+56 2 555 2222', 'info@bayer.cl', 'Av. Apoquindo', '4567', 'Santiago', 'Chile'),
       ('fab-003', 'F003', 'Pfizer', '+56 2 555 3333', 'support@pfizer.cl', 'Calle Las Industrias', '890', 'Santiago',
        'Chile'),
       ('fab-004', 'F004', 'Merck', '+56 2 555 4444', 'info@merck.cl', 'Av. Matta', '234', 'Santiago', 'Chile'),
       ('fab-005', 'F005', 'GSK', '+56 2 555 5555', 'contact@gsk.com', 'Av. Macul', '500', 'Santiago', 'Chile'),
       ('fab-006', 'F006', 'Roche', '+56 2 555 6666', 'info@roche.cl', 'Av. Las Condes', '8000', 'Santiago', 'Chile'),
       ('fab-007', 'F007', 'Saval', '+56 2 555 7777', 'contacto@saval.cl', 'Av. Grecia', '1200', 'Santiago', 'Chile'),
       ('fab-008', 'F008', 'Andrómaco', '+56 2 555 8888', 'info@andromaco.cl', 'Calle Apoquindo', '980', 'Santiago',
        'Chile');



-- ============================================
-- ================= PRODUCTOS =================
-- ============================================

INSERT INTO producto (id_producto, nombre_comercial, nombre_generico, presentacion,
                      dosificacion, unidad_medida, stock_minimo, stock_maximo,
                      activo, categoria, url_foto, id_fabricante)
VALUES ('prod-001', 'Paracetamol MK', 'Paracetamol', 'Tabletas', 500, 'mg', 20, 200, TRUE,
        'ANALGESICOS_ANTIINFLAMATORIOS', 'https://example.com/paracetamol.jpg', 'fab-001'),
       ('prod-002', 'Amoxicilina 500', 'Amoxicilina', 'Cápsulas', 500, 'mg', 10, 150, TRUE, 'ANTIBIOTICOS',
        'https://example.com/amoxicilina.jpg', 'fab-002'),
       ('prod-003', 'Ibuprofeno 400', 'Ibuprofeno', 'Tabletas', 400, 'mg', 30, 300, TRUE,
        'ANALGESICOS_ANTIINFLAMATORIOS', 'https://example.com/ibuprofeno.jpg', 'fab-003'),
       ('prod-004', 'Ceterizina 10mg', 'Ceterizina', 'Tabletas', 10, 'mg', 15, 120, TRUE, 'ANTIHISTAMINICOS',
        'https://example.com/ceterizina.jpg', 'fab-001'),
       ('prod-005', 'Omeprazol 20mg', 'Omeprazol', 'Cápsulas', 20, 'mg', 25, 200, TRUE, 'GASTROINTESTINALES',
        'https://example.com/omeprazol.jpg', 'fab-004'),
       ('prod-006', 'Metformina 850', 'Metformina', 'Tabletas', 850, 'mg', 40, 300, TRUE, 'GASTROINTESTINALES',
        'https://example.com/metformina.jpg', 'fab-005'),
       ('prod-007', 'Losartán 50mg', 'Losartán', 'Tabletas', 50, 'mg', 20, 180, TRUE, 'GASTROINTESTINALES',
        'https://example.com/losartan.jpg', 'fab-006'),
       ('prod-008', 'Enalapril 10mg', 'Enalapril', 'Tabletas', 10, 'mg', 15, 150, TRUE, 'GASTROINTESTINALES',
        'https://example.com/enalapril.jpg', 'fab-007'),
       ('prod-009', 'Aspirina 100mg', 'Ácido Acetilsalicílico', 'Tabletas', 100, 'mg', 30, 180, TRUE,
        'CARDIOVASCULARES', 'https://example.com/aspirina.jpg', 'fab-008'),
       ('prod-010', 'Vitamina C 1g', 'Ácido ascórbico', 'Tabletas', 1000, 'mg', 20, 200, TRUE, 'VITAMINAS_SUPLEMENTOS',
        'https://example.com/vitaminac.jpg', 'fab-003'),
       ('prod-011', 'Clorfenamina 4mg', 'Clorfenamina', 'Tabletas', 4, 'mg', 10, 120, TRUE, 'ANTIHISTAMINICOS',
        'https://example.com/clorfenamina.jpg', 'fab-002'),
       ('prod-012', 'Salbutamol Spray', 'Salbutamol', 'Inhalador', 100, 'mcg', 5, 80, TRUE, 'RESPIRATORIOS',
        'https://example.com/salbutamol.jpg', 'fab-006');


-- ============================================
-- ================ CODIGOS ====================
-- ============================================

INSERT INTO codigo (id_codigo, codigo_barra, tipo_codigo, activo, id_producto)
VALUES ('cod-001', '7801234001115', 'EAN13', TRUE, 'prod-001'),
       ('cod-002', '7801234001122', 'EAN13', TRUE, 'prod-001'),
       ('cod-003', '7809876002218', 'EAN13', TRUE, 'prod-002'),
       ('cod-004', '7805557003319', 'EAN13', TRUE, 'prod-003'),
       ('cod-005', '7805557003326', 'EAN13', TRUE, 'prod-003'),
       ('cod-006', '7809999004417', 'EAN13', TRUE, 'prod-004'),
       ('cod-007', '7891111001111', 'EAN13', TRUE, 'prod-005'),
       ('cod-008', '7891111002222', 'EAN13', TRUE, 'prod-005'),
       ('cod-009', '7892222001111', 'EAN13', TRUE, 'prod-006'),
       ('cod-010', '7892222002222', 'EAN13', TRUE, 'prod-006'),
       ('cod-011', '7893333001111', 'EAN13', TRUE, 'prod-007'),
       ('cod-012', '7893333002222', 'EAN13', TRUE, 'prod-007'),
       ('cod-013', '7894444001111', 'EAN13', TRUE, 'prod-008'),
       ('cod-014', '7894444002222', 'EAN13', TRUE, 'prod-008'),
       ('cod-015', '7895555001111', 'EAN13', TRUE, 'prod-009'),
       ('cod-016', '7895555002222', 'EAN13', TRUE, 'prod-009'),
       ('cod-017', '7896666001111', 'EAN13', TRUE, 'prod-010'),
       ('cod-018', '7896666002222', 'EAN13', TRUE, 'prod-010'),
       ('cod-019', '7897777001111', 'EAN13', TRUE, 'prod-011'),
       ('cod-020', '7897777002222', 'EAN13', TRUE, 'prod-011'),
       ('cod-021', '7898888001111', 'EAN13', TRUE, 'prod-012'),
       ('cod-022', '7898888002222', 'EAN13', TRUE, 'prod-012');


-- ============================================
-- ================== LOTES ====================
-- ============================================

INSERT INTO lote (id_lote, fecha_elaboracion, fecha_vencimiento, numero_lote,
                  estado, precio_unitario, limite_merma, porcentaje_oferta,
                  stock_inicial, stock_actual, stock_reservado,
                  id_codigo, id_guia_ingreso)
VALUES
    ('lot-001', '2024-01-10', '2026-01-10', 'LPA-001', 'DISPONIBLE', 1500, 5, 0.0, 100, 100, 0, 'cod-001', NULL),
    ('lot-002', '2024-02-15', '2026-02-15', 'LPA-002', 'DISPONIBLE', 2000, 5, 10.0, 120, 120, 0, 'cod-002', NULL),
    ('lot-003', '2024-03-05', '2026-03-05', 'LAM-001', 'DISPONIBLE', 4000, 3, 0.0, 200, 200, 0, 'cod-003', NULL),
    ('lot-004', '2024-01-20', '2025-12-20', 'LIB-001', 'DISPONIBLE', 2500, 4, 0.0, 150, 150, 0, 'cod-004', NULL),
    ('lot-005', '2024-02-22', '2026-02-22', 'LIB-002', 'DISPONIBLE', 1000, 4, 5.0, 80, 80, 0, 'cod-005', NULL),
    ('lot-006', '2024-04-10', '2026-04-10', 'LCE-001', 'DISPONIBLE', 5000, 2, 0.0, 60, 60, 0, 'cod-006', NULL),
    ('lot-007', '2024-05-12', '2026-05-12', 'LOM-001', 'DISPONIBLE', 450, 3, 0.0, 200, 200, 0, 'cod-007', NULL),
    ('lot-008', '2024-06-10', '2026-06-10', 'LOM-002', 'DISPONIBLE', 500, 3, 5.0, 200, 200, 0, 'cod-008', NULL),
    ('lot-009', '2024-01-18', '2025-12-18', 'LME-001', 'DISPONIBLE', 700, 4, 0.0, 100, 100, 0, 'cod-009', NULL),
    ('lot-010', '2024-03-14', '2026-03-14', 'LME-002', 'DISPONIBLE', 750, 4, 10.0, 90, 90, 0, 'cod-010', NULL),
    ('lot-011', '2024-02-10', '2026-02-10', 'LLO-001', 'DISPONIBLE', 900, 2, 0.0, 120, 120, 0, 'cod-011', NULL),
    ('lot-012', '2024-04-25', '2026-04-25', 'LLO-002', 'DISPONIBLE', 920, 2, 5.0, 120, 120, 0, 'cod-012', NULL),
    ('lot-013', '2024-01-05', '2025-11-05', 'LEN-001', 'DISPONIBLE', 1100, 2, 0.0, 160, 160, 0, 'cod-013', NULL),
    ('lot-014', '2024-03-20', '2026-03-20', 'LEN-002', 'DISPONIBLE', 1150, 2, 5.0, 160, 160, 0, 'cod-014', NULL),
    ('lot-015', '2024-01-10', '2026-01-10', 'LASP-001', 'DISPONIBLE', 350, 3, 0.0, 300, 300, 0, 'cod-015', NULL),
    ('lot-016', '2024-02-15', '2026-02-15', 'LASP-002', 'DISPONIBLE', 360, 3, 5.0, 300, 300, 0, 'cod-016', NULL),
    ('lot-017', '2024-03-12', '2026-03-12', 'LVIT-001', 'DISPONIBLE', 600, 4, 0.0, 200, 200, 0, 'cod-017', NULL),
    ('lot-018', '2024-04-18', '2026-04-18', 'LVIT-002', 'DISPONIBLE', 650, 4, 5.0, 200, 200, 0, 'cod-018', NULL);


-- ============================================
-- ============== MOVIMIENTOS ==================
-- ============================================

INSERT INTO movimiento (id_movimiento, tipo_movimiento, cantidad, fecha_movimiento, detalle, id_lote)
VALUES
('mov-001', 'INGRESO', 100, '2024-01-10', 'Ingreso inicial del lote', 'lot-001'),
('mov-002', 'INGRESO', 150, '2024-02-15', 'Ingreso inicial del lote', 'lot-002'),
('mov-003', 'INGRESO', 80,  '2024-03-05', 'Ingreso inicial del lote', 'lot-003'),
('mov-004', 'INGRESO', 200, '2024-01-20', 'Ingreso inicial del lote', 'lot-004'),
('mov-005', 'INGRESO', 180, '2024-02-22', 'Ingreso inicial del lote', 'lot-005'),
('mov-006', 'INGRESO', 120, '2024-04-10', 'Ingreso inicial del lote', 'lot-006'),
('mov-007', 'INGRESO', 160, '2024-05-12', 'Ingreso inicial del lote', 'lot-007'),
('mov-008', 'INGRESO', 140, '2024-06-10', 'Ingreso inicial del lote', 'lot-008'),
('mov-009', 'INGRESO', 90,  '2024-01-18', 'Ingreso inicial del lote', 'lot-009'),
('mov-010', 'INGRESO', 110, '2024-03-14', 'Ingreso inicial del lote', 'lot-010'),
('mov-011', 'INGRESO', 130, '2024-02-10', 'Ingreso inicial del lote', 'lot-011'),
('mov-012', 'INGRESO', 100, '2024-04-25', 'Ingreso inicial del lote', 'lot-012'),
('mov-013', 'INGRESO', 200, '2024-01-05', 'Ingreso inicial del lote', 'lot-013'),
('mov-014', 'INGRESO', 180, '2024-03-20', 'Ingreso inicial del lote', 'lot-014'),
('mov-015', 'INGRESO', 300, '2024-01-10', 'Ingreso inicial del lote', 'lot-015'),
('mov-016', 'INGRESO', 250, '2024-02-15', 'Ingreso inicial del lote', 'lot-016'),
('mov-017', 'INGRESO', 220, '2024-03-12', 'Ingreso inicial del lote', 'lot-017'),
('mov-018', 'INGRESO', 210, '2024-04-18', 'Ingreso inicial del lote', 'lot-018');

-- mock

INSERT INTO usuario(id_usuario, nombre_completo)
VALUES ('USR001', 'Usuario Demo');

INSERT INTO usuario(id_usuario, nombre_completo)
VALUES ('USR002', 'Usuario Demo');

INSERT INTO cliente (rut_cliente, nombre, direccion, id_usuario)
VALUES ('12.345.678-9', 'Cliente Demo', 'Calle de prueba 123', 'USR002');

-- ============================================
-- =========== SECUENCIA BOLETAS ==============
-- ============================================

-- Crear la secuencia para numero_boleta
CREATE SEQUENCE IF NOT EXISTS seq_num_boleta START WITH 1 INCREMENT BY 1;

-- Configurar la columna numero_boleta para usar la secuencia por defecto
ALTER TABLE boleta
    ALTER COLUMN numero_boleta
    SET DEFAULT NEXT VALUE FOR seq_num_boleta;