# 💻 Exemplos de Refatoração - Código Antes & Depois

---

## Exemplo 1: EXTRAÇÃO DE RESPONSABILIDADES

### ❌ ANTES: Usuario com múltiplas responsabilidades
```java
public abstract class Usuario {
    private String nome;
    private List<Livro> livroEmprestado;
    private Pagamento saldo;
    
    public void pegarLivro(Livro livro) {
        // Validação integrada
        boolean jaPossuiEsseExemplar = livroEmprestado.stream()
            .anyMatch(l -> l.getId() == livro.getId());
        if (jaPossuiEsseExemplar) {
            System.out.println("Você ja esta com esse livro");
            return;
        }
        
        // Verificação de limites
        if (this.saldo.getSaldoDevedor() >= getLimiteSaldo() || 
            livroEmprestado.size() >= getLimiteLivros()) {
            System.out.println("Saldo devedor atingiu o limite");
        } else {
            livroEmprestado.add(livro);
            saldo.registrarEmprestimo();
        }
    }
    
    public void devolverLivro(Livro livro, int diasCorridos) {
        // Lógica de devolução e cálculo de multa
        if (diasCorridos > 7) {
            int diasAtraso = diasCorridos - 7;
            double valorMulta = diasAtraso * 2;
            this.saldo.registrarMulta(valorMulta);
        }
        livroEmprestado.removeIf(l -> l.getId() == livro.getId());
    }
}
```

### ✅ DEPOIS: Responsabilidades separadas

```java
// 1. Modelo de dados APENAS (immutable)
public class Usuario {
    private final int id;
    private final String nome;
    private final List<Livro> livrosEmprestados;
    private final Pagamento saldo;
    
    public Usuario(String nome, Pagamento saldo) {
        this.id = UsuarioIdGenerator.nextId();
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        this.livrosEmprestados = new ArrayList<>();
        this.saldo = Objects.requireNonNull(saldo);
    }
    
    // Apenas getters para leitura
    public String getNome() { return nome; }
    public int getId() { return id; }
    public Pagamento getSaldo() { return saldo; }
    public List<Livro> getLivrosEmprestados() {
        return Collections.unmodifiableList(livrosEmprestados);
    }
    
    // Métodos que modificam estado retornam resultado
    public void adicionarLivro(Livro livro) {
        livrosEmprestados.add(livro);
    }
    
    public void removerLivro(Livro livro) {
        livrosEmprestados.removeIf(l -> l.getId() == livro.getId());
    }
}

// 2. Validador de empréstimo (estratégia)
public interface PoliticaEmprestimo {
    boolean podeEmprestar(Usuario usuario);
    int getLimiteLivros();
    double getLimiteSaldo();
}

public class PoliticaAlunoImpl implements PoliticaEmprestimo {
    @Override
    public boolean podeEmprestar(Usuario usuario) {
        return usuario.getLivrosEmprestados().size() < getLimiteLivros() &&
               usuario.getSaldo().getSaldoDevedor() < getLimiteSaldo();
    }
    
    @Override
    public int getLimiteLivros() { return 3; }
    
    @Override
    public double getLimiteSaldo() { return 15.0; }
}

// 3. Serviço de empréstimo (orquestra operações)
public class ServicoEmprestimo {
    private final PoliticaEmprestimo politica;
    private final ValidadorEmprestimo validador;
    
    public ServicoEmprestimo(PoliticaEmprestimo politica) {
        this.politica = politica;
        this.validador = new ValidadorEmprestimo(politica);
    }
    
    public void emprestarLivro(Usuario usuario, Livro livro) {
        validador.validarEmprestimo(usuario, livro);
        
        usuario.adicionarLivro(livro);
        usuario.getSaldo().registrarEmprestimo();
        livro.setDisponivel(false);
    }
}

// 4. Calculadora de multa (responsabilidade única)
public class CalculadoraMulta {
    private static final int PRAZO_PADRAO = 7;
    private static final double VALOR_DIA = 2.0;
    
    public double calcular(int diasCorridos) {
        if (diasCorridos <= PRAZO_PADRAO) {
            return 0.0;
        }
        return (diasCorridos - PRAZO_PADRAO) * VALOR_DIA;
    }
}

// 5. Validador centralizado
public class ValidadorEmprestimo {
    private final PoliticaEmprestimo politica;
    
    public ValidadorEmprestimo(PoliticaEmprestimo politica) {
        this.politica = politica;
    }
    
    public void validarEmprestimo(Usuario usuario, Livro livro) 
        throws LivroIndisponıvelException, LimiteAtivoException {
        
        if (!livro.isDisponivel()) {
            throw new LivroIndisponıvelException("Livro já está emprestado");
        }
        
        if (usuario.getLivrosEmprestados().contains(livro)) {
            throw new LivroIndisponıvelException("Usuário já possui este livro");
        }
        
        if (!politica.podeEmprestar(usuario)) {
            throw new LimiteAtivoException("Limite de empréstimo atingido");
        }
    }
}
```

