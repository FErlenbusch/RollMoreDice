package rollmoredice.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Required Format of JSON object to loging a user")
public class LoginRequest {
    @NotBlank
    @ApiModelProperty("Required")
    private String usernameOrEmail;

    @NotBlank
    @ApiModelProperty("Required")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}