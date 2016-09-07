package com.maaz.lms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maaz.lms.dao.EmployeeDao;
import com.maaz.lms.dao.LoginDao;
import com.maaz.lms.entity.Approvers;
import com.maaz.lms.entity.Department;
import com.maaz.lms.entity.Employee;
import com.maaz.lms.form.AdminForm;
import com.maaz.lms.vo.AdminDepartmentVo;
import com.maaz.lms.vo.AdminEmployeeVo;
import com.maaz.lms.vo.ApproverVo;
import com.maaz.lms.vo.DepartmentVo;
import com.maaz.lms.vo.EmployeeVo;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	public AdminEmployeeVo getAllEmployees(Integer companyAccountId) {
		AdminEmployeeVo adminEmployeeVo = new AdminEmployeeVo();
		List<EmployeeVo> lst = null;
		List<Employee> lstEmp = employeeDao.getAllEmployees(companyAccountId);
		if(lstEmp!=null) {
			lst = new ArrayList<EmployeeVo>();
			for(Employee emp : lstEmp) {
				EmployeeVo vo = new EmployeeVo();
				vo.setIdEmployee(emp.getIdEmployee());
				vo.setFirstName(emp.getFirstName());
				vo.setLastName(emp.getLastName());
				vo.setEmailId(emp.getEmailId());
				vo.setAdmin(emp.isAdmin());
				vo.setDepartmentId(emp.getDepartment().getIdDepartment());
				vo.setDepartmentName(emp.getDepartment().getDeptName());
				Set<Approvers> setApprovers = emp.getApprovers();
				Iterator<Approvers> itr = setApprovers.iterator();
				List<ApproverVo> lstApprovers = new ArrayList<ApproverVo>();
				while(itr.hasNext()) {
					Approvers approver = itr.next();
					ApproverVo apprVo = new ApproverVo();
					apprVo.setIdApprovers(approver.getIdApprovers());
					apprVo.setApproverName(approver.getApprover().getFirstName() + " " + approver.getApprover().getLastName());
					lstApprovers.add(apprVo);
				}
				vo.setApprovers(lstApprovers);
				lst.add(vo);
			}
		}
		adminEmployeeVo.setDraw(1);
		adminEmployeeVo.setRecordsTotal(lstEmp != null ? lstEmp.size() : 0);
		adminEmployeeVo.setData(lst);
		return adminEmployeeVo;
	}

	@Override
	public AdminDepartmentVo getAllDepartments() {
		List<DepartmentVo> lstDeptVo = null;
		List<Department> lstDept = employeeDao.getAllDepartments();
		if(lstDept!=null) {
			lstDeptVo = new ArrayList<DepartmentVo>();
			for(Department dept : lstDept) {
				DepartmentVo d = new DepartmentVo();
				d.setIdDepartment(dept.getIdDepartment());
				d.setDeptName(dept.getDeptName());
				lstDeptVo.add(d); 
			}
		}
		AdminDepartmentVo deptVo = new AdminDepartmentVo();
		deptVo.setData(lstDeptVo);
		return deptVo;
	}

	@Override
	public Map<Integer, String> getAllDepartmentsMap() {
		Map<Integer, String> mapDepts = new HashMap<Integer, String>();
		List<Department> lstDept = employeeDao.getAllDepartments();
		if(lstDept!=null) {
			for(Department dept : lstDept) {
				mapDepts.put(dept.getIdDepartment(), dept.getDeptName());
			}
		}
		return mapDepts;
	}

	@Override
	public void saveEmployee(AdminForm form) {
		Employee emp = null;
		if(form.getEmployeeId()!=null) {
			//update
			emp = employeeDao.getEmployee(form.getEmployeeId());
			emp.setIdEmployee(form.getEmployeeId());
			emp.setFirstName(form.getFirstName());
			emp.setLastName(form.getLastName());
			emp.setEmailId(form.getEmailId());
			emp.setAdmin(form.getAdmin().equals(0) ? false : true);
			emp.setDeleted(form.getDeleted().equals(0) ? false : true);
			
		} else {
			//new
			emp = new Employee();
			emp.setIdEmployee(form.getEmployeeId());
			emp.setFirstName(form.getFirstName());
			emp.setLastName(form.getLastName());
			emp.setEmailId(form.getEmailId());
			emp.setAdmin(form.getAdmin().equals(0) ? false : true);
			emp.setDeleted(form.getDeleted().equals(0) ? false : true);
			
			Set<Approvers> setApprovers = null;
			if(form.getApprovers()!=null && form.getApprovers().size()>0) {
				setApprovers = new HashSet<Approvers>();
				for(Integer approverId : form.getApprovers()) {
					Approvers approvers = new Approvers();
					Employee approver = employeeDao.getEmployee(approverId);
					approvers.setEmployee(emp);
					approvers.setApprover(approver);
					setApprovers.add(approvers);
				}
			}
			emp.setApprovers(setApprovers);
		}
		employeeDao.saveOrUpdateEmployee(emp);
	}

	@Override
	public Map<Integer, String> getEmployeesForDropDown(Integer companyAccountId) {
		Map<Integer, String> mapEmployees = new HashMap<Integer, String>();
		List<Employee> lstEmp = employeeDao.getAllEmployees(companyAccountId);
		if(lstEmp!=null) {
			for(Employee emp : lstEmp) {
				mapEmployees.put(emp.getIdEmployee(), emp.getFirstName() + " " + emp.getLastName());
			}
		}
		return mapEmployees;
	}

}
