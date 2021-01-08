package biz.gelicon.capital.utils;


import biz.gelicon.capital.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// https://tproger.ru/translations/useful-postgresql-commands/

// Общие методы работы с базой данных
public class DatebaseUtils {

    static Logger logger = LoggerFactory.getLogger(TableRepository.class);
    public static String dbType; // Тип текущей БД

    // Формирует текст ошибки в читаемом виде
    public static String makeErrorMessage(Exception e) {
        if (e == null) {return "Ошибка";}
        String s = e.getMessage();
        if (e instanceof DuplicateKeyException) {
            s = "Нарушение уникальности";
        } else if (e instanceof DataIntegrityViolationException) {
            s = "Нарушение ссылочной целостности. Возможно, на запись есть ссылки.";
        }
        return s;
    }

    // Формирует текст ошибки как он вернулся из базы
    public static String makeErrorMessageFull(Exception e) {
        if (e == null) {return "Ошибка";}
        return e.getMessage();
    }

    // Возвращает следующее значение генератора
    public static Integer getSequenceNextValue(
            String sequenceName,
            JdbcTemplate jdbcTemplate
    ) {
        if (sequenceName == null || jdbcTemplate == null || !isPostgreSQL()) {
            // integer	4 байта	типичный выбор для целых чисел	-2147483648 .. +2147483647
            return ThreadLocalRandom.current().nextInt(1000000000) + 1000000000;
        }
        return jdbcTemplate.queryForObject("SELECT nextval('" + sequenceName + "')", Integer.class);
    }

    // Устанавливает тип базы данных
    public static void setDbType() {
        // todo Необходима реализация полученя из application.properties
        String datasourceUrl = "jdbc:postgresql://localhost:5432/capital";
        if (dbType != null) {return;} // Уже установлена
        if (datasourceUrl == null) {return;} // Не из чего устанавливать
        if (datasourceUrl.contains("postgresql")) {
            dbType = "postgresql";
            return;
        }
        // Не смогли определить - устанавливаем по умолчанию dbType = "postgresql";
        setDbTypeDefault();
    }

    // Проверяет тип базы данных
    // Если забыли установить до этого - устанавливает
    public static String getDbType() {
        if (dbType == null) { // Еще не установлена
            // Установить
            setDbType();
        }
        // Вернуть
        return dbType;
    }

    // Тип базы данных по умолчанию - postgresql
    public static void setDbTypeDefault() {
        dbType = "postgresql";
    }

    // Проверяет, не PostgreSQL ли использутеся
    public static Boolean isPostgreSQL() {
        return getDbType().contains("postgresql");
    }

