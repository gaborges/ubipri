/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import server.model.AccessLevel;
import server.model.AccessType;
import server.model.Action;
import server.model.Environment;
import server.model.EnvironmentType;
import server.model.Functionality;
import server.model.LocalizationType;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class ActionDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public ActionDAO() {
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
    public ActionDAO(AccessBD db) {
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
    public void insert(Action o) {
        String sql =
                " INSERT INTO actions (accessLevel_id,functionality_id,custom_environment_id,act_action, "
                + " act_start_date,act_end_date,act_start_daily_interval,act_end_daily_interval) "
                + " VALUES (?,?,?,?,?,?,?,?); ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getAccessLevel().getId());
            pstmt.setInt(2, o.getFunctionality().getId());
            pstmt.setInt(3, o.getEnvironment().getId());
            pstmt.setString(4, o.getAction());
            pstmt.setTimestamp(5, o.getEndDate() == null ? null : new Timestamp(o.getStartDate().getTime()));
            pstmt.setTimestamp(6, o.getEndDate() == null ? null : new Timestamp(o.getEndDate().getTime()));
            pstmt.setInt(7, o.getStartDailyInterval());
            pstmt.setInt(8, o.getEndDailyInterval());
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
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
    public void delete(Action o) {
        String sql =
                " DELETE FROM actions WHERE act_id = ? ; ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
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
    public void update(Action o) {
        String sql =
                " UPDATE actions SET act_action = ? , act_start_date = ?, act_end_date = ? ,  "
                + " act_start_daily_interval = ? , act_end_daily_interval = ? , custom_environment_id = ? "
                + " WHERE act_id = ? ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getAction());
            pstmt.setTimestamp(2, o.getEndDate() == null ? null : new Timestamp(o.getStartDate().getTime()));
            pstmt.setTimestamp(3, o.getEndDate() == null ? null : new Timestamp(o.getEndDate().getTime()));
            pstmt.setInt(4, o.getStartDailyInterval());
            pstmt.setInt(5, o.getEndDailyInterval());
            pstmt.setInt(6, o.getEnvironment() == null ? -1 : o.getEnvironment().getId());
            pstmt.setInt(7, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.db.desconectar();
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
    public Action get(int actionId) {
        Action temp = null;
        Environment env = null;
        String sql = "";

        sql = " SELECT acclev_impact_factor, environment_type_id, access_type_id, envtyp_name, acctyp_name "
                + " FROM access_level, access_type, environment_type "
                + " WHERE environment_type_id = envtyp_id AND access_type_id = acctyp_id AND acclev_id = ?;";

        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, actionId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo registro, no caso o 1º registro
                EnvironmentDAO envDAO = new EnvironmentDAO(db);



                temp = new Action();
                temp.setAccessLevel(new AccessLevel());
                temp.getAccessLevel().setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.getAccessLevel().setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.getAccessLevel().setId(rs.getInt("access_level_id"));
                temp.getAccessLevel().setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setFunctionality(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
                temp.setEnvironment(envDAO.get(rs.getInt("custom_environment_id"), false));
                temp.setAction(rs.getString("act_action"));
                temp.setStartDate(new Date(rs.getTimestamp("act_start_date").getTime()));
                temp.setEndDate(rs.getTimestamp("act_end_date") == null ? null : new Date(rs.getTimestamp("defact_end_date").getTime()));
                temp.setStartDailyInterval(rs.getInt("act_start_daily_interval"));
                temp.setEndDailyInterval(rs.getInt("act_end_daily_interval"));
                temp.setId(actionId);

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.db.desconectar();
        return temp;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<Action> getList() {
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
    public ArrayList<Action> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até uma
     * quatidade Limit, método serve de suporte aos outros 2 lists
     *
     * @param
     * @return
     */
    private ArrayList<Action> getList(int begin, int limit, boolean all) {
        ArrayList<Action> list = null;
        Action temp = null;
        Environment env = null;
        String sql = "";

        this.db.conectar();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql =
                        " SELECT act_id,act_action,acclev_impact_factor,act_start_date,act_end_date,act_start_daily_interval,"
                        + " act_end_daily_interval, fun_name, fun_id, access_level_id, custom_environment_id, acclev_impact_factor "
                        + " environment_type_id, access_type_id, envtyp_name, acctyp_name "
                        + " FROM actions, functionality, environment_type, access_type, access_level "
                        + " WHERE environment_type_id = envtyp_id AND functionality_id = fun_id "
                        + " AND access_type_id = acctyp_id  AND access_level_id = acclev_id ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT act_id, act_action,acclev_impact_factor,act_start_date,act_end_date,act_start_daily_interval,"
                        + " act_end_daily_interval, fun_name, fun_id, access_level_id, custom_environment_id, acclev_impact_factor "
                        + " environment_type_id, access_type_id, envtyp_name, acctyp_name "
                        + " FROM actions, functionality, environment_type, access_type, access_level "
                        + " WHERE environment_type_id = envtyp_id AND functionality_id = fun_id "
                        + " AND access_type_id = acctyp_id  AND access_level_id = acclev_id "
                        + " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<Action>();
            EnvironmentDAO envDAO = new EnvironmentDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp = new Action();
                temp.setAccessLevel(new AccessLevel());
                temp.getAccessLevel().setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.getAccessLevel().setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.getAccessLevel().setId(rs.getInt("access_level_id"));
                temp.getAccessLevel().setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setFunctionality(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
                temp.setEnvironment(envDAO.get(rs.getInt("custom_environment_id"), false));
                temp.setAction(rs.getString("act_action"));
                temp.setStartDate(new Date(rs.getTimestamp("act_start_date").getTime()));
                temp.setEndDate(rs.getTimestamp("act_end_date") == null ? null : new Date(rs.getTimestamp("defact_end_date").getTime()));
                temp.setStartDailyInterval(rs.getInt("act_start_daily_interval"));
                temp.setEndDailyInterval(rs.getInt("act_end_daily_interval"));
                temp.setId(rs.getInt("act_id"));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.db.desconectar();
        return list;
    }

    public ArrayList<Action> getCustomActions(int environmentId) {
        ArrayList<Action> list = null;
        Action temp = null;
        Environment env = null;
        String sql = "";

        this.db.conectar();
        try {
            sql = " SELECT act_id,act_action,acclev_impact_factor,act_start_date,act_end_date,act_start_daily_interval,"
                    + " act_end_daily_interval, fun_name, fun_id, access_level_id, custom_environment_id, acclev_impact_factor "
                    + " environment_type_id, access_type_id, envtyp_name, acctyp_name "
                    + " FROM actions, functionality, environment_type, access_type, access_level "
                    + " WHERE environment_type_id = envtyp_id AND functionality_id = fun_id "
                    + " AND access_type_id = acctyp_id  AND access_level_id = acclev_id AND custom_environment_id = ? ;";
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentId);


            rs = pstmt.executeQuery();
            list = new ArrayList<Action>();
            EnvironmentDAO envDAO = new EnvironmentDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp = new Action();
                temp.setAccessLevel(new AccessLevel());
                temp.getAccessLevel().setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.getAccessLevel().setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.getAccessLevel().setId(rs.getInt("access_level_id"));
                temp.getAccessLevel().setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setFunctionality(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
                temp.setEnvironment(envDAO.get(rs.getInt("custom_environment_id"), false));
                temp.setAction(rs.getString("act_action"));
                temp.setStartDate(new Date(rs.getTimestamp("act_start_date").getTime()));
                temp.setEndDate(rs.getTimestamp("act_end_date") == null ? null : new Date(rs.getTimestamp("defact_end_date").getTime()));
                temp.setStartDailyInterval(rs.getInt("act_start_daily_interval"));
                temp.setEndDailyInterval(rs.getInt("act_end_daily_interval"));
                temp.setId(rs.getInt("act_id"));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.db.desconectar();
        return list;
    }
    
    public ArrayList<Action> getCustomActions(Environment environment, AccessLevel accessLevel){
        return this.getCustomActions(environment.getId(), accessLevel.getId());
    }
    
    public ArrayList<Action> getCustomActions(int environmentId, int accessLevelId) {
        ArrayList<Action> list = new ArrayList<Action>();
        Action temp = null;
        Environment env = null;
        String sql = "";

        this.db.conectar();
        try {
            sql = " SELECT act_id,act_action,acclev_impact_factor,act_start_date,act_end_date,act_start_daily_interval,"
                    + " act_end_daily_interval, fun_name, fun_id, access_level_id, custom_environment_id, acclev_impact_factor "
                    + " environment_type_id, access_type_id, envtyp_name, acctyp_name "
                    + " FROM actions, functionality, environment_type, access_type, access_level "
                    + " WHERE environment_type_id = envtyp_id AND functionality_id = fun_id "
                    + " AND access_type_id = acctyp_id  AND access_level_id = acclev_id AND custom_environment_id = ? AND access_level_id = ? ;";
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentId);
            pstmt.setInt(2, accessLevelId);
            if(Config.debugSQL) System.out.println("GET CUSTOM ACTION: "+environmentId+","+accessLevelId);
            rs = pstmt.executeQuery();
            EnvironmentDAO envDAO = new EnvironmentDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp = new Action();
                temp.setAccessLevel(new AccessLevel());
                temp.getAccessLevel().setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.getAccessLevel().setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.getAccessLevel().setId(rs.getInt("access_level_id"));
                temp.getAccessLevel().setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setFunctionality(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
                temp.setEnvironment(envDAO.get(rs.getInt("custom_environment_id"), false));
                temp.setAction(rs.getString("act_action"));
                temp.setStartDate(new Date(rs.getTimestamp("act_start_date").getTime()));
                temp.setEndDate(rs.getTimestamp("act_end_date") == null ? null : new Date(rs.getTimestamp("defact_end_date").getTime()));
                temp.setStartDailyInterval(rs.getInt("act_start_daily_interval"));
                temp.setEndDailyInterval(rs.getInt("act_end_daily_interval"));
                temp.setId(rs.getInt("act_id"));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erro Custom action: "+e);
        }
        this.db.desconectar();
        return list;
    }

    public ArrayList<Action> getList(AccessLevel accessLevel) {
        ArrayList<Action> list = null;
        Action temp = null;
        String sql = "";

        this.db.conectar();
        try {
            sql = " SELECT act_id,act_action,act_start_date,act_end_date,act_start_daily_interval, "
                    + " act_end_daily_interval, fun_name, fun_id, custom_environment_id "
                    + " FROM actions, functionality"
                    + " WHERE functionality_id = fun_id AND access_level_id = ? ;";
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, accessLevel.getId());

            rs = pstmt.executeQuery();
            list = new ArrayList<Action>();
            EnvironmentDAO envDAO = new EnvironmentDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new Action();
                temp.setAccessLevel(accessLevel);
                temp.setFunctionality(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
                temp.setEnvironment(envDAO.get(rs.getInt("custom_environment_id"), false));
                temp.setAction(rs.getString("act_action"));
                temp.setStartDate(new Date(rs.getTimestamp("act_start_date").getTime()));
                temp.setEndDate(rs.getTimestamp("act_end_date") == null ? null : new Date(rs.getTimestamp("defact_end_date").getTime()));
                temp.setStartDailyInterval(rs.getInt("act_start_daily_interval"));
                temp.setEndDailyInterval(rs.getInt("act_end_daily_interval"));
                temp.setId(rs.getInt("act_id"));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.db.desconectar();
        accessLevel.setActionsList(list);
        return list;
    }
}
