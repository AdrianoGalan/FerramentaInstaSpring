USE instagram
GO

CREATE TABLE categoria(

    id int IDENTITY PRIMARY KEY,
    nome VARCHAR(50) UNIQUE
)
GO

CREATE TABLE hashtag(

    id int IDENTITY PRIMARY KEY,
    nome VARCHAR(100) UNIQUE,
    id_categoria int

    FOREIGN KEY(id_categoria)  REFERENCES categoria(ID)

)
GO

CREATE TABLE email(

    id int IDENTITY PRIMARY KEY,
    email VARCHAR(200) UNIQUE NOT NULL
)
GO

CREATE TABLE status(

    id int IDENTITY PRIMARY KEY,
    status VARCHAR(30) UNIQUE NOT NULL
)
GO

CREATE TABLE Perfil(

    ID INT IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
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

    FOREIGN KEY(id_email)  REFERENCES email(ID),
    FOREIGN KEY(id_status)  REFERENCES status(ID)
)
GO
