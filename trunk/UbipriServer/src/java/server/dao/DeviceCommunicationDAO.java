/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.model.CommunicationType;
import server.model.Device;
import server.model.DeviceCommunication;
import server.util.AccessBD;
import server.util.Config;
import server.dao.CommunicationTypeDAO;

/**
 *
 * @author Guazzelli
 */
public class DeviceCommunicationDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public DeviceCommunicationDAO() {
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
    public DeviceCommunicationDAO(AccessBD db) {
        this.db = db;
    }

    /**
     * retorna a conexão que está sendo usada pela classe
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
    public void insert(DeviceCommunication o) {
        String sql
                = " INSERT INTO device_communication (devcom_name,devcom_address,devcom_parameters,devcom_address_format,devcom_port,device_id,communication_type_id,devcom_preferred_order) VALUES (?,?,?,?,?,?,?,?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setString(2, o.getAddress());
            pstmt.setString(3, o.getParameters());
            pstmt.setString(4, o.getAddressFormat());
            pstmt.setString(5, o.getPort());
            pstmt.setInt(6, 1);
            pstmt.setInt(7, o.getCommunicationType().getId());
            pstmt.setInt(8, o.getPreferredOrder());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
    }

    /**
     * Remove um registro no banco de dados através do id passado pelo objeto.
     * Ao menos o objeto deve conter o ID do device a ser removido.
     *
     * @param
     * @return void
     *
     */
    public void delete(DeviceCommunication o) {
        String sql
                = " DELETE FROM device_communication WHERE devcom_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
    }

    /**
     * Atualiza um registro no banco de dados, somente serão atualizados os
     * dados da classe, não atualizando os demais objetos que são agregados ou
     * que compõem ele.
     *
     * @param um device
     * @return void
     */
    public void update(DeviceCommunication o) {
        String sql
                = " UPDATE device_communication SET devcom_name = ?, devcom_address = ?, devcom_parameters = ?, devcom_address_format = ?, devcom_port = ?, device_id = ?, communication_type_id = ?, devcom_preferred_order = ? "
                + " WHERE devcom_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setString(2, o.getAddress());
            pstmt.setString(3, o.getParameters());
            pstmt.setString(4, o.getAddressFormat());
            pstmt.setString(5, o.getPort());
            pstmt.setInt(6, 1);
            pstmt.setInt(7, o.getCommunicationType().getId());
            pstmt.setInt(8, o.getPreferredOrder());
            pstmt.setInt(9, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
    }

    /**
     * passa-se por parâmetro dois argumentos: <ul> <li> 1) o id do device que
     * será retornado;</li> <li>2) se true retorna além dos dados do device suas
     * comunicações e funcionalidades (Não implementado ainda) </li>
     * </ul>
     *
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public DeviceCommunication get(int id, boolean allData) {
        DeviceCommunication devcom = null;
        String sql = "";

        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if (id == -1) {
            return null;
        }

        if (allData) {
            // não implementado ainda
            sql = " SELECT devcom_name,devcom_address,devcom_parameters,devcom_address_format,devcom_port,device_id,communication_type_id,devcom_preferred_order,comtyp_name"
                    + " FROM device_communication, communication_type"
                    + " WHERE communication_type_id = comtyp_id AND devcom_id = ?";
        } else {

            sql = " SELECT devcom_name,devcom_address,devcom_parameters,devcom_address_format,devcom_port,device_id,communication_type_id,devcom_preferred_order,comtyp_name"
                    + " FROM device_communication, communication_type"
                    + " WHERE communication_type_id = comtyp_id AND devcom_id = ?";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet localrs = pstmt.executeQuery();
            if (localrs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                devcom = new DeviceCommunication();
                devcom.setId(id); // Seta o ID
                devcom.setName(localrs.getString("devcom_name"));
                devcom.setAddress(localrs.getString("devcom_address"));
                devcom.setParameters(localrs.getString("devcom_parameters"));
                devcom.setAddressFormat(localrs.getString("devcom_address_format"));
                devcom.setPort(localrs.getString("devcom_port"));
                /*devcom.setDevice(
                 new Device(localrs.getInt(""), localrs.getString(""),localrs.getDouble(""), localrs.getString("")));*/
                devcom.setCommunicationType(
                        new CommunicationType(localrs.getInt("communication_type_id"), localrs.getString("comtyp_name")));
                devcom.setPreferredOrder(localrs.getInt("devcom_preferred_order"));

            }
            localrs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ".Method: Get. Exception: " + e);
        }
        //this.db.disconnect();
        return devcom;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<DeviceCommunication> getList() {
        return getList(-1, -1, false);
    }

    /**
     * Retorna os registros cadastrados no banco de dados a partir de parâmetro
     * de paginação.
     *
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<DeviceCommunication> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até uma
     * quatidade Limit, método serve de suporte aos outros 2 lists
     *
     * @param @return
     */
    private ArrayList<DeviceCommunication> getList(int begin, int limit, boolean all) {
        ArrayList<DeviceCommunication> list = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT devcom_id,devcom_name,devcom_address,devcom_parameters,devcom_address_format,devcom_port,device_id,communication_type_id,devcom_preferred_order,comtyp_name"
                        + " FROM device_communication, communication_type"
                        + " WHERE communication_type_id = comtyp_id"
                        + " ORDER BY devcom_id;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT devcom_id,devcom_name,devcom_address,devcom_parameters,devcom_address_format,devcom_port,device_id,communication_type_id,devcom_preferred_order"
                        + " FROM device_communication "
                        + " WHERE communication_type_id = comtyp_id LIMIT ? OFFSET ?"
                        + " ORDER BY devcom_id;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<DeviceCommunication>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                DeviceCommunication devcom = new DeviceCommunication();
                devcom.setId(rs.getInt("devcom_id")); // Seta o ID
                devcom.setName(rs.getString("devcom_name"));
                devcom.setAddress(rs.getString("devcom_address"));
                devcom.setParameters(rs.getString("devcom_parameters"));
                devcom.setAddressFormat(rs.getString("devcom_address_format"));
                devcom.setPort(rs.getString("devcom_port"));
                /*devcom.setDevice(
                 new Device(localrs.getInt(""), localrs.getString(""),localrs.getDouble(""), localrs.getString("")));*/
                devcom.setCommunicationType(
                        new CommunicationType(rs.getInt("communication_type_id"), rs.getString("comtyp_name")));
                devcom.setPreferredOrder(rs.getInt("devcom_preferred_order"));
                list.add(devcom);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
        return list;
    }
}
