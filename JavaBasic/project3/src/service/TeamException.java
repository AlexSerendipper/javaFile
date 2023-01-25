package service;
/**
 * 
 * @Description 自定义异常类
 * @author alex_serendipper Email:642671525@qq.com
 * @version
 * @date 2022年11月4日下午3:13:17
 *
 */
public class TeamException extends RuntimeException{
	static final long serialVersionUID = -7034897117895766939L;
	
    public TeamException() {
        super();
    }
    
    public TeamException(String message) {
        super(message);
    }
}
