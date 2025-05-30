package com.arekalov.jsfgraph.db;

/**
 * Factory class for data access object (DAO) instances.
 */
public class DAOFactory {
    private static ResultDAO resultDAO;

    private static DAOFactory instance;

    public static DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public ResultDAO getResultDAO() {
        if (resultDAO == null)
            resultDAO = new ResultDAOImpl();
        return resultDAO;
    }
}
