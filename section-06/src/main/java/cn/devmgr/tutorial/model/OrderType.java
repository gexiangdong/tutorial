package cn.devmgr.tutorial.model;

/**
 * 枚举类型；订单类别
 *
 */
public enum OrderType {
    RETAIL("零售", 1), WHOLESALE("批发", 2);
    
    private String name;  
    private int value;

    private OrderType(String name, int value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
  
    public int getValue() {
        return value;
    }

    
    @Override
    public String toString() {
        return this.name;
    }
    
    
}
