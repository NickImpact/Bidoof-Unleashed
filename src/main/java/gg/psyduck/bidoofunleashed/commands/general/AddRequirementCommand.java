package gg.psyduck.bidoofunleashed.commands.general;

import com.google.common.collect.Maps;
import com.nickimpact.impactor.api.commands.SpongeCommand;
import com.nickimpact.impactor.api.commands.annotations.Aliases;
import com.nickimpact.impactor.api.plugins.SpongePlugin;
import gg.psyduck.bidoofunleashed.api.gyms.Requirement;
import gg.psyduck.bidoofunleashed.config.MsgConfigKeys;
import gg.psyduck.bidoofunleashed.gyms.Gym;
import gg.psyduck.bidoofunleashed.impl.requirements.EvolutionRequirement;
import gg.psyduck.bidoofunleashed.impl.requirements.GymRequirement;
import gg.psyduck.bidoofunleashed.impl.requirements.LevelRequirement;
import gg.psyduck.bidoofunleashed.utils.MessageUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.Map;

@Aliases("addrequirement")
public class AddRequirementCommand extends SpongeCommand {

    public AddRequirementCommand(SpongePlugin plugin) {
        super(plugin);
    }

    @Override
    public CommandElement[] getArgs() {
        return new CommandElement[0];
    }

    @Override
    public Text getDescription() {
        return Text.of("Adds a requirement to the specified gym which challengers will need to pass in order to battle");
    }

    @Override
    public Text getUsage() {
        return Text.of("/bu3 addrequirement <requirement>");
    }

    @Override
    public SpongeCommand[] getSubCommands() {
        return new SpongeCommand[0];
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Gym gym = args.<Gym>getOne("gym").get();
        String requirementArg = args.<String>getOne("requirement").get();
        Requirement requirement;
        switch (requirementArg) {
            case "gym":
                requirement = new GymRequirement();
                break;
            case "lvl":
                requirement = new LevelRequirement();
                break;
            case "evo":
                requirement = new EvolutionRequirement();
                break;
            default:
                throw new CommandException(MessageUtils.fetchAndParseMsg(src, MsgConfigKeys.INVALID_REQUIREMENT, null, null));
        }
        gym.getRequirements().add(requirement);
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("bu3_gym", gym.getName());
        variables.put("bu3_requirement", requirementArg);

        src.sendMessage(MessageUtils.fetchAndParseMsg(src, MsgConfigKeys.COMMANDS_ADD_REQUIREMENT_SUCCESS, null, variables));
        return CommandResult.success();
    }
}
