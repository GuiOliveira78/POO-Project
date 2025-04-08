import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Atividade_D extends Atividade{

    private static Map<Integer, String> tipos;

    static {
        tipos = new HashMap<Integer, String>();
        tipos.put(1, "Remo");
        tipos.put(2, "Corrida na Pista de Atletismo");
        tipos.put(3, "Patinagem");
    }

    private double distanciaPercorrida;

    // Construtores
    public Atividade_D(){
        super();
        this.distanciaPercorrida = 0;
    }

    public Atividade_D(int t, LocalDateTime dataHora, Duration duracao, Utilizador user, double calorias, double distanciaPercorrida) {
        super(tipos.get(t), dataHora, duracao, user, calorias);
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public Atividade_D(Atividade_D a){
        super(a);
        this.distanciaPercorrida = a.getDistancia();
    }

    // Getters & Setters
    public double getDistancia() {
        return this.distanciaPercorrida;
    }

    public void setDistancia(double distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public Map<Integer, String> getTipos() {
        return new HashMap<>(tipos);
    }

    // To String
    public java.lang.String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Distância Percorrida = ").append(this.distanciaPercorrida).append(" Km").append('\n');
        return sb.toString();
    }

    // Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Atividade_D atividade_D = (Atividade_D) o;
        return Double.compare(atividade_D.getDistancia(), getDistancia()) == 0;
    }

    // Clone
    public Atividade clone() {
        return new Atividade_D(this);
    }


    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////


    // Calcula calorias:
    // ( fator multiplicativo * duração * distância )/ 100
    public double calculaCalorias(){
        double total = (this.getUser().fatorMultiplicativo() * this.getDuracao().getSeconds() * this.getDistancia())/300;
        return total;
    }
}