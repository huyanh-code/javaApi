package KLS.javaApi.repo;

import KLS.javaApi.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    @Query("select a from StudentCourse a where a.studentId = :stuid")
    List<StudentCourse> findBySinhVienId(@Param("stuid") Integer stuid);
}
