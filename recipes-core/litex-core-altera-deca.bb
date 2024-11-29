SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

S = "${WORKDIR}/git"

inherit deploy
inherit litexnative
inherit python3native

# inhibit default deps, to exclude libc
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "virtual/${HOST_PREFIX}gcc"

DEPENDS += "meson-native ninja-native"
DEPENDS += "yosys-native"
DEPENDS += "migen-native"
DEPENDS += "litex-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"
DEPENDS += "litex-pythondata-software-picolibc-native"
DEPENDS += "litex-brianhg-ddr3-native"
DEPENDS += "litex-uber-ddr3-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"
DEPENDS += "quartus-native"
DEPENDS += "litex"

inherit setuptools3

# disable any security flags set by security_flags.inc (e.g. poky distro)
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

inherit quartus-native

do_compile[network] = "1"

do_compile() {
    quartus_prepare_run
    echo $LD_LIBRARY_PATH
    python3 ${RECIPE_SYSROOT_NATIVE}${PYTHON_SITEPACKAGES_DIR}/litex_boards/targets/terasic_deca.py --build \
   --cpu-type vexriscv --cpu-variant minimal --sys-clk-freq 50e6 \
   --uart-baudrate=115200 \
   --bios-console=full \
   --with-dram 
}

do_deploy () {
    install -Dm 0644 ${B}/build/terasic_deca/gateware/terasic_deca.sof ${DEPLOYDIR}
    install -Dm 0644 ${B}/build/terasic_deca/gateware/terasic_deca.pof ${DEPLOYDIR}
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install
