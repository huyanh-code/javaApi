package KLS.javaApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseDTO {
    private Integer studentCourseId;
    private String name;
    private int duration;
    private Date enrollmentDate;
}
