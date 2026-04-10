# 📚 Roteiro de Aprendizado - Recomendações de Estudo

## Resumo Executivo

Baseado na análise do projeto, este documento define o caminho de aprendado para o junior evoluir de **programador iniciante** para **desenvolvedor sênior** em OOP.

**Tempo total estimado**: 4-5 semanas (dedicação full-time) ou 2-3 meses (part-time)

---

## 🎯 ROTEIRO POR SEMANAS

### **SEMANA 1-2: Fundações SOLID**

#### 1️⃣ Single Responsibility Principle (SRP) ⭐⭐⭐⭐⭐
- **Por quê**: É o MAIOR problema do código (Usuario faz tudo)
- **O que estudar**: 
  - Classes com UMA responsabilidade
  - Como identificar violações de SRP
  - Como quebrar uma god class
- **Recurso recomendado**: "SOLID Principles" - Uncle Bob (YouTube)
- **Praticar**: Refatore a classe `Usuario` do projeto
- **Tempo**: 3-4 horas

#### 2️⃣ Encapsulamento e Imutabilidade ⭐⭐⭐⭐⭐
- **Por quê**: Estado exposto quebra integridade dos dados
- **O que estudar**:
  - Access modifiers (private, public, protected, package-private)
  - Quando usar getters e setters
  - Cópia defensiva vs retorno direto
  - `final` keyword para imutabilidade
  - `Collections.unmodifiableList()` e `List.copyOf()`
- **Recurso recomendado**: "Effective Java" - Joshua Bloch (Item 16)
- **Praticar**: 
  - Remova setters de ID
  - Retorne listas imutáveis
  - Use `final` nas classes de modelo
- **Tempo**: 4-5 horas

#### 3️⃣ Open/Closed Principle (OCP) ⭐⭐⭐⭐
- **Por quê**: `instanceof` em DAO é herdação de más práticas
- **O que estudar**:
  - Polimorfismo ao invés de type checking
  - Por que `instanceof` é um code smell
  - Strategy Pattern
  - Como estender sem modificar código existente
- **Recurso recomendado**: "Design Patterns" - Gang of Four
- **Praticar**: 
  - Remova `instanceof` de `UsuarioDAO`
  - Use métodos polimórficos
- **Tempo**: 4-5 horas

---

### **SEMANA 3: Padrões de Design**

#### 4️⃣ Factory Pattern ⭐⭐⭐⭐
- **Por quê**: Lógica de criação espalhada na View
- **O que estudar**:
  - Factory Method vs Abstract Factory
  - Quando usar Factory
  - Factory como ponto centralizado de criação
- **Recurso recomendado**: Refactoring.guru - Factory Pattern
- **Praticar**: 
  - Crie `UsuarioFactory` interface
  - Refatore `menuGeral` para usar factory
  - Abstração de criação em um único lugar
- **Tempo**: 5 horas

#### 5️⃣ Strategy Pattern ⭐⭐⭐⭐
- **Por quê**: Substituir herança rígida por composição flexível
- **O que estudar**:
  - Composição sobre herança
  - Quando usar estratégias
  - Injeção de dependência de estratégia
- **Recurso recomendado**: Refactoring.guru - Strategy Pattern
- **Praticar**: 
  - Crie `PoliticaEmprestimo` interface
  - Implemente para Aluno, Professor, Visitante
  - Injete em `Usuario`
- **Tempo**: 4 horas

#### 6️⃣ Repository Pattern ⭐⭐⭐
- **Por quê**: Eliminar duplicação MASSIVA entre DAOs
- **O que estudar**:
  - Generic types em Java
  - Template Method Pattern
  - Abstração de acesso a dados
- **Recurso recomendado**: "Domain-Driven Design" - Eric Evans
- **Praticar**: 
  - Crie `Repository<T, ID>` genérico
  - Refatore `LivroDAO` e `UsuarioDAO`
  - Use `BaseRepository` para código comum
- **Tempo**: 5 horas

---

### **SEMANA 4: Práticas Avançadas**

#### 7️⃣ Dependency Injection ⭐⭐⭐⭐
- **Por quê**: Classes acopladas, impossível testar
- **O que estudar**:
  - Constructor Injection
  - Service Locator Pattern
  - Containers de DI
  - Como tornar código testável
