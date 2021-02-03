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
    last_login  TIMESTAMP,

    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    created_by  INTEGER   REFERENCES inventory_users (id)
);

CREATE INDEX username_index ON inventory_users(username);

CREATE TABLE building (
    id                  SERIAL                UNIQUE,
    address             VARCHAR(100) NOT NULL UNIQUE,
    building_identifier VARCHAR(50)  NOT NULL UNIQUE,

    created_at          TIMESTAMP   NOT NULL,
    updated_at          TIMESTAMP   NOT NULL,
    created_by          INTEGER REFERENCES inventory_users (id),
    updated_by          INTEGER REFERENCES inventory_users (id),
    is_deleted          BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE room (
    id          SERIAL UNIQUE,
    number      INT UNIQUE,
    flour       int         NOT NULL,
    description TEXT,
    appointment VARCHAR(50) NOT NULL,
    building    INTEGER REFERENCES building (id),
    responsible INTEGER REFERENCES inventory_users (id),

    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL,
    created_by  INTEGER REFERENCES inventory_users (id),
    updated_by  INTEGER REFERENCES inventory_users (id),
    is_deleted  BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE position (
    id          SERIAL UNIQUE,
    number      INT,
    room        INTEGER REFERENCES room (id),
    status      VARCHAR(20),

    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL,
    created_by  INTEGER REFERENCES inventory_users (id),
    updated_by  INTEGER REFERENCES inventory_users (id),
    is_deleted  BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE equipment (
    id              SERIAL UNIQUE,
    inventory_code  VARCHAR(50) NOT NULL UNIQUE,
    room            INTEGER REFERENCES room (id),
    child_equipment INTEGER REFERENCES equipment (id),
    is_atomic       BOOLEAN     NOT NULL,
    appointment     VARCHAR(50) NOT NULL,
    position        INTEGER REFERENCES position (id),

    created_at      TIMESTAMP   NOT NULL,
    updated_at      TIMESTAMP   NOT NULL,
    created_by      INTEGER REFERENCES inventory_users (id),
    updated_by      INTEGER REFERENCES inventory_users (id),
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
    from_position   INTEGER REFERENCES position (id),
    to_position     INTEGER REFERENCES position (id),
    from_room       INTEGER REFERENCES room (id),
    to_room         INTEGER REFERENCES room (id),
    from_user       INTEGER REFERENCES inventory_users (id),
    to_user         INTEGER REFERENCES inventory_users (id),

    created_at      TIMESTAMP   NOT NULL,
    updated_at      TIMESTAMP   NOT NULL,
    created_by      INTEGER REFERENCES inventory_users (id),
    updated_by      INTEGER REFERENCES inventory_users (id),
    is_deleted      BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE repair_service (
    id          SERIAL UNIQUE,
    name        VARCHAR(100)    NOT NULL UNIQUE,
    address     VARCHAR(100)    NOT NULL UNIQUE,
    telephone   VARCHAR(11)     NOT NULL UNIQUE,

    created_at  TIMESTAMP       NOT NULL,
    updated_at  TIMESTAMP       NOT NULL,
    created_by  INTEGER REFERENCES inventory_users (id),
    updated_by  INTEGER REFERENCES inventory_users (id),
    is_deleted  BOOLEAN         NOT NULL DEFAULT false
);

CREATE TABLE repair (
    id              SERIAL UNIQUE,
    equipment       INT REFERENCES equipment (id),
    repair_service  INT REFERENCES repair_service (id),
    appointed       INT REFERENCES inventory_users (id),

    created_at      TIMESTAMP   NOT NULL,
    updated_at      TIMESTAMP   NOT NULL,
    created_by      INTEGER REFERENCES inventory_users (id),
    updated_by      INTEGER REFERENCES inventory_users (id),
    is_deleted      BOOLEAN     NOT NULL DEFAULT false
);