**Benefícios**:
- ✓ Cada classe tem UMA responsabilidade
- ✓ Fácil de testar cada componente
- ✓ Reutilizável em diferentes contextos
- ✓ Fácil de estender com novas políticas

---

## Exemplo 2: ENCAPSULAMENTO E IMUTABILIDADE

### ❌ ANTES: Estado mutável exposto
```java
public class Pagamento {
    public double saldoDevedor;  // ❌ PUBLIC!
    private static int contadorPagamento = 1;
    
    public void setSaldoDevedor(double saldoDevedor) {
        this.saldoDevedor = saldoDevedor;  // ❌ Pode ser alterado arbitrariamente
    }
}

// Uso irresponsável:
pagamento.saldoDevedor = -100;  // ❌ Quebra validação!
pagamento.setSaldoDevedor(999999);  // ❌ Sem razão!
```

### ✅ DEPOIS: Encapsulado e imutável
```java
public class Pagamento {
    private final int id;
    private final LocalDateTime dataCriacao;
    private double saldoDevedor;  // Apenas modificável internamente
    
    private static final double CUSTO_EMPRESTIMO = 15.0;
    private static final double VALOR_DIA_ATRASO = 2.0;
    
    // Constructor privado - use Factory
    Pagamento(double saldoInicial) {
        this.id = gerarId();
        this.dataCriacao = LocalDateTime.now();
        validarSaldo(saldoInicial);
        this.saldoDevedor = saldoInicial;
    }
    
    // Getters apenas para leitura
    public int getId() { return id; }
    
    public double getSaldoDevedor() {
        return saldoDevedor;
    }
    
    public boolean temDebito() {
        return saldoDevedor > 0;
    }
    
    // Métodos de negócio (mudam estado internamente)
    public void registrarEmprestimo() {
        this.saldoDevedor += CUSTO_EMPRESTIMO;
        logOperacao("Empréstimo registrado: +R$" + CUSTO_EMPRESTIMO);
    }
    
    public void registrarMulta(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Multa não pode ser negativa");
        }
        this.saldoDevedor += valor;
        logOperacao("Multa registrada: +R$" + valor);
    }
    
    public void quitarDivida() {
        if (!temDebito()) {
            throw new DebiatoInexistenteException("Não há débitos");
        }
        double valorQuitado = this.saldoDevedor;
        this.saldoDevedor = 0;
        logOperacao("Débito quitado: -R$" + valorQuitado);
    }
    
    public ReceitoPagamento obterReciboPagamento() {
        return new ReceitaPagamento(
            this.id,
            this.dataCriacao,
            LocalDateTime.now(),
            this.saldoDevedor
        );
    }
    
    private void validarSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        }
    }
    
    private void logOperacao(String mensagem) {
        System.out.printf("[%s] %s%n", LocalDateTime.now(), mensagem);
    }
    
    // Sem setters! Estado é modificado apenas por operações específicas
}

// Factory para criar Pagamentos
public class PagamentoFactory {
    public static Pagamento criar() {
        return new Pagamento(0.0);  // Começa sem saldo
    }
    
    public static Pagamento criar(double saldoInicial) {
        return new Pagamento(saldoInicial);
    }
}
```

---

## Exemplo 3: ELIMINAR `instanceof` COM POLIMORFISMO

