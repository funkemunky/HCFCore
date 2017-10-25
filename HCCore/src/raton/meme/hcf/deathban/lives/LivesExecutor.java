package raton.meme.hcf.deathban.lives;

import com.doctordark.utils.other.command.ArgumentExecutor;

import raton.meme.hcf.HCF;
import raton.meme.hcf.deathban.lives.args.*;

/**
 * Handles the execution and tab completion of the lives command.
 */
public class LivesExecutor extends ArgumentExecutor {

    public LivesExecutor(HCF plugin) {
        super("lives");

        addArgument(new LivesCheckArgument(plugin));
        addArgument(new LivesCheckDeathbanArgument(plugin));
        addArgument(new LivesClearDeathbansArgument(plugin));
        addArgument(new LivesGiveArgument(plugin));
        addArgument(new LivesReviveArgument(plugin));
        addArgument(new LivesSetArgument(plugin));
        addArgument(new LivesSetDeathbanTimeArgument());
        addArgument(new LivesTopArgument(plugin));
    }
}