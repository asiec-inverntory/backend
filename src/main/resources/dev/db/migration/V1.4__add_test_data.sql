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
    'lorem opossum',
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

INSERT INTO equipment_type (
    type_name,
    human_readable
) VALUES    ( 'workplace', 'Рабочее место'),
            ('monitor', 'Монитор'),
            ('ram', 'Оперативная память');

INSERT INTO equipment (
    type,
    serial_code,
    room,
    parent_equipment,
    is_atomic,
    responsible,
    equipment_variant,
    is_deleted
) VALUES (
    1,
    '1234',
    1,
    NULL,
    false,
    2,
    'WORKPLACE',
    false
),  (
    2,
    '12345',
    1,
    1,
    true,
    2,
    'COMPONENT',
    false
),  (
     2,
     '123456',
     1,
     1,
     true,
     2,
     'COMPONENT',
     false
),  (
      3,
      '1234567',
      1,
      1,
      true,
      2,
      'COMPONENT',
      false
 ),  (
        3,
        '12345678',
        1,
        1,
        true,
        2,
        'COMPONENT',
        false
),  (
         3,
         '123456789',
         1,
         1,
         true,
         2,
         'COMPONENT',
         true
);

INSERT INTO characteristic (
    attribute,
    value,
    unit
) VALUES (
    5,
    'Crucial',
    NULL
), (
    6,
    'DDR3',
    NULL
), (
    7,
    '3600',
    8
), (
     5,
     'Ballistic',
     NULL
 ), (
     6,
     'DDR4',
     NULL
 ), (
     7,
     '3500',
     8
 ),(
     8,
     'R532G1601U1S-U',
     null
 ),(
       8,
       'PSD34G13332',
       null
);

INSERT INTO characteristic2equipment (
    characteristic_id,
    equipment_id
) VALUES    (1, 4 ),
            (2, 4),
            (3, 4),
            (4, 5),
            (5, 5),
            (6, 5),
            (6, 6),
            (7, 5),
            (8, 4);