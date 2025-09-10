import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static class Funcionario {
    private String nome;
    private LocalDate dataNascimento;
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", salario=" + salario +
                ", funcao='" + funcao + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remover o funcionário "João"
        System.out.println("--- Removendo o funcionário João ---");
        funcionarios.removeIf(f -> f.getNome().equals("João"));
        System.out.println("Funcionário João removido com sucesso.");
        System.out.println();

        // Imprimir todos os funcionários
        System.out.println("--- Lista de todos os funcionários ---");
        funcionarios.forEach(System.out::println);
        System.out.println();

        // Aumento de 10% no salário
        System.out.println("--- Aumento de 10% no salário ---");
        BigDecimal aumento = new BigDecimal("1.10");
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(aumento);
            f.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        }
        System.out.println("Salários atualizados. Nova lista:");
        funcionarios.forEach(System.out::println);
        System.out.println();

        // Agrupar funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir funcionários agrupados por função
        System.out.println("--- Funcionários agrupados por função ---");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println("  - " + f.getNome()));
        });
        System.out.println();
        
        // Imprimir funcionários que fazem aniversário nos meses 10 e 12
        System.out.println("--- Aniversariantes de Outubro e Dezembro ---");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);
        System.out.println();

        // Imprimir o funcionário com a maior idade
        System.out.println("--- Funcionário com a maior idade ---");
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            LocalDate hoje = LocalDate.now();
            Period idade = Period.between(maisVelho.getDataNascimento(), hoje);
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade.getYears());
        }
        System.out.println();

        // Imprimir lista de funcionários por ordem alfabética
        System.out.println("--- Funcionários em ordem alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);
        System.out.println();

        // Imprimir o total dos salários
        System.out.println("--- Total dos salários ---");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        System.out.println("Total dos salários: " + df.format(totalSalarios));
        System.out.println();

        // Imprimir quantos salários mínimos cada funcionário ganha
        System.out.println("--- Salários mínimos por funcionário ---");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + salariosMinimos + " salários mínimos");
        });
    }
}
}
