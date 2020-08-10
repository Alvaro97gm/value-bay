-- AUCTION SECTION ___________________
DROP TABLE IF EXISTS auctions;

CREATE TABLE auctions (
    auction_id      INT     PRIMARY KEY,
    end_time        DATE    NOT NULL
);

INSERT INTO auctions VALUES (1, CURRENT_DATE+1);
INSERT INTO auctions VALUES (2, CURRENT_DATE+1);
INSERT INTO auctions VALUES (3, CURRENT_DATE+1);
INSERT INTO auctions VALUES (4, CURRENT_DATE+1);
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

INSERT INTO bills VALUES (1234, 100, 1942, 11, 3, 'EBC', 559.9, DEFAULT);
INSERT INTO bills VALUES (4321, 10, 1940, 4, 20, 'BC', 150.0, 1);
INSERT INTO bills VALUES (5678, 500, 1943, 1, 18, 'EBC', 5999.0, DEFAULT);
INSERT INTO bills VALUES (8765, 5000, 1974, 7, 7, 'MBC', 300.0, 2);
INSERT INTO bills VALUES (0192, 1000, 1965, 12, 5, 'MBC', 220.0, 3);
INSERT INTO bills VALUES (6633, 5000, 1958, 15, 12, 'BC', 60.0, DEFAULT);
INSERT INTO bills VALUES (0400, 500, 1944, 30, 8, 'EBC', 400.0, 2);
INSERT INTO bills VALUES (1182, 10000, 1940, 13, 3, 'EBC', 1200.0, 1);
INSERT INTO bills VALUES (1993, 1000, 1955, 3, 6, 'MBC', 250.0, 3);
INSERT INTO bills VALUES (4645, 1000, 1955, 12, 5, 'BC', 80.0, 4);
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

INSERT INTO coins VALUES (8463, 10, 1966, 'MBC', 30.0, 1);
INSERT INTO coins VALUES (3946, 1, 1939, 'EBC', 300.0, 1);
INSERT INTO coins VALUES (9832, 5, 1954, 'MBC', 50.0, DEFAULT);
INSERT INTO coins VALUES (5527, 50, 1966, 'BC', 50.0, 3);
INSERT INTO coins VALUES (2283, 25, 1940, 'BC', 30.0, 4);
INSERT INTO coins VALUES (3748, 25, 1948, 'EBC', 100.0, 4);
INSERT INTO coins VALUES (7736, 10, 1962, 'BC', 80.0, 1);
INSERT INTO coins VALUES (0134, 200, 1970, 'EBC', 300.0, 2);
INSERT INTO coins VALUES (5546, 100, 1961, 'MBC', 220.0, DEFAULT);
INSERT INTO coins VALUES (0129, 50, 1946, 'BC', 50.0, 2);
--------------------------------------