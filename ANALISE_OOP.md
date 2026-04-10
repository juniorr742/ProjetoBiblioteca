# 📋 Análise OOP - Projeto Biblioteca

## Resumo Executivo
O projeto possui uma boa estrutura base com camadas (Model, DAO, Controller, View), mas apresenta **violações significativas de princípios SOLID**, especialmente em encapsulamento, responsabilidade única e abstração. Abaixo estão os principais problemas e refatorações recomendadas.

---

## 🔴 PROBLEMAS CRÍTICOS

### 1. **VIOLAÇÃO MASSIVE DO SINGLE RESPONSIBILITY PRINCIPLE (SRP)**

#### ❌ Problema: Classe `Usuario` faz TUDO
A classe `Usuario` é uma "god class" com múltiplas responsabilidades:
- ✗ Gerencia lista de livros emprestados
- ✗ Calcula multas por atraso
- ✗ Gerencia saldo devedor
- ✗ Valida limites (saldo e quantidade de livros)
- ✗ Realiza lógica de empréstimo/devolução

```java
public void pegarLivro(Livro livro) {
    boolean jaPossuiEsseExemplar = livroEmprestado.stream()
        .anyMatch(l -> l.getId() == livro.getId());
    if (jaPossuiEsseExemplar) { ... }
    
    if (this.saldo.getSaldoDevedor() >= getLimiteSaldo() 
        || livroEmprestado.size() >= getLimiteLivros()) { ... }
    else {
        livroEmprestado.add(livro);
        saldo.registrarEmprestimo();
    }
}
```

#### ✅ Solução: Extrair responsabilidades em serviços especializados
```java
// Gerencia apenas dados de usuário
public class Usuario {
    private String nome;
    private int id;
    private Pagamento saldo;
    private List<Livro> livrosEmprestados;
    // Apenas getters para leitura
}

// Gerencia políticas de empréstimo
public interface EmprestimoPolicy {
    boolean podeEmprestar(Usuario usuario);
    void registrarEmprestimo(Usuario usuario, Livro livro);
}

public class EmprestimoPolicyImpl implements EmprestimoPolicy {
    private LimiteValidador validador;
    private Pagamento pagamento;
    
    @Override
    public boolean podeEmprestar(Usuario usuario) {
        return validador.podeEmprestar(usuario);
    }
}

// Gerencia cálculo de multas
public class CalculadoraMulta {
    public double calcularMulta(int diasAtraso) {
        return diasAtraso * 2.0;
    }
}
```

---

### 2. **ENCAPSULAMENTO QUEBRADO**

#### ❌ Problema 1: Método retorna lista mutável
```java
public List<Livro> getlivroEmprestado() {
    return livroEmprestado;  // ❌ PERIGO! Cliente pode modificar
}
```

Cliente malicioso ou errado pode fazer:
```java
usuario.getlivroEmprestado().clear();  // Deleta TODOS os livros!
usuario.getlivroEmprestado().add(livroFalso);
```

#### ✅ Solução: Retornar cópia imutável
```java
public List<Livro> getLivrosEmprestados() {
    return Collections.unmodifiableList(livroEmprestado);
}
// Ou usando Java 10+:
public List<Livro> getLivrosEmprestados() {
    return List.copyOf(livroEmprestado);
}
```

#### ❌ Problema 2: Setters desnecessários
```java
public void setId(int id) { this.id = id; }
public void setSaldoDevedor(double saldoDevedor) { this.saldoDevedor = saldoDevedor; }
```

IDs e saldos devem ser **imutáveis após criação**. Permitir modificação quebra a integridade.

#### ✅ Solução: Remover setters, usar constructor
```java
public class Usuario {
    private final int id;  // final
    private final String nome;  // final
    private Pagamento saldo;
    
    // Constructor valida e atribui
    public Usuario(String nome) {
        this.nome = Objects.requireNonNull(nome);
        this.id = nextId++;  // Gerado aqui
        this.saldo = new Pagamento();
    }
}
```

---

### 3. **VIOLAÇÃO DO OPEN/CLOSED PRINCIPLE (OCP)**

#### ❌ Problema: Verificação de tipo com `instanceof`
```java
// Em UsuarioDAO.salvar()
if (usuario instanceof Aluno) {
    stmt.setString(2, "Aluno");
} else {
    stmt.setString(2, "Professor");
}

// Em UsuarioDAO.buscarporId()
Usuario u = tipo.equalsIgnoreCase("ALUNO") ? new Aluno(nome) : new Professor(nome);
```

**Problema**: Se adicionar novo tipo (Visitante, Pesquisador), precisa modificar DAO em vários lugares.

