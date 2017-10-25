package raton.meme.hcf.timer;

import com.doctordark.utils.other.command.ArgumentExecutor;

import raton.meme.hcf.HCF;
import raton.meme.hcf.timer.args.TimerCheckArgument;
import raton.meme.hcf.timer.args.TimerSetArgument;

/**
 * Handles the execution and tab completion of the timer command.
 */
public class TimerExecutor extends ArgumentExecutor {

    public TimerExecutor(HCF plugin) {
        super("timer");

        addArgument(new TimerCheckArgument(plugin));
        addArgument(new TimerSetArgument(plugin));
    }
}