### ❌ ANTES: Verificação de tipo
```java
public class UsuarioDAO {
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, tipo, saldo_devedor) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            
            // ❌ PROBLEMA: instanceof acoplado
            if (usuario instanceof Aluno) {
                stmt.setString(2, "ALUNO");
            } else if (usuario instanceof Professor) {
                stmt.setString(2, "PROFESSOR");
            } else {
                stmt.setString(2, "DESCONHECIDO");
            }
            
            stmt.executeDouble(3, usuario.getSaldo().getSaldoDevedor());
            stmt.executeUpdate();
        }
    }
    
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = ...; PreparedStatement stmt = ...) {
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                
                // ❌ PROBLEMA: Lógica de construção no DAO
                Usuario u = tipo.equals("ALUNO") ? 
                    new Aluno(rs.getString("nome")) : 
                    new Professor(rs.getString("nome"));
                
                u.setId(rs.getInt("id"));
                return u;
            }
        }
        return null;
    }
}

// ❌ Problema: Adicionar novo tipo requer modificar DAO em vários pontos!
```

### ✅ DEPOIS: Polimorfismo em ação
```java
// 1. Interface que CADA tipo implementa
public abstract class Usuario {
    public abstract String obterTipo();
    public abstract PoliticaEmprestimo obterPolitica();
    public abstract void salvarDadosEspecificos(PreparedStatement stmt);
}

public class Aluno extends Usuario {
    @Override
    public String obterTipo() {
        return "ALUNO";
    }
    
    @Override
    public PoliticaEmprestimo obterPolitica() {
        return new PoliticaAlunoImpl();
    }
    
    @Override
    public void salvarDadosEspecificos(PreparedStatement stmt) throws SQLException {
        // Aluno tem comportamento específico aqui (se necessário)
    }
}

public class Professor extends Usuario {
    @Override
    public String obterTipo() {
        return "PROFESSOR";
    }
    
    @Override
    public PoliticaEmprestimo obterPolitica() {
        return new PoliticaProfessorImpl();
    }
}

// 2. DAO LIMPO - sem instanceof
public class UsuarioRepository implements Repository<Usuario, Integer> {
    private final Connection connection;
    private final UsuarioFactory factory;
    
    public UsuarioRepository(Connection connection, UsuarioFactory factory) {
        this.connection = connection;
        this.factory = factory;
    }
    
    @Override
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, tipo, saldo_devedor) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.obterTipo());  // ✓ Polimórfico!
            stmt.setDouble(3, usuario.getSaldo().getSaldoDevedor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao salvar usuário", e);
        }
    }
    
    @Override
    public Usuario buscarPorId(Integer id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // ✓ Factory encapsula criação
                return factory.criar(
                    rs.getString("tipo"),
                    rs.getString("nome"),
                    rs.getDouble("saldo_devedor")
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao buscar usuário", e);
        }
        
        return null;
    }
}

// 3. Factory encapsula criação
public interface UsuarioFactory {
    Usuario criar(String tipo, String nome, double saldo);
}

public class UsuarioFactoryImpl implements UsuarioFactory {
    @Override
    public Usuario criar(String tipo, String nome, double saldo) {
        return switch(tipo.toUpperCase()) {
            case "ALUNO" -> new Aluno(nome, saldo);
            case "PROFESSOR" -> new Professor(nome, saldo);
            default -> throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        };
    }
}
```

**Vantagem OCP**:
- ✓ Para adicionar novo tipo (ex: Visitante), APENAS cria nova classe Visitante
- ✓ Atualiza Factory em um lugar
- ✓ DAO fica intocado!

---

## Exemplo 4: FACTORY PATTERN

### ❌ ANTES: Lógica de criação espalhada
```java
// Em menuGeral.java - View contaminada com lógica
public void menuUsuario() {
    System.out.println("Você é aluno ou professor?");
    String subUsuario = sc.nextLine();
    
    if (subUsuario.equalsIgnoreCase("aluno")) {
        System.out.println("Digite seu nome: ");
        String nomeAluno = sc.nextLine();
        
        // ❌ Criação direta na View!
        Usuario alunoNovo = new Aluno(nomeAluno);
        info.adicionarUsuario(alunoNovo);
        
        System.out.println("Aluno cadastrado, ID: " + alunoNovo.getId());
    } else {
        // ... código duplicado ...
    }
}
```

