CREATE SEQUENCE IF NOT EXISTS storage_file_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS storage_file
(
    id   BIGINT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('storage_file_seq'),
    data BYTEA  NOT NULL
);

CREATE TABLE IF NOT EXISTS storage_file_metadata (
    id UUID PRIMARY KEY,
    file_id BIGINT NOT NULL,
    file_name varchar not null,
    extension varchar not null,
    size bigint not null,
    uploaded_at timestamp with time zone,
    last_modified_at timestamp with time zone
)