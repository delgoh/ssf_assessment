package vttp2023.batch3.ssf.frontcontroller.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.Size;

public class Login {
    
    @Size(min = 2, message = "Username must be at least 2 characters long")
    private String username;

    @Size(min = 2, message = "Password must be at least 2 characters long")
    private String password;

    private Captcha captcha;
    private boolean isCaptchaShown;
    private int captchaAnswer;

    public int getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(int captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }

    public Login() {
        this.captcha = new Captcha();
        this.isCaptchaShown = false;
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void generateNewCaptcha() {
        this.captcha = new Captcha();
    }

    public Captcha getCaptcha() {
        return this.captcha;
    }
    
    public boolean isCaptchaShown() {
        return isCaptchaShown;
    }

    public void setCaptchaShown(boolean isCaptchaShown) {
        this.isCaptchaShown = isCaptchaShown;
    }

    public boolean isCaptchaCorrect() {
        return this.getCaptcha().checkCaptcha(this.getCaptchaAnswer());
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("username", this.getUsername())
            .add("password", this.getPassword())
            .build();
    }
    
}
