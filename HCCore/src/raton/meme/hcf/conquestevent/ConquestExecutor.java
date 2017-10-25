package raton.meme.hcf.conquestevent;

import com.doctordark.utils.other.command.ArgumentExecutor;

import raton.meme.hcf.HCF;

public class ConquestExecutor extends ArgumentExecutor {

    public ConquestExecutor(HCF plugin) {
        super("conquest");
        addArgument(new ConquestSetpointsArgument(plugin));
    }
}
