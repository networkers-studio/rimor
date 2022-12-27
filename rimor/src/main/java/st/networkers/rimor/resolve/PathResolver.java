package st.networkers.rimor.resolve;

import lombok.Data;
import st.networkers.rimor.context.ExecutionContext;
import st.networkers.rimor.instruction.Instruction;
import st.networkers.rimor.internal.command.MappedCommand;

import java.util.List;

/**
 * Resolver for simple string paths, like:
 * <ul>
 *     <li>["echo", "Hello", "world!"] -> command "echo", main instruction, leftoverPath ["Hello", "world!"]</li>
 *     <li>["git", "checkout", "dev"] -> command "git", instruction "checkout", leftoverPath ["dev"]</li>
 *     <li>["setActive", "true"] -> command "setActive", main instruction, leftoverPath ["true"]</li>
 * </ul>
 */
public interface PathResolver {

    @Data
    class Results {
        private final MappedCommand mainCommand;
        private final Instruction instruction;
        private final List<String> leftoverPath;
    }

    Results resolvePath(MappedCommand command, List<String> path, ExecutionContext context);

}
