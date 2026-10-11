package daw2.t2.fabianchachi.citas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration,org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration"
})
class MicroservicioCitasApplicationTests {

    @Test
    void contextLoads() {
    }
}