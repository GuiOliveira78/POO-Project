import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class PlanoDeTreino implements Serializable{

    private static int nextId = 0;

    private int id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Map<Integer, Atividade> atividades;
    private double objetivoCalorias;
    private double objetivoDistancia;
    private int frequenciaSemanal;
    private Utilizador user;

    // Método que devolve um novo ID e incrementa a variàvel nextId    
    private static int getNewId() {
        return nextId++;
    }

    // Construtor Vazio
    public PlanoDeTreino(){
        this.id = getNewId();
        this.nome = "";
        this.dataInicio = LocalDate.now();
        this.dataFim = LocalDate.now();
        this.atividades = new HashMap<>();
        this.objetivoCalorias = 0;
        this.objetivoDistancia = 0;
        this.frequenciaSemanal = 0;
        this.user = new Utilizador();
    }

    // Construtor Parametrizado
    public PlanoDeTreino(String nome, LocalDate dataInicio, LocalDate dataFim, double objetivoCalorias, double objetivoDistancia, int frequenciaSemanal, Utilizador user) {
        this.id = getNewId();
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.atividades = new HashMap<>();
        this.objetivoCalorias = objetivoCalorias;
        this.objetivoDistancia = objetivoDistancia;
        this.frequenciaSemanal = frequenciaSemanal;
        this.user = user;
    }

    // Construtor de Cópia
    public PlanoDeTreino(PlanoDeTreino p){
        this.id = getNewId();
        this.nome = p.getNome();
        this.dataInicio = p.getDataInicio();
        this.dataFim = p.getDataFim();
        this.atividades = p.getAtividades();
        this.objetivoCalorias = p.getObjetivoCalorias();
        this.objetivoDistancia = p.getObjetivoDistancia();
        this.frequenciaSemanal = p.getFrequenciaSemanal();
        this.user = p.getUser();
    }

    
    // Getters & Setters
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }


    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Map<Integer, Atividade> getAtividades() {
        Map<Integer, Atividade> copiaSegura = new HashMap<>();
        for (Map.Entry<Integer, Atividade> entry : this.atividades.entrySet()) {
            copiaSegura.put(entry.getKey(), (entry.getValue() != null) ? entry.getValue().clone() : null);
        }
        return copiaSegura;
    }

    public void setAtividades(Map<Integer, Atividade> atividades) {
        Map<Integer, Atividade> novaMapa = new HashMap<>();
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            novaMapa.put(entry.getKey(), (entry.getValue() != null) ? entry.getValue().clone() : null);
        }
        this.atividades = novaMapa;
    }

    public double getObjetivoCalorias() {
        return this.objetivoCalorias;
    }

    public void setObjetivoCalorias(double objetivoCalorias) {
        this.objetivoCalorias = objetivoCalorias;
    }

    public double getObjetivoDistancia() {
        return this.objetivoDistancia;
    }

    public void setObjetivoDistancia(double objetivoDistancia) {
        this.objetivoDistancia = objetivoDistancia;
    }

    public int getFrequenciaSemanal() {
        return this.frequenciaSemanal;
    }

    public void setFrequenciaSemanal(int frequenciaSemanal) {
        this.frequenciaSemanal = frequenciaSemanal;
    }

    public Utilizador getUser() {
        return this.user;
    }

    public void setUser(Utilizador user) {
        this.user = user;
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlanoDeTreino that = (PlanoDeTreino) obj;
        return Double.compare(that.objetivoCalorias, objetivoCalorias) == 0 &&
               Double.compare(that.objetivoDistancia, objetivoDistancia) == 0 &&
               frequenciaSemanal == that.frequenciaSemanal &&
               (nome == null ? that.nome == null : nome.equals(that.nome)) &&
               dataInicio.equals(that.dataInicio) &&
               dataFim.equals(that.dataFim) &&
               atividades.equals(that.atividades);
    }

    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plano de Treino: ").append("\n");
        sb.append("ID = ").append(this.getId()).append("\n");
        sb.append("Nome = ").append(this.getNome()).append("\n");
        sb.append("Data de Início = ").append(this.getDataInicio()).append("\n");
        sb.append("Data de Fim = ").append(this.getDataFim()).append("\n");
        sb.append("Objetivo de Calorias = ").append(this.getObjetivoCalorias()).append(" Kcal").append("\n");
        sb.append("Objetivo de Distância = ").append(this.getObjetivoDistancia()).append(" Km").append("\n");
        sb.append("Frequência Semanal = ").append(this.getFrequenciaSemanal()).append(" vezes").append("\n");
        sb.append("Atividades: ").append("\n\n");
        for(Map.Entry<Integer, Atividade> entry : atividades.entrySet()){
            sb.append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    // Clone
    public PlanoDeTreino clone(){
        return new PlanoDeTreino(this);
    }

    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////

    // Adicionar Atividade
    public void addAtv(Atividade atividade) {
        this.atividades.put(atividade.getId(), atividade);
    }

    // Remover Atividade
    public void removerAtividade(int id) {
        this.atividades.remove(id);
    }
}
