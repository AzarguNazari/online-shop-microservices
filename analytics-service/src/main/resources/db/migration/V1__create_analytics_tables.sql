CREATE TABLE sales_analytics (
     id SERIAL PRIMARY KEY,
     date DATE NOT NULL,
     total_sales DOUBLE PRECISION NOT NULL,
     region VARCHAR(255),
     category VARCHAR(255)
);

CREATE TABLE customer_analytics (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    new_customers INTEGER NOT NULL,
    returning_customers INTEGER NOT NULL,
    region VARCHAR(255)
);

CREATE TABLE product_analytics (
   id SERIAL PRIMARY KEY,
   date DATE NOT NULL,
   product_id VARCHAR(255),
   product_name VARCHAR(255),
   category VARCHAR(255),
   units_sold INTEGER NOT NULL,
   total_revenue DOUBLE PRECISION NOT NULL
);

CREATE INDEX idx_sales_analytics_date ON sales_analytics(date);
CREATE INDEX idx_sales_analytics_region ON sales_analytics(region);
CREATE INDEX idx_sales_analytics_category ON sales_analytics(category);

CREATE INDEX idx_customer_analytics_date ON customer_analytics(date);
CREATE INDEX idx_customer_analytics_region ON customer_analytics(region);

CREATE INDEX idx_product_analytics_date ON product_analytics(date);
CREATE INDEX idx_product_analytics_category ON product_analytics(category);