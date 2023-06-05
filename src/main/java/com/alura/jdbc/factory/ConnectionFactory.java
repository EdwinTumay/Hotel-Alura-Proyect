package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource dataSourse;
	
	public ConnectionFactory() {
		var	pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("#Larryteamo1");
		pooledDataSource.setMaxPoolSize(10);
		
		this.dataSourse = pooledDataSource;
		}

	public Connection recuperaConexion() {
		try {
			return this.dataSourse.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
