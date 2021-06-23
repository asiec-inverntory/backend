INSERT INTO unit (unit, description) VALUES
    ('килограмм', ''),
    ('грамм', ''),
    ('метр', ''),
    ('сантиметр', ''),
    ('миллиметр', ''),
    ('ватт', ''),
    ('вольт', ''),
    ('Mhz', '');

INSERT INTO attribute (type, minimum, maximum, attribute, human_readable) VALUES
    ('NUMBER', null, null, 'length', 'Длина'),
    ('NUMBER', null, null, 'width', 'Ширина'),
    ('NUMBER', null, null, 'weight', 'Вес'),
    ('STRING', null, null, 'color', 'Цвет'),
    ('STRING', null, null, 'ram_manufacturer', 'Производитель'),
    ('STRING', null, null, 'ram_type', 'Тип'),
    ('RANGE',  0,    4000, 'ram_frequency', 'Частота'),
    ('STRING', null, null, 'ram_model', 'Модель'),
    ('STRING', null, null, 'keyboard_type', 'Тип'),
    ('STRING', null, null, 'connection_interface', 'Интерфейс подключения'),
    ('STRING', null, null, 'mouse_type', 'Тип');