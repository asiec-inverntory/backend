INSERT INTO unit (id, unit, description) VALUES
    (1, 'килограмм', ''),
    (2, 'грамм', ''),
    (3, 'метр', ''),
    (4, 'сантиметр', ''),
    (5, 'милиметр', ''),
    (6, 'ватт', ''),
    (7, 'вольт', ''),
    (8, 'Mhz', '');

INSERT INTO attribute (id, type, minimum, maximum, attribute, human_readable) VALUES
    (1, 'NUMBER', null, null, 'длина', 'длина'),
    (2, 'NUMBER', null, null, 'ширина', 'ширина'),
    (3, 'NUMBER', null, null, 'вес', 'вес'),
    (4, 'STRING', null, null, 'цвет', 'цвет'),
    (5, 'STRING', null, null, 'RAM_manufacture', 'Производитель ОЗУ'),
    (6, 'STRING', null, null, 'RAM_type', 'тип ОЗУ'),
    (7, 'RANGE',  0,    4000, 'RAM_frequency', 'частота ОЗУ');