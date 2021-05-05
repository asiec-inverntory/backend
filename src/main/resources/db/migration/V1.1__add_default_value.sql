INSERT INTO unit (unit, description) VALUES
    ('килограмм', ''),
    ('грамм', ''),
    ('метр', ''),
    ('сантиметр', ''),
    ('милиметр', ''),
    ('ватт', ''),
    ('вольт', ''),
    ('Mhz', '');

INSERT INTO attribute (type, minimum, maximum, attribute, human_readable) VALUES
    ('NUMBER', null, null, 'length', 'Длина'),
    ('NUMBER', null, null, 'width', 'Ширина'),
    ('NUMBER', null, null, 'weight', 'Вес'),
    ('STRING', null, null, 'color', 'Цвет'),
    ('STRING', null, null, 'ram_manufacture', 'Производитель'),
    ('STRING', null, null, 'ram_type', 'Тип'),
    ('RANGE',  0,    4000, 'ram_frequency', 'Частота'),
    ('STRING', null, null, 'ram_model', 'модель');