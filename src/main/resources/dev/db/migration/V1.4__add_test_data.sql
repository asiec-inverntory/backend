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
    equipment_key,
    human_readable,
    serial_code,
    room,
    parent_equipment,
    is_atomic,
    responsible,
    equipment_type,
    is_deleted
) VALUES (
    1,
    'workplace',
    'Монитор',
    '1234',
    1,
    NULL,
    false,
    2,
    'WORKPLACE',
    false
),  (
    2,
    'monitor',
    'Монитор',
    '12345',
    1,
    1,
    true,
    2,
    'COMPONENT',
    false
),  (
     3,
     'monitor',
     'Монитор',
     '123456',
     1,
     1,
     true,
     2,
     'COMPONENT',
     false
),  (
      4,
      'RAM',
      'Оперативная память',
      '1234567',
      1,
      1,
      true,
      2,
      'COMPONENT',
      false
 ),  (
        5,
        'RAM',
        'Оперативная память',
        '12345678',
        1,
        1,
        true,
        2,
        'COMPONENT',
        false
),  (
         6,
         'RAM',
         'Оперативная память',
         '123456789',
         1,
         1,
         true,
         2,
         'COMPONENT',
         true
);

INSERT INTO characteristic (
    id,
    attribute,
    value,
    unit
) VALUES (
    1,
    5,
    'Crucial',
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
), (
     4,
     5,
     'Ballistic',
     NULL
 ), (
     5,
     6,
     'ddr4',
     NULL
 ), (
     6,
     7,
     '3500',
     8
 ),(
     7,
     8,
     'R532G1601U1S-U',
     null
 ),(
       8,
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