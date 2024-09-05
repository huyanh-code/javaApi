package KLS.javaApi.service;

import KLS.javaApi.dto.CourseDTO;
import KLS.javaApi.dto.NewSinhVienKhoaHocRequest;
import KLS.javaApi.dto.NewSinhVienKhoaHocResponse;
import KLS.javaApi.dto.StudentDTO;
import KLS.javaApi.ex.MyException;
import KLS.javaApi.model.Course;
import KLS.javaApi.model.Student;
import KLS.javaApi.model.StudentCourse;
import KLS.javaApi.repo.CourseRepository;
import KLS.javaApi.repo.StudentCourseRepository;
import KLS.javaApi.repo.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    public NewSinhVienKhoaHocResponse addKhoaHoc(NewSinhVienKhoaHocRequest dto){
        Integer stuId = dto.getStuId();
        Integer khoaHocId = dto.getKhoaHocId();

        Optional<Course> courseOp = courseRepository.findById(khoaHocId);
        if(courseOp.isEmpty()){
            throw new MyException("Khoa hoc ID khong ton tai " + khoaHocId);
        }
        Optional<Student> studentOp = studentRepository.findById(stuId);
        if(studentOp.isEmpty()){
            throw new MyException("Sinh Vien ID khong ton tai " + stuId);
        }

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(stuId);
        studentCourse.setCourseId(khoaHocId);
        studentCourse.setEnrollmentDate(new Date());
        studentCourseRepository.save(studentCourse);

        NewSinhVienKhoaHocResponse res = new NewSinhVienKhoaHocResponse();
        res.setTenKhoaHoc(courseOp.get().getName());
        res.setTenSinhVien(studentOp.get().getName());

        return res;
    }

    public List<StudentDTO> findAll(){
        List<StudentDTO> students = new ArrayList<>();
        List<Student> list = studentRepository.findAll();
        for(Student stu: list){
            students.add(this.extractStudentDTO(stu));
        }
        return students;
    }

    public StudentDTO extractStudentDTO(Student stu){
        StudentDTO dto = new StudentDTO();
        BeanUtils.copyProperties(stu, dto);

        List<CourseDTO> courses = this.extractCourses(stu.getId());
        dto.setCourses(courses);
        return dto;
    }

    public StudentDTO findById(Integer stuId) {
        Student stu = studentRepository.findById(stuId).get();
        return this.extractStudentDTO(stu);
    }

    public void xoaStudent(Integer stuId) {
        Optional<Student> studentOp = studentRepository.findById(stuId);
        if(studentOp.isEmpty()){
            throw new MyException("Sinh vien ID khong ton tai " + stuId);
        }

        studentRepository.delete(studentOp.get());
    }

    public List<CourseDTO> extractCourses(Integer stuId){
        List<CourseDTO> courses = new ArrayList<>();
        List<StudentCourse> studentCourses = studentCourseRepository.findBySinhVienId(stuId);
        for(StudentCourse stuCourse: studentCourses){
            CourseDTO dto = new CourseDTO();
            dto.setStudentCourseId(stuCourse.getId());
            Course course = courseRepository.findById(stuCourse.getCourseId()).get();
            dto.setName(course.getName());
            dto.setDuration(course.getDuration());
            dto.setEnrollmentDate(stuCourse.getEnrollmentDate());

            courses.add(dto);
        }
        return courses;
    }
}
