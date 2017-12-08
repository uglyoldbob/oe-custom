addtask do_simulate after do_build
do_simulate[nostamp] = "1"
python do_simulate() {
    if d.getVar('SIMULATOR_NAME', True) is None:
      bb.fatal("SIMULATOR_NAME does not exist, cannot continue with simulation")
    if d.getVar('SIMULATOR_ARGS', True) is None:
      bb.fatal("SIMULATOR_ARGS does not exist, cannot continue with simulation")
    simname = d.getVar('SIMULATOR_NAME', True)
    simargs = "%s" % d.getVar('SIMULATOR_ARGS', True)
    totalsimcmd = "\"%s %s\"" % (simname, simargs)
    bb.note(totalsimcmd)
    bb.note(os.getcwd())
    oe_terminal("${SHELL} -c %s" % totalsimcmd, "Running simulator", d)
}

