/*
 * 创建人: zhanchaohan
 */
package com.jachs.hb.phoenix;

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
            System.setProperty ( "hadoop.home.dir", "F:\\hDriver" );
            Class.forName ( "org.apache.phoenix.jdbc.PhoenixDriver" );
            connection = DriverManager.getConnection ( "jdbc:phoenix:101.200.126.12,39.105.55.147:2181" );
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
