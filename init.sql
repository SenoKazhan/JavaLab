-- Создание таблицы countries
CREATE TABLE countries (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           code VARCHAR(255) NOT NULL,
                           phone BIGINT
);

-- Создание таблицы cities
CREATE TABLE cities (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        country_id INTEGER NOT NULL,
                        FOREIGN KEY (country_id) REFERENCES countries (id)
);

-- Создание таблицы languages
CREATE TABLE languages (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

-- Создание таблицы связи count_lang для связи между countries и languages
CREATE TABLE count_lang (
                            country_id INTEGER NOT NULL,
                            language_id INTEGER NOT NULL,
                            PRIMARY KEY (country_id, language_id),
                            FOREIGN KEY (country_id) REFERENCES countries (id),
                            FOREIGN KEY (language_id) REFERENCES languages (id)
);

INSERT INTO countries (name, code, phone) VALUES ('USA', 'US', 1);
INSERT INTO countries (name, code, phone) VALUES ('Belarus', 'BY', 375);
