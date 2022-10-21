package st.networkers.rimor;

import lombok.Getter;
import st.networkers.rimor.command.Command;
import st.networkers.rimor.command.CommandRegistry;
import st.networkers.rimor.context.ExecutionContext;
import st.networkers.rimor.inject.Injector;
import st.networkers.rimor.internal.CommandExecutorImpl;
import st.networkers.rimor.internal.inject.InjectorImpl;
import st.networkers.rimor.internal.instruction.CommandInstruction;
import st.networkers.rimor.internal.provide.ProviderRegistryImpl;
import st.networkers.rimor.internal.resolve.CommandResolver;
import st.networkers.rimor.plugin.PluginManager;
import st.networkers.rimor.plugin.PluginManagerImpl;
import st.networkers.rimor.plugin.RimorPlugin;
import st.networkers.rimor.plugin.event.RimorInitializationEvent;
import st.networkers.rimor.provide.ProviderRegistry;
import st.networkers.rimor.provide.RimorProvider;

@Getter
public class Rimor {

    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final PluginManager pluginManager = new PluginManagerImpl(this);
    private final ProviderRegistry providerRegistry = new ProviderRegistryImpl();

    private final Injector injector = new InjectorImpl(providerRegistry);
    private final CommandExecutor executor = new CommandExecutorImpl(injector);
    private final boolean initialized = false;

    /**
     * Registers the given {@link Command}.
     *
     * @param command the command to register
     */
    public Rimor registerCommand(Command command) {
        commandRegistry.registerCommand(CommandResolver.resolve(command));
        return this;
    }

    /**
     * Registers the given {@link Command}s.
     *
     * @param commands the commands to register
     */
    public Rimor registerCommands(Command... commands) {
        for (Command command : commands)
            this.registerCommand(command);
        return this;
    }

    /**
     * Registers the given {@link RimorProvider}.
     *
     * @param provider the provider to register into this injector
     */
    public Rimor registerProvider(RimorProvider<?> provider) {
        this.providerRegistry.register(provider);
        return this;
    }

    /**
     * Registers the given {@link RimorProvider}s.
     *
     * @param providers the providers to register into this injector
     */
    public Rimor registerProviders(RimorProvider<?>... providers) {
        for (RimorProvider<?> provider : providers)
            this.registerProvider(provider);
        return this;
    }

    /**
     * Registers the given {@link RimorPlugin}.
     *
     * @param plugin the plugin to register
     */
    public Rimor registerPlugin(RimorPlugin plugin) {
        this.pluginManager.registerPlugin(plugin);
        return this;
    }

    /**
     * Registers the given {@link RimorPlugin}.
     *
     * @param plugins the plugins to register
     */
    public Rimor registerPlugins(RimorPlugin... plugins) {
        for (RimorPlugin plugin : plugins)
            this.registerPlugin(plugin);
        return this;
    }

    /**
     * Initializes all the registered plugins.
     */
    public Rimor initialize() {
        this.pluginManager.callEvent(new RimorInitializationEvent(this));
        return this;
    }

    public Object execute(CommandInstruction instruction, ExecutionContext context) {
        return this.executor.execute(instruction, context);
    }
}
