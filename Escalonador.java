/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonador;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author gabriela
 */
public class Escalonador {
	private int velocidade = 1;// variavel que muda o tempo de clock
	
	private List<Processo> FE; //fila geral de processos
    private List<Processo> FTR; //fila de processos prontos do tipo tempo-real
    private List<Processo> FU; //fila de processos prontos do tipo usuario
    private int quantum = 0;//quantum do processo atual no feedback
    private int timer = 0;
    
    // filas do feedback
    private List<Processo> fila1 = new ArrayList<Processo>();
    private List<Processo> fila2 = new ArrayList<Processo>(); 
    private List<Processo> fila3 = new ArrayList<Processo>();
    
    /**
     * @param args the command line arguments
     */
    
    public void setFTR(List<Processo> ftr){
        this.FTR = ftr;
    }
    
    public void setFE(List<Processo> fe){
        this.FE = fe;
    }

    public void setFU(List<Processo> fu){
        this.FU = fu;
    }
    
    public List<Processo> getFTR(){
        return this.FTR;
    }
    
    public List<Processo> getFE(){
        return this.FE;
    }
    
    public List<Processo> getFU(){
        return this.FU;
    }
    
    
    public void adicionarEmFTR(Processo novo){
        this.FTR.add(novo);
    }
    
    public void adicionarEmFE(Processo novo){
        this.FE.add(novo);
    }
    
    public void adicionarEmFila1(Processo novo){
        this.fila1.add(novo);
    }
    
    public void adicionarEmFU(Processo novo){
        this.FU.add(novo);
    }

    
    public void adicionaProcesso(String[] informacoesProcesso, int id){
        Processo processoNovo = new Processo();        
        //pega cada informacao, transforma em int e associa ao novo processo
        
        processoNovo.setID(id);         
        processoNovo.setTempoChegada(Integer.parseInt(informacoesProcesso[0])); 
        processoNovo.setPrioridade(Integer.parseInt(informacoesProcesso[1]));
        processoNovo.setTempoProcessamento(Integer.parseInt(informacoesProcesso[2]));
        processoNovo.setTamanho(Integer.parseInt(informacoesProcesso[3]));
        processoNovo.setQtdImpressoras(Integer.parseInt(informacoesProcesso[4]));
        processoNovo.setQtdScanners(Integer.parseInt(informacoesProcesso[5]));
        processoNovo.setQtdModems(Integer.parseInt(informacoesProcesso[6]));
        processoNovo.setQtdCDs(Integer.parseInt(informacoesProcesso[7]));
        this.adicionarEmFE(processoNovo);
                
    }
    
    public void arquivoParaProcesso(){ //coleta as informações do arquivo, transforma em processos e adiciona no escalonador
        Scanner ler = new Scanner(System.in);
        int id = 0;
 
        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();
 
        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            while (linha != null) {
                System.out.printf("id: %d|  %s\n",id, linha);
                String[] informacoesProcesso = linha.split(", ");
                this.adicionaProcesso(informacoesProcesso, id);
                id++;
                linha = lerArq.readLine(); //le a proxima linha
            }
        Collections.sort(FE);
        arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
 
        System.out.println();
        
    }
    
