CREATE OR REPLACE FUNCTION products_search_vector_update() RETURNS trigger AS $$
BEGIN
    NEW.search_vector :=
        setweight(to_tsvector('russian', coalesce(NEW.title, '')), 'A') ||
        setweight(to_tsvector('russian', coalesce(NEW.description, '')), 'B');
RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_products_search_vector_update
    BEFORE INSERT OR UPDATE OF title, description
                     ON products
                         FOR EACH ROW
                         EXECUTE FUNCTION products_search_vector_update();