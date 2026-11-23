
import java.util.Scanner;

public class sistemacademico{

    // Função auxiliar para capturar e validar a nota entre 0 e 10
    public static double obterNotaValida(Scanner entrada) {
        double valorNota = -1.0;

        while (valorNota < 0.0 || valorNota > 10.0) {
            System.out.print("Informe a nota (0 a 10): ");
            String textoDigitado = entrada.nextLine().trim();

            // Padroniza o separador decimal caso o usuário digite vírgula
            textoDigitado = textoDigitado.replace(",", ".");

            try {
                valorNota = Double.parseDouble(textoDigitado);

                if (valorNota < 0.0 || valorNota > 10.0) {
                    System.out.println("AVISO: A nota precisa ser um valor entre 0 e 10.");
                }
            } catch (NumberFormatException erro) {
                System.out.println("AVISO: Entrada inválida! Insira apenas números.");
                valorNota = -1.0; // Mantém o laço ativo para nova tentativa
            }
        }
        return valorNota;
    }

    // Função auxiliar para validar a presença (somente S ou N)
    public static String obterPresencaValida(Scanner entrada) {
        String status;
        do {
            System.out.print("Presença (S - Presente / N - Ausente): ");
            status = entrada.nextLine().trim().toUpperCase();

            if (!status.equals("S") && !status.equals("N")) {
                System.out.println("AVISO: Opção inválida! Responda apenas com S ou N.");
            }
        } while (!status.equals("S") && !status.equals("N"));

        return status;
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // DEFINIÇÃO DAS CREDENCIAIS DE ACESSO
        final String LOGIN_PADRAO = "professor";
        final String CHAVE_PADRAO = "12345";
        final int PAUSA_SEGURANCA = 10000; // 10 segundos em milissegundos

        int errosLogin = 0;
        boolean autenticado = false;

        System.out.println("----------------------------------------");
        System.out.println("   PORTAL ACADÊMICO - ACESSO RESTRITO   ");
        System.out.println("----------------------------------------");

        // ETAPA DE AUTENTICAÇÃO DO DOCENTE
        while (!autenticado) {
            System.out.print("\nUsuário: ");
            String loginInformado = entrada.nextLine();

            System.out.print("Senha: ");
            String chaveInformada = entrada.nextLine();

            if (loginInformado.equals(LOGIN_PADRAO) && chaveInformada.equals(CHAVE_PADRAO)) {
                autenticado = true;
                System.out.println("\n----------------------------------------");
                System.out.println("   ACESSO AUTORIZADO! BEM-VINDO!        ");
                System.out.println("----------------------------------------");
            } else {
                errosLogin++;
                System.out.println("\n----------------------------------------");
                System.out.println("   ACESSO NEGADO! DADOS INCORRETOS.     ");
                System.out.println("----------------------------------------");

                if (errosLogin == 3) {
                    System.out.println("\nBLOQUEIO PREVENTIVO ATIVADO!");
                    System.out.println("Aguarde 10 segundos para realizar uma nova tentativa...");

                    try {
                        Thread.sleep(PAUSA_SEGURANCA);
                    } catch (InterruptedException erroPausa) {
                        System.out.println("A contagem de tempo foi interrompida.");
                        Thread.currentThread().interrupt();
                        entrada.close();
                        return;
                    }

                    System.out.println("\nSistema liberado! Você já pode tentar novamente.");
                    errosLogin = 0; // Zera o contador após o tempo de espera
                } else {
                    System.out.println("Você ainda possui " + (3 - errosLogin) + " tentativa(s).");
                }
            }
        }

        // DECLARAÇÃO DOS VETORES PARA ARMAZENAMENTO DOS DADOS
        final int QTD_ALUNOS = 3;
        String[] listaNomes = new String[QTD_ALUNOS];
        String[] listaPresencas = new String[QTD_ALUNOS];
        double[] listaNotas = new double[QTD_ALUNOS];

        System.out.println("\n----------------------------------------");
        System.out.println("   MÓDULO DE LANÇAMENTO DE NOTAS        ");
        System.out.println("----------------------------------------");

        // LAÇO PARA CADASTRO DOS 3 ESTUDANTES
        for (int indice = 0; indice < QTD_ALUNOS; indice++) {
            System.out.println("\nRegistro do " + (indice + 1) + "º estudante:");

            System.out.print("Nome completo: ");
            listaNomes[indice] = entrada.nextLine();

            listaPresencas[indice] = obterPresencaValida(entrada);
            listaNotas[indice] = obterNotaValida(entrada);

            System.out.println("-> Registro salvo com sucesso!");
        }

        // PROCESSAMENTO E EXIBIÇÃO DO BOLETIM DA TURMA
        System.out.println("\n----------------------------------------");
        System.out.println("   RELATÓRIO FINAL DE DESEMPENHO        ");
        System.out.println("----------------------------------------");

        for (int indice = 0; indice < QTD_ALUNOS; indice++) {
            String descricaoPresenca = listaPresencas[indice].equals("S") ? "Presente" : "Ausente";
            String statusFinal;

            // Critério de aprovação: nota mínima 7.0 e presença confirmada
            if (listaNotas[indice] >= 7.0 && listaPresencas[indice].equals("S")) {
                statusFinal = "APROVADO";
            } else {
                statusFinal = "ATENÇÃO";
            }

            System.out.println("\nEstudante: " + listaNomes[indice]);
            System.out.println("Frequência: " + descricaoPresenca);
            System.out.printf("Média Final: %.1f%n", listaNotas[indice]);
            System.out.println("Situação: " + statusFinal);
        }

        // FINALIZAÇÃO DO PROGRAMA
        System.out.println("\n----------------------------------------");
        System.out.println("   SESSÃO ENCERRADA                     ");
        System.out.println("----------------------------------------");
        System.out.println("Professor, seus registros foram realizados.");
        System.out.println("Obrigado por utilizar o sistema!");
        System.out.println("Até a próxima!");

        entrada.close();
    }
}