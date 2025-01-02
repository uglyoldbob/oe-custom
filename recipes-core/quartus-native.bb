SUMMARY = "Quartus lite"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native"

SRC_URI = "\
	file://qinst-lite-linux-23.1std.1-993.run \
"

S = "${UNPACKDIR}"

do_compile[network] = "1"

do_compile() {
    ${UNPACKDIR}/qinst-lite-linux-23.1std.1-993.run --target ${WORKDIR} --noexec
    chmod -R +w ${WORKDIR}/q
    rm -rf ${WORKDIR}/q
    ${WORKDIR}/qinst.sh --cli --accept-eula --download-dir ${WORKDIR}/q --install-dir ${WORKDIR}/q
}

addtask fix_files after do_compile before do_install

do_fix_files() {
	ls -la ${WORKDIR}/q
	chmod +xw -R ${WORKDIR}/q
}

inherit native

SYSROOT_DIRS_NATIVE += "${prefix}/quartus"
INHIBIT_SYSROOT_STRIP = "1"

do_install() {
	install -d ${D}${prefix}/quartus
	cp --no-preserve=ownership -r ${WORKDIR}/q/* ${D}${prefix}/quartus
	rm ${D}${prefix}/quartus/quartus/linux64/tclsh
	rm ${D}${prefix}/quartus/quartus/linux64/wish
}
