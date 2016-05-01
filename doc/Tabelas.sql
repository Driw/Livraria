
CREATE TABLE categorias
(
	id INT NOT NULL AUTO_INCREMENT,
	codigo VARCHAR(9) NOT NULL,
	tema VARCHAR(200) NOT NULL,

	UNIQUE (codigo),
	PRIMARY KEY(id)
);

CREATE TABLE livros
(
	id INT AUTO_INCREMENT,
	isbn CHAR(13),
	titulo VARCHAR(48) NOT NULL,
	editora INT,
	preco FLOAT,
	publicacao DATE,
	paginas INT,
	capa INT,
	resumo VARCHAR(4096),
	sumario VARCHAR(1024),

	PRIMARY KEY (id),
	UNIQUE (isbn),
	FOREIGN KEY (editora) REFERENCES editoras (id)
);

CREATE TABLE livros_categorias
(
	livro INT,
	categoria INT,

	PRIMARY KEY (livro, categoria),
	FOREIGN KEY (livro) REFERENCES livros (id),
	FOREIGN KEY (categoria) REFERENCES categorias (id)
)

CREATE TABLE editoras
(
	id INT AUTO_INCREMENT,
	cnpj CHAR(14),
	nome VARCHAR(32) NOT NULL,
	endereco VARCHAR(48),
	telefone VARCHAR(11),
	contrato_inicio DATE,
	contrato_fim DATE,

	PRIMARY KEY (id),
	UNIQUE (cnpj)
);

CREATE TABLE autores
(
	id INT AUTO_INCREMENT,
	nome VARCHAR(48) NOT NULL,
	nascimento DATE,
	falecimento DATE,
	local_morte VARCHAR(32),
	biografia VARCHAR(512),

	PRIMARY KEY (id)
);

CREATE TABLE livros_autores
(
	livro INT NOT NULL,
	autor INT NOT NULL,

	PRIMARY KEY (livro, autor),
	FOREIGN KEY (livro) REFERENCES livros (id),
	FOREIGN KEY (autor) REFERENCES autores (id)
);

CREATE TABLE carrinhos
(
	id INT AUTO_INCREMENT,
	criado DATE NOT NULL,
	concluido DATE NOT NULL,
	estado INT NOT NULL,

	PRIMARY KEY (id)
);

CREATE TABLE carrinhos_itens
(
	carrinho INT NOT NULL,
	livro INT NOT NULL,
	quantidade INT NOT NULL,

	PRIMARY KEY (carrinho, livro),
	FOREIGN KEY (carrinho) REFERENCES carrinhos(id),
	FOREIGN KEY (livro) REFERENCES livros(id)
);
