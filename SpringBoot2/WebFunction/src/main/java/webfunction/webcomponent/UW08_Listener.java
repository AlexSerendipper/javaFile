package webfunction.webcomponent;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 @author Alex
 @create 2023-03-23-13:11
 */
@Slf4j
@WebListener
public class UW08_Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("UW08_Listener监听到 项目初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("UW08_Listener监听到 项目销毁");
    }
}
