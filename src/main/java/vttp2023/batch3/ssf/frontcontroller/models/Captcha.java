package vttp2023.batch3.ssf.frontcontroller.models;

import java.util.Arrays;
import java.util.Random;

public class Captcha {
    
    private final int num1;
    private final int num2;
    private final String operation;
    private Integer answer;

    public Captcha() {
        Random r = new Random();
        this.num1 = r.nextInt(1, 51);
        this.num2 = r.nextInt(1, 51);
        this.operation = Arrays.asList("+", "-", "*", "/").get(r.nextInt(4));
        this.answer = 0;
    }

    public int getNum1() {
        return num1;
    }
    public int getNum2() {
        return num2;
    }
    public String getOperation() {
        return operation;
    }
    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
    
    public boolean checkCaptcha(int answer) {
        // if (this.answer == null) {
        //     return false;
        // }

        switch(this.operation) {
            case "+":
                return answer == this.num1 + this.num2;
            case "-":
                return answer == this.num1 - this.num2;
            case "*":
                return answer == this.num1 * this.num2;
            case "/":
                return answer == this.num1 / this.num2;
            default:
                return false;
        }
    }

}
