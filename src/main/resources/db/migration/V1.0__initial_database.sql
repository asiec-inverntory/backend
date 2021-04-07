CREATE TABLE inventory_users (
    id          SERIAL               UNIQUE,
    username    VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(128) NOT NULL,
    name        VARCHAR(30) NOT NULL,
    surname     VARCHAR(30) NOT NULL,
    last_name   VARCHAR(30),
    UNIQUE (name, surname, last_name),
    role        VARCHAR(20) NOT NULL,
    is_deleted  BOOLEAN     NOT NULL DEFAULT false,
    last_login  TIMESTAMP
);

CREATE INDEX username_index ON inventory_users(username);

CREATE TABLE building (
    id                  SERIAL                UNIQUE,
    address             VARCHAR(100) NOT NULL UNIQUE,
    building_identifier VARCHAR(50)  NOT NULL UNIQUE,

    is_deleted          BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE room (
    id          SERIAL UNIQUE,
    number      INT         NOT NULL UNIQUE,
    flour       int         NOT NULL,
    description TEXT,
    building    INTEGER REFERENCES building (id),
    responsible INTEGER REFERENCES inventory_users (id),

    is_deleted  BOOLEAN     NOT NULL DEFAULT false
);


CREATE TABLE equipment (
    id              SERIAL UNIQUE,
    name            VARCHAR(50),
    inventory_code  VARCHAR(50) NOT NULL UNIQUE,
    room            INTEGER REFERENCES room (id),
    parent_equipment INTEGER REFERENCES equipment (id),
    is_atomic       BOOLEAN     NOT NULL,
    responsible INTEGER REFERENCES inventory_users (id),
    equipment_type VARCHAR(50) NOT NULL,

    is_deleted      BOOLEAN     NOT NULL DEFAULT false
);


CREATE TABLE attribute (
    id SERIAL UNIQUE,
    attribute   TEXT NOT NULL,
    description TEXT,
    is_deleted  BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE unit (
    id SERIAL UNIQUE,
    unit TEXT NOT NULL,
    description TEXT,
    is_deleted  BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE characteristic (
    id          SERIAL UNIQUE,
    attribute   INTEGER REFERENCES attribute (id),
    value       TEXT    NOT NULL,
    unit        INTEGER REFERENCES unit (id)
);

CREATE TABLE characteristic2equipment (
    characteristic_id   INTEGER REFERENCES characteristic (id),
    equipment_id        INTEGER REFERENCES equipment (id)
);

CREATE TABLE event (
    id      SERIAL,
    type    VARCHAR(20) NOT NULL,
    data    TEXT        NOT NULL
);

CREATE TABLE inventory_order (
    id              SERIAL UNIQUE,
    type            VARCHAR(20) NOT NULL,
    appointed       VARCHAR(50) NOT NULL,
    equipment       INTEGER REFERENCES equipment (id),
    from_room       INTEGER REFERENCES room (id),
    to_room         INTEGER REFERENCES room (id),
    from_user       INTEGER REFERENCES inventory_users (id),
    to_user         INTEGER REFERENCES inventory_users (id),

    is_deleted      BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE repair_service (
    id          SERIAL UNIQUE,
    name        VARCHAR(100)    NOT NULL UNIQUE,
    address     VARCHAR(100)    NOT NULL UNIQUE,
    telephone   VARCHAR(11)     NOT NULL UNIQUE,

    is_deleted  BOOLEAN         NOT NULL DEFAULT false
);

CREATE TABLE repair (
    id              SERIAL UNIQUE,
    equipment       INT REFERENCES equipment (id),
    repair_service  INT REFERENCES repair_service (id),

    is_deleted      BOOLEAN     NOT NULL DEFAULT false
);