import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ConsoleRunner runner = context.getBean(ConsoleRunner.class);
        runner.run();
        context.close();
    }
}
