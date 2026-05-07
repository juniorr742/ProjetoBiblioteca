-- DDL
-- Sistema escolar

-- 1. Instituicao
select @@global.time_zone, @@session.time_zone;

SET global time_zone = 'America/Sao_Paulo';
SET session time_zone = 'America/Sao_Paulo';
select now();

create table instituicao (
idInstituicao int auto_increment primary key,
nome varchar(200) not null,
cnpj varchar(18) not null unique,
cidade varchar(100) not null,
estado varchar(100) not null,
email varchar(150) not null,
telefone varchar(20),
criado_em datetime not null default current_timestamp);

-- 2. Professor

create table professor (
idProfessor int auto_increment primary key,
instituicaoId int not null, 
nome varchar(150) not null,
cpf varchar (14) not null unique,
email varchar(150) not null unique,
titulacao enum('GRADUACAO', 'ESPECIALIZACAO', 'MESTRADO', 'DOUTORADO') DEFAULT 'GRADUACAO',
criado_em datetime not null default current_timestamp,
constraint fk_prof_inst foreign key (instituicaoId)
	references instituicao(idInstituicao)
);


create table aluno(
idAluno int auto_increment primary key,
nome varchar(150) not null,
cpf varchar (14) not null unique,
email varchar(150) not null unique,
data_nascimento date not null,
criado_em datetime not null default current_timestamp,
constraint fk_prof_inst foreign key (instituicaoId)
references instituicao(idInstituicao)
);

create table turma (
idTurma int auto_increment primary key,
instituicaoId int not null,
professorId int not null, 
nome varchar(80) not null,
anoLetivo year not null, 
turno  enum('MATUTINO', 'VESPERTINO', 'NOTURNO') NOT NULL,
vagas SMALLINT NOT NULL DEFAULT 40,
criado_em DATETIME NOT NULL default current_timestamp
);

CREATE TABLE matricula ( 
idMatricula int auto_increment primary key,
alunoId int not null,
turmaId int not null,
dataMatricula date not null default (current_date), 
situacao enum('ATIVA', 'TRANCADA', 'CANCELADA', 'CONCLUIDA') NOT NULL default 'ATIVA',

UNIQUE KEY uq_aluno_turma (aluno_id, turma_id),

constraint fk_mat_aluno foreign key (alunoId)
references aluno(idAluno), 
CONSTRAINT fk_mat_turma foreign key (turmaId)
references turma(idTurma)
);