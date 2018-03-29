CREATE SEQUENCE public.company_id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

CREATE TABLE public.company
(
  id bigint NOT NULL DEFAULT nextval('company_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  name character varying(200) NOT NULL,
  CONSTRAINT company_pkey PRIMARY KEY (id),
  CONSTRAINT uk_niu8sfil2gxywcru9ah3r4ec5 UNIQUE (name)
);

CREATE SEQUENCE public.genre_id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

CREATE TABLE public.genre
(
  id bigint NOT NULL DEFAULT nextval('genre_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  name character varying(200) NOT NULL,
  CONSTRAINT genre_pkey PRIMARY KEY (id),
  CONSTRAINT uk_ctffrbu4484ft8dlsa5vmqdka UNIQUE (name)
);

CREATE SEQUENCE public.platform_id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

 CREATE TABLE public.platform
(
  id bigint NOT NULL DEFAULT nextval('platform_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  name character varying(200) NOT NULL,
  CONSTRAINT platform_pkey PRIMARY KEY (id),
  CONSTRAINT uk_hp36t3hx9su23msu2p5qvukyh UNIQUE (name)
);

CREATE SEQUENCE public.users_id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

CREATE TABLE public.users
(
  id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  email character varying(100) NOT NULL,
  enabled boolean,
  name character varying(100) NOT NULL,
  password character varying(255) NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.role_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE public.role
(
  id bigint NOT NULL DEFAULT nextval('role_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  name character varying(200) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id),
  CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name)
);

CREATE SEQUENCE public.privilege_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE public.privilege
(
  id bigint NOT NULL DEFAULT nextval('privilege_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  name character varying(100) NOT NULL,
  CONSTRAINT privilege_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.game_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE public.game
(
  id bigint NOT NULL DEFAULT nextval('game_id_seq'::regclass),
  date_time_inclusion timestamp without time zone NOT NULL,
  date_time_last_edition timestamp without time zone,
  price numeric(10,2) NOT NULL,
  title character varying(200),
  company_id bigint NOT NULL,
  genre_id bigint NOT NULL,
  platform_id bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT game_pkey PRIMARY KEY (id),
  CONSTRAINT fk_game_company FOREIGN KEY (company_id)
  REFERENCES public.company (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_game_genre FOREIGN KEY (genre_id)
  REFERENCES public.genre (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_game_platform FOREIGN KEY (platform_id)
  REFERENCES public.platform (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_game_user FOREIGN KEY (user_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_b2i64cpcke4nrwphbye9noshb UNIQUE (title)
);

CREATE TABLE public.game_has_user
(
  game_id bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT game_has_user_pkey PRIMARY KEY (game_id, user_id),
  CONSTRAINT fkb40bc8cqtymcu8822t2cjb2xp FOREIGN KEY (game_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkt1vidtyjwoisc5wnmmgn5514a FOREIGN KEY (user_id)
  REFERENCES public.game (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.user_has_role
(
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT user_has_role_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk2dl1ftxlkldulcp934i3125qo FOREIGN KEY (user_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkc1m07gjgx777ukpfw6wa94dfh FOREIGN KEY (role_id)
  REFERENCES public.role (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.role_has_privilege
(
  role_id bigint NOT NULL,
  privilege_id bigint NOT NULL,
  CONSTRAINT role_has_privilege_pkey PRIMARY KEY (role_id, privilege_id),
  CONSTRAINT fk8utqwhxwbnxarlybuotls6r90 FOREIGN KEY (privilege_id)
  REFERENCES public.privilege (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkt1medfh3emfqk6kh7ujx3s0ky FOREIGN KEY (role_id)
  REFERENCES public.role (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);