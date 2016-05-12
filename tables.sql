CREATE TABLE localidad(
id                       INTEGER(2),
nombre                   VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE grupo_racial(
id                       INTEGER(2),
nombre                   VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE tipo(
id                       INTEGER(2),
nombre                   VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE sistem(
id                       INTEGER(2),
nombre                   VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE calidad(
id                       INTEGER(2),
descripcion              VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE carne(
id                       INTEGER(100),
canal_frio_der           VARCHAR(100)    NOT NULL,
canal_frio_izq           VARCHAR(100)    NOT NULL,
ojo_chuleta              VARCHAR(100)    NOT NULL,
grosor_grasa_dorsal      VARCHAR(100)    NOT NULL,
calidad_id               INTEGER(2)      NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(calidad_id) REFERENCES calidad (id)
);

CREATE TABLE animal(
id                       INTEGER(100),
edad                     INTEGER(4)        NOT NULL,
kpv                      INTEGER(100)      NOT NULL,
tipo_id                  INTEGER(2)        NOT NULL,
localidad_id             INTEGER(2)        NOT NULL,
carne_id                 INTEGER(100)      NOT NULL,
grupo_racial_id          INTEGER(2)        NOT NULL,
sistem_id                INTEGER(2)        NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(localidad_id) REFERENCES localidad (id),
FOREIGN KEY(carne_id) REFERENCES carne (id),
FOREIGN KEY(grupo_racial_id) REFERENCES grupo_racial (id),
FOREIGN KEY(sistem_id) REFERENCES sistem (id)
);

CREATE TABLE atributos_carne(
id                       INTEGER(2),
descripcion              VARCHAR(60)     NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE calidad_atributos_carne(
carne_id                 INTEGER(100),
calidad_id               INTEGER(2),
atributos_carne_id       INTEGER(2),

PRIMARY KEY(carne_id,calidad_id,atributos_carne_id),
FOREIGN KEY(carne_id) REFERENCES carne (id),
FOREIGN KEY(calidad_id) REFERENCES calidad (id),
FOREIGN KEY(atributos_carne_id) REFERENCES atributos_carne (id)
);
