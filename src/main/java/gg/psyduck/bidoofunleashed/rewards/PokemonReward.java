package gg.psyduck.bidoofunleashed.rewards;

import com.nickimpact.impactor.json.Typing;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import gg.psyduck.bidoofunleashed.api.rewards.BU3Reward;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

@Typing("pokemon")
public class PokemonReward extends BU3Reward<PokemonSpec> {

	private final String type = "pokemon";

	public PokemonReward(PokemonSpec reward) {
		super(reward);
	}

	@Override
	public void give(Player player) throws Exception {
		PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).orElseThrow(() -> new Exception("Unable to locate player storage for " + player.getName()));
		EntityPixelmon poke = this.reward.create((World) player.getWorld());
		storage.addToParty(poke);
		storage.sendUpdatedList();
	}
}
