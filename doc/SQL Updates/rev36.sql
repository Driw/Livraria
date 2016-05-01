
RENAME TABLE cdus TO categorias;
ALTER TABLE livros_categorias CHANGE COLUMN cdu categoria INT;