    // Устанавливает тип СУБД из jdbcTemplate
    public static void setDbType(JdbcTemplate jdbcTemplate) {
        dbType = null; // Обнулим все что было
        if (jdbcTemplate == null || jdbcTemplate.getDataSource() == null) {
            return;
        }
        Connection connection = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error("jdbcTemplate.getDataSource().getConnection() filed", e);
            throw new RuntimeException("jdbcTemplate.getDataSource().getConnection() filed", e);
        }
        String dbDriverName = null;
        try {
            dbDriverName = connection.getMetaData().getDriverName().toLowerCase();
        } catch (SQLException e) {
            logger.error("connection.getMetaData().getDriverName().toLowerCase() filed", e);
            throw new RuntimeException("connection.getMetaData().getDriverName().toLowerCase() filed", e);
        }
        if (dbDriverName != null && dbDriverName.contains("postgresql")) {
            dbType = "postgresql";
        }
    }

    // Возвращает connection из jdbcTemplate
    public static Connection getJdbcTemplateConnection(JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null || jdbcTemplate.getDataSource() == null) {
            throw new RuntimeException("Нет коннекта");
        }
        Connection connection = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error("jdbcTemplate.getDataSource().getConnection() filed", e);
            throw new RuntimeException("jdbcTemplate.getDataSource().getConnection() filed", e);
        }
        if (connection == null) {
            logger.error("connection is null");
            throw new RuntimeException("connection is null");
        }
        return connection;
    }

    // List внешних ключей таблицы
    public static List<String> getForeignKeyTable(
            String tableName,
            JdbcTemplate jdbcTemplate
    ) {
        if (tableName == null || jdbcTemplate == null) {return null;}
        Connection connection = getJdbcTemplateConnection(jdbcTemplate);
        String sqlText = ""
                + " SELECT TC.table_name, "
                + "        KCU.column_name, "
                + "        CCU.table_name, "
                + "        CCU.column_name, "
                + "        TC.constraint_name "
                + " FROM   information_schema.table_constraints TC, "
                + "        information_schema.key_column_usage KCU, "
                + "        information_schema.constraint_column_usage CCU "
                + " WHERE  TC.table_name = :table_name "
                + "   AND  TC.constraint_type = 'FOREIGN KEY' "
                + "   AND  KCU.constraint_name = TC.constraint_name  "
                + "   AND  KCU.table_schema = TC.table_schema "
                + "   AND  CCU.constraint_name = TC.constraint_name  "
                + "   AND  CCU.table_schema = TC.table_schema";
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(""
                    + " SELECT TC.table_name, "
                    + "        KCU.column_name, "
                    + "        CCU.table_name master_table_name, "
                    + "        CCU.column_name master_column_name, "
                    + "        TC.constraint_name "
                    + " FROM   information_schema.table_constraints TC, "
                    + "        information_schema.key_column_usage KCU, "
                    + "        information_schema.constraint_column_usage CCU "
                    + " WHERE  TC.table_name = ? "
                    + "   AND  TC.constraint_type = 'FOREIGN KEY' "
                    + "   AND  KCU.constraint_name = TC.constraint_name  "
                    + "   AND  KCU.table_schema = TC.table_schema "
                    + "   AND  CCU.constraint_name = TC.constraint_name  "
                    + "   AND  CCU.table_schema = TC.table_schema ");
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.add(rs.getString("table_name")
                        + "," + rs.getString("column_name")
                        + "," + rs.getString("master_table_name")
                        + "," + rs.getString("master_column_name")
                        + "," + rs.getString("constraint_name"));
            }
            ps.close();
        } catch (Exception e) {
            String errText = String.format("Retriving foreign key for table %s filed", tableName);
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        return list;
    }

    // List внешних ключей на таблицу
    public static List<String> getForeignKeyMasterTable(
            String masterTableName,
            JdbcTemplate jdbcTemplate
    ) {
        if (masterTableName == null || jdbcTemplate == null) {return null;}
        Connection connection = getJdbcTemplateConnection(jdbcTemplate);
        String sqlText = ""
                + " SELECT TC.table_name, "
                + "        KCU.column_name, "
                + "        CCU.table_name, "
                + "        CCU.column_name, "
                + "        TC.constraint_name "
                + " FROM   information_schema.table_constraints TC, "
                + "        information_schema.key_column_usage KCU, "
                + "        information_schema.constraint_column_usage CCU "
                + " WHERE  TC.table_name = :table_name "
                + "   AND  TC.constraint_type = 'FOREIGN KEY' "
                + "   AND  KCU.constraint_name = TC.constraint_name  "
                + "   AND  KCU.table_schema = TC.table_schema "
                + "   AND  CCU.constraint_name = TC.constraint_name  "
                + "   AND  CCU.table_schema = TC.table_schema";
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(""
                    + " SELECT TC.table_name, "
                    + "        KCU.column_name, "
                    + "        CCU.table_name master_table_name, "
                    + "        CCU.column_name master_column_name, "
                    + "        TC.constraint_name "
                    + " FROM   information_schema.table_constraints TC, "
                    + "        information_schema.key_column_usage KCU, "
                    + "        information_schema.constraint_column_usage CCU "
                    + " WHERE  CCU.table_name = ? "
                    + "   AND  TC.constraint_type = 'FOREIGN KEY' "
                    + "   AND  KCU.constraint_name = TC.constraint_name  "
                    + "   AND  KCU.table_schema = TC.table_schema "
                    + "   AND  CCU.constraint_name = TC.constraint_name  "
                    + "   AND  CCU.table_schema = TC.table_schema ");
            ps.setString(1, masterTableName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.add(rs.getString("table_name")
                        + "," + rs.getString("column_name")
                        + "," + rs.getString("master_table_name")
                        + "," + rs.getString("master_column_name")
                        + "," + rs.getString("constraint_name"));
            }
            ps.close();
        } catch (Exception e) {
            String errText = String.format("Retriving foreign key for master table %s filed", masterTableName);
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        return list;
    }
}
