import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Atividade_R extends Atividade{

    private static Map<Integer, String> tipos;

    static {
        tipos = new HashMap<Integer, String>();
        tipos.put(1, "Abdominais");
        tipos.put(2, "Alongamentos");
        tipos.put(3, "Flexões");
    }

    private int numeroRepeticoes;

    // Construtores
    public Atividade_R(){
        super();
        this.numeroRepeticoes = 0;
    }

    public Atividade_R(int t, LocalDateTime dataHora, Duration duracao, Utilizador user, double calorias, int numeroRepeticoes){
        super(tipos.get(t), dataHora, duracao, user, calorias);
        this.numeroRepeticoes = numeroRepeticoes;
    }

    public Atividade_R(Atividade_R a){
        super(a);
        this.numeroRepeticoes = a.getNReps();
    }


    // Getters & Setters
    public int getNReps() {
        return this.numeroRepeticoes;
    }

    public void setNReps(int numeroRepeticoes) {
        this.numeroRepeticoes = numeroRepeticoes;
    }

    public Map<Integer, String> getTipos() {
        return tipos;
    }

    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Número de Repetições = ").append(this.numeroRepeticoes).append('\n');
        return sb.toString();
    }

    // Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Atividade_R atividade_R = (Atividade_R) o;
        return getNReps() == atividade_R.getNReps();
    }

    // Clone
    public Atividade clone() {
        return new Atividade_R(this);
    }

    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////

    // Calcula calorias:
    // ( fator multiplicativo * duração * repetições )/ 100
    public double calculaCalorias(){
        double total = (this.getUser().fatorMultiplicativo() * this.getNReps())/5;
        return total;
    }
}