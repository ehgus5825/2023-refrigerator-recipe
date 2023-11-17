package refrigerator.back.annotation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class TestDataInitExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getTestClass().orElse(null);
        if (testClass != null){
            AnnotationUtils.findAnnotation(testClass, TestDataInit.class)
                    .ifPresent(testDataInit -> {
                        DataSource dataSource = getDataSource(context);
                        try (Connection connection = dataSource.getConnection()){
                            for (String path : testDataInit.value()) {
                                ScriptUtils.executeSqlScript(connection, new ClassPathResource(path));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private DataSource getDataSource(ExtensionContext context){
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        return applicationContext.getBean(DataSource.class);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

        Class<?> testClass = context.getTestClass().orElse(null);
        if (testClass != null){
            AnnotationUtils.findAnnotation(testClass, TestDataInit.class)
                    .ifPresent(testDataInit -> {
                        DataSource dataSource = getDataSource(context);
                        try (Connection connection = dataSource.getConnection()){
                            ScriptUtils.executeSqlScript(connection, new ClassPathResource("/delete.sql"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
