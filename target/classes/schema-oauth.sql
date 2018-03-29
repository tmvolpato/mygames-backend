DROP table IF EXISTS oauth_client_details;

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               character varying(255) PRIMARY KEY,
  resource_ids            character varying(255),
  client_secret           character varying(255),
  scope                   character varying(255),
  authorized_grant_types  character varying(255),
  web_server_redirect_uri character varying(255),
  authorities             character varying(255),
  access_token_validity   integer,
  refresh_token_validity  integer,
  additional_information  character varying(4096),
  autoapprove             boolean
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          character varying(255) DEFAULT NULL,
  token             BYTEA,
  authentication_id character varying(255) DEFAULT NULL,
  user_name         character varying(255) DEFAULT NULL,
  client_id         character varying(255) DEFAULT NULL,
  authentication    BYTEA,
  refresh_token     character varying(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       character varying(255) DEFAULT NULL,
  token          BYTEA,
  authentication BYTEA
);

