  CREATE TABLE IF NOT EXISTS auditories 
  (
    id SERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    auditory_type_id INT,
    capacity INT,
    description VARCHAR(200)
  );

  CREATE TABLE IF NOT EXISTS auditory_types 
  (
    id SERIAL PRIMARY KEY,
    type VARCHAR(45) NOT NULL UNIQUE
  );

