-- Insert sample data into sales_analytics
INSERT INTO sales_analytics (date, total_sales, region, category)
VALUES
    ('2023-01-01', 1000.50, 'North', 'Electronics'),
    ('2023-01-02', 1500.75, 'South', 'Clothing'),
    ('2023-01-03', 2000.25, 'East', 'Home'),
    ('2023-01-04', 1800.60, 'West', 'Electronics'),
    ('2023-01-05', 2200.90, 'North', 'Clothing');

-- Insert sample data into customer_analytics
INSERT INTO customer_analytics (date, new_customers, returning_customers, region)
VALUES
    ('2023-01-01', 50, 150, 'North'),
    ('2023-01-02', 45, 200, 'South'),
    ('2023-01-03', 60, 180, 'East'),
    ('2023-01-04', 55, 220, 'West'),
    ('2023-01-05', 70, 250, 'North');

-- Insert sample data into product_analytics
INSERT INTO product_analytics (date, product_id, product_name, category, units_sold, total_revenue)
VALUES
    ('2023-01-01', 'P001', 'Smartphone', 'Electronics', 20, 10000.00),
    ('2023-01-02', 'P002', 'T-shirt', 'Clothing', 50, 1250.00),
    ('2023-01-03', 'P003', 'Sofa', 'Home', 5, 5000.00),
    ('2023-01-04', 'P004', 'Laptop', 'Electronics', 10, 15000.00),
    ('2023-01-05', 'P005', 'Jeans', 'Clothing', 30, 1800.00);