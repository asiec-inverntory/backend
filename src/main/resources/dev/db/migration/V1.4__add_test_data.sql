INSERT INTO room (
    id,
    number,
    flour,
    description,
    building,
    responsible
) VALUES (
    1,
    101,
    1,
    'lorem oposum',
    1,
    1
);

INSERT INTO inventory_users  (
    id,
    username,
    password,
    name,
    surname,
    last_name,
    role
)  VALUES (
    2,
    'alice',
    '8201c12212ab1aaa8acf4d2f5386939db503003eb43bac0dd7abd5d6080ebd65',
    'Алиса',
    'Жукова',
    'Тимофеевна',
    'OWNER'
);

INSERT INTO equipment (
    id,
    name,
    inventory_code,
    room,
    parent_equipment,
    is_atomic,
    responsible,
    equipment_type
) VALUES (
    1,
    'Рабочее место',
    '1234',
    1,
    NULL,
    false,
    2,
    'WORKPLACE'
),  (
    2,
    'Монитор',
    '12345',
    1,
    1,
    true,
    2,
    'COMPONENT'
),  (
     3,
     'Монитор',
     '123456',
     1,
     1,
     true,
     2,
     'COMPONENT'
),  (
      4,
      'ОЗУ',
      '1234567',
      1,
      1,
      true,
      2,
      'COMPONENT'
 );

INSERT INTO characteristic (
    id,
    attribute,
    value,
    unit
) VALUES (
    1,
    5,
    'crucial',
    NULL
), (
    2,
    6,
    'ddr3',
    NULL
), (
    3,
    7,
    '3600',
    8
);

INSERT INTO characteristic2equipment (
    characteristic_id,
    equipment_id
) VALUES    (1, 4 ),
            (2, 4),
            (3, 4);