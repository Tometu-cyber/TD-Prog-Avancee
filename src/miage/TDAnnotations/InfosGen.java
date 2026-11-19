package miage.TDAnnotations;

import java.lang.annotation.*;

@Documented                         // Pour que l'anontation soit dans la documentation
@Retention(RetentionPolicy.RUNTIME) // Pour que l'anontation éxiste à l'execution
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface InfosGen {
    enum Groupe { UN, DEUX, TROIS }

    String nom();
    String prenom() default "";
    int seance() default 1;
    Groupe groupe();
}
