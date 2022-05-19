package com.jachs.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.navercorp.pinpoint.common.hbase.HBaseAdminTemplate;

/***
 * 
 * @author zhanchaohan
 *
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:/applicationContext-web-hbase.xml" } )
public class HBaseAdminTemplateDemo {
	@Autowired
	private HBaseAdminTemplate template;
	
	
	@Test
	public void test1() throws DeserializationException, IOException {
		HTableDescriptor descriptor =HTableDescriptor.parseFrom("scanTable".getBytes());
		
		template.createTable(descriptor);
	}
	
	//删表
	@Test
	public void test2() {
		template.dropTable(TableName.valueOf("people"));
	}
	
	//获取表全部列
	@Test
	public void test3() {
		HTableDescriptor descriptor= template.getTableDescriptor(TableName.valueOf("people"));
		
		for (HColumnDescriptor des : descriptor.getColumnFamilies()) {
			System.out.println(des.getNameAsString());
		}
	}
}
