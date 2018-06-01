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
            } //ate aqui funciona
            else{
                boolean removeu = false;
                int tempo = this.FU.get(0).getTempoProcessamento();
                int i;
                for(i = 0; i <=2; i++){
                    if(tempo > 0){ //se nao tiver acabado o processamento
                        System.out.println("Tempo restante: " + tempo);
                        this.FU.get(0).setTempoProcessamento(tempo-1);
                        tempo = this.FU.get(0).getTempoProcessamento();
                    }
                    else{
                        System.out.println("PROCESSO TERMINADO");
                        this.FU.remove(0);
                        removeu = true;
                        i=3; //uma forma idiota de sair do loop pra nao dar erro
                    }
                }
                if(!removeu){
                    Processo aux = this.FU.get(0);
                    this.FU.remove(0);
                    this.adicionarEmFU(aux); //vai pro fim da fila            
                }
            }
        }
        
    }
    
    
    public static void main(String[] args) {
        Escalonador escalonador = new Escalonador();
        
        List<Processo> ftr = new ArrayList<Processo>();
        escalonador.setFTR(ftr);
        
        List<Processo> fu = new ArrayList<Processo>();
        escalonador.setFU(fu);
        
        escalonador.arquivoParaProcesso();
        //meu exemplo: C:\Users\gabriela\Documents\NetBeansProjects\Escalonador\src\escalonador\arquivo.txt
        
        escalonador.mostraProcessos();
        escalonador.escalonamento();
        
        //resolver esses bugs aí depois
        /*
        */
    }
}
