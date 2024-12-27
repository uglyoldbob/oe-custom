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
DEPENDS += "custom-litex-boards-native"
DEPENDS += "litex-boards-native"
DEPENDS += "litex-oldsystems-native"
DEPENDS += "litex-pythondata-cpu-vexriscv-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"
DEPENDS += "litex-pythondata-software-picolibc-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"
DEPENDS += "gowin-native"
DEPENDS += "litex"

inherit setuptools3

# disable any security flags set by security_flags.inc (e.g. poky distro)
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

inherit quartus-native

do_compile[network] = "1"

do_compile() {
    echo $LD_LIBRARY_PATH
    python3 ${RECIPE_SYSROOT_NATIVE}${PYTHON_SITEPACKAGES_DIR}/uob-litex-boards/targets/uob_pcie1.py --build \
    --cpu-type vexriscv --cpu-variant linux \
    --uart-baudrate=115200 \
    --bios-console=full
}

do_deploy () {
    install -Dm 0644 ${B}/build/sipeed_tang_nano_20k/gateware/sipeed_tang_nano_20k.fs ${DEPLOYDIR}
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install