### ✅ DEPOIS: Factory centralizado
```java
// 1. Contrato da Factory
public interface UsuarioFactory {
    Usuario criarAluno(String nome);
    Usuario criarProfessor(String nome);
    Usuario criar(String tipo, String nome) throws TipoUsuarioInvalidoException;
}

// 2. Implementação (encapsula lógica)
public class UsuarioFactoryImpl implements UsuarioFactory {
    private final UsuarioRepository repository;
    private final Logger logger;
    
    public UsuarioFactoryImpl(UsuarioRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    
    @Override
    public Usuario criarAluno(String nome) {
        validarNome(nome);
        Usuario aluno = new Aluno(nome);
        repository.salvar(aluno);
        logger.info("Aluno criado: {} (ID: {})", nome, aluno.getId());
        return aluno;
    }
    
    @Override
    public Usuario criarProfessor(String nome) {
        validarNome(nome);
        Usuario professor = new Professor(nome);
        repository.salvar(professor);
        logger.info("Professor criado: {} (ID: {})", nome, professor.getId());
        return professor;
    }
    
    @Override
    public Usuario criar(String tipo, String nome) throws TipoUsuarioInvalidoException {
        return switch(tipo.toLowerCase()) {
            case "aluno" -> criarAluno(nome);
            case "professor" -> criarProfessor(nome);
            default -> throw new TipoUsuarioInvalidoException("Tipo inválido: " + tipo);
        };
    }
    
    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }
}

// 3. VIEW LIMPA - apenas interação
public class MenuUsuario {
    private final UsuarioFactory usuarioFactory;
    private final Scanner scanner;
    
    public MenuUsuario(UsuarioFactory usuarioFactory) {
        this.usuarioFactory = usuarioFactory;
        this.scanner = new Scanner(System.in);
    }
    
    public void cadastrarUsuario() {
        System.out.println("Tipo (aluno/professor): ");
        String tipo = scanner.nextLine();
        
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        
        try {
            Usuario usuario = usuarioFactory.criar(tipo, nome);
            System.out.printf("✓ %s cadastrado com ID: %d%n", nome, usuario.getId());
        } catch (TipoUsuarioInvalidoException e) {
            System.out.println("✗ Tipo inválido: " + e.getMessage());
        }
    }
}
```

---

## Exemplo 5: GENERICS COM REPOSITORY PATTERN

### ❌ ANTES: DAOs duplicados
```java
public class LivroDAO {
    public void salvar(Livro livro) { ... }
    public Livro buscarPorId(int id) { ... }
    public List<Livro> listarTodos() { ... }
}

public class UsuarioDAO {
    public void salvar(Usuario usuario) { ... }
    public Usuario buscarPorId(int id) { ... }
    public List<Usuario> listarTodos() { ... }
}

// ❌ Mesma estrutura repetida!
```

### ✅ DEPOIS: Repository genérico
```java
// 1. Contrato genérico
public interface Repository<T, ID> {
    void salvar(T entidade);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void atualizar(T entidade);
    void deletar(ID id);
}

// 2. Implementação base comum
public abstract class BaseRepository<T, ID> implements Repository<T, ID> {
    protected final Connection connection;
    
    public BaseRepository(Connection connection) {
        this.connection = connection;
    }
    
    protected void executarUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            preencherParametros(stmt, params);
            stmt.executeUpdate();
        }
    }
    
    protected void preencherParametros(PreparedStatement stmt, Object... params) 
        throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}

// 3. Livro Repository
public class LivroRepository extends BaseRepository<Livro, Integer> {
    public LivroRepository(Connection connection) {
        super(connection);
    }
    
    @Override
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)";
        executarUpdate(sql, livro.getTitulo(), livro.getAutor(), livro.isDisponivel());
    }
    
    @Override
    public Livro buscarPorId(Integer id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        // ... implementação ...
        return null;  // Livro encontrado
    }
    
    @Override
    public List<Livro> listarTodos() {
        String sql = "SELECT * FROM livros";
        // ... implementação ...
        return new ArrayList<>();
    }
    
    @Override
    public void atualizar(Livro livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, disponivel = ? WHERE id = ?";
        // ... implementação ...
    }
    
    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        executarUpdate(sql, id);
    }
}

// 4. Usuario Repository
public class UsuarioRepository extends BaseRepository<Usuario, Integer> {
    public UsuarioRepository(Connection connection) {
        super(connection);
    }
    
    @Override
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, tipo, saldo_devedor) VALUES (?, ?, ?)";
        executarUpdate(sql, usuario.getNome(), usuario.obterTipo(), 
                      usuario.getSaldo().getSaldoDevedor());
    }
    
    // ... outros métodos ...
}

// 5. Uso - sem duplicação
Repository<Livro, Integer> livroRepo = new LivroRepository(connection);
Repository<Usuario, Integer> usuarioRepo = new UsuarioRepository(connection);

livroRepo.salvar(novoLivro);
usuarioRepo.salvar(novoUsuario);
```

