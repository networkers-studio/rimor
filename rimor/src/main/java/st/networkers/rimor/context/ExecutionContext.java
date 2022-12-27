package st.networkers.rimor.context;

import com.google.common.reflect.TypeToken;
import lombok.EqualsAndHashCode;
import st.networkers.rimor.inject.Token;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains injectable objects relative to the execution of a command (for example, its parameters, the sender...).
 * <p>
 * Every injectable object is wrapped in a {@link ContextComponent}.
 */
@EqualsAndHashCode
public class ExecutionContext {

    public static ExecutionContext build(ContextComponent<?>... components) {
        return new ExecutionContext(Arrays.stream(components).collect(Collectors.groupingBy(ContextComponent::getType)));
    }

    public static ExecutionContext build(Collection<ContextComponent<?>> components) {
        return new ExecutionContext(components.stream().collect(Collectors.groupingBy(ContextComponent::getType)));
    }

    private final Map<TypeToken<?>, List<ContextComponent<?>>> components;

    private ExecutionContext(Map<TypeToken<?>, List<ContextComponent<?>>> components) {
        this.components = components;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Token<T> token) {
        if (!this.components.containsKey(token.getType()))
            return Optional.empty();

        return this.components.get(token.getType()).stream()
                .filter(component -> component.canProvide(token))
                .map(component -> (ContextComponent<T>) component)
                .map(ContextComponent::getObject)
                .findAny();
    }
}
