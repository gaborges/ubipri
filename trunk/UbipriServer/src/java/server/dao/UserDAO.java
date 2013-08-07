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
import server.model.AccessType;
import server.model.Environment;
import server.model.User;
import server.model.UserEnvironment;
import server.util.AccessBD;
import server.util.SingleConnection;

/**
 *
 * @author Estudo
 */
public class UserDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public UserDAO() {
        db = SingleConnection.getAccessDB();
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public UserDAO(AccessBD db) {
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
    public void insert(User o) {
        String sql =
                " INSERT INTO users (use_name, use_password, use_full_name) VALUES (?,?,?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getUserName());
            pstmt.setString(2, o.getPassword());
            pstmt.setString(3, o.getFullName());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
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
    public void delete(User o) {
        String sql =
                " DELETE FROM users WHERE use_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
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
    public void update(User o) {
        String sql =
                " UPDATE users SET use_name = ? , use_password = ? , use_full_name = ? , current_environment_id = ? "
                + " WHERE use_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getUserName());
            pstmt.setString(2, o.getPassword());
            pstmt.setString(3, o.getFullName());
            pstmt.setInt(4, (o.getCurrentEnvironment() == null) ? -1 : o.getCurrentEnvironment().getId()); // se for nulo -1 senão getId
            pstmt.setInt(5, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * Atualiza um registro somente o ambiente atual do usuário.
     *
     * @param um device
     * @return void
     */
    public void updateCurrentEnvironment(User o) {
        String sql =
                " UPDATE users SET current_environment_id = ? WHERE use_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, (o.getCurrentEnvironment() == null) ? -1 : o.getCurrentEnvironment().getId());
            pstmt.setInt(2, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }
    
    public void updateCurrentEnvironment(String uniqueUserName,int environmentId) {
        String sql =
                " UPDATE users SET current_environment_id = ? WHERE use_name = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentId);
            pstmt.setString(2,uniqueUserName);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
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
    public User get(int id, boolean allData) {
        User temp = null;
        String sql = "";

        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if (id == -1) {
            return null;
        }

        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT use_name, use_password, use_full_name, current_environment_id "
                    + " FROM users "
                    + " WHERE use_id = ? ;";
        } else {

            sql =
                    " SELECT use_name, use_password, use_full_name, current_environment_id "
                    + " FROM users "
                    + " WHERE use_id = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                EnvironmentDAO envDAO = new EnvironmentDAO(db);
                temp = new User();
                temp.setId(id); // Seta o ID
                temp.setUserName(rs.getString("use_name"));
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }

    public User getByUserName(String userName, boolean allData) {
        User temp = null;
        String sql = "";

        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT use_id,use_name, use_password, use_full_name, current_environment_id "
                    + " FROM users "
                    + " WHERE use_name = ? ;";
        } else {

            sql =
                    " SELECT use_id,use_name, use_password, use_full_name, current_environment_id "
                    + " FROM users "
                    + " WHERE use_name = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                EnvironmentDAO envDAO = new EnvironmentDAO(db);
                temp = new User();
                temp.setId(rs.getInt("use_id")); // Seta o ID
                temp.setUserName(userName);
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<User> getList() {
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
    public ArrayList<User> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até uma
     * quatidade Limit, método serve de suporte aos outros 2 lists
     *
     * @param
     * @return
     */
    private ArrayList<User> getList(int begin, int limit, boolean all) {
        ArrayList<User> list = null;
        User temp = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = "SELECT use_id, use_name, use_password, use_full_name, current_environment_id "
                        + " FROM users ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT use_id, use_name, use_password, use_full_name, current_environment_id "
                        + " FROM users ORDER BY use_id "
                        + " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<User>();
            EnvironmentDAO envDAO = new EnvironmentDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp = new User();
                temp.setId(rs.getInt("use_id")); // Seta o ID
                temp.setUserName(rs.getString("use_name"));
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));

                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return list;
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
    public User getUserEnvironment(int userId, int environmentId) {
        User temp = null;
        String sql =
                " SELECT use_name, use_password, use_full_name, current_environment_id, "
                + " userenv_id,useenv_impact_factor, current_access_type_id, acctyp_name, "
                + " env_id, env_name, env_latitude, env_longitude, localization_type_id, "
                + " loctyp_name, loctyp_precision, loctyp_metric "
                + " FROM users, user_in_environment, access_type, environment, environment_type, localization_type "
                + " WHERE user_id = use_id AND acctyp_id = current_access_type_id "
                + " AND localization_type_id = loctyp_id "
                + " AND environment_id = env_id AND environment_type_id = envtyp_id  "
                + " AND environment_id = ? AND use_id = ? ;";

        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentId);
            pstmt.setInt(2, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                EnvironmentDAO envDAO = new EnvironmentDAO(db);
                UserEnvironment useEnv = new UserEnvironment();
                temp = new User();
                temp.setId(userId); // Seta o ID
                temp.setUserName(rs.getString("use_name"));
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));

                useEnv.setId(rs.getInt("userenv_id"));
                useEnv.setImpactFactor(rs.getDouble("useenv_impact_factor"));
                useEnv.setCurrentAccessType(
                        new AccessType(rs.getInt("current_access_type_id"), rs.getString("acctyp_name")));
                useEnv.setEnvironment(envDAO.get(rs.getInt("env_id"), false));
                temp.setUsersEnvironment(useEnv);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }
    public User getUserEnvironment(String userName, int environmentId) {
        User temp = null;
        String sql =
                " SELECT use_id, use_password, use_full_name, current_environment_id, environment_id, "
                + " userenv_id,useenv_impact_factor, current_access_type_id, acctyp_name, "
                + " env_id, env_name, env_latitude, env_longitude, localization_type_id, "
                + " loctyp_name, loctyp_precision, loctyp_metric "
                + " FROM users, user_in_environment, access_type, environment, environment_type, localization_type "
                + " WHERE user_id = use_id AND acctyp_id = current_access_type_id "
                + " AND localization_type_id = loctyp_id "
                + " AND environment_id = env_id AND environment_type_id = envtyp_id  "
                + " AND environment_id = ? AND use_name = ? ;";

        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentId);
            pstmt.setString(2, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                EnvironmentDAO envDAO = new EnvironmentDAO(db);
                UserEnvironment useEnv = new UserEnvironment();
                temp = new User();
                temp.setId(rs.getInt("use_id")); // Seta o ID
                temp.setUserName(userName);
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));

                useEnv.setId(rs.getInt("userenv_id"));
                useEnv.setImpactFactor(rs.getDouble("useenv_impact_factor"));
                useEnv.setCurrentAccessType(
                        new AccessType(rs.getInt("current_access_type_id"), rs.getString("acctyp_name")));
                useEnv.setEnvironment(envDAO.get(rs.getInt("environment_id"), false));
                temp.setUsersEnvironment(useEnv);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }
    
    public boolean hasAccessPermission(String userName, String userPassword){
        String sql =
                    " SELECT use_name, use_password "
                    + " FROM users "
                    + " WHERE use_name = ? AND use_password = ? ;";
       
        this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, userPassword);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                if(userName.equals(rs.getString("use_name")) && userPassword.equals(rs.getString("use_password"))) {
                    rs.close();
                    pstmt.close();
                    //this.db.disconnect();
                    return true;
                }                 
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return false;
    }
    
    public boolean hasChangedUserEnvironment(String userName, int environmentId){
        String sql = " SELECT current_environment_id "
                    + " FROM users "
                    + " WHERE use_name = ? ;";
       
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Verifica se o ambiente continua o mesmo
                if(environmentId != rs.getInt("current_environment_id")) {
                    rs.close();
                    pstmt.close();
                    //this.db.disconnect();
                    return true;
                }                 
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return false;
    }

    public User getUserAfterUpdateUserEnvironment(int userId, int newEnvironmentId) {
        User temp = null;
        String sql =
                " SELECT use_name, use_password, use_full_name, current_environment_id, "
                + " userenv_id,useenv_impact_factor, current_access_type_id, acctyp_name "
                + " FROM users, user_in_environment, access_type "
                + " WHERE user_id = use_id AND acctyp_id = current_access_type_id "
                + " AND environment_id = ? AND use_id = ? ;";

        //this.db.connect();
        try {
            temp = new User();
            temp.setId(userId); // Seta o ID
            temp.setCurrentEnvironment(new Environment());
            EnvironmentDAO envDAO = new EnvironmentDAO(db);  
            temp.getCurrentEnvironment().setId(newEnvironmentId);
            updateCurrentEnvironment(temp);
            
            
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, newEnvironmentId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp.setUserName(rs.getString("use_name"));
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("current_environment_id"), false));
                UserEnvironment useEnv = new UserEnvironment();
                useEnv.setId(rs.getInt("userenv_id"));
                useEnv.setImpactFactor(rs.getDouble("useenv_impact_factor"));
                useEnv.setCurrentAccessType(
                        new AccessType(rs.getInt("current_access_type_id"), rs.getString("acctyp_name")));
                useEnv.setEnvironment(temp.getCurrentEnvironment());
                temp.setUsersEnvironment(useEnv);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }
    
    public User getUserByDeviceCode(String deviceCode) {
        User temp = null;
        String sql =
                " SELECT use_id,use_name, use_password, use_full_name, users.current_environment_id AS cur_env_id "
                + " FROM users, device "
                + " WHERE user_id = use_id AND dev_code = ? ;";

        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, deviceCode);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new User();
                EnvironmentDAO envDAO = new EnvironmentDAO(db);
                temp.setId(rs.getInt("use_id"));
                temp.setUserName(rs.getString("use_name"));
                temp.setPassword(rs.getString("use_password"));
                temp.setFullName(rs.getString("use_full_name"));
                temp.setCurrentEnvironment(envDAO.get(rs.getInt("cur_env_id"), false));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp; 
    }
}
