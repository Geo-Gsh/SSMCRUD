package com.cn.crud.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.cn.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

//ʹ��spring����ģ���ṩ�Ĳ��������ܣ�����crud����
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//·����Ҫ�޸�  Eclipse��IDEA·���Ĳ�ͬ D:\developer_projects\SSMCRUD\WebContent\WEB-INF\dispatcherServlet-servlet.xml
@ContextConfiguration(locations= {"classpath:applicationContext.xml",
		"file:D:\\developer_projects\\SSMCRUD\\WebContent\\WEB-INF\\dispatcherServlet-servlet.xml"})
//@ContextConfiguration(locations= {"classpath:applicationContext.xml",
//		"file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MVCTest {
	@Autowired
	WebApplicationContext context;
	MockMvc mockMvc;
	@Before
	public void initMockMvc() {
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testPage() throws Exception {
		//ģ�ⷢ���������õ�����ֵ
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		//����ɹ����������л���pageInfo  ���ǿ���ȡ��������֤
		MockHttpServletRequest request= result.getRequest();
		PageInfo p=(PageInfo) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ�룺"+p.getPageNum());
		System.out.println("��ҳ�룺"+p.getPages());
		System.out.println("�ܼ�¼����"+p.getTotal());
		System.out.println("��ҳ����Ҫ������ʾ��ҳ�룺");
		int []nums=p.getNavigatepageNums();
		for(int i:nums) {
			System.out.print(" --"+i);
		}
		//��ȡԱ������
		List<Employee> list=p.getList(); 
		for(Employee employee :list) {
			System.out.println("ID:"+employee.getEmpId()+"  Name��"+employee.getEmpName());;
			
		}
		
	}
}