#### ✅ Solução: Polimorfismo com Pattern Strategy
```java
// Cada tipo de usuário define seu próprio comportamento
public abstract class Usuario {
    private String tipoUsuario;
    
    public abstract String obterTipo();
    public abstract double getLimiteSaldo();
    public abstract int getLimiteLivros();
}

public class Aluno extends Usuario {
    @Override
    public String obterTipo() { return "ALUNO"; }
    
    @Override
    public double getLimiteSaldo() { return 15; }
    
    @Override
    public int getLimiteLivros() { return 3; }
}

// DAO fica mais simples:
public class UsuarioDAO {
    public void salvar(Usuario usuario) {
        stmt.setString(2, usuario.obterTipo());  // Polimórfico!
    }
}
```

---

### 4. **GERAÇÃO DE ID COM PROBLEMA SÉRIO**

#### ❌ Problemas: Contador estático global compartilhado
```java
public class Livro {
    private static int contadorId = 1;  // ❌ Estado compartilhado
    
    public Livro(String titulo, String autor) {
        this.id = contadorId;
        contadorId++;  // ❌ Thread-unsafe, sem persistência
    }
}

// Mesmo problema em Usuario e RegistroEmprestimo
```

**Problemas catastróficos**:
1. ❌ **Não é thread-safe** → comportamento imprevisível em aplicações multi-thread
2. ❌ **Perde a sequência ao reiniciar** → IDs duplicados após restart
3. ❌ **Sem persistência compartilhada** → Livro e Usuario podem ter ID = 1
4. ❌ **Impossível testar** → Estado global compartilhado

#### ✅ Solução: Usar banco de dados ou padrão Builder
```java
// Opção 1: Auto-increment do banco (MELHOR)
public class LivroDAO {
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)";
        // BD gera ID automaticamente
        // SELECT LAST_INSERT_ID() recupera
    }
}

// Opção 2: Usar UUID se não tiver relação com banco
public class Livro {
    private final UUID id;  // Único globalmente
    
    public Livro(String titulo, String autor) {
        this.id = UUID.randomUUID();
    }
}

// Opção 3: Factory Pattern
public class LivroFactory {
    private int proximoId = 1;  // Sincronizado
    
    public synchronized Livro criar(String titulo, String autor) {
        return new Livro(proximoId++, titulo, autor);
    }
}
```

---

### 5. **LÓGICA DUPLICADA E INCONSISTENTE**

#### ❌ Problema: Devolução com duplicação
```java
public void devolverLivro(Livro livro, int diasCorridos) {
    // ... validação ...
    
    if (jaPossuiEsseExemplar) {
        // ... Atraso e multa ...
    } else {
        System.out.println("Livro devolvido no prazo.");
        livroEmprestado.remove(livro);  // Remove aqui
    }
    livroEmprestado.removeIf(l -> l.getId() == livro.getId());  // ❌ Remove NOVAMENTE!
}
```

O `removeIf()` final sempre executa, mesmo que já tenha removido. Lógica confusa.

#### ✅ Solução: Lógica clara e sem duplicação
```java
public void devolverLivro(Livro livro, int diasCorridos) {
    if (!possuiLivro(livro)) {
        throw new LivroNaoEncontradoException("Livro não encontrado");
    }
    
    if (diasCorridos > PRAZO_PADRAO) {
        aplicarMulta(diasCorridos - PRAZO_PADRAO);
    }
    
    livroEmprestado.removeIf(l -> l.getId() == livro.getId());
}

private boolean possuiLivro(Livro livro) {
    return livroEmprestado.stream()
        .anyMatch(l -> l.getId() == livro.getId());
}

private void aplicarMulta(int diasAtraso) {
    double multa = diasAtraso * VALOR_DIA_ATRASO;
    saldo.registrarMulta(multa);
}
```

---

### 6. **CREDENCIAIS HARDCODED**

#### ❌ Crítico: Banco de dados exposto
```java
public class LivroDAO {
    private String url = "jdbc:mysql://localhost:3306/biblioteca_db";
    private String user = "root";
    private String password = "admin123";  // ❌ EXPOSIÇÃO DE SEGURANÇA!
}
```

**Problemas**:
- ❌ Credenciais em código-fonte
- ❌ Sem flexibilidade para diferentes ambientes
- ❌ Difícil manutenção

#### ✅ Solução: Usar properties/configuration
```java
// application.properties
db.url=jdbc:mysql://localhost:3306/biblioteca_db
db.user=root
db.password=admin123

// Classe de configuração
@Configuration
public class DatabaseConfig {
    @Value("${db.url}")
    private String url;
    
    @Value("${db.user}")
    private String user;
    
    @Value("${db.password}")
    private String password;
    
    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
}

// DAO recebe por injeção
public class LivroDAO {
    private DatabaseConfig config;
    
    public LivroDAO(DatabaseConfig config) {
        this.config = config;
    }
}
```

