package hello.hello_spring.config;

import javax.sql.DataSource;

//@Configuration
public class SpringConfig {

    private DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
    @Bean
    public MemoryMemberRepository MemoryMemberRepository() {
        return new MemoryMemberRepository();
    }
     */

    /*
    @Bean
    public JdbcMemberRepository JdbcMemberRepository() {
        return new JdbcMemberRepository(dataSource);
    }
     */
}
