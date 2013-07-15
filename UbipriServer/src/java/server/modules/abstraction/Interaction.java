/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.abstraction;

import java.util.logging.Level;
import java.util.logging.Logger;
import server.modules.localcommunication.LocalCommunicationSerialUSB;
import server.modules.service.EnvironmentService;
import server.util.Config;


/**
 *
 * @author Estudo
 */
public class Interaction {
   private EnvironmentService service;
   private LocalCommunicationSerialUSB usb;
   
   public Interaction(EnvironmentService service,LocalCommunicationSerialUSB localCommunication){
       this.service = service;
       usb = localCommunication;
   }

   public void start() {
        try {
            // executar uma fução para verificar se está funcionando a placa
            // Executar o listner 
            usb.connect();
        } catch (Exception ex) {
            Logger.getLogger(Interaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean handleInput(String input){
        if(Config.debugInteracao) System.out.println("Entrada interação:" +input);
        try{
                // Verifica se possui símbolo de entrada e de saída
            if(input.charAt(0) =='[' && input.charAt(input.length()-1)==']'){
            //if(input.charAt(input.length()-1)==']'){
                if(Config.debugInteracao)System.out.println("m1");
                String[] parte = input.split(","); // 0 - operação / 1 - formato dos dados / 2 - dados
                if(Config.debugInteracao) System.out.println("m2");
                switch(parte[0].charAt(1)){ //Operação u - atualização
                    case 'u': // Atualização (update)
                        if(parte[1].charAt(0)=='d'){ // Somente dados puros
                            if(Config.debugInteracao)System.out.println("m3");
                            onlyHandlesData(parte[2]);// manipula os dados // onlyHandlesData();
                            if(Config.debugInteracao)System.out.println("Interação: Sucesso ao tratar mensagem.");
                            return true;
                        } else if(parte[1].charAt(0) == 'x'){} // Se XML
                        else if(parte[1].charAt(0) == 'j'){} // Se JSON
                    break;
                    case 'r': // Leitura (read)
                    break;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        } catch(Exception e){
            System.out.println("Interação, Erro ao Tratar mensagem: "+e+" mes: "+e.getMessage()+" - "+e.getLocalizedMessage());
        }
        return true;
    }
    
    public void onlyHandlesData(String data){ // onlyHandlesData();
        String dados = "";
        char[] hex = data.toCharArray();
        
        for(int i = 0; i <hex.length-1 ;i++){ // o último é uma colchete, por isso -1
            //if(hex[i] == '-' && hex[i+2] == '-') {
            //    dados += "0"; // entradas com zero na frente não são mostradas
            //}
            dados += hex[i];
        }
        if(Config.debugInteracao) {
            System.out.println("Dados: "+dados);
        }
        service.newReading(dados);
    }
}
