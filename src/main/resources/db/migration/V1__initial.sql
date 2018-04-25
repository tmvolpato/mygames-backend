CREATE SEQUENCE public.company_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_with_oids = false;

CREATE TABLE public.company (
    id bigint DEFAULT nextval('public.company_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    name character varying(200) NOT NULL
);

CREATE SEQUENCE public.game_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.game (
    id bigint DEFAULT nextval('public.game_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    price numeric(10,2) NOT NULL,
    title character varying(200),
    company_id bigint NOT NULL,
    genre_id bigint NOT NULL,
    platform_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE SEQUENCE public.genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.genre (
    id bigint DEFAULT nextval('public.genre_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    name character varying(200) NOT NULL
);

CREATE SEQUENCE public.platform_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.platform (
    id bigint DEFAULT nextval('public.platform_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    name character varying(200) NOT NULL
);


CREATE SEQUENCE public.privilege_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.privilege (
    id bigint DEFAULT nextval('public.privilege_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    name character varying(100) NOT NULL
);


CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.role (
    id bigint DEFAULT nextval('public.role_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    name character varying(200) NOT NULL
);


CREATE TABLE public.role_has_privilege (
    role_id bigint NOT NULL,
    privilege_id bigint NOT NULL
);


CREATE TABLE public.user_has_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.users (
    id bigint DEFAULT nextval('public.users_id_seq'::regclass) NOT NULL,
    date_time_inclusion timestamp without time zone NOT NULL,
    date_time_last_edition timestamp without time zone,
    email character varying(100) NOT NULL,
    enabled boolean,
    name character varying(100) NOT NULL,
    password character varying(255) NOT NULL
);


SELECT pg_catalog.setval('public.company_id_seq', 1, false);

SELECT pg_catalog.setval('public.game_id_seq', 1, false);

SELECT pg_catalog.setval('public.genre_id_seq', 1, false);

SELECT pg_catalog.setval('public.platform_id_seq', 1, false);

SELECT pg_catalog.setval('public.privilege_id_seq', 1, false);

SELECT pg_catalog.setval('public.role_id_seq', 1, false);

SELECT pg_catalog.setval('public.users_id_seq', 1, false);

ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT idx_email UNIQUE (email);

ALTER TABLE ONLY public.company
    ADD CONSTRAINT idx_name UNIQUE (name);

ALTER TABLE ONLY public.platform
    ADD CONSTRAINT platform_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.privilege
    ADD CONSTRAINT privilege_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.role_has_privilege
    ADD CONSTRAINT role_has_privilege_pkey PRIMARY KEY (role_id, privilege_id);

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.role
    ADD CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT uk_b2i64cpcke4nrwphbye9noshb UNIQUE (title);

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT uk_ctffrbu4484ft8dlsa5vmqdka UNIQUE (name);

ALTER TABLE ONLY public.platform
    ADD CONSTRAINT uk_hp36t3hx9su23msu2p5qvukyh UNIQUE (name);

ALTER TABLE ONLY public.company
    ADD CONSTRAINT uk_niu8sfil2gxywcru9ah3r4ec5 UNIQUE (name);

ALTER TABLE ONLY public.user_has_role
    ADD CONSTRAINT user_has_role_pkey PRIMARY KEY (user_id, role_id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_has_role
    ADD CONSTRAINT fk2dl1ftxlkldulcp934i3125qo FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.role_has_privilege
    ADD CONSTRAINT fk8utqwhxwbnxarlybuotls6r90 FOREIGN KEY (privilege_id) REFERENCES public.privilege(id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk_game_company FOREIGN KEY (company_id) REFERENCES public.company(id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk_game_genre FOREIGN KEY (genre_id) REFERENCES public.genre(id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk_game_platform FOREIGN KEY (platform_id) REFERENCES public.platform(id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT fk_game_user FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.user_has_role
    ADD CONSTRAINT fkc1m07gjgx777ukpfw6wa94dfh FOREIGN KEY (role_id) REFERENCES public.role(id);

ALTER TABLE ONLY public.role_has_privilege
    ADD CONSTRAINT fkt1medfh3emfqk6kh7ujx3s0ky FOREIGN KEY (role_id) REFERENCES public.role(id);