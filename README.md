Sistema de Gestão de Biblioteca v2.0

Sistema em Java para gerenciamento de empréstimos, devoluções e controle financeiro de uma biblioteca acadêmica.

🚀 Funcionalidades

Gestão de Usuários: Diferenciação entre Alunos e Professores com limites de empréstimo e saldo distintos.

Controle de Acervo: Cadastro e busca de livros por ID único.

Sistema de Empréstimos: Registro de transações com histórico detalhado e cálculo automático de disponibilidade.

Gestão Financeira: Aplicação de multas automáticas baseadas em dias de atraso e controle de saldo devedor.

Persistência de Dados: Salvamento e carregamento de dados via arquivos .txt (CSV).

🛠️ Conceitos de Programação Aplicados
Este projeto foi desenvolvido focando em Clean Code e nos pilares da Programação Orientada a Objetos (POO):

Abstração e Herança: Classe Usuario como base para Aluno e Professor.

Polimorfismo: Métodos de limites de livros e saldo que se comportam de forma diferente dependendo do tipo de usuário.

Encapsulamento: Proteção de dados sensíveis e uso de IDs estáticos para garantir integridade.

Java Time API: Uso de LocalDate e ChronoUnit para cálculos precisos de prazos e multas.

📊 Estrutura do Banco de Dados (Lógica Relacional)
Embora o projeto utilize arquivos de texto, a estrutura foi pensada para futura migração para SQL:

Entidades: Usuário, Livro, Pagamento.

Relacionamento: RegistroEmprestimo (Tabela associativa que liga Usuário e Livro).

💻 Como Executar
Clone o repositório.

Certifique-se de ter o JDK 17 ou superior instalado.

Compile e rode a classe Main.java.

Os dados serão salvos automaticamente em arquivos .txt na raiz do projeto ao sair.

🛠️ Tecnologias
Java SE

Java Time API (Manipulação de datas)

Java I/O (Manipulação de arquivos)


ESSE PROJETO AINDA NÃO FINALIZOU, ESTAREI IMPLEMENTANDO UM BANCO DE DADOS LOGO.
