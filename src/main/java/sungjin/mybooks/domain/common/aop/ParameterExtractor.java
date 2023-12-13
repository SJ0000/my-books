package sungjin.mybooks.domain.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

public class ParameterExtractor {

    public static Optional<Long> extractByAnnotation(JoinPoint joinPoint, Class<?> annotationType){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = signature.getMethod().getParameters();
        for (int i = 0; i < args.length; i++) {
            Parameter parameter = parameters[i];
            boolean hasAnnotation = Arrays.stream(parameter.getAnnotations())
                    .anyMatch(annotationType::isInstance);

            if (hasAnnotation) {
                return Optional.of((Long) args[i]);
            }
        }
        return Optional.empty();
    }

    public static Optional<Long> extractByName(JoinPoint joinPoint, String name) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = signature.getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            Parameter parameter = parameters[i];
            if (name.equalsIgnoreCase(parameter.getName())) {
                return Optional.of((Long) args[i]);
            }
        }
        return Optional.empty();
    }
}
