  CREATE TABLE IF NOT EXISTS lecturies
  (
  id SERIAL PRIMARY KEY,
  number VARCHAR(5) NOT NULL UNIQUE,
  start_time TIME,
  end_time TIME
  );
