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
    (1, 'NUMBER', null, null, 'length', 'Длина'),
    (2, 'NUMBER', null, null, 'width', 'Ширина'),
    (3, 'NUMBER', null, null, 'weight', 'Вес'),
    (4, 'STRING', null, null, 'color', 'Цвет'),
    (5, 'STRING', null, null, 'ram_manufacture', 'Производитель'),
    (6, 'STRING', null, null, 'ram_type', 'Тип'),
    (7, 'RANGE',  0,    4000, 'ram_frequency', 'Частота'),
    (8, 'STRING', null, null, 'ram_model', 'модель');