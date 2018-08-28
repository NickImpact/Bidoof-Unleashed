package gg.psyduck.bidoofunleashed.commands.general;

import com.google.common.collect.Maps;
import com.nickimpact.impactor.api.commands.SpongeCommand;
import com.nickimpact.impactor.api.commands.annotations.Aliases;
import com.nickimpact.impactor.api.plugins.SpongePlugin;
import gg.psyduck.bidoofunleashed.commands.arguments.GymArg;
import gg.psyduck.bidoofunleashed.config.MsgConfigKeys;
import gg.psyduck.bidoofunleashed.gyms.Gym;
import gg.psyduck.bidoofunleashed.utils.MessageUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Map;

@Aliases("opengym")
public class OpenGymCommand extends SpongeCommand {

    public OpenGymCommand(SpongePlugin plugin) {
        super(plugin);
    }

    @Override
    public CommandElement[] getArgs() {
        return new CommandElement[] {
                new GymArg(Text.of("Gym"))
        };
    }

    @Override
    public Text getDescription() {
        return Text.of("Opens the specified gym");
    }

    @Override
    public Text getUsage() {
        return Text.of("/opengym <gym>");
    }

    @Override
    public SpongeCommand[] getSubCommands() {
        return new SpongeCommand[0];
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Gym gym = args.<Gym>getOne("gym").get();

        if (!(src instanceof Player)) {
            throw new CommandException(MessageUtils.fetchAndParseMsg(src, MsgConfigKeys.SOURCE_NOT_PLAYER, null, null));
        }
        Player player = (Player) src;

        if (!gym.getLeaders().containsKey(player.getUniqueId())) {
            throw new CommandException(MessageUtils.fetchAndParseMsg(src, MsgConfigKeys.PLAYER_NOT_LEADER, null, null));
        }

        gym.setOpen(true);
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("bu3_gym", gym.getName());
        src.sendMessage(MessageUtils.fetchAndParseMsg(src, MsgConfigKeys.COMMANDS_OPEN_GYM_SUCCESS, null, variables));
        return CommandResult.success();
    }
}