SUMMARY = "Vivado"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native"

SRC_URI = "file://FPGAs_AdaptiveSoCs_Unified_2024.2_1113_1001_Lin64.bin \
"

SRC_URI[sha256sum] = "1cd0b9ce86897509b12f05bebd0ec2a7b193b7168c37d82676584e9211a6e2fa"

S = "${UNPACKDIR}"

inherit terminal native

do_compile[network] = "1"

do_configure() {
	${UNPACKDIR}/FPGAs_AdaptiveSoCs_Unified_2024.2_1113_1001_Lin64.bin --keep --noexec --target ${WORKDIR}
}

addtask generate after do_configure

do_generateb() {
	cp ~/.Xilinx/install_config.txt ${WORKDIR}/install_config.txt
	sed -i "s#tools/Xilinx#/${WORKDIR}/Xilinx#" ${WORKDIR}/install_config.txt
}

python do_generate() {
	oe_terminal("sh -c '%s/xsetup -b ConfigGen; if [ \\$? -ne 0 ]; then echo \"Command failed.\"; printf \"Press any key to continue... \"; read r; fi'" % d.getVar('WORKDIR'),
                d.getVar('PN') + ' Configuration', d)
	bb.build.exec_func("do_generateb", d)
}

addtask generate2 after do_generate before do_compile
do_generate2[network] = "1"
python do_generate2() {
	oe_terminal("sh -c '%s/xsetup -b AuthTokenGen; if [ \\$? -ne 0 ]; then echo \"Command failed.\"; printf \"Press any key to continue... \"; read r; fi'" % d.getVar('WORKDIR'),
                d.getVar('PN') + ' Configuration2', d)
}

do_compile() {
	sed -i "s/Shortcuts=1/Shortcuts=0/" ${WORKDIR}/install_config.txt
	sed -i "s/Association=1/Association=0/" ${WORKDIR}/install_config.txt
	${WORKDIR}/xsetup -b Install -a XilinxEULA,3rdPartyEULA -c ${WORKDIR}/install_config.txt
}

SYSROOT_DIRS_NATIVE += "${prefix}/vivado"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP = "pkgconfig"

do_install() {
	install -d ${D}${prefix}/vivado
	install -d ${D}${bindir}
	mv ${WORKDIR}/Xilinx/* ${D}${prefix}/vivado
#	ln -s -r ${D}${prefix}/gowin/IDE/bin/gw_sh ${D}${bindir}/gw_sh 
}
