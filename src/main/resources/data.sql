-- AUCTION SECTION ___________________
DROP TABLE IF EXISTS auctions;

CREATE TABLE auctions (
    auction_id              INT             PRIMARY KEY,
    name                    VARCHAR(100)    NOT NULL,
    active                  BOOLEAN         NOT NULL,
    activation_time         TIMESTAMP       NOT NULL,
    duration                INT             NOT NULL
);

INSERT INTO auctions VALUES (17365, 'Subasta 1', true, LOCALTIMESTAMP(3), 24);
INSERT INTO auctions VALUES (21009, 'Subasta 2', false, LOCALTIMESTAMP(3), 24);
INSERT INTO auctions VALUES (32542, 'Subasta 3', true, LOCALTIMESTAMP(3), 72);
INSERT INTO auctions VALUES (48359, 'Subasta 4', false, LOCALTIMESTAMP(3), 48);
--------------------------------------

-- BILLS SECTION _____________________
DROP TABLE IF EXISTS bills;

CREATE TABLE bills (
    lot_id                      INT         PRIMARY KEY,
    item_value                  INT         NOT NULL,
    emission_year               INT         NOT NULL,
    emission_month              INT         NOT NULL,
    emission_day                INT         NOT NULL,
    conservation_status         VARCHAR(4)  NOT NULL,
    price                       FLOAT       NOT NULL,
    fk_auction                  INT
);
ALTER TABLE bills ADD FOREIGN KEY (fk_auction) REFERENCES auctions(auction_id) ON DELETE SET NULL;

INSERT INTO bills VALUES (12345, 100, 1942, 11, 3, 'EBC', 559.95, DEFAULT);
INSERT INTO bills VALUES (43212, 10, 1940, 4, 20, 'BC', 150.00, 17365);
INSERT INTO bills VALUES (56783, 500, 1943, 1, 18, 'EBC', 5999.00, DEFAULT);
INSERT INTO bills VALUES (87656, 5000, 1974, 7, 27, 'MBC', 300.00, 21009);
INSERT INTO bills VALUES (01922, 1000, 1965, 12, 5, 'MBC', 220.00, 17365);
INSERT INTO bills VALUES (66332, 5000, 1958, 10, 12, 'BC', 60.00, DEFAULT);
INSERT INTO bills VALUES (04006, 500, 1944, 8, 28, 'EBC', 400.00, 21009);
INSERT INTO bills VALUES (11823, 10000, 1940, 6, 23, 'EBC', 1200.00, 32542);
INSERT INTO bills VALUES (19933, 1000, 1955, 3, 6, 'MBC', 250.00, 48359);
INSERT INTO bills VALUES (46457, 1000, 1955, 1, 5, 'BC', 80.00, 17365);
--------------------------------------

-- COINS SECTION _____________________
DROP TABLE IF EXISTS coins;

CREATE TABLE coins (
    lot_id                      INT         PRIMARY KEY,
    item_value                  INT         NOT NULL,
    emission_year               INT         NOT NULL,
    conservation_status         VARCHAR(4)  NOT NULL,
    price                       FLOAT       NOT NULL,
    fk_auction                  INT
);
ALTER TABLE coins ADD FOREIGN KEY (fk_auction) REFERENCES auctions(auction_id) ON DELETE SET NULL;

INSERT INTO coins VALUES (18463, 10, 1966, 'MBC', 30.0, 32542);
INSERT INTO coins VALUES (63946, 1, 1939, 'EBC', 300.0, 17365);
INSERT INTO coins VALUES (39832, 5, 1954, 'MBC', 50.0, DEFAULT);
INSERT INTO coins VALUES (35527, 50, 1966, 'BC', 50.0, 32542);
INSERT INTO coins VALUES (62283, 25, 1940, 'BC', 30.0, 21009);
INSERT INTO coins VALUES (23748, 25, 1948, 'EBC', 100.0, 17365);
INSERT INTO coins VALUES (77736, 10, 1962, 'BC', 80.0, 17365);
INSERT INTO coins VALUES (80134, 200, 1970, 'EBC', 300.0, 48359);
INSERT INTO coins VALUES (15546, 100, 1961, 'MBC', 220.0, DEFAULT);
INSERT INTO coins VALUES (30129, 50, 1946, 'BC', 50.0, 48359);
--------------------------------------

-- USERS SECTION ---------------------
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id         INT             PRIMARY KEY,
    email           VARCHAR(100)    NOT NULL,
    first_name      VARCHAR(25)     NOT NULL,
    last_name       VARCHAR(50),
    password        VARCHAR(100)    NOT NULL,
    role            VARCHAR(6)      NOT NULL
);

INSERT INTO users VALUES (99999, 'admin@valuebay.com', 'Admin', 'admin', '$2a$10$6Hq/kP/hSfOl9zbWud7GlO1wlynHjbttODISzk./nFRg/XIp6ezeO', 'ADMIN');
INSERT INTO users VALUES (12629, 'Alvaro97gm@gmail.com', 'Alvaro', 'Garcia Merino', '$2b$10$3euPcmQFCiblsZeEu5s7p.9OVHgeHWFDk9nhMqZ0m/3pd/lhwZgES', 'USER');
INSERT INTO users VALUES (83221, 'Juancar922@gmail.com', 'Juan Carlos', 'Pérez Acebrón', '$2b$10$3euPcmQFCiblsZeEwrntu5s7p.kSDWFDk9nhMqZ0m/3pd/lhwasgES', 'USER');
INSERT INTO users VALUES (91164, 'ochtyruj@gmail.com', 'Jorge', 'Retuerta Merino', '$2b$10$3euPcFe6trblsZeEu5s7p.lSVHgeHWFDk9nhMqZ0m/3pd/lhwZgES', 'USER');
INSERT INTO users VALUES (52736, 'slash270897@gmail.com', 'David', 'Ramos Oliva', '$2b$10$3eASDQWsdgB6sZeEu5s7p.lt72heHWFDk9nhMqZ0m/3pd/lhwZgES', 'USER');
--------------------------------------

-- BIDS SECTION ----------------------
DROP TABLE IF EXISTS bids;

CREATE TABLE bids (
    bid_id        INT         PRIMARY KEY,
    amount        FLOAT       NOT NULL,
    fk_lot_c      INT,
    fk_lot_b      INT,
    fk_user       INT         NOT NULL
);
ALTER TABLE bids ADD FOREIGN KEY (fk_lot_c) REFERENCES coins(lot_id) ON DELETE CASCADE;
ALTER TABLE bids ADD FOREIGN KEY (fk_lot_b) REFERENCES bills(lot_id) ON DELETE CASCADE;
ALTER TABLE bids ADD FOREIGN KEY (fk_user) REFERENCES users(user_id) ON DELETE CASCADE;

INSERT INTO bids VALUES(83736, 200.0, 39832, null, 99999);