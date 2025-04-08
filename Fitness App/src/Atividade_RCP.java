import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

 public class Atividade_RCP extends Atividade{

    private static Map<Integer, String> tipos;

    static {
        tipos = new HashMap<Integer, String>();
        tipos.put(1, "Levantamento de Pesos");
        tipos.put(2, "Extensão de Pernas");
    }

    private int numeroRepeticoes;
    private double pesoLevantado;

    public Atividade_RCP(){
        super();
        this.numeroRepeticoes = 0;
        this.pesoLevantado = 0;
    }

    public Atividade_RCP(int t, LocalDateTime dataHora, Duration duracao, Utilizador user, double calorias, int numeroRepeticoes, double pesoLevantado){
        super(tipos.get(t), dataHora, duracao, user, calorias);
        this.numeroRepeticoes = numeroRepeticoes;
        this.pesoLevantado = pesoLevantado;
    }

    public Atividade_RCP(Atividade_RCP a){
        super(a);
        this.numeroRepeticoes = a.getNReps();
        this.pesoLevantado = a.getPeso();
    }


    // Getters & Setters
    public int getNReps() {
        return this.numeroRepeticoes;
    }

    public void setNReps(int numeroRepeticoes) {
        this.numeroRepeticoes = numeroRepeticoes;
    }

    public double getPeso() {
        return this.pesoLevantado;
    }

    public void setPeso(double pesoLevantado) {
        this.pesoLevantado = pesoLevantado;
    }

    public Map<Integer, String> getTipos() {
        return tipos;
    }

    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Número de Repetições = ").append(this.numeroRepeticoes).append('\n');
        sb.append("Peso Levantado = ").append(this.pesoLevantado).append(" Kg.").append('\n');
        return sb.toString();
    }

    // Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Atividade_RCP atividade_RCP = (Atividade_RCP) o;
        return getNReps() == atividade_RCP.getNReps() &&
                Double.compare(atividade_RCP.getPeso(), getPeso()) == 0;
    }

    // Clone
    public Atividade clone() {
        return new Atividade_RCP(this);
    }

    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////

    // Calcula calorias:
    // ( fator multiplicativo * duração * ( repetições * peso ) )/ 100
    public double calculaCalorias(){
        double total = (this.getUser().fatorMultiplicativo() * (this.getNReps() * this.getPeso()))/100;
        return total;
    }
}