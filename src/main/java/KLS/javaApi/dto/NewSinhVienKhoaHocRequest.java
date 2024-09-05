package KLS.javaApi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewSinhVienKhoaHocRequest {
    @NotNull(message = "ID sinh vien is required")
    private Integer stuId;

    @NotNull(message = "ID khoa hoc is required")
    private Integer khoaHocId;
}