**Benefícios**:
- ✓ Sem duplicação de código
- ✓ Fácil manutenção
- ✓ Padrão consistente
- ✓ Type-safe

---

## Exemplo 6: EXCEÇÕES CUSTOMIZADAS

### ❌ ANTES: System.out.println para erros
```java
public void emprestarLivro(int idLivro, int idUsuario) {
    Livro livroEncontrado = bucarLivroPorId(idLivro);
    Usuario usuarioEncontrado = buscarUsuariosPorId(idUsuario);
    
    if (livroEncontrado != null && usuarioEncontrado != null) {
        if (!livroEncontrado.isDisponivel()) {
            System.out.println("Livro ja está emprestado!");  // ❌ Erro perdido
        }
    } else {
        System.out.println("Livro ou usuário não encontrado!");  // ❌ Erro genérico
    }
}

// Chamador não sabe o que aconteceu!
biblioteca.emprestarLivro(1, 2);  // Sucesso ou erro?
```

### ✅ DEPOIS: Exceções estruturadas
```java
// Exceções customizadas
public class BibliotecaException extends Exception {
    public BibliotecaException(String mensagem) {
        super(mensagem);
    }
    
    public BibliotecaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

public class LivroNaoEncontradoException extends BibliotecaException {
    public LivroNaoEncontradoException(int id) {
        super("Livro com ID " + id + " não encontrado");
    }
}

public class UsuarioNaoEncontradoException extends BibliotecaException {
    public UsuarioNaoEncontradoException(int id) {
        super("Usuário com ID " + id + " não encontrado");
    }
}

public class LivroIndisponıvelException extends BibliotecaException {
    public LivroIndisponıvelException(String mensagem) {
        super(mensagem);
    }
}

public class LimiteEmprestimoException extends BibliotecaException {
    private final double limiteSaldo;
    private final double saldoAtual;
    
    public LimiteEmprestimoException(double limiteSaldo, double saldoAtual) {
        super(String.format("Limite de R$%.2f atingido (Saldo: R$%.2f)", 
              limiteSaldo, saldoAtual));
        this.limiteSaldo = limiteSaldo;
        this.saldoAtual = saldoAtual;
    }
    
    public double getLimiteSaldo() { return limiteSaldo; }
    public double getSaldoAtual() { return saldoAtual; }
}

// Serviço com exceções apropriadas
public class ServicoEmprestimo {
    public void emprestarLivro(int idLivro, int idUsuario) 
        throws LivroNaoEncontradoException, UsuarioNaoEncontradoException, 
               LivroIndisponıvelException, LimiteEmprestimoException {
        
        Livro livro = buscarLivroPorId(idLivro);
        if (livro == null) {
            throw new LivroNaoEncontradoException(idLivro);
        }
        
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException(idUsuario);
        }
        
        if (!livro.isDisponivel()) {
            throw new LivroIndisponıvelException("Livro já está emprestado");
        }
        
        if (usuario.getSaldo().getSaldoDevedor() >= usuario.getLimiteSaldo()) {
            throw new LimiteEmprestimoException(
                usuario.getLimiteSaldo(),
                usuario.getSaldo().getSaldoDevedor()
            );
        }
        
        // Executar empréstimo...
    }
}

// Chamador trata cada exceção apropriadamente
try {
    servicoEmprestimo.emprestarLivro(1, 2);
    System.out.println("✓ Empréstimo realizado com sucesso!");
    
} catch (LivroNaoEncontradoException e) {
    logger.warn("Livro não encontrado: {}", e.getMessage());
    System.out.println("✗ " + e.getMessage());
    
} catch (UsuarioNaoEncontradoException e) {
    logger.warn("Usuário não encontrado: {}", e.getMessage());
    System.out.println("✗ " + e.getMessage());
    
} catch (LivroIndisponıvelException e) {
    logger.info("Empréstimo negado: {}", e.getMessage());
    System.out.println("ℹ " + e.getMessage());
    
} catch (LimiteEmprestimoException e) {
    logger.info("Limite de empréstimo atingido para usuário");
    System.out.printf("ℹ Limite: R$%.2f | Saldo: R$%.2f%n", 
                      e.getLimiteSaldo(), e.getSaldoAtual());
}
```

