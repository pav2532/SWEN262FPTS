/**
 * Created by Quang on 10/31/15.
 */
public class UserProxy implements GetUserData {
    User realUser;
    public UserProxy(User realUser){
        this.realUser = realUser;
    }

    @Override
    public String getPassword() {
        return realUser.getPassword();
    }

    @Override
    public String getUsername() {
        return realUser.getUsername();
    }

    @Override
    public int getID() {
        return realUser.getID();
    }
}
