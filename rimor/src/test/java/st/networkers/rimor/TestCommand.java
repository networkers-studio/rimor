package st.networkers.rimor;

import st.networkers.rimor.command.AbstractRimorCommand;
import st.networkers.rimor.command.CommandMapping;
import st.networkers.rimor.instruction.InstructionMapping;
import st.networkers.rimor.instruction.MainInstructionMapping;

import java.util.List;

@CommandMapping({"test", "testCommand"})
public class TestCommand extends AbstractRimorCommand {

    public TestCommand() {
        registerSubcommand(new Bar());
    }

    @MainInstructionMapping
    public boolean main() {
        return true;
    }

    @InstructionMapping("fooAlias")
    public void foo(@FooAnnotation List<String> params, String param0, String param1) {
    }

    @InstructionMapping(value = "bazAlias", ignoreMethodName = true)
    public void baz() {
    }

    public static class Bar extends AbstractRimorCommand {

        @InstructionMapping
        public boolean set(boolean enabled) {
            return enabled;
        }
    }
}
