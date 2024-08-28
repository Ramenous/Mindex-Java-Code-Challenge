package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Creating reporting structure with employee id [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        return new ReportingStructure(employee, getNumberOfReports(employee));
    }

    private int getNumberOfReports(Employee employee) {
        int reports = 0;
        if (employee != null && employee.getDirectReports() != null) {
            reports += employee.getDirectReports().size();
            for (Employee e : employee.getDirectReports()) {
                reports += getNumberOfReports(employeeRepository.findByEmployeeId(e.getEmployeeId()));
            }
        }
        return reports;
    }
}
