importPackage(Packages.objects.users);

function enter(pi) {
    pi.warp(141030000, 0);
    pi.getPlayer().cancelEffectFromBuffStat(SecondaryStatFlag.RIDING_VEHICLE);
    pi.getPlayer().dropMessage(-1, "[Voyage Success] Go to Ddabo on the right and help with Ddabo's request.");
    pi.getPlayer().dropMessage(5, "[Voyage Success] Go to Ddabo on the right and help with Ddabo's request.");
    return true;
}