**Benefícios**:
- ✓ Erros são explícitos e estruturados
- ✓ Chamador pode tratar diferentemente cada erro
- ✓ Stack trace útil para debug
- ✓ Logging apropriado

---

## Exemplo 7: COMPOSIÇÃO SOBRE HERANÇA (STRATEGY PATTERN)

### ❌ ANTES: Hierarquia rígida
```java
public abstract class Usuario { ... }

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

// ❌ E se precisar de: Visitante, Pesquisador, etc?
// ❌ A hierarquia fica muito grande e rígida
```

### ✅ DEPOIS: Strategy + Composição
```java
// 1. Contrato de política
public interface PoliticaEmprestimo {
    double getLimiteSaldo();
    int getLimiteLivros();
    boolean podeEmprestar(Usuario usuario);
    void aplicarRestricoes(Usuario usuario);
}

// 2. Implementações de política
public class PoliticaAluno implements PoliticaEmprestimo {
    @Override
    public double getLimiteSaldo() { return 15.0; }
    
    @Override
    public int getLimiteLivros() { return 3; }
    
    @Override
    public boolean podeEmprestar(Usuario usuario) {
        return usuario.getSaldo().getSaldoDevedor() < getLimiteSaldo() &&
               usuario.getLivrosEmprestados().size() < getLimiteLivros();
    }
    
    @Override
    public void aplicarRestricoes(Usuario usuario) {
        // Aplicar regras específicas se necessário
        if (usuario.getLivrosEmprestados().size() >= getLimiteLivros()) {
            logger.warn("Aluno {} atingiu limite de livros", usuario.getNome());
        }
    }
}

public class PoliticaProfessor implements PoliticaEmprestimo {
    @Override
    public double getLimiteSaldo() { return 100.0; }
    
    @Override
    public int getLimiteLivros() { return 10; }
    
    @Override
    public boolean podeEmprestar(Usuario usuario) {
        return usuario.getSaldo().getSaldoDevedor() < getLimiteSaldo();
    }
    
    @Override
    public void aplicarRestricoes(Usuario usuario) {
        // Professores têm menos restrições
    }
}

// Adicionar nova política é trivial:
public class PoliticaVisitante implements PoliticaEmprestimo {
    @Override
    public double getLimiteSaldo() { return 5.0; }
    
    @Override
    public int getLimiteLivros() { return 1; }
    
    @Override
    public boolean podeEmprestar(Usuario usuario) {
        return usuario.getSaldo().getSaldoDevedor() < getLimiteSaldo();
    }
    
    @Override
    public void aplicarRestricoes(Usuario usuario) {
        // Visitantes só podem uma semana
    }
}

// 3. Usuário usa estratégia por composição
public class Usuario {
    private final String nome;
    private final PoliticaEmprestimo politica;  // Injetada
    private final List<Livro> livrosEmprestados;
    private final Pagamento saldo;
    
    public Usuario(String nome, PoliticaEmprestimo politica) {
        this.nome = nome;
        this.politica = politica;
        this.livrosEmprestados = new ArrayList<>();
        this.saldo = new Pagamento();
    }
    
    public boolean podeEmprestar() {
        return politica.podeEmprestar(this);
    }
    
    public double getLimiteSaldo() {
        return politica.getLimiteSaldo();
    }
    
    public int getLimiteLivros() {
        return politica.getLimiteLivros();
    }
}

// 4. Factory cria usuário com política apropriada
public class UsuarioFactory {
    public Usuario criarAluno(String nome) {
        return new Usuario(nome, new PoliticaAluno());
    }
    
    public Usuario criarProfessor(String nome) {
        return new Usuario(nome, new PoliticaProfessor());
    }
    
    public Usuario criarVisitante(String nome) {
        return new Usuario(nome, new PoliticaVisitante());
    }
}

// 5. Uso - simples e flexível!
Usuario aluno = usuarioFactory.criarAluno("João");
Usuario professor = usuarioFactory.criarProfessor("Dr. Silva");
Usuario visitante = usuarioFactory.criarVisitante("Maria");

// Todas têm a mesma interface, mas comportamentos diferentes
if (aluno.podeEmprestar()) { ... }
if (professor.podeEmprestar()) { ... }
if (visitante.podeEmprestar()) { ... }
```