---

### 7. **PADRÕES ESTRUTURAIS AUSENTES**

#### ❌ Problema: Criação de usuários sem padrão
```java
// Em menuGeral.java - lógica misturada com UI
if (subUsuario.equalsIgnoreCase("aluno")) {
    Usuario alunoNovo = new Aluno(nomeAluno);
    info.adicionarUsuario(alunoNovo);
} else {
    Usuario professorNovo = new Professor(nomeProfessor);
    info.adicionarUsuario(professorNovo);
}
```

Lógica de criação espalhada na View.

#### ✅ Solução: Factory Pattern
```java
public interface UsuarioFactory {
    Usuario criarAluno(String nome);
    Usuario criarProfessor(String nome);
}

public class UsuarioFactoryImpl implements UsuarioFactory {
    private UsuarioDAO usuarioDAO;
    
    @Override
    public Usuario criarAluno(String nome) {
        Usuario aluno = new Aluno(nome);
        usuarioDAO.salvar(aluno);
        return aluno;
    }
    
    @Override
    public Usuario criarProfessor(String nome) {
        Usuario professor = new Professor(nome);
        usuarioDAO.salvar(professor);
        return professor;
    }
}

// View fica simples:
Usuario usuario = usuarioFactory.criar(tipo, nome);
```

---

### 8. **VIOLAÇÃO DO LISKOV SUBSTITUTION PRINCIPLE (LSP)**

#### ❌ Problema: Subclasses não ampliam, apenas constrain
```java
public class Aluno extends Usuario {
    @Override
    public double getLimiteSaldo() { return 15; }
    
    @Override
    public int getLimiteLivros() { return 3; }
}

public class Professor extends Usuario {
    @Override
    public double getLimiteSaldo() { return 100; }
    
    @Override
    public int getLimiteLivros() { return 10; }
}
```

Embora funcione, seria melhor usar **Strategy Pattern** para política de limite.

#### ✅ Solução: Composição com Strategy
```java
public interface PoliticaEmprestimo {
    double getLimiteSaldo();
    int getLimiteLivros();
}

public class PoliticaAluno implements PoliticaEmprestimo {
    @Override
    public double getLimiteSaldo() { return 15; }
    
    @Override
    public int getLimiteLivros() { return 3; }
}

public class Usuario {
    private final PoliticaEmprestimo politica;
    
    public Usuario(String nome, PoliticaEmprestimo politica) {
        this.nome = nome;
        this.politica = politica;
    }
    
    public double getLimiteSaldo() {
        return politica.getLimiteSaldo();
    }
}
```

**Benefício**: Política de empréstimo é independente da hierarquia de usuários.

---

### 9. **NOMEAÇÃO INADEQUADA**

#### ❌ Problemas de naming
```java
public List<Livro> getlivroEmprestado() {  // ❌ "livro" em minúscula
public void listarLista() {  // ❌ Redundante
public class menuGeral {  // ❌ Interface deveria ser PascalCase
public void bucarLivroPorId(int id) {  // ❌ "bucar" deveria ser "buscar"
```

#### ✅ Convenções corretas
```java
public List<Livro> getLivrosEmprestados() {  // ✓ Plural, camelCase
public void listarLivrosEmprestados() {  // ✓ Verbo claro
public class MenuPrincipal {  // ✓ PascalCase
public Livro buscarLivroPorId(int id) {  // ✓ Assim certo
```

---

### 10. **FALTA DE ABSTRAÇÃO DE DAO**

#### ❌ Problema: Cada DAO é independente, sem contrato
```java
public class LivroDAO { ... }
public class UsuarioDAO { ... }
public class EmprestimosDAO { ... }
```

Sem interface, há duplicação e sem contrato.

#### ✅ Solução: Usar padrão Repository com Generics
```java
public interface Repository<T, ID> {
    void salvar(T entidade);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void atualizar(T entidade);
    void deletar(ID id);
}

public class LivroRepository implements Repository<Livro, Integer> {
    private DatabaseConfig config;
    
    @Override
    public void salvar(Livro livro) { ... }
    
    @Override
    public Livro buscarPorId(Integer id) { ... }
}

public class UsuarioRepository implements Repository<Usuario, Integer> { ... }
```

---

### 11. **GERENCIAMENTO DE RECURSOS (DAO)**

#### ❌ Problema: Conexões (em potencial)
```java
try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement stmt = conn.prepareStatement(sql)) {
    // ... código ...
}
```

