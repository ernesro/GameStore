-- ============================================================================
-- V4: Clean seed data
-- Removes inconsistent test data inserted during early development and
-- replaces it with a coherent, realistic dataset.
-- Also resets all identity sequences so that JPA inserts never collide
-- with explicitly seeded IDs (fixes the order_item_pkey conflict).
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. Wipe existing data
--    TRUNCATE handles FK ordering automatically with CASCADE and resets
--    identity sequences with RESTART IDENTITY.
-- ----------------------------------------------------------------------------
TRUNCATE TABLE
    public.refresh_tokens,
    public.order_item,
    public.orders,
    public.stock,
    public.product_item_tags,
    public.product_tags,
    public.product,
    public.warehouse,
    public.user_roles,
    public.users,
    public.role
    RESTART IDENTITY CASCADE;

-- ----------------------------------------------------------------------------
-- 2. Roles
-- ----------------------------------------------------------------------------
INSERT INTO public.role (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

-- ----------------------------------------------------------------------------
-- 3. Users (password = BCrypt)
--    admin / admin1234
--    user  / user1234
-- ----------------------------------------------------------------------------
INSERT INTO public.users (id, email, password, username)
VALUES (1, 'admin@gamestore.local', '$2a$10$jUPKMD0DDX212kOXIHAr1OgFoTXOaeasze6X8mQo8bMX6Ce3EO0BS', 'admin'),
       (2, 'user@gamestore.local',  '$2a$10$uNI20tAndRXBtBW9EZhfSuQA7.DbhkLCTh9y1mOFdJ1HI01h2/g56', 'user');

INSERT INTO public.user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

-- ----------------------------------------------------------------------------
-- 4. Warehouses
-- ----------------------------------------------------------------------------
INSERT INTO public.warehouse (id, name, location, capacity)
VALUES (1, 'Madrid Central',  'Madrid',    5000),
       (2, 'Barcelona Norte', 'Barcelona', 3000);

-- ----------------------------------------------------------------------------
-- 5. Products
--    itemcategory must be one of the values allowed by product_category_check.
--    Platform information is carried by item tags.
-- ----------------------------------------------------------------------------
INSERT INTO public.product (id, average_rating, itemcategory, itemcondition, description, image_url, name, price)
VALUES (1, 4.7, 'CONSOLE_GAME', 'NEW',  'Post-apocalyptic action-adventure focused on survival and revenge.', NULL, 'The Last of Us Part II',      39.99),
       (2, 4.9, 'CONSOLE_GAME', 'NEW',  'Open-world adventure across Hyrule with a revolutionary building system.', NULL, 'The Legend of Zelda: Tears of the Kingdom', 59.99),
       (3, 4.8, 'CONSOLE_GAME', 'NEW',  'Open-world action RPG by FromSoftware and George R. R. Martin.',     NULL, 'Elden Ring',                  49.99),
       (4, 4.6, 'CONSOLE_GAME', 'NEW',  'Hand-drawn metroidvania full of secrets in a vast underground kingdom.', NULL, 'Hollow Knight',            14.99),
       (5, 4.8, 'CONSOLE_GAME', 'USED', 'An epic of the American frontier about loyalty and survival in 1899.', NULL, 'Red Dead Redemption 2',      29.99),
       (6, 4.7, 'CONSOLE_GAME', 'NEW',  'Rogue-like dungeon crawler where you defy the god of the dead.',      NULL, 'Hades',                       24.99),
       (7, 4.9, 'CONSOLE_GAME', 'NEW',  'Kratos and Atreus face the end of the world in Norse mythology.',     NULL, 'God of War Ragnarok',         54.99),
       (8, 4.8, 'CONSOLE_GAME', 'NEW',  'Inherit an old farm and build the life you always dreamed of.',       NULL, 'Stardew Valley',              12.99);

-- ----------------------------------------------------------------------------
-- 6. Product tags (platform + genre, values allowed by the check constraint)
-- ----------------------------------------------------------------------------
INSERT INTO public.product_item_tags (product_id, itemtags)
VALUES (1, 'PS4'),             (1, 'ACTION'),    (1, 'ADVENTURE'),
       (2, 'NINTENDO_SWITCH'), (2, 'ADVENTURE'), (2, 'ACTION'),
       (3, 'PS5'),             (3, 'RPG'),       (3, 'ACTION'),
       (4, 'PC'),              (4, 'ACTION'),    (4, 'ADVENTURE'),
       (5, 'PS4'),             (5, 'ACTION'),    (5, 'ADVENTURE'),
       (6, 'PC'),              (6, 'RPG'),       (6, 'ACTION'),
       (7, 'PS5'),             (7, 'ACTION'),    (7, 'ADVENTURE'),
       (8, 'PC'),              (8, 'SIMULATION'),(8, 'RPG');

-- ----------------------------------------------------------------------------
-- 7. Stock
--    Includes two low-stock rows (quantity <= 5) so the LowStockEvent
--    can be demonstrated right after creating an order.
-- ----------------------------------------------------------------------------
INSERT INTO public.stock (id, warehouse_id, product_id, quantity)
VALUES (1, 1, 1, 24),
       (2, 1, 2,  8),
       (3, 2, 3,  3),
       (4, 1, 4, 51),
       (5, 2, 5,  2),
       (6, 1, 6,  9),
       (7, 1, 7, 17),
       (8, 2, 8, 33);

-- ----------------------------------------------------------------------------
-- 8. Sample orders for the demo user, in different lifecycle states.
--    order_item.price stores the unit price at purchase time.
--    orders.price is the order total.
-- ----------------------------------------------------------------------------
INSERT INTO public.orders (id, user_id, status, created_at, price)
VALUES (1, 2, 'DELIVERED', '2026-04-12 14:30:00', 64.98),
       (2, 2, 'SHIPPED',   '2026-04-28 10:15:00', 49.98),
       (3, 2, 'PENDING',   '2026-05-08 18:45:00', 54.99);

INSERT INTO public.order_item (id, price, quantity, order_id, product_id)
VALUES (1, 49.99, 1, 1, 3),  -- Elden Ring
       (2, 14.99, 1, 1, 4),  -- Hollow Knight
       (3, 24.99, 2, 2, 6),  -- Hades x2
       (4, 54.99, 1, 3, 7);  -- God of War Ragnarok

-- ----------------------------------------------------------------------------
-- 9. Reset identity sequences to MAX(id).
--    Inserting with explicit IDs does NOT advance an identity sequence,
--    so without this step the next JPA insert would reuse ID 1 and fail
--    with a duplicate-key violation.
-- ----------------------------------------------------------------------------
SELECT setval('public.role_id_seq',       (SELECT MAX(id) FROM public.role));
SELECT setval('public.users_id_seq',      (SELECT MAX(id) FROM public.users));
SELECT setval('public.warehouse_id_seq',  (SELECT MAX(id) FROM public.warehouse));
SELECT setval('public.product_id_seq',    (SELECT MAX(id) FROM public.product));
SELECT setval('public.stock_id_seq',      (SELECT MAX(id) FROM public.stock));
SELECT setval('public.orders_id_seq',     (SELECT MAX(id) FROM public.orders));
SELECT setval('public.order_item_id_seq', (SELECT MAX(id) FROM public.order_item));