SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

S = "${WORKDIR}/git"

inherit deploy
inherit litex-native
inherit python3native

# inhibit default deps, to exclude libc
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "virtual/${HOST_PREFIX}gcc"

DEPENDS += "meson-native ninja-native"
DEPENDS += "yosys-native"
DEPENDS += "migen-native"
DEPENDS += "litex-native"

DEPENDS += "${LITEX_CORE_DEPENDS}"

DEPENDS += "litex-boards-native"
DEPENDS += "litex-pythondata-software-compiler-rt-native"
DEPENDS += "litex-pythondata-software-picolibc-native"
DEPENDS += "litedram-native"
DEPENDS += "litesdcard-native"
DEPENDS += "litex"

inherit setuptools3

# disable any security flags set by security_flags.inc (e.g. poky distro)
SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

inherit quartus-native
inherit radiant-native
inherit vivado-native

do_compile[network] = "1"

do_compile() {
    ${LITEX_COMPILE_PREPARE}
    python3 ${RECIPE_SYSROOT_NATIVE}${PYTHON_SITEPACKAGES_DIR}/${LITEX_BOARD} --build ${LITEX_BOARD_ARGS}
}

do_deploy () {
    for f in ${LITEX_RESULT}; do
        install -Dm 0644 ${B}/build/$f ${DEPLOYDIR}
    done
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install
