package com.jachs.hbase.mapper;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jachs.entity.People;
import com.jachs.mapper.PeopleMapper;
import com.navercorp.pinpoint.common.hbase.HbaseOperations2;

/**
 * @author zhanchaohan
 * 
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:/applicationContext-web-hbase.xml" } )
public class Demo {
	 @Autowired
	 private HbaseOperations2 hbaseOperations2;
	 
	 private static final String TABLENAME="people";//表名
	 
	 private static final String ROW="tKey02";//行key
	 
	 //添加数据
	 @Test
	 public void test1() {
		 Put put=new Put(ROW.getBytes());
		 
		 put.add("info".getBytes(),"info:name".getBytes(),"jchde".getBytes());
		 put.add("info".getBytes(),"info:age".getBytes(),"20".getBytes());
		 
		 
		 hbaseOperations2.put(TableName.valueOf (TABLENAME), put);
	 }
	 //根据row key查询数据
	 @Test
	 public void tect3() {
		People peo= hbaseOperations2.get(TableName.valueOf (TABLENAME), ROW.getBytes(), new PeopleMapper());
		
		System.out.println(peo.toString());
	 }
}
