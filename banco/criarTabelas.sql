USE instagram
GO

CREATE TABLE categoria(

    id int IDENTITY PRIMARY KEY,
    nome VARCHAR(50) UNIQUE
)
GO

CREATE  TABLE hashtag(

    id int IDENTITY PRIMARY KEY,
    nome VARCHAR(100) UNIQUE,
    id_categoria int

    FOREIGN KEY(id_categoria)  REFERENCES categoria(ID),

)
GO

