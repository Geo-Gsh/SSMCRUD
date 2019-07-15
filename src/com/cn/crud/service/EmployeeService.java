package com.cn.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.crud.bean.Employee;
import com.cn.crud.bean.EmployeeExample;
import com.cn.crud.bean.EmployeeExample.Criteria;
import com.cn.crud.dao.EmployeeMapper;


@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper 	employeeMapper;
	
	//��ѯ����Ա��
	public List<Employee> getAll() {		
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {	
		employeeMapper.insertSelective(employee);  //1.insertSelective  id������  2.insert   ����id��
	}
	//�����û����Ƿ����   �Ƿ��Ѿ�ӵ����    ==0  true:����ǰ��������  false:��������
	public boolean checkUser(String empName) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count=employeeMapper.countByExample(example);
		return count==0;
	}

	/**
	 * ����id����Ա��
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee=employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	/**
	 * Ա������
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);  //����������ѡ��ĸ���   ����û�����ø���
		
	}
	/**
	 * Ա��ɾ��
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}

	public void deleteBatch(List<Integer> ids) {
		//��������
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpIdIn(ids);    //wherre empId in (1,2,3,.....) 
		employeeMapper.deleteByExample(example);//������ɾ��
		
	}

	

	

}
