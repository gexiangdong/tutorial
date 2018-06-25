package cn.devmgr.tutorial;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于管理登录和退出的controller
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    private final static Log log = LogFactory.getLog(TokenController.class);

    @Autowired TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> userInfo){
        String userName = userInfo.get("username");
        String password = userInfo.get("password");
        
        HashMap<String, Object> result = new HashMap<>();
        String token = tokenService.login(userName, password);
        if(token == null) {
            result.put("message", "invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }else {
            result.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
    
    @DeleteMapping
    public Map<String, Object> logout(HttpServletRequest request){
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            if(log.isTraceEnabled()) {
                log.trace("will delete token : " + token);
            }
            tokenService.logout(token);
        }
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }
}