    public void mostraProcessos(){
        System.out.println("FTR:---------------------------------------");
        for(Processo processo : this.getFTR()){
        	
            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
        
        System.out.println("FE:---------------------------------------");
        for(Processo processo : this.getFE()){

            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
        
        System.out.println("FU:---------------------------------------");
        for(Processo processo : this.getFU()){

            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
        
        System.out.println("------------------FEEDBACK---------------------");
        System.out.println("fila1:---------------------------------------");
        for(Processo processo : this.fila1){

            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
        System.out.println("fila2:---------------------------------------");
        for(Processo processo : this.fila2){

            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
        System.out.println("fila3:---------------------------------------");
        for(Processo processo : this.fila3){

            System.out.println("Processo " + processo.getID()+": "+processo.getTempoChegada()+", "+processo.getPrioridade()+", "+processo.getTempoProcessamento()+", "+processo.getTamanho()+", "+processo.getQtdImpressoras()+", "+processo.getQtdScanners()+", "+processo.getQtdModems()+", "+processo.getQtdCDs());
            
        }
    }

    public void escalonamento() throws InterruptedException{
        while(this.FTR.size() != 0 || this.FU.size() != 0 || this.FE.size() != 0){
        	
        	TimeUnit.SECONDS.sleep(velocidade);
        	System.out.println("\nTIMER: "+timer+"\n");
        	
        	int cont = 0;
        	for(Processo processo: this.getFE()) {
        		if(processo.getPrioridade() == 0 && processo.getTempoChegada() == timer) {
            		adicionarEmFTR(processo);
            		cont++;
            	} 
            	if (processo.getPrioridade() > 0 && processo.getTempoChegada() == timer){
            		adicionarEmFU(processo);
            		adicionarEmFila1(processo);
            		cont++;
            	}
        	}
        	for(int i = 0; i < cont; i++) {
        		this.FE.remove(0);
        	}

        	
        	mostraProcessos();
        	
            if(this.FTR.size() != 0 && this.quantum == 0){ //se a FTR nao estiver vazia
                //int i;
                
                System.out.println("processo: "+this.FTR.get(0).getID()+" Tempo restante: " + this.FTR.get(0).getTempoProcessamento());
                this.FTR.get(0).setTempoProcessamento(this.FTR.get(0).getTempoProcessamento()- 1);
                
                /*for(i = this.FTR.get(0).getTempoProcessamento(); i > 0; i--){
                    System.out.println("processo: "+this.FTR.get(0).getID()+" Tempo restante: " + i);
                    this.FTR.get(0).setTempoProcessamento(i);
                }*/
                if(this.FTR.get(0).getTempoProcessamento() == 0) {
                	this.FTR.remove(0);
                }
                
            }
            else{
                this.FU = organizaPorPrioridade(this.FU);
                this.feedback();
            }
            this.timer++;
        }
        mostraProcessos();
    }
    
    public void feedback(){
    	
        if(fila1.size() != 0 || fila2.size() != 0 || fila3.size() != 0){
            if(fila1.size() == 0){
                if(fila2.size() == 0){
                    if(fila3.size() == 0){
                    }
                    else{
                    	if(this.quantum == 0)this.quantum = 8;
                        int tempo_restante = processa(fila3.get(0));
                        if(tempo_restante != 0 && this.quantum == 0){
                            Processo aux = fila3.get(0);
                            fila3.remove(0);
                            fila1.add(aux); //volta pra a terceira fila
                            System.out.println("PROCESSO"+aux.getID()+" NÃO TERMINOU, VOLTOU PRA A FILA 1");
                            
                        }
                        else if (tempo_restante == 0){
                        	System.out.println("PROCESSO"+fila3.get(0).getID()+" FINALIZADO E REMOVIDO");
                        	this.FU.remove(fila3.get(0));
                            fila3.remove(0);
                            
                        }
                    }
                }
                else{
                	if(this.quantum == 0)this.quantum = 4;
                    int tempo_restante = processa(fila2.get(0));
                    if(tempo_restante != 0 && this.quantum == 0){
                        Processo aux = fila2.get(0);
                        fila2.remove(0);
                        fila3.add(aux); //vai pra o final da segunda fila
                        System.out.println("PROCESSO"+aux.getID()+" NÃO TERMINOU, PASSOU PRA A FILA 3");
                        
                    }
                    else if (tempo_restante == 0){
                    	System.out.println("PROCESSO"+fila2.get(0).getID()+" FINALIZADO E REMOVIDO");
                    	this.FU.remove(fila2.get(0));
                        fila2.remove(0);
                        
                    }
                }
            }
            else{ //tem processo na fila1
            	if(this.quantum == 0)this.quantum = 2;
                int tempo_restante = processa(fila1.get(0));
                if(tempo_restante != 0 && this.quantum == 0){
                    Processo aux = fila1.get(0);
                    fila1.remove(0);
                    fila2.add(aux); //vai pra a segunda fila
                    System.out.println("PROCESSO"+aux.getID()+" NÃO TERMINOU, PASSOU PRA A FILA 2");
                    
                }
                else if (tempo_restante == 0){
                	System.out.println("PROCESSO"+fila1.get(0).getID()+" FINALIZADO E REMOVIDO");
                	this.FU.remove(fila1.get(0));
                    fila1.remove(0);
                    
                }
            }
        }
        
    }
    
    
    public int processa(Processo processo){
        //int i;
        int tempo = processo.getTempoProcessamento();
        System.out.println("\nINICIANDO PROCESSAMENTO "+ processo.getID());
        System.out.println("QUANTUM: " + this.quantum);
        if(this.quantum > 0){
            if(tempo > 0){ //se nao tiver acabado o processamento
                System.out.println("processo: "+processo.getID()+" Tempo restante: " + (tempo-1));
                processo.setTempoProcessamento(tempo-1);
                tempo = processo.getTempoProcessamento();
            }
            else{
                System.out.println("PROCESSO TERMINADO "+ processo.getID());
                this.quantum = 0;
                return 0;
            }
        } 
        this.quantum --;
        System.out.println("PROCESSAMENTO FINALIZADO "+ processo.getID());
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
        
        List<Processo> fe = new ArrayList<Processo>();
        escalonador.setFE(fe);
        
        List<Processo> fu = new ArrayList<Processo>();
        escalonador.setFU(fu);
        
        escalonador.arquivoParaProcesso();
        //meu exemplo: C:\Users\gabriela\Documents\NetBeansProjects\Escalonador\src\escalonador\arquivo.txt
        
        //escalonador.mostraProcessos();
        try {
        	escalonador.escalonamento();
        }catch(Exception e) {
        	System.out.println("deu ruim aqui! "+ e);
        }

        
    }
}
