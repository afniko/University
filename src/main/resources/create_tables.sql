CREATE TABLE IF NOT EXISTS auditory_types 
  (
    id SERIAL PRIMARY KEY,
    type VARCHAR(45) NOT NULL UNIQUE
  );


CREATE TABLE IF NOT EXISTS groups
  (
     id SERIAL PRIMARY KEY,
     NAME VARCHAR (50) NOT NULL
  );

CREATE TABLE IF NOT EXISTS students
  (
     id SERIAL PRIMARY KEY,
     group_id INT NOT NULL,
     first_name VARCHAR (25) NOT NULL,
     last_name VARCHAR (25) NOT NULL,
     CONSTRAINT fk_students_group FOREIGN KEY(group_id) REFERENCES groups(id) ON DELETE CASCADE
  );

CREATE TABLE IF NOT EXISTS courses
  (
     id SERIAL PRIMARY KEY,
     name VARCHAR (50) NOT NULL,
     description VARCHAR (100) NOT NULL
  );

CREATE TABLE IF NOT EXISTS students_courses
  (
     student_id INT,
     course_id INT,
     PRIMARY KEY (student_id, course_id),
     CONSTRAINT fk_sc_students FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
     CONSTRAINT fk_sc_courses FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
  ); 
