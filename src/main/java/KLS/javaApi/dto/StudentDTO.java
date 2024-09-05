package KLS.javaApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentDTO {
    private Integer id;
    private String name;
    private String className;
    private List<CourseDTO> courses;
}
