import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;

 public class Stats implements Serializable{

    public Stats(){
    }
    
    // Método que verifica se uma data e hora está entre duas datas
    public boolean dataValidaLDT(LocalDateTime dataInicial, LocalDateTime dataFinal, LocalDateTime dataAVerificar){
        return dataAVerificar.isAfter(dataInicial) 
                && dataAVerificar.isBefore(dataFinal);
    }

    // Método que devolve o utilizador que mais calorias dispendeu num período dado
    public String maisCalorias(Map<String, Utilizador> utilizadores, LocalDateTime dataInicial, LocalDateTime dataFinal){
        double maxCalorias = 0;
        String username = "";
        for(Entry<String, Utilizador> entryU : utilizadores.entrySet()){
            double calorias = 0;
            for(Entry<Integer, Atividade> entryA : entryU.getValue().getAtividades().entrySet()){
                if(dataValidaLDT(dataInicial, dataFinal, entryA.getValue().getDataHora())){
                    calorias += entryA.getValue().getCalorias();
                }
            }
            if(calorias > maxCalorias){
                maxCalorias = calorias;
                username = entryU.getKey();
            }
        }
        return username;
    }

    // Método que devolve utilizador que mais atividades praticou num período dado
    public String maisAtividades(Map<String, Utilizador> utilizadores, LocalDateTime dataInicial, LocalDateTime dataFinal){
        double maxAtividades = 0;
        String username = "";
        for(Entry<String, Utilizador> entryU : utilizadores.entrySet()){
            
            if(entryU.getValue().getAtividades().size() > maxAtividades){
                maxAtividades = entryU.getValue().getAtividades().size();
                username = entryU.getKey();
            }
        }
        return username;
    }
    


    // Método que devolve a atividade mais realizada
    public String maisRealizada(Map<Integer, Atividade> atividades){
        int countD = 0;
        int countDA = 0;
        int countR = 0;
        int countRCP = 0;
        for(Entry<Integer, Atividade> entryA : atividades.entrySet()){
            if (dataValidaLDT(LocalDateTime.MIN, LocalDateTime.now(), entryA.getValue().getDataHora())){
                if(entryA.getValue() instanceof Atividade_D){
                    countD++;
                } else if(entryA.getValue() instanceof Atividade_DA){
                    countDA++;
                } else if(entryA.getValue() instanceof Atividade_R){
                    countR++;
                } else if(entryA.getValue() instanceof Atividade_RCP){
                    countRCP++;
                }
            }
        }
        if(countD > countDA && countD > countR && countD > countRCP){
            return "Atividade_D";
        } else if(countDA > countD && countDA > countR && countDA > countRCP){
            return "Atividade_DA";
        } else if(countR > countD && countR > countDA && countR > countRCP){
            return "Atividade_R";
        } else if(countRCP > countD && countRCP > countDA && countRCP > countR){
            return "Atividade_RCP";
        } else {
            return "Empate";
        }

    }

    // Método que devolve quantos kilómetros um utilizador realizou num período dado
    public double maisKms(Utilizador user, LocalDateTime dataInicial, LocalDateTime dataFinal){
        double kms = 0;
        for(Entry<Integer, Atividade> entryA : user.getAtividades().entrySet()){
            if(dataValidaLDT(dataInicial, dataFinal, entryA.getValue().getDataHora())){
                if(entryA.getValue() instanceof Atividade_D){
                    kms += ((Atividade_D) entryA.getValue()).getDistancia();
                } else if(entryA.getValue() instanceof Atividade_DA){
                    kms += ((Atividade_DA) entryA.getValue()).getDistancia();
                }
            }
        }
        return kms;
    }

    // Método que devolve quantos metros de altimetria é que um utilizador totalizou num período
    public double quantaAltimetria(Utilizador user, LocalDateTime dataInicial, LocalDateTime dataFinal){
        double altimetria = 0;
        for(Entry<Integer, Atividade> entryA : user.getAtividades().entrySet()){
            if(dataValidaLDT(dataInicial, dataFinal, entryA.getValue().getDataHora())){
                if(entryA.getValue() instanceof Atividade_DA){
                    altimetria += ((Atividade_DA) entryA.getValue()).getAltimetria();
                }
            }
        }
        return altimetria;
    }

    // Método que devolve o id do plano de treino mais exigente
    public int planoMaisExigente(Map<Integer, PlanoDeTreino> planos){
        int max = 0;
        int id = 0;
        for(Entry<Integer, PlanoDeTreino> entry : planos.entrySet()){
            if ((int) entry.getValue().getObjetivoCalorias() > max){
                max = (int) entry.getValue().getObjetivoCalorias();
                id = entry.getKey();
            }
        }
        return id;
    }

}