- **Recurso recomendado**: "Growing Object-Oriented Software, Guided by Tests"
- **Praticar**: 
  - Crie `ApplicationBootstrap` factory
  - Injete dependências via constructor
  - Prepare para usar Spring/Guice futuramente
- **Tempo**: 5-6 horas

#### 8️⃣ Exceptions Customizadas ⭐⭐⭐
- **Por quê**: Erros perdidos em `System.out.println()`
- **O que estudar**:
  - Exception hierarchy
  - Checked vs Unchecked exceptions
  - Quando criar exceções customizadas
  - Logging vs System.out
- **Recurso recomendado**: "Exception Handling in Java" - Marcus Green
- **Praticar**: 
  - Crie `LivroNaoEncontradoException`
  - Crie `LimiteEmprestimoException`
  - Remova `System.out.println()` de erros
- **Tempo**: 3-4 horas

#### 9️⃣ Generics em Java ⭐⭐⭐
- **Por quê**: Necessário para Repository Pattern
- **O que estudar**:
  - Type parameters e wildcard
  - Bounded types
  - Erasure
  - Generic methods
- **Recurso recomendado**: "Java Generics and Collections" - Maurice Naftalin
- **Praticar**: 
  - Estude Repository genérico
  - Crie métodos genéricos
- **Tempo**: 6-8 horas

---

### **SEMANA 5: Testes e Manutenção**

#### 🔟 Unit Testing (JUnit) ⭐⭐⭐⭐
- **Por quê**: Sem testes, refatoração é perigosa
- **O que estudar**:
  - AAA Pattern (Arrange, Act, Assert)
  - Asserções
  - Fixtures
  - Quando mockar vs integração
- **Recurso recomendado**: "JUnit in Action" - Petar Tahchiev
- **Praticar**: 
  - Teste `CalculadoraMulta`
  - Teste `ValidadorEmprestimo`
  - Mínimo 70% de cobertura
- **Tempo**: 6-8 horas

#### 1️⃣1️⃣ Mocking (Mockito) ⭐⭐⭐
- **Por quê**: Testar sem banco de dados real
- **O que estudar**:
  - `when()`, `thenReturn()`
  - `verify()`
  - `@Mock`, `@InjectMocks`
  - Spy vs Mock
- **Recurso recomendado**: Documentação oficial Mockito
- **Praticar**: 
  - Teste `ServicoEmprestimo` com Mock de DAO
  - Teste sem conectar ao BD
- **Tempo**: 4-5 horas

#### 1️⃣2️⃣ Code Review e Refatoração ⭐⭐⭐⭐
- **Por quê**: Aplicar aprendizados no código real
- **O que estudar**:
  - Como ler código de outros
  - Sinais de código ruim (code smells)
  - Técnicas de refatoração
  - Refatoração segura (com testes)
- **Recurso recomendado**: "Refactoring" - Martin Fowler
- **Praticar**: 
  - Refatore incrementalmente o projeto
  - Faça code review com sênior regularmente
  - Entenda cada mudança antes de aplicar
- **Tempo**: Contínuo

---

## 📖 RECURSOS RECOMENDADOS

### **Livros Essenciais**

| Livro | Autor | Capítulos-Chave | Tempo | Importância |
|-------|-------|-----------------|-------|-------------|
| **Effective Java** | Joshua Bloch | 1-6, 16, 75-79 | 40h | 🔴 CRÍTICO |
| **Clean Code** | Robert Martin | 1, 3, 4, 7 | 20h | 🔴 CRÍTICO |
| **Design Patterns** | Gang of Four | Factory, Strategy | 30h | 🟡 IMPORTANTE |
| **Refactoring** | Martin Fowler | 1-3, 9-11 | 25h | 🟡 IMPORTANTE |
| **Java Generics** | Maurice Naftalin | 1-5, 8-10 | 20h | 🟢 SECUNDÁRIO |

### **Vídeos/Cursos (Gratuitos)**

- **SOLID Principles** - Java Brains
  - Link: https://youtube.com/playlist?list=PLkQkbY7JNJuC8SMIYcjrFCJyKzH4mz-1W
  - Tempo: 4 horas
  
