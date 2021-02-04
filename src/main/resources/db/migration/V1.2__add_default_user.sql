INSERT INTO inventory_users  (
    username,
    password,
    name,
    surname,
    last_name,
    role,
    created_at,
    created_by
)  VALUES (
    'admin',
    '8201c12212ab1aaa8acf4d2f5386939db503003eb43bac0dd7abd5d6080ebd65',
    'Иван',
    'Попов',
    'Попович',
    'ADMIN',
    now(),
    1
);