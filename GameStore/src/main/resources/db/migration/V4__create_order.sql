CREATE TYPE orderStatus AS ENUM (
   'PENDING', 'PAID', 'SHIPPED', 'DELIVERED'
);

CREATE TABLE IF NOT EXISTS public.orders (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status orderStatus NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_user FOREIGN KEY (user_id)
    REFERENCES public.users (id) ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS public.order_items (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_order FOREIGN KEY (order_id)
    REFERENCES public.orders (id) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_product_order FOREIGN KEY (product_id)
    REFERENCES public.product (id) ON UPDATE CASCADE ON DELETE CASCADE
    );