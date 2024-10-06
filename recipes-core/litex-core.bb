SUMMARY = "LiteX boards files"
HOMEPAGE = "https://github.com/litex-hub/litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "\
	git://github.com/litex-hub/litex-boards;protocol=https;branch=master;name=master \
	git://github.com/enjoy-digital/litex;protocol=https;branch=master;name=litex;destsuffix=litex2 \
	https://github.com/enjoy-digital/litex/files/6076336/ter-u16b.txt;name=font;destsuffix=font \
"
SRCREV_FORMAT = "master_litex_font"
SRC_URI[font.sha256sum] = "92861fecd6f94c5ecc764fc43df5a69eb000ae13a75f652c6525db0495274f0a"
SRCREV_master = "2022.04"
SRCREV_litex = "64cf925b39b92cfc6dadced6b1067a9bd50a1d7f"
SRCPV = "2022.04"
PV = "2022.04+git${SRCPV}"

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

addtask copy_font after do_unpack before do_compile

do_copy_font() {
    cp ${UNPACKDIR}/ter-u16b.txt ${S}/ter-u16b.bdf
}

do_compile() {
    quartus_prepare_run
    echo $LD_LIBRARY_PATH
    ${S}/litex_boards/targets/terasic_deca.py --build \
   --cpu-type vexriscv --cpu-variant standard --sys-clk-freq 50e6 \
   --with-ethernet --with-video-terminal --uart-name=serial \
   --uart-baudrate=115200 \
   --bios-console=full
}

do_deploy () {
    install -Dm 0644 ${B}/build/terasic_deca/gateware/terasic_deca.sof ${DEPLOYDIR}
    install -Dm 0644 ${B}/build/terasic_deca/gateware/terasic_deca.pof ${DEPLOYDIR}
}
do_install[noexec] = "1"

addtask deploy before do_build after do_install
