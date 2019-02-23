package cn.devmgr.tutorial.webflow;

import cn.devmgr.tutorial.webflow.pojo.Order;
import cn.devmgr.tutorial.webflow.pojo.Product;
import cn.devmgr.tutorial.webflow.pojo.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 在buy-flow.xml有配置用到此component
 */
@Component
public class BuyHandler {
    private final static Logger logger = LoggerFactory.getLogger(BuyHandler.class);


    public ShoppingCart init(){
        logger.trace("init()");

        ShoppingCart cart = new ShoppingCart();

        //初始化购物车类；一般需要从数据库或cookie读取内容到这个类
        ShoppingCart.CartItem cartItemApple = cart.new CartItem();

        Product prodApple = new Product();
        prodApple.setId("A001");
        prodApple.setName("红富士苹果");
        cartItemApple.setProduct(prodApple);
        cartItemApple.setNum(5);
        cart.getCartItems().add(cartItemApple);

        ShoppingCart.CartItem cartItemBanana = cart.new CartItem();
        Product prodBanana = new Product();
        prodBanana.setId("B0002");
        prodBanana.setName("海南大香蕉");
        cartItemBanana.setProduct(prodBanana);
        cartItemBanana.setNum(10);
        cart.getCartItems().add(cartItemBanana);

        return cart;
    }


    /**
     * on-exit 执行的方法不需要返回值
     * @param shoppingCart
     */
    public void updateShoppingCart(ShoppingCart shoppingCart){
        logger.trace("updateShoppingCart {}", shoppingCart);
        if(logger.isTraceEnabled()){
            for(ShoppingCart.CartItem item : shoppingCart.getCartItems()){
                logger.trace("   item: {} {} , {} ", item.getProduct().getId(), item.getProduct().getName(), item.getNum());
            }
        }
    }

    /**
     * action-state 中的 evaluate 执行的方法需要返回值；并且 action-state 中根据这个返回值来决定下一步去那里
     * @param shoppingCart
     * @param error
     * @return
     */
    public String createOrder(ShoppingCart shoppingCart, Order order, MessageContext error) {
        logger.trace("createOrder shoppingCart:{}, order: {}, eror: {}", shoppingCart, order, error);

        String returnValue = "success";

        if (shoppingCart.getCartItems() == null || shoppingCart.getCartItems().size() == 0) {
            error.addMessage(new MessageBuilder(). //
                    error() //
                    .defaultText("购物车是空的") //
                    .build());

            returnValue = "fail";
        }

        // 还应检查购物车内物品是否已经下架等

        //创建订单
        order.setId("O99999");
        order.setPayDate(new Date());

        return returnValue;
    }

    public String payOrder(Order order){
        logger.trace("payOrder {}", order == null ? "null" : order.getId());

        return "success";
    }
}
