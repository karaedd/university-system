package com.kraievskyi.library.service;

import com.kraievskyi.library.exception.EntityNotFoundException;
import com.kraievskyi.library.model.Department;
import com.kraievskyi.library.model.Lector;
import com.kraievskyi.library.model.request.DepartmentCreationRequest;
import com.kraievskyi.library.model.request.LectorCreationRequest;
import com.kraievskyi.library.repository.DepartmentRepository;
import com.kraievskyi.library.repository.LectorRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final LectorRepository lectorRepository;
    private final DepartmentRepository departmentRepository;

    public Lector createLector(LectorCreationRequest lector) {
        Lector lectorToCreate = new Lector();
        BeanUtils.copyProperties(lector, lectorToCreate);
        lectorToCreate.setName(lector.getName());
        lectorToCreate.setDegree(lector.getDegree());
        lectorToCreate.setSalary(lector.getSalary());

        return lectorRepository.save(lectorToCreate);
    }

    public Lector getLectorById(Long id) {
        Optional<Lector> lector = lectorRepository.findById(id);
        if (lector.isPresent()) {
            return lector.get();
        }
        throw new EntityNotFoundException("Can't find any lector under given id");
    }

    public Lector updateLector(Long id, LectorCreationRequest request) {
        Optional<Lector> optionalLector = lectorRepository.findById(id);
        if (!optionalLector.isPresent()) {
            throw new EntityNotFoundException("Lector is not present in the database");
        }
        Lector lector = optionalLector.get();
        lector.setName(request.getName());
        lector.setDegree(request.getDegree());
        lector.setSalary(request.getSalary());
        return lectorRepository.save(lector);
    }

    public void deleteAllLectors() {

        lectorRepository.deleteAll();
    }

    public void deleteLector(Long id) {

        lectorRepository.deleteById(id);
    }

    public Department addLectorToDepartment(Department department, Lector lector) {
        Optional<Department> optionalDepartment = departmentRepository.findById(department.getId());
        Optional<Lector> optionalLector = lectorRepository.findById(lector.getId());

        Department departmentById = optionalDepartment.orElseThrow(() ->
                new RuntimeException("Department is not present in the database"));
        Lector lectorById = optionalLector.orElseThrow(() ->
                new RuntimeException("Lector is not present in the database"));

        if (departmentById.getLectorList().contains(lectorById)) {
            throw new RuntimeException("Lector with id "
                    + lectorById.getId() + " already exists");
        }
        department.getLectorList().add(lectorById);
        return departmentRepository.save(departmentById);
    }

    public String showTheAverageSalary(String departmentName) {

        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(departmentList::add);

        int sum = 0;
        int avg;
        List<Integer> collect = departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .flatMap(Collection::stream)
                .map(Lector::getSalary)
                .collect(Collectors.toList());
        for (Integer integer : collect) {
            sum += integer;
        }
        avg = sum / collect.size();
        return "The average salary of " + departmentName + " is " + avg;
    }

    public String findHeadOfDepartment(String departmentName) {

        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(departmentList::add);

        String collect = departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .flatMap(Collection::stream)
                .filter(lector -> lector.getDegree().equals("Professor"))
                .map(Lector::getName)
                .collect(Collectors.joining());
        return "Head of " + departmentName + " department is " + collect;
    }

    public long showCountOfEmployee(String departmentName) {

        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(departmentList::add);

        return departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .mapToLong(Collection::size)
                .sum();
    }

    public String showDepartmentStatistic(String departmentName) {

        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(departmentList::add);

        List<Lector> assistant = departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .flatMap(Collection::stream)
                .filter(l -> l.getDegree().equals("Assistant"))
                .collect(Collectors.toList());

        List<Lector> professor = departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .flatMap(Collection::stream)
                .filter(l -> l.getDegree().equals("Professor"))
                .collect(Collectors.toList());

        List<Lector> associateProfessor = departmentList.stream()
                .filter(department -> department.getName().contains(departmentName))
                .map(Department::getLectorList)
                .flatMap(Collection::stream)
                .filter(l -> l.getDegree().equals("Associate professor"))
                .collect(Collectors.toList());

        return "assistans - " + assistant.size() + "\n"
                + "associate professors - " + associateProfessor.size() + "\n"
                + "professors - " + professor.size() + "\n";
    }

    public List<String> globalSearch(String template) {

        List<Lector> lectorList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();
        lectorRepository.findAll().forEach(lectorList::add);
        departmentRepository.findAll().forEach(departmentList::add);

        List<String> collect = lectorList.stream()
                .map(Lector::getName)
                .filter(name -> name.toLowerCase().contains(template))
                .collect(Collectors.toList());

        List<String> collect2 = lectorList.stream()
                .map(Lector::getDegree)
                .filter(degree -> degree.toLowerCase(Locale.ROOT).contains(template))
                .collect(Collectors.toList());

        List<String> collect3 = lectorList.stream()
                .map(Lector::getSalary)
                .map(Object::toString)
                .filter(s -> s.contains(template))
                .collect(Collectors.toList());

        List<String> collect1 = departmentList.stream()
                .map(Department::getName)
                .filter(name -> name.toLowerCase(Locale.ROOT).contains(template))
                .collect(Collectors.toList());

        if (collect.size() > 0) {
            collect.addAll(collect1);
            collect.addAll(collect2);
            collect.addAll(collect3);
            return collect;
        } else if (collect1.size() > 0) {
            collect1.addAll(collect);
            collect1.addAll(collect2);
            collect1.addAll(collect3);
            return collect1;
        } else if (collect2.size() > 0) {
            collect2.addAll(collect);
            collect2.addAll(collect1);
            collect2.addAll(collect3);
            return collect2;
        } else if (collect3.size() > 0) {
            collect3.addAll(collect);
            collect3.addAll(collect1);
            collect3.addAll(collect2);
            return collect3;
        } else {
            return new ArrayList<>();
        }
    }

    public Department createDepartment(DepartmentCreationRequest department) {
        Department departmentToCreate = new Department();
        BeanUtils.copyProperties(department, departmentToCreate);
        departmentToCreate.setName(department.getName());
        departmentToCreate.setLectorList(new ArrayList<>());
        return departmentRepository.save(departmentToCreate);

    }

    public Department getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        }
        throw new EntityNotFoundException("Can't find any department under given id");
    }

    public Department updateDepartment(Long id, DepartmentCreationRequest request) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            throw new EntityNotFoundException("Department is not present in the database");
        }
        Department department = optionalDepartment.get();
        department.setName(request.getName());
        return departmentRepository.save(department);
    }

    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
