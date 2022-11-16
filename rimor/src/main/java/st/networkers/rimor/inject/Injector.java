package st.networkers.rimor.inject;

import st.networkers.rimor.Rimor;
import st.networkers.rimor.context.ExecutionContext;
import st.networkers.rimor.internal.reflect.CachedMethod;
import st.networkers.rimor.provide.RimorProvider;

/**
 * Provides an object associated with a given {@link Token} from an {@link ExecutionContext}, or from a registered
 * {@link RimorProvider} if the context is not able to provide anything for the token.
 * <p>
 * To register a {@link RimorProvider}, see {@link Rimor#registerProvider(RimorProvider)}.
 */
public interface Injector {

    /**
     * Gets the object for the given {@link Token} from the given {@link ExecutionContext}, if able. Otherwise,
     * gets it from a registered provider, or {@code null}.
     *
     * @param token   the token to get its associated object
     * @param context the context of a command execution
     * @return the object associated with the token
     */
    <T> T get(Token<T> token, ExecutionContext context);

    /**
     * Invokes the given method injecting all its parameters.
     *
     * @param cachedMethod the method to invoke
     * @param instance     an instance of the method's class to invoke it on, or {@code null} if static
     * @param context      the context of a command execution
     * @return the result of executing the method
     */
    Object invokeMethod(CachedMethod cachedMethod, Object instance, ExecutionContext context);
}
