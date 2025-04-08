import java.lang.String;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Atividade_DA extends Atividade{

    private static Map<Integer, String> tipos;

    static {
        tipos = new HashMap<Integer, String>();
        tipos.put(1, "Corrida na Estrada");
        tipos.put(2, "Trail no Monte");
        tipos.put(3, "Bicicleta de Estrada");
        tipos.put(4, "Bicicleta de Montanha");
    }

    private double distanciaPercorrida;
    private double altimetria;



    // Construtores
    public Atividade_DA(){
        super();
        this.altimetria = 0;
        this.distanciaPercorrida = 0;
    }

    public Atividade_DA(int t, LocalDateTime dataHora, Duration duracao, Utilizador user, double calorias, double altimetria, double distanciaPercorrida) {
        super(tipos.get(t), dataHora, duracao, user, calorias);
        this.altimetria = altimetria;
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public Atividade_DA(Atividade_DA a){
        super(a);
        this.altimetria = a.getAltimetria();
        this.distanciaPercorrida = a.getDistancia();
    }

    // Getters & Setters
    public double getAltimetria() {
        return this.altimetria;
    }

    public void setAltimetria(double altimetria) {
        this.altimetria = altimetria;
    }

    public double getDistancia() {
        return this.distanciaPercorrida;
    }

    public void setDistancia(double distanciaPercorrida) {
        this.distanciaPercorrida = distanciaPercorrida;
    }

    public Map<Integer, String> getTipos() {
        return tipos;
    }


    // To String
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Distância Percorrida = ").append(distanciaPercorrida).append(" Km").append("\n");
        sb.append("Altimetria = ").append(altimetria).append("\n");
        return sb.toString();
    }

    // Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Atividade_DA atividade_DA = (Atividade_DA) o;
        return Double.compare(atividade_DA.getAltimetria(), getAltimetria()) == 0 &&
                Double.compare(atividade_DA.getDistancia(), getDistancia()) == 0;
    }

    // Clone
    public Atividade clone() {
        return new Atividade_DA(this);
    }

    ///////////////////////////////////////////////////
    // Implementação de métodos
    ///////////////////////////////////////////////////

    // Calcula calorias:
    // ( fator multiplicativo * duração * ( altimetria * distância ) )/ 100
    public double calculaCalorias(){
        double total = (this.getUser().fatorMultiplicativo() * this.getDuracao().getSeconds() * (this.getAltimetria() + this.getDistancia()))/600;
        return total;
    }


}