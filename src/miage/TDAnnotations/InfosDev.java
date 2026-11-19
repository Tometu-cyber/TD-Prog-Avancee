package miage.TDAnnotations;

import java.lang.annotation.*;

@Documented                         // Pour que l'anontation soit dans la documentation
@Retention(RetentionPolicy.RUNTIME) // Pour que l'anontation éxiste à l'execution
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface InfosDev {
    enum Completude { PARTIELLE, COMPLETE, FINALISEE }

    Completude completude() default Completude.PARTIELLE;
    boolean test() default false;
    boolean auto() default false;
}