import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Utilizador implements Serializable{

    private String username;
    private String nome;
    private String morada;
    private String email;
    private double frequenciaCardiacaMedia;
    private TipoUtilizador tipoUtilizador;
    private Map<Integer, Atividade> atividades;



    // Construtor Vazio
    public Utilizador() {
        this.username = "";
        this.nome = "";
        this.morada = "";
        this.email = "";
        this.frequenciaCardiacaMedia = 0;
        this.tipoUtilizador = TipoUtilizador.OCASIONAL;
        this.atividades = new HashMap<Integer, Atividade>();
    }

    // Construtor Parametrizado
    public Utilizador(String username, String nome, String morada, String email, double frequenciaCardiacaMedia, TipoUtilizador tipoUtilizador, Map<Integer, Atividade> atividades) {
        this.username = username;
        this.nome = nome;
        this.morada = morada;
        this.email = email;
        this.frequenciaCardiacaMedia = frequenciaCardiacaMedia;
        this.tipoUtilizador = tipoUtilizador;
        this.atividades = atividades;
    }

    // Construtor de Cópia
    public Utilizador(Utilizador u) {
        this.username = u.getUsername();
        this.nome = u.getNome();
        this.morada = u.getMorada();
        this.email = u.getEmail();
        this.frequenciaCardiacaMedia = u.getFrequenciaCardiacaMedia();
        this.tipoUtilizador = u.getTipoUtilizador();
        this.atividades = u.getAtividades();
    }

    // Getters & Setters
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getFrequenciaCardiacaMedia() {
        return this.frequenciaCardiacaMedia;
    }

    public void setFrequenciaCardiacaMedia(double frequenciaCardiacaMedia) {
        this.frequenciaCardiacaMedia = frequenciaCardiacaMedia;
    }

    public TipoUtilizador getTipoUtilizador() {
        return this.tipoUtilizador;
    }

    public void setTipoUtilizador(TipoUtilizador tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    public Map<Integer, Atividade> getAtividades() {
        Map<Integer, Atividade> copiaSegura = new HashMap<>();
        for (Map.Entry<Integer, Atividade> entry : this.atividades.entrySet()) {
            copiaSegura.put(entry.getKey(), (entry.getValue() != null) ? entry.getValue().clone() : null);
        }
        return copiaSegura;
    }

    public void setAtividades(Map<Integer, Atividade> atividades) {
        this.atividades = new HashMap<>();
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            this.atividades.put(entry.getKey(), (entry.getValue() != null) ? entry.getValue().clone() : null);
        }
    }

    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username = ").append(this.username).append('\n');
        sb.append("Nome = ").append(this.nome).append('\n');
        sb.append("Morada = ").append(this.morada).append('\n');
        sb.append("Email = ").append(this.email).append('\n');
        sb.append("Frequência Cardíaca Média = ").append(this.frequenciaCardiacaMedia).append('\n');
        sb.append("Tipo de Utilizador = ").append(this.tipoUtilizador).append('\n');
        sb.append("Lista de Atividades: \n\n");
        for(Map.Entry<Integer, Atividade> entry : atividades.entrySet()){
            sb.append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    // Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador utilizador = (Utilizador) o;
        return getUsername() == utilizador.getUsername() &&
                Double.compare(utilizador.getFrequenciaCardiacaMedia(), getFrequenciaCardiacaMedia()) == 0 &&
                getNome().equals(utilizador.getNome()) &&
                getMorada().equals(utilizador.getMorada()) &&
                getEmail().equals(utilizador.getEmail()) &&
                getTipoUtilizador() == utilizador.getTipoUtilizador() &&
                getAtividades().equals(utilizador.getAtividades());
    }

    // Clone
    public Utilizador clone() {
        return new Utilizador(this);
    }
    
    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////
    
    // Fator Multiplicativo:
    // ( valor decimal * frequencia cardiaca media )/10
    public double fatorMultiplicativo() {
        switch (tipoUtilizador) {
            case PROFISSIONAL:
                return (0.8*frequenciaCardiacaMedia)/10;
            case AMADOR:
                return (0.9*frequenciaCardiacaMedia)/10;
            case OCASIONAL:
                return (1.0*frequenciaCardiacaMedia)/10;
            default:
                return -1.0;
        }
    }

    public void addAtv(Atividade a) {
        this.atividades.put(a.getId(), a);
    }

    public void removeAtv(Atividade a) {
        this.atividades.remove(a.getId());
    }

    public void addPT(PlanoDeTreino pt) {
        int freq = pt.getFrequenciaSemanal();
        while (freq > 0) {
            for (Map.Entry<Integer, Atividade> entry : pt.getAtividades().entrySet()) {
                this.atividades.put(entry.getKey(), entry.getValue());
            }
            
            
            freq--;
        }
    }

    public double getKms() {
        double kms = 0;
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            if(entry.getValue() instanceof Atividade_D){
                kms += ((Atividade_D) entry.getValue()).getDistancia();

            } else if(entry.getValue() instanceof Atividade_DA){
                kms += ((Atividade_DA) entry.getValue()).getDistancia();
            }
        }
        return kms;
    }

    public double getTotalCalorias() {
        double calorias = 0;
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            calorias += entry.getValue().getCalorias();
        }
        return calorias;
    }

    public double getTotalCaloriasDatas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        double calorias = 0;
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            if(entry.getValue().getDataHora().isAfter(dataInicio) && entry.getValue().getDataHora().isBefore(dataFim)){
                calorias += entry.getValue().getCalorias();
            }
        }
        return calorias;
        
    }

    public int getAtividadesDatas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        int count = 0;
        for (Map.Entry<Integer, Atividade> entry : atividades.entrySet()) {
            if(entry.getValue().getDataHora().isAfter(dataInicio) && entry.getValue().getDataHora().isBefore(dataFim)){
                count++;
            }
        }
        return count;
    }
}
