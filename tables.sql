-- tables para la DB de prueba
-- Table localidad
CREATE TABLE IF NOT EXISTS `localidad`(
	`id` INT NOT NULL,
	`nombre` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table grupo_racial
CREATE TABLE IF NOT EXISTS `grupo_racial`(
	`id` INT NOT NULL,
	`nombre` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table tipo
CREATE TABLE IF NOT EXISTS `tipo`(
	`id` INT NOT NULL,
	`nombre` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table sistema
CREATE TABLE IF NOT EXISTS `sistema`(
	`id` INT NOT NULL,
	`nombre` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table calidad
CREATE TABLE IF NOT EXISTS `calidad`(
	`id` INT NOT NULL,
	`descripcion` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table carne
CREATE TABLE IF NOT EXISTS `carne`(
        `id` INT NOT NULL,
        `canal_frio_der` VARCHAR(100) NOT NULL,
        `canal_frio_izq` VARCHAR(100) NOT NULL,
        `ojo_chuleta` VARCHAR(100) NOT NULL,
        `grosor_grasa_dorsal` VARCHAR(100) NOT NULL,
        `calidad_id` INT NOT NULL,  
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table animal
CREATE TABLE IF NOT EXISTS `animal`(
        `id` INT NOT NULL,
        `edad` INT NOT NULL,
        `kpv` INT NOT NULL,
        `tipo_id` INT NOT NULL,
        `localidad_id` INT NOT NULL,
        `carne_id` INT NOT NULL,
        `grupo_racial_id` INT NOT NULL,
        `sistema_id` INT NOT NULL,
        PRIMARY KEY(`id`))
ENGINE = InnoDB;

-- Table atributos_carne
CREATE TABLE IF NOT EXISTS `atributos_carne`(
	`id` INT NOT NULL,
	`descripcion` VARCHAR(200) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- Table calidad_atributos_carne
CREATE TABLE IF NOT EXISTS `calidad_atributos_carne`(
	`carne_id` INT NOT NULL,
	`calidad_id` INT NOT NULL,
	`atributos_carne_id` INT NOT NULL,
	PRIMARY KEY(`carne_id`, `calidad_id`, `atributos_carne_id`))
ENGINE = InnoDB;
