package springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import springsecurity.entity.User;
import springsecurity.service.UserService;
import springsecurity.util.CommunityUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 @author Alex
 @create 2023-04-21-20:40
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 首先一定要注入userservice接口，因为spring security底层依赖UserDetailsService
    @Autowired
    private UserService userService;


    // 不拦截静态资源~主要就是访问resources下的静态资源时（图片等），就不需要输入用户名和密码了
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }


    /**
     * 主要处理 负责权限管理（认证+授权）中的认证工作
     * AuthenticationManager是认证的核心接口
     * AuthenticationManager接口的默认实现类是 ProviderManager✔
     * @param auth：AuthenticationManagerBuilder用于构建该接口对象的工具
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 调用底层默认的认证规则即可，底层也是通过查看用户名是否在数据库中来进行认证
        // 一：开发新系统时，可以使用底层提供的密码加密工具。Pbkdf2PasswordEncoder，secret是salt，会将用户传入的密码加上salt后进行加密
        // auth.userDetailsService(userService).passwordEncoder(new Pbkdf2PasswordEncoder("123456"));

        // 二：旧系统需要使用springSecurity管理，使用自定义的认证规则
        // 委托模式：因为 ProviderManager 其本身不能实现认证功能，可将其视为一个集合， ProviderManager其中的 AuthenticationProvider组件负责认证功能
        // 一个AuthenticationProvider就实现了一种认证方式（因为在实际系统中，用户登录验证的方式有多种！这样做可以实现使用多种方式登录验证的功能）
        auth.authenticationProvider(new AuthenticationProvider() {
            // (1) Authentication是用于封装认证信息的接口（账号、密码）。不同的认证方式，其中封装了不同的认证信息
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();
                User user = userService.findUserByName(username);
                if(user==null){
                    // 抛springsecurity提供的异常
                    throw new UsernameNotFoundException("账号不存在");
                }
                password = CommunityUtil.md5(password + user.getSalt());
                if(!user.getPassword().equals(password)){
                    throw new BadCredentialsException("密码错误");
                }
                // 认证成功后，结果会通过SecurityContextHolder存入SecurityContext对象中,故需要返回一些信息
                // 需要返回Authentication的实现类，并返回三个信息（1、主要信息principal，通常回传User对象即可 2、证书credentials，账号密码模式下通常回传密码 3、权限authorities）
                return new UsernamePasswordAuthenticationToken(user, user.getPassword(),user.getAuthorities());
            }

            // (2) supports方法 要返回当前认证方式支持的方式（需要指明是账号密码认证 而不是微信扫码认证）
            @Override
            public boolean supports(Class<?> aClass) {
                // UsernamePasswordAuthenticationToken是 Authentication的一个常用的实现类。
                return UsernamePasswordAuthenticationToken.class.equals(aClass);
            }

        });
    }

    /**
     * 主要处理业务的授权功能：底层代码就是默认实现了 所有请求都需要授权（是否登录成功 / 是否有管理员权限）才能访问
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录相关配置（需要告知security我们登陆表单/登录请求的信息，当需要登陆时被拦截）
        http.formLogin()
                .loginPage("/loginpage")  // 返回登录页面的controller请求 (因为要使用我们自己的登录页面！)
                .loginProcessingUrl("/login")  // 用户提交登录信息的controller请求
//                .successForwardUrl()   // 登录结果处理方案1：登录成功时跳转的页面
//                .failureForwardUrl()  // 登录结果处理方案1：登录失败时跳转的页面
                .successHandler(new AuthenticationSuccessHandler() {  // 登录结果处理方案2：登录成功时
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
                        res.sendRedirect(req.getContextPath() + "/index");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {  // 登录结果处理方案2：登录失败时
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
                        req.setAttribute("error",e.getMessage());  // 登陆失败要回传数据，这里的异常就是认证失败抛出的异常
                        req.getRequestDispatcher("/loginpage").forward(req,res);
                    }
                });

        // 退出相关配置
        http.logout()
                .logoutUrl("/logout")  // 用户退出的controller请求
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
                        try {
                            res.sendRedirect(req.getContextPath() + "/index");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        // 授权相关配置
        http.authorizeRequests()
                .antMatchers("/letter").hasAnyAuthority("USER","ADMIN")  // 表示拥有user或admin权限，都可以访问私信页面
                .antMatchers("/admin").hasAuthority("ADMIN")  // 表示只有admin权限才能访问管理员页面，其他页面的访问不作无需权限，均可访问
                .anyRequest().permitAll()  // 其他所有请求 都被允许访问，实际上这句话不写也是默认开启的
                .and()
                .csrf().disable()  // 关闭CSRF功能
                .exceptionHandling().accessDeniedPage("/denied");  // 指定当权限不匹配时，访问的页面

        // 添加filter 处理验证码功能 (指定在判断用户名密码的filter执行之前处理，即UsernamePasswordAuthenticationFilter之前)
        http.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) servletRequest;
                HttpServletResponse res = (HttpServletResponse) servletResponse;
                // 只有登录业务才需要执行处理验证码的功能
                if(req.getServletPath().equals("/login")){
                    String verifyCode = req.getParameter("verifyCode");
                    if(verifyCode==null || !verifyCode.equals("1234")){
                        req.setAttribute("error","验证码错误");
                        req.getRequestDispatcher("/loginpage").forward(req,res);
                        return;
                    }
                }
                // 不是登录请求，或者验证码正确，放行！
                filterChain.doFilter(req,res);
            }
        }, UsernamePasswordAuthenticationFilter.class);

        // 记住我功能
        http.rememberMe()
                .tokenRepository(new InMemoryTokenRepositoryImpl())  // 将用户存储在内存中
                .tokenValiditySeconds(3600*24)  // 过期时间（s）
                .userDetailsService(userService);  // 实际上底层只是记住了用户name。需要传入userDetailsService，下次可以根据name查询到用户完整信息，从而自动通过认证
    };



}
