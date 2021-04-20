INSERT INTO unit (id, unit, description) VALUES
    (1, 'килограмм', ''),
    (2, 'грамм', ''),
    (3, 'метр', ''),
    (4, 'сантиметр', ''),
    (5, 'милиметр', ''),
    (6, 'ватт', ''),
    (7, 'вольт', ''),
    (8, 'Mhz', '');

INSERT INTO attribute (id, type, minimum, maximum, attribute, description) VALUES
    (1, 'NUMBER', null, null, 'длина', ''),
    (2, 'NUMBER', null, null, 'ширина', ''),
    (3, 'NUMBER', null, null, 'вес', ''),
    (4, 'STRING', null, null, 'цвет', ''),
    (5, 'STRING', null, null, 'RAM manufacture', ''),
    (6, 'STRING', null, null, 'RAM type', ''),
    (7, 'RANGE',  0,    4000, 'RAM frequency', '');