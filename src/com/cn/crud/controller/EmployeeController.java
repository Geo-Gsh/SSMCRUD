package com.cn.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
//import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.crud.bean.Employee;
import com.cn.crud.bean.Msg;
import com.cn.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;	
	
	
	/**
	 * ����+����  ɾ��
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids) {
		if(ids.contains("-")) {//����ɾ��
			String []str_ids=ids.split("-");
			List<Integer> del_ids=new ArrayList<Integer>();
			//��װids����
			for(String s:str_ids) {
				del_ids.add(Integer.parseInt(s));
			}
			employeeService.deleteBatch(del_ids);//����ɾ��
			return Msg.success();
		}else {     			//����ɾ��
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
			return Msg.success();
		}
		
	}
	
	/**
	 * ����Ա����Ϣ
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp_update/{empId}",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		System.out.println("���ݣ�"+employee.getEmail());
		System.out.println("���ݣ�"+employee.getEmpGender());
		System.out.println("���ݣ�"+employee.getEmpName());
		System.out.println("���ݣ�"+employee.getDeptId());
		System.out.println("���ݣ�"+employee.getEmpId());
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/**
	 * ����id��ѯԱ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee =employeeService.getEmp(id); 
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * ����û����Ƿ����
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkUser(@RequestParam("empName")String empName) {
		//���ж��û����Ƿ��ǺϷ��ı��ʽ
		String regempName="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regempName)) {
			return Msg.fail().add("va_msg", "�û���������2-5λ���Ļ���6-16λӢ�ĺ����ֻ����»��ߵ����!!!"); 
		}
		
		//���ݿ��û����ظ���֤
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.success();  //100
		}else {
			return Msg.fail().add("va_msg", "�û���������!!!");
		}
		
	}
	
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)     //�涨POST����Ϊ����
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {   //@Valid :У�� 
		if(result.hasErrors()) {
			List <FieldError> errors=result.getFieldErrors();
			Map <String ,Object>map=new HashMap <String, Object>();
			for(FieldError fieldError:errors) {
				System.out.println("������ֶ���:"+fieldError.getField());
				System.out.println("�������Ϣ:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);	//У��ʧ��
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();	
		}		
	} 
	
	@RequestMapping("/emps")
	@ResponseBody       //@ResponseBody�����Զ������صĶ���תΪjson�ַ���   ��Ҫ����jackson��
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn) {
		PageHelper.startPage(pn, 5);//˳����Ҫ
		List<Employee> emps= employeeService.getAll();
		//ʹ��pageInfo��װ��ѯ��Ľ����ֻ�轫pageInfo����ҳ������ˡ�
		//�����װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ����������
		PageInfo page=new PageInfo(emps,5);  //������ʾ��ҳ��
		return Msg.success().add("pageInfo", page);
		
	}
	
	//��ѯԱ������	 
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,
			Model model) {
		
		PageHelper.startPage(pn, 5);//˳����Ҫ
		List<Employee> emps= employeeService.getAll();
		//ʹ��pageInfo��װ��ѯ��Ľ����ֻ�轫pageInfo����ҳ������ˡ�
		//�����װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ����������
		PageInfo page=new PageInfo(emps,5);  //������ʾ��ҳ��
		model.addAttribute("pageInfo",page);
		//page.getNavigatepageNums();
		return "list";
	}
}
