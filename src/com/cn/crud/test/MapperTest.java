package com.cn.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.crud.bean.Department;
import com.cn.crud.bean.Employee;
import com.cn.crud.dao.DepartmentMapper;
/*
 * ����dao��Ĺ���
 */
//1.����springTestģ��
//2.ʹ��@ContextConfigurationע��ָ��spring�����ļ���λ��
//3.ֱ��autowiredҪʹ�õ����
import com.cn.crud.dao.EmployeeMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;  //��������
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD() {
	//1.����springIOC����
	//	ApplicationContext ioc=new ClassPathXmlApplicationContext("applicationContext.xml");
	//2.�������л�ȡMapper
	//	DepartmentMapper bean= ioc.getBean(DepartmentMapper.class);
		System.out.println(departmentMapper);
	//1.���뼸������
		//departmentMapper.insertSelective(new Department(null,"������02"));
		//departmentMapper.insertSelective(new Department(null, "���Բ�")); 
	//2.����Ա�����ݣ�����Ա������
		//employeeMapper.insertSelective(new Employee(null,"Jerry","M","123@.com",3));
		//employeeMapper.insertSelective(new Employee(null,"Tom","M","122223@.com",3));
	//3.����������Ա��    ʹ�ÿ���ִ������������sqlSession 
		EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
		for(int i=0;i<100;i++) {
			String id=UUID.randomUUID().toString().substring(0, 5)+""+i;
			mapper.insertSelective(new Employee(null,id,i%2==0?"M":"F",id+"@123.com",1));		
		}
		System.out.println("����ִ�����!!!");
	}
}
