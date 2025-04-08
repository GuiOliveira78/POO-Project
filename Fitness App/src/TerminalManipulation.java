import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;



public class TerminalManipulation implements Serializable{
    
    private transient Scanner scanner;
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
    private transient DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    
    public TerminalManipulation(){
        init();
    }
    
    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////

    // Get Opção
    public int getOpcao(){
        System.out.println("Escolha uma opção: ");
        try{
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        } catch (InputMismatchException e){
            System.out.println("Opção inválida. Insira um número inteiro.");
            scanner.nextLine();
            sleep(1500);
            clearLastTwoLines();
            return getOpcao();
        }
    }

    // Clear Two Lines
    public void clearLastTwoLines() {
        // Move cursor up 2 lines
        System.out.print("\033[2A");
        // Clear the last line
        System.out.print("\033[K");
        // Move cursor up 1 line
        System.out.print("\033[1A");
        // Clear the line
        System.out.print("\033[J");    
    }

    public void clearLastLine() {
        // Move cursor up 1 line
        System.out.print("\033[1A");
        // Clear the line
        System.out.print("\033[J");
    }

    // Clear Terminal
    public void clearTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Sleep
    public void sleep(int time){
        try {
            Thread.sleep(time); // Dorme por 2000 milissegundos, ou 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Voltar
    public void voltar(){
        System.out.println("\n");
        System.out.println("Prima ENTER para voltar ao menu anterior.");
        scanner.nextLine();
    }

    public LocalDateTime getDataHoraTM(){
        System.out.println("Introduza a data (DD-MM-AAAA): ");
        String data = scanner.nextLine();
        System.out.println("Introduza a hora (HH:MM): ");
        String hora = scanner.nextLine();
        String dataHora = data + " " + hora;
        try{
            LocalDateTime dataNova = LocalDateTime.parse(dataHora, formatter);
            return dataNova;

        } catch (DateTimeParseException e){
            System.out.println("Data ou hora inválida. Insira a data no formato (DD-MM-AAAA) e a hora no formato (HH:MM).");
            sleep(1500);
            clearTerminal();

            return getDataHoraTM();
        }
    }

    public LocalDate getDataTM(){
        System.out.println("Introduza a data (DD-MM-AAAA): ");
        String data = scanner.nextLine();
        try{
            LocalDate dataNova = LocalDate.parse(data, formatter2);
            return dataNova;
        } catch (DateTimeParseException e){
            System.out.println("Data inválida. Insira a data no formato (DD-MM-AAAA).");
            sleep(1500);
            clearTerminal();

            return getDataTM();
        }
    }

    public Duration getDuracaoTM(){
        System.out.println("Introduza a duração (HH:MM): ");
        String hora = scanner.nextLine();
        try{
            LocalTime horaNova = LocalTime.parse(hora);
            Duration duracao = Duration.ofSeconds(horaNova.toSecondOfDay());
            return duracao;
        } catch (DateTimeParseException e){
            System.out.println("Duração inválida. Insira a duração no formato (HH:MM).");
            sleep(1500);
            clearTerminal();

            return getDuracaoTM();
        }
    }




    public void init() {
        this.scanner = new Scanner(System.in);
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        this.formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }
}
