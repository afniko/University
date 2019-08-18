  INSERT INTO groups (title, department_id, yearEntry) VALUES
    ('G151', 1, '2015-01-01'),
    ('G152', 1, '2015-01-01'),
    ('G153', 1, '2015-01-01'),
    ('G154', 1, '2015-01-01'),
    ('G155', 1, '2015-01-01'),
    ('G251', 2, '2015-01-01'),
    ('G252', 2, '2015-01-01'),
    ('G253', 2, '2015-01-01'),
    ('G254', 2, '2015-01-01'),
    ('G351', 3, '2015-01-01'),
    ('G352', 3, '2015-01-01'),
    ('G353', 3, '2015-01-01'),
    ('G354', 3, '2015-01-01'),
    ('G451', 4, '2015-01-01'),
    ('G452', 4, '2015-01-01'),
    ('G453', 4, '2015-01-01'),
    ('G454', 4, '2015-01-01'),
    ('G161', 1, '2016-01-01'),
    ('G162', 1, '2016-01-01'),
    ('G163', 1, '2016-01-01'),
    ('G164', 1, '2016-01-01'),
    ('G165', 1, '2016-01-01'),
    ('G261', 2, '2016-01-01'),
    ('G262', 2, '2016-01-01'),
    ('G263', 2, '2016-01-01'),
    ('G264', 2, '2016-01-01'),
    ('G361', 3, '2016-01-01'),
    ('G362', 3, '2016-01-01'),
    ('G363', 3, '2016-01-01'),
    ('G364', 3, '2016-01-01'),
    ('G461', 4, '2016-01-01'),
    ('G462', 4, '2016-01-01'),
    ('G463', 4, '2016-01-01'),
    ('G464', 4, '2016-01-01');

  INSERT INTO persons (first_name, last_name, middle_name, birthday, idfees) VALUES
    ('firstname1', 'lastname1', 'middlename1', '1991-02-01', 1111111),
    ('firstname2', 'lastname2', 'middlename2', '1992-02-02', 1111112),
    ('firstname3', 'lastname3', 'middlename3', '1993-02-03', 1111113),
    ('firstname4', 'lastname4', 'middlename4', '1994-02-04', 1111114),
    ('firstname5', 'lastname5', 'middlename5', '1995-02-05', 1111115),
    ('firstname6', 'lastname6', 'middlename6', '1996-02-06', 1111116),
    ('firstname7', 'lastname7', 'middlename7', '1997-02-07', 1111117),
    ('firstname8', 'lastname8', 'middlename8', '1998-02-08', 1111118),
    ('firstname9', 'lastname9', 'middlename9', '1999-02-09', 1111119),
    ('firstname10', 'lastname10', 'middlename10', '2000-02-10', 1111120);

  INSERT INTO students (person_id, group_id) VALUES
    (1,1),
    (2,1),
    (3,2),
    (4,2),
    (5,3),
    (6,3),
    (7,4),
    (8,4),
    (9,5),
    (10,5);
