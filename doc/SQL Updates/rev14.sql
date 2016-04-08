
ALTER TABLE livros DROP COLUMN categoria;
ALTER TABLE livros DROP COLUMN desconto;

ALTER TABLE livros ADD COLUMN paginas INT AFTER publicacao;
ALTER TABLE livros ADD COLUMN capa INT AFTER paginas;
ALTER TABLE livros ADD COLUMN resumo VARCHAR(4096) AFTER capa;
ALTER TABLE livros ADD COLUMN sumario VARCHAR(1024) AFTER resumo;
ALTER TABLE livros ADD COLUMN preco_custo FLOAT AFTER sumario;
ALTER TABLE livros ADD COLUMN margem_lucro FLOAT AFTER preco_custo;

ALTER TABLE autores CHANGE COLUMN localMorte local_morte VARCHAR(32);

CREATE TABLE cdus
(
	id INT NOT NULL AUTO_INCREMENT,
	codigo VARCHAR(9) NOT NULL,
	tema VARCHAR(200) NOT NULL,

	UNIQUE (codigo),
	PRIMARY KEY(id)
);

CREATE TABLE livros_categorias
(
	livro INT,
	cdu INT,

	PRIMARY KEY (livro, cdu),
	FOREIGN KEY (livro) REFERENCES livros (id),
	FOREIGN KEY (cdu) REFERENCES cdus (id)
)
