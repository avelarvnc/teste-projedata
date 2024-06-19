import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import pessoa.*;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 – Imprimir todos os funcionários com todas suas informações
        imprimirFuncionarios(funcionarios);

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarios.forEach(funcionario -> 
            funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10")))
        );

        // 3.5 – Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários, agrupados por função.
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        imprimirAniversariantes(funcionarios, 10);
        imprimirAniversariantes(funcionarios, 12);

        // 3.9 – Imprimir o funcionário com a maior idade
        imprimirFuncionarioMaisVelho(funcionarios);

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        imprimirFuncionarios(funcionariosOrdenados);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + formatarValor(totalSalarios));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário.
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formato = "| %-10s | %-12s | %-12s | %-12s |%n";
        System.out.format("+------------+--------------+--------------+--------------+%n");
        System.out.format("| Nome       | Data Nasc.   | Salário      | Função       |%n");
        System.out.format("+------------+--------------+--------------+--------------+%n");
        for (Funcionario funcionario : funcionarios) {
            System.out.format(formato, 
                funcionario.getNome(), 
                funcionario.getDataNascimento().format(formatter), 
                formatarValor(funcionario.getSalario()), 
                funcionario.getFuncao());
        }
        System.out.format("+------------+--------------+--------------+--------------+%n");
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, funcionarios) -> {
            System.out.println("Função: " + funcao);
            imprimirFuncionarios(funcionarios);
        });
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int mes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Aniversariantes do mês " + mes + ":");
        funcionarios.stream()
            .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == mes)
            .forEach(funcionario -> {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                System.out.println();
            });
    }

    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario funcionarioMaisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Funcionário mais velho: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade + " anos.");
    }

    private static String formatarValor(BigDecimal valor) {
        return String.format("%,.2f", valor).replace(',', 'X').replace('.', ',').replace('X', '.');
    }
}