- **Design Patterns** - Refactoring Guru
  - Link: https://refactoring.guru/design-patterns
  - Tempo: ~8 horas interativas
  
- **Factory Pattern em Java** - Buscador Português
  - Tempo: 1-2 horas

### **Plataformas Recomendadas**

- **Alura** (Português) - Cursos sobre SOLID, Design Patterns, JUnit
- **Udemy** - "Java e OOP + Design Patterns"
- **Pluralsight** - Caminho de "Java OOP Fundamentals"
- **Refactoring.guru** (Gratuito) - Padrões de Design com exemplos
- **GitHub** - Explore repositórios open-source (Spring, Guava, Apache Commons)

---

## 🎯 PLANO PRÁTICO (HANDS-ON)

### **Exercício 1: Refatorar Usuario** (Semana 1-2)
**Objetivo**: Separar responsabilidades da classe Usuario

```
Tarefas:
□ Remova metodospegarLivro() e devolverLivro() de Usuario
□ Crie classe ServicoEmprestimo
□ Crie classe ValidadorEmprestimo
□ Crie classe CalculadoraMulta
□ Usuario passa a ser apenas um modelo de dados
□ Escreva testes para cada nova classe
```

### **Exercício 2: Implementar Factory** (Semana 3)
**Objetivo**: Centralizar criação de usuários

```
Tarefas:
□ Crie UsuarioFactory interface
□ Implemente UsuarioFactoryImpl
□ Mova a lógica de menuGeral para Factory
□ Remova duplicação de código em menuGeral
□ Testes para Factory
```

### **Exercício 3: Strategy Pattern** (Semana 3)
**Objetivo**: Tornar políticas de empréstimo flexíveis

```
Tarefas:
□ Crie PoliticaEmprestimo interface
□ Implemente PoliticaAluno
□ Implemente PoliticaProfessor
□ Injete em Usuario
□ Adicione novo tipo (Visitante) facilmente
□ Teste cada política
```

### **Exercício 4: Repository Genérico** (Semana 4)
**Objetivo**: Eliminar duplicação em DAOs

```
Tarefas:
□ Estude Generics em Java
□ Crie Repository<T, ID> interface
□ Crie BaseRepository<T, ID> com código comum
□ Refatore LivroDAO extends BaseRepository
□ Refatore UsuarioDAO extends BaseRepository
□ Remova 60%+ do código duplicado
```

### **Exercício 5: Testes Unitários** (Semana 5)
**Objetivo**: Fazer projeto ser testável

```
Tarefas:
□ Teste CalculadoraMulta
  - Teste SEM atraso
  - Teste COM atraso
  - Teste valores limite
  
□ Teste ValidadorEmprestimo
  - Teste livro indisponível
  - Teste limite atingido
  - Teste sucesso
  
□ Teste ServicoEmprestimo com mocks
  - Mock de Repository
  - Mock de DAO
  - Nenhuma conexão real com BD
```

---

## ⏱️ CRONOGRAMA RECOMENDADO

### **Se tem 2 semanas (dedicação full-time)**
```
Dia 1-2:   SRP (2-3h)
Dia 3-4:   Encapsulamento (4h)
Dia 5-6:   OCP (4h) + Praticar
Dia 7-8:   Factory Pattern (5h) + Refatorar
Dia 9-10:  Strategy Pattern (4h) + Refatorar
Dia 11-14: Praticar exercícios 1-3, testes
```

### **Se tem 1 mês (dedicação full-time)**
```
Semana 1-2: Fundações SOLID (3 tópicos)
Semana 3:   Design Patterns (3 padrões)
Semana 4:   Refactoring + testes
```

### **Se tem 3 meses (part-time 1-2h/dia)**
```
Semana 1-3:   SOLID Principles
Semana 4-6:   Design Patterns
Semana 7-8:   Repository Pattern + Generics
Semana 9-10:  Testing + Refactoring
Semana 11-12: Spring Framework / Próximos passos
```

---

## 📊 RESUMO EM CHECKLIST

