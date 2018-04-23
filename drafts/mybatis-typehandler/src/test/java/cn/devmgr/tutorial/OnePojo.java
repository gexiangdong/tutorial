package cn.devmgr.tutorial;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class OnePojo {
    @Digits(fraction = 2, integer = 3)
    private float age;
    
//    @Size.List({
//        @Size(min=4,max=6)}
//    )
    @Digits(fraction = 2, integer = 3)
    private String score;
    
//    private Boolean right;
    
    @AssertTrue
    public boolean didRight() {
        return false;
    }
    public void setRight(Boolean b) {
        
    }
    
    public float getAge() {
        return age;
    }
    public void setAge(float age) {
        this.age = age;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    
    
}