Embora use try-with-resources (bom!), falta:
- ❌ Pool de conexões
- ❌ Transações
- ❌ Tratamento de erros consistente

#### ✅ Solução: Connection Pool
```java
public class ConnectionPool {
    private static final HikariDataSource dataSource = new HikariDataSource();
    
    static {
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/biblioteca_db");
        dataSource.setUsername("root");
        dataSource.setPassword("admin123");
        dataSource.setMaximumPoolSize(10);
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

// No DAO:
try (Connection conn = ConnectionPool.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
    // ...
}
```

---

## 🟡 PROBLEMAS SECUNDÁRIOS

### 12. **Lógica de negócio na View**
A classe `menuGeral` contém lógica que deveria estar no controller:
```java
// ❌ Está em menuGeral
if (tituloEmprestimo != null) {
    info.procurarLivroPorTitulo(tituloEmprestimo);
    // ...
}
```

**View deveria apenas**:
- Exibir menu
- Coletar input
- Chamar controller

### 13. **Modelo de Transação fraco**
`RegistroEmprestimo` é passivo. Deveria gerenciar ciclo de vida:
```java
public class RegistroEmprestimo {
    public void finalizarComSucesso() { }
    public void finalizarComAtraso(int dias) { }
    public boolean estaAblerto() { return !finalizado; }
}
```

### 14. **Falta de Exceções customizadas**
```java
// ❌ Ao invés de Sistema.out.println()
public void emprestarLivro(int idLivro, int idUsuario) {
    // ...
    System.out.println("Livro já está emprestado!");
}

// ✅ Lançar exceção
public void emprestarLivro(int idLivro, int idUsuario) 
    throws LivroIndisponıvelException {
    // ...
    throw new LivroIndisponıvelException("Livro já está emprestado!");
}
```

### 15. **Comparação de ID com tipo primitivo**
```java
// ❌ Comparação ineficiente
for (Livro l : acervo) {
    if (l.getId() == livroProcurar.trim()) {  // String == int ❌
        return "ID do livro: " + l.getId();
    }
}

// ✅ Usar busca com lambda/stream
return acervo.stream()
    .filter(l -> l.getTitulo().equalsIgnoreCase(livroProcurar))
    .findFirst()
    .map(Livro::getId)
    .orElse(null);
```

---

## 📊 RESUMO DE VIOLAÇÕES SOLID

| Princípio | Violação | Severidade |
|-----------|----------|-----------|
| **S**ingle Responsibility | Usuario faz tudo | 🔴 CRÍTICA |
| **O**pen/Closed | `instanceof` em DAO | 🔴 CRÍTICA |
| **L**iskov Substitution | Subclasses não são substituíveis | 🟡 MÉDIA |
| **I**nterface Segregation | Interfaces muito genéricas | 🟡 MÉDIA |
| **D**ependency Inversion | Sem injeção de dependência | 🔴 CRÍTICA |

---

## ✅ PLANO DE REFATORAÇÃO

### **Fase 1: Fundações (URGENTE)**
1. [ ] Criar interfaces Repository para DAOs
2. [ ] Extrair lógica de validação em classe separada
3. [ ] Remover credenciais hardcoded
4. [ ] Criar exceções customizadas
5. [ ] Implementar Factory para usuários

### **Fase 2: Encapsulamento**
1. [ ] Remover setters desnecessários
2. [ ] Retornar cópias imutáveis
3. [ ] Usar `final` para imutabilidade
4. [ ] Validação em constructors

### **Fase 3: Separação de Responsabilidades**
1. [ ] Extrair `EmprestimoService`
2. [ ] Extrair `CalculadoraMulta`
3. [ ] Extrair `LimiteValidador`
4. [ ] Limpar classes de modelo

### **Fase 4: Padrões de Design**
1. [ ] Implementar Strategy para políticas
2. [ ] Implementar Builder para objetos complexos
3. [ ] Implementar Dependency Injection
4. [ ] Adicionar Transaction Management

### **Fase 5: Testes e Qualidade**
1. [ ] Unit tests para cada camada
2. [ ] Integration tests para DAOs
3. [ ] Adicionar logging
4. [ ] Documentação JavaDoc

---

## 🎯 PRÓXIMOS PASSOS RECOMENDADOS

1. **HOJE**: Refatorar `Usuario` para remover responsabilidades
2. **HOJE**: Criar `EmprestimoValidator` centralizado
3. **AMANHÃ**: Implementar Factory Pattern
4. **AMANHÃ**: Criar Layer de Service entre Controller e Model
5. **SEMANA**: Remover hardcoding e adicionar configuração

---

**Documento preparado por: Senior Java Developer**
**Data:** 2026-04-09
**Status:** Análise Completa ✓
