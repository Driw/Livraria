
ALTER TABLE livros CHANGE COLUMN titulo titulo VARCHAR(48) NOT NULL;
ALTER TABLE editoras CHANGE COLUMN nome nome VARCHAR(32) NOT NULL;
ALTER TABLE autores CHANGE COLUMN nome nome VARCHAR(48) NOT NULL;

ALTER TABLE livros_autores CHANGE COLUMN livro livro INT NOT NULL;
ALTER TABLE livros_autores CHANGE COLUMN autor autor INT NOT NULL;

ALTER TABLE carrinhos CHANGE COLUMN criado criado DATE NOT NULL;
ALTER TABLE carrinhos CHANGE COLUMN concluido concluido DATE NOT NULL;
ALTER TABLE carrinhos CHANGE COLUMN estado estado INT NOT NULL;

ALTER TABLE carrinhos_itens CHANGE COLUMN carrinho carrinho INT NOT NULL;
ALTER TABLE carrinhos_itens CHANGE COLUMN livro livro INT NOT NULL;
ALTER TABLE carrinhos_itens CHANGE COLUMN quantidade quantidade INT NOT NULL;

ALTER TABLE editoras ADD COLUMN contrato_inicio DATE AFTER telefone;
ALTER TABLE editoras ADD COLUMN contrato_fim DATE AFTER contrato_inicio;
