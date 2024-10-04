quartus_prepare_run() {
	export LD_LIBRARY_PATH=${WORKDIR}/recipe-sysroot-native${base_libdir}
	#export LM_LICENSE_FILE=${WORKDIR}/recipe-sysroot-native${prefix}/quartus/license
	export QSYS_ROOTDIR="${WORKDIR}/quartus/quartus/sopc_builder/bin"
	export PATH=$PATH:${WORKDIR}/recipe-sysroot-native${prefix}/quartus/quartus/bin
}

