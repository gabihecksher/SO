/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonador;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author gabriela
 */
public class Escalonador {
    private List<Processo> FTR; //fila de processos prontos do tipo tempo-real
    private List<Processo> FU; //fila de processos prontos do tipo usuario
    private Processo processo_cpu; //processo sendo executado atualmente
    
    /**
     * @param args the command line arguments
     */
    
    public void setFTR(List<Processo> ftr){
        this.FTR = ftr;
    }

    public void setFU(List<Processo> fu){
        this.FU = fu;
    }
    
    public List<Processo> getFTR(){
        return this.FTR;
    }
    
    public List<Processo> getFU(){
        return this.FU;
    }
    
    
    public void adicionarEmFTR(Processo novo){
        this.FTR.add(novo);
    }
    
    public void adicionarEmFU(Processo novo){
        this.FU.add(novo);
    }
    
    public void adicionaProcesso(String[] informacoesProcesso){
        Processo processoNovo = new Processo();        
        //pega cada informacao, transforma em int e associa ao novo processo
                
        processoNovo.setTempoChegada(Integer.parseInt(informacoesProcesso[0])); 
        processoNovo.setPrioridade(Integer.parseInt(informacoesProcesso[1]));
        processoNovo.setTempoProcessamento(Integer.parseInt(informacoesProcesso[2]));
        processoNovo.setTamanho(Integer.parseInt(informacoesProcesso[3]));
        processoNovo.setQtdImpressoras(Integer.parseInt(informacoesProcesso[4]));
        processoNovo.setQtdScanners(Integer.parseInt(informacoesProcesso[5]));
        processoNovo.setQtdModems(Integer.parseInt(informacoesProcesso[6]));
        processoNovo.setQtdCDs(Integer.parseInt(informacoesProcesso[7]));
        if(processoNovo.getPrioridade() == 0) this.adicionarEmFTR(processoNovo);
        else this.adicionarEmFU(processoNovo);
                
    }
    
    public void arquivoParaProcesso(){ //coleta as informações do arquivo, transforma em processos e adiciona no escalonador
        Scanner ler = new Scanner(System.in);
 
        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();
 
        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            while (linha != null) {
                System.out.printf("%s\n", linha);
                String[] informacoesProcesso = linha.split(", ");
                this.adicionaProcesso(informacoesProcesso);                
                linha = lerArq.readLine(); //le a proxima linha
            }
 
        arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
 
        System.out.println();
        
    }
    
    public void mostraProcessos(){
        int numero = 0;
        System.out.println("FTR:");
        for(Processo processo : this.getFTR()){
            numero++;
            System.out.println("-----------------------------------------");
            System.out.println("Processo " + numero);
            System.out.println("Tempo Chegada: " + processo.getTempoChegada());
            System.out.println("Prioridade: " + processo.getPrioridade());
            System.out.println("Tempo Processamento: " + processo.getTempoProcessamento());
            System.out.println("Tamanho: " + processo.getTamanho());
            System.out.println("Qtd Impressoras: " + processo.getQtdImpressoras());
            System.out.println("Qtd Scanners: " + processo.getQtdScanners());
            System.out.println("Qtd Modems: " + processo.getQtdModems());
            System.out.println("Qtd CDs: " + processo.getQtdCDs());
            
        }
        
        System.out.println("FU:");
        for(Processo processo : this.getFU()){
            numero++;
            System.out.println("-----------------------------------------");
            System.out.println("Processo " + numero);
            System.out.println("Tempo Chegada: " + processo.getTempoChegada());
            System.out.println("Prioridade: " + processo.getPrioridade());
            System.out.println("Tempo Processamento: " + processo.getTempoProcessamento());
            System.out.println("Tamanho: " + processo.getTamanho());
            System.out.println("Qtd Impressoras: " + processo.getQtdImpressoras());
            System.out.println("Qtd Scanners: " + processo.getQtdScanners());
            System.out.println("Qtd Modems: " + processo.getQtdModems());
            System.out.println("Qtd CDs: " + processo.getQtdCDs());
            
        }
    }

