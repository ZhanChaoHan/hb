/*
 * 创建人: zhanchaohan
 */
package com.jachs.hb.hive;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddTest {
    private Connection connection;

    @Before
    public void initHbase () throws IOException {
        try {
            Class.forName ( "org.apache.hive.jdbc.HiveDriver" );
            connection = DriverManager.getConnection ( "jdbc:hive2://101.200.126.12:10000", "hive", "hive" );
        }
        catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    @After
    public void destroy () {
        try {
            connection.close ();
        }
        catch ( SQLException e ) {
            e.printStackTrace ();
        }
    }

    @Test
    public void test () {
        try {
            Statement statement = connection.createStatement ();
            ResultSet resultSet= statement.executeQuery ( "select * from jachsTestTR" );
            while(resultSet.next ()) {
                System.out.println ( resultSet.getString ( "username" ) );
            }
            resultSet.close ();
            statement.close ();
        }
        catch ( SQLException e ) {
            e.printStackTrace ();
        }

    }
}
