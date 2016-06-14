/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbcimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import persistence.DaoAnimal;
import persistence.DaoFactory;
import persistence.PersistenceException;

/**
 * Crea los metodos necesarios para la impementacion de JDBC para acceder a la base de datos
 * @author Carlos Valencia Y Cesar Vega
 */
public class JDBCDaoFactory extends DaoFactory{
    private static final ThreadLocal<Connection> connectionInstance = new ThreadLocal<Connection>() {
    };

    private static Properties appProperties = null;

    public JDBCDaoFactory(Properties appProperties) {
        this.appProperties = appProperties;
    }

    private static Connection openConnection() throws PersistenceException {
        String url = appProperties.getProperty("url");
        String driver = appProperties.getProperty("driver");
        String user = appProperties.getProperty("user");
        String pwd = appProperties.getProperty("pwd");

        try {
            Class.forName(driver);
            Connection _con = DriverManager.getConnection(url, user, pwd);
            _con.setAutoCommit(false);
            return _con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new PersistenceException("Error on connection opening.", ex);
        }

    }
    
    @Override
    public void beginSession() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){            
                connectionInstance.set(openConnection());
            }
            else{
                throw new PersistenceException("Session was already opened.");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while opening a JDBC connection.",ex);
        }
        
    }

    @Override
    public void endSession() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().close();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().commit();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }        
    }
    
    @Override
    public void rollbackTransaction() throws PersistenceException {
        try {
            if (connectionInstance.get()==null || connectionInstance.get().isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                connectionInstance.get().rollback();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }
    
    
    
    /**
     * Clase encargada de traer de la capa de persistencia los datos correspondientes al animal
     * @return DaoAnimal 
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    @Override
    public DaoAnimal getDaoAnimal() throws PersistenceException{
        return new JDBCDaoAnimal(connectionInstance.get());
    }
    
}

    

