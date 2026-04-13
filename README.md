- Sistema de Gerenciamento de Biblioteca (Java OOP)
Este projeto é um sistema de gestão de biblioteca desenvolvido em Java, focado na aplicação prática de Princípios de Orientação a Objetos e Clean Code. O objetivo principal é gerenciar o fluxo de empréstimos e devoluções de livros, garantindo que as regras de negócio sejam respeitadas através de uma arquitetura limpa e bem definida.

- O que o projeto faz?
Atualmente, o sistema conta com as seguintes funcionalidades principais:

Gestão de Usuários e Livros: Cadastro e controle de diferentes tipos de usuários (Alunos e Professores) e exemplares de livros.

Sistema de Empréstimos Inteligente:

Validação de limites (quantidade de livros e saldo devedor).

Prevenção de empréstimos duplicados do mesmo exemplar para o mesmo usuário.

Cálculo Automático de Multas: Processamento de devoluções com cálculo de multa diária para atrasos fora do prazo padrão (7 dias).

Separação de Responsabilidades: Utilização de serviços específicos para cada tarefa (EmprestimoService, CalculadoraMulta, ValidadorEmprestimo), garantindo que o código seja fácil de manter e testar.

- Arquitetura e Princípios Aplicados
Durante o desenvolvimento, foquei em sair de um código procedural para um design orientado a objetos robusto:

SRP (Single Responsibility Principle): Extraí as lógicas de validação e cálculo de multa para classes independentes.

Encapsulamento: Melhorei a forma como os dados são acessados, garantindo que as classes de modelo (como Usuario) controlem seu próprio estado.

Herança e Polimorfismo: Implementação de diferentes perfis de usuário com limites de crédito e livros distintos.

- Evolução do Projeto
O projeto está em constante evolução. Atualmente, os próximos passos são:

Integração com Banco de Dados (JDBC): Estamos na fase de substituir o armazenamento em memória por um banco de dados relacional para persistência real dos dados.

Injeção de Dependência: Refatorar os serviços para remover o acoplamento forte (uso do new dentro dos construtores).

Tratamento de Exceções: Implementar exceções customizadas para substituir mensagens de console por um fluxo de erro mais profissional.

- Futuro: Spring Boot API
O roadmap deste projeto prevê a transformação desta lógica de negócio em uma API RESTful utilizando Spring Boot.

Exposição de endpoints para frontend/mobile.

Uso de Spring Data JPA para facilitar o acesso aos dados.

Segurança e autenticação de usuários.

Como rodar o projeto
Clone o repositório:

Bash
git clone https://github.com/seu-usuario/seu-repositorio.git
Importe o projeto na sua IDE favorita (IntelliJ, Eclipse ou VS Code).

Execute a classe principal (Main) para testar as funcionalidades de empréstimo e devolução no console.

Desenvolvido por Francisco Junior - Focado em aprender as melhores práticas de engenharia de software.
