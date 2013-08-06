/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import server.model.CommunicationType;
import server.model.Device;
import server.model.DeviceCommunication;
import server.model.DeviceType;
import server.model.Functionality;
import server.model.User;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class DeviceDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public DeviceDAO() {
        db = new AccessBD(
                Config.dbServer, // IP do Servidor
                Config.dbName, // Nome do Banco de dados
                Config.dbUser, // Usuário
                Config.dbPassword); // Senha
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public DeviceDAO(AccessBD db) {
        this.db = db;
    }

    /**
     *  retorna a conexão que está sendo usada pela classe
     * 
     * @param @return 
     */
    public Connection getConnection() {
        return db.getConnection();
    }

    /**
     * insere um device no banco de dados.
     *
     * @param
     * @return
     */
    public void insert(Device device) {
        String sql =
                " INSERT INTO device (dev_code,dev_name,device_type_id,user_id)  VALUES (?,?,?,?) ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, device.getCode());
            pstmt.setString(2, device.getName());
            pstmt.setInt(3, device.getDeviceType().getId());
            pstmt.setInt(4, device.getUser().getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    /**
     * Remove um registro no banco de dados através do id passado pelo objeto.
     * Ao menos o objeto deve conter o ID do device a ser removido.
     *
     * @param
     * @return void
     *
     */
    public void delete(Device device) {
        String sql =
                " DELETE FROM device WHERE dev_id = ? ; ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, device.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    /**
     * Atualiza um registro no banco de dados, somente serão atualizados os
     * dados da classe, não atualizando os demais objetos que são agregados ou
     * que compõem ele.
     *
     * @param um device
     * @return void
     */
    public void update(Device device) {
        String sql =
                " UPDATE device SET dev_code = ? , dev_name = ? , device_type_id = ? , user_id = ? "
                + " WHERE dev_id = ? ; ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, device.getCode());
            pstmt.setString(2, device.getName());
            pstmt.setInt(3, device.getDeviceType().getId());
            pstmt.setInt(4, device.getUser().getId());
            pstmt.setInt(5, device.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    /**
     * passa-se por parâmetro dois argumentos: 1) o id do device que será
     * retornado; 2) se true retorna além dos dados do device suas comunicações
     * e funcionalidades (Não implementado ainda)
     *
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public Device get(int id, boolean allData) {
        Device device = null;
        User user = null;
        String sql = "";
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT dev_code,dev_name,device_type_id,user_id,devtyp_name , use_full_name, use_name "
                    + " FROM device, device_type, users "
                    + " WHERE device_type_id = devtyp_id AND use_id = user_id AND dev_id = ? ;";
        } else {

            sql =
                    " SELECT dev_code,dev_name,device_type_id,user_id,devtyp_name , use_full_name, use_name "
                    + " FROM device, device_type, users "
                    + " WHERE device_type_id = devtyp_id AND use_id = user_id AND dev_id = ? ;";
        }
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                device = new Device();
                user = new User();
                device.setId(id); // Seta o ID
                device.setName(rs.getString("dev_name"));
                device.setCode(rs.getString("dev_code"));
                device.setDeviceType(new DeviceType(rs.getInt("device_type_id"), rs.getString("devtyp_name")));
                user.setId(rs.getInt("user_id"));
                user.setFullName(rs.getString("use_full_name"));
                user.setUserName(rs.getString("use_name"));
                device.setDeviceCommunications(getDeviceCommunications(device.getId()));
                device.setUser(user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return device;
    }
/**
     * passa-se por parâmetro o device code do dispositivo (não é o ID)
     *
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public Device get(String deviceCode) {
        Device device = null;
        User user = null;
        String sql = "";

            sql =
                    " SELECT dev_id, dev_name,device_type_id,user_id,devtyp_name , use_full_name, use_name "
                    + " FROM device, device_type, users "
                    + " WHERE device_type_id = devtyp_id AND use_id = user_id AND dev_code = ? ;";
        
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, deviceCode);
            rs = pstmt.executeQuery();
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                device = new Device();
                user = new User();
                device.setId(rs.getInt("dev_id")); // Seta o ID
                device.setName(rs.getString("dev_name"));
                device.setCode(deviceCode);
                device.setDeviceType(new DeviceType(rs.getInt("device_type_id"), rs.getString("devtyp_name")));
                user.setId(rs.getInt("user_id"));
                user.setFullName(rs.getString("use_full_name"));
                user.setUserName(rs.getString("use_name"));
                device.setDeviceCommunications(getDeviceCommunications(device.getId()));
                device.setUser(user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return device;
    }
    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<Device> getList() {
        return getList(-1, -1, false);
    }

    /**
     * Retorna os registros cadastrados no banco de dados a partir de parâmetro de paginação.
     * 
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<Device> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os devices que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<Device> getList(int begin, int limit, boolean all) {
        ArrayList<Device> list = null;
        Device device = null;
        User user = null;
        String sql = "";

        this.db.conectar();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = "SELECT dev_id, dev_code, dev_name, device_type_id, user_id, devtyp_name, use_full_name, use_name "
                        + " FROM device, device_type, users WHERE device_type_id = devtyp_id AND use_id = user_id ; ";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = "SELECT dev_id, dev_code, dev_name, device_type_id, user_id, devtyp_name, use_full_name, use_name "
                        + " FROM device, device_type, users WHERE device_type_id = devtyp_id AND use_id = user_id LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }
            

            rs = pstmt.executeQuery();
            list = new ArrayList<Device>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                device = new Device();
                user = new User();
                device.setId(rs.getInt("dev_id")); // Seta o ID
                device.setName(rs.getString("dev_name"));
                device.setCode(rs.getString("dev_code"));
                device.setDeviceType(new DeviceType(rs.getInt("device_type_id"), rs.getString("devtyp_name")));
                user.setId(rs.getInt("user_id"));
                user.setFullName(rs.getString("use_full_name"));
                user.setUserName(rs.getString("use_name"));
                device.setUser(user);
                device.setDeviceCommunications(getDeviceCommunications(device));
                list.add(device);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return list;
    }
    
    /**
     * retorna todos os devices do usuário cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<Device> getUserDevices(User user){
        return getUserDevices(user, -1,-1,false);
    }
    
    /**
     * Retorna os devices do usuário cadastrados no banco de dados a partir de parâmetro de paginação.
     * 
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<Device> getUserDevices(User user,int begin, int limit){
        return getUserDevices(user,begin,limit,true);
    }
    
     /**
     * Retorna todos os devices do usuário passado pelo parâmetro que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<Device> getUserDevices(User user,int begin, int limit, boolean all) {
        ArrayList<Device> list = null;
        Device device = null;
        String sql = "";

        this.db.conectar();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT dev_id, dev_code,dev_name, device_type_id, user_id, devtyp_name" +
                        " FROM device,device_type " +
                        " WHERE device_type_id = devtyp_id AND user_id = ? ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT dev_id, dev_code,dev_name, device_type_id, user_id, devtyp_name" +
                        " FROM device,device_type " +
                        " WHERE device_type_id = devtyp_id AND user_id = ? " +
                        " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(2, limit);
                pstmt.setInt(3, (begin-1));
            }
            pstmt.setInt(1, user.getId());

            rs = pstmt.executeQuery();
            list = new ArrayList<Device>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                device = new Device();
                device.setId(rs.getInt("dev_id")); // Seta o ID
                device.setName(rs.getString("dev_name"));
                device.setCode(rs.getString("dev_code"));
                device.setDeviceType(new DeviceType(rs.getInt("device_type_id"), rs.getString("devtyp_name")));
                device.setUser(user);
                device.setDeviceCommunications(getDeviceCommunications(device));
                list.add(device);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return list;
    }
    
    public ArrayList<DeviceCommunication> getDeviceCommunications(Device device){
        DeviceDAO dao = new DeviceDAO();
        return dao.getDeviceCommunications(device.getId());
    }
    
    public ArrayList<DeviceCommunication> getDeviceCommunications(int deviceId){
        ArrayList<DeviceCommunication> list = null;
        DeviceCommunication comm = null;
        
        this.db.conectar();
        try {
            
           String sql = " SELECT devcom_id, devcom_name, devcom_address, devcom_address_format, devcom_port, devcom_preferred_order, "
                            + " devcom_parameters, communication_type_id, comtyp_name "+
                        " FROM device_communication, communication_type "+
                        " WHERE communication_type_id = comtyp_id AND device_id = ? " +
                        " ORDER BY devcom_preferred_order;";

           pstmt = getConnection().prepareStatement(sql);
     
            pstmt.setInt(1, deviceId);

            rs = pstmt.executeQuery();

            list = new ArrayList<DeviceCommunication>();
            
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                comm = new DeviceCommunication();
                comm.setId(rs.getInt("devcom_id")); // Seta o ID
                comm.setName(rs.getString("devcom_name"));
                comm.setAddress(rs.getString("devcom_address"));
                comm.setAddressFormat(rs.getString("devcom_address_format"));
                comm.setPort(rs.getString("devcom_port"));
                comm.setPreferredOrder(rs.getInt("devcom_preferred_order"));
                comm.setParameters(rs.getString("devcom_parameters"));
                comm.setCommunicationType(
                        new CommunicationType(rs.getInt("communication_type_id"), rs.getString("comtyp_name")));

                list.add(comm);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return list;
    }
    
    public ArrayList<Functionality> getDeviceFunctionalities(int deviceId){
        ArrayList<Functionality> list = null;

        
        this.db.conectar();
        try {
            
           String sql = " SELECT fun_id, fun_name FROM functionality, device_functionality "+
                        " WHERE fun_id = functionality_id AND device_id = ? "
                        + " ORDER BY fun_id ;";

           pstmt = getConnection().prepareStatement(sql);
     
            pstmt.setInt(1, deviceId);

            rs = pstmt.executeQuery();

            list = new ArrayList<Functionality>();
            
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                list.add(new Functionality(rs.getInt("fun_id"),rs.getString("fun_name")));
                
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return list;
    }
    
    public void updateCurrentEnvironment(Device device) {
        this.updateCurrentEnvironment(device.getCode(), device.getCurrentEnvironment() == null ? -1 : device.getCurrentEnvironment().getId());
    }
    
    public void updateCurrentEnvironment(String uniqueDeviceCode,int newEnvironmentId) {
        String sql =
                " UPDATE device SET current_environment_id = ? WHERE dev_code = ? ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, newEnvironmentId);
            pstmt.setString(2,uniqueDeviceCode);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    public boolean isDeviceRegistered(String deviceCode) {
        String sql = " SELECT dev_code "
                    + " FROM device "
                    + " WHERE dev_code = ? ;";
        
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, deviceCode);
            rs = pstmt.executeQuery();
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                if(rs.getString("dev_code").equals(deviceCode)){
                    rs.close();
                    pstmt.close();
                    this.db.desconectar();
                    return true;
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return false;
    }

    public boolean hasChangedDeviceEnvironment(String deviceCode, int environmentId) {
        String sql = " SELECT current_environment_id "
                    + " FROM device "
                    + " WHERE dev_code = ? ;";
        
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, deviceCode);
            rs = pstmt.executeQuery();
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo registro, no caso o 1º registro
                if(rs.getInt("current_environment_id") != environmentId){
                    rs.close();
                    pstmt.close();
                    this.db.desconectar();
                    return true;
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return false;
    }
    
    public boolean onInsertCommunicationCode(Device dev, String communicationCode, int communicationType, int communicationId) {
        
        // Verifica se está especificado o id da comunicação
        if(communicationId > 0){
            // se sim atualiza diretamente a comunicação do dispositivo
            // não implementado
            return true;
        }    
            // se não, verifica se o device possui o tipo alvo - GCM
        if(communicationType == 4 ) // GOOGLE CLOUD MESSAGE
        {
            // pega o preferido, se não houver preferência pega o primeiro
            if(dev.getPreferredDeviceCommunication().getCommunicationType().getId() == 4){
                this.updateCommunicationCode(dev.getPreferredDeviceCommunication(),communicationCode);
                return true;
            } else {
                for(DeviceCommunication c : dev.getDeviceCommunications()){
                    if(c.getCommunicationType().getId() == 4){
                        // atualiza no banco
                        this.updateCommunicationCode(c,communicationCode);
                        return true;
                    }
                }
            }
            // se não possui nenhum, cria uma nova e adiciona como preferido
            // não implementado
            DeviceCommunication devcomm = new DeviceCommunication();
            devcomm.setAddress("AIzaSyDAVL0xUjF4i0k0FhpzZA4owWsUdBNPySY"); // API Google Gloud Message Bruno
            devcomm.setParameters(communicationCode);
            devcomm.setName("GCM "+dev.getName());
            devcomm.setCommunicationType(new CommunicationType(communicationType, "GCM"));
            this.insertNewCommunicationCode(dev,devcomm);
        } else {
            System.out.println("Não implementado");
            return false;
        }
        return true;
    }

    private void updateCommunicationCode(DeviceCommunication deviceCommunication, String communicationCode) {
       String sql =
                " UPDATE device_communication SET devcom_parameters = ? WHERE devcom_id = ? ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, communicationCode);
            pstmt.setInt(2,deviceCommunication.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    private void insertNewCommunicationCode(Device device, DeviceCommunication devcomm) {
        String sql =
                "INSERT INTO device_communication (devcom_name,devcom_address,devcom_parameters,communication_type_id,device_id) VALUES (?,?,?,?,?) ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, devcomm.getName());
            pstmt.setString(2, devcomm.getAddress());
            pstmt.setString(3, devcomm.getParameters());
            pstmt.setInt(4, devcomm.getCommunicationType().getId());
            pstmt.setInt(5, device.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

}
