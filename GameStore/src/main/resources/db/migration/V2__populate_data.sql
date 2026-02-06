


-- Roles
INSERT INTO public.role (id, name) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- Usuarios
INSERT INTO public.users (id, email, password, username) VALUES
(2, 'admin@gmail.com', '$2a$10$jrrLEAAG61TVzjdWaLxNiekKlDZLipyfoNvG7x.BtGJ1FAWwIz/8y', 'admin');

-- Warehouse
INSERT INTO public.warehouse (id, name, location, capacity) VALUES
(1, 'Almacen', 'Torrevieja', 10000),
(2, 'string', 'string', 200);

-- Productos
INSERT INTO public.product (id, average_rating, itemcategory, itemcondition, description, image_url, name, price) VALUES
(3, 5, 'CONSOLE_GAME', 'NEW', 'string', 'string', 'string', 10000),
(4, 5, 'CONSOLE_GAME', 'NEW', 'string', 'string', 'string', 10000);

-- Orders
INSERT INTO public.orders (id, user_id, status, created_at, price) VALUES
(1, 2, 'PENDING', '2025-11-09 18:34:13.665321', 170000);

-- Order Items
INSERT INTO public.order_item (id, price, quantity, order_id, product_id) VALUES
(1, 0, 10, 1, 3),
(2, 0, 7, 1, 4);

-- Stock
INSERT INTO public.stock (id, warehouse_id, product_id, quantity) VALUES
(1, 1, 3, 2000);

-- User Roles
INSERT INTO public.user_roles (user_id, role_id) VALUES
(2, 1);