-- DDL 
CREATE TABLE instituicao (
idInstituicao int not null auto_increment,
nomeInstituicao varchar(60) not null, 
enderecoInstituicao varchar(100) not null, 
telefoneInstituicao varchar(11) not null,
primary key(idInstituicao) 
);

drop table alunos;


CREATE DATABASE SistemaEscolar;

	CREATE TABLE Alunos (
    idAluno int not null auto_increment,
    nomeAluno varchar(60) not null,
    matriculaAluno varchar(15) not null unique,
    cursoAluno varchar(100) not null,
    instituicaoId int not null,
    PRIMARY KEY (idAluno),
    FOREIGN KEY (instituicaoId) REFERENCES instituicao(IdInstituicao)
    );
    
    insert into alunos (cursoAlunos) values 
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');
INSERT INTO alunos (cursoAluno) VALUES ('Back-end');
INSERT INTO alunos (cursoAluno) VALUES ('Front-end');

alter table alunos add cursoAluno varchar(100);