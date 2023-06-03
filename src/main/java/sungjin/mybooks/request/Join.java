package sungjin.mybooks.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Join {

    private String email;
    private String name;
    private String rawPassword;

}
