package rollmoredice.payload;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Required Format of JSON object to create a new User")
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    @ApiModelProperty("Required")
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    @ApiModelProperty("Required")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    @ApiModelProperty("Required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}