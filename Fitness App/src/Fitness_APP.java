import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map.Entry;

 public class Fitness_APP implements Serializable{ 

    private Map<String, Utilizador> utilizadores;
    private Map<Integer, Atividade> atividades;
    private Map<Integer, PlanoDeTreino> planos;
    private ChronoLocalDateTime<?> data;
    private transient Scanner scanner;
    
    private TerminalManipulation tm = new TerminalManipulation();
    private Stats stats = new Stats();

    public Fitness_APP() {
        this.utilizadores = new HashMap<>();
        this.atividades = new HashMap<>();
        this.planos = new HashMap<>();
        this.data = LocalDateTime.now();
        this.scanner = new Scanner(System.in);
    }


    public ChronoLocalDateTime<?> getData(){
        return this.data;
    }

    public void setData(ChronoLocalDateTime<?> data){
        this.data = data;
        recalculaCalorias();
    }

    ///////////////////////////////////////////////////
    // Métodos do Utilizador
    ///////////////////////////////////////////////////

    // Registar Utilizador
    public void regUtilizador() {
        Utilizador u = getUserData();
        this.utilizadores.put(u.getUsername(), u);
        tm.clearTerminal();
        System.out.println("Utilizador registado com sucesso!");
        System.out.println("\n");
        System.out.println(u.toString());
        scanner.nextLine();
        tm.voltar();
    }

    public Utilizador getUserData() {
        Utilizador u = new Utilizador();
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o username: ");
            String username = scanner.nextLine();
            if (username == ""){
                System.out.println("Username vazio. Insira um username.");
                tm.sleep(1500);
                tm.clearLastTwoLines();
            } else if (utilizadores.containsKey(username)) {
                System.out.println("Username já existente!");
                System.out.println("Por favor, introduza um novo username.");
                tm.sleep(2000);
                tm.clearTerminal();
            } else {
                u.setUsername(username);
                break;
            }
        }
        System.out.println("Introduza o seu nome: ");
        u.setNome(scanner.nextLine());
        System.out.println("Introduza a sua morada: ");
        u.setMorada(scanner.nextLine());
        System.out.println("Introduza o seu email: ");
        u.setEmail(scanner.nextLine().toLowerCase());
        while (true) {
            System.out.println("Introduza a sua frequência cardíaca média: ");
            try {
                u.setFrequenciaCardiacaMedia(scanner.nextDouble());
                scanner.nextLine(); // consume the rest of the line
                break; // exit the loop if the input was valid
            } catch (InputMismatchException e) {
                System.out.println("Frequência cardíaca inválida. Insira um número inteiro ou decimal.");
                scanner.nextLine(); // consume the invalid input
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        while (true){
            System.out.println("Introduza o seu tipo de utilizador(PROFISSIONAL, AMADOR ou OCASIONAL): ");
            try {
                u.setTipoUtilizador(TipoUtilizador.valueOf(scanner.next().toUpperCase()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de utilizador inválido. Insira um dos seguintes: PROFISSIONAL, AMADOR ou OCASIONAL.");
                scanner.nextLine(); // consume the invalid input
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        return u;
    }
    
    // Print da lista de todos os utilizadores
    public void printUtilizadores(){
        tm.clearTerminal();
        System.out.println("Lista de Utilizadores: \n");
        for(Entry<String, Utilizador> entry : utilizadores.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue().getNome().toString());
        }
        tm.voltar();
    }

    // Print de um utilizador com um id específico
    public void printUtilizador() {
        tm.clearTerminal();

        System.out.println("Introduza o Username do Utilizador: ");
        String username = scanner.nextLine();
        tm.clearTerminal();
        try {
            System.out.println(utilizadores.get(username).toString());
        } catch (NullPointerException e) {
            System.out.println("Utilizador não encontrado.");
        }

        tm.voltar();

    }

    // Remover Utilizador
    public void removerUtilizador() {
        tm.clearTerminal();
        System.out.println("Introduza o Username do Utilizador: ");
        String username = scanner.nextLine();
        if (!utilizadores.containsKey(username)) {
            System.out.println("Utilizador não encontrado.");
            tm.voltar();
            return;
        }
        System.out.println("Tem a certeza que deseja remover o utilizador " + username + "? (S/N)");
        if (scanner.nextLine().equals("S")) {
            try{
                // Remoção do utilizador
                utilizadores.remove(username);
    
                // Remoção das atividades associadas ao utilizador

                List<Integer> atividadesToRemove = atividades.entrySet().stream()
                        .filter(entry -> entry.getValue().getUser().getUsername().equals(username))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                atividadesToRemove.forEach(atividades::remove);
                
                // Remoção dos planos associados ao utilizador
                List<Integer> planosToRemove = planos.entrySet().stream()
                        .filter(entry -> entry.getValue().getUser().getUsername().equals(username))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                planosToRemove.forEach(planos::remove);
    
                System.out.println("Utilizador removido com sucesso!");
                tm.sleep(2000);

            } catch(NullPointerException e){
                System.out.println("Utilizador não encontrado.");
                tm.voltar();
            }
        }
    }

    ///////////////////////////////////////////////////
    // Métodos da Atividade
    ///////////////////////////////////////////////////

    // Registar Atividade 
    public void regAtividade() {
        try{
            Atividade a = getAtvidadeData();
        this.atividades.put(a.getId(), a);
        this.utilizadores.get(a.getUser().getUsername()).addAtv(a);
        tm.clearTerminal();
        System.out.println("Atividade registada com sucesso!");
        System.out.println("\n");
        System.out.println(a.toString());
        tm.voltar();
        } catch (NullPointerException e){
            System.out.println("\nOperação cancelada.");
            tm.sleep(2000);
        }
        
    }

    public Atividade getAtvidadeData() {
        int i = 0;
        while (i == 0) {
            tm.clearTerminal();
            System.out.println("Escolha o tipo de atividade: ");

            System.out.println("1 - Distância");
            System.out.println("2 - Distância e altimetria");
            System.out.println("3 - Repetições");
            System.out.println("4 - Repetições com peso");
            int opcao = tm.getOpcao();
                switch (opcao) {
                    case 1:
                        return getAtividade_D();
                    case 2:
                        return getAtividade_DA();
                    case 3:
                        return getAtividade_R();
                    case 4:
                        return getAtividade_RCP();
                    default:
                        System.out.println("Opção inválida!");
                        System.out.println("Selecione uma nova opção.");
                        tm.sleep(2000);
                        break;
                }
            }
        return null;
    }

    public Atividade_D getAtividade_D(){
        Atividade_D a = new Atividade_D();
        int i = 0;
        while (i == 0) {
            if (atividades.containsKey(a.getId())){
                a.setId(a.getId() + 1);
            } else {
                i = 1;
            }
        }
        tm.clearTerminal();
        while (true) {
            System.out.println("Escolha uma atividade: ");
            for (Entry<Integer, String> entry : a.getTipos().entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            try {
                int opcao = tm.getOpcao();
                a.setNome(a.getTipos().get(opcao));
                break;
            } catch (NullPointerException e) {
                System.out.println("Opção inválida!");
                System.out.println("Selecione uma nova opção.");
                tm.sleep(2000);
                tm.clearLastTwoLines();
            }
        }
        a.setDataHora(tm.getDataHoraTM());
        a.setDuracao(tm.getDuracaoTM());
        while (true) {
            System.out.println("Introduza o utilizador que realiza (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return null;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                a.setUser(this.utilizadores.get(username));
                break;
            }
        }
        while (true) {
            System.out.println("Introduza a distância: ");
            try {
                a.setDistancia(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Distância inválida. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        
        scanner.nextLine();
        a.calorias(this.data);
        return a;
    }

    public Atividade_DA getAtividade_DA(){
        Atividade_DA a = new Atividade_DA();
        int i = 0;
        while (i == 0) {
            if (atividades.containsKey(a.getId())){
                a.setId(a.getId() + 1);
            } else {
                i = 1;
            }
        }
        tm.clearTerminal();
        while (true) {
            System.out.println("Escolha uma atividade: ");
            for (Entry<Integer, String> entry : a.getTipos().entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            try {
                int opcao = tm.getOpcao();
                a.setNome(a.getTipos().get(opcao));
                break;
            } catch (NullPointerException e) {
                System.out.println("Opção inválida!");
                System.out.println("Selecione uma nova opção.");
                tm.sleep(2000);
                tm.clearLastTwoLines();
            }
        }
        a.setDataHora(tm.getDataHoraTM());
        a.setDuracao(tm.getDuracaoTM());
        while (true) {
            System.out.println("Introduza o utilizador que realiza (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return null;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                a.setUser(this.utilizadores.get(username));
                break;
            }
        }
        while (true) {
            System.out.println("Introduza a distância: ");
            try {
                a.setDistancia(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Distância inválida. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        while (true) {
            System.out.println("Introduza a altimetria: ");
            try {
                a.setAltimetria(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Altimetria inválida. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        scanner.nextLine();
        a.calorias(this.data);
        return a;
    }

    public Atividade_R getAtividade_R(){
        Atividade_R a = new Atividade_R();
        int i = 0;
        while (i == 0) {
            if (atividades.containsKey(a.getId())){
                a.setId(a.getId() + 1);
            } else {
                i = 1;
            }
        }
        tm.clearTerminal();
        while (true) {
            System.out.println("Escolha uma atividade: ");
            for (Entry<Integer, String> entry : a.getTipos().entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            try {
                int opcao = tm.getOpcao();
                a.setNome(a.getTipos().get(opcao));
                break;
            } catch (NullPointerException e) {
                System.out.println("Opção inválida!");
                System.out.println("Selecione uma nova opção.");
                tm.sleep(2000);
                tm.clearLastTwoLines();
            }
        }
        a.setDataHora(tm.getDataHoraTM());
        a.setDuracao(tm.getDuracaoTM());
        while (true) {
            System.out.println("Introduza o utilizador que realiza (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return null;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                a.setUser(this.utilizadores.get(username));
                break;
            }
        }
        while (true) {
            System.out.println("Introduza o número de repetições: ");
            try{
                a.setNReps(scanner.nextInt());
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Número de repetições inválido. Insira um número inteiro.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        a.calorias(this.data);
        return a;
    }

    public Atividade_RCP getAtividade_RCP(){
        Atividade_RCP a = new Atividade_RCP();
        int i = 0;
        while (i == 0) {
            if (atividades.containsKey(a.getId())){
                a.setId(a.getId() + 1);
            } else {
                i = 1;
            }
        }
        tm.clearTerminal();
        while (true) {
            System.out.println("Escolha uma atividade: ");
            for (Entry<Integer, String> entry : a.getTipos().entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            try {
                int opcao = tm.getOpcao();
                a.setNome(a.getTipos().get(opcao));
                break;
            } catch (NullPointerException e) {
                System.out.println("Opção inválida!");
                System.out.println("Selecione uma nova opção.");
                tm.sleep(2000);
                tm.clearLastTwoLines();
            }
        }
        a.setDataHora(tm.getDataHoraTM());
        a.setDuracao(tm.getDuracaoTM());
        while (true) {
            System.out.println("Introduza o utilizador que realiza (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return null;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                a.setUser(this.utilizadores.get(username));
                break;
            }
        }
        while (true) {
            System.out.println("Introduza o número de repetições: ");
            try{
                a.setNReps(scanner.nextInt());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Número de repetições inválido. Insira um número inteiro.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        while (true) {
            System.out.println("Introduza o peso: ");
            try{
                a.setPeso(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Peso inválido. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();   
            }
        }
        scanner.nextLine();
        a.calorias(this.data);
        return a;
    }

    // Print da lista de todas as atividades
    public void printAtividades(){
        tm.clearTerminal();
        System.out.println("Lista de Atividades: \n");
        for(Entry<Integer, Atividade> entry : atividades.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue().getNome().toString());
        }
        tm.voltar();
    }
    
    // Print de uma atividade com um id específico
    public void printAtividade() {
        tm.clearTerminal();
        System.out.println("Introduza o ID da Atividade: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            tm.clearTerminal();
            System.out.println(atividades.get(id).toString());
        } catch (NullPointerException e) {
            System.out.println("Atividade não encontrada.");
        } catch (InputMismatchException e) {
            scanner.nextLine();
            tm.clearTerminal();
            System.out.println("ID inválido.");
        }
        tm.voltar();

    }

    // Remover Atividade
    public void removerAtividade() {
        tm.clearTerminal();
        System.out.println("Introduza o ID da Atividade: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            if (!atividades.containsKey(id)) {
                System.out.println("Atividade não encontrada.");
                tm.voltar();
                return;
            }
            System.out.println(
                    "Tem a certeza que deseja remover a atividade " + atividades.get(id).getNome() + "? (S/N)");
            if (scanner.nextLine().equals("S")) {
                atividades.remove(id);
                utilizadores.entrySet().stream()
                        .filter(entry -> entry.getValue().getAtividades().containsKey(id))
                        .forEach(entry -> entry.getValue().getAtividades().remove(id));
                planos.entrySet().stream()
                        .filter(entry -> entry.getValue().getAtividades().containsKey(id))
                        .forEach(entry -> entry.getValue().getAtividades().remove(id));
                System.out.println("Atividade removida com sucesso!");
                tm.sleep(2000);
            } else {
                tm.voltar();
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("ID inválido.");
            tm.voltar();
        } catch (NullPointerException e) {
            scanner.nextLine();
            System.out.println("Atividade não encontrada.");
            tm.voltar();
        }
    }

    

    ///////////////////////////////////////////////////
    // Métodos da Planos de treino
    ///////////////////////////////////////////////////

    // Criar Plano de Treino
    public void regPlano(){
        PlanoDeTreino p = getAtvPlano();
        this.planos.put(p.getId(), p);
        tm.clearTerminal();
        System.out.println("Plano de Treino criado com sucesso!");
        System.out.println("\n");
        System.out.println(p.toString());
        tm.voltar();
    }

    public PlanoDeTreino getPlanoData(){
        PlanoDeTreino p = new PlanoDeTreino();
        tm.clearTerminal();
        System.out.println("Introduza o nome do plano de treino: ");
        p.setNome(scanner.nextLine());
        System.out.print("DATA INÍCIO, ");
        p.setDataInicio(tm.getDataTM());
        System.out.print("DATA FINAL, ");
        p.setDataFim(tm.getDataTM());
        while (true) {
            System.out.println("Introduza o objetivo de calorias: ");
            try{
                p.setObjetivoCalorias(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Objetivo de calorias inválido. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        scanner.nextLine();
        while (true) {
            System.out.println("Introduza o objetivo de distância: ");
            try{
                p.setObjetivoDistancia(scanner.nextDouble());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Objetivo de distância inválido. Insira um número inteiro ou decimal.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }
        }
        scanner.nextLine();
        while (true) {
            System.out.println("Introduza a frequência semanal: ");
            try{
                p.setFrequenciaSemanal(scanner.nextInt());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Frequência semanal inválida. Insira um número inteiro.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearLastTwoLines();
            }

        }
        scanner.nextLine();
        while (true) {
            System.out.println("Introduza o utilizador que realiza (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return null;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                p.setUser(this.utilizadores.get(username));
                break;
            }
        }
        return p;
    }

    public PlanoDeTreino getAtvPlano(){
        PlanoDeTreino p = getPlanoData();
        int  i = 0;
        while (i == 0) {
            tm.clearTerminal();
            System.out.println("Deseja adicionar uma atividade com repetições ao plano? (S/N)");
            String opcao = scanner.nextLine();
            if (opcao.equals("S") || opcao.equals("s")) {
                while (true) {
                    System.out.println("Introduza quantas vezes deseja repetir a atividade: ");
                    try{
                        int repeticoes = scanner.nextInt();
                        scanner.nextLine();
                        Atividade a = getAtvidadeData();
                        p.addAtv(a);
                        atividades.put(a.getId(), a);
                        utilizadores.get(a.getUser().getUsername()).addAtv(a);
                        repeticoes--;
                        while (repeticoes > 0) {
                            Atividade b = a.clone();
                            b.setDataHora(a.getDataHora().plus(a.getDuracao()));
                            a = b.clone();
                            b.checkID(atividades);
                            p.addAtv(b);
                            atividades.put(b.getId(), b);
                            utilizadores.get(b.getUser().getUsername()).addAtv(b);
                            repeticoes--;
                        }
                        break;
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Número de Repetições inválido. Insira um número inteiro.");
                        tm.sleep(2000);
                        tm.clearLastTwoLines();
                    }
                    scanner.nextLine();
                }   

            } else {

                Atividade a = getAtvidadeData();
                a.checkID(atividades);
                p.addAtv(a);
                atividades.put(a.getId(), a);
                utilizadores.get(a.getUser().getUsername()).addAtv(a);
                atividades.put(a.getId(), a);
            }
            tm.clearTerminal();
            System.out.println("Deseja adicionar mais atividades ao plano? (S/N)");
            String opcao2 = scanner.nextLine();
            if (opcao2.equals("N") || opcao2.equals("n")) {
                i = 1;
            }
        }
        return p;
    }

    // Print da lista de todos os planos de treino
    public void printPlanos(){
        tm.clearTerminal();
        System.out.println("Lista de Planos de Treino: \n");
        for(Entry<Integer, PlanoDeTreino> entry : planos.entrySet()){
            // ID e nome do plano
            System.out.print(entry.getKey() + " - " + entry.getValue().getNome().toString());
            // Data de Início e Fim
            System.out.println(" (" + entry.getValue().getDataInicio().toString() + " - " + entry.getValue().getDataFim().toString() + ")");
        }
        tm.voltar();
    }

    // Print de um plano de treino com um id específico
    public void printPlano(){
        tm.clearTerminal();
        System.out.println("Introduza o ID do Plano de Treino: ");
        try{
            int id = scanner.nextInt();
            scanner.nextLine();
            tm.clearTerminal();
            System.out.println(planos.get(id).toString());
        } catch (NullPointerException e) {
            System.out.println("Plano de Treino não encontrado.");
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("ID inválido.");
        }
        tm.voltar();
    }

    // Remover Plano de Treino
    public void removerPlano() {
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o ID do Plano de Treino: ");
            try {
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Tem a certeza que deseja remover o plano de treino " + planos.get(id).getNome() + " de "
                        + planos.get(id).getUser().getUsername() + "? (S/N)");
                String confirmacao = scanner.nextLine();
                if (confirmacao.equals("S") || confirmacao.equals("s")) {
                    // remover atividades do plano do map das atividades
                    for (Entry<Integer, Atividade> entry : planos.get(id).getAtividades().entrySet()) {
                        atividades.remove(entry.getKey());
                    }
                    // remover atividades do plano do map das atividades do utilizador
                    for (Entry<Integer, Atividade> entry : planos.get(id).getAtividades().entrySet()) {
                        utilizadores.get(entry.getValue().getUser().getUsername()).getAtividades()
                                .remove(entry.getKey());
                    }
                    planos.remove(id);
                    System.out.println("Plano de Treino removido com sucesso!");
                    tm.sleep(2000);
                    break;
                } else {
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("ID inválido. Insira o ID de um Plano de treino existente.");
                tm.sleep(1500);
                tm.clearTerminal();;
            } catch (InputMismatchException e){
                System.out.println("ID inválido. Insira um número inteiro.");
                scanner.nextLine();
                tm.sleep(1500);
                tm.clearTerminal();;
            }
        }

    }

    //////////////////////////////////////////////////
    // Métodos de Estatísticas
    ///////////////////////////////////////////////////

    // Método que devolve o utilizador que mais calorias dispendeu num período
    public void maisCaloriasPeriodo(){
        tm.clearTerminal();
        System.out.println("Escolha o período de tempo: ");
        System.out.print("DATA INICIAL, ");
        LocalDateTime dataInicial = tm.getDataHoraTM();
        System.out.print("DATA FINAL, ");
        LocalDateTime dataFinal = tm.getDataHoraTM();

        if (dataInicial.isAfter(dataFinal)){
            System.out.println("Data final não pode ser anterior à data inicial.");
            tm.sleep(2000);
            return;
        }

        String username = stats.maisCalorias(new HashMap<>(utilizadores), dataInicial, dataFinal);
        Utilizador u = utilizadores.get(username);

        if (u == null){
            System.out.println("Não existem atividades registadas no período de tempo selecionado.");
            tm.sleep(2000);
            return;
        }

        tm.clearTerminal();
        System.out.println("O utilizador que mais calorias dispendeu no período de " 
        + dataInicial + " a " +  dataFinal + " foi: ");
        System.out.println(u.getUsername().toString() + " com " + u.getTotalCaloriasDatas(dataInicial, dataFinal) + " calorias.");
        tm.voltar();
    }

    // Método que devolve o utilizador que mais calorias dispendeu desde sempre
    public void maisCaloriasSempre(){
        String username = stats.maisCalorias(new HashMap<>(utilizadores), LocalDateTime.MIN, LocalDateTime.now());
        Utilizador u = utilizadores.get(username);
        tm.clearTerminal();
        System.out.println("O utilizador que mais calorias dispendeu desde sempre foi: \n");
        System.out.println(u.getUsername().toString() + " com " + u.getTotalCalorias() + " calorias.");

        tm.voltar();
    }

    // Método que devolve o utilizador que mais atividades praticou num período
    public void maisAtividadesPeriodo(){
        tm.clearTerminal();
        System.out.println("Escolha o período de tempo: ");
        System.out.print("DATA INICIAL, ");
        LocalDateTime dataInicial = tm.getDataHoraTM();
        System.out.print("DATA FINAL, ");
        LocalDateTime dataFinal = tm.getDataHoraTM();

        if (dataInicial.isAfter(dataFinal)){
            System.out.println("Data final não pode ser anterior à data inicial.");
            tm.sleep(2000);
            return;
        }

        Utilizador u = utilizadores.get(stats.maisAtividades(new HashMap<>(utilizadores), dataInicial, dataFinal));

        tm.clearTerminal();
        System.out.println("O utilizador que realizou mais atividades no período de "
        + dataInicial + " a " +  dataFinal + " foi: \n");
        
        System.out.println(u.getUsername().toString() + " com " + u.getAtividadesDatas(dataInicial, dataFinal) + " atividades.");
        tm.voltar();
    }

    // Método que devolve o utilizador que mais atividades praticou desde sempre
    public void maisAtividadesSempre(){
        Utilizador u = utilizadores.get(stats.maisAtividades(new HashMap<>(utilizadores), LocalDateTime.MIN, LocalDateTime.now()));
        tm.clearTerminal();
        System.out.println("O utilizador que realizou mais atividades desde sempre foi: \n");
        System.out.println(u.getUsername().toString() + " com " + u.getAtividades().size() + " atividades.");
        tm.voltar();
    }

    // Método que diz qual o tipo de actividade mais realizada
    public void atvMaisRealizada(){
        tm.clearTerminal();
        System.out.println("O tipo de atividades mais realizadas é: \n");
        String res = stats.maisRealizada(new HashMap<>(atividades));
        if (res == "Atividade_D") {
            System.out.println("Distância");
        } else if (res == "Atividade_DA") {
            System.out.println("Distância e Altimetria");
        } else if (res == "Atividade_R") {
            System.out.println("Repetições");
        } else if (res == "Atividade_RCP") {
            System.out.println("Repetições com Peso");
        } else {
            System.out.println("Empate ou não existem atividades registadas.");
        }
        tm.voltar();
    }

    // Método que diz quantos kms é que um utilizdor realizou num período
    public void kmsPeriodo(){
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o utilizador (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                System.out.println("Escolha o período de tempo: ");
                System.out.print("DATA INICIAL, ");
                LocalDateTime dataInicial = tm.getDataHoraTM();
                System.out.print("DATA FINAL, ");
                LocalDateTime dataFinal = tm.getDataHoraTM();
                
                if (dataInicial.isAfter(dataFinal)){
                    System.out.println("Data final não pode ser anterior à data inicial.");
                    tm.sleep(2000);
                    return;
                }

                double kms = stats.maisKms(new Utilizador(utilizadores.get(username)), dataInicial, dataFinal);
                
                tm.clearTerminal();
                System.out.println("O utilizador realizou " + kms + " kms no período de " 
                + dataInicial +  " a " +  dataFinal + "\n");
                tm.voltar();
                break;
            }
        }
    }

    // Método que diz quantos kms é que um utilizdor realizou desde sempre
    public void kmsSempre(){
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o utilizador (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                double kms = stats.maisKms(new Utilizador(utilizadores.get(username)), LocalDateTime.MIN, LocalDateTime.now());
                tm.clearTerminal();
                System.out.println("O utilizador realizou " + kms + " kms desde sempre.");
                tm.voltar();
                break;
            }
        }
    }

    // Método que diz quantos metros de altimetria é que um utilizador totalizou num período
    public void altimetriaPeriodo(){
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o utilizador (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                System.out.print("DATA INICIAL, ");
                LocalDateTime dataInicial = tm.getDataHoraTM();
                System.out.print("DATA FINAL, ");
                LocalDateTime dataFinal = tm.getDataHoraTM();

                if (dataInicial.isAfter(dataFinal)){
                    System.out.println("Data final não pode ser anterior à data inicial.");
                    tm.sleep(2000);
                    return;
                }

                double altimetria = stats.quantaAltimetria(new Utilizador(utilizadores.get(username)), dataInicial, dataFinal);

                tm.clearTerminal();
                System.out.println("O utilizador totalizou " + altimetria + " Km de altimetria no período de " + dataInicial + " a " + dataFinal);
                tm.voltar();
                break;
            }
        }
    }

    // Método que diz quantos metros de altimetria é que um utilizador totalizou desde sempre
    public void altimetriaSempre(){
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o utilizador (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                return;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                double altimetria = stats.quantaAltimetria(new Utilizador(utilizadores.get(username)), LocalDateTime.MIN, LocalDateTime.now());

                tm.clearTerminal();
                System.out.println("O utilizador totalizou " + altimetria + " Km de altimetria desde sempre.");
                tm.voltar();
                break;
            }
        }
    }

    // Método que diz qual o plano de treino mais exigente em função do dispêndio de calorias proposto
    public void planoMaisExigente(){
        try{
            tm.clearTerminal();
            System.out.println("O plano de treino mais exigente em função do dispêndio de calorias proposto é: \n");
            PlanoDeTreino p = planos.get(stats.planoMaisExigente(new HashMap<>(planos)));
            System.out.println(p.toString());
            tm.voltar();
        } catch (NullPointerException e){
            System.out.println("Não existem planos de treino registados.");
            tm.sleep(2000);
        }
    }

    // Método para listar todas as actividades de um utilizador
    public void listarAtv(){
        tm.clearTerminal();
        while (true) {
            System.out.println("Introduza o utilizador (username): ");
            String username = scanner.nextLine();
            if(username.toLowerCase().equals("exit")){
                break;
            } else if (!utilizadores.containsKey(username)) {
                System.out.println("Utilizador não encontrado.");
                System.out.println("Insira um utilizador existente. Se desejar cacelar a operação insira \"exit\"");
                tm.sleep(1500);
                tm.clearLastTwoLines();
                tm.clearLastLine();
            } else {
                tm.clearTerminal();
                System.out.println("As atividades de " + username + " são (ID - Nome): ");
                for (Entry<Integer, Atividade> entry : this.utilizadores.get(username).getAtividades().entrySet()){
                    int id = entry.getValue().getId();
                    String nome = entry.getValue().getNome();
                    System.out.println(id + " - " + nome);
                }
                tm.voltar();
                break;
            }
        }
    }

    //////////////////////////////////////////////////
    // Cálculo de Calorias
    ///////////////////////////////////////////////////

    public void recalculaCalorias(){
        for (Entry<Integer, Atividade> entry : atividades.entrySet()){
            entry.getValue().calorias(this.data);
        }
        for(Entry<Integer, PlanoDeTreino> entry : planos.entrySet()){
            for(Entry<Integer, Atividade> entry2 : entry.getValue().getAtividades().entrySet()){
                entry2.getValue().calorias(this.data);
            }
        }
        for(Entry<String, Utilizador> entry : utilizadores.entrySet()){
            for(Entry<Integer, Atividade> entry2 : entry.getValue().getAtividades().entrySet()){
                entry2.getValue().calorias(this.data);
            }
        }
    }

    //////////////////////////////////////////////////
    // Gravar Dados
    ///////////////////////////////////////////////////

    // Método para gravar os dados em bínario
    public void gravaBinario(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    // Método para ler os dados em bínario
    public Fitness_APP lerBinario(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Fitness_APP a = (Fitness_APP) ois.readObject();
        ois.close();
        return a;
    }


    // Método para inicializar o scanner
    public void initScanner() {
        this.scanner = new Scanner(System.in);
        this.tm.init();
    }
}