CREATE INDEX idx_products_search_vector
    ON products
    USING GIN (search_vector);