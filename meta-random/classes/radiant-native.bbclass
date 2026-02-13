lattice_radiant_prepare_run() {
	export LD_LIBRARY_PATH=${WORKDIR}/recipe-sysroot-native${base_libdir}:${WORKDIR}/recipe-sysroot-native${prefix}/lattice-radiant/radiant/bin/lin64
	export PATH=$PATH:${WORKDIR}/recipe-sysroot-native${prefix}/lattice-radiant/radiant/bin/lin64
}

