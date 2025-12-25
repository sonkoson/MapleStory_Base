function enter(pi) {
    try {
        var em = pi.getEventManager("OrbisPQ");
        if (em != null && em.getProperty("stage6_" + (pi.getPortal().getName().substring(2, 5)) + "").equals("1")) {
            pi.warpS(pi.getMapId(), (pi.getPortal().getName().startsWith("rp08") ? 2 : (pi.getPortal().getId() + 4)));
            pi.playerMessage(-1, "ถูกต้อง!");
        } else {
            pi.warpS(pi.getMapId(), (pi.getPortal().getName().startsWith("rp01") ? 5 : (pi.getPortal().getName().startsWith("rp05") ? 1 : (pi.getPortal().getId() - 4))));
            pi.playerMessage(-1, "ไม่ถูกต้อง");
        }
    } catch (e) {
        pi.getPlayer().dropMessage(5, "Error: " + e);
    }
}