### **Conceitos Fundamentais**
- [ ] Entender SRP com 3 exemplos práticos
- [ ] Saber identificar god classes
- [ ] Entender encapsulamento vs exposição
- [ ] Entender imutabilidade e seus benefícios
- [ ] Conhecer polimorfismo (nem sempre instanceof)

### **Padrões de Design**
- [ ] Dominar Factory Pattern
- [ ] Dominar Strategy Pattern
- [ ] Entender Repository Pattern
- [ ] Saber quando usar cada padrão
- [ ] Poder implementar sozinho

### **Qualidade de Código**
- [ ] Remover `System.out.println()` de lógica
- [ ] Usar exceções apropriadas
- [ ] Nomear variáveis/métodos claramente
- [ ] Código é documentado automaticamente
- [ ] Sem credenciais hardcoded

### **Testes**
- [ ] Escrever testes unitários com JUnit
- [ ] Usar Mockito para isolar dependências
- [ ] Cobertura de testes > 70%
- [ ] Testes são executados automaticamente
- [ ] TDD: Test-first thinking

### **Refatoração**
- [ ] Refatorar usuario.java
- [ ] Refatorar DAOs com Repository
- [ ] Remover duplicação
- [ ] Melhorar nomes
- [ ] Aplicar padrões aprendidos

---

## 💡 DICAS IMPORTANTES

### **1. Estude E PRATIQUE Simultaneamente**
Não apenas leia teoria - refatore o projeto enquanto estuda:
- Aprendeu SRP? Refatore Usuario HOJE
- Aprendeu Factory? Refatore menuGeral HOJE
- Aprendeu Testes? Escreva testes para novo código HOJE

A prática consolida 10x mais que só ler!

### **2. Code Review Regular**
- [ ] Peça review do sênior após cada refatoração
- [ ] Justifique cada mudança
- [ ] Aprenda com feedback
- [ ] Implemente sugestões

### **3. Leia Código de Qualidade**
Explore projetos open-source:
- **Spring Framework** - Exemplo de OOP profissional
- **Guava** - Google libraries, padrões avançados
- **Apache Commons** - Utilitários bem feitos
- Veja como profissionais estruturam código

### **4. Um Conceito por Vez**
Não tente aprender tudo de uma vez. Foque:
1. SRP primeiro (semana 1)
2. Encapsulamento (semana 1-2)
3. Padrões (semana 3)
4. Testes (semana 5)

### **5. Teste Seu Aprendizado**
Para cada tópico, responda:
- ✓ Posso explicar para alguém?
- ✓ Posso implementar do zero?
- ✓ Sei quando usar e quando não usar?
- ✓ Posso refatorar código existente?

Se respondeu "não" em alguma, estude mais.

---

## 🚀 PRÓXIMOS PASSOS APÓS ESTE ROTEIRO

Após completar os 5 exercícios práticos e dominar os conceitos:

1. **Spring Framework** (2-3 semanas)
   - Dependency Injection profissional
   - MVC Pattern
   - Camadas bem separadas

2. **Test-Driven Development (TDD)** (1-2 semanas)
   - Escrever teste ANTES do código
   - Red-Green-Refactor
   - Mentalidade diferente

3. **CI/CD e DevOps Básico** (2-3 semanas)
   - GitHub Actions / Jenkins
   - Testes automatizados
   - Deploy automático

4. **Arquitetura de Software** (contínuo)
   - Microserviços
   - Domain-Driven Design
   - Escalabilidade

---

## 📝 NOTAS FINAIS

### **O que você vai ganhar:**
✓ Código mais limpo e manutenível  
✓ Menos bugs e erros  
✓ Mais fácil de estender  
✓ Mais fácil de testar  
✓ Melhor salário e oportunidades  
✓ Respeito dos colegas sêniors  

### **Tempo investido vai valer a pena:**
- Agora: 4-5 semanas intensas
- Depois: Produtividade +200%, bugs -70%, refatorações 10x mais rápidas

### **Recomendação Final:**
Comece HOJE com SRP e Encapsulamento. Essas duas coisas sozinhas vão transformar seu código. Depois continue incrementalmente com padrões de design.

**Sucesso! 🎯**

---

*Documento preparado por: Senior Java Developer*  
*Data: 2026-04-09*  
*Versão: 1.0*
