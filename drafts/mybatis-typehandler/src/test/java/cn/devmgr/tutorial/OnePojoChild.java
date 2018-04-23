package cn.devmgr.tutorial;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NotNull
public class OnePojoChild extends OnePojo {
    
   
   
    @NotNull
    private List< @Size(min=2)  @NotNull String> images;
    
    @AssertTrue
    public boolean isValid() {
        //这里验证类的各种成员变量组合
        return true;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
