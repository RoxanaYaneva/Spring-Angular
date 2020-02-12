//package mvc.spring.restmvc.config;
//
//import mvc.spring.restmvc.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().cacheControl().disable();
//        http
//                .authorizeRequests()
//                .antMatchers(
//                        "/api/register**",
//                        "/js/**",
//                        "/css/**",
//                        "/img/**",
//                        "/api/products",
//                        "/api/*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/api/login")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
//                .logoutSuccessUrl("/api/login?logout")
//                .permitAll();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//}
