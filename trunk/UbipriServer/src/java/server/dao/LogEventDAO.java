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
import java.sql.Timestamp;
import java.util.ArrayList;
import server.model.LogEvent;
import server.util.AccessBD;
import server.util.SingleConnection;

/**
 *
 * @author Guazzelli
 */
public class LogEventDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public LogEventDAO() {
        db = SingleConnection.getAccessDB();
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public LogEventDAO(AccessBD db) {
        this.db = db;
    }

    /**
     * retorna a conexão que está sendo usada pela classe
     *
     * @param
     * @return
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
    public void insert(LogEvent o) {
        String sql
                = " INSERT INTO log_event (log_time,log_shift,log_weekday,log_workday,"
                + "log_event,log_code,user_id,device_id,environment_id) VALUES (?,?,?,?,?,?,?,?,?) ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(o.getTime().getTime()));
            pstmt.setString(2, o.getShift().toString());
            pstmt.setInt(3, o.getWeekday());
            pstmt.setString(4, o.getWorkday().toString());
            pstmt.setString(5, o.getEvent());
            pstmt.setString(6, o.getCurrentData());
            pstmt.setInt(7, o.getUser().getId());
            pstmt.setInt(8, o.getDevice().getId());
            pstmt.setInt(9, o.getEnvironment().getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ".Method: Insert. Exception: " + e);
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
    public void delete(LogEvent o) {
        String sql
                = " DELETE FROM log_event WHERE log_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Method: Delete. Exception: " + e);
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
    public void update(LogEvent o) {
        String sql
                = " UPDATE log_event SET log_time = ?, log_shift = ?, log_weekday = ?, "
                + " log_workday = ?, log_event = ?, log_code = ?, user_id = ?, device_id = ?, environment_id = ? "
                + " WHERE log_id = ? ;";
        //this.db.connect();
        System.out.println("Debug: " + o.toString());
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(o.getTime().getTime()));
            pstmt.setString(2, o.getShift().toString());
            pstmt.setInt(3, o.getWeekday());
            pstmt.setString(4, o.getWorkday().toString());
            pstmt.setString(5, o.getEvent());
            pstmt.setString(6, o.getCurrentData());
            pstmt.setInt(7, o.getUser().getId());
            pstmt.setInt(8, o.getDevice().getId());
            pstmt.setInt(9, o.getEnvironment().getId());
            pstmt.setInt(10, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Method: update. Exception: " + e);
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
    public LogEvent get(int id, boolean allData) {
        LogEvent log_event = new LogEvent();
        String sql = "";
        UserDAO useDAO = new UserDAO(db);
        DeviceDAO devDAO = new DeviceDAO(db);
        EnvironmentDAO envDAO = new EnvironmentDAO(db);

        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if (id == -1) {
            return null;
        }

        if (allData) {
            // não implementado ainda
            sql
                    = " SELECT log_time, log_shift, log_weekday, log_workday, log_event, log_code, user_id, device_id, environment_id "
                    + " FROM log_event"
                    + " WHERE log_id = ?;";
        } else {

            sql
                    = " SELECT log_time, log_shift, log_weekday, log_workday, log_event, log_code, user_id, device_id, environment_id "
                    + " FROM log_event"
                    + " WHERE log_id = ?;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet localrs = pstmt.executeQuery();
            if (localrs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                log_event = new LogEvent();
                log_event.setId(id); // Seta o ID
                log_event.setTime(localrs.getDate("log_time"));
                log_event.setShift(localrs.getString("log_shift").charAt(0));
                log_event.setWeekday(localrs.getInt("log_weekday"));
                log_event.setWorkday(localrs.getString("log_workday").charAt(0));
                log_event.setEvent(localrs.getString("log_event"));
                log_event.setCurrentData(localrs.getString("log_code"));
                log_event.setUser(useDAO.get(localrs.getInt("user_id"), false));
                log_event.setDevice(devDAO.get(localrs.getInt("device_id"), false));
                log_event.setEnvironment(envDAO.get(localrs.getInt("environment_id"), false));
            }
            localrs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ".Method: Get. Exception: " + e);
        }
        //this.db.disconnect();
        return log_event;
    }

    private LogEvent getExclusive(int id, boolean allData) {
        LogEventDAO dao = new LogEventDAO();
        return dao.get(id, allData);
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<LogEvent> getList() {
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
    public ArrayList<LogEvent> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os devices que começam pelo índice Begin até uma quatidade
     * Limit, método serve de suporte aos outros 2 lists
     *
     * @param
     * @return
     */
    private ArrayList<LogEvent> getList(int begin, int limit, boolean all) {
        ArrayList<LogEvent> list = null;
        LogEvent log_event = new LogEvent();
        String sql = "";
        UserDAO useDAO = new UserDAO(db);
        DeviceDAO devDAO = new DeviceDAO(db);
        EnvironmentDAO envDAO = new EnvironmentDAO(db);

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT log_id, log_time, log_shift, log_weekday, log_workday, log_event, log_code, user_id, device_id, environment_id "
                        + " FROM log_event"
                        + " ORDER BY log_id;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT log_id, log_time, log_shift, log_weekday, log_workday, log_event, log_code, user_id, device_id, environment_id "
                        + " FROM log_event"
                        + " ORDER BY log_id;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<LogEvent>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                log_event = new LogEvent();
                log_event.setId(rs.getInt("log_id")); // Seta o ID
                log_event.setTime(rs.getDate("log_time"));
                log_event.setShift(rs.getString("log_shift").charAt(0));
                log_event.setWeekday(rs.getInt("log_weekday"));
                log_event.setWorkday(rs.getString("log_workday").charAt(0));
                log_event.setEvent(rs.getString("log_event"));
                log_event.setCurrentData(rs.getString("log_code"));
                log_event.setUser(useDAO.get(rs.getInt("user_id"), false));
                log_event.setDevice(devDAO.get(rs.getInt("device_id"), false));
                log_event.setEnvironment(envDAO.get(rs.getInt("environment_id"), false));
                list.add(log_event);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ".Method:getList. Exception: " + e);
        }
        //this.db.disconnect();
        return list;
    }
}
