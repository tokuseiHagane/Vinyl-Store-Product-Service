CREATE TABLE IF NOT EXISTS vinyl_t (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name_of_album VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    price DECIMAL NOT NULL,
    count_of_discs INTEGER NOT NULL,
    musician VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    list_of_songs VARCHAR(255) NOT NULL
);