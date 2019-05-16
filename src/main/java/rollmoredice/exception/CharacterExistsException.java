package rollmoredice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CharacterExistsException extends RuntimeException {
    
	private static final long serialVersionUID = 3304374987334056537L;
	private String username;
    private String charName;

    public CharacterExistsException(String username, String charName) {
    	super(String.format("User: '%s' already has a character named: '%s'", username, charName));
        this.username = username;
        this.charName = charName;
    }
    public String getUsername() {
        return username;
    }

    public String getCharName() {
        return charName;
    }
}
