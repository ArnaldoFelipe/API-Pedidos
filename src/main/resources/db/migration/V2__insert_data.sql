
-- Clientes
INSERT INTO customers (name, email) VALUES
('Alice Silva', 'alice@email.com'),
('Bruno Costa', 'bruno@email.com'),
('Carla Mendes', 'carla@email.com'),
('Daniel Souza', 'daniel@email.com'),
('Elisa Rocha', 'elisa@email.com');

-- Produtos
INSERT INTO products (name, price) VALUES
('Notebook', 3500.00),
('Smartphone', 2000.00),
('Teclado', 150.00),
('Mouse', 80.00),
('Monitor', 900.00);

-- Pedidos
INSERT INTO orders (customer_id, status, total, created_at) VALUES
(1, 'PENDING', 3650.00, CURRENT_TIMESTAMP),
(2, 'PAID', 2000.00, CURRENT_TIMESTAMP),
(3, 'CANCELLED', 980.00, CURRENT_TIMESTAMP);

-- Itens de pedido
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 3500.00),  -- Notebook
(1, 4, 2, 80.00),    -- Mouse
(2, 2, 1, 2000.00),  -- Smartphone
(3, 3, 2, 150.00),   -- Teclado
(3, 5, 1, 900.00);   -- Monitor
