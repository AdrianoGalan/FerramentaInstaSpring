CREATE TABLE categoria(

    id int PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE hashtag(

    id int PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) UNIQUE  NOT NULL,
    id_categoria int NOT NULL,
    CONSTRAINT `fk_categoria_hashtag`

        FOREIGN KEY(id_categoria)  REFERENCES categoria(ID)

);

CREATE TABLE email(

    id int PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(200) UNIQUE NOT NULL
);


CREATE TABLE status(

    id int PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE Perfil(

    ID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    sobre_nome VARCHAR(150) NOT NULL,
    dispositivo varchar(100),
    data_criacao DATE,
    data_cadastro DATE,
    data_bloqueio DATE,
    data_inicio_trabalho DATE,
    numero_seguidor INT,
    numero_seguindo INT,
    genero CHAR(1),
    id_email int,
    id_status int,

    CONSTRAINT `valida_genero`
        CHECK(genero = 'F' or genero = 'M'),

    CONSTRAINT `fk_email_perfil`
        FOREIGN KEY(id_email)  REFERENCES email(ID),

    CONSTRAINT `fk_status_perfil`
        FOREIGN KEY(id_status)  REFERENCES status(ID)
);

CREATE TABLE nomes(

    id int PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) UNIQUE NOT NULL,
    genero char(1) not NULL,

    CONSTRAINT `valida_genero`
        CHECK(genero = 'F' or genero = 'M')
);

CREATE TABLE sobrenomes(

    id int PRIMARY KEY AUTO_INCREMENT,
    sobrenome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE biografia(

    id int PRIMARY KEY AUTO_INCREMENT,
    bio VARCHAR(100) UNIQUE NOT NULL
);