**Vantagens sobre herança**:
- ✓ Fácil adicionar novas políticas sem modificar classe de negócio
- ✓ Políticas podem ser trocadas em runtime
- ✓ Sem "explosão de hierarquia"
- ✓ Mais flexível e testável

---

## Exemplo 8: DEPENDENCY INJECTION

### ❌ ANTES: Dependências hardcoded
```java
public class MenuPrincipal {
    private Biblioteca biblioteca = new Biblioteca();  // ❌ Criação acoplada
    private MenuGeral menuGeral = new MenuGeral(biblioteca);  // ❌ Novo
    private LivroDAO livroDAO = new LivroDAO();  // ❌ Novo sempre
    
    public void executar() {
        // Difícil testar - LivroDAO tenta conectar BD real
        menuGeral.menuLivro();
    }
}

// ❌ Em testes, imposivel mockr LivroDAO!
```

### ✅ DEPOIS: Dependency Injection
```java
// 1. Componentes com injeção
public class MenuPrincipal {
    private final Biblioteca biblioteca;
    private final MenuGeral menuGeral;
    
    // Constructor injection
    public MenuPrincipal(Biblioteca biblioteca, MenuGeral menuGeral) {
        this.biblioteca = Objects.requireNonNull(biblioteca);
        this.menuGeral = Objects.requireNonNull(menuGeral);
    }
    
    public void executar() {
        menuGeral.exibirMenuPrincipal();
    }
}

// 2. Factory centralizado ou container de DI
public class ApplicationBootstrap {
    public static Biblioteca criarBiblioteca() {
        return new Biblioteca();
    }
    
    public static MenuGeral criarMenuGeral(Biblioteca biblioteca) {
        LivroDAO livroDAO = criarLivroDAO();
        return new MenuGeral(biblioteca, livroDAO);
    }
    
    public static LivroDAO criarLivroDAO() {
        DatabaseConfig config = criarDatabaseConfig();
        return new LivroDAO(config);
    }
    
    public static DatabaseConfig criarDatabaseConfig() {
        return new DatabaseConfig(
            "jdbc:mysql://localhost:3306/biblioteca_db",
            "root",
            "admin123"
        );
    }
    
    public static MenuPrincipal criarApp() {
        Biblioteca biblioteca = criarBiblioteca();
        MenuGeral menuGeral = criarMenuGeral(biblioteca);
        return new MenuPrincipal(biblioteca, menuGeral);
    }
}

// 3. Uso na main
public class Main {
    public static void main(String[] args) {
        MenuPrincipal app = ApplicationBootstrap.criarApp();
        app.executar();
    }
}

// 4. Em testes - mockamos dependências
public class MenuGeraliTest {
    private Biblioteca bibliotecaMock;
    private MenuGeral menu;
    
    @Before
    public void setUp() {
        bibliotecaMock = mock(Biblioteca.class);
        menu = new MenuGeral(bibliotecaMock);
    }
    
    @Test
    public void testarCadastroUsuario() {
        // Sem conectar ao BD real!
        when(bibliotecaMock.adicionarUsuario(any()))
            .thenReturn(true);
        
        // Testar apenas MenuGeral
    }
}
```

---

Este documento de exemplos práticos complementa a análise conceitual. Cada exemplo mostra um padrão específico que resolve um dos problemas identificados no projeto.

**Recomendação**: Implemente estes padrões gradualmente, começando pelos mais críticos (1, 2, 3).
