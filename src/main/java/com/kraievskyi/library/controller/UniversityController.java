package com.kraievskyi.library.controller;

import com.kraievskyi.library.model.Department;
import com.kraievskyi.library.model.Lector;
import com.kraievskyi.library.model.request.DepartmentCreationRequest;
import com.kraievskyi.library.model.request.LectorCreationRequest;
import com.kraievskyi.library.service.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @PostMapping("/lector")
    public ResponseEntity<Lector> createLector(@RequestBody LectorCreationRequest request) {
        return ResponseEntity.ok(universityService.createLector(request));
    }

    @GetMapping("/lector/{id}")
    public ResponseEntity<Lector> getLector(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getLectorById(id));
    }

    @GetMapping("/department/headOfDepartment")
    public String getHeadOfDepartment(@RequestParam(required = false) String departmentName) {
        return universityService.findHeadOfDepartment(departmentName);
    }

    @GetMapping("/department/showCountOfEmployee")
    public long showCountOfEmployee(@RequestParam(required = false) String departmentName) {
        return universityService.showCountOfEmployee(departmentName);
    }

    @GetMapping("/globalSearch")
    public List<String> globalSearch(@RequestParam(required = false) String template) {
        return universityService.globalSearch(template);
    }

    @GetMapping("/showStatistics")
    public String showDepartmentStatistics(@RequestParam(required = false) String departmentName) {
        return universityService.showDepartmentStatistic(departmentName);
    }

    @GetMapping("/department/avgSalary")
    public String showAverageSalary(@RequestParam(required = false) String departmentName) {
        return universityService.showTheAverageSalary(departmentName);
    }

    @PatchMapping("/lector/{id}")
    public ResponseEntity<Lector> updateLector(@PathVariable("id") Long id,
                                               @RequestBody LectorCreationRequest request) {
        return ResponseEntity.ok(universityService.updateLector(id, request));
    }

    @DeleteMapping("/lector")
    public ResponseEntity<Void> deleteAllLectors() {
        universityService.deleteAllLectors();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lector/{id}")
    public ResponseEntity<Void> deleteLector(@PathVariable Long id) {
        universityService.deleteLector(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addLector/{departmentId}/{lectorId}")
    public ResponseEntity<Department> addLector(
            @PathVariable Long departmentId, @PathVariable Long lectorId) {
        return ResponseEntity.ok(universityService.addLectorToDepartment(
                universityService.getDepartmentById(departmentId),
                universityService.getLectorById(lectorId)));
    }

    @PostMapping("/department")
    public ResponseEntity<Department> createDepartment(
            @RequestBody DepartmentCreationRequest request) {
        return ResponseEntity.ok(universityService.createDepartment(request));
    }

    @DeleteMapping("/department")
    public ResponseEntity<Void> deleteAllDepartments() {
        universityService.deleteAllDepartments();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getDepartmentById(id));
    }

    @PatchMapping("/department/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long id,
                                               @RequestBody DepartmentCreationRequest request) {
        return ResponseEntity.ok(universityService.updateDepartment(id, request));
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        universityService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }
}
