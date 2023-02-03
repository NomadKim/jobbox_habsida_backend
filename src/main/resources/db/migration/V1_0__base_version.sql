CREATE TABLE IF NOT EXISTS users
(
    id                 BIGINT auto_increment PRIMARY KEY,
    email              VARCHAR (255) NULL,
    is_active          BIT NULL,
    password           VARCHAR (255) NULL,
    social_token       VARCHAR (1000) NULL,
    social_type        INT NULL,
    on_boarding_status INT NULL,
    created_at         DATETIME NULL,
    updated_at         DATETIME NULL,
    CONSTRAINT uk_users_email UNIQUE ( email )
    );

CREATE TABLE IF NOT EXISTS roles
(
    id         BIGINT auto_increment PRIMARY KEY,
    name       VARCHAR (20) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL
    );

CREATE TABLE IF NOT EXISTS user_roles
(
    id      BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY ( id, role_id ),
    CONSTRAINT fk_user_roles_role_id FOREIGN KEY ( role_id ) REFERENCES roles ( id ),
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY ( id ) REFERENCES users ( id )
    );

CREATE TABLE IF NOT EXISTS profiles
(
    id         BIGINT auto_increment PRIMARY KEY,
    about_me   VARCHAR (255) NULL,
    birthday   VARCHAR (255) NULL,
    gender     INT NULL,
    has_car    BIT NULL,
    name       VARCHAR (255) NULL,
    phone      VARCHAR (255) NULL,
    visa       INT NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT uk_profiles_user_id UNIQUE ( user_id ),
    CONSTRAINT fk_profiles_user_id FOREIGN KEY ( user_id ) REFERENCES users (
    id )
    );

CREATE TABLE IF NOT EXISTS events
(
    id                      BIGINT auto_increment PRIMARY KEY,
    about                   VARCHAR (255) NULL,
    address                 VARCHAR (255) NULL,
    daily_sum               FLOAT NULL,
    date                    VARCHAR (255) NULL,
    hours_worked            INT NULL,
    is_completed            BIT NULL,
    is_overtime_paid_hourly BIT NULL,
    is_paid                 BIT NULL,
    is_paid_hourly          BIT NULL,
    overtime_from           VARCHAR (255) NULL,
    overtime_pay            INT NULL,
    overtime_to             VARCHAR (255) NULL,
    payment                 INT NULL,
    position                VARCHAR (255) NULL,
    title                   VARCHAR (255) NULL,
    worktime_from           VARCHAR (255) NULL,
    worktime_to             VARCHAR (255) NULL,
    created_at              DATETIME NULL,
    updated_at              DATETIME NULL,
    user_id                 BIGINT NULL,
    CONSTRAINT fk_events_user_id FOREIGN KEY ( user_id ) REFERENCES users ( id
                                                                          )
    );

CREATE TABLE IF NOT EXISTS files
(
    id             BIGINT auto_increment PRIMARY KEY,
    created_at     DATETIME NULL,
    updated_at     DATETIME NULL,
    original_title VARCHAR (255) NULL,
    path           VARCHAR (255) NULL,
    type           VARCHAR (255) NULL
    );

CREATE TABLE IF NOT EXISTS categories
(
    id         BIGINT auto_increment PRIMARY KEY,
    is_active  BIT NULL,
    sort       INT NULL,
    title      VARCHAR (255) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    file_id    BIGINT NULL,
    CONSTRAINT fk_categories_file_id FOREIGN KEY ( file_id ) REFERENCES files (
    id ) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS services
(
    id          BIGINT auto_increment PRIMARY KEY,
    address     VARCHAR (255) NULL,
    description VARCHAR (255) NULL,
    is_active   BIT NULL,
    latitude    FLOAT NULL,
    longitude   FLOAT NULL,
    phone       VARCHAR (255) NULL,
    site        VARCHAR (255) NULL,
    sort        INT NULL,
    time        VARCHAR (255) NULL,
    title       VARCHAR (255) NULL,
    created_at  DATETIME NULL,
    updated_at  DATETIME NULL,
    category_id BIGINT NOT NULL,
    file_id     BIGINT NULL,
    CONSTRAINT fk_services_file_id FOREIGN KEY ( file_id ) REFERENCES files (
    id ) ON DELETE SET NULL,
    CONSTRAINT fk_services_category_id FOREIGN KEY ( category_id ) REFERENCES
    categories ( id )
    );

CREATE TABLE IF NOT EXISTS banners
(
    id         BIGINT auto_increment PRIMARY KEY,
    sort       INT NULL,
    title      VARCHAR (255) NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    file_id    BIGINT NULL,
    service_id BIGINT NOT NULL,
    CONSTRAINT fk_banners_service_id FOREIGN KEY ( service_id ) REFERENCES
    services ( id ),
    CONSTRAINT fk_banners_file_id FOREIGN KEY ( file_id ) REFERENCES files ( id
                                                                           ) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS templates
(
    id                      BIGINT auto_increment PRIMARY KEY,
    address                 VARCHAR(255) NULL,
    created_at              DATETIME NULL,
    daily_sum               FLOAT NULL,
    is_default              BIT NULL,
    is_overtime_paid_hourly BIT NULL,
    is_paid_hourly          BIT NULL,
    overtime_from           VARCHAR(255) NULL,
    overtime_pay            INT NULL,
    overtime_to             VARCHAR(255) NULL,
    payment                 INT NULL,
    position                VARCHAR(255) NULL,
    title                   VARCHAR(255) NULL,
    updated_at              DATETIME NULL,
    worktime_from           VARCHAR(255) NULL,
    worktime_to             VARCHAR(255) NULL
    );

CREATE TABLE IF NOT EXISTS user_templates
(
    template_id BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    PRIMARY KEY (template_id, user_id),
    CONSTRAINT fk_user_templates_template_id FOREIGN KEY (template_id) REFERENCES templates (id),
    CONSTRAINT fk_user_templates_user_id FOREIGN KEY (user_id) REFERENCES users (id)
    );