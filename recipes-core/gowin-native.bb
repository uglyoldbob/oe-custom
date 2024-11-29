SUMMARY = "Quartus lite"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native"

SRC_URI = "\
	file://Gowin_V1.9.10.03_linux.tar.gz \
"

S = "${UNPACKDIR}"

inherit native

SYSROOT_DIRS_NATIVE += "${prefix}/gowin"

do_install() {
	install -d ${D}${prefix}/gowin
	cp -r ${UNPACKDIR}/* ${D}${prefix}/gowin
	rm ${D}${prefix}/gowin/IDE/lib/libfreetype.so.6
}
