CREATE TABLE IF NOT EXISTS public.warehouse
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name character varying(255) NOT NULL,
    location character varying(255),
    capacity integer
);

CREATE TABLE IF NOT EXISTS public.stock
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    warehouse_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity integer NOT NULL DEFAULT 0,

    CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id)
    REFERENCES public.warehouse (id) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_product FOREIGN KEY (product_id)
    REFERENCES public.product (id) ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT unique_product_warehouse UNIQUE (warehouse_id, product_id)
);