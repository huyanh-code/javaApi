package KLS.javaApi.controller;

import KLS.javaApi.dto.NewSinhVienKhoaHocRequest;
import KLS.javaApi.dto.NewSinhVienKhoaHocResponse;
import KLS.javaApi.dto.StudentDTO;
import KLS.javaApi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO findDetails(@PathVariable("id") Integer stuId) {
        return studentService.findById(stuId);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> xoaStudent(@PathVariable("id") Integer stuId) {
        studentService.xoaStudent(stuId);
        return Map.of(
                "message", "Da xoa sinh vien " + stuId
        );
    }

    @PostMapping
    public Map<String, Object> themKhoaHoc(@RequestBody @Valid NewSinhVienKhoaHocRequest request) {
        NewSinhVienKhoaHocResponse res = studentService.addKhoaHoc(request);
        return Map.of(
                "message", "Da Assign khoa hoc cho sinh vien",
                "data", res
        );
    }
}
