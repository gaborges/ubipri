/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.localcommunication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.modules.abstraction.Interaction;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class LocalCommunicationSerialUSB {
    
    private String portName;
    private Interaction localInteraction;

    public LocalCommunicationSerialUSB(String porta) {
        super();
        this.portName = porta;
    }

    public void setLocalInteraction(Interaction sessaoLocal) {
        this.localInteraction = sessaoLocal;
    }
    
    public LocalCommunicationSerialUSB(){}
    
    void connect ( String portName ) throws Exception
    {
        System.out.println("port: "+portName);
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                
                (new Thread(new LocalCommunicationSerialUSB.SerialReader(in,localInteraction))).start();
                (new Thread(new LocalCommunicationSerialUSB.SerialWriter(out,localInteraction))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    public void connect () throws Exception{
        this.connect(portName);
    }
    
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        Interaction sessao; // interação
        
        public SerialReader ( InputStream in , Interaction sessao)
        {
            this.in = in;
            this.sessao = sessao;
        }
        
        @Override
        public void run ()
        {
            byte[] buffer = new byte[1024];
            String str = "";
            int len = -1;
            boolean test = true;
            try
            {
                while(true){
                String msg = "";
                //System.out.println("inicio...");
                while(test){    
                int available = this.in.available();
                byte chunk[] = new byte[available];
                this.in.read(chunk, 0, available);  
                msg = (new String(chunk));
                    if(!msg.isEmpty()){
                        //System.out.println("AA: "+msg);
                        str += msg;
                        if(msg.equals("]")){
                            break;
                        }
                    }
                }
                    if(Config.debugLocalCommunication) System.out.println("MSG: "+str);
                    
//                while ( ( len = this.in.read(buffer)) > -1 )
//                {
//                    try {
//                        Thread.sleep(500);
//                        
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(LocalCommunicationSerialUSB.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    msg = new String(buffer,0,len);
//                    if(!msg.isEmpty()) {
//                        //System.out.println(msg);
//                        break;
//                    }
//                    
//                }
                //System.out.println("fimm");
                    if(str != null && !str.isEmpty() && str.endsWith("]")){
                        if(sessao.handleInput(str)) {
                            if(Config.debugLocalCommunication) {System.out.println("Resposta: true"); }
                        }
                        else {
                            if(Config.debugLocalCommunication) {System.out.println("Resposta:false");}
                        }
                        
                    }
                    str = "";
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        Interaction sessao; // interação
        
        public SerialWriter ( OutputStream out , Interaction sessao)
        {
            this.out = out;
            this.sessao = sessao;
        }
        
        @Override
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
}
