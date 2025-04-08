import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public abstract class Atividade implements Serializable{

    private static int nextId = 0;

    private int id;
    private String nome;
    private LocalDateTime dataHora;
    private Duration duracao;
    private Utilizador user;
    private double calorias;

    // Método que devolve um novo ID e incrementa a variàvel nextId    
    private static int getNewId() {
      return nextId++;
    }

    // Construtor vazio
    public Atividade(){
        this.id = getNewId();
        this.nome = "";
        this.dataHora = LocalDateTime.now();
        this.duracao = Duration.ZERO;
        this.user = new Utilizador();
        this.calorias = 0;
    }

    // Constructor parametrizado
    public Atividade(String nome, LocalDateTime dataHora, Duration duracao, Utilizador user, double calorias) {
        this.id = getNewId();
        this.nome = nome;
        this.dataHora = dataHora;
        this.duracao = duracao;
        this.user = user;
        this.calorias = calorias;
    }

    // Construtor de cópia
    public Atividade(Atividade a){
        this.id = getNewId();
        this.nome = a.getNome();
        this.dataHora = a.getDataHora();
        this.duracao = a.getDuracao();
        this.user = a.getUser();
        this.calorias = a.getCalorias();
    }

    // Getters
    public int getId(){
        return this.id;
    }
    
    public String getNome() {
        return this.nome;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public Duration getDuracao() {
        return this.duracao;
    }

    public Utilizador getUser() {
        return this.user;
    }

    public double getCalorias() {
        return this.calorias;
    }


    // Setters
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public void setUser(Utilizador user) {
        this.user = user;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }
    
    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Atividade: ").append("\n");
        sb.append("Id = ").append(this.getId()).append("\n");
        sb.append("Nome = ").append(this.getNome()).append("\n");
        sb.append("Data e Hora = ").append(this.getDataHora()).append("\n");
        sb.append("Duração = ").append(this.getDuracao()).append("\n");
        sb.append("Utilizador = ").append(this.user.getUsername() + " - " + this.user.getNome()).append("\n");
        sb.append("Calorias = ").append(this.getCalorias()).append(" Kcal").append("\n");
        return sb.toString();
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividade atividade = (Atividade) o;
        return Integer.valueOf(this.id).equals(atividade.getId())
                && this.nome.equals(atividade.getNome())
                && this.dataHora.equals(atividade.getDataHora())
                && this.duracao.equals(atividade.getDuracao())
                && this.user.equals(atividade.getUser());
    }

    // Clone
    public abstract Atividade clone();
    
    
    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////
    
    public abstract double calculaCalorias();

    // Data após a atividade
    public LocalDateTime dataAposAtividade(){
        return this.dataHora.plus(this.duracao);
    }
    
    // calcula calorias?
    public void calorias(ChronoLocalDateTime<?> data){
        if(dataAposAtividade().isBefore(data)){
            this.setCalorias(this.calculaCalorias());
        } else {
            this.setCalorias(0);
        }
    }

    // Verifica se o ID já existe num Map de atividades e incrementa o ID no caso de já existir
    public void checkID(Map<Integer, Atividade> atividades) {
        if (!atividades.isEmpty()) {
            List<Integer> ids = new ArrayList<>(atividades.keySet());
            Collections.sort(ids, Collections.reverseOrder());
            int lastId = ids.get(0);
            this.setId(lastId + 1);
        } else {
            this.setId(0); 
        }
    }
}