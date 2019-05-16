package rollmoredice.payload;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "What is returned from the API when requesting if a "
		+ "username or email is in the database")
public class UserIdentityAvailability {
    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}