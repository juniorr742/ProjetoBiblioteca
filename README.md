# 📚 Sistema de Gerenciamento de Biblioteca — Java OOP

> Projeto desenvolvido em Java puro com foco na aplicação prática de **Orientação a Objetos** e **Clean Code**, sem frameworks externos.

---

## 💡 Sobre o projeto

Sistema de gestão de biblioteca que gerencia o fluxo completo de empréstimos e devoluções de livros, garantindo que as regras de negócio sejam respeitadas através de uma arquitetura limpa e bem definida em camadas.

---

## ⚙️ Funcionalidades

- **Gestão de Usuários e Livros** — Cadastro e controle de Alunos e Professores com perfis distintos
- **Sistema de Empréstimos Inteligente** — Validação de limites de livros, saldo devedor e prevenção de empréstimos duplicados
- **Cálculo Automático de Multas** — Processamento de devoluções com multa diária para atrasos além do prazo padrão de 7 dias
- **Separação de Responsabilidades** — Serviços independentes para cada responsabilidade (`EmprestimoService`, `CalculadoraMulta`, `ValidadorEmprestimo`)

---

## 🏗️ Arquitetura

```
src/
└── br.com.biblioteca/
    ├── config/       → Constantes e configurações do sistema
    ├── controller/   → Coordenação entre camadas
    ├── dao/          → Acesso a dados (em implementação)
    ├── factory/      → Criação de objetos (Factory Pattern)
    ├── model/        → Entidades do domínio
    ├── service/      → Regras de negócio
    └── view/         → Menus e interação com o usuário
```

---

## 📐 Princípios aplicados

| Princípio | Aplicação |
|---|---|
| **SRP** | Validação e cálculo de multa extraídos para classes independentes |
| **Encapsulamento** | Classes de modelo controlam seu próprio estado; listas expostas como `unmodifiableList` |
| **Herança e Polimorfismo** | `Aluno` e `Professor` estendem `Usuario` com limites distintos de livros e crédito |
| **Factory Pattern** | `UsuarioFactory` centraliza a criação de usuários |
| **Constantes centralizadas** | `BibliotecaConfig` como única fonte de verdade para valores do negócio |

---

## 🗺️ Roadmap

- [x] Lógica de negócio em Java puro
- [x] Arquitetura em camadas (MVC parcial)
- [ ] **Integração com banco de dados via JDBC** ← em andamento
- [ ] Credenciais externalizadas em `db.properties`
- [ ] Exceções customizadas no lugar de mensagens de console
- [ ] Injeção de dependência sem acoplamento forte
- [ ] Migração para Spring Boot com API RESTful
- [ ] Spring Data JPA para acesso aos dados
- [ ] Autenticação e segurança de usuários

---

## ▶️ Como rodar

```bash
# Clone o repositório
git clone https://github.com/juniorr742/ProjetoBiblioteca.git
```

1. Importe o projeto na sua IDE (IntelliJ, Eclipse ou VS Code)
2. Execute a classe `Main` localizada em `br.com.biblioteca.view`
3. Interaja com o sistema pelo console

---

## 👨‍💻 Autor

**Francisco Junior**  
Focado em aprender as melhores práticas de engenharia de software.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Francisco_Junior-blue?style=flat&logo=linkedin)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-juniorr742-black?style=flat&logo=github)](https://github.com/juniorr742)
