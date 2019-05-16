package rollmoredice.payload;

import io.swagger.annotations.ApiModel;


@ApiModel(description = "Format of JSON object returned from the API, and to be "
		+ "sent to the API when a User object is involved")
public class UserSummary {
    private Long id;
    private String username;
    private String email;

    
    public UserSummary(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
