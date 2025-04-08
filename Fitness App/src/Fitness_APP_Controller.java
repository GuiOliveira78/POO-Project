import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

 public class Fitness_APP_Controller implements Serializable{
    private Fitness_APP app;
    private TerminalManipulation tm;
    private transient Scanner scanner;
    
    public Fitness_APP_Controller(){
        this.app = new Fitness_APP();
        this.tm = new TerminalManipulation();
        this.scanner = new Scanner(System.in);
    }
        
    public void run() throws IOException, ClassNotFoundException {
        int i = 0;
        while (i == 0) {
            menu();
            
            int opcao = tm.getOpcao();
            switch (opcao) {
                case 1:
                    menuUser();
                    break;
                case 2:
                    menuAtividade();
                    break;
                case 3:
                    menuPlano();
                    break;
                case 4:
                    menuEstatisticas();
                    break;
                case 5:
                    gravaFicheiro();
                    break;
                case 6:
                    carregaFicheiro();
                    break;
                case 7:
                    mudarData();
                    break;
                case 8:
                    i = 1;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Selecione uma nova opção.");
                    tm.sleep(2000);
                    break;
            }
        }
        scanner.close();
    }

    ///////////////////////////////////////////////////
    // Menus
    ///////////////////////////////////////////////////

    // Menu Inicial
    public void menu() {
        tm.clearTerminal();
        System.out.println("Bem-vindo à sua aplicação de Fitness!");
        System.out.println("\n");
        System.out.println("1 - Menu Utilizador");
        System.out.println("2 - Menu Atividade");
        System.out.println("3 - Menu Planos de Treino");
        System.out.println("4 - Estatísticas");
        System.out.println("5 - Guardar dados");
        System.out.println("6 - Carregar dados");
        System.out.println("7 - Mudar data");
        System.out.println("8 - Sair");
        System.out.println("\n");
    }

    // Menu User
    public void menuUser(){
        int i = 0;
        while(i == 0){
            tm.clearTerminal();
            System.out.println("Menu Utilizador");
            System.out.println("\n");
            System.out.println("1 - Registar utilizador.");
            System.out.println("2 - Ver lista de utilizadores.");
            System.out.println("3 - Ver informação de um utilizador.");
            System.out.println("4 - Remover utilizador.");
            System.out.println("5 - Voltar.");
            System.out.println("\n");

            int opcao = tm.getOpcao();
            switch (opcao) {
                case 1:
                    app.regUtilizador();
                    break;
                case 2:
                    app.printUtilizadores();
                    break;
                case 3:
                    app.printUtilizador();
                    break;
                case 4:
                    app.removerUtilizador();
                    break;
                case 5:
                    i = 1;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Selecione uma nova opção.");
                    tm.sleep(2000);
                    break;
            }
        }
    }

    // Menu Atividade
    public void menuAtividade(){
        int i = 0;
        while(i == 0){
            tm.clearTerminal();
            System.out.println("Menu Atividade");
            System.out.println("\n");
            System.out.println("1 - Registar atividade.");
            System.out.println("2 - Ver lista de atividades.");
            System.out.println("3 - Ver informação de uma atividade.");
            System.out.println("4 - Remover atividade.");
            System.out.println("5 - Voltar.");
            System.out.println("\n");

            int opcao = tm.getOpcao();
            switch (opcao) {
                case 1:
                    app.regAtividade();
                    break;
                case 2:
                    app.printAtividades();
                    break;
                case 3:
                    app.printAtividade();
                    break;
                case 4:
                    app.removerAtividade();
                    break;
                case 5:
                    i = 1;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Selecione uma nova opção.");
                    tm.sleep(2000);
                    break;
            }
        }
    }

    // Menu Planos de Treino
    public void menuPlano(){
        int i = 0;
        while(i == 0){
            tm.clearTerminal();
            System.out.println("Menu Planos de Treino");
            System.out.println("\n");
            System.out.println("1 - Criar Plano de Treino.");
            System.out.println("2 - Ver lista de Planos de Treino.");
            System.out.println("3 - Ver informação de um Plano de Treino.");
            System.out.println("4 - Remover Plano de Treino.");
            System.out.println("5 - Voltar.");
            System.out.println("\n");

            int opcao = tm.getOpcao();
            switch (opcao) {
                case 1:
                    app.regPlano();
                    break;
                case 2:
                    app.printPlanos();
                    break;
                case 3:
                    app.printPlano();
                    break;
                case 4:
                    app.removerPlano();
                    break;
                case 5:
                    i = 1;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Selecione uma nova opção.");
                    tm.sleep(2000);
                    break;
            }
        }
    }

    // Menu Estatísticas
    public void menuEstatisticas(){
        int i = 0;
        while(i == 0){
            tm.clearTerminal();
            System.out.println("Menu Estatísticas");
            System.out.println("\n");
            System.out.println("1 - Utilizador que mais calorias dispendeu num período.");
            System.out.println("2 - Utilizador que mais calorias dispendeu desde sempre.");
            System.out.println("3 - Utilizador que mais actividades realizou num período.");
            System.out.println("4 - Utilizador que mais actividades realizou desde sempre.");
            System.out.println("5 - Tipo de atividade mais realizada.");
            System.out.println("6 - Kilómetros que um utilizador realizou num período.");
            System.out.println("7 - Kilómetros que um utilizador realizou desde sempre.");
            System.out.println("8 - Altimetria que um utilizador totalizou num período.");
            System.out.println("9 - Altimetria que um utilizador totalizou desde sempre.");
            System.out.println("10 - Plano de treino mais exigente em função do dispêndio de calorias proposto.");
            System.out.println("11 - Listar as actividades de um utilizador.");
            System.out.println("12 - Voltar.");
            System.out.println("\n");

            int opcao = tm.getOpcao();
            switch (opcao) {
                case 1:
                    app.maisCaloriasPeriodo();
                    break;
                case 2:
                    app.maisCaloriasSempre();
                    break;
                case 3:
                    app.maisAtividadesPeriodo();
                    break;
                case 4:
                    app.maisAtividadesSempre();
                    break;
                case 5:
                    app.atvMaisRealizada();
                    break;
                case 6:
                    app.kmsPeriodo();
                    break;
                case 7:
                    app.kmsSempre();
                    break;
                case 8:
                    app.altimetriaPeriodo();
                    break;
                case 9:
                    app.altimetriaSempre();
                    break;
                case 10:
                    app.planoMaisExigente();
                    break;
                case 11:
                    app.listarAtv();
                    break;
                case 12:
                    i = 1;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    System.out.println("Selecione uma nova opção.");
                    tm.sleep(2000);
                    break;
            }
        }
    }

    // Menu Grava Ficheiro
    public void gravaFicheiro() throws FileNotFoundException, IOException {
        tm.clearTerminal();
        System.out.println("Insira o nome do ficheiro:");
        String fileName = scanner.nextLine();
        if (fileName.equals("exit")){
            System.out.println("Operação cancelada...");
            tm.sleep(1200);
            return;
        }
        app.gravaBinario(fileName);
        System.out.println("Dados guardados com sucesso...");
        tm.sleep(1200);
    }

    // Menu Carrega Ficheiro
    public void carregaFicheiro() throws ClassNotFoundException, IOException{
        while (true) {
            tm.clearTerminal();
            System.out.println("Insira o nome do ficheiro a carregar:");
            try{
                String fileName2 = scanner.nextLine();
                if (fileName2.equals("exit")){
                    System.out.println("Operação cancelada...");
                    tm.sleep(1200);
                    return;
                }
                app = app.lerBinario(fileName2);
                initScanner();
                app.setData(LocalDateTime.now());
                System.out.println("Leitura feita com sucesso...");
                tm.sleep(1200);
                break;
            } catch (InvalidClassException e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Ficheiro com formato inválido.");
                tm.sleep(2500);
            } catch (FileNotFoundException e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Ficheiro não encontrado.");
                tm.sleep(2500);
            } catch (IOException e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Erro na leitura do ficheiro.");
                tm.sleep(2500);
            } catch (ClassNotFoundException e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Erro na leitura do ficheiro.");
                tm.sleep(2500);
            }
        }
    }

    // Menu Mudar Data
    public void mudarData(){
        tm.clearTerminal();
        System.out.println("DATA ATUAL: " + app.getData());
        System.out.print("NOVA DATA, ");
        LocalDateTime dataNew = tm.getDataHoraTM();
        System.out.println("Tem a certeza que deseja alterar a data de " + app.getData() + " para " + dataNew + "? (S/N)");
        System.out.println("Esta ação irá recalcular as calorias para todas as atividades anteriores à data inserida.");
        String confirmacao = scanner.nextLine();
                if (confirmacao.equals("S") || confirmacao.equals("s")) {
                    app.setData(dataNew);
                    app.recalculaCalorias();
                    System.out.println("Data alterada com sucesso!");
                    tm.sleep(1500);
                }
    }

    ///////////////////////////////////////////////////

    // Inicializa o scanner
    public void initScanner() {
        this.scanner = new Scanner(System.in);
        this.tm.init();
        this.app.initScanner();
    }

}
