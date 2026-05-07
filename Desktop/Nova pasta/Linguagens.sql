-- DML 

INSERT INTO 
sistemaescolar.instituicao
(nomeInstituicao, enderecoInstituicao, telefoneInstituicao)
VALUES
('Senai', 'Rua niteroi , 180 - São Caetano do sul', '2022-2554'),
('Etec Jorge Street', 'RUa do parelelo , 898 - São Caetano do Sul', '56987845');

-- DQL 
SELECT nomeInstituicao FROM instituicao;

ALTER TABLE alunos
drop column cursoAluno;




INSERT INTO alunos (nomeAluno, matriculaAluno, cursoAluno, instituicaoId) VALUES 
('Ana Silva', '20240001', 'Back-end', 1),
('Bruno Oliveira', '20240002', 'Front-end', 2),
('Carla Souza', '20240003', 'Back-end', 1),
('Diego Santos', '20240004', 'Front-end', 2),
('Eduarda Lima', '20240005', 'Back-end', 1),
('Fabio Pereira', '20240006', 'Back-end', 1),
('Giovanna Costa', '20240007', 'Front-end', 2),
('Henrique Rocha', '20240008', 'Front-end', 2),
('Isabela Martins', '20240009', 'Back-end', 1),
('João Vitor', '20240010', 'Front-end', 2),
('Lucas Ferreira', '20240011', 'Back-end', 1),
('Mariana Alves', '20240012', 'Back-end', 1),
('Nicolas Ribeiro', '20240013', 'Front-end', 2),
('Olivia Gomes', '20240014', 'Back-end', 1),
('Pedro Henrique', '20240015', 'Front-end', 2),
('Rafaela Dias', '20240016', 'Front-end', 2),
('Samuel Barbosa', '20240017', 'Back-end', 1),
('Tatiane Ramos', '20240018', 'Front-end', 2),
('Ursula Moraes', '20240019', 'Back-end', 1),
('Vinicius Junior', '20240020', 'Front-end', 2);


select * from alunos;

SELECT 
cursoAluno, COUNT(*) as totalAlunos 
FROM alunos group by cursoAluno 
ORDER BY totalAlunos desc;

SELECT 
cursoAluno, COUNT(*) as totalAlunos 
FROM alunos group by cursoAluno 
ORDER BY totalAlunos asc;

-- os alunos e a instituicao de ensino 

SELECT 
	nomeAluno,
    cursoAluno,
    nomeInstituicao
    FROM alunos
    JOIN instituicao ON alunos.instituicaoId = instituicao.idInstituicao;
    
    
    --  QUANTOS ALUNOS EXISTEM DENTRO DE UMA INSTITUICAO;
    
    SELECT i.nomeInstituicao,
	count(*) AS totalAlunos
    FROM  instituicao i 
    JOIN alunos on i.idInstituicao = alunos.instituicaoId
    group by i.nomeInstituicao;
    
    -- exiba quantos alunos existem em curso x da instituicao a 
    
    SELECT 
    i.nomeInstituicao, 
    a.cursoAluno, 
    COUNT(*) AS totalAlunos
FROM instituicao i
JOIN alunos a ON i.idInstituicao = a.instituicaoId
WHERE i.nomeInstituicao = 'Nome Da Instituicao A' 
  AND a.cursoAluno = 'Back-end'
GROUP BY i.nomeInstituicao, a.cursoAluno;
    
    
    