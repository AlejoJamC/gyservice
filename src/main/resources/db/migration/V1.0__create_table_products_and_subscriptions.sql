-- UPDATED AT FUNCTION
CREATE FUNCTION public.update_updated_at_column()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS
$BODY$

BEGIN
    NEW.updated_at = clock_timestamp();
    RETURN NEW;
END;

$BODY$;

-- PRODUCTS TABLE
CREATE TABLE products
(
    id          SERIAL                                              NOT NULL,
    name        character varying(100) COLLATE pg_catalog."default" NOT NULL
        CONSTRAINT unq_product_name UNIQUE,
    description character varying(255) COLLATE pg_catalog."default",
    days_range  integer                                             NOT NULL,
    price       decimal                                             NOT NULL,
    tax         character varying(500) COLLATE pg_catalog."default" NOT NULL,
    is_active   boolean                                             NOT NULL DEFAULT true,
    is_deleted  boolean,
    created_at  timestamp with time zone                            NOT NULL DEFAULT clock_timestamp(),
    updated_at  timestamp with time zone                            NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

-- SUBSCRIPTIONS TABLE
CREATE TABLE subscriptions
(
    id         SERIAL                   NOT NULL,
    product_id bigint                   NOT NULL,
    user_id    bigint                   NOT NULL,
    start_date timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    end_date   timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    is_active  boolean                  NOT NULL DEFAULT true,
    is_deleted boolean,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    updated_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT subscriptions_pkey PRIMARY KEY (id),
    CONSTRAINT product_subs_fkey FOREIGN KEY (product_id) REFERENCES products (id)
);