    //por enquanto só executa primeiro os processos da FTR e depois os da FU, nao coloquei o quantum ainda
    public void escalonamento(){
        while(this.FTR.size() != 0 || this.FU.size() != 0){
            if(this.FTR.size() != 0){ //se a FTR nao estiver vazia
                int i;
                for(i = this.FTR.get(0).getTempoProcessamento(); i >= 0; i--){
                    System.out.println("Tempo restante: " + i);
                    this.FTR.get(0).setTempoProcessamento(i);
                }
                this.FTR.remove(0);
            }
            else{
                this.feedback();
            }
        }
        
    }
    
    public void feedback(){
        List<Processo> fila1 = organizaPorPrioridade(this.FU);
        List<Processo> fila2 = new ArrayList<Processo>(); 
        List<Processo> fila3 = new ArrayList<Processo>();
        
        while(fila1.size() != 0 || fila2.size() != 0 || fila3.size() != 0){
            if(fila1.size() == 0){
                if(fila2.size() == 0){
                    if(fila3.size() == 0){
                    }
                    else{
                        int tempo_restante = processa(fila3.get(0), 8);
                        if(tempo_restante != 0){
                            Processo aux = fila3.get(0);
                            fila3.remove(0);
                            fila1.add(aux); //volta pra a terceira fila
                            System.out.println("PROCESSO NÃO TERMINOU, VOLTOU PRA A FILA 1");
                        }
                        else{
                            fila3.remove(0);
                            System.out.println("PROCESSO FINALIZADO E REMOVIDO");
                        }
                    }
                }
                else{
                    int tempo_restante = processa(fila2.get(0), 4);
                    if(tempo_restante != 0){
                        Processo aux = fila2.get(0);
                        fila2.remove(0);
                        fila3.add(aux); //vai pra o final da segunda fila
                        System.out.println("PROCESSO NÃO TERMINOU, PASSOU PRA A FILA 3");
                    }
                    else{
                        fila2.remove(0);
                        System.out.println("PROCESSO FINALIZADO E REMOVIDO");
                    }
                }
            }
            else{ //tem processo na fila1
                int tempo_restante = processa(fila1.get(0), 2);
                if(tempo_restante != 0){
                    Processo aux = fila1.get(0);
                    fila1.remove(0);
                    fila2.add(aux); //vai pra a segunda fila
                    System.out.println("PROCESSO NÃO TERMINOU, PASSOU PRA A FILA 2");
                }
                else{
                    fila1.remove(0);
                    System.out.println("PROCESSO FINALIZADO E REMOVIDO");
                }
            }
        }
        
    }
    
    
    public int processa(Processo processo, int quantum){
        int i;
        int tempo = processo.getTempoProcessamento();
        System.out.println("INICIANDO PROCESSAMENTO");
        System.out.println("QUANTUM: " + quantum);
        for(i = 0; i < quantum; i++){
            if(tempo > 0){ //se nao tiver acabado o processamento
                System.out.println("Tempo restante: " + tempo);
                processo.setTempoProcessamento(tempo-1);
                tempo = processo.getTempoProcessamento();
            }
            else{
                System.out.println("PROCESSO TERMINADO");
                return 0;
            }
        }
        System.out.println("PROCESSAMENTO FINALIZADO");
        return tempo;
    }
    
    
    public static List<Processo> organizaPorPrioridade(List<Processo> fila){
        List<Processo> aux = new ArrayList<Processo>();
        int i;
        int prioridade;
        for(prioridade = 0; prioridade <= 3; prioridade++){
            for(i = 0; i < fila.size(); i++){
                if(fila.get(i).getPrioridade() == prioridade){
                    aux.add(fila.get(i));
                }
            }
        }
        return aux;
    }
    
    public static void main(String[] args) {
        Escalonador escalonador = new Escalonador();
        
        List<Processo> ftr = new ArrayList<Processo>();
        escalonador.setFTR(ftr);
        
        List<Processo> fu = new ArrayList<Processo>();
        escalonador.setFU(fu);
        
        escalonador.arquivoParaProcesso();
        //meu exemplo: C:\Users\gabriela\Documents\NetBeansProjects\Escalonador\src\escalonador\arquivo.txt
        
        System.out.println("Processos não organizados:");
        escalonador.mostraProcessos();
        escalonador.FTR = organizaPorPrioridade(escalonador.FTR);
        escalonador.FU = organizaPorPrioridade(escalonador.FU);
        System.out.println("Processos ORGANIZADOS:");
        escalonador.mostraProcessos();
        
        //escalonador.escalonamento();
        
    }
}
